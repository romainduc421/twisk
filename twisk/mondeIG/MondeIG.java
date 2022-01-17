package twisk.mondeIG;

import javafx.concurrent.Task;
import javafx.geometry.Point2D;
import twisk.exceptions.ArcsException;
import twisk.exceptions.MondeException;
import twisk.exceptions.SetTpsEcartException;
import twisk.exceptions.TwiskException;
import twisk.monde.*;
import twisk.outils.ClassLoaderPerso;
import twisk.outils.FabriqueIdentifiant;
import twisk.outils.TailleComposants;
import twisk.outils.GestionnaireThreads;
import twisk.relations.CorrespondanceEtapes;
import twisk.simulation.ClientManager;
import twisk.simulation.InterfSimulation;
import twisk.ui.Observateur;

import java.io.*;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

@SuppressWarnings("all")
public class MondeIG  extends Sujet implements Iterable<EtapeIG>, Observateur, Serializable {

    private static final long serialVersionUID = /*6529685098267757690L;*/1350092881346723535L;
    private HashMap<String,EtapeIG> hmSteps;
    private List<ArcIG> listArcs;
    private List<ArcIG> loopArcs = new ArrayList<>(10);
    private List<EtapeIG> selectedSteps;
    private List<ArcIG> selectedBows;

    private transient CorrespondanceEtapes correspondanceEtapes;
    private transient Monde m;
    private transient boolean isSimulationActive = false;
    private boolean allowLoop = false, containGuichet = false;
    private boolean poisson = false;
    private boolean gaussienne = false;
    private boolean uniforme = true;
    private int delai = 6;
    private int ecartsTemps = 3;
    private int noEtape, noPoint;
    private transient int nbClients;

    private transient Object simulationObjet;
    private transient Method getClientManager;
    private transient InterfSimulation simulation;

    /**
     * Constructeur MondeIG : création des collections
     */
    public MondeIG(){
        super();
        setHmSteps(new HashMap<>(13) );
        setListArcs(new ArrayList<>(10) );
        setSelectedSteps( new ArrayList<>(10) );
        setSelectedBows( new ArrayList<>(10) );
        this.ajouter("Activité","Activite",5,2);
    }

    /**
     * déclaration des itérateurs
     */
    @Override
    public Iterator<EtapeIG> iterator() {
        return this.hmSteps.values().iterator();
    }
    public Iterator <ArcIG> iterator_Arcs(){
        return this.listArcs.iterator();
    }


    /**
     * methode permettant d'ajouter une activite dans la hashmap de MondeIG
     * @param type
     */
    public void ajouter(String type)
    {
        FabriqueIdentifiant id_maker = FabriqueIdentifiant.getInstance();
        TailleComposants tc = TailleComposants.getInstance();
        String idf;
        if(type.equals("Activite"))
        {
            idf = id_maker.getId_Step();
            this.hmSteps.put(
                    idf,
                    new ActiviteIG(type+""+idf, idf, tc.getViewStep()[0], tc.getViewStep()[1])
            );
            this.src_pt = null;
        }
        else if(type.equals("Guichet")) {
            idf = id_maker.getId_Step();
            this.hmSteps.put(
                    idf,
                    new GuichetIG(type + "" + idf, idf, tc.getViewCounter()[0], tc.getViewCounter()[1])
            );
            this.src_pt = null;
        }
        this.reagir();
    }

    public void ajouter(String type, String nom, int arg1, int arg2) {
        String idf;
        if (type.equals("Activité")) {
            idf = FabriqueIdentifiant.getInstance().getId_Step();
            ActiviteIG act;
            if (!nom.equals("Activite") ) {
                act = new ActiviteIG(nom, idf, arg1, arg2, TailleComposants.getInstance().getViewStep()[0], TailleComposants.getInstance().getViewStep()[1]);
            } else {
                act = new ActiviteIG(nom + idf, idf, arg1, arg2, TailleComposants.getInstance().getViewStep()[0], TailleComposants.getInstance().getViewStep()[1]);
            }

            this.hmSteps.put(idf, act);
            this.src_pt = null;
        } else if (type.equals("Guichet")) {
            idf = FabriqueIdentifiant.getInstance().getId_Step();
            GuichetIG guichet = new GuichetIG(nom, idf, TailleComposants.getInstance().getViewCounter()[0], TailleComposants.getInstance().getViewCounter()[1], arg1);
            this.hmSteps.put(idf, guichet);
            this.src_pt = null;
        }

        this.reagir();
    }

