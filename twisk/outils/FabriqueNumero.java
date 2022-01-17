package twisk.outils;

public class FabriqueNumero {
    private static FabriqueNumero instance_fb = new FabriqueNumero();
    private int cptEtape , cptNumeroMonde;
    private int cptSemaphore;

    private FabriqueNumero(){
        this.cptSemaphore = 0;
        this.cptEtape = -1;
        this.cptNumeroMonde = -1;
    }
    public static FabriqueNumero getInstance(){
        return instance_fb;
    }

    public int getNoEtape(){
        this.cptEtape = this.cptEtape + 1;
        return this.cptEtape;
    }

    public int getNumeroSemaphore(){
        this.cptSemaphore = this.cptSemaphore+1;
        return this.cptSemaphore;
    }

    public int getNumeroLib(){
        this.cptNumeroMonde = this.cptNumeroMonde+1;
        return this.cptNumeroMonde;
    }

    public void reset(){
        this.cptEtape = -1;
        this.cptSemaphore = 0;
    }
}
