package twisk.vues;


import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twisk.ecouteurs.DragDetectedEcouteur;
import twisk.ecouteurs.SlctStepEcouteur;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

public abstract class VueEtapeIG extends VBox implements Observateur {

    private MondeIG world;
    private EtapeIG step;
    private Label lbl;
    private HBox hb = new HBox();

    /**
     * Constructeur VueEtapeIG
     * @param world
     * @param step
     */
    public VueEtapeIG(MondeIG world, EtapeIG step){
        super();
        this.world=world;
        this.step=step;
        this.setOnMouseClicked(new SlctStepEcouteur(world, this ));
        this.setOnDragDetected(new DragDetectedEcouteur(world, this));
    }


    /**
     * getters & setters
     */
    public Label getLbl() {
        return lbl;
    }

    public void setLbl(Label lbl) {
        this.lbl = lbl;
    }

    public EtapeIG getStep() {
        return step;
    }

    public HBox getHb() {
        return hb;
    }

    /**
     * mise a jour de l'etiquette devant une activite estampillee entree sortie ou les deux
     */
    public void setIconEntryExit()
    {
        if(this.getStep().isEntry() || this.getStep().isExit()) {

            if (this.getStep().isEntry() && !getStep().isExit()) {
                setLbl(new Label("E\t"+getStep().getName() + " " + getStep().getId()+" : "+((ActiviteIG) step).getDelai()+"+="+ ((ActiviteIG)step).getEcartTps()));
            }
            if (this.getStep().isExit() && !this.getStep().isEntry()) {
                setLbl(new Label("S\t"+getStep().getName() + " " + getStep().getId()+" : "+((ActiviteIG) step).getDelai()+"+="+ ((ActiviteIG)step).getEcartTps()));
            }
            if (this.getStep().isExit() && this.getStep().isEntry()) {
                setLbl(new Label("E/S\t"+getStep().getName() + " " + getStep().getId()+" : "+((ActiviteIG) step).getDelai()+"+="+ ((ActiviteIG)step).getEcartTps()));
            }
            getLbl().setStyle("-fx-font-size: 11.93;");


            getChildren().remove(0);
            getChildren().add(0,getLbl());
        }
    }
}
