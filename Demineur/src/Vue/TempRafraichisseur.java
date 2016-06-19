/*
 * Polytech Lyon - 2016
 * Jensen JOYMANGUL & Gaetan MARTIN
 * Projet Informatique 3A - Creation d'un demineur MVC
 */
package Vue;

import Modele.GrilleCaseCarre;
import Modele.Jeu;
import javafx.scene.text.Text;

/**
 * Class ImageRefresher used to refresh the GUI
 *
 * @author Gaetan
 */
public class TempRafraichisseur implements Runnable {
    
    private final GrilleCaseCarre grille;
    private final Jeu jeu;
    private final Text ttemp;
    
    public TempRafraichisseur(GrilleCaseCarre grille, Jeu j, Text ttemp) {
        this.grille = grille;
        this.jeu = j;
        this.ttemp = ttemp;
    }

    @Override
    public void run() {
        this.ttemp.setText(""+jeu.getTempActuel());
    }
}
