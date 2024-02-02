package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Main;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

/**
 * Classe représentant l'objet projectile de type "Sardine".
 */
public class Sardine extends Projectile {
    // Vitesse minimale et maximale autorisée
    private static final int minVx = 300;
    private static final int maxVx = 500;
    private static final int minVy = -500;
    private static final int maxVy = 500;

    /**
     * Constructeur de la classe Sardine.
     *
     * @param x La position horizontale initiale
     * @param y La position verticale initiale
     */
    public Sardine(double x, double y) {
        super(x, y);
        this.image = new Image("sardines.png");
        this.vx = 300;
        this.vy = 0;
        this.ay = 0;
        this.ax = 0;
        this.w = 35;
        this.h = 29;
        this.setSardine(true);
    }

    /**
     * Met à jour la position et la physique de l'objet en fonction du temps écoulé.
     *
     * @param dt         Le temps écoulé depuis la dernière mise à jour
     * @param posCamera  La position de la caméra
     */
    @Override
    public void update(double dt, int posCamera) {
        if (isVisible()) {
            // Limite les vitesses aux valeurs minimales et maximales autorisées
            vx = Math.min(Math.max(vx, minVx), maxVx);
            vy = Math.min(Math.max(vy, minVy), maxVy);

            // Gère les collisions avec les bords de l'écran
            if (getHaut() < 0 || getBas() > Main.height) {
                y = Math.min(Math.max(y, 0), Main.height - h);
                ay = -ay;
            }

            // Rend l'objet invisible s'il sort de l'écran
            if (getGauche() > Main.width + posCamera) {
                setVisible(false);
            }

            // Met à jour la physique de l'objet
            updatePhysique(dt);
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

        // Dessine l'image de la sardine si elle est visible
        if (isVisible()) {
            context.drawImage(image, x - posCamera, y);
        }
    }
}
