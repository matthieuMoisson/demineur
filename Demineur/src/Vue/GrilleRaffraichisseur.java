/*
 * Polytech Lyon - 2016
 * Jensen JOYMANGUL & Gaetan MARTIN
 * Projet Informatique 3A - Creation d'un demineur MVC
 */
package Vue;

import Controleur.ControleurCaseGrille;
import Modele.GrilleCaseCarre;
import Modele.Jeu;
import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;

/**
 * Class ImageRefresher used to refresh the GUI
 *
 * @author Gaetan
 */
public class GrilleRaffraichisseur implements Runnable {

    private final GrilleCaseCarre grille;
    
    // initialisation du modèle que l'on souhaite utiliser
    Image interro = new Image("img/pointInterro.png");
    Image drapeau = new Image("img/drapeau.png");
    Image bomb_rouge = new Image("img/bomb_rouge.png");
    Image bomb_noir = new Image("img/bomb_noir.png");
    Image bomb_erreur = new Image("img/bomb_erreur.png");
    Image case_devoile = new Image("img/case_devoile.png");
    Image case_non_devoile = new Image("img/case_non_devoile.png");

    Image numeroUn = new Image("img/num_un.png");
    Image numeroDeux = new Image("img/num_deux.png");
    Image numeroTrois = new Image("img/num_trois.png");
    Image numeroQuatre = new Image("img/num_quatre.png");
    Image numeroCinq = new Image("img/num_cinq.png");
    Image numeroSix = new Image("img/num_six.png");
    Image numeroSept = new Image("img/num_sept.png");
    Image numeroHuit = new Image("img/num_huit.png");
    Image vide = new Image("img/EMPTY.png");
    
    private final ControleurCaseGrille[][] controleursGrille;
    private final Jeu j;
    
    public GrilleRaffraichisseur(GrilleCaseCarre grille, ControleurCaseGrille[][] controleursGrille, Jeu j) {
        this.grille = grille;
        this.controleursGrille = controleursGrille;
        this.j = j;
    }

    @Override
    public void run() {
        for (int y = 0; y < grille.getHauteur(); y++) {
            for (int x = 0; x < grille.getLongueur(); x++) {
                if (grille.getCase_X_Y(x, y).getEtat())// La case est dévoilé on l'affiche
                {
                    switch (grille.getCase_X_Y(x, y).getValeur()) {
                        case -1: {
                            //On place une bombe
                            ControleurCaseGrille c = this.controleursGrille[x][y];
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(bomb_rouge));
                            break;
                        }
                        //On place un chiffre ou rien
                        case 0:
                            ControleurCaseGrille c = this.controleursGrille[x][y];
                            c.setFill(new ImagePattern(vide));
                            break;
                        default: {
                            affichageNumero(x, y);
                            break;
                        }
                    }

                } else //La case n'est pas dévoilé...
                {
                    ControleurCaseGrille c = this.controleursGrille[x][y];
                    switch (grille.getCase_X_Y(x, y).getValeur_bouton()) {
                        //il y a une case vide a cliquer
                        case 0:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(case_non_devoile));
                            break;
                        //Il y a un flag (drapeau)
                        case 1:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(drapeau));
                            break;
                        //Il y a un point d'interogation
                        case 2:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(interro));
                            break;
                        default:
                            break;
                    }
                }
            }
        }
    }
    
    public ControleurCaseGrille affichageNumero(int x, int y) {
        ControleurCaseGrille c = this.controleursGrille[x][y];
        c.setWidth(28);
        c.setHeight(28);
        switch (grille.getCase_X_Y(x, y).getValeur()) {
            case 1:
                c.setFill(new ImagePattern(numeroUn));
                break;
            case 2:
                c.setFill(new ImagePattern(numeroDeux));
                break;
            case 3:
                c.setFill(new ImagePattern(numeroTrois));
                break;
            case 4:
                c.setFill(new ImagePattern(numeroQuatre));
                break;
            case 5:
                c.setFill(new ImagePattern(numeroCinq));
                break;
            case 6:
                c.setFill(new ImagePattern(numeroSix));
                break;
            case 7:
                c.setFill(new ImagePattern(numeroSept));
                break;
            case 8:
                c.setFill(new ImagePattern(numeroHuit));
                break;
            default:
                break;
        }
        return c;
    }
}
