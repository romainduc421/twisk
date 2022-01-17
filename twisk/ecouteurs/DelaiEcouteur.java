package twisk.ecouteurs;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.util.Duration;
import twisk.exceptions.SetTpsEcartException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class DelaiEcouteur implements EventHandler<ActionEvent>
{
    MondeIG w;

    /**
     * Constructeur DelaiEcouteur
     * permet de set le temps d'une activite donnee
     * @param w
     */
    public DelaiEcouteur(MondeIG w)
    {
        this.w = w ;
    }
    @Override
    public void handle(ActionEvent evt)
    {
        if(this.w.getSelectedSteps().size() < 2 && this.w.getSelectedSteps().size()>=1)
        {
            TextInputDialog dial = new TextInputDialog("Tps act") ;
            dial.setTitle("set delay of a step") ;
            dial.setContentText("Choose a number : ");
            Optional<String> res = dial.showAndWait() ;
            res.ifPresent((lambda) -> {
                EtapeIG et_concerne = this.w.getSelectedSteps().get(0);
                if(!et_concerne.estUnGuichet()) {
                    if (lambda.matches("[0-9]+")) {
                        ((ActiviteIG) et_concerne).setDelai(Integer.parseInt(lambda));
                    } else {
                        try {
                            throw new SetTpsEcartException("Tps mal regle", lambda);
                        } catch (TwiskException excp) {
                            System.err.println(excp.getLocalizedMessage());
                        }
                    }
                }
                else {
                    try {
                        throw new SetTpsEcartException("Pas une activite", lambda);
                    } catch (TwiskException excp) {
                        System.err.println(excp.getLocalizedMessage());
                    }
                }
            });
            this.w.notifierObservateurs();
        }
        else
        {
            Alert al = new Alert(Alert.AlertType.WARNING) ;
            al.setTitle("delay-Exception");
            PauseTransition pT = new PauseTransition(Duration.seconds(5.74));
            al.setContentText("Please select only one step and no empty-selection");
            al.show();
            pT.setOnFinished(evnt->{
                al.close();
            });
            pT.play();
        }
    }
}
