package twisk.ecouteurs;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import twisk.mondeIG.ArcIG;
import twisk.mondeIG.EtapeIG;
import twisk.mondeIG.MondeIG;

import java.util.Iterator;

public class DeleteSelectionEcouteur implements EventHandler<ActionEvent> {

    private MondeIG w;

    /**
     * Constructeur DeleteSelectionEcouteur
     * permet de supprimer toutes les etapes selectionnees jusqu'alors
     * @param w
     */
    public DeleteSelectionEcouteur(MondeIG w){
        this.w = w;
    }
    @Override
    public void handle(ActionEvent evt){
        for(EtapeIG st : this.w){
            if(st.isStepDetected())
                st.setStepDetected(false);
        }
        Iterator<ArcIG> bow_it= this.w.iterator_Arcs();
        while(bow_it.hasNext()){
            ArcIG bow = bow_it.next();
            if(bow.isBowDetected())
                bow.setBowDetected(false);
        }
        this.w.deleteSelectedBows();
        if(this.w.getSelectedSteps().size()!=0)
            this.w.removeArcIG();

        this.w.removeEtapeIG();
        this.w.notifierObservateurs();
    }
}
