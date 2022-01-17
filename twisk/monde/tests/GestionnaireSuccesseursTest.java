package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class GestionnaireSuccesseursTest {

    GestionnaireSuccesseurs step_manager, step_manager2, step_manager3, step_manager4;
    SasEntree entry; SasSortie exit;
    Activite act1, act_restr,act2,act3,act4;
    Guichet guich1, guich2;

    @BeforeEach
    void setUp() {
        step_manager = new GestionnaireSuccesseurs();
        step_manager2 = new GestionnaireSuccesseurs();
        step_manager3 = new GestionnaireSuccesseurs();
        step_manager4 = new GestionnaireSuccesseurs();
        entry = new SasEntree();
        exit = new SasSortie();
        act1 = new Activite("park"); act4 = new Activite("guitar");
        act_restr = new ActiviteRestreinte("resto",7,3);
        act2 = new Activite("rando"); act3 = new Activite("rollercoaster");
        guich1 = new Guichet("guich"); guich2 = new Guichet("attente_parc");
        step_manager2.ajouter(entry,act1,act_restr,
                guich1,act2);
        step_manager.ajouter(new Etape[0]);
        step_manager3.ajouter(entry);
        step_manager4.ajouter(entry,guich2,act3,exit);
    }

    @Test
    void ajouter() {
        assertEquals(step_manager.nbEtapes(),0);
        step_manager.ajouter(new ActiviteRestreinte("res",2,9));
        assertEquals(step_manager.nbEtapes(),1);
        step_manager2.ajouter(act4,exit);
        assertEquals(step_manager2.nbEtapes(),7);
        step_manager3.ajouter(new Activite("step2"));
        assertEquals(step_manager3.nbEtapes(),2);
        step_manager4.ajouter(exit);
        assertEquals(step_manager4.nbEtapes(),5);
    }

    @Test
    void nbEtapes() {
        assertEquals(step_manager.nbEtapes(),0);
        assertEquals(step_manager2.nbEtapes(),5);
        assertEquals(step_manager3.nbEtapes(),1);
        assertEquals(step_manager4.nbEtapes(),4);
    }

    @Test
    void iterator() {
        Activite act_fin = new Activite("step2");
        step_manager2.ajouter(act_fin,exit);
        ArrayList<Etape> checkSucc = new ArrayList<>(7);
        checkSucc.addAll(Arrays.asList(entry,act1,act_restr,
                guich1,act2,
                act_fin,exit));

        int indx=0;
        for(Etape e : step_manager2){
            assertTrue(e.equals(checkSucc.get(indx)));
            indx++;
        }

        indx=0;
        checkSucc = new ArrayList<>(2);
        step_manager3.ajouter(exit);
        checkSucc.addAll(Arrays.asList(entry,exit));
        for(Etape e : step_manager3){
            assertTrue(e.equals(checkSucc.get(indx)));
            indx++;
        }
        indx = 0;
        checkSucc = new ArrayList<>(0);
        for(Etape e : step_manager){
            assertTrue(e.equals(checkSucc.get(indx)));
            indx++;
        }
    }

    @Test
    void isEmpty() {
        assertTrue(step_manager.isEmpty());
        assertTrue(!step_manager2.isEmpty());
        assertTrue(!step_manager3.isEmpty());
        assertTrue(!step_manager4.isEmpty());
    }
}