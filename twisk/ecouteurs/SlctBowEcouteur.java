package twisk.ecouteurs;

import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.mondeIG.MondeIG;
import twisk.vues.VueArcIG;

public class SlctBowEcouteur implements EventHandler<MouseEvent> {

    private VueArcIG bow_view;
    private Polyline pl;
    private Line l;
    private MondeIG w;
    private int nbCliques;

    /**
     * Constructeur SlctBowEcouteur
     * @param w
     * @param bow_view
     * @param l
     * @param pl
     */
    public SlctBowEcouteur(MondeIG w, VueArcIG bow_view, Line l, Polyline pl)
    {
        this.w = w;
        this.bow_view = bow_view;
        this.l = l;
        this.pl = pl;
    }
    @Override
    public void handle(MouseEvent evt)
    {
        if(nbCliques%2 != 0)
        {
            this.bow_view.getLongBow().setBowDetected(false);
            this.l.setStyle("-fx-stroke: #0d4978");
            this.pl.setStyle("-fx-stroke: BURLYWOOD; -fx-stroke-width: 3");
        }
        else
        {
            this.bow_view.getLongBow().setBowDetected(true);
            this.l.setStyle("-fx-stroke: red");
            this.pl.setStyle("-fx-stroke: red");
            this.w.addBowSelected(bow_view.getLongBow());
        }
        ++nbCliques;
    }
}
