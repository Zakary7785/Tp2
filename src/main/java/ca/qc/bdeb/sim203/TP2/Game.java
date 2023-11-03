package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;

import java.util.Random;

public class Game {
    private Charlotte charlotte;
    private Baril baril;
    private ObjetDuJeu[]objets;
    private int level=0;

    private int vieBarre;
    boolean fini;
    private Color currentCouleur;
    public Game() {


    }

    public void lancerNiveau() {
        Random r= new Random();
        this.currentCouleur= Color.hsb(r.nextDouble(190,271),0.84,1);
        this.charlotte = new Charlotte();
        this.baril= new Baril();
        baril.setX(r.nextDouble(Main.WIDTH/5,(Main.WIDTH*4)/5));
        this.objets = new ObjetDuJeu[r.nextInt(1,6)];//add space for the chosen projectile
        for (int i = 0; i < objets.length; i++) {
            objets[i]=new Ennemi(level);
        }

        this.level +=1;
        this.vieBarre = 4;
        this.fini=false;
    }

    public void update(double dt){
        charlotte.update(dt);
        baril.update(dt);
        for (ObjetDuJeu o: objets) {
            o.update(dt);
        }
    }
    public void draw(GraphicsContext context){

        charlotte.draw(context);
        baril.draw(context);
        for (ObjetDuJeu o: objets) {
            o.draw(context);
        }
    }

    public Color getCurrentCouleur() {
        return currentCouleur;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public void setCharlotte(Charlotte charlotte) {
        this.charlotte = charlotte;
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
