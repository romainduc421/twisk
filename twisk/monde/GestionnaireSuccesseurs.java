package twisk.monde;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.Objects;

public class GestionnaireSuccesseurs implements Iterable<Etape>
{

    protected ArrayList<Etape> succs;

    /**
     * Constructeur GS
     */
    public GestionnaireSuccesseurs(){
        succs = new ArrayList<>();
    }

    /**
     * Ajoute une collection d'étapes à succs
     * @param steps  paramètre à taille variable d'étapes (collection d'étapes)
     */
    public void ajouter(Etape... steps) {
        Collections.addAll(succs, steps);
    }

    /**
     * @return nb d'étapes
     */
    public int nbEtapes(){
        return succs.size();
    }

    /**
     * @return un itérateur d'étapes rendant la classe GS itérable
     */
    @Override
    public Iterator<Etape> iterator() {
        return succs.iterator();
    }

    public Etape getAt(int indx){
        return succs.get(indx);
    }

    @Override
    public String toString(){
        String res;
        StringBuilder sb = new StringBuilder(nbEtapes());
        Iterator iterator_gestSucc = this.iterator();
        while(iterator_gestSucc.hasNext()){
            Etape et = (Etape) iterator_gestSucc.next();
            sb.append(" - "+et.nom);
        }
        res = sb.toString();
        return res;
    }

    public boolean isEmpty(){
        return this.succs.isEmpty();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        GestionnaireSuccesseurs etapes = (GestionnaireSuccesseurs) o;
        return Objects.equals(succs, etapes.succs);
    }

}
