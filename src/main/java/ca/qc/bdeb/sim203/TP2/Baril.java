package ca.qc.bdeb.sim203.TP2;

import ca.qc.bdeb.sim203.TP2.projectiles.Projectile;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class Baril extends ObjetDuJeu{
    private Projectile projectile;
    private Image barilOuvert;
    private boolean isOuvert;
    private  final double tempsLancement;// le temps quand le niveau a ete lance
    public Baril() {
        this.y=0;
        this.h=83;
        this.w=70;
        this.isOuvert=false;
        this.image= new Image("baril.png");
        this.barilOuvert=new Image("baril-ouvert.png");
        this.tempsLancement=System.nanoTime()*1e-9;
    }
    @Override
    public void draw(GraphicsContext context){
        if(!isOuvert)
            context.drawImage(image,x,y);
        else context.drawImage(barilOuvert,x,y);
    }
    @Override
    public void update(double dt){
       y=(Main.HEIGHT - h)/2 * Math.sin( 2*Math.PI*((System.nanoTime()*1e-9)-tempsLancement)/3) + (Main.HEIGHT - h)/2;

    }

}
