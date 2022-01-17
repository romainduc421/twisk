package twisk.ecouteurs;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

import java.util.Optional;

public class RenameEcouteur implements EventHandler<ActionEvent>, Observateur {

    private MondeIG w;
    private PauseTransition pT;
    private Alert al;

    /**
     * Constructeur RenameEcouteur
     * @param w
     */
    public RenameEcouteur(MondeIG w)
    {
        this.w = w;
    }
    @Override
    public void handle(ActionEvent evt)
    {
        if(!this.w.getSelectedSteps().isEmpty() && this.w.getSelectedSteps().size() < 2)
        {
            TextInputDialog txtDial = new TextInputDialog("Rename");
            txtDial.setHeaderText((String) null);
            txtDial.setTitle("Renommer ");
            txtDial.setContentText("Nouveau nom : ");
            Optional<String> res = txtDial.showAndWait();
            res.ifPresent((nom)->{
                ((EtapeIG) this.w.getSelectedSteps().get(0)).setName(nom);
            });
            this.w.Unselect();
            this.w.notifierObservateurs();
        }
        else
        {
            this.al = new Alert(Alert.AlertType.WARNING);
            this.al.setTitle("Rename exception");
            this.al.setContentText("Please select only one step ! ");
            this.al.show();
            this.pT = new PauseTransition(Duration.seconds(5.42));
            this.pT.setOnFinished(event->al.close());
            this.pT.play();
        }
    }

    @Override
    public void reagir()
    {

    }
}
