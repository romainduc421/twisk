package twisk.controllers;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerNavigateur extends Controller {
   @FXML
   private Button navButtonAccueil;
   @FXML
   private Button navButtonAjouterEtape;
   @FXML
   private Button navButtonInformations;

   public ControllerNavigateur(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
      this.w.ajouterController(this);
   }

   public void reagir() {
      this.navButtonAjouterEtape.setDisable(this.w.isSimulationActive());
   }

   public void accueil(ActionEvent actionEvent) {
      this.gestionnaireGraphique.setAccueilPressed(true);
   }

   public void ajouterEtape(ActionEvent actionEvent) {
      this.gestionnaireGraphique.setAjouterEtapeIGPressed(true);
   }

   public void informations(ActionEvent actionEvent) {
      this.gestionnaireGraphique.setInformationsPressed(true);
   }

   public void mouseEnteredAccueil(MouseEvent mouseEvent) {
      this.navButtonAccueil.setStyle("-fx-background-color: #6d7b92; -fx-text-fill: white");
   }

   public void mouseExitAccueil(MouseEvent mouseEvent) {
      this.navButtonAccueil.setStyle("-fx-background-color: #d02985; -fx-text-fill: white");
   }

   public void mouseEnteredAjouterEtape(MouseEvent mouseEvent) {
      this.navButtonAjouterEtape.setStyle("-fx-background-color: #6d7b92; -fx-text-fill: white");
   }

   public void mouseExitAjouterEtape(MouseEvent mouseEvent) {
      this.navButtonAjouterEtape.setStyle("-fx-background-color: #d02985; -fx-text-fill: white");
   }

   public void mouseEnteredInformations(MouseEvent mouseEvent) {
      this.navButtonInformations.setStyle("-fx-background-color: #6d7b92; -fx-text-fill: white");
   }

   public void mouseExitInformations(MouseEvent mouseEvent) {
      this.navButtonInformations.setStyle("-fx-background-color: #d02985; -fx-text-fill: white");
   }
}
