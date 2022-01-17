package twisk.controllers;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public class ControllerClient extends Controller implements Initializable {
   @FXML
   private Circle clientIG;
   private double[] rgb;
   private double width;
   private double height;

   public ControllerClient(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, double[] rgb) {
      super(monde, gestionnaireGraphique);
      this.rgb = rgb;
   }

   public void setPosition(VBox obsEtape) {
      this.width = obsEtape.getPrefWidth();
      this.height = obsEtape.getPrefHeight();
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.clientIG.setFill(Color.color(this.rgb[0], this.rgb[1], this.rgb[2]));
      if (this.width != 0.0 && this.height != 0.0) {
         Random rand = new Random();
         this.clientIG.setCenterX((double)rand.nextInt((int)(this.width - this.clientIG.getRadius() * 2.0 + 1.0)) + this.clientIG.getRadius());
         this.clientIG.setCenterY((double)rand.nextInt((int)(this.height - 30.0 - this.clientIG.getRadius() * 2.0 + 1.0)) + this.clientIG.getRadius() * 2.0);
      }

   }

   public void reagir() {
   }
}
