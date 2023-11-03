package ca.qc.bdeb.sim203.TP2;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
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

public class Main extends Application {
    public static void main(String[] args) {

        launch(args);
    }

    public static final double WIDTH = 900;
    public static final double HEIGHT = 520;
    private Stage stage;

    @Override
    public void start(Stage stage) {
        this.stage = stage;
        /*
        Le fonctionnement que GITHUB
        if you want to get the latest code
        1. click on fetch all remotes and then
        2. update project

        after you finish code that you wanna add:
        1: click commit click on what want to commit or add
        2: After you click push to send the code
         */
        stage.setScene(setSceneMenu());

        var logo = new Image("logo.png");
        // au lieu de tout mettre dans le animation timer , faire une classe niveau ou on update tout et qu'on dessine tout dedans comme ca on peut jsute call niveau.update et niveau.call


        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(logo);
        stage.setResizable(false);
        stage.show();
    }

    public Scene setSceneMenu() {
        var root = setPaneAvecBackground();
        var scene = new Scene(root, WIDTH, HEIGHT);
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
        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                Platform.exit();


            } else if (event.getCode() == KeyCode.D) {
                System.out.println("Mode débuggage");
            }
        });
        jouer.setOnAction(event -> {
                    stage.setScene(setEcranDeJeu());
                }
        );
        info.setOnAction(event -> stage.setScene(setSceneInfos(stage)));
        return scene;
    }

    private static BorderPane setPaneAvecBackground() {
        var root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#2A7FFF"), null, null)));
        return root;
    }

    public Scene setEcranDeJeu() {
        var root = new Pane();
        var scene = new Scene(root, WIDTH, HEIGHT);
        var canvas = new Canvas(WIDTH, HEIGHT);
        var context = canvas.getGraphicsContext2D();
        root.getChildren().add(canvas);
        var game = new Game();
        AnimationTimer timer = new AnimationTimer() {
            private long lastTime = System.nanoTime();

            @Override
            public void handle(long now) {
                if(game.isFini()){
                    game.lancerNiveau();
                    game.setFini(false);
                }

                if (lastTime == 0) {
                    lastTime = now;
                    return;
                }
                double dt = (now - lastTime) * 1e-9;

                game.update(dt);
                context.clearRect(0, 0, WIDTH, HEIGHT);
                context.setFill(game.getCurrentCouleur());
                context.fillRect(0, 0, WIDTH, HEIGHT);
                game.draw(context);
                lastTime = now;
                if(game.getCharlotte().getDroite()>=WIDTH)
                    game.setFini(true);
            }

        };
        timer.start();


        scene.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ESCAPE) {
                stage.setScene(setSceneMenu());
                timer.stop();
            } else if (event.getCode() == KeyCode.D) {
                System.out.println("mode débugage");
            } else {
                Input.setKeyPressed(event.getCode(), true);
            }
        });
        scene.setOnKeyReleased(event -> Input.setKeyPressed(event.getCode(), false));

        return scene;
    }


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
        imageVPoisson.setFitWidth(WIDTH / 5);
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

        return new Scene(root, WIDTH, HEIGHT);
    }

    private void formatTaille(HBox box, String s, double taille) {
        var t = new Text(s);
        t.setFont(Font.font("Arial", FontWeight.BOLD, taille));
        box.getChildren().add(t);
        box.setAlignment(Pos.CENTER);
    }

}