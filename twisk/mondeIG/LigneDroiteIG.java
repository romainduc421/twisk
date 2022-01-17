package twisk.mondeIG;

import java.io.Serializable;

public class LigneDroiteIG extends ArcIG implements Serializable {

    private PointDeControleIG[] arc_relie;

    /**
     * Constructeur LigneDroiteIG
     * @param src
     * @param destination
     */
    public LigneDroiteIG(PointDeControleIG src, PointDeControleIG destination){
        super( );
        this.arc_relie = new PointDeControleIG[2];
        this.arc_relie[0] = src;
        this.arc_relie[1] = destination;
        super.setSrc(src);
        super.setDest(destination);
    }


    /**
     * getters
     */
    public PointDeControleIG[] getArc_relie() {
        return arc_relie;
    }
    public PointDeControleIG getOrigin(){
        return this.arc_relie[0];
    }
    public PointDeControleIG getDestination(){
        return this.arc_relie[1];
    }

    /**
     * déterminer la nature de l'arc considéré
     * @return false pour la courbe true pour la ligne droite
     */
    @Override
    public boolean isCurve(){
        return false;
    }
    @Override
    public boolean isLine() {
        return true;
    }
}
