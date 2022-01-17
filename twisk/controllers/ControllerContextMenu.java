package twisk.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Dialog;
import javafx.scene.control.MenuItem;
import twisk.Main;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class ControllerContextMenu extends ControllerMenu implements Initializable {
   @FXML
   private ContextMenu contextMenu;
   @FXML
   private MenuItem rename;
   @FXML
   private MenuItem entree;
   @FXML
   private MenuItem sortie;
   @FXML
   private MenuItem modify;
   private EtapeIG etapeIG;

   public ControllerContextMenu(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, EtapeIG etapeIG) {
      super(monde, gestionnaireGraphique, false);
      this.etapeIG = etapeIG;
   }

   public ContextMenu getContextMenu() {
      return this.contextMenu;
   }

   public void reagir() {
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      if (this.etapeIG.estActivite()) {
         this.modify.setText("Modify delay-timeGap");
      } else {
         this.modify.setText("Modify tokens");
         this.sortie.setStyle("-fx-text-fill: grey");
      }

   }

   public void modify(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() == 1) {
         FXMLLoader loader;
         Dialog dialog;
         if (!((EtapeIG)this.w.getSelectedSteps().get(0)).estActivite()) {
            ControllerParametreGuichet cpg = new ControllerParametreGuichet(this.w, this.gestionnaireGraphique);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ui/ObservateurParametreGuichet.fxml"));
            loader.setControllerFactory((ic) -> {
               return cpg;
            });

            try {
               dialog = (Dialog)loader.load();
            } catch (Exception excp) {
               excp.printStackTrace();
            }
         } else {
            ControllerParametreActivite cpa = new ControllerParametreActivite(this.w, this.gestionnaireGraphique);
            loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ui/ObservateurParametreActivite.fxml"));
            loader.setControllerFactory((ic) -> {
               return cpa;
            });

            try {
               dialog = (Dialog)loader.load();
            } catch (Exception excp2) {
               excp2.printStackTrace();
            }
         }
      }

   }
}
