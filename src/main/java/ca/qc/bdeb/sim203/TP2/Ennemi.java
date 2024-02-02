package ca.qc.bdeb.sim203.TP2;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.Random;

/**
 * Classe représentant un ennemi dans le jeu.
 */
public class Ennemi extends ObjetDuJeu {
    private boolean dead;
    private boolean aToucherCharlotte = false;

    /**
     * Constructeur de la classe Ennemi.
     *
     * @param level   Niveau de l'ennemi.
     * @param posCam  Position de la caméra.
     */
    public Ennemi(int level, int posCam) {
        Random r = new Random();
        this.image = new Image("poisson" + r.nextInt(1, 6) + ".png");
        this.h = r.nextInt(50, 121);
        this.w = (h * 120) / 104;
        this.y = r.nextDouble(Main.height / 5, Main.height * 4 / 5);
        this.x = posCam + Main.width - w;
        this.vx = -((100 * Math.pow(level, 0.33)) + 200);
        this.vy = r.nextDouble(0, 100) * r.nextInt(-1, 2);
        this.ay = 0;
        this.ax = -500;
    }

    /**
     * Vérifie si l'ennemi est hors de l'écran.
     *
     * @return True si l'ennemi est hors de l'écran, sinon false.
     */
    public boolean isOutScreen() {
        return getDroite() < 0 || getBas() < 0 || getHaut() > Main.height;
    }

    /**
     * Vérifie si l'ennemi est mort.
     *
     * @return True si l'ennemi est mort, sinon false.
     */
    public boolean isDead() {
        return dead;
    }

    /**
     * Vérifie si l'ennemi a touché le personnage principal (Charlotte).
     *
     * @return True si l'ennemi a touché Charlotte, sinon false.
     */
    public boolean isAToucherCharlotte() {
        return aToucherCharlotte;
    }

    /**
     * Définit si l'ennemi a touché le personnage principal (Charlotte).
     *
     * @param atoucherCharlotte True si l'ennemi a touché Charlotte, sinon false.
     */
    public void setAToucherCharlotte(boolean atoucherCharlotte) {
        this.aToucherCharlotte = atoucherCharlotte;
    }

    /**
     * Définit si l'ennemi est mort.
     *
     * @param dead True si l'ennemi est mort, sinon false.
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }

    /**
     * Dessine l'ennemi sur le contexte graphique.
     *
     * @param context   Le contexte graphique.
     * @param posCamera La position de la caméra.
     * @param modeDebug True si le mode de débogage est activé, sinon false.
     */
    @Override
    public void draw(GraphicsContext context, double posCamera, boolean modeDebug) {
        if (modeDebug) {
            context.setFill(Color.YELLOW);
            context.strokeRect(x - posCamera, y, image.getWidth(), image.getHeight());
        }
        context.drawImage(image, x - posCamera, y);
    }

    /**
     * Met à jour l'état de l'ennemi en fonction du temps et de la position de la caméra.
     *
     * @param deltaTemps Le temps écoulé depuis la dernière mise à jour.
     * @param posCam     La position de la caméra.
     */
    @Override
    public void update(double deltaTemps, double posCam) {
        super.update(deltaTemps, posCam);
    }
}

