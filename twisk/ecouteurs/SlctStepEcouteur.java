package twisk.ecouteurs;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.MouseEvent;
import twisk.mondeIG.MondeIG;
import twisk.vues.VueEtapeIG;

public class SlctStepEcouteur implements EventHandler<MouseEvent> {

    private MondeIG w;
    private VueEtapeIG viewS;
    private int nbClique;

    /**
     * Constructeur SlctStepEcouteur
     * @param w
     * @param viewS
     */
    public SlctStepEcouteur(MondeIG w, VueEtapeIG viewS)
    {
        this.w = w;
        this.viewS = viewS;
    }
    @Override
    public void handle(MouseEvent evt){
        if(this.nbClique %2 != 0)
        {
            this.w.eraseStepUnselected(viewS.getStep());
            viewS.setStyle("-fx-border-color: #00ff2e;\n" +
                    "-fx-background-color: #efdda3;\n" +
                    "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                    "-fx-background-radius: 4px, 3px, 2px, 1px");
            if(viewS.getStep().estActivite()) {
                viewS.getHb().setStyle("-fx-border-color: #0059ff;\n" +
                        "-fx-background-color: #ffffcf; \n" +
                        "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                        "-fx-background-radius: 3px, 3px, 2px, 1px;");
                viewS.getHb().setAlignment(Pos.BOTTOM_CENTER);
            }
        }
        else
        {
            viewS.setStyle("-fx-border-color: #00ff2e;\n"+
                    "-fx-background-color: #ffea1e; \n");

            if(viewS.getStep().estActivite()) {
                viewS.getHb().setStyle("-fx-border-color: RED;\n" +
                        "-fx-background-color: #ffffcf; \n" +
                        "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                        "-fx-background-radius: 3px, 3px, 2px, 1px;");
                viewS.getHb().setAlignment(Pos.BOTTOM_CENTER);
            }

            this.w.addStepSelected(viewS.getStep());
        }
        ++nbClique;
    }
}
