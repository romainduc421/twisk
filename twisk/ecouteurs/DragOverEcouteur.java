package twisk.ecouteurs;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.TransferMode;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;
import twisk.vues.VueMondeIG;

public class DragOverEcouteur implements EventHandler<DragEvent>, Observateur {

    private MondeIG w;
    private VueMondeIG vw;

    /**
     * Constructeur DragOverEcouteur
     * @param w
     * @param vw
     */
    public DragOverEcouteur(MondeIG w, VueMondeIG vw)
    {
        this.w = w;
        this.vw = vw;
    }
    @Override
    public void handle(DragEvent evt)
    {
        if(evt.getDragboard().hasString())
            evt.acceptTransferModes(new TransferMode[]{TransferMode.MOVE});
        evt.consume();
    }

    @Override
    public void reagir()
    {

    }
}
