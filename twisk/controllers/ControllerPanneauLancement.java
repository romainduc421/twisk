package twisk.controllers;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import twisk.Main;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;
import twisk.outils.GestionnaireThreads;

public class ControllerPanneauLancement extends Controller implements Initializable {
   @FXML
   private Button launchButton;
   @FXML
   private Button pauseButton;
   @FXML
   private Button stopButton;
   @FXML
   private HBox hName;
   @FXML
   private HBox hButtonLancement;
   @FXML
   private HBox hBoucles;
   @FXML
   private CheckBox boucles;
   @FXML
   private Label loiTitle;
   @FXML
   private Label loi;
   @FXML
   private TextField nbClients;
   private Image imgLaunch;
   private Image imgLaunchHover;
   private Image imgPause;
   private Image imgStop;
   private ImageView imgViewLaunch;
   private ImageView imgViewPause;
   private ImageView imgViewStop;

   public ControllerPanneauLancement(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
      this.w.ajouterController(this);
   }

   public void reagir() {
      if (!this.w.isSimulationActive()) {
         this.launchButton.setDisable(false);
         this.pauseButton.setDisable(true);
         this.stopButton.setDisable(true);
      } else {
         this.launchButton.setDisable(true);
         this.pauseButton.setDisable(true);
         this.stopButton.setDisable(false);
      }

      Platform.runLater(() -> {
         Label var10000;
         int var10001;
         if (ControllerPanneauLancement.this.w.isUniforme()) {
            ControllerPanneauLancement.this.loiTitle.setText("Uniform : ");
            var10000 = ControllerPanneauLancement.this.loi;
            var10001 = ControllerPanneauLancement.this.w.getDelai();
            var10000.setText("( " + var10001 + " ; " + ControllerPanneauLancement.this.w.getEcartsTemps() + " )");
         } else if (ControllerPanneauLancement.this.w.isGaussienne()) {
            ControllerPanneauLancement.this.loiTitle.setText("gaussianDistribution : ");
            var10000 = ControllerPanneauLancement.this.loi;
            var10001 = ControllerPanneauLancement.this.w.getDelai();
            var10000.setText("( " + var10001 + " ; " + ControllerPanneauLancement.this.w.getEcartsTemps() + " )");
         } else if (ControllerPanneauLancement.this.w.isPoisson()) {
            ControllerPanneauLancement.this.loiTitle.setText("Poisson's law : ");
            ControllerPanneauLancement.this.loi.setText("( " + ControllerPanneauLancement.this.w.getDelai() + " )");
         }

      });
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.imgLaunch = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/play.png")),90,90,false,false);
      this.imgLaunchHover = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/launchHover.png")),90,90,false,false);
      this.imgPause = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/pause.png")));
      this.imgStop = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("ressources/images/stop.png")));
      this.imgViewLaunch = new ImageView(this.imgLaunch);
      this.imgViewPause = new ImageView(this.imgPause);
      this.imgViewStop = new ImageView(this.imgStop);
      this.launchButton.setGraphic(this.imgViewLaunch);
      this.pauseButton.setGraphic(this.imgViewPause);
      this.stopButton.setGraphic(this.imgViewStop);
      this.launchButton.setStyle("-fx-background-color: transparent");
      this.pauseButton.setStyle("-fx-background-color: transparent");
      this.stopButton.setStyle("-fx-background-color: transparent");
      this.pauseButton.setDisable(true);
      this.stopButton.setDisable(true);
      VBox.setMargin(this.hName, new Insets(50.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hBoucles, new Insets(20.0D, 0.0D, 10.0D, 0.0D));
      VBox.setMargin(this.hButtonLancement, new Insets(250.0D, 0.0D, 10.0D, 0.0D));
      this.nbClients.setText("5");
      if (!this.w.isSimulationActive()) {
         this.launchButton.setDisable(false);
         this.pauseButton.setDisable(true);
         this.stopButton.setDisable(true);
      } else {
         this.launchButton.setDisable(true);
         this.pauseButton.setDisable(true);
         this.stopButton.setDisable(false);
      }

      this.boucles.selectedProperty().addListener((observableValue, aBoolean, t1) -> {
         this.w.setAllowedLoops(t1);
      });
   }

   public void simuler(ActionEvent actionEvent) {
      if (this.nbClients.getText().matches("[0-9]+") && !this.nbClients.getText().isBlank() && !this.nbClients.getText().isEmpty()) {
         this.w.setNbClients(Integer.parseInt(this.nbClients.getText()));
         if (!this.w.isSimulationActive()) {
            try {
               this.w.simuler();
            } catch (Exception var5) {
               this.w.setSimulationActive(false);
               Alert alert = new Alert(AlertType.WARNING);
               alert.setContentText(var5.getMessage());
               alert.setHeaderText(null);
               alert.setGraphic(null);
               alert.getDialogPane().setMinWidth(500.0);
               PauseTransition pause = new PauseTransition(Duration.seconds(5.0));
               alert.show();
               pause.setOnFinished((event) -> {
                  alert.close();
               });
               pause.play();
            }
         }
      } else {
         Alert alert = new Alert(AlertType.WARNING);
         alert.setContentText("Please set up customers' number.\n");
         alert.setHeaderText(null);
         alert.setGraphic(null);
         PauseTransition pause = new PauseTransition(Duration.seconds(5.0D));
         alert.show();
         pause.setOnFinished((event) -> {
            alert.close();
         });
         pause.play();
      }

   }

   public void pauseSimulation(ActionEvent actionEvent) {
   }

   public void stopSimulation(ActionEvent actionEvent) {
      GestionnaireThreads.getInstance().detruireTout();
   }

   public void mouseEnteredLaunch(MouseEvent mouseEvent) {
      this.imgViewLaunch.setImage(this.imgLaunchHover);
   }

   public void mouseExitedLaunch(MouseEvent mouseEvent) {
      this.imgViewLaunch.setImage(this.imgLaunch);
   }

   public void mouseEnteredPause(MouseEvent mouseEvent) {
   }

   public void mouseExitedPause(MouseEvent mouseEvent) {
   }

   public void mouseEnteredStop(MouseEvent mouseEvent) {
   }

   public void mouseExitedStop(MouseEvent mouseEvent) {
   }
}
