package Controleur;

import Modele.Jeu;
import Vue.VueGrilleJeux;
import javafx.event.ActionEvent;
import javafx.scene.control.MenuItem;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class ControleurDifficulte extends Rectangle {

    Stage st;
    VueGrilleJeux fenetre;
    MenuItem item;
    String nom;
    int difficulte; //0 débutant 1 intermédiaire 2 expert, 3 personalisé
    //l*h*nbMine
    //Débutant = 9*9*10 
    //intérmédiaire = 16*16*40
    //Expert = 30*16*100
    
    int l, h, nbMine; // Pour la difficulté personalisé
    Jeu jeu;
    
    
    public ControleurDifficulte(Stage s, VueGrilleJeux f, Jeu jeu, int d, String nom) {

        this.st = s;
        this.difficulte=d;
        this.nom = nom;
        this.jeu = jeu;
        this.fenetre = f;
        
        this.item = new MenuItem(nom);
        item.setOnAction((ActionEvent actonEvent) -> {
            switch (difficulte) {
                case 0:
                    jeu.changeNiveauJeu(9, 9, 10);
                    break;
                case 1:
                    jeu.changeNiveauJeu(16, 16, 40);
                    break;
                case 2:
                    jeu.changeNiveauJeu(30, 16, 100);
                    break;
                case 3:
                    jeu.changeNiveauJeu(jeu.getlNext(), jeu.gethNext(), jeu.getNbMineNext());
                    break;
                default:
                    break;
            }
            f.start(st);
        });
    }
    
    public MenuItem getMenuItem(){
        return this.item;
    }
}
