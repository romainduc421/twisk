package twisk.controllers;
import java.net.URL;
import java.util.Iterator;
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
import twisk.exceptions.SetTpsEcartException;
import twisk.exceptions.TwiskException;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class ControllerParametreActivite extends Controller implements Initializable {
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

   public ControllerParametreActivite(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
   }

   public void reagir() {
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.dialog.setResultConverter(new Callback<ButtonType,Pair<String,String>>() {
         public Pair<String, String> call(ButtonType dialogButton) {
            String res1 = null;
            String res2 = null;
            if (dialogButton == ControllerParametreActivite.this.confirm) {
               res1 = ControllerParametreActivite.this.delai.getText();
               res2 = ControllerParametreActivite.this.ecartTemps.getText();
            } else if (dialogButton == ControllerParametreActivite.this.cancel) {
               ControllerParametreActivite.this.dialog.close();
               return null;
            }
            return new Pair(res1, res2);
         }
      });
      Optional<Pair<String,String>> result = this.dialog.showAndWait();
      result.ifPresent(new Consumer<Pair<String,String>>() {
         public void accept(Pair<String,String> stringStringPair) {
            Iterator<EtapeIG> var2;
            EtapeIG e;
            ActiviteIG act;
            Alert alertx;
            PauseTransition pausex;
            if (ControllerParametreActivite.this.delai == null) {
               if (ControllerParametreActivite.this.ecartTemps != null) {
                  var2 = ControllerParametreActivite.this.w.getSelectedSteps().iterator();

                  while(var2.hasNext()) {
                     e = var2.next();
                     if (e.getClass().getSimpleName().equals("ActiviteIG")) {
                        act = (ActiviteIG)e;

                        try {
                           ControllerParametreActivite.this.w.setDelaiEcartTemps(act.getDelai(), ControllerParametreActivite.this.ecartTemps.getText());
                        } catch ( TwiskException excp) {
                           alertx = new Alert(AlertType.WARNING);
                           alertx.setContentText(excp.getMessage());
                           alertx.setHeaderText(null);
                           alertx.setGraphic(null);
                           pausex = new PauseTransition(Duration.seconds(5.0D));
                           alertx.show();
                           Alert finalAlertx = alertx;
                           pausex.setOnFinished((even) -> {
                              finalAlertx.close();
                           });
                           pausex.play();
                        }
                     }
                  }
               }
            } else if (ControllerParametreActivite.this.ecartTemps == null) {
               var2 = ControllerParametreActivite.this.w.getSelectedSteps().iterator();

               while(var2.hasNext()) {
                  e = var2.next();
                  if (e.getClass().getSimpleName().equals("ActiviteIG")) {
                     act = (ActiviteIG)e;

                     try {
                        ControllerParametreActivite.this.w.setDelaiEcartTemps(ControllerParametreActivite.this.delai.getText(), act.getEcartTps());
                     } catch (TwiskException excp2) {
                        alertx = new Alert(AlertType.WARNING);
                        alertx.setContentText(excp2.getMessage());
                        alertx.setHeaderText(null);
                        alertx.setGraphic(null);
                        pausex = new PauseTransition(Duration.seconds(5.0));
                        alertx.show();
                        Alert finalAlertx1 = alertx;
                        pausex.setOnFinished((event) -> {
                           finalAlertx1.close();
                        });
                        pausex.play();
                     }
                  }
               }
            } else {
               try {
                  ControllerParametreActivite.this.w.setDelaiEcartTemps(ControllerParametreActivite.this.delai.getText(), ControllerParametreActivite.this.ecartTemps.getText());
               } catch (TwiskException excp3) {
                  Alert alert = new Alert(AlertType.WARNING);
                  alert.setContentText(excp3.getMessage());
                  alert.setHeaderText(null);
                  alert.setGraphic(null);
                  PauseTransition pause = new PauseTransition(Duration.seconds(5.0));
                  alert.show();
                  pause.setOnFinished((event) -> {
                     alert.close();
                  });
                  pause.play();
               }
            }

            ControllerParametreActivite.this.w.Unselect();
         }

      });
   }
}