    /**
     * Permet d'ajouter un arc entre deux activités (2 points cliqués -> ligne)
     * @param pt_source
     * @param pt_dest
     */
    public void ajouter(PointDeControleIG pt_source, PointDeControleIG pt_dest) throws TwiskException, MondeException{
        boolean gotLoop = false;
        if(pt_source.getStep().equals(pt_dest.getStep()))
            throw new ArcsException("2 control points parts of the same step", pt_source,pt_dest);
        else if(pt_source.getId().equals(pt_dest.getId()))
            throw new ArcsException("2 different steps w/ the same id!",pt_source,pt_dest);
        if(pt_source.getStep().estAccessibleDepuis(pt_dest.getStep())){
            if(!this.allowLoop)
                throw new TwiskException("Loops forbidden !");
            gotLoop = true;
        }

        for(ArcIG bow : this.listArcs)
        {
            if ( bow.getSrc().equals(pt_source)
            && bow.getDest().equals(pt_dest) )
                throw new ArcsException("2 X même transition", pt_source, pt_dest);

            if(bow.getSrc().equals(pt_dest)
            && bow.getDest().equals(pt_source))
                throw new ArcsException("no loops allowed",pt_source,pt_dest);

        }
        pt_source.getStep().ajouterSuccesseurs(pt_dest.getStep());
        this.verifierConstructionArc();
        LigneDroiteIG arc = new LigneDroiteIG(pt_source,pt_dest);
        this.listArcs.add(arc);
        if(gotLoop)
            this.loopArcs.add(arc);
        if(pt_dest.getStep().estUnGuichet()){
            if(Integer.parseInt(pt_dest.getId()) % 2 == 0)
                ((GuichetIG)pt_dest.getStep()).setExitRight(false);
            else if(Integer.parseInt(pt_dest.getId()) % 2 == 1)
                ((GuichetIG)pt_dest.getStep()).setExitRight(true);
        }
    }

    /**
     * Permet d'ajouter un arc curvi-ligne entre deux activités (4 pts cliqués)
     * @param pt_source
     * @param pt_dest
     * @param vect1
     * @param vect2
     * @throws TwiskException
     */
    public void ajouter(PointDeControleIG pt_source, PointDeControleIG pt_dest, Point2D vect1, Point2D vect2) throws TwiskException{
        if(pt_source.getStep().equals(pt_dest.getStep()))
            throw new ArcsException("2 control points parts of the same step", pt_source,pt_dest);
        else if(pt_source.getId().equals(pt_dest.getId()))
            throw new ArcsException("2 different steps w/ the same id!",pt_source,pt_dest);
        this.listArcs.add(new CourbeIG(pt_source,pt_dest,vect1,vect2));
    }

    public void supprimerArc(ArcIG bow){
        this.listArcs.remove(bow);
    }

    public boolean isEmpty(){
        return this.hmSteps.isEmpty();
    }

    /**
     * getters
     */
    public HashMap<String, EtapeIG> getHmSteps() {
        return hmSteps;
    }
    public List<ArcIG> getListArcs() {
        return listArcs;
    }
    public PointDeControleIG getSource_point() {
        return src_pt;
    }
    public List<EtapeIG> getSelectedSteps() {
        return selectedSteps;
    }
    public List<ArcIG> getSelectedBows() {
        return selectedBows;
    }
    public List<ArcIG> getLoopArcs() {
        return loopArcs;
    }

