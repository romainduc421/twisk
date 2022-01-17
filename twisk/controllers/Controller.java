package twisk.controllers;
import twisk.gestionnaireGraphique.GestionnaireGraphique;
import twisk.mondeIG.MondeIG;

public abstract class Controller {
   protected GestionnaireGraphique gestionnaireGraphique;
   protected MondeIG w;

   public Controller(MondeIG monde, GestionnaireGraphique gestionnaireGraphique) {
      this.w = monde;
      this.gestionnaireGraphique = gestionnaireGraphique;
   }

   public abstract void reagir();
}
