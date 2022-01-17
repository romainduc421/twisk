package twisk.ecouteurs;

import javafx.event.EventHandler;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;
import twisk.vues.VueMondeIG;

public class DragDroppedEcouteur implements EventHandler<DragEvent>, Observateur {

    private VueMondeIG vW;
    private MondeIG w;

    /**
     * Constructeur DragDroppedEcouteur
     * permet de detecter le drop d'une activite a des coordonnees donnees
     * @param w
     * @param vW
     */
    public DragDroppedEcouteur(MondeIG w, VueMondeIG vW)
    {
        this.vW = vW;
        this.w = w;
    }
    @Override
    public void handle(DragEvent evt)
    {
        Dragboard dB = evt.getDragboard();
        boolean hasSucceed=false;
        if(dB.hasString()) {
            String id = dB.getString();
            for(int k=0; k<vW.getVueSteps().size(); k++){
                String[] param = dB.getString().split(",");
                if(vW.getVueSteps().get(k).getStep().getId().equals(param[0])){
                    vW.relocate(vW.getVueSteps().get(k),evt.getX()-Double.parseDouble(param[1]),evt.getY()-Double.parseDouble(param[2]));
                }
            }
            hasSucceed = !hasSucceed;
            /*if (!(movedStep == null)) {
                this.vW.relocate(evt.getX() - movedStep.getWidth() / 2.0, evt.getY() - movedStep.getHeight() / 2.0);
                hasSucceed = true;
            }*/
        }
        this.w.notifierObservateurs();
        evt.setDropCompleted(hasSucceed);
        evt.consume();

    }

    @Override
    public void reagir()
    {

    }

}
