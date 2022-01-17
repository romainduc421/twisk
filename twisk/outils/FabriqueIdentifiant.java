package twisk.outils;

public class FabriqueIdentifiant {

    private static FabriqueIdentifiant Instance = new FabriqueIdentifiant();
    private int id_Etape = 0;
    private int noPoint;

    /**
     * Constructeur du singleton FabriqueIdentifiant
     */
    private FabriqueIdentifiant(){

    }


    /**
     * getters & setters
     * @param numEtape
     */
    public void setIdStep(int numEtape){
        this.id_Etape = numEtape;
    }

    public static FabriqueIdentifiant getInstance(){
        return Instance;
    }


    /**
     * methodes permettant de passer au numero suivant
     */
    public void incrementer(){
        setIdStep(id_Etape+1);
    }

    public String getId_Step(){
        this.incrementer();
        return Integer.toString(this.getId_Etape());
    }

    public String getId_Point()
    {
        ++this.noPoint;
        return Integer.toString(this.noPoint);
    }

    /**
     * reset le compteur
     */
    public void reset(){
        id_Etape = 0;
    }

    public int getId_Etape() {
        return id_Etape;
    }

    public int getNoPoint() {
        return noPoint;
    }

    public void init(int noEtape, int noPoint)
    {
        this.noPoint = -1;
        this.id_Etape = 0;
    }
}
