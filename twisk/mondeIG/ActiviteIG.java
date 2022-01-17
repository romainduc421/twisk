package twisk.mondeIG;

import java.io.Serializable;

public class ActiviteIG extends EtapeIG implements Serializable {

    private int delai, ecartTps ;

    /**
     * Constructeur d'une activite
     * @param nom   nom de l'activite consideree
     * @param idf   identifiant unique de l'activite
     * @param larg  largeur visuel de la VBox
     * @param haut  hauteur visuel de la VBox
     */
    public ActiviteIG(String nom, String idf, int larg, int haut)
    {
        super(nom,idf,larg,haut);
        this.delai = 4;
        this.ecartTps = 2 ;
        this.activiteRestreinte = false;
    }

    /**
     * second constructeur avec les parametres delai et ecartTps
     * @param nom
     * @param idf
     * @param delai
     * @param ecartTps
     * @param larg
     * @param haut
     */
    public ActiviteIG(String nom, String idf, int delai,int ecartTps, int larg, int haut)
    {
        super(nom,idf, larg, haut);
        this.delai = delai ;
        this.ecartTps = ecartTps ;
        this.activiteRestreinte = false ;
    }

    public boolean estActivite(){
        return true;
    }
    public boolean estUnGuichet(){
        return false;
    }
    public boolean estActiviteRestreinte(){
        return true;
    }

    /**
     * getters/setters
     */
    public String getDelai()
    {
        return Integer.toString(this.delai) ;
    }
    public String getEcartTps(){
        return Integer.toString(this.ecartTps) ;
    }
    public boolean estSortie(){
        return super.isExit();
    }

    public void setDelai(int delai)
    {
        this.delai = delai;
    }
    public void setEcartTps(int ecartTps)
    {
        this.ecartTps = ecartTps;
    }
    public void setActiviteRestreinte(boolean rest){
        this.activiteRestreinte = rest;
        this.activite = false;
    }
    public void setSortie(boolean res)
    {
        super.setExit(res);
    }
    /**
     * toString() de ActiviteIG
     */
    @Override
    public String toString()
    {
        return super.toString()+"\nDelai : "+this.getDelai()+", EcartTps : "+this.getEcartTps()+"\n" ;
    }
}
