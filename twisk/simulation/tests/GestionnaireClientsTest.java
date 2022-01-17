package twisk.simulation.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.Activite;
import twisk.monde.Guichet;
import twisk.simulation.Client;
import twisk.simulation.ClientManager;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireClientsTest {

    ClientManager clientManager, clientManager2;
    Client client, client1, client2, client3, client4 ;
    Activite act,act2,act3,act4;
    Guichet guichet;
    @BeforeEach
    void setUp() {
        this.clientManager = new ClientManager(3) ;
        this.clientManager2 = new ClientManager(0) ;
        this.client = new Client(1);
        this.client1 = new Client(2);
        this.client2 = new Client(3);
        this.client3 = new Client(4);
        this.client4 = new Client(5);
        this.guichet = new Guichet("guichet_caisse",7) ;
        this.act = new Activite("Guitare");
        this.act2 = new Activite("foot");
        this.act3 = new Activite("theatre");
        this.act4 = new Activite("musee");
        this.clientManager.setClients(this.client.getCustomer_nb(), this.client3.getCustomer_nb(), this.client2.getCustomer_nb());
    }

    @Test
    void iterator() {
        this.clientManager.setClients( this.client.getCustomer_nb(), this.client3.getCustomer_nb(), this.client2.getCustomer_nb());
        this.clientManager.allerA(this.client.getCustomer_nb(), this.act, 0);
        this.clientManager.allerA(this.client3.getCustomer_nb(), this.act, 1);
        this.clientManager.allerA(this.client2.getCustomer_nb(), this.act, 2);
        List<Client> clients = new ArrayList(3);
        clients.add(this.client);
        clients.add(this.client3);
        clients.add(this.client2);
        int cpt = 0;

        for(Iterator var3 = this.clientManager.iterator(); var3.hasNext(); ++cpt) {
            Client c = (Client)var3.next();
            switch(cpt) {
                case 0:
                    assertEquals( (clients.get(cpt)).getCustomer_nb() , c.getCustomer_nb() ) ;
                    break;
                case 1:
                    assertEquals ( (clients.get(cpt)).getCustomer_nb() , c.getCustomer_nb() );
                case 2:
                    assertEquals ( (clients.get(cpt)).getCustomer_nb() , c.getCustomer_nb() );
            }
        }
    }

    @Test
    void allerA() {
        this.clientManager.allerA(this.client.getCustomer_nb(), this.act, 0);
        this.clientManager.allerA(this.client3.getCustomer_nb(), this.act, 1);
        this.clientManager.allerA(this.client2.getCustomer_nb(), this.act, 2);
        Iterator<Client> ite = this.clientManager.iterator();
        Client c = ite.next();

        assertEquals( c.getCustomer_nb() , 1 ) ;

        assertEquals( c.getStep().getNom(),"Guitare");

        assertEquals( c.getRank() , 0 ) ;

        c = ite.next();

        assertEquals( c.getCustomer_nb() , 4 ) ;

        assertEquals( c.getStep().getNom() ,"Guitare" ) ;

        assertEquals( c.getRank() , 1 ) ;

        c = ite.next();

        assertEquals( c.getCustomer_nb() , 3 );

        assertEquals( c.getStep().getNom() , "Guitare" );

        assertEquals( c.getRank() , 2 );

        this.clientManager2.setClients(new int[0]);

        assertFalse( this.clientManager2.iterator().hasNext() ) ;

        assertEquals( this.clientManager2.getClientsNb() , 0 ) ;
    }

    @Test
    void reset() {
        this.clientManager.allerA(this.client.getCustomer_nb(), this.act, 0);
        this.clientManager.allerA(this.client3.getCustomer_nb(), this.act, 1);
        this.clientManager.allerA(this.client2.getCustomer_nb(), this.act, 2);
        this.clientManager.reset();

        assertEquals( this.clientManager.getClientsNb() , 0 ) ;

        assertFalse( this.clientManager.iterator().hasNext() ) ;
    }
}