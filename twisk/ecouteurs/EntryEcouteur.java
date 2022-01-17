package twisk.ecouteurs;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

public class EntryEcouteur implements EventHandler<ActionEvent>, Observateur {

    private MondeIG w;

    /**
     * Constructeur EntryEcouteur
     * @param w
     */
    public EntryEcouteur(MondeIG w)
    {
        this.w = w;
    }
    @Override
    public void handle(ActionEvent evt)
    {
        if(this.w.getSelectedSteps().size() >= 1
        && this.w.getSelectedSteps().size() < 2)
        {
            this.w.addEntree((EtapeIG) this.w.getSelectedSteps().get(0));
        }
        else
        {
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText("Entry: select only one step..");
            alert.show();
            pause.setOnFinished(event ->
                    alert.close());
            pause.play();
        }
        this.w.notifierObservateurs();
    }

    @Override
    public void reagir()
    {

    }
}
