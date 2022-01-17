package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class EtapeTest {

    private SasEntree entry;
    private SasSortie exit;
    private Activite act1, act2;
    private Guichet guichet1, guichet2, guichet3;
    private GestionnaireSuccesseurs gest_succ1;
    private GestionnaireSuccesseurs gest_succ2;

    @BeforeEach
    void setUp() {
        entry = new SasEntree();
        exit = new SasSortie();
        act1 = new Activite("Activité 1", 5, 2);
        act2 = new Activite ("Activité 2", 3, 2);
        guichet1 = new Guichet("Guichet 1", 9);
        guichet2 = new Guichet("Guichet 2", 7);
        guichet3 = new Guichet("Guichet 3",5);
        entry.ajouterSuccesseur(guichet1);
        act1.ajouterSuccesseur(guichet2);
        act2.ajouterSuccesseur(guichet3);
        guichet1.ajouterSuccesseur(act1);
        guichet2.ajouterSuccesseur(act2);
        guichet3.ajouterSuccesseur(act1, exit);
        gest_succ1 = new GestionnaireSuccesseurs();
        gest_succ2 = new GestionnaireSuccesseurs();
        GestionnaireSuccesseurs gest_succ3 = new GestionnaireSuccesseurs();
        gest_succ1.ajouter(entry,guichet1,exit);
        FabriqueNumero.getInstance().reset();
    }

    @Test
    void isActivity(){
        assertTrue (entry.estUneActivite() );
        assertTrue( exit.estUneActivite() );
        assertTrue( !guichet1.estUneActivite() );

    }

    @Test
    void isAWicket(){
        assertTrue( !entry.estUnGuichet() );
        assertTrue( !exit.estUnGuichet() );
        assertTrue( guichet1.estUnGuichet() );
    }

    @Test
    void getNo(){
        assertEquals(entry.getNo(), 0);
        assertEquals(exit.getNo(), 1);
        assertEquals(guichet3.getNo(), 6);
    }

    @Test
    void ajouterSuccesseur(){
        assertEquals(entry.nbSuccesseur(), 1);
        assertEquals(exit.nbSuccesseur(), 0);
        assertEquals(guichet3.nbSuccesseur(), 2);
    }

    @Test
    void iterator(){
        ArrayList<Etape> verifEtape = new ArrayList(3);
        verifEtape.addAll(Arrays.asList(entry, guichet1, exit));

        int indx = 0;

        for(Etape e : gest_succ1){
            assertTrue( e.equals(verifEtape.get(indx)) );
            indx++;
        }

        verifEtape = new ArrayList(0);
        indx = 0;

        for(Etape e : gest_succ2){
            assertTrue( e.equals(verifEtape.get(indx)) );
            indx++;
        }
    }

    @Test
    void getNumero(){
        assertEquals(entry.getNo(),0);
        assertEquals(exit.getNo(),1);
        assertEquals(act1.getNo(),2);
        assertEquals(act2.getNo(),3);
        assertEquals(guichet1.getNo(),4);
        assertEquals(guichet1.getNoSemaphore(),1);
        assertEquals(guichet1.getNbJetons(),9);
        assertEquals(guichet2.getNo(),5);
        assertEquals(guichet2.getNoSemaphore(),2);
        assertEquals(guichet2.getNbJetons(),7);
        assertEquals(guichet3.getNo(),6);
        assertEquals(guichet3.getNoSemaphore(),3);
        assertEquals(guichet3.getNbJetons(),5);
        FabriqueNumero.getInstance().reset();
    }
}