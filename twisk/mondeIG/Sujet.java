package twisk.mondeIG;

import twisk.controllers.Controller;
import twisk.ui.Observateur;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Sujet implements Serializable {

    protected transient List<Observateur> list_obsv ;
    protected transient List<Controller> controller ;
    protected PointDeControleIG src_pt;
    //private static final long serialVersionUID = -9116721254471658773L;

    /**
     * Constructeur Sujet
     */
    public Sujet(){
        this.list_obsv = new ArrayList<Observateur>(10);
        this.controller = new ArrayList<>(10);
    }

    /**
     * methode permettant de rafraichir les vues
     */
    public void notifierObservateurs(){
        for(Observateur obsv : list_obsv)
            obsv.reagir();
        for(Controller cntrl : controller)
            cntrl.reagir();
        this.src_pt = null;
    }

    /**
     * methode permettant d'ajouter un observateur au gestionnaire de vues
     * @param view
     */
    public void ajouterObservateur(Observateur view){
        if (view == null)
            throw new NullPointerException();
        if(!list_obsv.contains(view))
            this.list_obsv.add(view);
    }
    public void ajouterObservateurs(Observateur... views){
        if(views == null)
            throw new NullPointerException();
        this.list_obsv.addAll(Arrays.asList(views));
    }

    /**
     * methode permettant de supprimer des vues l'observateur considéré
     * @param view
     */
    public void removeObservateur(Observateur view){
        if(this.list_obsv.contains(view))
            this.list_obsv.remove(view);
    }

    /**
     * vider la collection d'observateurs
     */
    public void deleteAllObservateurs(){
        list_obsv.removeAll(this.list_obsv);
    }

    /**
     * retourne le nombre d'observateur de la collection de vues
     */
    public int nbObservateur(){
        return this.list_obsv.size();
    }

    /**
     * getters & setters
     * @return
     */
    public List<Observateur> getList_obsv() {
        return list_obsv;
    }

    public void setList_obsv(List<Observateur> list_obsv) {
        this.list_obsv = list_obsv;
    }

    /**
     * toString() de Sujet
     */
    @Override
    public String toString() {
        return "Sujet{" +
                "list_obsv=" + list_obsv +
                '}';
    }

    /**
     * equals() de Sujet
     * @param o
     * @return
     */
    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Sujet sujet = (Sujet) o;
        return list_obsv.equals(sujet.list_obsv);
    }

    public void ajouterController(Controller ctr)
    {
        this.controller.add(ctr) ;
    }
}
