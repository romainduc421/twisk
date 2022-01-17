package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireEtapesTest {

    private SasEntree entry;
    private SasSortie exit;
    private Activite act1,act2,act3;
    private Guichet guich1, guich2, guich3;
    private GestionnaireEtapes Steps_manager1, Steps_manager2, Steps_manager3;

    @BeforeEach
    void setUp() {
        this.entry = new SasEntree();
        this.exit = new SasSortie();
        this.guich1 = new Guichet("Attente_parc",6);
        this.guich2 = new Guichet("Parking",7);
        this.guich3 = new Guichet("guichet_recompenses",13);
        this.act1 = new Activite("Parc d'attractions",5,1);
        this.act2 = new Activite("Act2",4,3);
        this.act3 = new Activite("Ping pong");
        this.Steps_manager1 = new GestionnaireEtapes();
        this.Steps_manager2 = new GestionnaireEtapes();
        this.Steps_manager3 = new GestionnaireEtapes();
        this.entry.ajouterSuccesseur(this.guich1);
        this.act1.ajouterSuccesseur(this.guich2);
        this.act2.ajouterSuccesseur(this.guich3);
        this.guich1.ajouterSuccesseur(this.act1);
        this.guich2.ajouterSuccesseur(this.act2);
        this.guich3.ajouterSuccesseur(this.act3);
        this.act3.ajouterSuccesseur(this.exit);

    }

    @Test
    void ajouter() {
        this.Steps_manager1.ajouter(entry,guich1,act1,guich2,act2,guich3,act3,new Activite("Bonus"),exit);
        assertEquals(this.Steps_manager1.nbEtapes(),9);
        this.Steps_manager2.ajouter(this.entry,this.exit);
        assertEquals(this.Steps_manager2.nbEtapes(),2);
        this.Steps_manager3.ajouter(new Etape[0]);
        assertEquals(this.Steps_manager3.nbEtapes(),0);

    }

    @Test
    void nbEtapes() {
        this.Steps_manager1.ajouter(this.entry,this.guich1,this.act1,this.guich2,this.act2, this.guich3,this.act3,this.exit);
        assertEquals(this.Steps_manager1.nbEtapes() , 8);
        this.Steps_manager2.ajouter(this.entry,this.guich1,this.act1,this.guich2,this.act3,this.exit);
        assertEquals(this.Steps_manager2.nbEtapes(),6);
        assertEquals(this.Steps_manager3.nbEtapes(), 0);
    }

    @Test
    void iterator(){
        this.Steps_manager1.ajouter(entry,guich1,act1,guich2,act2,guich3,act3,exit);
        ArrayList<Etape> checkSteps = new ArrayList<>(8);
        checkSteps.addAll(Arrays.asList(new Etape[]{entry,guich1,act1,guich2,act2,guich3,act3,exit}));
        int indx = 0;

        for(Etape e : Steps_manager1){
            assertTrue(e.equals(checkSteps.get(indx)));
            indx++;
        }

        this.Steps_manager2.ajouter(entry,guich2,act3,exit);
        checkSteps = new ArrayList<>(4);
        checkSteps.addAll(Arrays.asList(new Etape[] {entry,guich2,act3,exit}));
        indx = 0;

        for(Etape e : Steps_manager2){
            assertTrue(e.equals(checkSteps.get(indx)));
            indx++;
        }

        checkSteps = new ArrayList<>(0);
        indx = 0;
        for(Etape e : Steps_manager3){
            assertTrue(e.equals(checkSteps.get(indx)));
            indx++;
        }
    }
}