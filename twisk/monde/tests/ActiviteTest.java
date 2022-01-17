package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteTest extends EtapeTest {

    private GestionnaireSuccesseurs gest1,gest2,gest3;
    private SasEntree sasEntree;
    private SasSortie sasSortie;
    private Activite ac1,ac2,ac3;
    private Guichet g1,g2;

    @BeforeEach
    void setUp(){
        gest1=new GestionnaireSuccesseurs();
        gest2=new GestionnaireSuccesseurs();
        gest3=new GestionnaireSuccesseurs();
        sasEntree=new SasEntree();
        sasSortie=new SasSortie();
        ac1=new Activite("act1");
        ac2=new Activite("ac2", 1, 1);
        ac3=new ActiviteRestreinte("act3", 2, 2);
        g1=new Guichet("Guichet1",1);
        g2=new Guichet("Guichet2");
        sasEntree.ajouterSuccesseur(g1);
        g1.ajouterSuccesseur(ac1,g2);
        g2.ajouterSuccesseur(ac2,ac3,sasSortie);
        gest1.ajouter(sasEntree);
        gest2.ajouter(g1,ac1);
        gest3.ajouter(ac2,g2,ac3);

    }

    @Test
    void isActivity(){
        assertTrue(ac1.estUneActivite());
        assertTrue(ac2.estUneActivite());
        assertTrue(ac3.estUneActivite());
        assertTrue(sasEntree.estUneActivite());
        assertTrue(sasSortie.estUneActivite());
        assertFalse(g1.estUneActivite());
    }

    @Test
    void isAWicket(){
        assertFalse(ac1.estUnGuichet());
        assertFalse(ac2.estUnGuichet());
        assertFalse(ac3.estUnGuichet());
        assertFalse(sasEntree.estUnGuichet());
        assertFalse(sasSortie.estUnGuichet());
        assertTrue(g1.estUnGuichet());
    }

    @Test
    void getTime(){
        assertEquals(ac1.getTemps(), 0);
        assertEquals(ac2.getTemps(), 1);
        assertEquals(ac3.getTemps(), 2);
    }

    @Test
    void getLimitTime(){
        assertEquals(ac1.getEcartTemps(), 0);
        assertEquals(ac2.getEcartTemps(), 1);
        assertEquals(ac3.getEcartTemps(), 2);
    }

    @Test
    void ajouterSuccesseur(){
        assertEquals(sasEntree.nbSuccesseur(), 1);
        assertEquals(sasSortie.nbSuccesseur(), 0);
        assertEquals(g2.nbSuccesseur(), 3);
    }

    @Test
    void getNo(){
        assertEquals(g2.getNo(), 6);
        assertEquals(ac2.getNo(), 3);
    }

    @Test
    void getNumero(){
        assertEquals(g1.getNoSemaphore(), 1);
        assertEquals(g2.getNoSemaphore(), 2);
    }

    @Test
    void iterator(){
        ArrayList<Etape> verifEtape = new ArrayList(3);
        verifEtape.addAll(Arrays.asList(ac2, g2, ac3));

        int indx = 0;

        for(Etape e : gest3){
            assertTrue( e.equals(verifEtape.get(indx)) );
            indx++;
        }
    }

}