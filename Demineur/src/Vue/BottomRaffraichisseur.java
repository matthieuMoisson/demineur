/*
 * Polytech Lyon - 2016
 * Jensen JOYMANGUL & Gaetan MARTIN
 * Projet Informatique 3A - Creation d'un demineur MVC
 */
package Vue;

import Controleur.ControlleurBtmSmilley;
import Modele.GrilleCaseCarre;
import Modele.Jeu;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;

/**
 * Class ImageRefresher used to refresh the GUI
 *
 * @author Gaetan
 */
public class BottomRaffraichisseur implements Runnable {
    
    private final Image smileyGagner = new Image("img/smiley_content.png");
    private final Image smiley = new Image("img/smiley.png");
    private final Image smileyPerdu = new Image("img/smiley_pleure.png");
    
    private final Color couleurGauche = Color.ORANGE;
    private final Color couleurDroite = Color.PALEGREEN;
    
    private final GrilleCaseCarre grille;
    private final Jeu jeu;
    private final ControlleurBtmSmilley c;
    private final BorderPane bP;
    VueGrilleJeux f;
    private Text score;
    
    public BottomRaffraichisseur(GrilleCaseCarre grille, Jeu j, ControlleurBtmSmilley c, BorderPane bP, Text t) {
        this.grille = grille;
        this.jeu = j;
        this.c = c;
        this.bP = bP;
        this.f = f;
        this.score = t;
    }

    @Override
    public void run() {
        if (grille.partieGagne())//Si la partie est gagné
        {
            c.setFill(new ImagePattern(smileyGagner));
        } else if(jeu.getPartiePerdu())// Si la partie est perdu
        {
            c.setFill(new ImagePattern(smileyPerdu));
        }else 
            c.setFill(new ImagePattern(smiley));
        String subSCouleurGauche = couleurGauche.toString().substring(2,  couleurGauche.toString().length()-2);
        String subSCouleurDroite = couleurDroite.toString().substring(2,  couleurDroite.toString().length()-2);
        //bP.setStyle("-fx-background-color: linear-gradient(from 25% 25% to 75% 75%, #"+subSCouleurGauche+", #"+subSCouleurDroite+")");
        score.setText("" + grille.getScore());
    }
}
