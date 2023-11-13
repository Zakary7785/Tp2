package ca.qc.bdeb.sim203.TP2;

import com.sun.javafx.scene.paint.GradientUtils;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.LinearGradient;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.Random;

public class Game {
    private final Charlotte charlotte;
    private boolean showGameover;// quand on perd tous les vies
    private boolean finiGame;//quand on fini tous les niveaux du jeu
    private boolean showLevelNumber;
    private boolean CharlotteCooldown;
    private Baril baril;
    private double NewWaveTime;
    private ArrayList<Ennemi> poissonEnnemis;
    private int level=0;

    private int vieBarre;
    boolean fini;
    private Color currentCouleur;
    public Game() {
        this.fini=true;
        this.charlotte = new Charlotte();
        this.showGameover=false;
        this.finiGame=false;

    }

    public boolean isCharlotteCooldown() {
        return CharlotteCooldown;
    }

    public void setCharlotteCooldown(boolean charlotteCooldown) {
        CharlotteCooldown = charlotteCooldown;
    }

    public boolean isFiniGame() {
        return finiGame;
    }

    public void setFiniGame(boolean finiGame) {
        this.finiGame = finiGame;
    }

    public boolean isShowGameover() {
        return showGameover;
    }

    public void setShowGameover(boolean showGameover) {
        this.showGameover = showGameover;
    }

    public void setShowLevelNumber(boolean showLevelNumber) {
        this.showLevelNumber = showLevelNumber;
    }

    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    public double getNewWaveTime() {
        return NewWaveTime;
    }

    public void lancerNiveau() {
        Random r= new Random();
        level +=1;
        this.NewWaveTime=0.75+( 1/Math.sqrt(level));
        currentCouleur= Color.hsb(r.nextDouble(190,271),0.84,1);

        baril= new Baril();
        baril.setX(r.nextDouble(Main.WIDTH/5,(Main.WIDTH*4)/5));
        poissonEnnemis= new ArrayList<>();

        for (int i = 0; i < r.nextInt(0,6); i++) {
            poissonEnnemis.add( new Ennemi(level));
        }
        vieBarre = 4;
        fini=false;
    }

    public void update(double dt){
        if (charlotte.getVie()==0)
            showGameover=true;
        charlotte.update(dt);
        baril.update(dt);
        for (int i=poissonEnnemis.size()-1;i>0;i--) {
            if (poissonEnnemis.get(i).isDead()||poissonEnnemis.get(i).isOutScreen())
                poissonEnnemis.remove(i);
        }
        for (Ennemi e: poissonEnnemis) {
            if (e.getGauche()>charlotte.getArme().getDroite()&&charlotte.getArme().isSardine()){
                calculForceElec(e);
            }
            e.update(dt);
        }

        charlotte.getArme().update(dt);
        //todo Methods check crash with projectile and check crash with charlotte probably crash charlotte barrel and if level is over by getting charlotte's fini niveau toggle

    }
    public void newVaguePoisson(){
        Random r= new Random();
        for (int i=0;i<r.nextInt(0,6);i++) {
            poissonEnnemis.add(new Ennemi(level));
        }
    }

    private void calculForceElec(ObjetDuJeu o) {
        double distanceExpo2 =distanceExpo2(charlotte.getArme().getDroite(),charlotte.getArme().getHaut(), o.getGauche(), o.getHaut());
        double distance=Math.sqrt(distanceExpo2);
        double deltaX = charlotte.getArme().x - (o.x+ o.w/2);
        double deltaY = charlotte.getArme().y - (o.y+ o.h/2);
        if(distance<0.01)
            distance=0.01;
        double proportionX = deltaX / distance;
        double proportionY = deltaY / distance;
        double forceElectrique = 1000*100*-200/distanceExpo2;
        charlotte.getArme().setAx(charlotte.getAx()+(forceElectrique * proportionX));
        charlotte.getArme().setAy(charlotte.getAy()+forceElectrique * proportionY);
    }

    public double distanceExpo2(double sardineX,double sardineY,double poissonX,double poissonY) {
        return  (Math.pow(sardineX-poissonX, 2) + Math.pow(sardineY - poissonY, 2));
        }

    public void draw(GraphicsContext context){
        context.setFill(Color.DARKRED);
        if (showGameover){
            context.setFont(Font.font("arial", FontWeight.BOLD,40));
            context.fillText("FIN DE PARTIE",Main.WIDTH/4,Main.HEIGHT/2,Main.WIDTH);
        }

        context.setFill(Color.WHITE);
        if (showLevelNumber){
            context.setFont(Font.font("arial", FontWeight.BOLD,40));
            context.fillText("Niveau "+level,Main.WIDTH/4,Main.HEIGHT/2,Main.WIDTH);
        }

        if (finiGame){
            context.setFont(Font.font("arial", FontWeight.BOLD,50));
            context.fillText("Vous avez Gagné",Main.WIDTH/4,Main.HEIGHT/2,Main.WIDTH);
        }

        context.strokeRect(10,10,150,30);
        context.drawImage(charlotte.getArme().getImage(),170,10);
        context.fillRect(10,10,getVieBarre()*37.5,30);//le 37.5 egale au nombre de pixel par vie
        charlotte.draw(context);
        charlotte.getArme().draw(context);
        baril.draw(context);
        for (Ennemi e:poissonEnnemis) {
            e.draw(context);
        }
    }

    public Color getCurrentCouleur() {
        return currentCouleur;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }




    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVieBarre() {
        return vieBarre;
    }

    public void setVieBarre(int vieBarre) {
        this.vieBarre = vieBarre;
    }
}
