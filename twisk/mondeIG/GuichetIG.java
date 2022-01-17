package twisk.mondeIG;

public class GuichetIG extends EtapeIG{

    private int nbJetons;
    private boolean isExit;

    public GuichetIG(String nom, String idf, int larg, int haut){
        super(nom,idf,larg,haut);
        this.nbJetons = 1;
        this.isExit = true;
    }

    public GuichetIG(String nom, String idf, int larg, int haut, int nbJetons){
        super(nom,idf,larg,haut);
        this.nbJetons = nbJetons;
        this.isExit = true;
    }

    @Override
    public String toString(){
        StringBuilder s = new StringBuilder(7);
        s.append("nom : " + super.getName() + "\n").append("identifiant : " + super.getId() + "\n")
        .append("largeur : " + super.getLargeur() + "\n")
        .append("hauteur : " + super.getHauteur() + "\n")
        .append("X : " + super.getPosX() + "\n")
        .append("Y : " + super.getPosY() + "\n")
        .append("Nombre de jetons : " + this.nbJetons + "\n");
        return s.toString();
    }

    public int getNbJetons() {
        return this.nbJetons;
    }

    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }

    public boolean estActivite() {
        return false;
    }

    public boolean estUnGuichet() {
        return true;
    }

    public boolean isExitRight() {
        return this.isExit;
    }

    public void setExitRight(boolean res) {
        this.isExit = res;
    }
}
