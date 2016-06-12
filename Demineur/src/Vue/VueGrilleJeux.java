package Vue;

import Controleur.ControlleurBtmSmilley;
import Controleur.ControleurCaseGrille;
import Controleur.ControleurDifficulte;

import Modele.Grille;
import javafx.application.Application;
import Modele.Jeu;
import javafx.scene.Scene;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.Slider;

import javafx.scene.image.Image;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 *
 * @author freder
 */
public class VueGrilleJeux extends Application {

    // affiche la saisie et le résultat
    Text score;

    static Jeu game;
   

    // initialisation du modèle que l'on souhaite utiliser
    Image drapeau = new Image("img/drapeau.png");
    Image bomb_rouge = new Image("img/bomb_rouge.png");
    Image bomb_noir = new Image("img/bomb_noir.png");
    Image bomb_erreur = new Image("img/bomb_erreur.png");
    Image case_devoile = new Image("img/case_devoile.png");
    Image case_non_devoile = new Image("img/case_non_devoile.png");

    Image smileyGagner = new Image("img/smiley_content.png");
    Image smiley = new Image("img/smiley.png");
    Image smileyPerdu = new Image("img/smiley_pleure.png");

    Image numeroUn = new Image("img/num_un.png");
    Image numeroDeux = new Image("img/num_deux.png");
    Image numeroTrois = new Image("img/num_trois.png");
    Image numeroQuatre = new Image("img/num_quatre.png");
    Image numeroCinq = new Image("img/num_cinq.png");
    Image numeroSix = new Image("img/num_six.png");
    Image numeroSept = new Image("img/num_sept.png");
    Image numeroHuit = new Image("img/num_huit.png");
    Image vide = new Image("img/EMPTY.png");

    @Override
    public void start(Stage primaryStage) {

        // gestion du placement (permet de palcer le champ Text affichage en haut, et GridPane gPane au centre)
        BorderPane border = new BorderPane();

        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Difficulté");
        ControleurDifficulte facilControleur = new ControleurDifficulte(primaryStage, this, 0, "Débutant");
        ControleurDifficulte moyenControleur = new ControleurDifficulte(primaryStage, this, 1, "Intermédiaire");
        ControleurDifficulte difficileControleur = new ControleurDifficulte(primaryStage, this, 2, "Expert");
        Menu difficulteAvance = new Menu("Avance");

        fileMenu.getItems().setAll(facilControleur.getMenuItem(), moyenControleur.getMenuItem(), difficileControleur.getMenuItem(), difficulteAvance);
        ControleurDifficulte personalierControleur = new ControleurDifficulte(primaryStage, this, 3, "Personaliser  : longueur*hauteur*mine");
        Slider sliderLongueur = new Slider(0, 40, 20);
        Slider sliderHauteur = new Slider(0, 25, 20);
        Slider sliderMine = new Slider(0, 1000, 75);
        
        CustomMenuItem cmiLongueur = new CustomMenuItem(sliderLongueur);
        CustomMenuItem cmiHauteur = new CustomMenuItem(sliderHauteur);
        CustomMenuItem cmiMine = new CustomMenuItem(sliderMine);
        cmiLongueur.setHideOnClick(false);
        cmiHauteur.setHideOnClick(false);
        cmiMine.setHideOnClick(false);

        difficulteAvance.getItems().setAll(personalierControleur.getMenuItem(),cmiLongueur, cmiHauteur, cmiMine);

        Menu editMenu = new Menu("Affichage");
        Menu helpMenu = new Menu("Aide");
        menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);
        border.setTop(menuBar);

        // permet de placer les diffrents boutons dans une grille
        GridPane gPane = new GridPane();

        Grille g = game.getG();
        // création des bouton et placement dans la grille

        for (int y = 0; y < g.getHauteur(); y++) {
            for (int x = 0; x < g.getLongueur(); x++) {
                final Text t;
                if (g.getCase_X_Y(x, y).getEtat())// La case est dévoilé on l'affiche
                {
                    switch (g.getCase_X_Y(x, y).getValeur()) {
                        case -1: {
                            //On place une bombe
                            ControleurCaseGrille c = new ControleurCaseGrille(x, y, primaryStage, this);
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(bomb_rouge));
                            gPane.add(c, x, y);
                            break;
                        }
                        //On place un chiffre ou rien
                        case 0:
                            Rectangle r = new Rectangle();
                            r.setWidth(28);
                            r.setHeight(28);
                            r.setFill(new ImagePattern(vide));
                            gPane.add(r, x, y);
                            break;
                        default: {
                            ControleurCaseGrille c = affichageNumero(x, y, primaryStage, g);
                            gPane.add(c, x, y);
                            break;
                        }
                    }

                } else //La case n'est pas dévoilé...
                {
                    ControleurCaseGrille c = new ControleurCaseGrille(x, y, primaryStage, this);
                    switch (g.getCase_X_Y(x, y).getValeur_bouton()) {
                        //il y a une case vide a cliquer
                        case 0:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(case_non_devoile));
                            gPane.add(c, x, y);
                            break;
                        //Il y a un flag (drapeau)
                        case 1:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(drapeau));
                            gPane.add(c, x, y);
                            break;
                        //Il y a un point d'interogation
                        case 2:
                            c.setX(36 + (x * 30));
                            c.setY(150 + (y * 30));
                            c.setWidth(28);
                            c.setHeight(28);
                            c.setFill(new ImagePattern(numeroHuit));
                            gPane.add(c, x, y);
                            break;
                        default:
                            break;
                    }
                }
            }
        }

        BorderPane bottomPane = new BorderPane();
        ControlleurBtmSmilley c = new ControlleurBtmSmilley(primaryStage, this);
        c.setWidth(40);
        c.setHeight(40);
        bottomPane.setCenter(c);

        if (g.partieGagne())//Si la partie est gagné
        {
            c.setFill(new ImagePattern(smileyGagner));
        } else if (game.getPartiePerdu())// Si la partie est perdu
        {
            c.setFill(new ImagePattern(smileyPerdu));
        } else {//Si la oartie est en cours
            c.setFill(new ImagePattern(smiley));
        }

        Text t;
        t = new Text("" + g.getScore());
        t.setFont(Font.font("Verdana", 20));
        t.setTextAlignment(TextAlignment.CENTER);
        bottomPane.setLeft(t);

        t = new Text("Ti");
        t.setFont(Font.font("Verdana", 20));
        t.setTextAlignment(TextAlignment.RIGHT);
        bottomPane.setRight(t);

        gPane.setGridLinesVisible(true);

        border.setBottom(bottomPane);
        border.setCenter(gPane);

        Scene scene = new Scene(border, Color.GAINSBORO);

        primaryStage.setTitle("Démineur");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public ControleurCaseGrille affichageNumero(int i, int j, Stage s, Grille g) {
        ControleurCaseGrille c = new ControleurCaseGrille(i, j, s, this);
        c.setWidth(28);
        c.setHeight(28);
        switch (g.getCase_X_Y(i, j).getValeur()) {
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

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        game = new Jeu(9, 9, 10);
        launch(args);

    }

    public Jeu getJeu() {
        return this.game;
    }

    public void restart() {
        game.restartJeu();
    }

    public Jeu newJeu(int l, int h, int nbMine) {
        game = new Jeu(l, h, nbMine);
        return game;
    }

}
