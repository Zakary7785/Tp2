package ca.qc.bdeb.sim203.TP2;

import ca.qc.bdeb.sim203.TP2.projectiles.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Classe représentant l'objet "Baril" dans le jeu.
 */
public class Baril extends ObjetDuJeu {
    private final Image barilOuvert;
    private boolean isOuvert;
    private final double tempsLancement; // Le temps lorsque le niveau a été lancé

    /**
     * Constructeur de la classe Baril.
     */
    public Baril() {
        this.y = 0;
        this.h = 83;
        this.w = 70;
        this.isOuvert = false;
        this.image = new Image("baril.png");
        this.barilOuvert = new Image("baril-ouvert.png");
        this.tempsLancement = System.nanoTime() * 1e-9;
    }

    /**
     * Dessine l'objet Baril sur le contexte graphique.
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

        // Dessine l'image du baril ouvert ou fermé en fonction de l'état
        if (!isOuvert) {
            context.drawImage(image, x - posCamera, y);
        } else {
            context.drawImage(barilOuvert, x - posCamera, y);
        }
    }

    /**
     * Met à jour la position de l'objet Baril en fonction du temps écoulé.
     *
     * @param dt     Le temps écoulé depuis la dernière mise à jour
     * @param posCam La position de la caméra
     */
    @Override
    public void update(double dt, double posCam) {
        // Mouvement du baril selon une trajectoire sinusoïdale
        y = (Main.height - h) / 2 * Math.sin(2 * Math.PI * ((System.nanoTime() * 1e-9) - tempsLancement) / 3) + (Main.height - h) / 2;
    }

    /**
     * Vérifie si le personnage "Charlotte" touche le baril et le marque comme ouvert s'il est touché.
     *
     * @param charlotte Le personnage principal
     * @return          True si le baril est touché, false sinon
     */
    public boolean toucherBaril(Charlotte charlotte) {
        boolean toucher = getGauche() < charlotte.getDroite() &&
                getDroite() > charlotte.getGauche() &&
                getHaut() < charlotte.getBas() &&
                getBas() > charlotte.getHaut();
        if (toucher) {
            isOuvert = true;
        }
        return toucher;
    }

    /**
     * Change l'arme du personnage "Charlotte" si elle touche le baril et que le baril n'est pas ouvert.
     *
     * @param charlotte Le personnage principal
     */
    public void changerArme(Charlotte charlotte) {
        if (!isOuvert) {
            if (toucherBaril(charlotte)) {
                // Sélectionne aléatoirement une nouvelle arme (différente de l'arme actuelle)
                Random r = new Random();
                Projectile[] armesActuelles = charlotte.getArmes();
                ArrayList<Projectile> armesNouveau;

                do {
                    armesNouveau = new ArrayList<>(List.of(armesActuelles[r.nextInt(3)]));
                } while (armesActuelles[0] == armesNouveau.get(0));

                charlotte.setArme(armesNouveau);
            }
        }

    }
}

