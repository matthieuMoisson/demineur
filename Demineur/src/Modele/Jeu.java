package Modele;

import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Jeu extends Observable {

    private GrilleCaseCarre g;
    private boolean finChrono;
    private int lNext, hNext, nbMineNext; // Pour la partie suivante
    private long tempActuel;
    private boolean premierClicEffectue;

    public Jeu(int longueur, int hauteur, int nbMines) {
        this.lNext = longueur;
        this.hNext = hauteur;
        this.nbMineNext = nbMines;
        this.finChrono = false;
        this.tempActuel = 0;
        this.premierClicEffectue = false;
        g = new GrilleCaseCarre(longueur, hauteur, nbMines);
    }

    public void restartJeu() {
        this.finChrono = true;
        this.premierClicEffectue = false;
        this.tempActuel = 0;
        g.reinitialiserGrille();
        setChanged();
        notifyObservers();
        try {//On attend pour que le thread du chrono soit bien arreté
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.finChrono = false;
    }

    public void changeNiveauJeu(int l, int h, int nbM) {
        this.lNext = l;
        this.hNext = h;
        this.nbMineNext = nbM;
        this.finChrono = true;
        this.premierClicEffectue = false;
        this.tempActuel = 0;
        g = new GrilleCaseCarre(l, h, nbM);
        try {//On attend pour que le thread du chrono soit bien arreté
            Thread.sleep(100);
        } catch (InterruptedException ex) {
            Logger.getLogger(Jeu.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.finChrono = false;
    }

    public boolean est_une_bombe(int x, int y) {
        return g.getTabCases()[x][y].getValeur() == -1;
    }

    public void MettreDrapeau(int x, int y) {
        g.getTabCases()[x][y].setValeur_bouton(1);
    }

    public void MettrePointInterrogation(int x, int y) {
        g.getTabCases()[x][y].setValeur_bouton(2);
    }

    public void MettreCaseVide(int x, int y) {
        g.getTabCases()[x][y].setValeur_bouton(0);
    }

    public void ClicDroitCase(int x, int y) {
        if (!premierClicEffectue) {
            premierClicEffectue = true;
        }
        switch (getG().getCase_X_Y(x, y).getValeur_bouton()) {
            case 0:
                MettreDrapeau(x, y);
                break;
            case 1:
                MettrePointInterrogation(x, y);
                break;
            case 2:
                MettreCaseVide(x, y);
                break;
            default:
                break;
        }
        // Notifier la vue de la mise a jour
        setChanged();
        notifyObservers();
    }

    public void ClicGaucheCase(int x, int y) {
        if (!premierClicEffectue) {
            premierClicEffectue = true;
        }
        if (getG().getCase_X_Y(x, y).getValeur_bouton() == 0) {
            // Il n'est possible de jouer que sur une case vide sans ? ni drapeau
            if (g.getTabCases()[x][y].getValeur() == -1) {
                //Il a perdu donc on devoile tout
                g.devoiler();
                this.finChrono = true;
            } else {
                g.getTabCases()[x][y].setEtat(true);
                g.algoDevoilement(x, y);
            }
            // Notifier la vue de la mise a jour
            setChanged();
            notifyObservers();
        }
    }

    /**
     * @return the g
     */
    public GrilleCaseCarre getG() {
        return g;
    }

    public boolean getPartiePerdu() {
        return this.finChrono;
    }

    public void setPartieGagne(boolean b) {
        this.finChrono = b;
    }

    public int getlNext() {
        return lNext;
    }

    public void setlNext(int lNext) {
        this.lNext = lNext;
    }

    public int gethNext() {
        return hNext;
    }

    public void sethNext(int hNext) {
        this.hNext = hNext;
    }

    public int getNbMineNext() {
        return nbMineNext;
    }

    public void setNbMineNext(int nbMineNext) {
        this.nbMineNext = nbMineNext;
    }

    public long getTempActuel() {
        return this.tempActuel;
    }

    public boolean dejaClique(){
        return premierClicEffectue;
    }
    
    public void tempDeJeu() {
        long debut;
        debut = System.currentTimeMillis();
        while (!finChrono) {
            tempActuel = (System.currentTimeMillis() - debut)/1000;
            try {
                Thread.sleep(50);
            } catch (InterruptedException ex) {
            }
            setChanged();
            notifyObservers("temp");
        }
    }

}
