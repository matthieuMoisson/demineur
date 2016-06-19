package Vue;

import Controleur.ControlleurBtmSmilley;
import Controleur.ControleurCaseGrille;
import Controleur.ControleurDifficulte;
import Controleur.ControleurDifficultePersonalise;

import Modele.GrilleCaseCarre;
import javafx.application.Application;
import Modele.Jeu;
import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;

import javafx.scene.image.Image;

import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

public class VueGrilleJeux extends Application implements Observer{

    // affiche la saisie et le résultat
    Stage primaryStage;
    private static Jeu game;
    private BorderPane principale;
    private ControleurCaseGrille[][] controleursGrille;
    private static GrilleRaffraichisseur grilleRaffraichisseur;
    private static TempRafraichisseur tempRafraichisseur;
    private static BottomRaffraichisseur boutonRaffraichisseur;
    private ControlleurBtmSmilley btmSmiley;
    private Text ttemp;
    private Text score;
    
    private Image smiley = new Image("img/smiley.png");
    private Image case_non_devoile = new Image("img/case_non_devoile.png");
    private Image vide = new Image("img/EMPTY.png");

    private Color couleurGauche = Color.ORANGE;
    private Color couleurDroite = Color.PALEGREEN;
    
    /**
     * Max number of threads for the ExecutorService Used to process the model
     */
    private final int NB_THREAD_MAX = 5;

    /**
     * Executor : Provides @NB_THREAD_MAX threads Stores in queue the tasks if
     * more than @NB_THREAD_MAX threads are needed
     */
    private  final ExecutorService executor
            = Executors.newFixedThreadPool(NB_THREAD_MAX, (Runnable r)
                    -> {
                Thread thread = new Thread(r);
                thread.setDaemon(true);
                return thread;
            });    

