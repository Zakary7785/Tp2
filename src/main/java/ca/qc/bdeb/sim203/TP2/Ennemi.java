package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;

import java.util.Random;

public class Ennemi extends ObjetDuJeu {
    public Ennemi(int level) {
        Random r = new Random();
        this.h=r.nextInt(50,121);
        this.w=(h*120)/104;
        this.y=r.nextDouble(Main.HEIGHT/5,Main.HEIGHT*4/5);
        this.x = Main.WIDTH -w;
        this.vx= (100 *  Math.pow(level,0.33)) + 200;
        this.vy=0;// random from 0 to 100 * random -1 ou 1
        this.ax=0;//change this to -500 after
        this.ay=0;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image,x,y);
    }
}
