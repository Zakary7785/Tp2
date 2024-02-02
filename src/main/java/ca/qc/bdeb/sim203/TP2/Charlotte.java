package ca.qc.bdeb.sim203.TP2;

import ca.qc.bdeb.sim203.TP2.projectiles.Etoiles;
import ca.qc.bdeb.sim203.TP2.projectiles.Hippocampe;
import ca.qc.bdeb.sim203.TP2.projectiles.Projectile;
import ca.qc.bdeb.sim203.TP2.projectiles.Sardine;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe représentant le personnage "Charlotte" dans le jeu.
 */
public class Charlotte extends ObjetDuJeu {
    protected ArrayList<Projectile> armeArray = new ArrayList<>(List.of(new Etoiles(x + w / 2, y + h / 2)));
    private final Projectile[] armes = {new Etoiles(x + w / 2, y + h / 2), new Hippocampe(x + w / 2, y + h / 2), new Sardine(x + w / 2, y + h / 2)};
    private static final double VMAX = 300;
    private final Image imageBlesse;
    private final Image imageMouvement;
    private long derniereFoisToucher;
    protected boolean bouge, isInvinsible;
    protected boolean finiNiveau;
    private boolean visible = (derniereFoisToucher / 250) % 2 == 0;
    protected int vie;
    private long tempsDeMort = 0;
    private long tempsEcoulerTirer = 0;

    /**
     * Constructeur de la classe Charlotte.
     */
    public Charlotte() {
        this.vie = 4;
        this.bouge = false;
        this.isInvinsible = false;
        this.w = 102;
        this.h = 90;
        this.x = 0;
        this.y = ((Main.height / 2) - h);
        this.vx = 0;
        this.vy = 0;
        this.ax = 0;
        this.ay = 0;
        this.image = new Image("charlotte.png");
        this.imageBlesse = new Image("charlotte-outch.png");
        this.imageMouvement = new Image("charlotte-avant.png");
    }

    /**
     * Méthode qui gère l'attaque de Charlotte.
     */
    public void attaque() {
        // Vérifie si le délai entre les tirs est écoulé
        if (System.currentTimeMillis() > tempsEcoulerTirer + 500) {
            Projectile arme1;

            // Sélectionne le type d'arme en fonction de la première arme dans armeArray
            if (armeArray.get(0) instanceof Etoiles) {
                arme1 = new Etoiles(getGauche() + w / 2, getHaut() + h / 2);
            } else if (armeArray.get(0) instanceof Hippocampe) {
                arme1 = new Hippocampe(getGauche() + w / 2, getHaut() + h / 2);
            } else {
                arme1 = new Sardine(getGauche() + w / 2, getHaut() + h / 2);
            }

            // Rend l'arme visible et l'ajoute à armeArray
            arme1.setVisible(true);
            armeArray.add(arme1);

            // Met à jour le temps du dernier tir
            tempsEcoulerTirer = System.currentTimeMillis();
        }
    }

    /**
     * Méthode qui dessine l'objet sur le contexte graphique.
     *
     * @param context   Le contexte graphique
     * @param posCamera La position de la caméra
     * @param modeDebug Indique si le mode débogage est activé
     */
    @Override
    public void draw(GraphicsContext context, double posCamera, boolean modeDebug) {
        if (modeDebug) {
            // Dessine le contour de l'objet en mode débogage
            context.setFill(Color.YELLOW);
            context.strokeRect(x - posCamera, y, w, h);
        }

        // Dessine l'image en fonction de l'état d'invincibilité et de mouvement
        if (isInvinsible) {
            if (visible) {
                context.drawImage(imageBlesse, x - posCamera, y);
            }
        } else if (bouge) {
            context.drawImage(imageMouvement, x - posCamera, y);
        } else {
            context.drawImage(image, x - posCamera, y);
        }
    }

    /**
     * Méthode qui vérifie si l'objet Charlotte touche un ennemi.
     *
     * @param ennemi L'ennemi à vérifier
     * @return true si Charlotte touche l'ennemi, sinon false
     */
    public boolean toucherCharlotte(Ennemi ennemi) {
        // Vérifie si les coordonnées de Charlotte se chevauchent avec celles de l'ennemi
        boolean toucher = getGauche() < ennemi.getDroite() &&
                getDroite() > ennemi.getGauche() &&
                getHaut() < ennemi.getBas() &&
                getBas() > ennemi.getHaut();

        // Si Charlotte touche l'ennemi, la rend invincible
        if (toucher) {
            isInvinsible = true;
        }

        // Retourne le résultat de la vérification de la collision
        return toucher;
    }

    /**
     * Méthode qui gère les collisions entre Charlotte et les ennemis.
     *
     * @param ennemis La liste des ennemis présents dans le jeu
     */
    public void charlotteToucher(ArrayList<Ennemi> ennemis) {
        // Vérifie si Charlotte n'est pas invincible
        if (!isInvinsible) {
            // Parcourt la liste des ennemis
            for (Ennemi ennemi : ennemis) {
                // Vérifie si Charlotte touche l'ennemi et si l'ennemi n'a pas déjà été touché par Charlotte
                if (toucherCharlotte(ennemi) && !ennemi.isAToucherCharlotte()) {
                    // Réduit la vie de Charlotte
                    vie--;

                    // Marque l'ennemi comme touché par Charlotte
                    ennemi.setAToucherCharlotte(true);

                    // Enregistre le moment où Charlotte a été touchée
                    derniereFoisToucher = System.currentTimeMillis();
                }
            }
        }

        // Vérifie si la vie de Charlotte est épuisée
        if (vie == 0) {
            tempsDeMort = System.nanoTime();
        }
    }

