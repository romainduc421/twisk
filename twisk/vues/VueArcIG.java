package twisk.vues;

import javafx.scene.layout.Pane;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

public abstract class VueArcIG extends Pane implements Observateur {

    private MondeIG world;
    protected ArcIG longBow;

    /**
     * Constructeur VueArcIG
     * @param world
     * @param longBow
     */
    public VueArcIG(MondeIG world, ArcIG longBow){
        super();
        this.world = world;
        this.longBow = longBow;
    }

    @Override
    public void reagir(){

    }

    /**
     * getters
     */
    public MondeIG getWorld() {
        return world;
    }

    public ArcIG getLongBow() {
        return longBow;
    }
}
