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
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;

import java.util.Optional;

public class TokenEcouteur implements EventHandler<ActionEvent> {

    private MondeIG w;
    public TokenEcouteur(MondeIG w)
    {
        this.w = w;
    }
    @Override
    public void handle(ActionEvent evt){
        if(this.w.getSelectedSteps().size() == 1 && this.w.getSelectedSteps().get(0).estUnGuichet())
        {
            GuichetIG act_select = (GuichetIG) this.w.getSelectedSteps().get(0) ;
            TextInputDialog dial = new TextInputDialog("guich") ;
            dial.setTitle("set the number of tokens") ;
            dial.setContentText("Choose a number : ");
            Optional<String> res = dial.showAndWait() ;
            res.ifPresent((lambda) -> {

                if (lambda.matches("[0-9]+") && Integer.parseInt(lambda) <= 20)
                    act_select.setNbJetons(Integer.parseInt(lambda));
                else
                {
                    try
                    {
                        throw new SetTpsEcartException("NbJetons mal regle", lambda);
                    } catch (TwiskException excp) {
                        Alert al = new Alert(Alert.AlertType.WARNING) ;
                        al.setTitle("Nb Jetons-Exception");
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
            al.setTitle("nbToken-Exception");
            PauseTransition pT = new PauseTransition(Duration.seconds(5.74));
            al.setContentText("Please select only one step and no empty-selection and not an activity");
            al.show();
            pT.setOnFinished(evnt->{
                al.close();
            });
            pT.play();
        }
    }
}
