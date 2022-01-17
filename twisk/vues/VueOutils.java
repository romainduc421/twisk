package twisk.vues;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.TilePane;
import javafx.scene.text.TextAlignment;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.ui.Observateur;

import java.util.Objects;


public class VueOutils extends TilePane implements Observateur {

    private MondeIG world;
    private Button to_add;

    /**
     * Constructeur VueOutils
     * @param world
     */
    public VueOutils(MondeIG world){
        super();

        TailleComposants size_components = TailleComposants.getInstance();
        this.world = world;
        Button add = new Button("",new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/twisk/ressources/images/add.png")), (size_components.getButton())[0], (size_components.getButton())[1], true, true)));
        add.setId("plusbutton");
        Tooltip field_act = new Tooltip("add activity");
        field_act.setTextAlignment(TextAlignment.RIGHT);
        add.setTooltip(field_act);
        this.to_add = add;
        this.to_add.setOnMouseClicked(evt -> {
            world.ajouter("Activite");
            world.notifierObservateurs();
        });

        Button add_counter = new Button("",new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/twisk/ressources/images/guich.png")),(size_components.getButton())[0], (size_components.getButton())[1], true, true)));
        Tooltip field_counter = new Tooltip("add a ticket-counter");
        add_counter.setTooltip(field_counter);
        add_counter.setId("plusbutton");
        add_counter.setOnMouseClicked(delta-> {
            world.ajouter("Guichet");
            world.notifierObservateurs();
        });
        this.getChildren().addAll(to_add, add_counter);
        this.world.ajouterObservateur(this);
    }

    @Override
    public void reagir() {
    }
}
