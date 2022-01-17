package twisk.ecouteurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

public class AllUnselectEcouteur implements EventHandler<ActionEvent> {
    private MondeIG w;

    /**
     * Constructeur AllUnselectEcouteur
     * permet de deselectionner toutes les activites selectionnees jusqu'alors
     * @param w
     */
    public AllUnselectEcouteur(MondeIG w)
    {
        this.w = w;
    }
    @Override
    public void handle(ActionEvent evt)
    {
        for(EtapeIG st : this.w)
        {
            if(st.isStepDetected())
                st.setStepDetected(false);
        }
        while(w.iterator_Arcs().hasNext())
        {
            ArcIG bow = w.iterator_Arcs().next();
            if(bow.isBowDetected())
                bow.setBowDetected(false);
        }

        this.w.notifierObservateurs();
    }
}
