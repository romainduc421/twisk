package twisk.controllers;
import java.io.Serializable;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polyline;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.MondeIG;

import static java.lang.Math.*;

public class ControllerArcIG extends Controller implements Initializable {
   private ArcIG arc;
   @FXML
   private Line connection;
   @FXML
   private Polyline arrow;
   @FXML
   private Pane arcGraphique;

   public ControllerArcIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, ArcIG arc) {
      super(monde, gestionnaireGraphique);
      this.arc = arc;
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.connection = new Line(this.arc.getSrc().getAbs_centre(), this.arc.getSrc().getOrd_centre(), this.arc.getDest().getAbs_centre(), this.arc.getDest().getOrd_centre());
      double angle = atan2(this.arc.getDest().getOrd_centre() - this.arc.getSrc().getOrd_centre(), this.arc.getDest().getAbs_centre() - this.arc.getSrc().getAbs_centre()) - PI/2;
      double sin = sin(angle);
      double cos = cos(angle);
      double x1 = (-0.5 * cos + sqrt(3.0) / 2.0 * sin) * 15.0 + this.arc.getDest().getAbs_centre();
      double y1 = (-0.5 * sin - sqrt(3.0) / 2.0 * cos) * 15.0 + this.arc.getDest().getOrd_centre();
      double x2 = (0.5 * cos + sqrt(3.0) / 2.0 * sin) * 15.0 + this.arc.getDest().getAbs_centre();
      double y2 = (0.5 * sin - sqrt(3.0) / 2.0 * cos) * 15.0 + this.arc.getDest().getOrd_centre();
      double x3 = -(0.5 * cos + sqrt(3.0) / 2.0 * sin) * 15.0 + x2;
      double y3 = -(0.5 * sin - sqrt(3.0) / 2.0 * cos) * 15.0 + y2;
      this.arrow = new Polyline(this.arc.getDest().getAbs_centre(), this.arc.getDest().getOrd_centre(), x1, y1, x2, y2, x3, y3);
      this.connection.setStrokeWidth(1.0D);
      this.connection.setStyle("-fx-border-color: red; -fx-stroke-width: 2");
      this.arrow.setFill(Color.BLACK);
      if (this.w.getSelectedBows().contains(this.arc)) {
         this.connection.setStyle("-fx-stroke: blue; -fx-stroke-width: 2");
         this.arrow.setFill(Color.BLUE);
      }

      this.arcGraphique.getChildren().addAll(this.connection, this.arrow);
      this.arcGraphique.setPickOnBounds(false);
      this.arrow.setMouseTransparent(true);
   }

   public void reagir() {
   }

   public void select(MouseEvent mouseEvent) {
      if (!this.connection.getBoundsInLocal().contains(mouseEvent.getX(), mouseEvent.getY())) {
         return;
      }
      this.w.addBowSelected(this.arc);
   }
}
