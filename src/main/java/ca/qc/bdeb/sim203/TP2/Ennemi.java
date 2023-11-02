package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Ennemi extends ObjetDuJeu {
    public Ennemi(int level) {
        Random r = new Random();
        this.image=new Image("poisson"+r.nextInt(1,6)+".png");
        this.h=r.nextInt(50,121);
        this.w=(h*120)/104;
        this.y=r.nextDouble(Main.HEIGHT/5,Main.HEIGHT*4/5);
        this.x = Main.WIDTH -w;
        this.vx= (100 *  Math.pow(level,0.33)) + 200;
        this.vy= r.nextDouble(0,100) * r.nextInt(-1,2);
        this.ax=-500;
        this.ay=0;
    }

    @Override
    public void draw(GraphicsContext context) {
        context.drawImage(image,x,y);

    }
    @Override
    public void update(double dt){
        x=x;
        y=y;

    }
}
