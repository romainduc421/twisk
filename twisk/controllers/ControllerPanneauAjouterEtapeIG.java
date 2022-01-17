package twisk.controllers;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import twisk.Main;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerPanneauAjouterEtapeIG extends Controller implements Initializable {
   @FXML
   private TextField nameActivite;
   @FXML
   private TextField delai;
   @FXML
   private TextField ecartTemps;
   @FXML
   private Button addButtonActivite;
   @FXML
   private TextField nameGuichet;
   @FXML
   private TextField nbJetons;
   @FXML
   private Button addButtonGuichet;
   @FXML
   private HBox hNomActivite;
   @FXML
   private HBox hDelai;
   @FXML
   private HBox hEcartTemps;
   @FXML
   private HBox hButtonActivite;
   @FXML
   private HBox hSeparateur;
   @FXML
   private HBox hNomGuichet;
   @FXML
   private HBox hNbJetons;
   @FXML
   private HBox hButtonGuichet;

   public ControllerPanneauAjouterEtapeIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      Image img = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/add.png")));
      Image img2 = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/guich.png")));
      ImageView imgViewActivite = new ImageView(img);
      ImageView imgViewGuichet = new ImageView(img2);
      imgViewActivite.setFitHeight(40.0);
      imgViewActivite.setFitWidth(40.0);
      imgViewGuichet.setFitHeight(40.0);
      imgViewGuichet.setFitWidth(40.0);
      this.addButtonActivite.setGraphic(imgViewActivite);
      this.addButtonGuichet.setGraphic(imgViewGuichet);
      this.addButtonActivite.setStyle("-fx-background-color: transparent");
      this.addButtonGuichet.setStyle("-fx-background-color: transparent");
      VBox.setMargin(this.hNomActivite, new Insets(20.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hDelai, new Insets(10.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hEcartTemps, new Insets(10.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hButtonActivite, new Insets(30.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hSeparateur, new Insets(10.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hNomGuichet, new Insets(20.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hNbJetons, new Insets(10.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hButtonGuichet, new Insets(30.0D, 0.0D, 10.0D, 0.0D));
   }

   public void reagir() {
   }

   public void addActivite(ActionEvent actionEvent) {
      if (!this.nameActivite.getText().isEmpty() && this.nameActivite.getText() != null && !this.nameActivite.getText().isBlank()) {
         if (!this.delai.getText().isEmpty() && this.delai.getText() != null && !this.delai.getText().isBlank()) {
            if (!this.ecartTemps.getText().isEmpty() && this.ecartTemps.getText() != null && !this.ecartTemps.getText().isBlank()) {
               if (this.ecartTemps.getText().matches("[1-9]+")) {
                  if (this.delai.getText().matches("[1-9]+")) {
                     this.w.ajouter("Activité", this.nameActivite.getText(), Integer.parseInt(this.delai.getText()), Integer.parseInt(this.ecartTemps.getText()));
                     this.w.Unselect();
                  } else {
                     this.w.ajouter("Activité", this.nameActivite.getText(), 1, Integer.parseInt(this.ecartTemps.getText()));
                     this.w.Unselect();
                  }
               } else if (this.delai.getText().matches("[1-9]+")) {
                  this.w.ajouter("Activité", this.nameActivite.getText(), Integer.parseInt(this.delai.getText()), 1);
               } else {
                  this.w.ajouter("Activité", this.nameActivite.getText(), 1, 1);
                  this.w.Unselect();
               }
            } else if (this.delai.getText().matches("[1-9]+")) {
               this.w.ajouter("Activité", this.nameActivite.getText(), Integer.parseInt(this.delai.getText()), 1);
               this.w.Unselect();
            } else {
               this.w.ajouter("Activité", this.nameActivite.getText(), 1, 1);
               this.w.Unselect();
            }
         } else if (!this.ecartTemps.getText().isEmpty() && this.ecartTemps.getText() != null && !this.ecartTemps.getText().isBlank()) {
            if (this.ecartTemps.getText().matches("[1-9]+")) {
               this.w.ajouter("Activité", this.nameActivite.getText(), 1, Integer.parseInt(this.ecartTemps.getText()));
               this.w.Unselect();
            } else {
               this.w.ajouter("Activité", this.nameActivite.getText(), 1, 1);
               this.w.Unselect();
            }
         } else {
            this.w.ajouter("Activité", this.nameActivite.getText(), 1, 1);
            this.w.Unselect();
         }
      } else if (!this.delai.getText().isEmpty() && this.delai.getText() != null && !this.delai.getText().isBlank()) {
         if (!this.ecartTemps.getText().isEmpty() && this.ecartTemps.getText() != null && !this.ecartTemps.getText().isBlank()) {
            if (this.delai.getText().matches("[1-9]+")) {
               if (this.ecartTemps.getText().matches("[1-9]+")) {
                  this.w.ajouter("Activité", "Activite", Integer.parseInt(this.delai.getText()), Integer.parseInt(this.ecartTemps.getText()));
                  this.w.Unselect();
               } else {
                  this.w.ajouter("Activité", "Activite", Integer.parseInt(this.delai.getText()), 1);
                  this.w.Unselect();
               }
            } else if (this.ecartTemps.getText().matches("[1-9]+")) {
               this.w.ajouter("Activité", "Activite", 1, Integer.parseInt(this.ecartTemps.getText()));
               this.w.Unselect();
            } else {
               this.w.ajouter("Activite");
               this.w.Unselect();
            }
         } else if (this.delai.getText().matches("[1-9]+")) {
            this.w.ajouter("Activité", "Activite", Integer.parseInt(this.delai.getText()), 1);
            this.w.Unselect();
         } else {
            this.w.ajouter("Activité");
            this.w.Unselect();
         }
      } else if (!this.ecartTemps.getText().isEmpty() && this.ecartTemps.getText() != null && !this.ecartTemps.getText().isBlank()) {
         if (this.ecartTemps.getText().matches("[1-9]+")) {
            this.w.ajouter("Activité", "Activite", 1, Integer.parseInt(this.ecartTemps.getText()));
            this.w.Unselect();
         } else {
            this.w.ajouter("Activite");
            this.w.Unselect();
         }
      } else {
         this.w.ajouter("Activite");
         this.w.Unselect();
      }

   }

   public void addGuichet(ActionEvent actionEvent) {
      if (!this.nameGuichet.getText().isEmpty() && this.nameGuichet.getText() != null && !this.nameGuichet.getText().isBlank()) {
         if (!this.nbJetons.getText().isEmpty() && this.nbJetons.getText() != null && !this.nbJetons.getText().isBlank()) {
            if (this.nbJetons.getText().matches("[1-9]+")) {
               this.w.ajouter("Guichet", this.nameGuichet.getText(), Integer.parseInt(this.nbJetons.getText()), 0);
            } else {
               this.w.ajouter("Guichet", this.nameGuichet.getText(), 1, 0);
            }
         } else {
            this.w.ajouter("Guichet", this.nameGuichet.getText(), 1, 0);
         }
      } else if (!this.nbJetons.getText().isEmpty() && this.nbJetons.getText() != null && !this.nbJetons.getText().isBlank()) {
         if (this.nbJetons.getText().matches("[1-9]+")) {
            this.w.ajouter("Guichet", "Guichet", Integer.parseInt(this.nbJetons.getText()), 0);
         } else {
            this.w.ajouter("Guichet");
         }
      } else {
         this.w.ajouter("Guichet");
      }
   }
}
