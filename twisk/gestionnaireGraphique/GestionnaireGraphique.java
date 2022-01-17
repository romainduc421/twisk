package twisk.gestionnaireGraphique;
import javafx.scene.image.Image;
import twisk.mondeIG.MondeIG;
import twisk.outils.TailleComposants;

public class GestionnaireGraphique {
   MondeIG monde;
   private boolean accueilPressed;
   private boolean ajouterEtapeIGPressed;
   private boolean informationsPressed;
   private Image imgEntre;
   private Image imgSortie;
   private Image imgEntreeSortie;
   private int largeurActivite;
   private int hauteurActivite;
   private int largeurGuichet;
   private int hauteurGuichet;

   public GestionnaireGraphique(MondeIG monde) {
      this.monde = monde;
      this.accueilPressed = true;
      this.ajouterEtapeIGPressed = false;
      this.informationsPressed = false;
      this.largeurActivite = TailleComposants.getInstance().getViewStep()[0];
      this.hauteurActivite = TailleComposants.getInstance().getViewStep()[1];
      this.largeurGuichet = TailleComposants.getInstance().getViewCounter()[0];
      this.hauteurGuichet = TailleComposants.getInstance().getViewCounter()[1];
      Image imageEntreeSortie = new Image("/images/entryExit.png");
      this.setImgEntreeSortie(imageEntreeSortie);
      Image imageEntree = new Image("/images/entry.png");
      this.setImgEntre(imageEntree);
      Image imageSortie = new Image("/images/exit.png");
      this.setImgSortie(imageSortie);
   }

   public boolean isAccueilPressed() {
      return this.accueilPressed;
   }

   public void setAccueilPressed(boolean accueilPressed) {
      this.accueilPressed = accueilPressed;
      this.ajouterEtapeIGPressed = false;
      this.informationsPressed = false;
      this.monde.Unselect();
   }

   public boolean isAjouterEtapeIGPressed() {
      return this.ajouterEtapeIGPressed;
   }

   public void setAjouterEtapeIGPressed(boolean ajouterEtapeIGPressed) {
      this.ajouterEtapeIGPressed = ajouterEtapeIGPressed;
      this.accueilPressed = false;
      this.informationsPressed = false;
      this.monde.Unselect();
   }

   public boolean isInformationsPressed() {
      return this.informationsPressed;
   }

   public void setInformationsPressed(boolean informationsPressed) {
      this.informationsPressed = informationsPressed;
      this.accueilPressed = false;
      this.ajouterEtapeIGPressed = false;
      this.monde.Unselect();
   }

   public Image getImgEntre() {
      return this.imgEntre;
   }

   public void setImgEntre(Image imgEntre) {
      this.imgEntre = imgEntre;
   }

   public Image getImgSortie() {
      return this.imgSortie;
   }

   public void setImgSortie(Image imgSortie) {
      this.imgSortie = imgSortie;
   }

   public Image getImgEntreeSortie() {
      return this.imgEntreeSortie;
   }

   public void setImgEntreeSortie(Image imgEntreeSortie) {
      this.imgEntreeSortie = imgEntreeSortie;
   }
}
