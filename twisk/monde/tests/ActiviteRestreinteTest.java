package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import static org.junit.jupiter.api.Assertions.*;

class ActiviteRestreinteTest {
    ActiviteRestreinte actR1, actR2;
    Activite ac1;
    SasEntree sasEntree;
    SasSortie sasSortie;
    Guichet g1,g2;

    @BeforeEach
    void setUp(){
        actR1=new ActiviteRestreinte("Restreinte");
        actR2=new ActiviteRestreinte("R2", 2, 1);
        g1=new Guichet("g1");
        g2=new Guichet("g2");
        sasEntree=new SasEntree();
        sasSortie=new SasSortie();
        ac1=new Activite("sansR");
    }

    @Test
    void isActivity(){
        assertTrue(actR1.estUneActivite());
        assertTrue(actR2.estUneActivite());
        assertTrue(ac1.estUneActivite());
        assertFalse(g1.estUneActivite());
        assertTrue(sasEntree.estUneActivite());
    }

}