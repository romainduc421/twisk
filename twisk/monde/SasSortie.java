package twisk.monde;

public class SasSortie extends Activite
{
    /**
     * Constructeur
     */
    public SasSortie(){
        super("SasSortie");
    }

    @Override
    public String toString(){
        return "SasSortie : "+this.succ_gest.nbEtapes()+" successeur"+(this.succ_gest.nbEtapes()>1?"s":"") +"-"+"\n";
    }

    @Override
    public boolean estSortie(){
        return true;
    }
    public String toC(){
        return "}\n\n";
    }
}
