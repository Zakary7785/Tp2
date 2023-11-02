package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Charlotte extends ObjetDuJeu{
   private Image imageBlesse;
   private Image imageMouvement;
    public Charlotte() {
        this.vie=4;
        this.bouge=false;
        this.isInvinsible=false;
        this.w=102;
        this.h=90;
        this.x=0;
        this.y=(Main.HEIGHT-h/2);
        this.vx=0;
        this.vy=0;
        this.vMax=300;
        this.ax=0;
        this.ay=0;
        this.image=new Image("charlotte.png");
        this.imageBlesse=new Image("charlotte-outch.png");
        this.imageMouvement= new Image("charlotte-avant.png");
    }
    @Override
    public void draw(GraphicsContext context){
        if(isInvinsible){
            context.drawImage(imageBlesse,x,y);
        } else if (bouge){
            context.drawImage(imageMouvement,x,y);
        }
        else{
            context.drawImage(image,x,y);
        }

    }
}
