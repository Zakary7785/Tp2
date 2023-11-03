package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Sardine extends Projectile{
    public Sardine(double x, double y) {
        super(x, y);
        this.image= new Image("sardine.png");
        this.vx= 300;
        this.vy=0;
        this.ay=0;
        this.ax=0;
        this.w=35;
        this.h=29;
        this.setSardine(true);
    }
    @Override
    public   void update(double dt){
        if (isVisible()){
            if(vx<300)
                vx=300;
            if (vx>500)
                vx=500;
            if (vy<-500)
                vy=-500;
            if (vy>500)
                vy=500;
            if (getHaut()<0){
                y=0;
                ay=-ay;
            } else if (getBas()> Main.HEIGHT) {
                y=Main.HEIGHT-h;
                ay=-ay;
            }
            if(getGauche()>Main.WIDTH) setVisible(false);
            updatePhysique(dt);
        }

    }

    @Override
    public   void draw(GraphicsContext context){
        if (isVisible())
            context.drawImage(image,x,y);

    }
}
