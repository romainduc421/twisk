package twisk.monde;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Objects;


public class GestionnaireEtapes implements Iterable<Etape>
{

    protected ArrayList<Etape> steps;

    /**
     * Constructeur de GestionnaireEtapes sans paramètres
     */
    public GestionnaireEtapes(){
        steps = new ArrayList<>();
    }

    /**
     * ajoute une collection d'étapes à steps
     * @param steps  paramètre à taille variable d'étapes (collection d'étapes)
     */
    public void ajouter(Etape... steps){
        for(Etape step : steps)
            this.steps.add(step);
    }

    /**
     * @return nb d'étapes
     */
    public int nbEtapes(){
        return this.steps.size();
    }

    public Etape getStep(int k){
        return this.steps.get(k);
    }

    /**
     * @return un itérateur d'étapes rendant la classe GE itérable
     */
    @Override
    public Iterator<Etape> iterator() {
        return steps.iterator();
    }

    @Override
    public String toString(){
        String res;
        StringBuilder sb = new StringBuilder(3*nbEtapes());
        Iterator it_gestEt = this.iterator();

        while(it_gestEt.hasNext()) {
            Etape step = (Etape) it_gestEt.next();
            sb.append(step.nom + " : " + step.succ_gest.nbEtapes() +
                    " successeur");
            sb.append(step.succ_gest.nbEtapes() > 1? "s ":" ");
            sb.append(step.succ_gest.toString() + "\n");
        }
        res = sb.toString();
        return res;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        GestionnaireEtapes etapes = (GestionnaireEtapes) o;
        return steps.equals(etapes.steps);
    }

}
