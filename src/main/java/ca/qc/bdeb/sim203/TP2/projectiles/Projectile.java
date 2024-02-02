package ca.qc.bdeb.sim203.TP2.projectiles;

import ca.qc.bdeb.sim203.TP2.Ennemi;
import ca.qc.bdeb.sim203.TP2.ObjetDuJeu;

import java.util.ArrayList;

/**
 * Classe abstraite représentant un projectile dans le jeu.
 */
public abstract class Projectile extends ObjetDuJeu {
    private boolean visible;
    private boolean isSardine;

    /**
     * Constructeur de la classe Projectile.
     *
     * @param x La position en x du projectile.
     * @param y La position en y du projectile.
     */
    public Projectile(double x, double y) {
        this.x = x;
        this.y = y;
        this.visible = false;
        this.isSardine = false;
    }

    /**
     * Vérifie si le projectile est visible.
     *
     * @return True si le projectile est visible, sinon false.
     */
    public boolean isVisible() {
        return visible;
    }

    /**
     * Définit la visibilité du projectile.
     *
     * @param visible True pour rendre le projectile visible, sinon false.
     */
    public void setVisible(boolean visible) {
        this.visible = visible;
    }

    /**
     * Vérifie si le projectile est une sardine.
     *
     * @return True si le projectile est une sardine, sinon false.
     */
    public boolean isSardine() {
        return isSardine;
    }

    /**
     * Définit si le projectile est une sardine.
     *
     * @param sardine True si le projectile est une sardine, sinon false.
     */
    public void setSardine(boolean sardine) {
        isSardine = sardine;
    }

    /**
     * Met à jour l'état du projectile en fonction du temps et de la position de la caméra.
     *
     * @param dt        Le temps écoulé depuis la dernière mise à jour.
     * @param posCamera La position de la caméra.
     */
    public abstract void update(double dt, int posCamera);

    /**
     * Vérifie si le projectile a touché un poisson (ennemi).
     *
     * @param ennemi Le poisson (ennemi) à vérifier.
     * @return True si le projectile a touché le poisson, sinon false.
     */
    public boolean toucherPoisson(Ennemi ennemi) {
        boolean toucher = getGauche() < ennemi.getDroite() &&
                getDroite() > ennemi.getGauche() &&
                getHaut() < ennemi.getBas() &&
                getBas() > ennemi.getHaut();
        return toucher;
    }

    /**
     * Tue les poissons (ennemis) touchés par le projectile.
     *
     * @param ennemi La liste des poissons (ennemis) à vérifier et tuer.
     */
    public void projectileTue(ArrayList<Ennemi> ennemi) {
        for (Ennemi ennemi1 : ennemi) {
            if (toucherPoisson(ennemi1)) {
                ennemi1.setDead(true);
            }
        }
    }
}

