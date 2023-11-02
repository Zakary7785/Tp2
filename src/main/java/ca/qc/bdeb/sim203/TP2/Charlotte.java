package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;

public class Charlotte extends ObjetDuJeu {
    private final Image imageBlesse;
    private final Image imageMouvement;
    protected boolean bouge, isInvinsible;
    protected boolean finiNiveau;
    protected int vie;

    public Charlotte() {
        this.vie = 4;
        this.bouge = false;
        this.isInvinsible = false;
        this.w = 102;
        this.h = 90;
        this.x = 0;
        this.y = ((Main.HEIGHT / 2) - h);
        this.vx = 0;
        this.vy = 0;
        this.vMax = 300;
        this.ax = 0;
        this.ay = 0;
        this.image = new Image("charlotte.png");
        this.imageBlesse = new Image("charlotte-outch.png");
        this.imageMouvement = new Image("charlotte-avant.png");
    }

    @Override
    public void draw(GraphicsContext context) {
        if (isInvinsible) {
            context.drawImage(imageBlesse, x, y);
        } else if (bouge) {
            context.drawImage(imageMouvement, x, y);
        } else {
            context.drawImage(image, x, y);
        }

    }

    @Override
    public void update(double dt) {
        var left = Input.isKeyPressed(KeyCode.LEFT);
        var right = Input.isKeyPressed(KeyCode.RIGHT);
        var up = Input.isKeyPressed(KeyCode.UP);
        var down = Input.isKeyPressed(KeyCode.DOWN);
        bouge = ax != 0 || ay != 0;
        mouvement(dt, left, right, true, vx);
        mouvement(dt, up, down, false, vy);
        updatePhysique(dt);
        if(x<0)
            x=0;
        if(getDroite()>Main.WIDTH){
            //niveau completeeeeeeeeeeeeeeee
            x=0;
        }
        if (getBas()>Main.HEIGHT){
            y=Main.HEIGHT;
            ay=-ay;
        }
        if(getHaut()<Main.HEIGHT){
            y=0;
            ay=-ay;
        }

        System.out.println("x " + x + "\t" + "y " + y);
    }


    private void mouvement(double dt, boolean direction1, boolean direction2, boolean moveX, double v) {
        //moveX verifie si on bouge en x ou pas et cela determine l'affectation de v et a a vx, ax ou ay,vy
        // direction 1 est soit gauche ou up et direction 2 est soit droite ou bas
        double a;
        bouge = ax != 0 || ay != 0;
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
            if (nouveauSigneVitesse != signeVitesse) {
                v = 0;
            }
        }
        if (v > vMax)
            v = 300;
        else if (v < -1 * vMax)
            v = -300;

        if (moveX && (direction1 || direction2)) {//si on bouge en x et qu'on bouge
            this.ax = a;
        } else if (!moveX && (direction1 || direction2)) {// en y et on bouge
            this.ay = a;
        } else {
            if (moveX) {// en x si on bouge pas
                this.ax = 0;
                this.vx = v;
            } else {// en y si on bouge pas
                this.ay = 0;
                this.vy = v;
            }
        }


    }
}


