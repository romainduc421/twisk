package twisk.monde;

import twisk.outils.FabriqueNumero;

import java.util.Iterator;

public abstract class Etape implements Iterable<Etape>
{
    protected String nom;
    protected GestionnaireSuccesseurs succ_gest;
    protected int no ;
    protected int cptEtape;

    /**
     * Constructeur d'une étape à 1 paramètre
     * @param nom
     */
    public Etape(String nom){
        this.nom = nom;
        this.nom = this.nom.replaceAll("à", "a");
        this.nom = this.nom.replaceAll("à", "a");
        this.nom = this.nom.replaceAll("á", "a");
        this.nom = this.nom.replaceAll("â", "a");
        this.nom = this.nom.replaceAll("ã", "a");
        this.nom = this.nom.replaceAll("ä", "a");
        this.nom = this.nom.replaceAll("ç", "c");
        this.nom = this.nom.replaceAll("è", "e");
        this.nom = this.nom.replaceAll("é", "e");
        this.nom = this.nom.replaceAll("ê", "e");
        this.nom = this.nom.replaceAll("ë", "e");
        this.nom = this.nom.replaceAll("ì", "i");
        this.nom = this.nom.replaceAll("í", "i");
        this.nom = this.nom.replaceAll("ì", "i");
        this.nom = this.nom.replaceAll("î", "i");
        this.nom = this.nom.replaceAll("ï", "i");
        this.nom = this.nom.replaceAll("ñ", "n");
        this.nom = this.nom.replaceAll("ò", "o");
        this.nom = this.nom.replaceAll("ó", "o");
        this.nom = this.nom.replaceAll("ô", "o");
        this.nom = this.nom.replaceAll("õ", "o");
        this.nom = this.nom.replaceAll("ö", "o");
        this.nom = this.nom.replaceAll("ù", "u");
        this.nom = this.nom.replaceAll("ú", "u");
        this.nom = this.nom.replaceAll("û", "u");
        this.nom = this.nom.replaceAll("ü", "u");
        this.nom = this.nom.replaceAll("ý", "y");
        this.nom = this.nom.replaceAll("ÿ", "y");
        this.nom = this.nom.replaceAll("À", "a");
        this.nom = this.nom.replaceAll("Á", "a");
        this.nom = this.nom.replaceAll("Â", "a");
        this.nom = this.nom.replaceAll("Ã", "a");
        this.nom = this.nom.replaceAll("Ä", "a");
        this.nom = this.nom.replaceAll("Ç", "c");
        this.nom = this.nom.replaceAll("È", "e");
        this.nom = this.nom.replaceAll("É", "e");
        this.nom = this.nom.replaceAll("Ê", "e");
        this.nom = this.nom.replaceAll("Ë", "e");
        this.nom = this.nom.replaceAll("Ì", "i");
        this.nom = this.nom.replaceAll("Í", "i");
        this.nom = this.nom.replaceAll("Î", "i");
        this.nom = this.nom.replaceAll("Ï", "i");
        this.nom = this.nom.replaceAll("Ñ", "n");
        this.nom = this.nom.replaceAll("Ò", "o");
        this.nom = this.nom.replaceAll("Ó", "o");
        this.nom = this.nom.replaceAll("Ô", "o");
        this.nom = this.nom.replaceAll("Õ", "o");
        this.nom = this.nom.replaceAll("Ö", "o");
        this.nom = this.nom.replaceAll("Ù", "u");
        this.nom = this.nom.replaceAll("Ú", "u");
        this.nom = this.nom.replaceAll("Û", "u");
        this.nom = this.nom.replaceAll("Ü", "u");
        this.nom = this.nom.replaceAll("Ý", "y");
        this.nom = this.nom.replaceAll("[^a-zA-Z0-9]", "_");
        this.succ_gest = new GestionnaireSuccesseurs();
        this.no = FabriqueNumero.getInstance().getNoEtape();
        this.cptEtape = 0;
    }

    /**
     * ajoyte une collection d'étapes à l'ArrayList d'étapes
     * @param steps une collection d'étapes
     */
    public void ajouterSuccesseur(Etape... steps){
        succ_gest.ajouter(steps);
        cptEtape++;
    }

    /**
     * methodes abstraites redéfinies dans les classes filles
     * @return true si l'étape est un guichet, false sinon
     * @return true si l'étape est une Activité, false sinon
     */
    public abstract boolean estUnGuichet();
    public abstract boolean estUneActivite();
    public boolean estSortie(){
        return false;
    };

    /**
     * retourne le nombre de successeurs d'une étape considérée
     * @return  taille la collection de GestionnaireSuccesseurs
     */
    public int nbSuccesseur(){
        return succ_gest.nbEtapes();
    }

    /**
     * rendre itérable la classe Etape
     * @return un itérateur de Etape
     */
    @Override
    public Iterator<Etape> iterator(){
        return succ_gest.iterator();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(7);
        sb.append(this.nom).append(" : ").append(succ_gest.nbEtapes()).append(" successeur");
        if(succ_gest.nbEtapes() > 1)
            sb.append("s");
        sb.append(succ_gest);
        sb.append("\n");
        return sb.toString();
    }

    public Etape getSuccesseur(){
        return succ_gest.iterator().next();

    }

    @Override
    public boolean equals(Object object){
        if(object instanceof Etape)
            return getNo() == ((Etape) object).no ;
        return false;
    }

    public String getNom() {
        return nom;
    }

    public int getNo() {
        return no;
    }

    public void reset(){
        FabriqueNumero.getInstance().reset();
    }

    public String toC(){
        return "";
    }

}