    /**
     * setters
     */
    public void setHmSteps(HashMap<String, EtapeIG> hmSteps) {
        this.hmSteps = hmSteps;
    }
    public void setListArcs(List<ArcIG> listArcs) {
        this.listArcs = listArcs;
    }
    public void setSelectedSteps(List<EtapeIG> selectedSteps) {
        this.selectedSteps = selectedSteps;
    }
    public void setSelectedBows(List<ArcIG> selectedBows) {
        this.selectedBows = selectedBows;
    }

    /**
     *  R a zero du point de controle source de l'arc ..
     */
    public void reset_PtCtrlSrc(){
        this.src_pt = null;
    }

    /**
     * fonction toString() de MondeIG
     */
    @Override
    public String toString(){
        StringBuffer res = (new StringBuffer()).append("MondeIG : { ").append("Hash_steps = ").append(hmSteps).append(" }");
        return res.toString();
    }

    /**
     * ancienne methode permettant de gérer les points de controle selectionnes
     * @param pdc
     * @throws TwiskException
     */
    public void selectedControlpoint(PointDeControleIG pdc) throws TwiskException,MondeException{
        if(this.getSource_point() == null)
            this.src_pt = pdc;
        else{
            this.ajouter(this.src_pt, pdc);
            super.notifierObservateurs();
            this.src_pt = null;
            this.reagir();
        }
    }

    /**
     * ajouter une étape à la collection d'étapes sélectionnées
     * @param st
     */
    public void addStepSelected(EtapeIG st){
        if(this.selectedSteps.contains(st))
            this.selectedSteps.remove(st);
        else {
            st.setStepDetected(true);
            this.selectedSteps.add(st);
        }
        this.reagir();
    }

    /**
     * ajouter un arc à la collection d'arcs sélectionnés
     * @param bow
     */
    public void addBowSelected(ArcIG bow)
    {
        if(this.selectedBows.contains(bow))
            this.selectedBows.remove(bow);
        else{
            bow.setBowDetected(true);
            this.selectedBows.add(bow);
        }
    }

    /**
     * Supprimer une étape de la collection des étapes sélectionnées
     * @param st
     */
    public void eraseStepUnselected(EtapeIG st){
        st.setStepDetected(false);
        selectedSteps.remove(st);
        reagir();
    }

    /**
     * tout dé-selectionner : vider les collections d'étapes et d'arcs sélectionnés
     */
    public void Unselect(){
        this.selectedSteps.clear();
        this.selectedBows.clear();
        this.containGuichet = false;
        this.reagir();
    }

    /**
     * supprimer tous les arcs reliés à des étapes sélectionnées
     */
    public void removeArcIG(){
        Iterator<ArcIG> bow_it ;
        List<ArcIG> toRemove = new ArrayList<ArcIG>();
        for(bow_it = this.iterator_Arcs(); bow_it.hasNext();)
        {
            ArcIG bowToRemove ;
            do {
                bowToRemove = bow_it.next();
            }while(bow_it.hasNext() &&!this.getSelectedSteps().contains(bowToRemove.getDest().getStep()) && !this.getSelectedSteps().contains(bowToRemove.getSrc().getStep()));
            toRemove.add(bowToRemove);
        }
        this.getListArcs().removeAll(toRemove);
        this.reagir();
    }

    /**
     * supprimer etapes selectionnees
     */
    public void removeEtapeIG(){
        for (EtapeIG stepToRemove : this.getSelectedSteps()) {
            this.getHmSteps().remove(stepToRemove.getId());
        }
        reagir();
    }

    /**
     * supprimer arcs selectionnes
     */
    public void deleteSelectedBows(){
        for (ArcIG bow : this.getSelectedBows()) {
            this.getListArcs().remove(bow);
            bow.getSrc().getStep().enleverSuccesseurs(bow.getDest().getStep());
        }
        this.reagir();
    }

    /**
     * ajouter la caracteristique entree a une activite
     * si deja une entree, activite perd son status d'entree
     * @param step
     */
    public void addEntree(EtapeIG step)
    {
        if(this.getSelectedSteps().contains(step))
        {
            step.setEntry(!step.isEntry());
        }
        for (EtapeIG st : this) {
            if (!st.equals(step))
                st.setEntry(false);
        }
        reagir();
        Unselect();
    }

