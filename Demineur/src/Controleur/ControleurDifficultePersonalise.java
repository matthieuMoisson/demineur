package Controleur;

import Modele.Jeu;
import Vue.VueGrilleJeux;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControleurDifficultePersonalise extends Rectangle {

    Stage st;
    VueGrilleJeux fenetre;
    int min, max, value;
    int direction; //0 -> longueur; 1 -> hauteur; 2 -> NbMine
    Spinner spinner;
    Jeu jeu;

    public ControleurDifficultePersonalise(Stage s, VueGrilleJeux f, Jeu j, int min, int max, int m, int d) {
        this.min = min;
        this.max = max;
        this.value = m;
        this.spinner = new Spinner(min, max, value);
        this.direction = d;
        this.jeu = j;

        this.spinner.valueProperty().addListener(new ChangeListener<Number>() {
            @Override
            public void changed(ObservableValue<? extends Number> ov, Number old_val, Number new_val) {
                switch (d) {
                    case 0:
                        jeu.setlNext(new_val.intValue());
                        break;
                    case 1:
                        jeu.sethNext(new_val.intValue());
                        break;
                    case 2:
                        jeu.setNbMineNext(new_val.intValue());
                        break;
                    default:
                        break;
                }
            }
        });
    }

    public Spinner getSpinner() {
        return this.spinner;
    }

}
