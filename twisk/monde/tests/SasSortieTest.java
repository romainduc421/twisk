package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.monde.SasSortie;

import static org.junit.jupiter.api.Assertions.*;

class SasSortieTest {

    SasSortie s=new SasSortie();
    Activite a=new Activite("a");
    Guichet g=new Guichet("g");
    @Test
    void isActivity(){
        assertTrue(s.estUneActivite());
        assertTrue(a.estUneActivite());
        assertFalse(g.estUneActivite());
    }


}