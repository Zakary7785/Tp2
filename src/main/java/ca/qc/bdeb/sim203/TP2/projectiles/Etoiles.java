package ca.qc.bdeb.sim203.TP2.projectiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Etoiles extends Projectile {
    public Etoiles(double x, double y) {
        super(x, y);
        this.image= new Image("etoile.png");
        this.vx= 800;
        this.vy=0;
        this.ay=0;
        this.ax=0;
        this.w=36;
        this.h=35;
    }
    @Override
    public   void update(double dt){

    }

    @Override
    public   void draw(GraphicsContext context){

    }
}
