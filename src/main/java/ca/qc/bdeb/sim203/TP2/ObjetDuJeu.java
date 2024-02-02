package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

/**
 * Classe abstraite représentant un objet du jeu.
 */
public abstract class ObjetDuJeu {
    protected double x, y, vy, vx, ax, ay;
    protected double w, h;
    protected Image image;

    /**
     * Définit la position en x de l'objet.
     *
     * @param x La nouvelle position en x de l'objet.
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Obtient l'accélération en x de l'objet.
     *
     * @return L'accélération en x de l'objet.
     */
    public double getAx() {
        return ax;
    }

    /**
     * Définit l'accélération en x de l'objet.
     *
     * @param ax La nouvelle accélération en x de l'objet.
     */
    public void setAx(double ax) {
        this.ax = ax;
    }

    /**
     * Obtient l'accélération en y de l'objet.
     *
     * @return L'accélération en y de l'objet.
     */
    public double getAy() {
        return ay;
    }

    /**
     * Définit l'accélération en y de l'objet.
     *
     * @param ay La nouvelle accélération en y de l'objet.
     */
    public void setAy(double ay) {
        this.ay = ay;
    }

    /**
     * Met à jour l'objet du jeu en fonction du temps et de la position de la caméra.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour.
     * @param posCam     La position de la caméra.
     */
    public void update(double deltaTemps, double posCam) {
        updatePhysique(deltaTemps);
    }

    /**
     * Met à jour les aspects physiques de l'objet en fonction du temps.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour.
     */
    protected void updatePhysique(double deltaTemps) {
        vx += deltaTemps * ax;
        vy += deltaTemps * ay;
        x += deltaTemps * vx;
        y += deltaTemps * vy;
    }

    /**
     * Dessine l'objet sur le contexte graphique.
     *
     * @param context   Le contexte graphique.
     * @param posCamera La position de la caméra.
     * @param modeDebug True si le mode de débogage est activé, sinon false.
     */
    public abstract void draw(GraphicsContext context, double posCamera, boolean modeDebug);

    /**
     * Obtient la position en y du haut de l'objet.
     *
     * @return La position en y du haut de l'objet.
     */
    public double getHaut() {
        return y;
    }

    /**
     * Obtient la position en y du bas de l'objet.
     *
     * @return La position en y du bas de l'objet.
     */
    public double getBas() {
        return y + h;
    }

    /**
     * Obtient la position en x de la gauche de l'objet.
     *
     * @return La position en x de la gauche de l'objet.
     */
    public double getGauche() {
        return x;
    }

    /**
     * Obtient la position en x de la droite de l'objet.
     *
     * @return La position en x de la droite de l'objet.
     */
    public double getDroite() {
        return x + w;
    }

    /**
     * Obtient l'image associée à l'objet.
     *
     * @return L'image associée à l'objet.
     */
    public Image getImage() {
        return image;
    }
}
