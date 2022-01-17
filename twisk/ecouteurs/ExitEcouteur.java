package twisk.ecouteurs;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.util.Duration;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class ExitEcouteur implements EventHandler<ActionEvent> {

    private MondeIG w;

    /**
     * Constructeur ExitEcouteur
     * @param w
     */
    public ExitEcouteur(MondeIG w)
    {
        this.w = w ;
    }
    @Override
    public void handle(ActionEvent evt){
        if(this.w.getSelectedSteps().size() >= 1
                && this.w.getSelectedSteps().size() < 2)
        {
            this.w.addSortie((EtapeIG) this.w.getSelectedSteps().get(0));
        }
        else
        {
            PauseTransition pause = new PauseTransition(Duration.seconds(6));
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Exception");
            alert.setContentText("Exit : select only one step..");
            alert.show();
            pause.setOnFinished(event ->
                    alert.close());
            pause.play();
        }
        this.w.notifierObservateurs();
    }
}
