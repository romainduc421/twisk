package twisk.monde;

public class Activite extends Etape
{
    protected int temps;
    protected int ecartTemps;

    /**
     * Constructeur à 1 paramètre
     * @param nom   nom donné à l'activité considérée
     */
    public Activite(String nom){
        super(nom);
        if(nom.isEmpty()) {
            this.nom = "Activite n° " + no;
        }
        temps = 4;
        ecartTemps = 1;

    }

    /**
     * Constructeur de Activite à 3 paramètres
     * @param nom   nom donné à l'activité considérée
     * @param tps   temps estimé
     * @param ecartTps  ecart de temps
     */
    public Activite(String nom, int tps, int ecartTps){
        super(nom);
        this.temps = tps;
        this.ecartTemps = ecartTps;
    }

    /**
     * retourne true si c'est une activité, false sinon
     * @return  true puisqu'il s'agit bien d'une activité
     */
    @Override
    public boolean estUneActivite() {
        return true;
    }

    /**
     * retourne true si c'est un guichet, false sinon
     * @return  false puisqu'il ne s'agit pas d'un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return false;
    }

    @Override
    public boolean estSortie(){
        return false;
    }
    /**
     * @return temps estimé de la durée de l'activité
     */
    public int getTemps() {
        return temps;
    }

    /**
     * @return  l'écart de temps de l'activité
     */
    public int getEcartTemps() {
        return ecartTemps;
    }

    public String toC(){

        StringBuilder sb_C = new StringBuilder();
        sb_C.append("\tdelai(" + this.temps + "," + this.ecartTemps + ");\n");
        if (this.nbSuccesseur() > 0) {
            String name_h;
            if (this.nbSuccesseur() > 1) {
                int nbCas = 0;
                sb_C.append("\tint nb = (float) (rand() / (float)RAND_MAX) * " + this.nbSuccesseur() + " ;\n");
                sb_C.append("\tswitch(nb) {\n");

                for(Etape e : this) {
                    sb_C.append("\t\tcase " + nbCas + " : \n\t\t{\n");
                    sb_C.append("\t\t\ttransfert(" + nom.toUpperCase() + "," + e.nom.toUpperCase() + ");\n");
                    if (!e.estSortie())
                        sb_C.append("\t\t\t" + e.nom + "_" + e.getNo() + "(ids);\n");

                    sb_C.append("\t\t\tbreak;\n");
                    sb_C.append("\t\t}\n");
                    ++nbCas;
                }

                sb_C.append("\t}\n");
            } else {
                name_h = this.nom.toUpperCase();
                sb_C.append("\ttransfert(" + name_h + "," + (this.succ_gest.iterator().next()).nom.toUpperCase() + ");\n");
                if (!(this.succ_gest.iterator().next()).nom.equals("SasSortie")) {
                    if ((this.succ_gest.iterator().next()).estUnGuichet()) {
                        name_h = (this.succ_gest.iterator().next()).nom;
                        sb_C.append("\t" + name_h + "_" + (this.succ_gest.iterator().next()).getNo() + "(ids);\n");
                    } else {
                        name_h = (this.succ_gest.iterator().next()).nom;
                        sb_C.append("\t" + name_h + "_" + (this.succ_gest.iterator().next()).getNo() + "(ids);\n");
                    }
                }
            }
        } else
            sb_C.append("\n");

        return sb_C.toString();
    }

}
