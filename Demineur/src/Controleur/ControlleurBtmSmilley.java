/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Vue.VueGrilleJeux;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 *
 * @author Matthieu
 */
public class ControlleurBtmSmilley extends Rectangle {

    Stage st;
    VueGrilleJeux fenetre;

    public ControlleurBtmSmilley(Stage s, VueGrilleJeux f) {
        this.st = s;
        this.fenetre = f;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                //f.getJeu().setPartieGagne(false);
                //f.getJeu().getG().reinitialiserGrille();
                f.restart();
                f.start(st);
            }

        });
    }

}
