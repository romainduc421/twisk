package twisk.mondeIG;

import java.io.Serializable;

public class PointDeControleIG implements Serializable {

    private String Id;
    private EtapeIG step;
    private double abs_centre, ord_centre;
    private boolean isSelected;

    /**
     * Constructeur PointDeControleIG avec coord de placement
     * @param Id
     * @param step
     * @param x
     * @param y
     */
    public PointDeControleIG(String Id, EtapeIG step, double x,double y){
        this.Id = Id;
        this.step = step;
        setAbs_centre(x);
        setOrd_centre(y);
        setSelected(false);
    }

    /**
     * Constructeur PointDeControleIG
     * @param Id
     * @param step
     */
    public PointDeControleIG(String Id, EtapeIG step){
        this.Id = Id;
        this.step = step;
        setAbs_centre(this.getStep().getPosX()-20);
        setOrd_centre(this.getStep().getPosY()+20);
        setSelected(false);
    }


    /**
     * getters
     */
    public String getId() {
        return Id;
    }
    public EtapeIG getStep() {
        return step;
    }
    public double getAbs_centre() {
        return abs_centre;
    }
    public double getOrd_centre() {
        return ord_centre;
    }
    public boolean isSelected() {
        return isSelected;
    }

    /**
     * setters
     */
    public void setAbs_centre(double abs_centre) {
        this.abs_centre = abs_centre;
    }
    public void setOrd_centre(double ord_centre) {
        this.ord_centre = ord_centre;
    }
    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    /**
     * toString() de PointDeControleIG
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(12);
        sb.append("identifiant : ").append(this.Id).append("\n");
        sb.append("centre abs : ").append(this.abs_centre).append("\n");
        sb.append("centre ord : ").append(this.ord_centre).append("\n");
        sb.append("etape liee : ").append(this.step).append("\n");
        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        PointDeControleIG that = (PointDeControleIG) o;
        return Double.compare(that.abs_centre, abs_centre) == 0 && Double.compare(that.ord_centre, ord_centre) == 0 && Id.equals(that.Id)
        && this.getStep().equals(that.getStep());
    }
}
