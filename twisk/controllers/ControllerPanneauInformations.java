package twisk.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerPanneauInformations extends Controller implements Initializable {
   @FXML
   private Label textInformations;

   public ControllerPanneauInformations(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
   }

   public void reagir() {
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.textInformations.setText("- Select a step : Left-click on a step\n" +
     "- Context Menu on a step : Right-click on a step \n\n" +
     "- extend/reduce the app: Place the mouse on a window's border and apply the modification\n" +
     "- Zoom on a step : select one or several steps and after that, use scroll wheel\n" +
     "- Modify zoom's value : Tools -> Zoom\n");
   }
}
