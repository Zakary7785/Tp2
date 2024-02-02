package ca.qc.bdeb.sim203.TP2;

import ca.qc.bdeb.sim203.TP2.projectiles.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * La classe Game représente le moteur du jeu. Elle gère les niveaux, les éléments du jeu et les interactions.
 */
public class Game {

    // Liste des décors potentiels pour le niveau
    private final ArrayList<Decors> decorsPotentiel = new ArrayList<>();

    // Position précédente de Charlotte
    private double anciennePosCharlotte = 0;

    // Indique si le joueur a demandé de retourner au menu
    private boolean retourMenu;

    // Tableau des types de décors possibles
    private final String[] typeDecors = {"decor1.png", "decor2.png", "decor3.png", "decor4.png", "decor5.png", "decor6.png"};

    // Instance de Charlotte, le personnage principal du jeu
    private final Charlotte charlotte;

    // Indique si l'écran de fin de partie doit être affiché
    private boolean showGameover;

    // Indique si le jeu est terminé (tous les niveaux sont complétés)
    private boolean finiGame;

    // Indique si le numéro du niveau doit être affiché
    private boolean showLevelNumber;

    // Instance de Baril, utilisé dans le jeu
    private Baril baril;

    // Temps avant l'apparition d'une nouvelle vague d'ennemis
    private double NewWaveTime;

    // Liste des ennemis (poissons) présents dans le jeu
    private ArrayList<Ennemi> poissonEnnemis;

    // Niveau actuel du jeu
    private int level = 0;

    // Instance de la caméra utilisée dans le jeu
    private Camera camera;

    // Nombre de vies restantes dans la barre de vie
    private int vieBarre;

    // Indique si le jeu est terminé
    private boolean fini;

    // Couleur actuelle utilisée dans le jeu
    private Color currentCouleur;

    // Nombre de fois que la vie maximale a été atteinte
    private int nbFoisVieAMax = 0;

    // Nombre de fois que l'arme a été changée
    private int nbFoisChangerArme = 1;

    // Nombre de fois que le niveau a été passé
    private int nbFoisPasserNiveau = 1;

    /**
     * Constructeur de la classe Game.
     */
    public Game() {
        this.fini = true;
        this.charlotte = new Charlotte();
        this.showGameover = false;
        this.retourMenu = false;
        this.finiGame = false;
        this.camera = new Camera();
    }

    /**
     * Initialise et lance un nouveau niveau du jeu.
     */
    public void lancerNiveau() {
        this.camera = new Camera();

        // Initialisation des éléments du niveau
        Random r = new Random();
        level += 1;
        this.NewWaveTime = 0.75 + (1 / Math.sqrt(level));
        currentCouleur = Color.hsb(r.nextDouble(190, 271), 0.84, 1);

        baril = new Baril();
        baril.setX(r.nextDouble(Main.width / 5, (Main.width * 4) / 5));
        poissonEnnemis = new ArrayList<>();

        // Création des ennemis pour le niveau
        for (int i = 0; i < r.nextInt(0, 6); i++) {
            poissonEnnemis.add(new Ennemi(level, (int) camera.getX()));
        }
        vieBarre = 4;
        fini = false;
        int pos = 0;

        // Création des décors pour le niveau
        while (pos < Main.width * 8) {
            Random random = new Random();
            Decors decor = new Decors(typeDecors[random.nextInt(6)], pos);
            decorsPotentiel.add(decor);
            pos = pos + 80 + random.nextInt(50, 101);
        }
    }

