package twisk.ui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import twisk.controllers.ControllerApplication;
import twisk.controllers.ControllerMenu;
import twisk.controllers.ControllerMondeIG;
import twisk.controllers.ControllerNavigateur;
import twisk.controllers.ControllerPanneauAjouterEtapeIG;
import twisk.controllers.ControllerPanneauInformations;
import twisk.controllers.ControllerPanneauLancement;
import twisk.controllers.ControllerPointDeControleIG;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class GUI extends Application {
    public GUI() throws Exception{

    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        TailleComposants.getInstance().setApplicationWidth(1575);
        TailleComposants.getInstance().setApplicationHeight(885);
        GUI gui = new GUI();
        MondeIG monde = new MondeIG();
        GestionnaireGraphique gestionnaireGraphiques = new GestionnaireGraphique(monde);
        ControllerApplication ca = new ControllerApplication(monde, gestionnaireGraphiques);
        ControllerMondeIG cmig = new ControllerMondeIG(monde, gestionnaireGraphiques);
        ControllerPointDeControleIG cpdcig = new ControllerPointDeControleIG(monde, gestionnaireGraphiques);
        ControllerMenu cm = new ControllerMenu(monde, gestionnaireGraphiques);
        ControllerNavigateur cn = new ControllerNavigateur(monde, gestionnaireGraphiques);
        ControllerPanneauAjouterEtapeIG cpaetapeig = new ControllerPanneauAjouterEtapeIG(monde, gestionnaireGraphiques);
        ControllerPanneauInformations cpinfos = new ControllerPanneauInformations(monde, gestionnaireGraphiques);
        ControllerPanneauLancement cpl = new ControllerPanneauLancement(monde, gestionnaireGraphiques);
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(gui.getClass().getResource("ObservateurApplication.fxml"));
        loader.setControllerFactory((ic) -> {
            if (ic.equals(ControllerApplication.class)) {
                return ca;
            } else if (ic.equals(ControllerMondeIG.class)) {
                return cmig;
            } else if (ic.equals(ControllerPointDeControleIG.class)) {
                return cpdcig;
            } else if (ic.equals(ControllerMenu.class)) {
                return cm;
            } else if (ic.equals(ControllerNavigateur.class)) {
                return cn;
            } else if (ic.equals(ControllerPanneauAjouterEtapeIG.class)) {
                return cpaetapeig;
            } else if (ic.equals(ControllerPanneauInformations.class)) {
                return cpinfos;
            } else {
                return ic.equals(ControllerPanneauLancement.class) ? cpl : null;
            }
        });
        Parent root;
        root = loader.load();
        primaryStage.setTitle("twisk");
        primaryStage.setScene(new Scene(root, TailleComposants.getInstance().getApplicationWidth(), TailleComposants.getInstance().getApplicationHeight()));
        primaryStage.show();
    }
}
