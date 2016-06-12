package Controleur;

import Modele.Jeu;
import Vue.VueGrilleJeux;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControleurCaseGrille extends Rectangle {

    int x;
    int y;
    Stage st;
    VueGrilleJeux fenetre;

    public ControleurCaseGrille(int x, int y, Stage s, VueGrilleJeux f) {

        this.x = x;
        this.y = y;
        this.st = s;
        this.fenetre = f;
        this.setOnMouseClicked((MouseEvent mouseEvent) -> {
            Jeu m = f.getJeu();
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                switch (m.getG().getCase_X_Y(x, y).getValeur_bouton()) {
                    case 0:
                        m.MettreDrapeau(x, y);
                        break;
                    case 1:
                        m.MettrePointInterrogation(x, y);
                        break;
                    case 2:
                        m.MettreCaseVide(x, y);
                        break;
                    default:
                        break;
                }
                f.start(st);
            } else if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                if (m.getG().getCase_X_Y(x, y).getValeur_bouton() == 0) {
                    // Il n'est possible de jouer que sur une case vide sans ? ni drapeau
                    m.ClicCase(x, y);
                    f.start(st);
                }
            }
        });
    }

}
