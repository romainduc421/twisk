package twisk.vues;

import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.ecouteurs.SlctBowEcouteur;
import twisk.mondeIG.LigneDroiteIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

public class VueLigneDroiteIG extends VueArcIG implements Observateur {

    private Line linking;
    private Polyline arrow;

    /**
     * Constructeur VueLigneDroiteIG
     * @param world
     * @param longBow
     */
    public VueLigneDroiteIG(MondeIG world, LigneDroiteIG longBow){
        super(world,longBow);
        Point2D startPt = new Point2D(super.getLongBow().getSrc().getAbs_centre(), super.getLongBow().getSrc().getOrd_centre());
        Point2D endPt = new Point2D(super.getLongBow().getDest().getAbs_centre(), super.getLongBow().getDest().getOrd_centre());
        double dx = endPt.getX() - startPt.getX(), dy = endPt.getY() - startPt.getY();

        this.linking = new Line() ;
        this.linking.setStartX(startPt.getX()) ;
        this.linking.setStartY(startPt.getY()) ;
        this.linking.setEndX(endPt.getX()) ;
        this.linking.setEndY(endPt.getY()) ;

        double angle = Math.atan2(dy, dx) - 2*Math.atan(1.0);

        /* public static double atan2 ( double y, double x ) ;
        cette methode permet de calculer l'arctan de y/x (où x et y représentent les coordonnées d'un point)
        elle renvoie la valeur d'un angle compris entre -π/2 et π/2 (rad)
         */

        //https://gist.github.com/kn0412/2086581e98a32c8dfa1f69772f14bca4
        double abs1 = (-0.5*this.cosinus(angle) + this.sq_root(3.0)/2 * this.sinus(angle)) * 15+ endPt.getX() ,
        abs2 = (0.5*this.cosinus(angle) + this.sq_root(3.0)/2 * this.sinus(angle)) * 15+ endPt.getX() ,
        abs3 = endPt.getX() ,
        ord1 = (-0.5*this.sinus(angle) - this.sq_root(3.0)/2 * this.cosinus(angle)) * 15+ endPt.getY() ,
        ord2 = (0.5*this.sinus(angle) - this.sq_root(3.0)/2 * this.cosinus(angle)) * 15+ endPt.getY() ,
        ord3 = endPt.getY();

        this.arrow = new Polyline(abs3,ord3,abs1,ord1,abs2,ord2,abs3,ord3);
        this.arrow.setStrokeWidth(3);

        this.arrow.setFill(Color.BURLYWOOD);
        this.linking.setStyle("-fx-stroke: #0d4978; -fx-stroke-width: 2");
        this.linking.setStrokeWidth(2);

        this.arrow.setMouseTransparent(true);
        this.linking.setOnMouseClicked(new SlctBowEcouteur(world,this,linking,arrow));
        this.setPickOnBounds(false);
        this.getChildren().addAll(this.linking,this.arrow);
    }

    public double sinus(double angle){
        return Math.sin(angle);
    }

    public double cosinus(double angle){
        return Math.cos(angle);
    }

    public double sq_root(double val){
        return Math.sqrt(val);
    }

    @Override
    public void reagir(){

    }
}
