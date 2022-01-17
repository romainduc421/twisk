package twisk.monde;

import twisk.outils.FabriqueNumero;

import java.util.ArrayList;
import java.util.Iterator;

public class Monde implements Iterable<Etape> {
    private GestionnaireEtapes et_gest = new GestionnaireEtapes();
    private SasSortie exit;
    private SasEntree entry;

    /**
     * Constructeur de Monde sans paramètre
     */
    public Monde() {
        FabriqueNumero.getInstance().reset();
        entry = new SasEntree();
        exit = new SasSortie();
    }

    /**
     * Définit une collection d'étapes comme entrées potentielles d'un monde donné
     *
     * @param steps paramètre à taille variable d'étapes (collection d'étapes)
     */
    public void aCommeEntree(Etape... steps) {
        entry.ajouterSuccesseur(steps);
    }

    /**
     * Définit une collection d'étapes comme sorties potentielles d'un monde donné
     *
     * @param steps paramètre à taille variable d'étapes (collection d'étapes)
     */
    public void aCommeSortie(Etape... steps) {
        int k;
        for (k = 0; k <= steps.length - 1; k++)
            assert !steps[k].estUnGuichet() : "le sas de Sortie ne peut pas suivre un guichet !!";
        for (int j = 0; j <= steps.length - 1; j++) {
            Etape et = steps[j];
            et.ajouterSuccesseur(exit);
        }
    }

    /**
     * Ajout d'un nombre variable d'étapes au monde considéré
     *
     * @param steps paramètre à taille variable d'étapes (collection d'étapes)
     */
    public void ajouter(Etape... steps) {
        et_gest.ajouter(steps);
    }

    /**
     * @return nb d'étapes dans le monde considéré
     */
    public int nbEtapes() {
        return et_gest.nbEtapes();
    }

    /**
     * @return nb de guichets dans le monde considéré
     */
    public int nbGuichets() {
        int res = 0;
        for (Etape step : this) {
            if (step.estUnGuichet())
                res += 1;
        }
        return res;
    }

    /**
     * rendre la classe Monde itérable
     *
     * @return l'itérateur des étapes constituant le monde
     */
    @Override
    public Iterator<Etape> iterator() {
        ArrayList<Etape> steps_list = new ArrayList<>(et_gest.nbEtapes());
        for (Etape et : et_gest)
            steps_list.add(et);
        return steps_list.iterator();
    }

    @Override
    public String toString() {
        String res;
        StringBuilder sb = new StringBuilder(3);
        sb.append(entry.toString());
        sb.append(et_gest.toString());
        sb.append(exit.toString());
        res = sb.toString();
        return res;
    }


    public SasSortie getExit() {
        return exit;
    }

    public SasEntree getEntry() {
        return entry;
    }

    public String getNameEntry(){
        return entry.getNom();
    }

    public String getNameExit(){
        return exit.getNom();
    }

    public int getNoExit(){
        return exit.getNo();
    }

    public int getNoEntry(){
        return entry.getNo();
    }

    public String getNameStepNo(int k){
        return getStepFromNumber(k).getNom();
    }

    public int getNoStepFromNumber(int k){
        return getStepFromNumber(k).getNo();
    }

    public Etape getStepFromNumber(int k){
        return getEt_gest().getStep(k);
    }
    public GestionnaireEtapes getEt_gest(){
        return this.et_gest;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (o == null || getClass() != o.getClass())
            return false;
        Monde etapes = (Monde) o;
        return et_gest.equals(etapes.et_gest);
    }

    public String toC() {
        StringBuilder sb_C = new StringBuilder(9);
        sb_C.append("#include \"def.h\"\n"
                + "#include<time.h>\n"
                + "#include \"delais.c\"\n"
                + "#include \"prototypes.h\"\n"
        );
        sb_C.append("#define "+entry.nom.toUpperCase()+" "+this.entry.no+"\n");
        sb_C.append("#define "+exit.nom.toUpperCase()+ " "+this.exit.no+"\n");
        for (Etape e : this.et_gest) {
            sb_C.append("#define " + e.getNom().toUpperCase() + " " + e.getNo() + "\n");
        }

        for (Etape st : et_gest) {
            if (st.estUnGuichet()) {
                sb_C.append("#define SEM_ID_" + ((Guichet) st).getNom().toUpperCase() + " " + ((Guichet) st).getNoSemaphore() + "\n");
            }
        }

        sb_C.append(entry.toC());
        sb_C.append(exit.toC());


        return sb_C.toString();
    }

    public void setUniforme(boolean res, int delai, int ecartTemps) {
        this.entry.setGaussienne(false, 0, 0);
        this.entry.setPoisson(false, 0);
        this.entry.setUniforme(res, delai, ecartTemps);
    }

    public void setPoisson(boolean res, double lambda) {
        this.entry.setUniforme(false, 0, 0);
        this.entry.setGaussienne(false, 0.0, 0.0);
        this.entry.setPoisson(res, lambda);
    }

    public void setGaussienne(boolean res, double moyenne, double ecartype) {
        this.entry.setUniforme(false, 0, 0);
        this.entry.setPoisson(false, 0.0);
        this.entry.setGaussienne(res, moyenne, ecartype);
    }

}
