package twisk.controllers;

import java.net.URL;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Random;
import java.util.ResourceBundle;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.ScrollEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import twisk.Main;
import twisk.components.VBoxEtapeIG;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.ActiviteIG;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.GuichetIG;
import twisk.mondeIG.MondeIG;
import twisk.mondeIG.PointDeControleIG;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.simulation.Client;
import twisk.ui.Observateur;

public class ControllerMondeIG extends Controller implements Initializable, Observateur {
   @FXML
   private Pane pane;
   private HashMap<String, ControllerActiviteIG> cactsig = new HashMap<>(10);
   private HashMap<String, ControllerGuichetIG> cgsig = new HashMap<>(10);
   private HashMap<Integer, double[]> colors = new HashMap<>(10);

   public ControllerMondeIG(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
      this.w.ajouterController(this);
      this.w.ajouterObservateur(this);
   }

   public void drawArc(EtapeIG e) {
      Iterator<ArcIG> ite = this.w.iterator_Arcs();

      while(ite.hasNext()) {
         ArcIG arc = ite.next();
         ControllerArcIG caig = new ControllerArcIG(this.w, this.gestionnaireGraphique, arc);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ui/ObservateurArcIG.fxml"));
         loader.setControllerFactory((ic) -> caig);

         try {
            this.pane.getChildren().add(loader.load());
         } catch (Exception ex) {
            ex.printStackTrace();
         }
      }

   }

   public void initCoordonnee(EtapeIG e) {
      if (e.getPosY() == -1.0 && e.getPosX() == -1.0) {
         Random rand = new Random();
         e.setPosX(rand.nextInt((int) this.pane.getWidth() - e.getLargeur() + 1));
         e.setPosY(rand.nextInt((int) this.pane.getHeight() - e.getHauteur() + 1));
      } else {
         if (e.getPosX() < 0.0) {
            e.setPosX(0.0);
         }

         if (e.getPosX() > this.pane.getWidth() - (double)e.getLargeur()) {
            e.setPosX(this.pane.getWidth() - (double)e.getLargeur());
         }

         if (e.getPosY() < 0.0D) {
            e.setPosY(0.0D);
         }

         if (e.getPosY() > this.pane.getHeight() - (double)(e.getHauteur() + 30)) {
            e.setPosY(this.pane.getHeight() - (double)(e.getHauteur() + 30));
         }
      }

   }

   public void drawActivite(EtapeIG e) {
      ControllerActiviteIG cactig = new ControllerActiviteIG(this.w, this.gestionnaireGraphique, e, e.getName(), ((ActiviteIG)e).getDelai(), ((ActiviteIG)e).getEcartTps());
      this.cactsig.put(e.getName(), cactig);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("ui/ObservateurActiviteIG.fxml"));
      loader.setControllerFactory((ic) -> cactig);
      this.drawContextMenu(cactig, e);

