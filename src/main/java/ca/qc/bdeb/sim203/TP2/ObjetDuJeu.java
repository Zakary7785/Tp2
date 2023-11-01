package ca.qc.bdeb.sim203.TP2;

public class ObjetDuJeu {
    private double x,y,vy,vx,ax,ay,w,h;

    public void update(double deltaTemps) {
        updatePhysique(deltaTemps);

    }

    protected void updatePhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }
    public double getHaut() {
        return y;
    }
    public double getBas() {
        return y + h;
    }
    public double getGauche() {
        return x;
    }
    public double getDroite() {
        return x + w;
    }

}
