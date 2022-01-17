package twisk.simulation;

import twisk.monde.Etape;

public class Client {
    private int rank;
    private Etape step;
    private int Customer_nb;

    public Client(int no){
        this.Customer_nb = no;
    }

    public void allerA (Etape st, int rank){
        this.setRank(rank);
        this.setStep(st);
    }

    /*
    getters
     */
    public int getRank() {
        return rank;
    }
    public Etape getStep() {
        return step;
    }
    public int getCustomer_nb() {
        return Customer_nb;
    }
    /*
    setters
     */

    public void setRank(int rank) {
        this.rank = rank;
    }

    public void setStep(Etape step) {
        this.step = step;
    }

    @Override
    public String toString(){
        int no = this.getCustomer_nb();
        if(!(this.getStep() == null))
            return "Client: " + no + "\t step \t(?)\trank : " + this.getRank();
        else
            return "Client: " + "\t step " + this.getStep().getNo() + "\t(" + this.getStep().getNom() + ")\t rank : " + this.getRank();

    }
}
