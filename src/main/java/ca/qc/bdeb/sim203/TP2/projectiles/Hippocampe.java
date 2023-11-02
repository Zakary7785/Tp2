package ca.qc.bdeb.sim203.TP2.projectiles;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

import java.util.Random;

public class Hippocampe extends Projectile {
    private final double YINITIAL1,YINITIAL2,YINITIAL3;

    private final double TEMPSCREATION;
    private double x2,x3,y2,y3;
    public Hippocampe(double x, double y) {
        super(x, y);
        x2=x;
        x3=x;
        y2=y;
        y3=y;
        this.YINITIAL1 =y;
        this.YINITIAL2 =y;
        this.YINITIAL3 =y;
        this.TEMPSCREATION = System.nanoTime() * 1e-9;
        this.image = new Image("hippocampe.png");
        this.vx = 500;
        this.vy = 0;
        this.ay = 0;
        this.ax = 0;
        this.w = 36;
        this.h = 20;
    }

    @Override
    public void update(double dt) {
        var r = new Random();
        x = dt * vx;
        int sensY = r.nextInt(0, 2);
        if (sensY == 0)
            sensY = -1;
        y = (r.nextDouble(30, 60) * sensY) * Math.sin(2 * Math.PI * (System.nanoTime() * 1e-9 - TEMPSCREATION) / r.nextInt(1, 4)) + YINITIAL1;

    }

    @Override
    public void draw(GraphicsContext context) {


    }
}