    public Scene initScene(){
        game.addObserver(this);
        
        MenuBar menu = menuBar(primaryStage);
        principale.setTop(menu);
        
        GridPane grille = initGrillePane(primaryStage);
        principale.setCenter(grille);
        
        BorderPane affichage = bottomPane(primaryStage);
        principale.setBottom(affichage);
        
        grilleRaffraichisseur=new GrilleRaffraichisseur(game.getG(), controleursGrille, game);
        tempRafraichisseur = new TempRafraichisseur(game.getG(), game, ttemp);
        boutonRaffraichisseur = new BottomRaffraichisseur(game.getG(), game, this.btmSmiley, affichage, score);
        Scene scene = new Scene(this.principale, Color.WHITE);
        return scene;
    }
    
    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.principale = new BorderPane();
        Scene scene;
        scene = initScene();
        this.primaryStage.setTitle("Démineur");
        this.primaryStage.setScene(scene);
        this.primaryStage.show();
        //long timeS = System.currentTimeMillis();
    }
    
    public MenuBar menuBar(Stage primaryStage){
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("Difficulté");
        ControleurDifficulte facilControleur = new ControleurDifficulte(primaryStage, this, game, 0, "Débutant");
        ControleurDifficulte moyenControleur = new ControleurDifficulte(primaryStage, this, game, 1, "Intermédiaire");
        ControleurDifficulte difficileControleur = new ControleurDifficulte(primaryStage, this, game, 2, "Expert");
        Menu difficulteAvance = new Menu("Avance");
        fileMenu.getItems().setAll(facilControleur.getMenuItem(), moyenControleur.getMenuItem(), difficileControleur.getMenuItem(), difficulteAvance);
        ControleurDifficulte personalierControleur = new ControleurDifficulte(primaryStage, this,game, 3, "Personaliser  : longueur*hauteur*mine");
        ControleurDifficultePersonalise  spinnerLongueur;
        spinnerLongueur = new ControleurDifficultePersonalise(primaryStage, this,game, 0, 45, game.getlNext(),0);
        ControleurDifficultePersonalise  spinnerHauteur = new ControleurDifficultePersonalise(primaryStage, this,game,0, 20, game.gethNext(), 1);
        ControleurDifficultePersonalise  spinnerMine = new ControleurDifficultePersonalise(primaryStage, this,game,0, 800, game.getNbMineNext(), 2);
        CustomMenuItem cmiLongueur = new CustomMenuItem(spinnerLongueur.getSpinner());
        CustomMenuItem cmiHauteur = new CustomMenuItem(spinnerHauteur.getSpinner());
        CustomMenuItem cmiMine = new CustomMenuItem(spinnerMine.getSpinner());
        cmiLongueur.setHideOnClick(false);
        cmiHauteur.setHideOnClick(false);
        cmiMine.setHideOnClick(false);
        difficulteAvance.getItems().setAll(personalierControleur.getMenuItem(),cmiLongueur, cmiHauteur, cmiMine);
        
        Menu editMenu = new Menu("Affichage");
        Menu couleurBotton = new Menu("Couleur");
        ColorPicker couleurPickerGauche = new ColorPicker();
        couleurPickerGauche.setOnAction((ActionEvent t) -> {
            Color valeur = couleurPickerGauche.getValue();
            couleurGauche = valeur;
        });
        CustomMenuItem cmiCouleurGauche = new CustomMenuItem(couleurPickerGauche);
        cmiCouleurGauche.setHideOnClick(false);
        ColorPicker couleurPickerDroite = new ColorPicker();
        couleurPickerDroite.setOnAction((ActionEvent t) -> {
            Color valeur = couleurPickerDroite.getValue();
            couleurDroite = valeur;
        });
        CustomMenuItem cmiCouleurDroite = new CustomMenuItem(couleurPickerDroite);
        cmiCouleurDroite.setHideOnClick(false);
        couleurBotton.getItems().setAll(cmiCouleurGauche, cmiCouleurDroite);
        editMenu.getItems().setAll(couleurBotton);
        
        Menu helpMenu = new Menu("Aide");
        
        
        menuBar.getMenus().setAll(fileMenu, editMenu, helpMenu);
        return menuBar;
    }
    
    public BorderPane bottomPane(Stage primaryStage){
        BorderPane bottomPane = new BorderPane();
        ControlleurBtmSmilley c = new ControlleurBtmSmilley(primaryStage, this, game);
        c.setWidth(40);
        c.setHeight(40);
        bottomPane.setCenter(c);
        c.setFill(new ImagePattern(smiley));
        this.btmSmiley = c;

        score = new Text("" + game.getG().getScore());
        score.setFont(Font.font("Verdana", 20));
        score.setTextAlignment(TextAlignment.CENTER);
        bottomPane.setLeft(score);

        this.ttemp = new Text("0");
        this.ttemp.setFont(Font.font("Verdana", 20));
        this.ttemp.setTextAlignment(TextAlignment.RIGHT);
        bottomPane.setRight(this.ttemp);
        
        return bottomPane;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        VueGrilleJeux.game = new Jeu(9, 9, 10);
        launch(args);
    }

    @Override
    public void update(Observable o, Object arg) {
        if(arg!= null){
            if(arg == "temp"){
                Platform.runLater(tempRafraichisseur);
            }
        }else{
            Platform.runLater(grilleRaffraichisseur);
            Platform.runLater(boutonRaffraichisseur);
        }
    }

    private GridPane initGrillePane(Stage primaryStage) {
        // permet de placer les diffrents boutons dans une grille
        GridPane grillePane = new GridPane();
        GrilleCaseCarre g = game.getG();
        this.controleursGrille = new ControleurCaseGrille[g.getLongueur()][g.getHauteur()];
        // création des bouton et placement dans la grille
        for (int y = 0; y < g.getHauteur(); y++) {
            for (int x = 0; x < g.getLongueur(); x++) {
                ControleurCaseGrille c = new ControleurCaseGrille(x, y, primaryStage, game, executor);
                c.setX(36 + (x * 30));
                c.setY(150 + (y * 30));
                c.setWidth(28);
                c.setHeight(28);
                c.setFill(new ImagePattern(case_non_devoile));
                grillePane.add(c, x, y);
                controleursGrille[x][y] = c;
            }
        }
        grillePane.setGridLinesVisible(true);
        return grillePane;
    }
}


