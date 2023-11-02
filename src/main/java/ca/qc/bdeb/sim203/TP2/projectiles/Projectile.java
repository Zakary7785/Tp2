package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.ObjetDuJeu;
import javafx.scene.canvas.GraphicsContext;

public abstract class Projectile extends ObjetDuJeu {

    public Projectile(double x, double y) {
        this.x=x;
        this.y=y;

    }

    public  abstract void update(double dt);



}