    /**
     * Met à jour l'état du jeu en fonction du temps écoulé et des actions du joueur.
     *
     * @param dt                     Le temps écoulé depuis la dernière mise à jour.
     * @param modeDebug              Indique si le mode de débogage est activé.
     * @param projectileEtoile       Indique si le projectile étoile est en cours d'utilisation.
     * @param projectileHippocampe   Indique si le projectile hippocampe est en cours d'utilisation.
     * @param projectileSardine      Indique si le projectile sardine est en cours d'utilisation.
     * @param vieAMax                Le nombre de vies maximales à atteindre.
     * @param passerNiveau           Le nombre de niveaux à passer.
     * @param changementArmeDemander Le nombre de changements d'arme demandés.
     */
    public void update(double dt, boolean modeDebug, boolean projectileEtoile, boolean projectileHippocampe, boolean projectileSardine, int vieAMax, int passerNiveau, int changementArmeDemander) {
        // Met à jour la vie de Charlotte si le nombre de vies maximales est atteint en mode débogage
        if (vieAMax == nbFoisVieAMax && modeDebug) {
            charlotte.setVie(4);
            nbFoisVieAMax++;
        }

        // Indique que le niveau est terminé si le nombre de niveaux à passer est atteint
        if (passerNiveau == nbFoisPasserNiveau) {
            charlotte.finiNiveau = true;
            nbFoisPasserNiveau++;
            if (nbFoisPasserNiveau == 6) {
                charlotte.setTempsDeMort(System.nanoTime());
            }
        }

        // Affiche l'écran de fin de partie si la vie de Charlotte atteint 0
        if (charlotte.getVie() == 0) {
            showGameover = true;
        }

        // Efface la liste des ennemis si le niveau est terminé
        if (charlotte.finiNiveau) {
            poissonEnnemis.clear();
        }

        // Met à jour la position de Charlotte et de la caméra
        charlotte.update(dt, camera.getX());
        if (((charlotte.getGauche() > camera.getX() + (Main.width / 5)) && (charlotte.getGauche() < (7 * Main.width)) || charlotte.finiNiveau) && anciennePosCharlotte < charlotte.getGauche()) {
            camera.update(charlotte);
        }

        // Met à jour le baril et les ennemis
        baril.update(dt, camera.getX());
        for (Ennemi e : poissonEnnemis) {
            for (int i = 0; i < charlotte.armeArray.size(); i++) {
                if (e.getGauche() > charlotte.getArmeArray(i).getDroite() && charlotte.getArmeArray(i).isSardine()) {
                    calculForceElec(e, i);
                }
                charlotte.getArmeArray(i).projectileTue(poissonEnnemis);
            }
            e.update(dt, camera.getX());
            charlotte.charlotteToucher(poissonEnnemis);
        }

        // Supprime les ennemis morts ou hors de l'écran
        for (int i = poissonEnnemis.size() - 1; i > 0; i--) {
            if (poissonEnnemis.get(i).isDead() || poissonEnnemis.get(i).isOutScreen())
                poissonEnnemis.remove(i);
        }

        // Met à jour les projectiles de Charlotte
        for (int i = 0; i < charlotte.armeArray.size(); i++) {
            charlotte.getArmeArray(i).update(dt, camera.getX());
        }

        // Met à jour la barre de vie de Charlotte
        vieBarre = charlotte.getVie();

        // Change l'arme du baril si nécessaire
        baril.changerArme(charlotte);

        // Met à jour la position précédente de Charlotte
        anciennePosCharlotte = charlotte.getGauche();

        // Active le changement d'arme en mode débogage
        if (modeDebug) {
            if ((projectileEtoile || projectileHippocampe || projectileSardine) && (changementArmeDemander == nbFoisChangerArme)) {
                ArrayList<Projectile> projectiles = creerProjectile(projectileEtoile, projectileHippocampe, projectileSardine);
                charlotte.setArme(projectiles);
                nbFoisChangerArme++;
            }
        }
    }

    /**
     * Ajoute une nouvelle vague d'ennemis au jeu.
     */
    public void newVaguePoisson() {
        Random r = new Random();
        for (int i = 0; i < r.nextInt(0, 6); i++) {
            poissonEnnemis.add(new Ennemi(level, (int) (camera.getX())));
        }
    }

