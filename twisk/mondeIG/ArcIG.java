package twisk.mondeIG;

import java.io.Serializable;

public abstract class ArcIG implements Serializable {

    private PointDeControleIG src, dest;
    private boolean bowDetected;

    /**
     * Constructeur ArcIG
     */
    public ArcIG( ){
        setBowDetected(false);
    }

    /**
     * getters & setters
     */
    public PointDeControleIG getSrc() {
        return src;
    }
    public PointDeControleIG getDest() {
        return dest;
    }
    public boolean isBowDetected() {
        return bowDetected;
    }

    public void setSrc(PointDeControleIG src) {
        this.src = src;
    }
    public void setDest(PointDeControleIG dest) {
        this.dest = dest;
    }
    public void setBowDetected(boolean bowDetected) {
        this.bowDetected = bowDetected;
    }

    /**
     * toString() de ArcIG
     */
    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder(6);
        sb.append("Origin's point : ").append(this.getSrc().getId()).append("\n")
        .append("Destination's point : ").append(this.getDest().getId()).append("\n");
        return sb.toString();
    }


    /**
     * determiner si l'arc est une ligne droite ou une courbe
     * @return un booleen determinant la nature de l'arc
     */
    public abstract boolean isLine();
    public abstract boolean isCurve();

}
