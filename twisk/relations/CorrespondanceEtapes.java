package twisk.relations;

import java.util.HashMap;
import twisk.monde.Etape;
import twisk.mondeIG.EtapeIG;

public class CorrespondanceEtapes {
    private HashMap<String, EtapeIG> stepsIG;
    private HashMap<String, Etape> steps;

    public CorrespondanceEtapes(HashMap<String,EtapeIG> stepsIG)
    {
        this.steps = new HashMap<>(stepsIG.size());
        this.stepsIG = stepsIG;
    }

    public Etape get(EtapeIG st)
    {
        return this.steps.get(st.getName());
    }
    public void ajouter(EtapeIG stepig, Etape step)
    {
        assert (step != null):"bug qd il n'y a pas d'étapes à rentrer";
        this.steps.put(stepig.getName(), step);
    }
}
