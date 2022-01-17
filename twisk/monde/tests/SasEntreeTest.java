package twisk.monde.tests;

import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.SasEntree;

import static org.junit.jupiter.api.Assertions.*;

class SasEntreeTest {
    Activite ac1=new Activite("a1");
    SasEntree sasEntree=new SasEntree();


    @Test
    void isActivity(){
        assertTrue(sasEntree.estUneActivite());
        assertTrue(ac1.estUneActivite());
    }
}