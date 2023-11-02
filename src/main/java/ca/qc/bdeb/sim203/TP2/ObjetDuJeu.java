package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public abstract class ObjetDuJeu {
    protected double x,y,vy,vx,vMax,ax,ay;
    protected int w,h;

    protected Image image;



    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);

    }

    protected void updatePhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }
   public abstract void draw(GraphicsContext context);
    public double getHaut() {
        return y;
    }
    public double getBas() {
        return y + h;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + w;
    }

}
