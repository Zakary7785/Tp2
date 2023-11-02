package ca.qc.bdeb.sim203.TP2.projectiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sardine extends Projectile{
    public Sardine(double x, double y) {
        super(x, y);
        this.image= new Image("hippocampe.png");
        this.vx= 500;
        this.vy=0;
        this.ay=0;
        this.ax=0;
        this.w=36;
        this.h=20;
    }
    @Override
    public   void update(double dt){

    }

    @Override
    public   void draw(GraphicsContext context){

    }
}
