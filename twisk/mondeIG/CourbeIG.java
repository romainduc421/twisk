package twisk.mondeIG;

import javafx.geometry.Point2D;

public class CourbeIG extends ArcIG{

    private Point2D pt_controle1, pt_controle2;

    /**
     * Constructeur CourbeIG
     * @param pdc_src
     * @param pdc_dest
     * @param pt_controle1
     * @param pt_controle2
     */
    public CourbeIG(PointDeControleIG pdc_src, PointDeControleIG pdc_dest, Point2D pt_controle1, Point2D pt_controle2)
    {
        super( );
        super.setSrc(pdc_src);
        super.setDest(pdc_dest);
        this.pt_controle1 = pt_controle1 ;
        this.pt_controle2 = pt_controle2 ;
    }

    /**
     * getters
     */
    public Point2D getPt_controle1() {
        return pt_controle1;
    }

    public Point2D getPt_controle2() {
        return pt_controle2;
    }

    /**
     * determiner la nature de l'arc
     * @return true pour la courbe false pour la ligne
     */
    @Override
    public boolean isCurve(){
        return true;
    }

    @Override
    public boolean isLine() {
        return false;
    }
}
