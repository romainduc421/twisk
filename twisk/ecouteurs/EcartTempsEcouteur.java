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
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class EcartTempsEcouteur implements EventHandler<ActionEvent>
{
    private MondeIG w;

    /**
     * Constructeur EcartTempsEcouteur
     * @param w
     */
    public EcartTempsEcouteur(MondeIG w)
    {
        this.w = w;
    }
    @Override
    public void handle (ActionEvent evt)
    {
        if(this.w.getSelectedSteps().size() < 2 && this.w.getSelectedSteps().size()>=1)
        {
            ActiviteIG act_select = (ActiviteIG) this.w.getSelectedSteps().get(0) ;
            TextInputDialog dial = new TextInputDialog("Ecart-tps act") ;
            dial.setTitle("set the time-deviation of a step") ;
            dial.setContentText("Choose a number : ");
            Optional<String> res = dial.showAndWait() ;
            res.ifPresent((lambda) -> {

                if (lambda.matches("[0-9]+"))
                    if ( Integer.parseInt(lambda) <= Integer.parseInt(act_select.getDelai()) )
                        act_select.setEcartTps(Integer.parseInt(lambda));
                    else
                    {
                        try
                        {
                            throw new SetTpsEcartException("Ecart tps superieur au temps.."+act_select.getDelai(), lambda);
                        } catch (TwiskException excp) {
                            Alert al = new Alert(Alert.AlertType.WARNING) ;
                            al.setTitle("delay-Exception");
                            PauseTransition pT = new PauseTransition(Duration.seconds(5.74));
                            al.setContentText(excp.getLocalizedMessage());
                            al.show();
                            pT.setOnFinished(evnt->{
                                al.close();
                            });
                            pT.play();
                        }
                    }
                else
                {
                    try
                    {
                        throw new SetTpsEcartException("Ecart tps mal regle", lambda);
                    } catch (TwiskException excp) {
                        Alert al = new Alert(Alert.AlertType.WARNING) ;
                        al.setTitle("delay-Exception");
                        PauseTransition pT = new PauseTransition(Duration.seconds(5.74));
                        al.setContentText(excp.getLocalizedMessage());
                        al.show();
                        pT.setOnFinished(evnt->{
                            al.close();
                        });
                        pT.play();
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
