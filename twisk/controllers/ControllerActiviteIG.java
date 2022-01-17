package twisk.controllers;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class ControllerActiviteIG extends ControllerEtapeIG {
   @FXML
   private ImageView imgView;
   @FXML
   private Pane clientContainer;
   private String delai;
   private String ecartTemps;

   public ControllerActiviteIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, EtapeIG etapeIG, String nom, String delai, String ecartTemps) {
      super(monde, gestionnaireGraphique, etapeIG, nom);
      this.delai = delai;
      this.ecartTemps = ecartTemps;
   }

   public Pane getClientContainer() {
      return this.clientContainer;
   }

   public void dragndrop(MouseEvent mouseEvent) {
      super.dragndrop(mouseEvent);
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      super.initialize(url, resourceBundle);
      this.description.setText(this.nom + " : " + this.delai + " ± " + this.ecartTemps);
      ActiviteIG act = (ActiviteIG)this.etapeIG;
      if (act.isEntry()) {
         if (act.isExit()) {
            this.imgView = new ImageView(this.gestionnaireGraphique.getImgEntreeSortie());
            this.imgView.setFitHeight(20.0);
            this.imgView.setFitWidth(20.0);
            this.description.setGraphic(this.imgView);
         }
      } else if (act.isExit()) {
         ImageView imgViewSortie = new ImageView(this.gestionnaireGraphique.getImgSortie());
         imgViewSortie.setFitHeight(20.0);
         imgViewSortie.setFitWidth(20.0);
         this.description.setGraphic(imgViewSortie);
      }

   }

   public void select(MouseEvent mouseEvent) {
      super.select(mouseEvent);
   }

   public void contextMenuRequested(ContextMenuEvent contextMenuEvent) {
      super.contextMenuRequested(contextMenuEvent);
   }
}
