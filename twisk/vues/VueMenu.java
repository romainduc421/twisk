package twisk.vues;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.VBox;
import twisk.ecouteurs.*;
import twisk.mondeIG.MondeIG;
import twisk.ui.Observateur;

public class VueMenu extends MenuBar implements Observateur {

    private MondeIG w;
    private VueMondeIG vw;

    /**
     * Constructeur de la vue permettant d'ajouter un menu
     * @param w modèle du monde
     * @param vw vue du monde représenté
     */
    public VueMenu(MondeIG w, VueMondeIG vw)
    {
        super();
        this.w = w;
        this.vw = vw;
        VBox vb = new VBox();
        Menu edit = new Menu("Editing");
        Menu file = new Menu ("File");
        Menu wd = new Menu("World");
        Menu params = new Menu("Parameters") ;
        Menu theme = new Menu("Themes") ;
        MenuItem quit = new MenuItem("Leave");
        MenuItem rename = new MenuItem("Rename");
        MenuItem remove = new MenuItem("Delete selection");
        MenuItem unselectAll = new MenuItem("Unselect items");
        MenuItem entry = new MenuItem("Entree");
        MenuItem exit = new MenuItem("Sortie");
        MenuItem tps = new MenuItem("Delai");
        MenuItem delay = new MenuItem("Ecart-temps") ;
        MenuItem token = new MenuItem("Nb de jetons") ;
        MenuItem modifVw = new MenuItem("dark theme") ;
        MenuItem modifVw1 = new MenuItem("standard theme") ;
        MenuItem modifVw2 = new MenuItem("light theme") ;

        quit.setOnAction(evt-> Platform.exit());
        remove.setOnAction(new DeleteSelectionEcouteur(this.w));
        rename.setOnAction(new RenameEcouteur(this.w));
        unselectAll.setOnAction(new AllUnselectEcouteur(this.w));
        entry.setOnAction(new EntryEcouteur(this.w));
        exit.setOnAction(new ExitEcouteur(this.w));
        tps.setOnAction(new DelaiEcouteur(this.w));
        delay.setOnAction(new EcartTempsEcouteur(this.w));
        token.setOnAction(new TokenEcouteur(this.w));

        modifVw.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent evt)
            {
                vw.setStyle("-fx-background-color: #03032D;\n"+
                "-fx-border-color: #dbf4f6;\n"+
                "-fx-border-radius: 12.467;\n"+
                "-fx-border-width: 4.12");
                w.notifierObservateurs();
            }
        });

        modifVw1.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                vw.setStyle("-fx-background-color: #e1e5ed; -fx-border-color: #0059FF;");
                w.notifierObservateurs();
            }
        });

        modifVw2.setOnAction(new EventHandler<ActionEvent>()
        {
            @Override
            public void handle(ActionEvent event)
            {
                vw.setStyle("-fx-background-color: #ffffff;\n"+
                "-fx-border-color: #F1432E;\n");
                w.notifierObservateurs();
            }
        });

        file.getItems().addAll(quit);
        edit.getItems().addAll(remove, unselectAll,rename);
        wd.getItems().addAll(entry,exit);
        params.getItems().addAll(tps,delay, token) ;
        theme.getItems().addAll(modifVw,modifVw1,modifVw2) ;
        this.getMenus().addAll(file, edit, wd, params, theme) ;
        vb.getChildren().add(this);
        this.w.ajouterObservateur(this);
    }

    @Override
    public void reagir()
    {

    }
}