    /**
     * Calcule la force électrique entre l'objet du jeu et l'arme spécifiée de Charlotte.
     *
     * @param o L'objet du jeu sur lequel la force électrique est appliquée.
     * @param i L'indice de l'arme de Charlotte utilisée pour le calcul.
     */
    private void calculForceElec(ObjetDuJeu o, int i) {
        double distanceExpo2 = distanceExpo2(charlotte.getArmeArray(i).getDroite(), charlotte.getArmeArray(i).getHaut(), o.getGauche(), o.getHaut());
        double distance = Math.sqrt(distanceExpo2);
        double deltaX = charlotte.getArmeArray(i).x - (o.x + o.w / 2);
        double deltaY = charlotte.getArmeArray(i).y - (o.y + o.h / 2);
        if (distance < 0.01)
            distance = 0.01;
        double proportionX = deltaX / distance;
        double proportionY = deltaY / distance;
        double forceElectrique = 1000 * 100 * -200 / distanceExpo2;
        charlotte.getArmeArray(i).setAx(charlotte.getAx() + (forceElectrique * proportionX));
        charlotte.getArmeArray(i).setAy(charlotte.getAy() + forceElectrique * proportionY);
    }

    /**
     * Calcule la distance euclidienne au carré entre deux points.
     *
     * @param sardineX Coordonnée X du premier point.
     * @param sardineY Coordonnée Y du premier point.
     * @param poissonX Coordonnée X du deuxième point.
     * @param poissonY Coordonnée Y du deuxième point.
     * @return La distance euclidienne au carré entre les deux points.
     */
    public double distanceExpo2(double sardineX, double sardineY, double poissonX, double poissonY) {
        return (Math.pow(sardineX - poissonX, 2) + Math.pow(sardineY - poissonY, 2));
    }

    /**
     * Dessine les éléments du jeu sur le contexte graphique.
     *
     * @param context   Le contexte graphique sur lequel dessiner.
     * @param modeDebug Indique si le mode de débogage est activé.
     */
    public void draw(GraphicsContext context, boolean modeDebug) {
        context.setFill(Color.DARKRED);
        if (showGameover) {
            context.setFont(Font.font("arial", FontWeight.BOLD, 40));
            context.fillText("FIN DE PARTIE", Main.width / 4, Main.height / 2, Main.width);
        }

        context.setFill(Color.WHITE);
        if (showLevelNumber) {
            if (level < 6) {
                context.setFont(Font.font("arial", FontWeight.BOLD, 40));
                context.fillText("Niveau " + level, Main.width / 4, Main.height / 2, Main.width);
            }
        }
        if (modeDebug) {
            context.setFont(Font.font("arial", FontWeight.BOLD, 10));
            context.fillText("Nombre de projectile dans le jeu: " + (nbProjectileVisible(charlotte.armeArray)), 10, 60, Main.width);
            context.fillText("Nombre de poissons dans le jeu: " + (poissonEnnemis.size() - 1), 10, 70, Main.width);
            context.fillText("Position de Charlotte dans le niveau: " + (int) ((charlotte.getGauche() / (8 * Main.width)) * 100) + " %", 10, 80, Main.width);
        }
        if (finiGame) {
            context.setFont(Font.font("arial", FontWeight.BOLD, 50));
            context.fillText("Vous avez Gagné", Main.width / 4, Main.height / 2, Main.width);
        }

        context.strokeRect(10, 10, 150, 30);


        context.fillRect(10, 10, getVieBarre() * 37.5, 30);//le 37.5 egale au nombre de pixel par vie
        charlotte.draw(context, camera.getX(), modeDebug);
        for (int i = 1; i < charlotte.armeArray.size(); i++) {
            charlotte.getArmeArray(i).draw(context, camera.getX(), modeDebug);
        }
        baril.draw(context, camera.getX(), modeDebug);
        for (Ennemi e : poissonEnnemis) {
            e.draw(context, camera.getX(), modeDebug);
        }
        context.drawImage(charlotte.getArmeArray(0).getImage(), 170, 10);
        for (Decors decors : decorsPotentiel) {
            decors.draw(context, camera.getX());
        }


    }

