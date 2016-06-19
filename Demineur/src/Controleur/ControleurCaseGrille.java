package Controleur;

import Modele.Jeu;
import java.util.concurrent.ExecutorService;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControleurCaseGrille extends Rectangle {

    int x;
    int y;
    Stage st;
    private Jeu jeu;
    private final ExecutorService executor;

    public ControleurCaseGrille(int x, int y, Stage s, Jeu j, ExecutorService executor) {

        this.x = x;
        this.y = y;
        this.st = s;
        this.jeu = j;
        this.executor = executor;

        this.setOnMouseClicked((MouseEvent mouseEvent) -> {
            if (mouseEvent.getButton().equals(MouseButton.SECONDARY)) {
                executor.execute(() -> jeu.ClicDroitCase(x, y));
                //m.ClicDroitCase(x, y);
            } else if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
                executor.execute(() -> jeu.ClicGaucheCase(x, y));
            }
            if(!jeu.dejaClique())
                executor.execute(() -> jeu.tempDeJeu());
        });
    }

}
