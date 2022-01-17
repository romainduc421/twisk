package twisk.simulation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.simulation.Client;

import static org.junit.jupiter.api.Assertions.*;

class ClientTest {

    Client cl ;
    Activite act, act2, act3, act4;
    Guichet guich ;

    @BeforeEach
    void setUp()
    {
        this.cl = new Client(1) ;
        this.act = new Activite("Guitare") ;
        this.act2 = new Activite("foot") ;
        this.act3 = new Activite("theatre") ;
        this.act4 = new Activite("musee") ;
        this.guich = new Guichet("guichet_caisse",7) ;
    }

    @Test
    void allerA()
    {
        this.cl.allerA(this.act, 0);
        assertEquals( this.cl.getStep().getNom() , "Guitare" ) ;
        assertEquals( this.cl.getRank() , 0 ) ;
        this.cl.allerA(this.act2, 0);
        assertEquals( this.cl.getStep().getNom() , "foot" ) ;
        assertEquals( this.cl.getRank(), 0 ) ;
        this.cl.allerA(this.guich, 2);
        assertEquals( this.cl.getStep().getNom() , "guichet_caisse" ) ;
        assertEquals( this.cl.getRank() , 2 ) ;
    }
}