    /**
     * ajouter la caracteristique sortie a une activite
     * si deja une sortie, activite perd son status de sortie
     * @param step
     */
    public void addSortie(EtapeIG step)
    {
        if(this.getSelectedSteps().contains(step))
        {
            step.setExit(!step.isExit());
        }
        for (EtapeIG st : this) {
            if (!st.equals(step))
                st.setExit(false);
        }
        //reagir();
    }

    private void verifierConstructionArc() throws MondeException{
        for(EtapeIG e : this){
            if(!e.estActivite()){
                for (EtapeIG et_succ : e.getSuccesseurs()) {
                    if (!et_succ.estActivite()) {
                        e.enleverSuccesseurs(et_succ);
                        throw new MondeException("Un guichet ne peut pas être succédé par un autre guichet..");
                    }
                    if (et_succ.estActivite())
                        ((ActiviteIG) et_succ).setActiviteRestreinte(true);
                }
            }
        }
    }
    private void verifierMondeIG() throws MondeException{
        boolean existEntr=false, existSort=false, gotSortie=false, existGuich=false;
        for(EtapeIG st : this)
        {
            if(!st.estActivite())
            {
                existGuich = true;
                for(EtapeIG step_succ : st.getSuccesseurs()){
                    if(!step_succ.estActivite()){
                        st.enleverSuccesseurs(step_succ);
                        throw new MondeException("Un guichet ne peut être le successeur d'un autre guichet");
                    }
                    if(step_succ.estActivite()){
                        ((ActiviteIG)step_succ).setActiviteRestreinte(true);
                        if(((ActiviteIG)step_succ).estSortie())
                            gotSortie = true;
                        else if(!gotSortie)
                            gotSortie = this.checkSuccesseurs(step_succ);
                    }
                }
            }
            else if(((ActiviteIG)st).estSortie())
                existSort = true;
            if(st.isEntry())
                existEntr = true;
        }
        if(!existGuich)
            gotSortie = true;
        if(existEntr && existSort){
            if(!gotSortie)
                throw new MondeException("Un guichet doit mener à une sortie !") ;
        }else
            throw new MondeException("Votre monde doit comporter une entree et une/plsrs sortie(s) ...") ;
    }
    private boolean checkSuccesseurs(EtapeIG st)
    {
        boolean gotSortie = false;
        Iterator<EtapeIG> ite = st.getSuccesseurs().iterator();
        while(ite.hasNext() && !gotSortie)
        {
            EtapeIG etapeSucc = ite.next();
            if(etapeSucc.estActivite())
            {
                if(((ActiviteIG)etapeSucc).estSortie())
                    gotSortie = true;
                else
                    gotSortie = this.checkSuccesseurs(etapeSucc);
            }
        }
        return gotSortie;
    }

