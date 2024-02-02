package ca.qc.bdeb.sim203.TP2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;

import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.Stage;

import java.util.Random;

//Auteurs: Numa Trachsel-Bourbeau DA: 2279500 et Zakary Szekely DA: .......



/**
 * La classe principale qui étend Application pour créer l'interface graphique du jeu "Charlotte la Barbotte".
 */
public class Main extends Application {

    /**
     * Méthode principale qui lance l'application.
     *
     * @param args Les arguments de la ligne de commande.
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Largeur de la fenêtre du jeu.
     */
    public static final double width = 900;

    /**
     * Hauteur de la fenêtre du jeu.
     */
    public static final double height = 520;

    private Stage stage;
    private boolean modeDebug = false;
    private boolean projectileEtoiles = false;
    private boolean projectileHippocampe = false;
    private boolean projectileSardine = false;
    private int changementArmeDemander = 0;
    private int vieAMax = 0;
    private int passerNiveau = 0;

    /**
     * Méthode appelée lors du lancement de l'application, initialisant la fenêtre principale.
     *
     * @param stage La fenêtre principale de l'application.
     */
    @Override
    public void start(Stage stage) {
        this.stage = stage;

        // Instructions pour le fonctionnement avec GitHub

        stage.setScene(setSceneMenu());

        var logo = new Image("logo.png");

        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.show();
    }

    /**
     * Crée et retourne la scène du menu principal.
     *
     * @return La scène du menu principal.
     */
    public Scene setSceneMenu() {
        var root = setPaneAvecBackground();
        var scene = new Scene(root, width, height);
        var logo = new Image("logo.png");
        var logoView = new ImageView(logo);
        var jouer = new Button("Jouer!");
        var info = new Button("Infos");
        var zoneButton = new HBox();
        zoneButton.getChildren().addAll(jouer, info);
        zoneButton.setAlignment(Pos.BOTTOM_CENTER);
        root.setCenter(logoView);
        logoView.setFitWidth(460);
        logoView.setPreserveRatio(true);
        root.setBottom(zoneButton);
        zoneButton.setPadding(new Insets(10));

        // Gestion de la touche Escape pour quitter l'application
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();
            }
        });

        // Gestion du bouton "Jouer"
        jouer.setOnAction(event -> stage.setScene(setEcranDeJeu()));

        // Gestion du bouton "Infos"
        info.setOnAction(event -> stage.setScene(setSceneInfos(stage)));

        return scene;
    }
    /**
     * Méthode qui crée et retourne un {@link BorderPane} avec un fond coloré.
     *
     * @return Le {@link BorderPane} avec un fond coloré.
     */
    private static BorderPane setPaneAvecBackground() {
        var root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#2A7FFF"), null, null)));
        return root;
    }

    /**
     * Méthode qui crée et retourne la scène du jeu.
     *
     * @return La scène du jeu.
     */
    public Scene setEcranDeJeu() {
        var root = new Pane();
        var scene = new Scene(root, width, height);
        var canvas = new Canvas(width, height);
        var context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        var game = new Game();

        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();
            private double tempAffichageNiveau = 0.0;
            private double tempAffichageGameOver = 0;
            private double tempsDenvoieVaguePoisson = 0.0;

            /**
             * Méthode appelée à chaque trame d'animation pour mettre à jour et afficher le jeu.
             *
             * @param now Le temps actuel en nanosecondes.
             */
            @Override
            public void handle(long now) {
                // Logique de mise à jour du jeu

                if (game.isFini()) {
                    game.lancerNiveau();
                    tempAffichageNiveau = 0;
                }

                if (game.isShowGameover()) {
                    tempAffichageGameOver = 0;
                }

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }

                double dt = (now - lastTime) * 1e-9;

                if (tempsDenvoieVaguePoisson >= game.getNewWaveTime()) {
                    tempsDenvoieVaguePoisson = 0;
                    game.newVaguePoisson();
                } else tempsDenvoieVaguePoisson += dt;

                tempAffichageNiveau += dt;
                game.setShowLevelNumber(tempAffichageNiveau <= 4);

                if (game.getLevel() == 6) {
                    game.setFiniGame(true);
                }

                if (tempAffichageGameOver <= 3 && game.isShowGameover())
                    game.setShowGameover(true);
                else {
                    Input.setKeyPressed(KeyCode.ESCAPE, true);
                    Input.setKeyPressed(KeyCode.ESCAPE, false);
                }

                game.update(dt, modeDebug, projectileEtoiles, projectileHippocampe, projectileSardine, vieAMax, passerNiveau, changementArmeDemander);
                context.clearRect(0, 0, width, height);
                context.setFill(game.getCurrentCouleur());
                context.fillRect(0, 0, width, height);

                game.draw(context, modeDebug);

                if (game.getCharlotte().finiNiveau) {
                    game.setFini(true);
                    game.getCharlotte().setFiniNiveau(false);
                }

                lastTime = now;

                if (!game.isRetourMenu() && game.getCharlotte().getTempsDeMort() > 0 && ((System.nanoTime() - game.getCharlotte().getTempsDeMort()) * 1e-9 > 3)) {
                    game.setRetourMenu(true);
                    stage.setScene(setSceneMenu());
                    System.out.println("Fini");
                    stop();
                }
            }
        };
        timer.start();

        /*
         * Gestion des événements clavier pour le menu principal.
         *
         * @param scene La scène associée aux événements clavier.
         * @return La scène du menu principal.
         */
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(setSceneMenu());
                timer.stop();
            } else if (event.getCode() == KeyCode.D) {
                // mode debug est non accessible, pour activer la fonctionnaliter, enlever les // devant la prochaine ligne de code.
                modeDebug = !modeDebug;
            } else if (event.getCode() == KeyCode.SPACE) {
                game.getCharlotte().attaque();
                game.setCharlotteCooldown();
            } else {
                Input.setKeyPressed(event.getCode(), true);
            }

            if (modeDebug) {
                switch (event.getCode()) {
                    case Q -> {
                        projectileEtoiles = true;
                        projectileSardine = false;
                        projectileHippocampe = false;
                        changementArmeDemander++;
                    }
                    case W -> {
                        projectileEtoiles = false;
                        projectileSardine = false;
                        projectileHippocampe = true;
                        changementArmeDemander++;
                    }
                    case E -> {
                        projectileEtoiles = false;
                        projectileSardine = true;
                        projectileHippocampe = false;
                        changementArmeDemander++;
                    }
                    case R -> vieAMax++;
                    case T -> passerNiveau++;
                }
            }
        });

