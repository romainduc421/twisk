package twisk.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.util.Callback;
import javafx.util.Duration;
import javafx.util.Pair;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerDelaiUniforme implements Initializable {
   MondeIG monde;
   GestionnaireGraphique gestionnaireGraphique;
   @FXML
   private Dialog dialog;
   @FXML
   private TextField delai;
   @FXML
   private TextField ecartTemps;
   @FXML
   private ButtonType confirm;
   @FXML
   private ButtonType cancel;

   public ControllerDelaiUniforme(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      this.monde = monde;
      this.gestionnaireGraphique = gestionnaireGraphique;
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      dialog.setResultConverter(new Callback<ButtonType, Pair<String,String>>() {
         public Pair<String, String> call(ButtonType dialogButton) {
            String res1 = null;
            String res2 = null;
            if (dialogButton != ControllerDelaiUniforme.this.confirm) {
               if (dialogButton == ControllerDelaiUniforme.this.cancel) {
                  ControllerDelaiUniforme.this.dialog.close();
                  return null;
               }
            } else {
               res1 = ControllerDelaiUniforme.this.delai.getText();
               res2 = ControllerDelaiUniforme.this.ecartTemps.getText();
            }

            return new Pair(res1, res2);
         }
      });
      Optional<Pair<String, String>> result = dialog.showAndWait();
      result.ifPresent(new Consumer<Pair<String,String>>() {
         public void accept(Pair<String, String> stringStringPair) {
            if (ControllerDelaiUniforme.this.delai.getText().matches("\\d+") && ControllerDelaiUniforme.this.ecartTemps.getText().matches("\\d+")) {
               if (ControllerDelaiUniforme.this.delai != null) {
                  if (ControllerDelaiUniforme.this.ecartTemps != null) {
                     ControllerDelaiUniforme.this.monde.setEcartsTemps(Integer.parseInt(ControllerDelaiUniforme.this.ecartTemps.getText()));
                  }

                  ControllerDelaiUniforme.this.monde.setDelai(Integer.parseInt(ControllerDelaiUniforme.this.delai.getText()));
               } else if (ControllerDelaiUniforme.this.ecartTemps != null) {
                  ControllerDelaiUniforme.this.monde.setEcartsTemps(Integer.parseInt(ControllerDelaiUniforme.this.ecartTemps.getText()));
               }
            } else {
               Alert alert = new Alert(AlertType.WARNING);
               alert.setContentText("Please put only Integers on that line !");
               alert.setHeaderText((String)null);
               alert.setGraphic((Node)null);
               PauseTransition pause = new PauseTransition(Duration.seconds(5.0D));
               alert.show();
               pause.setOnFinished((event) -> {
                  alert.close();
               });
               pause.play();
            }

            ControllerDelaiUniforme.this.monde.Unselect();
         }
      });
   }
}