    private void creerMonde() throws MondeException {
        this.verifierMondeIG();
        this.correspondanceEtapes = new CorrespondanceEtapes(this.getHmSteps());
        this.m = new Monde();

        if (this.gaussienne) {
            this.m.setGaussienne(this.gaussienne, this.delai, this.ecartsTemps);
        } else if (this.poisson) {
            this.m.setPoisson(this.poisson, this.delai);
        } else {
            this.m.setUniforme(this.uniforme, this.delai, this.ecartsTemps);
        }

        for(EtapeIG e: this) {
            if (e.estActivite()) {
                Etape et = new Activite(e.getName(), Integer.parseInt(((ActiviteIG) e).getDelai()), Integer.parseInt(((ActiviteIG) e).getEcartTps()));
                this.correspondanceEtapes.ajouter(e, et);
                if (e.isEntry()) {
                    this.m.aCommeEntree(et);
                    if (e.isExit()) {
                        this.m.aCommeSortie(et);
                    }
                } else if (e.isExit()) {
                    this.m.aCommeSortie(et);
                }
            } else if (e.estActiviteRestreinte()) {
                Etape et = new ActiviteRestreinte(e.getName(), Integer.parseInt(((ActiviteIG) e).getDelai()), Integer.parseInt(((ActiviteIG) e).getEcartTps()));
                this.correspondanceEtapes.ajouter(e, et);
                if (e.isEntry()) {
                    this.m.aCommeEntree(et);
                    if (e.isExit()) {
                        this.m.aCommeSortie(et);
                    }
                } else if (e.isExit()) {
                    this.m.aCommeSortie(et);
                }
            } else if (e.estUnGuichet()) {
                Etape et = new Guichet(e.getName(), ((GuichetIG) e).getNbJetons());
                if (e.isEntry()) {
                    this.m.aCommeEntree(et);
                }

                this.correspondanceEtapes.ajouter(e, et);
            }
        }

        for (EtapeIG e : this) {
            for (EtapeIG etigSucc : e.getSuccesseurs()) {
                this.correspondanceEtapes.get(e).ajouterSuccesseur(this.correspondanceEtapes.get(etigSucc));
            }
            this.m.ajouter(this.correspondanceEtapes.get(e));
        }
    }

    public void setAllowedLoops(boolean b)
    {
        this.allowLoop = b;
        if(!b)
            this.removeArcsLoop();
    }

    public void removeArcsLoop(){
        for (ArcIG arc : this.loopArcs) {
            supprimerArc(arc);
            arc.getSrc().getStep().enleverSuccesseurs(arc.getDest().getStep());
        }
    }

    @Override
    public void reagir(){
        Task<Void> task = new Task<Void>() {
            protected Void call() throws Exception {
                MondeIG.this.notifierObservateurs();
                if(MondeIG.this.simulation != null) {
                    if (MondeIG.this.simulation.getClientManager() != null && MondeIG.this.simulation.getClientManager().getClientsNb() > 0) {
                        MondeIG.this.isSimulationActive = false;
                    }
                    else{
                        MondeIG.this.isSimulationActive=true;
                    }
                }
                this.done();
                return null;
            }
        };
        Thread thread = new Thread((Runnable) task) ;
        thread.setDaemon(true) ;
        thread.start() ;
    }

    public boolean isContainGuichet() {
        return containGuichet;
    }

    public boolean isAllowLoop() {
        return allowLoop;
    }

    public int getDelai() {
        return this.delai;
    }

    public int getEcartsTemps() {
        return this.ecartsTemps;
    }

    public int getNbClients() {
        return nbClients;
    }

    public void setDelai(int delai) {
        this.delai = delai;
    }

    public void setEcartsTemps(int ecartsTemps) {
        this.ecartsTemps = ecartsTemps;
    }

    public boolean isPoisson() {
        return poisson;
    }

    public boolean isGaussienne() {
        return gaussienne;
    }

    public boolean isUniforme() {
        return uniforme;
    }

    public void setPoisson(boolean poisson) {
        this.poisson = poisson;
    }

    public void setGaussienne(boolean gaussienne) {
        this.gaussienne = gaussienne;
    }

    public void setUniforme(boolean uniforme) {
        this.uniforme = uniforme;
    }

    public boolean isSimulationActive() {
        return isSimulationActive;
    }

    public void setSimulationActive(boolean simulationActive) {
        isSimulationActive = simulationActive;
    }

    public void setContainGuichet(boolean containGuichet) {
        this.containGuichet = containGuichet;
    }

