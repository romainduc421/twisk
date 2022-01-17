package twisk.vues;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
//import twiskIG.ecouteurs.ControlPointEcouteur;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.ui.Observateur;

public class VuePointDeControleIG extends Circle implements Observateur {

    private MondeIG world;
    private EtapeIG step;
    private PointDeControleIG control_point;

    /**
     * Constructeur VuePointDeControleIG
     * @param world
     * @param step
     * @param control_point
     */
    public VuePointDeControleIG(MondeIG world, EtapeIG step, PointDeControleIG control_point){
        super();
        this.control_point = control_point;
        this.step = step;
        this.world = world;
        this.setCenterX(control_point.getAbs_centre());
        this.setCenterY(control_point.getOrd_centre());
        this.setRadius(3.1);
        this.setStrokeWidth(1.9);
        this.setStroke(Color.DARKKHAKI);
        this.setFill(Color.STEELBLUE);
        //this.setOnMouseClicked(new ControlPointEcouteur(this.world,this));
    }
    @Override
    public void reagir() {

    }

    /**
     * getters
     */
    public PointDeControleIG getControl_point() {
        return this.control_point;
    }
    public EtapeIG getStep() {
        return step;
    }
}
