package twisk.controllers;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.function.Consumer;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
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

public class ControllerDelaiGaussienne implements Initializable {
   MondeIG monde;
   GestionnaireGraphique gestionnaireGraphique;
   @FXML
   private Dialog dialog;
   @FXML
   private TextField moyenne;
   @FXML
   private TextField ecartype;
   @FXML
   private ButtonType confirm;
   @FXML
   private ButtonType cancel;

   public ControllerDelaiGaussienne(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      this.monde = monde;
      this.gestionnaireGraphique = gestionnaireGraphique;
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.dialog.setResultConverter(new Callback<ButtonType,Pair<String,String>>() {
         public Pair<String, String> call(ButtonType dialogButton) {
            String res1 = null;
            String res2 = null;
            if (dialogButton == ControllerDelaiGaussienne.this.confirm) {
               res1 = ControllerDelaiGaussienne.this.moyenne.getText();
               res2 = ControllerDelaiGaussienne.this.ecartype.getText();
            } else if (dialogButton == ControllerDelaiGaussienne.this.cancel) {
               ControllerDelaiGaussienne.this.dialog.close();
               return null;
            }

            return new Pair(res1, res2);
         }

      });
      Optional<Pair<String, String>> result = this.dialog.showAndWait();
      result.ifPresent(new Consumer<Pair<String,String>>() {
         public void accept(Pair<String, String> stringStringPair) {
            if (ControllerDelaiGaussienne.this.moyenne.getText().matches("\\d+") && ControllerDelaiGaussienne.this.ecartype.getText().matches("\\d+")) {
               if (ControllerDelaiGaussienne.this.moyenne != null) {
                  if (ControllerDelaiGaussienne.this.ecartype != null) {
                     ControllerDelaiGaussienne.this.monde.setEcartsTemps(Integer.parseInt(ControllerDelaiGaussienne.this.ecartype.getText()));
                  }

                  ControllerDelaiGaussienne.this.monde.setDelai(Integer.parseInt(ControllerDelaiGaussienne.this.moyenne.getText()));
               } else if (ControllerDelaiGaussienne.this.ecartype != null) {
                  ControllerDelaiGaussienne.this.monde.setEcartsTemps(Integer.parseInt(ControllerDelaiGaussienne.this.ecartype.getText()));
               }
            } else {
               Alert alert = new Alert(AlertType.WARNING);
               alert.setContentText("Veuillez ne mettre que des entiers !");
               alert.setHeaderText(null);
               alert.setGraphic(null);
               PauseTransition pause = new PauseTransition(Duration.seconds(5.0D));
               alert.show();
               pause.setOnFinished((event) -> {
                  alert.close();
               });
               pause.play();
            }

            ControllerDelaiGaussienne.this.monde.Unselect();
         }
      });
   }
}
