package twisk.vues;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;
import twisk.ui.Observateur;

import java.util.HashMap;


public class VueGuichetIG extends VueEtapeIG implements Observateur {

    private HashMap<Integer,HBox> clientContainer = new HashMap<>(12);

    public VueGuichetIG(MondeIG w, EtapeIG g){
        super(w,g);
        EtapeIG st = getStep();
        getHb().setPrefSize(200,32);
        getHb().setId("hboxGuichet");
        getHb().setAlignment(Pos.BOTTOM_CENTER);
        creerBoxes();
        setLbl(new Label(st.getName() + ", NbTokens : "+((GuichetIG)st).getNbJetons()));
        getLbl().setPadding(new Insets(1, 15, 1, 15));

        this.setStyle("-fx-border-color: #00ff2e;\n" +
                "-fx-background-color: #efdda3;\n" +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                "-fx-background-radius: 4px, 3px, 2px, 1px");
        getHb().setStyle(
                "-fx-background-color: #ffffcf; \n" +
                "-fx-background-insets: 0 0 -1 0, 0, 1, 2; \n" +
                "-fx-background-radius: 3px, 3px, 2px, 1px;");
        this.relocate(st.getPosX(), st.getPosY());
        this.setPrefSize(TailleComposants.getInstance().getViewCounter()[0], TailleComposants.getInstance().getViewCounter()[1]);
        this.getChildren().addAll(getLbl(), getHb());
    }

    public void creerBoxes(){
        int k; HBox cust_box;
        if(((GuichetIG)this.getStep()).isExitRight()){
            for(k = this.getStep().getLargeur()/20; k>0 ; --k){
                cust_box = new HBox();
                cust_box.setPrefWidth(20.0);
                cust_box.setMaxHeight(31);
                cust_box.setStyle("-fx-border-color: #9a394f; -fx-background-color: white; -fx-alignment: center");
                cust_box.setId("position"+(k-1));
                this.getHb().getChildren().add(cust_box);
                this.clientContainer.put(k, cust_box);
            }
        }
    }

    public HBox getClientContainer(int rank){
        return (HBox) this.clientContainer.get(rank) ;
    }
    public void reagir(){

    }
}
