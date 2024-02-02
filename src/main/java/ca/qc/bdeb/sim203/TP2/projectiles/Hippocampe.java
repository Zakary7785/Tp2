package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Ennemi;
import ca.qc.bdeb.sim203.TP2.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Classe représentant le projectile Hippocampe dans le jeu.
 */
public class Hippocampe extends Projectile {
    private final double yInitial;
    private final double tempsCreation;
    private double y2, y3;

    /**
     * Constructeur de la classe Hippocampe.
     *
     * @param x La position en x de l'Hippocampe.
     * @param y La position en y de l'Hippocampe.
     */
    public Hippocampe(double x, double y) {
        super(x, y);
        y2 = y + 60;
        y3 = y - 60;
        this.yInitial = y;
        this.tempsCreation = System.nanoTime() * 1e-9;
        this.image = new Image("hippocampe.png");
        this.vx = 500;
        this.vy = 0;
        this.ay = 0;
        this.ax = 0;
        this.w = 36;
        this.h = 20;
    }

    /**
     * Met à jour l'état de l'Hippocampe en fonction du temps et de la position de la caméra.
     *
     * @param dt        Le temps écoulé depuis la dernière mise à jour.
     * @param posCamera La position de la caméra.
     */
    @Override
    public void update(double dt, int posCamera) {
        if (isVisible()) {
            x += dt * vx;
            y = calculY();
            y2 = y + 60;
            y3 = y - 60;
        }

        if (getDroite() > (Main.width + posCamera) && isVisible())
            setVisible(false);
    }

    /**
     * Calcule la position en y de l'Hippocampe en fonction du temps.
     *
     * @return La position en y calculée de l'Hippocampe.
     */
    private double calculY() {
        var r = new Random();
        int sensY = r.nextInt(0, 2);
        if (sensY == 0)
            sensY = -1;

        double dephasage = r.nextDouble(0, 2 * Math.PI); // Ajout de déphasage
        double amplitude = r.nextDouble(30, 60);

        return amplitude * sensY * Math.sin(2 * Math.PI * (System.nanoTime() * 1e-9 - tempsCreation) / r.nextInt(1, 4) + dephasage) + yInitial;
    }

    /**
     * Dessine l'Hippocampe sur le contexte graphique.
     *
     * @param context   Le contexte graphique.
     * @param posCamera La position de la caméra.
     * @param modeDebug True si le mode de débogage est activé, sinon false.
     */
    @Override
    public void draw(GraphicsContext context, double posCamera, boolean modeDebug) {
        if (modeDebug) {
            context.setStroke(Color.YELLOW);
            context.strokeRect(x - posCamera, y, w / 2, h * 2);
            context.strokeRect(x - posCamera, y2, w / 2, h * 2);
            context.strokeRect(x - posCamera, y3, w / 2, h * 2);
        }
        if (isVisible()) {
            context.drawImage(image, x - posCamera, y);
            context.drawImage(image, x - posCamera, y2);
            context.drawImage(image, x - posCamera, y3);
        }
    }

    /**
     * Vérifie si l'Hippocampe a touché un poisson (ennemi) à une position spécifiée en y.
     *
     * @param ennemi Le poisson (ennemi) à vérifier.
     * @return True si l'Hippocampe a touché le poisson, sinon false.
     */
    @Override
    public boolean toucherPoisson(Ennemi ennemi) {
        return (checkCollision(ennemi, y) || checkCollision(ennemi, y2) || checkCollision(ennemi, y3));
    }

    /**
     * Vérifie la collision entre l'Hippocampe et un poisson (ennemi) à une position spécifiée en y.
     *
     * @param ennemi Le poisson (ennemi) à vérifier.
     * @param posY   La position en y à vérifier.
     * @return True si l'Hippocampe a touché le poisson à la position en y, sinon false.
     */
    private boolean checkCollision(Ennemi ennemi, double posY) {
        return getGauche() < ennemi.getDroite() &&
                getDroite() > ennemi.getGauche() &&
                posY < ennemi.getBas() &&
                posY > ennemi.getHaut();
    }
}
