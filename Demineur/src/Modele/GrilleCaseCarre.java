package Modele;

public class GrilleCaseCarre {

    private CaseCarre[][] tabCases;
    // Notre tableau est de longueur (horizontal = x), hauteur (vertical = y)
    private int longueur;
    private int hauteur;
    private final int nbMines; // Nombre de mine total sur la grille initialiser au début ne change pas durant la partie

    public GrilleCaseCarre(int longueur, int hauteur, int nbMines) {
        this.longueur = longueur;
        this.hauteur = hauteur;
        if (nbMines > longueur * hauteur) {
            this.nbMines = (int) (longueur * hauteur * 0.2);
        } else {
            this.nbMines = nbMines;
        }
        initGrilleVide();
        poserMinesGrille();
        genererNbGrille();
        //afficheGrille();
    }

    public void reinitialiserGrille() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                tabCases[x][y].reinitialiser();
            }
        }
        poserMinesGrille();
        genererNbGrille();
    }

    /**
     * Pour initialiser la grille de jeu avec des case vide
     */
    public void initGrilleVide() {
        tabCases = new CaseCarre[longueur][hauteur];
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                tabCases[x][y] = new CaseCarre(x, y, 0);
            }
        }
    }

    public void poserMinesGrille() {
        for (int i = 0; i < nbMines; i++) {

            int x, y;
            boolean b = false;
            do {

                x = (int) (Math.random() * longueur - 1);
                y = (int) (Math.random() * hauteur - 1);

                if (tabCases[x][y].getValeur() == 0) {
                    b = true;
                }
            } while (!b);
            tabCases[x][y].setValeur(-1);
        }
    }

    public int nbMineVoisins(int x, int y) {
        int conteur = 0;
        CaseCarre[] tabVoisins = getVoisins(tabCases[x][y].getX(), tabCases[x][y].getY());
        for (CaseCarre voisin : tabVoisins) {
            if (voisin.getValeur() == -1) {
                conteur++;
            }
        }
        return conteur;
    }

    public void genererNbGrille() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                if (tabCases[x][y].getValeur() != -1) {
                    tabCases[x][y].setValeur(nbMineVoisins(x, y));
                }
            }
        }
    }

    //Teste si un indice est dans le tableau renvoie vrai si il l'est
    public boolean estDansTab(int x, int y) {
        return (x >= 0 && y >= 0 && x < this.longueur && y < this.hauteur);
    }

    //Parcour toutes les case adjacente a une case, teste si elle appartienne au tableau. 
    //Si oui l'ajoute a un tableau de case et le renvoie
    public CaseCarre[] getVoisins(int x, int y) {
        CaseCarre[] tab_voisins, tabTmp;
        int indice = 0;
        tabTmp = new CaseCarre[8];
        for (int j = y - 1; j <= y + 1; j++) {
            for (int i = x - 1; i <= x + 1; i++) {
                if (!(i == x && j == y) && estDansTab(i, j)) {
                    tabTmp[indice] = tabCases[i][j];
                    indice++;
                }
            }
        }
        tab_voisins = new CaseCarre[indice];
        System.arraycopy(tabTmp, 0, tab_voisins, 0, indice);
        return tab_voisins;
    }

    //Parcour l'ensemble des case pour devoier tout le plateau de jeu
    public void devoiler() {
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                tabCases[x][y].setEtat(true);
            }
        }
    }

    //Pour devoiler les case a devoiler apres un clique
    public void algoDevoilement(int x, int y) {
        CaseCarre[] voisins = getVoisins(x, y);
        CaseCarre caseCourante;
        caseCourante = tabCases[x][y];
        if (caseCourante.getValeur() == 0) { // Si il y a aucune case voisine
            caseCourante.setEtat(true);
            for (CaseCarre voisin1 : voisins) {
                if (!voisin1.getEtat()) {
                    algoDevoilement(voisin1.getX(), voisin1.getY());
                }
            }
        } else if (caseCourante.getValeur() != -1) { //Si la case n'est pas une bombe
            caseCourante.setEtat(true);
        }
    }

    public boolean partieGagne() {
        //La partie est gagné quand il y a autant de case dévoilé que de case - le nombre de mine
        return nbCaseDevoile() == (this.longueur) * (this.hauteur) - this.nbMines;
    }

    public int nbCaseDevoile() {
        int conteur = 0;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                if (tabCases[x][y].getEtat()) {
                    conteur++;
                }
            }
        }
        return conteur;
    }

    /**
     * @return the tab_cases
     */
    public CaseCarre[][] getTabCases() {
        return tabCases;
    }

    /**
     * @param x
     * @param y
     * @return the tab_cases
     */
    public CaseCarre getCase_X_Y(int x, int y) {
        return this.tabCases[x][y];
    }

    /**
     * @param tab_cases the tab_cases to set
     */
    public void setTabCases(CaseCarre[][] tab_cases) {
        this.tabCases = tab_cases;
    }

    /**
     * @return the longueur
     */
    public int getLongueur() {
        return longueur;
    }

    /**
     * @param longueur the longueur to set
     */
    public void setLongueur(int longueur) {
        this.longueur = longueur;
    }

    /**
     * @return the hauteur
     */
    public int getHauteur() {
        return hauteur;
    }

    /**
     * @param hauteur the hauteur to set
     */
    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    /**
     * @return the nb_mines
     */
    public int getNbMines() {
        return nbMines;
    }

    public int getScore() {
        int score = this.nbMines;
        for (int y = 0; y < hauteur; y++) {
            for (int x = 0; x < longueur; x++) {
                if (!tabCases[x][y].getEtat()) {
                    if (tabCases[x][y].getValeur_bouton() == 1) {//Si c'est un drapeau
                        score--;
                    }
                }

            }
        }
        if (score < 0) {
            return 0;
        }
        return score;
    }

}
