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
        this.fini=true;


    }

    public boolean isFini() {
        return fini;
    }

    public void setFini(boolean fini) {
        this.fini = fini;
    }

    public void lancerNiveau() {
        Random r= new Random();
        level +=1;
        currentCouleur= Color.hsb(r.nextDouble(190,271),0.84,1);
        charlotte = new Charlotte();
        baril= new Baril();
        baril.setX(r.nextDouble(Main.WIDTH/5,(Main.WIDTH*4)/5));
        objets = new ObjetDuJeu[r.nextInt(1,6)];//add space for the chosen projectile

        for (int i = 0; i < objets.length; i++) {
            objets[i]=new Ennemi(level);
        }
        vieBarre = 4;
        fini=false;
    }

    public void update(double dt){
        charlotte.update(dt);
        baril.update(dt);
        charlotte.getArme().update(dt);
        for (ObjetDuJeu o: objets) {
            o.update(dt);
        }
    }
    public void draw(GraphicsContext context){
        context.setFill(Color.WHITE);
        context.strokeRect(10,10,150,30);
        context.drawImage(charlotte.getArme().getImage(),170,10);
        context.fillRect(10,10,getVieBarre()*37.5,30);//le 37.5 egale au nombre de pixel par vie
        charlotte.draw(context);
        charlotte.getArme().draw(context);
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
