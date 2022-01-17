package twisk.controllers;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.*;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Dialog;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.util.Duration;
import twisk.Main;
import twisk.exceptions.TwiskException;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class ControllerMenu extends Controller implements Initializable {
   @FXML
   private Menu file;
   @FXML
   private Menu edit;
   @FXML
   private Menu world;
   @FXML
   private Menu setting;
   @FXML
   private Menu loi;
   @FXML
   private MenuItem deleteSelect;
   @FXML
   private MenuItem unselect;
   @FXML
   private MenuItem rename;
   @FXML
   private MenuItem entree;
   @FXML
   private MenuItem sortie;
   @FXML
   private MenuItem delai;
   @FXML
   private MenuItem jetons;
   @FXML
   private MenuItem save;
   @FXML
   private MenuItem open;
   @FXML
   private Menu load;

   public ControllerMenu(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      super(monde, gestionnaireGraphique);
      this.w.ajouterController(this);
   }

   public ControllerMenu(MondeIG monde, GestionnaireGraphique gestionnaireGraphique, boolean dontAddControler) {
      super(monde, gestionnaireGraphique);
   }

   public void reagir() {
      if (this.w.isSimulationActive()) {
         this.file.setDisable(true);
         this.edit.setDisable(true);
         this.world.setDisable(true);
         this.setting.setDisable(true);
         this.loi.setDisable(true);
         this.deleteSelect.setDisable(true);
         this.save.setDisable(true);
         this.open.setDisable(true);
      } else {
         this.file.setDisable(false);
         this.edit.setDisable(false);
         this.world.setDisable(false);
         this.setting.setDisable(false);
         this.loi.setDisable(false);
         this.deleteSelect.setDisable(false);
         this.save.setDisable(false);
         this.open.setDisable(false);
         if (this.w.getSelectedSteps().size() > 0) {
            this.deleteSelect.setStyle(null);
            if (!this.w.isContainGuichet()) {
               this.sortie.setStyle(null);
            } else {
               this.sortie.setStyle("-fx-text-fill: grey");
            }
         } else {
            this.deleteSelect.setStyle("-fx-text-fill: grey");
            this.sortie.setStyle("-fx-text-fill: grey");
         }

         if (this.w.getSelectedSteps().size() == 1) {
            this.rename.setStyle(null);
            this.entree.setStyle(null);
            if (((EtapeIG)this.w.getSelectedSteps().get(0)).estActivite()) {
               this.delai.setStyle(null);
            } else {
               this.jetons.setStyle(null);
            }
         } else {
            this.entree.setStyle("-fx-text-fill: grey");
            this.rename.setStyle("-fx-text-fill: grey");
            this.jetons.setStyle("-fx-text-fill: grey");
            this.delai.setStyle("-fx-text-fill: grey");
         }

         if (this.w.getSelectedSteps().size() <= 0 && this.w.getSelectedBows().size() <= 0) {
            this.unselect.setStyle("-fx-text-fill: grey");
         } else {
            this.unselect.setStyle(null);
         }
      }

   }

   public void exit(ActionEvent actionEvent) {
      Platform.exit();
   }

   public void deleteSelect(ActionEvent actionEvent) {
      this.w.deleteSelectedBows();
      this.w.removeArcIG();
      this.w.removeEtapeIG();
   }

   public void unselect(ActionEvent actionEvent) {
      this.w.Unselect();
   }

   public void rename(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() == 1) {
         TextInputDialog dialog = new TextInputDialog();
         dialog.setTitle("Rename");
         dialog.setHeaderText((String)null);
         dialog.setContentText("New name :");
         Optional<String> result = dialog.showAndWait();
         result.ifPresent((nom) -> {
            this.w.getSelectedSteps().get(0).setName(nom);
         });
         this.w.Unselect();
      }

   }

   public void error(String message) {
      Alert alert = new Alert(AlertType.WARNING);
      alert.setContentText(message);
      alert.setHeaderText(null);
      alert.setGraphic(null);
      PauseTransition pause = new PauseTransition(Duration.seconds(5.0D));
      alert.show();
      pause.setOnFinished((event) -> {
         alert.close();
      });
      pause.play();
   }

   public void entree(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() == 1) {
         this.w.addEntree(this.w.getSelectedSteps().get(0));
      }

   }

   public void sortie(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() > 0 && !this.w.isContainGuichet()) {
         try {
            this.w.ajouterSortie();
         } catch (TwiskException excp) {
            this.error(excp.getMessage());
         }
      }

   }

   public void modifyActivite(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() == 1 && this.w.getSelectedSteps().get(0).estActivite()) {
         ControllerParametreActivite cpa = new ControllerParametreActivite(this.w, this.gestionnaireGraphique);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ui/ObservateurParametreActivite.fxml"));
         loader.setControllerFactory((ic) -> cpa);

         try {
            Dialog var4 = loader.load();
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }
   }

   public void modifyGuichet(ActionEvent actionEvent) {
      if (this.w.getSelectedSteps().size() == 1 && !this.w.getSelectedSteps().get(0).estActivite()) {
         ControllerParametreGuichet cpg = new ControllerParametreGuichet(this.w, this.gestionnaireGraphique);
         FXMLLoader loader = new FXMLLoader();
         loader.setLocation(Main.class.getResource("ui/ObservateurParametreGuichet.fxml"));
         loader.setControllerFactory(ic -> cpg);

         try {
            Dialog var4 = loader.load();
         } catch (Exception var5) {
            var5.printStackTrace();
         }
      }

   }

   public void zoom(ActionEvent actionEvent) {
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Zoom's value");
      dialog.setHeaderText(null);
      dialog.setContentText("Value :");
      Optional<String> result = dialog.showAndWait();
      result.ifPresent((zoom) -> {
         if (zoom.matches("[0-9]+")) {
            TailleComposants.getInstance().setScrollZoom(Integer.parseInt(zoom));
         }

      });
   }

   public void save(ActionEvent actionEvent) {
      FileChooser fileChooser = new FileChooser();
      ExtensionFilter extFilter = new ExtensionFilter("Monde file (*.ser)", "*.ser");
      fileChooser.getExtensionFilters().add(extFilter);
      Stage saveStage = new Stage();
      saveStage.setTitle("Save the .ser");
      File filename = fileChooser.showSaveDialog(saveStage);
      if (filename != null) {
         this.w.save(filename);
      }

   }

   public void load(ActionEvent actionEvent) {
      FileChooser fileChooser = new FileChooser();
      fileChooser.setTitle("Choose a world");
      fileChooser.getExtensionFilters().addAll(new ExtensionFilter("extensions de monde ", "*.ser"));
      Stage openStage = new Stage();
      openStage.setTitle("Load");
      File filename = fileChooser.showOpenDialog(openStage);
      if (filename != null) {
         this.w.load(filename);
      }
   }

   public void gaussienne(ActionEvent actionEvent) {
      this.w.setPoisson(false);
      this.w.setGaussienne(true);
      this.w.setUniforme(false);
      ControllerDelaiGaussienne cdg = new ControllerDelaiGaussienne(this.w, this.gestionnaireGraphique);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("ui/ObservateurDelaiGaussienne.fxml"));
      loader.setControllerFactory((ic) -> cdg);

      try {
         Dialog var4 = loader.load();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void poisson(ActionEvent actionEvent) {
      this.w.setGaussienne(false);
      this.w.setPoisson(true);
      this.w.setUniforme(false);
      TextInputDialog dialog = new TextInputDialog();
      dialog.setTitle("Modify average");
      dialog.setHeaderText((String)null);
      dialog.setContentText("average (1/lambda) :");
      Optional<String> result = dialog.showAndWait();
      result.ifPresent((lambda) -> {
         if (lambda.matches("[0-9]+")) {
            this.w.setDelai(Integer.parseInt(lambda));
         }

      });
   }

   public void uniforme(ActionEvent actionEvent) {
      this.w.setPoisson(false);
      this.w.setGaussienne(false);
      this.w.setUniforme(true);
      ControllerDelaiUniforme cdu = new ControllerDelaiUniforme(this.w, this.gestionnaireGraphique);
      FXMLLoader loader = new FXMLLoader();
      loader.setLocation(Main.class.getResource("ui/ObservateurDelaiUniforme.fxml"));
      loader.setControllerFactory((ic) -> cdu);

      try {
         Dialog var4 = loader.load();
      } catch (Exception var5) {
         var5.printStackTrace();
      }

   }

   public void initialize(URL url, ResourceBundle resourceBundle) {
      final List<String> names = new ArrayList<>(10);

      try
      {
         URI uri = Objects.requireNonNull(Main.class.getResource("ressources/mondesPredef/")).toURI();
         FileSystem fileSystem = uri.getScheme().equals("jar") ? FileSystems.newFileSystem(uri, Collections.emptyMap()) : null;

         try
         {
            final Path path = Paths.get(uri);
            Files.walkFileTree(path, new SimpleFileVisitor() {
               public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                  names.add(file.getName(path.getNameCount()).toString());
                  return FileVisitResult.CONTINUE;
               }


               public FileVisitResult visitFile(Object var1, BasicFileAttributes var2) throws IOException {
                  return this.visitFile((Path)var1, var2);
               }
            });
         } catch (Throwable exc) {
            if (fileSystem != null) {
               try {
                  fileSystem.close();
               } catch (Throwable excp) {
                  excp.addSuppressed(excp);
               }
            }

            throw exc;
         }

         if (fileSystem != null) {
            fileSystem.close();
         }

         Path directories = Files.createDirectories(Paths.get("mondesPredef/"));

         for(String nom : names) {
            InputStream source = Objects.requireNonNull(this.getClass().getResource("/mondesPredef/" + nom)).openStream();
            Path absPath = directories.toAbsolutePath();
            File destination = new File(absPath + "/" + nom);
            this.copier(source, destination);
            MenuItem m = new MenuItem(nom);
            m.setOnAction((event) -> {
               this.w.load(destination);
            });
            this.load.getItems().add(m);
         }
      } catch (URISyntaxException | IOException e) {
         e.printStackTrace();
      }

   }

   private void copier(InputStream source, File dest) throws IOException {
      InputStream sourceFile = source;
      OutputStream destinationFile = new FileOutputStream(dest);
      byte[] buffer = new byte[524288];

      int nbLecture;
      while((nbLecture = sourceFile.read(buffer)) != -1) {
         destinationFile.write(buffer, 0, nbLecture);
      }

      destinationFile.close();
      sourceFile.close();
   }
}