/*
 * Gestion de la libération des touches du clavier.
 *
 * @param event L'événement de libération de touche.
 */
        scene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

/*
 * Retourne la scène du menu principal.
 */
        return scene;
    }


    /**
     * Crée et retourne la scène des informations du jeu.
     *
     * @param stage La fenêtre principale de l'application.
     * @return La scène des informations du jeu.
     */
    public Scene setSceneInfos(Stage stage) {
        var root = setPaneAvecBackground();
        var mainbox = new VBox();
        mainbox.setSpacing(20);
        mainbox.setPadding(new Insets(20));
        var lign1 = new HBox();
        var lign2 = new HBox();
        var lign3 = new HBox();
        var lign4 = new HBox();
        var lign5 = new HBox();
        var lign6 = new HBox();
        formatTaille(lign1, " Charlotte la Barbotte", 50.0);
        var r = new Random();
        var rnd = r.nextInt(1, 6);
        var imagePoisson = new Image("poisson" + rnd + ".png");
        var imageVPoisson = new ImageView(imagePoisson);
        imageVPoisson.setPreserveRatio(true);
        imageVPoisson.setFitWidth(width / 5);
        lign2.getChildren().add(imageVPoisson);
        lign2.setAlignment(Pos.CENTER);
        formatTaille(lign3, "Par", 15);
        formatTaille(lign3, " Zakary Szekely", 32);
        formatTaille(lign4, "et", 15);
        formatTaille(lign4, "Numa Trachel-Bourbeau", 32);
        var flowDescrip = new TextFlow();
        var descript = new Text(" Travail remis à Nicolas Hubertise. Graphismes adaptées de https://game-icons.net/ et de hhtps://openclipart.org/. Développé dans le cadre du cours 420-203-RE - Développement" +
                " de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");
        flowDescrip.getChildren().add(descript);
        lign5.getChildren().add(flowDescrip);
        lign5.setAlignment(Pos.CENTER_RIGHT);
        var retour = new Button("Retour");
        lign6.setAlignment(Pos.CENTER);
        lign6.getChildren().add(retour);
        mainbox.getChildren().addAll(lign1, lign2, lign3, lign4, lign5, lign6);
        root.setCenter(mainbox);
        retour.setOnAction(event -> stage.setScene(setSceneMenu()));

        return new Scene(root, width, height);
    }

    /**
     * Formatte le texte avec une taille de police spécifiée et l'ajoute à la boîte horizontale.
     *
     * @param box    La boîte horizontale à laquelle ajouter le texte.
     * @param s      Le texte à ajouter.
     * @param taille La taille de police du texte.
     */
    private void formatTaille(HBox box, String s, double taille) {
        var t = new Text(s);
        t.setFont(Font.font("Arial", FontWeight.BOLD, taille));
        box.getChildren().add(t);
        box.setAlignment(Pos.CENTER);
    }
}
