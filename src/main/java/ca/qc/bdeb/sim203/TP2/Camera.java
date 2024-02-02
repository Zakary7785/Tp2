package ca.qc.bdeb.sim203.TP2;

public class Camera {

    private double x =0;


    public void update(Charlotte charlotte) {
        // Mettre à jour la position de la caméra en fonction de la position de Charlotte
        this.x = charlotte.getGauche() - Main.width / 5;
    }

    public double getX() {
        return x;
    }

}
