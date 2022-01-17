package twisk.mondeIG;

import java.io.Serializable;
import java.util.*;

public abstract class EtapeIG implements Iterable<PointDeControleIG>, Serializable {

    private String name, id;
    private double posX, posY;
    private int largeur, hauteur;
    private List<PointDeControleIG> control_points;
    private boolean stepDetected;
    private boolean isEntry, isExit;
    private HashMap<String, EtapeIG> successeurs;
    protected boolean activite, activiteRestreinte, guichet;
    /**
     * Constructeur EtapeIG (non instanciable)
     * @param nom   nom de l'etape considérée
     * @param idf   identifiant unique de l'étape
     * @param larg  largeur du visuel de l'etape
     * @param haut  hauteur du visuel de l'etape
     */
    public EtapeIG(String nom, String idf, int larg, int haut){
        this.setName(nom);
        this.setId(idf);
        this.setLargeur(larg);
        this.setHauteur(haut);
        Random rand = new Random();
        this.successeurs = new HashMap<>(10);
        this.posX = rand.nextInt(810 - 20 + 1)+20;
        this.posY = rand.nextInt(505 - 20 + 1)+20;
        if(this.estActivite()) {
            this.control_points = new ArrayList<PointDeControleIG>(4);
            Collections.addAll(this.control_points, new PointDeControleIG(this.getId(), this, this.getPosX() + (double) (this.getLargeur() / 2), this.getPosY()),
            new PointDeControleIG(this.getId(), this, this.getPosX(), this.getPosY() + (double) (this.getHauteur() / 2)),
            new PointDeControleIG(this.getId(), this, this.getPosX() + (double) this.getLargeur(), this.getPosY() + (double) (this.getHauteur() / 2)),
            new PointDeControleIG(this.getId(), this, this.getPosX() + (double) (this.getLargeur() / 2), this.getPosY() + (double) this.getHauteur()));
        }
        else{
            this.control_points = new ArrayList<PointDeControleIG>(2);
            Collections.addAll(this.control_points, new PointDeControleIG(this.getId(),this,this.getPosX()+(double)(this.getLargeur()), this.getPosY()+(double)(getHauteur()/2)),
            new PointDeControleIG(this.getId(),this,this.getPosX(), this.getPosY()+(double)(getHauteur()/2)));
        }
        this.setEntry(false);
        this.setExit(false);
        this.setStepDetected(false);
    }


    @Override
    public boolean equals(Object ob){
        boolean res;
        if(ob == this)
            res = true;
        else{
            if(!(ob instanceof  EtapeIG))
                res = false;

            EtapeIG step = (EtapeIG) ob;
            res = Objects.equals(id,step.id) && Objects.equals(name,step.name);
        }
        return res;
    }

    /**
     * getters
     */

    public String getId(){
        return this.id;
    }

    public String getName() {
        return this.name;
    }

    public double getPosX() {
        return this.posX;
    }

    public double getPosY() {
        return this.posY;
    }

    public int getLargeur() {
        return this.largeur;
    }

    public int getHauteur() {
        return this.hauteur;
    }

    public List<PointDeControleIG> getControl_points() {
        return this.control_points;
    }

    public boolean isStepDetected() {
        return stepDetected;
    }

    public boolean isEntry() {
        return isEntry;
    }

    public boolean isExit() {
        return isExit;
    }

    public Collection<EtapeIG> getSuccesseurs() {
        return successeurs.values();
    }

    /**
     * Setters
     */

    public void setName(String name) {
        this.name = name;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPosX(double posX) {
        this.posX = posX;
        if(this.estActivite()){
            (this.control_points.get(0)).setAbs_centre(this.getPosX()+(double) (this.getLargeur()/2));
            (this.control_points.get(1)).setAbs_centre(this.getPosX()+(double) (this.getLargeur()));
            (this.control_points.get(2)).setAbs_centre(this.getPosX()+(double) (this.getLargeur()/2));
            (this.control_points.get(3)).setAbs_centre(this.getPosX());
        }
        else{
            (this.control_points.get(0)).setAbs_centre(this.getPosX()+(double) (this.getLargeur()));
            (this.control_points.get(1)).setAbs_centre(this.getPosX());
        }
    }

    public void setPosY(double posY) {
        this.posY = posY;
        if(this.estActivite()){
            (this.control_points.get(0)).setOrd_centre(this.getPosY());
            (this.control_points.get(1)).setOrd_centre(this.getPosY()+(double) (this.getHauteur()/2));
            (this.control_points.get(2)).setOrd_centre(this.getPosY()+(double) (this.getHauteur()));
            (this.control_points.get(3)).setOrd_centre(this.getPosY()+(double) (this.getHauteur()/2));
        }
        else{
            (this.control_points.get(0)).setOrd_centre(this.getPosY()+(double) (this.getHauteur()/2));
            (this.control_points.get(1)).setOrd_centre(this.getPosY()+(double) (this.getHauteur()/2));
        }
    }

    public void setLargeur(int largeur) {
        this.largeur = largeur;
    }

    public void setHauteur(int hauteur) {
        this.hauteur = hauteur;
    }

    public void setStepDetected(boolean stepDetected) {
        this.stepDetected = stepDetected;
    }

    public void setEntry(boolean entry) {
        isEntry = entry;
    }

    public void setExit(boolean exit) {
        isExit = exit;
    }

    /**
     * toString de EtapeIG
     * @return string composée du nom et de l'id
     */

    @Override
    public String toString() {
        return (new StringBuilder(12).append("EtapeIG { ")
        .append("name='").append(this.name).append('\'')
        .append(", id='").append(this.id).append('\'')
        .append(", larg='").append(this.largeur).append('\'')
        .append(", hauteur='").append(this.hauteur).append('\'')
        .append(" } ")).toString();
    }

    /**
     * iterateur de PointDeControleIG
     * @return  Iterator\<PointDeControleIG\>
     */
    @Override
    public Iterator<PointDeControleIG> iterator(){
        return this.getControl_points().iterator();
    }

    public boolean estActivite(){
        return false;
    }
    public boolean estUnGuichet(){
        return false;
    }
    public boolean estActiviteRestreinte(){
        return false;
    }

    public void ajouterSuccesseurs(EtapeIG... stepsIG){
        EtapeIG[] tab = stepsIG;
        int size = stepsIG.length;
        for(int y=0; y < size; y++)
        {
            EtapeIG st = tab[y] ;
            this.successeurs.put(st.getId(), st);
        }
    }

    public void enleverSuccesseurs(EtapeIG... stepsIG){
        EtapeIG[] tab = stepsIG;
        int size = stepsIG.length;
        for(int y=0; y < size; y++)
        {
            EtapeIG st = tab[y];
            this.successeurs.remove(st.getId());
        }
    }

    public boolean estAccessibleDepuis(EtapeIG st)
    {
        boolean accessible=false;
        if(st.getSuccesseurs().contains(this))
            accessible=true;
        else {
            for(EtapeIG step : st.getSuccesseurs()){
                for(EtapeIG st2 : step.getSuccesseurs()) {
                    if (st2 == this) {
                        accessible = true;
                        break;
                    }
                }
            }
        }
        return accessible;
    }
}
