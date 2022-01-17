package twisk.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.SnapshotParameters;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;
import twisk.components.VBoxEtapeIG;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public abstract class ControllerEtapeIG extends Controller implements Initializable {
   @FXML
   protected VBoxEtapeIG obsEtape;
   @FXML
   protected Label description;
   @FXML
   protected ImageView imgView;
   protected ContextMenu contextMenu;
   protected EtapeIG etapeIG;
   protected String nom;
   private boolean isSelected;

   public ControllerEtapeIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, EtapeIG etape, String nom) {
      super(monde, gestionnaireGraphique);
      this.etapeIG = etape;
      this.nom = nom;
      this.isSelected = false;
   }

   public VBoxEtapeIG getObsEtape() {
      return this.obsEtape;
   }

   public void setContextMenu(ContextMenu contextMenu) {
      this.contextMenu = contextMenu;
   }

   public void applyStyle() {
      DropShadow surbrillance = new DropShadow();
      surbrillance.setColor(Color.BLUE);
      surbrillance.setHeight(50.0D);
      surbrillance.setRadius(10.0D);
      this.obsEtape.setEffect(surbrillance);
      this.description.setTextFill(Color.WHITE);
      this.obsEtape.setStyle(this.obsEtape.getStyle().concat("-fx-background-color : red"));
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      this.obsEtape.setId(this.etapeIG.getId());
      this.obsEtape.setEtapeIG(this.etapeIG);
      if (this.w.getSelectedSteps().contains(this.etapeIG)) {
         this.isSelected = true;
         this.applyStyle();
      }

      if (this.etapeIG.isEntry()) {
         this.imgView = new ImageView(this.gestionnaireGraphique.getImgEntre());
         this.imgView.setFitHeight(20.0);
         this.imgView.setFitWidth(20.0);
         this.description.setGraphic(this.imgView);
      }

      this.obsEtape.setPrefSize((double)this.etapeIG.getLargeur(), (double)this.etapeIG.getHauteur());
   }

   public void dragndrop(MouseEvent mouseEvent) {
      Dragboard dragboard = this.obsEtape.startDragAndDrop(TransferMode.MOVE);
      ClipboardContent content = new ClipboardContent();
      content.putString(this.etapeIG.getId());
      SnapshotParameters param = new SnapshotParameters();
      param.setDepthBuffer(true);
      content.putImage(this.obsEtape.snapshot(param, null));
      dragboard.setContent(content);
      mouseEvent.consume();
   }

   public void select(MouseEvent mouseEvent) {
      if (mouseEvent.getButton() == MouseButton.PRIMARY) {
         this.w.addStepSelected(this.etapeIG);
      }

      mouseEvent.consume();
   }

   public void contextMenuRequested(ContextMenuEvent contextMenuEvent) {
      if (!this.w.isSimulationActive()) {
         if (!this.isSelected) {
            this.w.getSelectedSteps().add(this.etapeIG);
            this.applyStyle();
            this.isSelected = !this.isSelected;
         }

         if (this.w.getSelectedSteps().size() != 1) {
            ((MenuItem)this.contextMenu.getItems().get(1)).setStyle("-fx-text-fill: grey");
            ((MenuItem)this.contextMenu.getItems().get(2)).setStyle("-fx-text-fill: grey");
            ((MenuItem)this.contextMenu.getItems().get(4)).setStyle("-fx-text-fill: grey");
            if (this.w.isContainGuichet()) {
               ((MenuItem)this.contextMenu.getItems().get(3)).setStyle("-fx-text-fill: grey");
            }
         }

         this.contextMenu.show(this.obsEtape, contextMenuEvent.getScreenX(), contextMenuEvent.getScreenY());
      }

      contextMenuEvent.consume();
   }

   public void reagir() {
   }
}