    /**
     * Méthode qui met à jour les propriétés de l'objet Charlotte.
     *
     * @param dt     Le temps écoulé depuis la dernière mise à jour
     * @param posCam La position de la caméra
     */
    @Override
    public void update(double dt, double posCam) {
        // Vérifie les touches de direction pressées
        var left = Input.isKeyPressed(KeyCode.LEFT);
        var right = Input.isKeyPressed(KeyCode.RIGHT);
        var up = Input.isKeyPressed(KeyCode.UP);
        var down = Input.isKeyPressed(KeyCode.DOWN);

        // Vérifie si l'objet bouge actuellement (utile pour les contrôles et l'affichage)
        bouge = ax != 0 || ay != 0;

        // Gère le mouvement horizontal (axe x)
        mouvement(dt, left, right, true, vx);

        // Gère le mouvement vertical (axe y)
        mouvement(dt, up, down, false, vy);

        // Met à jour la physique de l'objet
        updatePhysique(dt);

        // Applique des contraintes en fonction de la position de l'objet
        if (x < 0) {
            x = 0;
            vx = -vx / 2;
        }

        if (getGauche() < posCam) {
            x = posCam;
            vx = -vx / 2;
        }

        if (getDroite() > Main.width * 8) {
            finiNiveau = true;
            x = 0;
            vx = 0;
            ax = 0;
        }

        if (getBas() > Main.height) {
            y = Main.height - h;
            ay = -ay;
        }

        if (getHaut() < 0) {
            y = 0;
            ay = -ay;
        }

        // Gère l'invincibilité et la visibilité après avoir été touché
        if (System.currentTimeMillis() - derniereFoisToucher > 1500) {
            isInvinsible = false;
            visible = true;
        } else {
            isInvinsible = true;
            visible = (System.currentTimeMillis() / 250) % 2 == 0;
        }
    }

    private void mouvement(double dt, boolean direction1, boolean direction2, boolean moveX, double v) {
        double a;

        // Vérifie si on bouge actuellement (utile pour le calcul de l'amortissement)
        bouge = ax != 0 || ay != 0;

        // Détermine l'accélération en fonction des entrées de direction
        if (direction1) {
            a = -10000;
        } else if (direction2) {
            a = 1000;
        } else {
            // Amortissement lorsque l'on ne presse aucune touche de direction
            a = 0;
            int signeVitesse = v > 0 ? 1 : -1;
            double vitesseAmortissementX = -signeVitesse * 500;
            v += dt * vitesseAmortissementX;
            int nouveauSigneVitesse = v > 0 ? 1 : -1;
            if (nouveauSigneVitesse != signeVitesse) {
                v = 0;
            }
        }

        // Limite la vitesse maximale
        if (v > VMAX) {
            v = VMAX;
            ax = 0;
        } else if (v < -1 * VMAX) {
            v = -VMAX;
        }

        // Affecte les valeurs de vitesse et d'accélération en fonction du mouvement en x ou y
        if (moveX && (direction1 || direction2)) { // Si on bouge en x et qu'on bouge
            this.ax = a;
            this.vx = v;
        } else if (!moveX && (direction1 || direction2)) { // Si on bouge en y et qu'on bouge
            this.ay = a;
            this.vy = v;
        } else {
            if (moveX) { // Si on bouge en x mais qu'on ne bouge pas en y
                this.ax = 0;
                this.vx = v;
            } else { // Si on bouge en y mais qu'on ne bouge pas en x
                this.ay = 0;
                this.vy = v;
            }
        }
    }

    /**
     * Méthode qui définit le temps de mort de Charlotte.
     *
     * @param tempsDeMort Le temps de mort à définir
     */
    public void setTempsDeMort(long tempsDeMort) {
        this.tempsDeMort = tempsDeMort;
    }

    /**
     * Retourne le temps de mort du joueur.
     *
     * @return Le temps de mort du joueur.
     */
    public long getTempsDeMort() {
        return tempsDeMort;
    }

    /**
     * Définit si le niveau est terminé.
     *
     * @param finiNiveau True si le niveau est terminé, sinon false.
     */
    public void setFiniNiveau(boolean finiNiveau) {
        this.finiNiveau = finiNiveau;
    }

    /**
     * Définit la liste d'armes du joueur.
     *
     * @param arme1 La nouvelle liste d'armes.
     */
    public void setArme(ArrayList<Projectile> arme1) {
        armeArray = arme1;
    }

    /**
     * Retourne le nombre de vies du joueur.
     *
     * @return Le nombre de vies du joueur.
     */
    public int getVie() {
        return vie;
    }

    /**
     * Définit le nombre de vies du joueur.
     *
     * @param vie Le nouveau nombre de vies.
     */
    public void setVie(int vie) {
        this.vie = vie;
    }

    /**
     * Retourne la liste d'armes du joueur.
     *
     * @return La liste d'armes du joueur.
     */
    public Projectile[] getArmes() {
        return armes;
    }

    /**
     * Retourne l'arme située à l'index spécifié dans la liste d'armes.
     *
     * @param index L'index de l'arme recherchée.
     * @return L'arme à l'index spécifié, ou null si l'index est hors limites.
     */
    public Projectile getArmeArray(int index) {
        if (armeArray.size() > index) {
            return armeArray.get(index);
        } else {
            // Gérer le cas où l'index est hors limites (par exemple, renvoyer null ou lancer une exception)
            // Dans cet exemple, nous renvoyons null
            return null;
        }
    }

}


