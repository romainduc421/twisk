package twisk.ecouteurs;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import twisk.exceptions.MondeException;
import twisk.exceptions.TwiskException;
import twisk.mondeIG.MondeIG;
import twisk.vues.VuePointDeControleIG;


public class ControlPointEcouteur implements EventHandler<MouseEvent> {

    private MondeIG world;
    private VuePointDeControleIG V_pdc;
    private PauseTransition p_tr;
    private Alert al;


    /**
     * Constructeur ControlPointEcouteur
     * click event de VuePointDeControleIG avant la refonte (curviligne)
     * @param world
     * @param V_pdc
     */
    public ControlPointEcouteur(MondeIG world, VuePointDeControleIG V_pdc){
        this.world = world;
        this.V_pdc = V_pdc;
    }

    @Override
    public void handle(MouseEvent evt){
        try{
            try {
                this.world.selectedControlpoint(V_pdc.getControl_point());
            } catch (MondeException e) {
                System.out.println(e.getLocalizedMessage());
            }
            this.V_pdc.setFill(Color.RED);
            this.V_pdc.getControl_point().setSelected(true);
        }catch (TwiskException exc){
            this.setP_tr( new PauseTransition( Duration.seconds(6.94) ) );
            this.setAl( new Alert( Alert.AlertType.WARNING ) );
            this.getAl().setTitle( "Twisk exception!" ) ;
            this.getAl().setContentText( exc.getLocalizedMessage() );
            this.getAl().show();

            this.getP_tr().setOnFinished(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    al.close();
                    V_pdc.getControl_point().setSelected(false);
                }
            });
            this.getP_tr().play();
            this.world.reset_PtCtrlSrc();
        }

    }

    public PauseTransition getP_tr() {
        return p_tr;
    }

    public Alert getAl() {
        return al;
    }

    public void setP_tr(PauseTransition p_tr) {
        this.p_tr = p_tr;
    }

    public void setAl(Alert al) {
        this.al = al;
    }
}
