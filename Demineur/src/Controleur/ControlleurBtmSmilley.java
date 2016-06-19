/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controleur;

import Modele.Jeu;
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
    Jeu jeu;

    public ControlleurBtmSmilley(Stage s, VueGrilleJeux f, Jeu jeu) {
        this.st = s;
        this.fenetre = f;
        this.jeu = jeu;
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent mouseEvent) {
                jeu.restartJeu();
            }
        });
    }
}
