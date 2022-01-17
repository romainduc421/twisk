package twisk.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerParametreGuichet extends Controller implements Initializable {
   @FXML
   private TextInputDialog dialog;
   @FXML
   private TextField nbJetons;
   @FXML
   private ButtonType confirm;
   @FXML
   private ButtonType cancel;

   public ControllerParametreGuichet(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
   }

   public void reagir() {
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      Optional<String> result = this.dialog.showAndWait();
      result.ifPresent((event) -> {
         this.w.setNbJetons(this.nbJetons.getText());
      });
      this.w.Unselect();
   }
}
