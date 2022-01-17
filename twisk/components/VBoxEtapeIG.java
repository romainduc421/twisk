package twisk.components;

import twisk.mondeIG.EtapeIG;
import javafx.scene.layout.VBox;

public class VBoxEtapeIG extends VBox {
    private EtapeIG etapeIG;

    public EtapeIG getEtapeIG() {
        return this.etapeIG;
    }

    public void setEtapeIG(EtapeIG etapeIG) {
        this.etapeIG = etapeIG;
    }
}
