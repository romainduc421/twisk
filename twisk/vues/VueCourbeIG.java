package twisk.vues;

import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import twisk.mondeIG.CourbeIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

/**
 * Example of how a cubic curve works, drag the anchors around to change the curve.
 * Extended with arrows with the help of José Pereda: https://stackoverflow.com/questions/26702519/javafx-line-curve-with-arrow-head
 * Original code by jewelsea: https://stackoverflow.com/questions/13056795/cubiccurve-javafx
 */

public class VueCourbeIG extends VueArcIG implements Observateur {

    private CubicCurve cubCurve ;
    private Path head;
    private int nbCliques=0;

    private double absStart;
    //abs coordinate of the start point of the cubic curve segment
    private double ordStart;
    //ord coordinate of the start point         " "
    private double absEnd, ordEnd;
    //abs & ord coordinates of the end point    " "
    private double absCtrl1, ordCtrl1, absCtrl2, ordCtrl2;
    //abs & ord coordinates of the first & second control-points  " "

    /**
     * Constructeur VueCourbeIG
     * @param w
     * @param curve
     */
    public VueCourbeIG(MondeIG w, CourbeIG curve)
    {
        super(w,curve) ;
        this.cubCurve = new CubicCurve(curve.getSrc().getAbs_centre(),
        curve.getSrc().getOrd_centre(),
        curve.getPt_controle1().getX(),
        curve.getPt_controle1().getY(),
        curve.getPt_controle2().getX(),
        curve.getPt_controle2().getY(),
        curve.getDest().getAbs_centre(),
        curve.getDest().getOrd_centre()) ;

        cubCurve.setStrokeLineCap(StrokeLineCap.ROUND);
        cubCurve.setStroke(Color.BURLYWOOD);
        cubCurve.setStrokeWidth(2.67);
        cubCurve.setFill(Color.TRANSPARENT);

        //TO-DO : array's head of cubic curve : with rotate doesnt work
        /*head = new Polyline();
                head.setStrokeWidth(2.321);
        Point2D pt = new Point2D(cubCurve.getEndX() - cubCurve.getStartX(), cubCurve.getEndY() - cubCurve.getEndX()) ;
        double rap = 20.0 / Math.sqrt(Math.pow(cubCurve.getEndX() - cubCurve.getStartX(),2) + Math.pow(cubCurve.getEndY() - cubCurve.getEndX(),2)),
        dX = -pt.getX()*rap, dY = -pt.getY()*rap;
        double eX = cubCurve.getEndX() + dX;
        double eY = cubCurve.getEndY() + dY;
        dX = dX/2;
        dY = dY/2;
        //head.getPoints().addAll(cubCurve.getEndX(),cubCurve.getEndY(), eX-dY, eY+dX, eX+dY, eY-dX, cubCurve.getEndX(), cubCurve.getEndY());
        head.setFill(Color.MEDIUMPURPLE);*/

        this.absStart = cubCurve.getStartX();
        this.ordStart = cubCurve.getStartY();
        this.absCtrl1 = cubCurve.getControlX1();
        this.ordCtrl1 = cubCurve.getControlY1();
        this.absCtrl2 = cubCurve.getControlX2();
        this.ordCtrl2 = cubCurve.getControlY2();
        this.absEnd = cubCurve.getEndX();
        this.ordEnd = cubCurve.getEndY();
        this.head = new Path() ;

        double taille = Math.max( this.cubCurve.getBoundsInLocal( ).getWidth( ),this.cubCurve.getBoundsInLocal( ).getHeight( )) ;
        Point2D origin = eval( 1.0 ) ;
        Point2D tan = evalDt( 1.0 ).normalize( ).multiply( taille/4d ) ;

        head.getElements().add(new MoveTo(origin.getX()-0.2*tan.getX()-0.2*tan.getY(),
                origin.getY()-0.2*tan.getY()+0.2*tan.getX())) ;
        head.getElements().add(new LineTo(origin.getX(), origin.getY()));
        head.getElements().add(new LineTo(origin.getX()-0.2*tan.getX()+0.2*tan.getY(),
                origin.getY()-0.2*tan.getY()-0.2*tan.getX()));
        head.setStyle("-fx-stroke: #FFADC1;"+
                "-fx-fill:#46EA2A;");
        this.getChildren().addAll(cubCurve, head) ;
        this.setPickOnBounds(false);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event)
            {
                if(nbCliques%2 != 0)
                {
                    getLongBow().setBowDetected(false);
                    head.setStyle("-fx-stroke: #FFADC1; -fx-fill:#46EA2A;");
                    cubCurve.setStyle("-fx-stroke: BURLYWOOD; -fx-stroke-width: 2.67; ");
                }
                else
                {
                    getLongBow().setBowDetected(true);
                    cubCurve.setStyle("-fx-stroke: red");
                    head.setStyle("-fx-stroke: red ; -fx-fill: #46EA2A");
                    w.addBowSelected(getLongBow());
                }
                ++nbCliques;
            }
        });
    }
    public void reagir()
    {

    }



    /**
     * Evaluates this cubic curve at the given parameter value, where
     * it is expected, but not required, that the parameter will be
     * between 0 and 1. 0 corresponds to the start point of the curve
     * and 1 corresponds to the end point of the curve.
     * @param t
     * @return  a newly allocated Point2D containing the evaluation of the curve at that parameter value
     */
    public Point2D eval(double t){
        return new Point2D(this.calcAbs(t), this.calcOrd(t));
    }

    /**
     * Evaluates the derivative of this cubic curve at the given
     * parameter value, where it is expected, but not required, that
     * the parameter will be between 0 and 1
     * @param t
     * @return Point2D in to which to store the derivative of the curve at that
     * parameter value
     */
    public Point2D evalDt(double t){
        final double u = 1-t ;
        double abs = 3*((absCtrl1-absStart)*u*u +
                2*(absCtrl2-absCtrl1)*u*t +
                (absEnd-absCtrl2)*t*t);
        double ord = 3*((ordCtrl1-ordStart)*u*u +
                2*(ordCtrl2-ordCtrl1)*u*t +
                (ordEnd-ordCtrl2)*t*t);

        return new Point2D(abs,ord);
    }

    private double calcOrd( double t ){
        final double u = 1 - t;
        return (
                u*u*u * ordStart
                        + 3*(t*u*u*ordCtrl1 +
                        t*t*u*ordCtrl2)
                        + t*t*t*ordEnd
        );
    }

    private double calcAbs( double t ){
        final double u = 1 - t ;
        return (
                u*u*u * absStart
                        + 3*(t*u*u*absCtrl1 +
                        t*t*u*absCtrl2)
                        + t*t*t*absEnd
        );
    }
}
