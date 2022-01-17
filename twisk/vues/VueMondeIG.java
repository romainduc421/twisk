package twisk.vues;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.util.Duration;
import twisk.ecouteurs.DragDroppedEcouteur;
import twisk.ecouteurs.DragOverEcouteur;
import twisk.exceptions.MondeException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.*;
import twisk.ui.Observateur;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class VueMondeIG extends Pane implements Observateur {

    private MondeIG world;
    private List<VueEtapeIG> vueSteps;
    private List<VuePointDeControleIG> vue_Pdc ;
    private PointDeControleIG pt_control_selected ;
    private Point2D pt1, pt2;

    public VueMondeIG(){

    }

    /**
     * Constructeur de la vue représentant toutes les activités répertoriées dans le monde
     * @param world
     */
    public VueMondeIG(MondeIG world) {
        super();
        this.vueSteps = new ArrayList<>(20);
        this.vue_Pdc = new ArrayList<>(12) ;
        this.world = world;
        for(EtapeIG step : this.world) {
            VueEtapeIG view_step = null;
            if(step.estActivite()) {
                view_step = new VueActiviteIG(this.world, step);

            }
            else if(step.estUnGuichet()) {
                view_step = new VueGuichetIG(this.world,step);
            }
            this.getChildren().add(view_step);
            this.vueSteps.add(view_step);
        }

        this.world.ajouterObservateur(this);
        this.setOnDragOver(new DragOverEcouteur(this.world,this));
        this.setOnDragDropped(new DragDroppedEcouteur(this.world,this));
        this.setStyle("-fx-background-color: #e1e5ed; -fx-border-color: #0059FF;");

        //ControlPointEcouteurCurveIncluded ctrPt = new ControlPointEcouteurCurveIncluded(this.world, this);
        //this.setOnMouseClicked(ctrPt);

        EventHandler<MouseEvent> handler = (MouseEvent evt) ->{
            PointDeControleIG clic_pt ;
            for(EtapeIG st : this.world){
                for(PointDeControleIG pdc : st){
                    if(Math.pow(evt.getX()-pdc.getAbs_centre(),2)+Math.pow(evt.getY()-pdc.getOrd_centre(), 2) < Math.pow(6.0, 2))
                    {
                        clic_pt = pdc ;
                        selectionnerPtDeControle(clic_pt);
                        return;
                    }
                }
            }
            if(pt_control_selected != null)
            {
                if(pt1 == null)
                    pt1 = new Point2D(evt.getX(), evt.getY()) ;
                else {
                    if (pt2 == null)
                        pt2 = new Point2D(evt.getX(), evt.getY());
                }
            }
        } ;
        this.setOnMouseClicked(handler);

    }
    @Override
    public void reagir() {
        this.getChildren().clear();
        this.world.Unselect();
        Iterator<ArcIG> iter_bow = this.world.iterator_Arcs();
        while(iter_bow.hasNext()){
            ArcIG bow = iter_bow.next();

            VueArcIG view_arc = null;
            if(bow.isLine())
                view_arc = new VueLigneDroiteIG(this.world, (LigneDroiteIG) bow);
            else if(bow.isCurve())
                view_arc = new VueCourbeIG(this.world,(CourbeIG) bow) ;
            this.getChildren().add(view_arc);

        }
        for(EtapeIG step : this.world) {
            VueEtapeIG view_act = null;
            if(step.estActivite())
                view_act = new VueActiviteIG(this.world,step);
            else if(step.estUnGuichet())
                view_act = new VueGuichetIG(this.world,step);
            this.getChildren().add(view_act);
            for(PointDeControleIG p_ig : step) {
                VuePointDeControleIG vpc = new VuePointDeControleIG(this.world, step, p_ig);
                this.getChildren().add(vpc);
            }
            if(!this.vueSteps.contains(view_act))
                this.vueSteps.add(view_act);
        }
    }

    public List<VueEtapeIG> getVueSteps() {
        return vueSteps;
    }

    /**
     * mise a jour du point du centre d'une EtapeIG considérée
     * @param st
     * @param abs
     * @param ord
     */
    public void relocate(VueEtapeIG st, double abs, double ord)
    {
        st.getStep().setPosX(abs);
        st.getStep().setPosY(ord);
    }

    /**
     * Methode permettant de selectionner successivement les deux points de controle
     * en sauvegardant les points de controle (pour une courbe eventuellement)
     * @param pdc
     */
    public void selectionnerPtDeControle (PointDeControleIG pdc)
    {
        if(pt_control_selected != null){
            if(pt2 != null && pt1 != null)
            {
                try{
                    world.ajouter(pt_control_selected, pdc, pt1,pt2);
                }catch (TwiskException excp){
                    this.errorPause(excp);
                }
                pt1 = null ;
                pt2 = null ;
                pt_control_selected = null ;
                world.notifierObservateurs();
                return ;
            }
            try{
                world.ajouter(pt_control_selected, pdc);
            }catch(TwiskException | MondeException excp){
                this.errorPause(excp);
            }
            pt_control_selected = null;
            world.notifierObservateurs();
        }
        else{
            pt_control_selected = pdc ;
        }
    }

    /**
     * Méthode permettant de gérer les exceptions du try-catch en selectionnant les pts de controle
     * @param excp
     */
    public void errorPause(Exception excp){
        PauseTransition pT = new PauseTransition( Duration.seconds(6.94) );
        Alert al = new Alert( Alert.AlertType.WARNING );
        al.setTitle( "Twisk exception!" ) ;
        al.setContentText( excp.getLocalizedMessage() );
        al.show();

        pT.setOnFinished(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                al.close() ;
            }
        });
        pT.play();
    }
}
