package twisk.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class ControllerApplication extends Controller implements Initializable {
   @FXML
   private AnchorPane root;
   @FXML
   private Pane obsMondeIG;
   @FXML
   private HBox obsMenu;
   @FXML
   private VBox obsNavigateur;
   @FXML
   private VBox obsPanneauAjouterEtapeIG;
   @FXML
   private VBox obsPanneauAccueil;
   @FXML
   private VBox obsPanneauInformations;

   public ControllerApplication(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
      this.w.ajouterController(this);
   }

   public void reagir() {
      if (this.gestionnaireGraphique.isAccueilPressed()) {
         this.obsPanneauAccueil.setVisible(true);
         this.obsPanneauAjouterEtapeIG.setVisible(false);
         this.obsPanneauInformations.setVisible(false);
      } else if (this.gestionnaireGraphique.isAjouterEtapeIGPressed()) {
         this.obsPanneauAccueil.setVisible(false);
         this.obsPanneauAjouterEtapeIG.setVisible(true);
         this.obsPanneauInformations.setVisible(false);
      } else if (this.gestionnaireGraphique.isInformationsPressed()) {
         this.obsPanneauAccueil.setVisible(false);
         this.obsPanneauAjouterEtapeIG.setVisible(false);
         this.obsPanneauInformations.setVisible(true);
      }

   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      if (this.gestionnaireGraphique.isAccueilPressed()) {
         this.obsPanneauAccueil.setVisible(true);
         this.obsPanneauAjouterEtapeIG.setVisible(false);
         this.obsPanneauInformations.setVisible(false);
      } else if (this.gestionnaireGraphique.isAjouterEtapeIGPressed()) {
         this.obsPanneauAccueil.setVisible(false);
         this.obsPanneauAjouterEtapeIG.setVisible(true);
         this.obsPanneauInformations.setVisible(false);
      } else if (this.gestionnaireGraphique.isInformationsPressed()) {
         this.obsPanneauAccueil.setVisible(false);
         this.obsPanneauAjouterEtapeIG.setVisible(false);
         this.obsPanneauInformations.setVisible(true);
      }

      this.obsMondeIG.prefWidthProperty().bind(this.root.widthProperty().subtract(TailleComposants.getInstance().getNavigateurWidth() + TailleComposants.getInstance().getPanneauWidth()));
      this.obsMondeIG.prefHeightProperty().bind(this.root.heightProperty());
      AnchorPane.setTopAnchor(this.obsMondeIG, this.obsMenu.getPrefHeight());
      AnchorPane.setLeftAnchor(this.obsMondeIG, TailleComposants.getInstance().getNavigateurWidth() + this.obsPanneauAjouterEtapeIG.getPrefWidth());
      this.obsMenu.setPadding(new Insets(0.0, 0.0, 0.0, TailleComposants.getInstance().getPanneauWidth()));
      this.root.widthProperty().addListener((observable, oldValue, newValue) -> {
         if (oldValue.doubleValue() != 0.0)
            this.w.Unselect();

      });
      this.root.heightProperty().addListener((observable, oldValue, newValue) -> {
         if (oldValue.doubleValue() != 0.0)
            this.w.Unselect();
      });
   }
}
