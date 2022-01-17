package twisk.monde;

import java.io.FileWriter;
import java.io.IOException;
import java.io.Serializable;
import java.util.ArrayList;

public class SasEntree extends Activite implements Serializable
{
    private boolean poisson=false, gaussian=false, uniform=true;
    private int delai=6, ecartTemps=3;
    private double moyenne=6.0, lambda=6.0;

    /**
     * Constructeur
     */
    public SasEntree(){
        super("SasEntree");
    }

    @Override
    public String toString(){
        return "SasEntree : "+this.succ_gest.nbEtapes()+" successeur"+(this.succ_gest.nbEtapes()>1?"s":"")+this.succ_gest.toString()+"\n";
    }


    public String toC()
    {
        StringBuilder sbC = new StringBuilder(10);
        ArrayList<Etape> etapesVisite = new ArrayList<>();
        this.DepthFirstSearch(this,etapesVisite);
        
        try{
            FileWriter writer = new FileWriter("/tmp/twisk/prototypes.h");
            for(Etape e : etapesVisite)
                writer.append("void "+e.nom+"_"+e.no+"(int ids) ;\n");
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        for(Etape e : etapesVisite)
        {
            if(!e.getNom().equals("SasEntree") && !e.getNom().equals("SasSortie"))
            {
                if(e.estUnGuichet()) {
                    sbC.append("void " + e.getNom() + "_" + e.getNo() + "(int ids) {\n");
                    sbC.append(e.toC()+"}\n\n");
                }
                else{
                    sbC.append("void "+e.getNom()+"_"+e.getNo()+"(int ids) {\n");
                    sbC.append(e.toC()+"}\n\n");
                }
            }
        }
        sbC.append("//-----------------------------------------------\n\nvoid simulation (int ids)\n{\n");
        int indx=0;

        for(boolean found=false; indx<etapesVisite.size() && !found; ++indx) {
            if (etapesVisite.get(indx).nbSuccesseur() > 1) {
                sbC.append("\tsrand(getpid()*314);\n");
                found = true;
            }
        }
        sbC.append("\n\t//Entree des clients\n");
        sbC.append("\tentrer(SASENTREE);\n");
        if(this.uniform)
            sbC.append("\tdelaiUniforme("+this.delai+", "+this.ecartTemps+");\n");
        else if(this.poisson)
            sbC.append("\tdelaiPoisson("+this.lambda+");\n");
        else if(this.gaussian)
            sbC.append("\tdelaiGauss("+this.moyenne+", "+this.lambda+");\n");
        if(this.succ_gest.nbEtapes()>0)
            sbC.append("\ttransfert(SASENTREE, "+(this.succ_gest.iterator().next()).getNom().toUpperCase()+");\n\n");
        if((this.succ_gest.iterator().next()).estUnGuichet() || (this.succ_gest.iterator().next()).estUneActivite())
            sbC.append("\t//Deroulement du monde\n\t"+(this.succ_gest.iterator().next()).nom+"_"+(this.succ_gest.iterator().next()).getNo()+"(ids);\n");

        return sbC.toString();
    }

    public void setUniforme(boolean res, int delai, int ecartTemps) {
        this.uniform = res;
        this.delai = delai;
        this.ecartTemps = ecartTemps;
    }

    public void setPoisson(boolean res, double lambda) {
        this.poisson = res;
        this.lambda = lambda;
    }

    public void setGaussienne(boolean res, double moyenne, double ecartype) {
        this.gaussian = res;
        this.moyenne = moyenne;
        this.lambda = ecartype;
    }
    
    public void DepthFirstSearch(Etape origin, ArrayList<Etape> steps_visited)
    {
        steps_visited.add(origin) ;
        for (Etape suiv : origin) {
            if (!steps_visited.contains(suiv))
                this.DepthFirstSearch(suiv, steps_visited);
        }
        for(Etape e : origin)
        {
            for (Etape suivant : e) {
                if (!steps_visited.contains(suivant))
                    this.DepthFirstSearch(suivant, steps_visited);
            }
        }
    }
}