    /**
     * Calculates the number of projectiles visible on the screen.
     *
     * @param armes The list of projectiles to check.
     * @return The number of projectiles visible on the screen.
     */
    public int nbProjectileVisible(ArrayList<Projectile> armes) {
        int nbProjectiles = 0;
        for (Projectile arme : armes) {
            if (arme.getDroite() < camera.getX() + Main.width) {
                nbProjectiles++;
            }
        }
        return nbProjectiles;
    }

    /**
     * Creates a list of projectiles based on the specified criteria.
     *
     * @param etoiles    Whether to include star projectiles.
     * @param hippocampe Whether to include seahorse projectiles.
     * @param sardine    Whether to include sardine projectiles.
     * @return The list of projectiles based on the specified criteria.
     */
    public ArrayList<Projectile> creerProjectile(boolean etoiles, boolean hippocampe, boolean sardine) {
        ArrayList<Projectile> projectiles;
        if (etoiles) {
            projectiles = new ArrayList<>(List.of(charlotte.getArmes()[0]));
            return projectiles;
        } else if (hippocampe) {
            projectiles = new ArrayList<>(List.of(charlotte.getArmes()[1]));
            return projectiles;
        } else if (sardine) {
            projectiles = new ArrayList<>(List.of(charlotte.getArmes()[2]));
            return projectiles;
        }
        return null;
    }

    /**
     * Gets the current color.
     *
     * @return The current color.
     */
    public Color getCurrentCouleur() {
        return currentCouleur;
    }

    /**
     * Gets the Charlotte object.
     *
     * @return The Charlotte object.
     */
    public Charlotte getCharlotte() {
        return charlotte;
    }

    /**
     * Gets the current level.
     *
     * @return The current level.
     */
    public int getLevel() {
        return level;
    }

    /**
     * Gets the life bar value.
     *
     * @return The life bar value.
     */
    public int getVieBarre() {
        return vieBarre;
    }

    /**
     * Checks if the game should return to the main menu.
     *
     * @return True if the game should return to the main menu, false otherwise.
     */
    public boolean isRetourMenu() {
        return retourMenu;
    }

    /**
     * Sets whether the game should return to the main menu.
     *
     * @param retourMenu True to return to the main menu, false otherwise.
     */
    public void setRetourMenu(boolean retourMenu) {
        this.retourMenu = retourMenu;
    }

    /**
     * Sets the cooldown status for Charlotte.
     *
     */
    public void setCharlotteCooldown() {
        // Indique si Charlotte est en période de cooldown
    }

    /**
     * Sets the game finish status.
     *
     * @param finiGame True if the game has finished, false otherwise.
     */
    public void setFiniGame(boolean finiGame) {
        this.finiGame = finiGame;
    }

    /**
     * Checks if the game over screen should be displayed.
     *
     * @return True if the game over screen should be displayed, false otherwise.
     */
    public boolean isShowGameover() {
        return showGameover;
    }

    /**
     * Sets whether the game over screen should be displayed.
     *
     * @param showGameover True to display the game over screen, false otherwise.
     */
    public void setShowGameover(boolean showGameover) {
        this.showGameover = showGameover;
    }

    /**
     * Sets whether the level number should be displayed.
     *
     * @param showLevelNumber True to display the level number, false otherwise.
     */
    public void setShowLevelNumber(boolean showLevelNumber) {
        this.showLevelNumber = showLevelNumber;
    }

    /**
     * Checks if the game has finished.
     *
     * @return True if the game has finished, false otherwise.
     */
    public boolean isFini() {
        return fini;
    }

    /**
     * Sets whether the game has finished.
     *
     * @param fini True if the game has finished, false otherwise.
     */
    public void setFini(boolean fini) {
        this.fini = fini;
    }

    /**
     * Gets the time for the start of a new wave.
     *
     * @return The time for the start of a new wave.
     */
    public double getNewWaveTime() {
        return NewWaveTime;
    }
}