      try {
         VBox etape ;
         etape=loader.load();
         etape.setLayoutX(e.getPosX());
         etape.setLayoutY(e.getPosY());
         this.pane.getChildren().add(etape);
      } catch (Exception excp) {
         excp.printStackTrace();
      }

   }

   public void drawGuichet(EtapeIG e) {
      ControllerGuichetIG cgig = new ControllerGuichetIG(this.w, this.gestionnaireGraphique, e, e.getName(), Integer.toString(((GuichetIG)e).getNbJetons()));
      this.cgsig.put(e.getName(), cgig);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("ui/ObservateurGuichetIG.fxml"));
      loader.setControllerFactory((ic) -> cgig);
      this.drawContextMenu(cgig, e);

      try {
         VBox etape = loader.load();
         etape.setLayoutX(e.getPosX());
         etape.setLayoutY(e.getPosY());
         this.pane.getChildren().add(etape);
      } catch (Exception excp) {
         excp.printStackTrace();
      }

   }

   public void drawContextMenu(ControllerEtapeIG ceig, EtapeIG e) {
      ControllerContextMenu ccm = new ControllerContextMenu(this.w, this.gestionnaireGraphique, e);
      FXMLLoader loaderContextMenu = new FXMLLoader();
      loaderContextMenu.setLocation(Main.class.getResource("ui/ObservateurContextMenu.fxml"));
      loaderContextMenu.setControllerFactory((ic) -> ccm);

      try {
         loaderContextMenu.load();
      } catch (Exception excp) {
         excp.printStackTrace();
      }

      ceig.setContextMenu(ccm.getContextMenu());
   }

   public void drawPDC(EtapeIG e) {

      for (PointDeControleIG point : e) {
         ControllerPointDeControleIG cpdcig = new ControllerPointDeControleIG(this.w, this.gestionnaireGraphique);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ui/ObservateurPointDeControleIG.fxml"));
         loader.setControllerFactory((ic) -> cpdcig);

         try {
            Circle pointDeControl ;
            pointDeControl = loader.load();
            pointDeControl.setCenterX(point.getAbs_centre());
            pointDeControl.setCenterY(point.getOrd_centre());
            this.pane.getChildren().add(pointDeControl);
         } catch (Exception excp) {
            excp.printStackTrace();
         }
      }

   }

   public void drawClient() {

      for (Client client : this.w.getClientManager()) {
         if (this.colors.get(client.getCustomer_nb()) == null) {
            double r = Math.random();
            double g = Math.random();
            double b = Math.random();
            double[] rgb = new double[]{r, g, b};
            this.colors.put(client.getCustomer_nb(), rgb);
         }

         if (client.getStep() != null && !client.getStep().estSortie() && !client.getStep().getNom().equals("SasEntree")) {
            ControllerClient cc = new ControllerClient(this.w, this.gestionnaireGraphique, this.colors.get(client.getCustomer_nb()));
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Main.class.getResource("ui/ObservateurClients.fxml"));
            loader.setControllerFactory((ic) -> cc);

            try {
               Circle clientIG;
               if (client.getStep().estUneActivite()) {
                  cc.setPosition(this.cactsig.get(client.getStep().getNom()).getObsEtape());
                  clientIG = loader.load();
                  try {
                     this.cactsig.get(client.getStep().getNom()).getClientContainer().getChildren().add(clientIG);
                  } catch (Exception var10) {
                     var10.printStackTrace();
                  }
               } else {
                  clientIG = loader.load();

                  try {
                     this.cgsig.get(client.getStep().getNom()).getClientContainer(client.getRank()).getChildren().add(clientIG);
                  } catch (NullPointerException excp) {
                  }
               }
            } catch (Exception e) {
               e.printStackTrace();
            }
         }
      }
   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      EtapeIG e;
      for(Iterator<EtapeIG> var3 = this.w.iterator(); var3.hasNext(); this.drawPDC(e)) {
         e = var3.next();
         this.drawArc(e);
         if (e.getPosY() == -1.0 && e.getPosX() == -1.0) {
            Random rand = new Random();
            e.setPosX(rand.nextInt((int) (TailleComposants.getInstance().getApplicationWidth() - TailleComposants.getInstance().getPanneauWidth() - TailleComposants.getInstance().getNavigateurWidth()) - e.getLargeur() + 1));
            e.setPosY(rand.nextInt((int) (TailleComposants.getInstance().getApplicationHeight() - 30.0D) - e.getHauteur() + 1));
         }

         if (e.estActivite()) {
            this.drawActivite(e);
         } else {
            this.drawGuichet(e);
         }
      }

   }

   public void dragOver(DragEvent dragEvent) {
      if (dragEvent.getDragboard().hasString()) {
         dragEvent.acceptTransferModes(TransferMode.MOVE);
      }

      dragEvent.consume();
   }

   public void dragDropped(DragEvent dragEvent) {
      Dragboard dragboard = dragEvent.getDragboard();
      boolean success = false;
      String id = dragboard.getString();
      VBoxEtapeIG etapeMoved = (VBoxEtapeIG)this.pane.lookup("#" + id);
      if (etapeMoved != null) {
         this.pane.getChildren().remove(etapeMoved);
         this.pane.getChildren().add(etapeMoved);
         this.w.relocate(etapeMoved.getEtapeIG(), dragEvent.getX() - etapeMoved.getWidth() / 2.0D, dragEvent.getY() - etapeMoved.getHeight() / 2.0D);
         success = true;
      }

      dragEvent.setDropCompleted(success);
      dragEvent.consume();
      this.w.Unselect();
   }

   public void zoom(ScrollEvent scrollEvent) {

      for (EtapeIG e : this.w.getSelectedSteps()) {
         if (scrollEvent.getDeltaY() > 1.0) {
            if (e.estActivite()) {
               if ((double) e.getLargeur() + TailleComposants.getInstance().getScrollZoom() <= (double) TailleComposants.getInstance().getLargeurMaxActivite()) {
                  e.setLargeur(e.getLargeur() + (int) TailleComposants.getInstance().getScrollZoom());
               }

               if ((double) e.getHauteur() + TailleComposants.getInstance().getScrollZoom() <= (double) TailleComposants.getInstance().getHauteurMaxActivite()) {
                  e.setHauteur(e.getHauteur() + (int) TailleComposants.getInstance().getScrollZoom());
               }
            } else if ((double) e.getLargeur() + TailleComposants.getInstance().getScrollZoom() <= (double) TailleComposants.getInstance().getLargeurMaxGuichet()) {
               e.setLargeur(e.getLargeur() + (int) TailleComposants.getInstance().getScrollZoom());
            }

            e.setPosX(e.getPosX());
            e.setPosY(e.getPosY());
         }

         if (scrollEvent.getDeltaY() < 0.0) {
            if (e.estActivite()) {
               if ((double) e.getLargeur() - TailleComposants.getInstance().getScrollZoom() > (double) TailleComposants.getInstance().getLargeurMinActivite()) {
                  e.setLargeur(e.getLargeur() - (int) TailleComposants.getInstance().getScrollZoom());
               }

               if ((double) e.getHauteur() - TailleComposants.getInstance().getScrollZoom() > (double) TailleComposants.getInstance().getHauteurMinActivite()) {
                  e.setHauteur(e.getHauteur() - (int) TailleComposants.getInstance().getScrollZoom());
               }
            } else if ((double) e.getLargeur() - TailleComposants.getInstance().getScrollZoom() > (double) TailleComposants.getInstance().getLargeurMinGuichet()) {
               e.setLargeur(e.getLargeur() - (int) TailleComposants.getInstance().getScrollZoom());
            }

            e.setPosX(e.getPosX());
            e.setPosY(e.getPosY());
         }
      }

      this.w.reagir();
   }

   public void reagir() {
      final Pane panneau = this.pane;
      Runnable command = new Runnable() {
         @Override
         public void run() { 
            if (panneau != null) {
               panneau.getChildren().clear();
               if (ControllerMondeIG.this.w.getHmSteps().isEmpty()) {
                  FabriqueIdentifiant.getInstance().reset();
               }

               for (EtapeIG e : ControllerMondeIG.this.w) {
                  ControllerMondeIG.this.drawArc(e);
                  ControllerMondeIG.this.initCoordonnee(e);
                  if (e.estActivite())
                     ControllerMondeIG.this.drawActivite(e);
                  else
                     ControllerMondeIG.this.drawGuichet(e);

                  if (!ControllerMondeIG.this.w.isSimulationActive())
                     ControllerMondeIG.this.drawPDC(e);
               }

               if (ControllerMondeIG.this.w.isSimulationActive()) {
                  ControllerMondeIG.this.drawClient();
               }
            }
         }
      };
      if (Platform.isFxApplicationThread())
         command.run();
      else
         Platform.runLater(command);
   }
}
