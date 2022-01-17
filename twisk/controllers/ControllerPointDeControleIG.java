package twisk.controllers;
import java.net.URL;
import java.util.Iterator;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Point2D;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import twisk.exceptions.MondeException;
import twisk.exceptions.TwiskException;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;

public class ControllerPointDeControleIG extends Controller implements Initializable {
   @FXML
   private Circle point;
   private Point2D pointCliquer;
   private boolean pressed;

   public ControllerPointDeControleIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.pressed = false;
   }

   public void reagir() {
   }

   public void select(MouseEvent mouseEvent) {
      if (this.pressed) {
         this.point.setFill(Color.BLUE);
      } else {
         this.point.setFill(Color.RED);
      }

      this.pointCliquer = new Point2D(mouseEvent.getX(), mouseEvent.getY());
      Iterator<EtapeIG> var2 = this.w.iterator();

      for(EtapeIG etape : this.w)
      {
         for(PointDeControleIG point : etape)
         {
            double d2 = (this.pointCliquer.getX() - point.getAbs_centre()) * (this.pointCliquer.getX() - point.getAbs_centre()) + (this.pointCliquer.getY() - point.getOrd_centre()) * (this.pointCliquer.getY() - point.getOrd_centre());
            if (d2 < this.point.getRadius() * this.point.getRadius())
            {
               try{
                  this.w.selectedControlpoint(point);
               } catch (MondeException | TwiskException var11) {
                  Alert alert = new Alert(AlertType.WARNING);
                  alert.setContentText(var11.getMessage());
                  alert.setHeaderText(null);
                  alert.setGraphic(null);
                  PauseTransition pause = new PauseTransition(Duration.seconds(5.0D));
                  alert.show();
                  pause.setOnFinished((event) -> {
                     alert.close();
                  });
                  pause.play();
                  this.w.Unselect();
               }
            }
         }
      }

      this.pressed = !this.pressed;
   }
}
