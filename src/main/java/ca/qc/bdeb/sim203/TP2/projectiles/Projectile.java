package ca.qc.bdeb.sim203.TP2.projectiles;
import ca.qc.bdeb.sim203.TP2.ObjetDuJeu;

public abstract class Projectile extends ObjetDuJeu {
    private boolean visible;
    private boolean isSardine;

    public Projectile(double x, double y) {
        this.x=x;
        this.y=y;
        this.visible=false;
        this.isSardine=false;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    public boolean isSardine() {
        return isSardine;
    }

    public void setSardine(boolean sardine) {
        isSardine = sardine;
    }

    public  abstract void update(double dt);


}
