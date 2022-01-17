package twisk.monde;

import twisk.outils.FabriqueNumero;

public class Guichet extends Etape
{
    private int nbJetons;
    private int noSemaphore ;

    /**
     * Constructeur
     * @param nom nom du guichet considéré
     */
    public Guichet(String nom){
        super(nom);
        noSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
        nbJetons = 1;
        if(nom.equals(""))
            this.nom = "Guichet n° "+this.no;
    }

    /**
     * Constructeur à 2 paramètres
     * @param nom   nom du guichet
     * @param nb    nb de jetons maximum
     */
    public Guichet(String nom, int nb){
        super(nom);
        noSemaphore = FabriqueNumero.getInstance().getNumeroSemaphore();
        if(nom.equals(""))
            this.nom = "Guichet n° "+this.no;
        this.nbJetons = nb;
    }

    /**
     * retourne true si c'est un guichet, false sinon
     * @return true puisqu'il s'agit bien d'un guichet
     */
    @Override
    public boolean estUnGuichet() {
        return true;
    }

    /**
     * retourne true si c'est une activité, false sinon
     * @return false puisqu'il ne s'agit pas d'une activité
     */
    @Override
    public boolean estUneActivite() {
        return false;
    }

    @Override
    public boolean estSortie(){
        return false;
    }


    /**
     * @return numero de semaphore
     * les numeros de semaphores associés aux guichets sont utilisés dans le code C
     * comme indices de tableau (1er indice déjà pris)
     * permet synchronisation de tous les processus qui vont s'exécuter sur le shell
     */
    public int getNoSemaphore(){
        return this.noSemaphore;
    }

    /**
     * @return nb de jetons du guichet considéré
     */
    public int getNbJetons() {
        return this.nbJetons;
    }

    public void setNbJetons(int nbJetons) {
        this.nbJetons = nbJetons;
    }

    public String toC(){
        StringBuilder sb_C = new StringBuilder();
        sb_C.append("\tP(ids,SEM_ID_" + this.nom.toUpperCase() + ");\n");
        if (this.nbSuccesseur() > 0) {
            String str;
            if (this.nbSuccesseur() > 1) {
                int nbCas = 0;
                sb_C.append("\tswitch(nb" + this.getNo() + ") {\n");

                for(Etape e : this)
                {
                    sb_C.append("\t\tcase " + nbCas + " : \n\t\t{\n");
                    str = this.nom.toUpperCase();
                    sb_C.append("\t\t\ttransfert(" + str + "," + e.nom.toUpperCase() + ");\n");
                    str = e.nom;
                    sb_C.append("\t\t\t" + str + "_" + e.getNo() + "(ids);\n");
                    sb_C.append("\t\t\tbreak;\n");
                    sb_C.append("\t\t}\n");
                    ++nbCas;
                }
                sb_C.append("\t}\n");
            } else {
                str = this.nom.toUpperCase();
                sb_C.append("\t\ttransfert(" + str + "," + (this.succ_gest.iterator().next()).nom.toUpperCase() + ");\n");
                if ((this.succ_gest.iterator().next()).estUneActivite()) {
                    Activite act = (Activite)this.succ_gest.iterator().next();
                    int var6 = act.getTemps();
                    sb_C.append("\t\tdelai(" + var6 + "," + act.getEcartTemps() + ");\n");
                }

                sb_C.append("\tV(ids,SEM_ID_" + this.nom.toUpperCase() + ");\n");
                if ((this.succ_gest.iterator().next()).nbSuccesseur() > 1) {
                    if ((this.succ_gest.iterator().next()).estUnGuichet()) {
                        str = (this.succ_gest.iterator().next()).nom;
                        sb_C.append("\t" + str + "_" + (this.succ_gest.iterator().next()).getNo() + "(ids);\n");
                    } else {
                        str = (this.succ_gest.iterator().next()).nom;
                        sb_C.append("\t" + str + "_" + (this.succ_gest.iterator().next()).getNo() + "(ids);\n");
                    }
                } else if ((this.succ_gest.iterator().next()).nbSuccesseur() > 0) {
                    str = (this.succ_gest.iterator().next()).nom.toUpperCase();
                    sb_C.append("\ttransfert(" + str + "," + ((this.succ_gest.iterator().next()).succ_gest.iterator().next()).nom.toUpperCase() + ");\n");
                    if (!((this.succ_gest.iterator().next()).succ_gest.iterator().next()).nom.equals("SasSortie")) {
                        if (((this.succ_gest.iterator().next()).succ_gest.iterator().next()).estUnGuichet()) {
                            str = ((this.succ_gest.iterator().next()).succ_gest.iterator().next()).nom;
                            sb_C.append("\t" + str + "_" + ((this.succ_gest.iterator().next()).succ_gest.iterator().next()).getNo() + "(ids);\n");
                        } else {
                            str = ((this.succ_gest.iterator().next()).succ_gest.iterator().next()).nom;
                            sb_C.append("\t" + str + "_" + ((this.succ_gest.iterator().next()).succ_gest.iterator().next()).getNo() + "(ids);\n");
                        }
                    }
                } else {
                    System.err.println("Pas de successeur\n");
                }
            }
        } else {
            sb_C.append("\n");
        }

        return sb_C.toString();
    }
}
