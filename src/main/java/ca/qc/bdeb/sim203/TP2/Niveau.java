package ca.qc.bdeb.sim203.TP2;

import java.util.Random;

public class Niveau {
    private Charlotte charlotte;
    private Baril baril;
    private ObjetDuJeu[]objets;
    private int level=0;
    private int vieBarre;
    boolean fini;

    public Niveau(double dt) {
        Random r= new Random();
        this.charlotte = new Charlotte();
        this.baril= new Baril();
        baril.setX(r.nextDouble(Main.WIDTH/5,(Main.WIDTH*4)/5));
        this.objets = new ObjetDuJeu[r.nextInt(1,6)];//add space for the chosen projectile
        for (int i = 0; i < objets.length; i++) {
            objets[i]=new Ennemi(level);
        }
        this.level +=1;
        this.vieBarre = 4;
        this.fini=false;
    }
    public void update(double dt){
        charlotte.update(dt);
        baril.update(dt);
        for (ObjetDuJeu o: objets) {
            o.update(dt);
        }
    }

    public Baril getBaril() {
        return baril;
    }

    public ObjetDuJeu[] getObjets() {
        return objets;
    }

    public void setObjets(ObjetDuJeu[] objets) {
        this.objets = objets;
    }

    public Charlotte getCharlotte() {
        return charlotte;
    }

    public void setCharlotte(Charlotte charlotte) {
        this.charlotte = charlotte;
    }

    public ObjetDuJeu[] getObjet() {
        return objets;
    }

    public void setObjet(ObjetDuJeu[] objet) {
        this.objets = objet;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public int getVieBarre() {
        return vieBarre;
    }

    public void setVieBarre(int vieBarre) {
        this.vieBarre = vieBarre;
    }
}
