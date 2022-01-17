package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.ui.Observateur;

public class VueActiviteIG extends VueEtapeIG implements Observateur {

    private MondeIG world;

    /**
     * Constructeur VueActiviteIG
     * @param world
     * @param step
     */
    public VueActiviteIG(MondeIG world, EtapeIG step) {
        super(world, step);
        this.world = world;
        TailleComposants size_components = TailleComposants.getInstance();
        setLbl(new Label(getStep().getName() + " : " + ((ActiviteIG) step).getDelai() + "+=" + ((ActiviteIG) step).getEcartTps()));
        getLbl().setPadding(new Insets(1, 15, 1, 15));


        getHb().setPrefSize((size_components.getSee_customer())[0], (size_components.getSee_customer())[1]);
        getHb().setStyle("-fx-border-color: #0059ff;\n" +
                "-fx-background-color: #ffffcf; \n" +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                "-fx-background-radius: 3px, 3px, 2px, 1px;");
        getHb().setAlignment(Pos.BOTTOM_CENTER);
        this.setPadding(new Insets(2, 2, 2, 2));
        this.setStyle("-fx-border-color: #00ff2e;\n" +
                "-fx-background-color: #efdda3;\n" +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                "-fx-background-radius: 4px, 3px, 2px, 1px");

        this.relocate(step.getPosX(), step.getPosY());
        this.getChildren().addAll(getLbl(), getHb());
        this.setIconEntryExit();
        this.setPrefSize((size_components.getViewStep())[0], (size_components.getViewStep())[1]);
    }

    @Override
    public void reagir() {

    }
}
