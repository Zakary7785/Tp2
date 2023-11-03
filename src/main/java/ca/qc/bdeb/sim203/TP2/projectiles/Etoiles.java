package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Main;
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
        if(isVisible())
            x=dt*vx;
        if(getDroite()> Main.WIDTH)
            setVisible(false);
    }

    @Override
    public   void draw(GraphicsContext context){
        if(isVisible())
         context.drawImage(image,x,y);

    }
}
