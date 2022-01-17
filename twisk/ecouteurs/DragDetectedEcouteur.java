package twisk.ecouteurs;

import javafx.event.EventHandler;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;
import twisk.vues.VueEtapeIG;

public class DragDetectedEcouteur implements EventHandler<MouseEvent>, Observateur {

    private MondeIG w;
    private VueEtapeIG vw;

    /**
     * Constructeur DragDetectedEcouteur
     * permet de detecter le mouvement de souris pour lancer le drag'n'drop
     * @param w
     * @param vw
     */
    public DragDetectedEcouteur(MondeIG w, VueEtapeIG vw)
    {
        this.w = w;
        this.vw = vw;
    }
    @Override
    public void handle(MouseEvent evt)
    {
        Dragboard dragBoard = vw.startDragAndDrop(TransferMode.MOVE);
        ClipboardContent content = new ClipboardContent();
        SnapshotParameters param = new SnapshotParameters();
        content.putImage(vw.snapshot(param, (WritableImage) null));
        content.putString(this.vw.getStep().getId()+","+(evt.getX()-vw.getTranslateX()+","+(evt.getY()-vw.getTranslateY())));
        dragBoard.setContent(content);
        //System.out.println(content.getString());
        evt.consume();
    }
    @Override
    public void reagir()
    {

    }
}
