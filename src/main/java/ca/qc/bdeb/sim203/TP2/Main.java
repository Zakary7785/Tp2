package ca.qc.bdeb.sim203.TP2;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;

import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
     final double WIDTH=900;
     final double HEIGHT=520;
     BorderPane root=new BorderPane();

    @Override
    public void start(Stage stage)  {
        var logo= new Image("logo.png");
        root=new BorderPane();
        root=setSceneMenu();
        var scene= new Scene(root,WIDTH,HEIGHT);





        stage.setTitle("Charlotte la Barbotte");
        stage.getIcons().add(logo);
        stage.setScene(scene);
        stage.show();
    }
    public  BorderPane setSceneMenu(){

        root.setBackground(new Background(new BackgroundFill(Paint.valueOf("#2A7FFF"),null,null)));
        var logo= new Image("logo.png");
        var logoView= new ImageView(logo);
        var jouer= new Button("Jouer!");
        var info= new Button("Infos");
        var zoneButton= new HBox();
        zoneButton.getChildren().addAll(jouer,info);
        zoneButton.setAlignment(Pos.BOTTOM_CENTER);
        root.setCenter(logoView);
        logoView.setFitWidth(460);
        logoView.setPreserveRatio(true);
        root.setBottom(zoneButton);
        zoneButton.setPadding(new Insets(10));
        jouer.setOnAction(event -> {
                System.out.println("PASSER AU NIVEAU 1");
        });
        info.setOnAction(event -> {
            root.getChildren().clear();
            root=setSceneInfos();

        });
        return root;
    }
    public BorderPane setSceneInfos(){

        var mainbox= new VBox();
        mainbox.setSpacing(20);
        mainbox.setPadding(new Insets(20));
        var lign1=new HBox();
        var lign2= new HBox();
        var lign3= new HBox();
        var lign4= new HBox();
        var lign5= new HBox();
        var lign6= new HBox();
        var titreJeu= new Text(" Charlotte la Barbotte");
        formatTaille(lign1,titreJeu,50.0);
        var r= new Random();
        var rnd=r.nextInt(1,6);
        var imagePoisson= new Image("poisson"+rnd+".png");
        var imageVPoisson= new ImageView(imagePoisson);
        imageVPoisson.setPreserveRatio(true);
        imageVPoisson.setFitWidth(WIDTH/5);
        lign2.getChildren().add(imageVPoisson);
        lign2.setAlignment(Pos.CENTER);
        var par= new Text("Par");
        formatTaille(lign3,par,15);
        var nom1= new Text(" Zakary Szekely");
        formatTaille(lign3,nom1,32);
        var et= new Text("et");
        formatTaille(lign4,et,15);
        var nom2 = new Text(" Numa Trachel-Bourbeau");
        formatTaille(lign4,nom2,32);
        var flowDescrip= new TextFlow();
        var descript= new Text(" Travail remis à Nicolas Hubertise. Graphismes adaptées de https://game-icons.net/ et de hhtps://openclipart.org/. Développé dans le cadre du cours 420-203-RE - Développement" +
                " de programmes dans un environnement graphique, au Collège de Bois-de-Boulogne.");
        flowDescrip.getChildren().add(descript);
        lign5.getChildren().add(flowDescrip);
        lign5.setAlignment(Pos.CENTER_RIGHT);
        var retour= new Button("Retour");
        lign6.setAlignment(Pos.CENTER);
        lign6.getChildren().add(retour);
        mainbox.getChildren().addAll(lign1,lign2,lign3,lign4,lign5,lign6);
        root.setCenter(mainbox);
        retour.setOnAction(event -> {
            root.getChildren().clear();
            root=setSceneMenu();
        });

        return root;
    }
    private void formatTaille(HBox box,Text t, double taille){
        t.setFont(Font.font("Arial",FontWeight.BOLD,taille));
        box.getChildren().add(t);
        box.setAlignment(Pos.CENTER);


    }

}