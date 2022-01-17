package twisk.monde;

public class ActiviteRestreinte extends Activite
{
    /**
     * Constructeur d'une activité restreinte à 1 paramètre
     * @param nom   nom de l'activité restreinte considérée
     */
    public ActiviteRestreinte(String nom){
        super(nom);
    }

    /**
     * Constructeur d'une activité restreinte à 3 paramètres
     * @param nom   nom de l'activité restreinte considérée
     * @param tps   durée estimée de l'activité restreinte
     * @param ecartTps  écart de temps
     */
    public ActiviteRestreinte(String nom, int tps, int ecartTps){
        super(nom, tps, ecartTps);
    }

    /*public String toC(){
        if(this.succ_gest.nbEtapes() == 0)
            return "";
        StringBuilder res = new StringBuilder(8);
        res.append("\ttransfert( "+this.getNom().toUpperCase()+", ")
        .append(this.getSuccesseur().getNom().toUpperCase()+" ) ;\n");

        res.append(this.getSuccesseur().toC());
        return res.toString();
    }*/
}
