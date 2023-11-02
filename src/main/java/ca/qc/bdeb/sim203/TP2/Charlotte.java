package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Charlotte extends ObjetDuJeu{
   private final Image imageBlesse;
   private final Image imageMouvement;
    protected boolean bouge,isInvinsible;
    protected int vie;
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
    @Override
    public void update(double dt){
        var left= Input.isKeyPressed(KeyCode.LEFT);
        var right= Input.isKeyPressed(KeyCode.RIGHT);
        var up= Input.isKeyPressed(KeyCode.UP);
        var down= Input.isKeyPressed(KeyCode.DOWN);
        mouvement(dt, left, right,true,vx);
        mouvement(dt,up,down,false,vy);
        updatePhysique(dt);
    }

    private void mouvement(double dt, boolean direction1, boolean direction2,boolean moveX, double v) {
        //moveX verifie si on bouge en x ou pas et cela determine l'affectation de v et a a vx, ax ou ay,vy
        double a;
        if (direction1) {
            a = -1000;
        } else if (direction2) {
            a = 1000;
        } else {
            a = 0;

            int signeVitesse = v > 0 ? 1 : -1;
            double vitesseAmortissementX = -signeVitesse * 500;
            v += dt * vitesseAmortissementX;
            int nouveauSigneVitesse = v > 0 ? 1 : -1;

            if(nouveauSigneVitesse != signeVitesse) {
                v = 0;
            }
        }
        if(v > 300)
            v = 300;
        else if(v < -300)
            v = -300;
        if (moveX){
            this.vx=v;
            this.ax=a;
        }
        else{
            this.vy=v;
            this.ay=a;
        }

    }
}