    public void save(File filename) {
        try {
            this.noPoint = FabriqueIdentifiant.getInstance().getNoPoint();
            this.noEtape = FabriqueIdentifiant.getInstance().getId_Etape();
            ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename));
            oos.writeObject(this);
            this.reagir();
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void load(File filename) {
        try {
            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename));
            MondeIG m = (MondeIG)ois.readObject();
            this.listArcs = m.getListArcs();
            this.hmSteps = m.getHmSteps();
            this.selectedBows = m.getSelectedBows();
            this.selectedSteps = m.getSelectedSteps();
            this.loopArcs = m.getLoopArcs();
            setContainGuichet( m.isContainGuichet() );
            this.allowLoop = m.isAllowLoop();
            this.noEtape = m.noEtape;
            this.noPoint = m.noPoint;
            FabriqueIdentifiant.getInstance().init(this.noEtape, this.noPoint);
            this.reagir();
        } catch (Exception var4) {
            var4.printStackTrace();
        }

    }

    public void setNbJetons(String nbJetons) {
        if (nbJetons.matches("[1-9]+")) {
            for(EtapeIG e : this.selectedSteps) {
                if (!e.estActivite()) {
                    GuichetIG guichet = (GuichetIG)e;
                    guichet.setNbJetons(Integer.parseInt(nbJetons));
                }
            }
        }
    }

    public void setDelaiEcartTemps(String delai, String ecartTemps) throws TwiskException {
        ActiviteIG act;
        if (delai.matches("[1-9]+")) {
            if (ecartTemps.matches("[1-9]+")) {
                for(EtapeIG e : this.selectedSteps) {
                    if (e.estActivite()) {
                        act = (ActiviteIG)e;
                        act.setDelai(Integer.parseInt(delai));
                        act.setEcartTps(Integer.parseInt(ecartTemps));
                    }
                }
            } else {
                for(EtapeIG st : this.selectedSteps)
                {
                    if (st.estActivite()) {
                        act = (ActiviteIG)st;
                        act.setDelai(Integer.parseInt(delai));
                    }
                }
            }
        } else {
            if (!ecartTemps.matches("[1-9]+")) {
                throw new SetTpsEcartException("Veuillez ne mettre que des entiers !",ecartTemps);
            }

            for(EtapeIG st2 : this.selectedSteps) {
                if (st2.estActivite()) {
                    act = (ActiviteIG)st2;
                    act.setEcartTps(Integer.parseInt(ecartTemps));
                }
            }
        }
    }

    public void setNbClients(int nbClients) {
        this.nbClients = nbClients;
    }

    public void simuler() throws MondeException {
        this.isSimulationActive = true;

        this.creerMonde();

        MondeIG mondeig = this;
        Task<Void> task = new Task<Void>() {
            public Void call() throws Exception {
                ClassLoader parent = MondeIG.class.getClassLoader();
                ClassLoaderPerso classLoaderPerso = new ClassLoaderPerso(parent);
                Class<?> simulationClass = classLoaderPerso.loadClass("twisk.simulation.Simulation");
                Constructor<?> co1 = simulationClass.getConstructor();
                Object o = co1.newInstance();
                MondeIG.this.simulationObjet = o;
                (MondeIG.this.simulation = (InterfSimulation) o).setNbClients(MondeIG.this.getNbClients());
                MondeIG.this.getClientManager = simulationClass.getMethod("getClientManager");
                MondeIG.this.simulation.ajouterObservateur(mondeig);
                MondeIG.this.simulation.simuler(MondeIG.this.m);
                this.done();
                MondeIG.this.isSimulationActive = false;
                MondeIG.this.reagir();

                return null;
            }

        };
        GestionnaireThreads.getInstance().lancer(task);
    }

    public ClientManager getClientManager()
    {
        ClientManager cm = null;
        try{
            cm = (ClientManager) this.getClientManager.invoke(this.simulationObjet, (Object[]) null);
        }catch(InvocationTargetException | IllegalAccessException excp){
            excp.printStackTrace();
        }
        return cm;
    }

    public void relocate(EtapeIG st, double abs, double ord)
    {

        for (EtapeIG etape : this) {
            if (etape.equals(st)) {
                st.setPosX(abs);
                st.setPosY(ord);
            }
        }
        this.reagir();
    }

    public void ajouterSortie() throws TwiskException {
        for(EtapeIG st : this.selectedSteps)
        {
            if(st.estActivite())
            {
                addSortie(st);
            }
        }
        Unselect();
    }
}
