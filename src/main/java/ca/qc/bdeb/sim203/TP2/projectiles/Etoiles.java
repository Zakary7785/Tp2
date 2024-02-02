package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Classe représentant l'objet projectile de type "Etoiles".
 */
public class Etoiles extends Projectile {
    // Vitesse horizontale constante
    private static final double VX = 800;

    /**
     * Constructeur de la classe Etoiles.
     *
     * @param x La position horizontale initiale
     * @param y La position verticale initiale
     */
    public Etoiles(double x, double y) {
        super(x, y);
        this.image = new Image("etoile.png");
        this.vx = VX;
        this.vy = 0;
        this.ay = 0;
        this.ax = 0;
        this.w = 36;
        this.h = 35;
    }

    /**
     * Met à jour la position de l'objet en fonction du temps écoulé.
     *
     * @param dt         Le temps écoulé depuis la dernière mise à jour
     * @param posCamera  La position de la caméra
     */
    @Override
    public void update(double dt, int posCamera) {
        if (isVisible()) {
            // Met à jour la position horizontale en fonction de la vitesse
            x += dt * vx;

            // Si l'objet sort de l'écran, le rend invisible
            if (getGauche() > Main.width + posCamera) {
                setVisible(false);
            }
        }
    }

    /**
     * Dessine l'objet sur le contexte graphique.
     *
     * @param context    Le contexte graphique
     * @param posCamera  La position de la caméra
     * @param modeDebug  Indique si le mode débogage est activé
     */
    @Override
    public void draw(GraphicsContext context, double posCamera, boolean modeDebug) {
        if (modeDebug) {
            // Dessine le contour de l'objet en mode débogage
            context.setStroke(Color.YELLOW);
            context.strokeRect(x - posCamera, y, w, h);
        }

        // Dessine l'image de l'étoile si elle est visible
        if (isVisible()) {
            context.drawImage(image, x - posCamera, y);
        }
    }
}
