package twisk.controllers;
import java.net.URL;
import java.util.HashMap;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;

public class ControllerGuichetIG extends ControllerEtapeIG {
   @FXML
   public HBox clientBoxs;
   private String nbJetons;
   private HashMap<Integer, HBox> clientContainer;

   public ControllerGuichetIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, EtapeIG etapeIG, String nom, String nbJetons) {
      super(monde, gestionnaireGraphique, etapeIG, nom);
      this.nbJetons = nbJetons;
      this.clientContainer = new HashMap<>(10);
   }

   public HBox getClientContainer(int rang) {
      return this.clientContainer.get(rang);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      super.initialize(url, resourceBundle);
      this.description.setText(this.nom + " : " + this.nbJetons);
      int i;
      HBox clientBox;
      if (!((GuichetIG)this.etapeIG).isExitRight()) {
         for(i = 0; i < this.etapeIG.getLargeur() / 20; ++i) {
            clientBox = new HBox();
            clientBox.setPrefWidth(20.0);
            clientBox.setMaxHeight(25.0);
            clientBox.setStyle("-fx-border-color: #9a394f; -fx-background-color: white; -fx-alignment: center");
            clientBox.setId("position" + (i + 1));
            this.clientBoxs.getChildren().add(clientBox);
            this.clientContainer.put(i + 1, clientBox);
         }
      } else {
         for(i = this.etapeIG.getLargeur() / 20; i > 0; --i) {
            clientBox = new HBox();
            clientBox.setPrefWidth(20.0);
            clientBox.setMaxHeight(25.0);
            clientBox.setStyle("-fx-border-color: #9a394f; -fx-background-color: white; -fx-alignment: center");
            clientBox.setId("position" + (i - 1));
            this.clientBoxs.getChildren().add(clientBox);
            this.clientContainer.put(i, clientBox);
         }
      }

   }

   public void reagir() {
   }

   public void dragndrop(MouseEvent mouseEvent) {
      super.dragndrop(mouseEvent);
   }

   public void select(MouseEvent mouseEvent) {
      super.select(mouseEvent);
   }

   public void contextMenuRequested(ContextMenuEvent contextMenuEvent) {
      super.contextMenuRequested(contextMenuEvent);
   }
}
