package twisk.outils;

public class TailleComposants {

    private static TailleComposants Instance = new TailleComposants();

    private int[] button;
    private int[] viewStep;
    private int[] see_customer;
    private int[] viewCounter;
    private double applicationWidth;
    private double applicationHeight;
    private double scrollZoom;
    private double panneauWidth = 300.0D;
    private double navigateurWidth = 100.0D;
    private double menuHeight = 30.0D;
    private double pageWidth;
    private double pageHeight;
    private int largeurMinGuichet;
    private int largeurMaxGuichet;
    private int largeurMinActivite = 150;
    private int largeurMaxActivite = 400;
    private int hauteurMinActivite;
    private int hauteurMaxActivite;

    /**
     * constructeur TailleComposants (singleton)
     */
    private TailleComposants() {
        this.button = new int[2];
        this.setButton(new int []{45,45});
        this.viewStep = new int[2];
        this.setViewStep(new int[]{180,111});
        this.see_customer = new int[2];
        this.setSee_customer(new int[]{40,65});
        this.viewCounter = new int[2];
        this.setViewCounter(new int[]{210,70});
        this.scrollZoom = 5.0;
        this.largeurMinGuichet = 100;
        this.largeurMaxGuichet = 400;
        this.hauteurMaxActivite = this.largeurMinActivite - (this.viewStep[0] - this.viewStep[1]);
        this.hauteurMinActivite = this.largeurMaxActivite - (this.viewStep[0] - this.viewStep[1]);
    }

    public static TailleComposants getInstance(){
        return Instance;
    }

    /**
     * getters
     */
    public int[] getButton() {
        return button;
    }

    public int[] getViewStep() {
        return viewStep;
    }

    public int[] getSee_customer() {
        return see_customer;
    }

    public int[] getViewCounter() {
        return viewCounter;
    }

    public double getApplicationWidth() {
        return applicationWidth;
    }

    public double getApplicationHeight() {
        return applicationHeight;
    }

    public double getScrollZoom() {
        return scrollZoom;
    }

    public double getPanneauWidth() {
        return panneauWidth;
    }

    public double getNavigateurWidth() {
        return navigateurWidth;
    }

    public double getMenuHeight() {
        return menuHeight;
    }

    public double getPageWidth() {
        return pageWidth;
    }

    public double getPageHeight() {
        return pageHeight;
    }

    public int getLargeurMinGuichet() {
        return largeurMinGuichet;
    }

    public int getLargeurMaxGuichet() {
        return largeurMaxGuichet;
    }

    public int getLargeurMinActivite() {
        return largeurMinActivite;
    }

    public int getLargeurMaxActivite() {
        return largeurMaxActivite;
    }

    public int getHauteurMinActivite() {
        return hauteurMinActivite;
    }

    public int getHauteurMaxActivite() {
        return hauteurMaxActivite;
    }

    /**
     * setters
     */
    public void setButton(int[] button) {
        this.button = button;
    }

    public void setViewStep(int[] viewStep) {
        this.viewStep = viewStep;
    }

    public void setSee_customer(int[] see_customer) {
        this.see_customer = see_customer;
    }

    public void setViewCounter(int[] viewCounter){
        this.viewCounter = viewCounter;
    }

    public void setApplicationWidth(double applicationWidth) {
        this.applicationWidth = applicationWidth;
    }

    public void setApplicationHeight(double applicationHeight) {
        this.applicationHeight = applicationHeight;
    }

    public void setScrollZoom(double scrollZoom) {
        this.scrollZoom = scrollZoom;
    }

    /**
     * reset les dimensions
     */
    public void reset(){
        this.button[0] = 0;
        this.button[1] = 0;
        this.see_customer[0] = 0;
        this.see_customer[1] = 0;
        this.viewStep[0] = 0;
        this.viewStep[1] = 0;
    }
}
