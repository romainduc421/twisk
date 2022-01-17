package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;

public class MondeTest {
    private SasEntree entry, entry2;
    private SasSortie exit, exit2;
    private Activite act1, act2, act3, act_Entree, act_Sortie;
    private ActiviteRestreinte activite1;
    private Guichet guichet1, guichet2, guichet3;
    private GestionnaireSuccesseurs gestsucc1, gestsucc2, gestsucc3;
    private GestionnaireEtapes gestet1, gestet2, gestet3;
    private Monde monde1, monde2, monde3;
    private Monde monde4, monde5;
    private Guichet guichet4;
    private Etape step1, step2;

    @BeforeEach
    void setUp() {
        entry = new SasEntree();
        exit = new SasSortie();
        entry2 = new SasEntree();
        exit2 = new SasSortie();
        act1 = new Activite("Activité 1", 4, 2);
        act2 = new Activite("Activité 2", 3, 2);
        act3 = new Activite("Activité 3",9,12);
        act_Entree = new Activite("Activité d'entrée");
        act_Sortie = new Activite("Activité de sortie");
        guichet1 = new Guichet("Guichet 1", 7);
        guichet2 = new Guichet("Guichet 2", 5);
        guichet3 = new Guichet("Guichet 3", 3);
        gestsucc1 = new GestionnaireSuccesseurs();
        gestsucc2 = new GestionnaireSuccesseurs();
        gestsucc3 = new GestionnaireSuccesseurs();
        gestet1 = new GestionnaireEtapes();
        gestet2 = new GestionnaireEtapes();
        gestet3 = new GestionnaireEtapes();
        //entry.ajouterSuccesseur(guichet1);
        act1.ajouterSuccesseur(guichet2);
        act2.ajouterSuccesseur(guichet3);
        //act3.ajouterSuccesseur(exit);
        guichet1.ajouterSuccesseur(act1);
        guichet2.ajouterSuccesseur(act2);
        guichet3.ajouterSuccesseur(act3);
        //entry2.ajouterSuccesseur(exit2);
        monde1 = new Monde();
        monde1.ajouter(guichet1, act1, guichet2, act2, guichet3,act3);
        monde1.aCommeEntree(guichet1);
        monde1.aCommeSortie(act3);
        monde2 = new Monde();
        //monde2.ajouter(entry2,exit2);
        //monde2.aCommeEntree(entry2);
        //monde2.aCommeSortie(exit2);
        monde3 = new Monde();
        /*monde3.ajouter();
        monde3.aCommeEntree();
        monde3.aCommeSortie();*/
        monde4 = new Monde();
        guichet4 = new Guichet("ticket", 2);
        activite1 = new ActiviteRestreinte("toboggan", 2, 1);
        step1 = new Activite("musee");
        step2 = new Activite("boutique");
        step1.ajouterSuccesseur(step2);
        step2.ajouterSuccesseur(guichet4);
        guichet4.ajouterSuccesseur(act1);
        monde4.ajouter(step1, step2);
        monde4.ajouter(guichet4);
        monde4.ajouter(act1);
        monde4.aCommeEntree(step1);
        monde4.aCommeSortie(act1);
        FabriqueNumero.getInstance().reset();
        monde5 = new Monde();
    }

    @Test
    void ajouter() {
        assertEquals(monde5.nbEtapes(), 2);
        monde5.ajouter(act1);
        assertEquals(monde5.nbEtapes(), 3);
        monde5.ajouter(guichet1);
        monde5.ajouter(act2);
        assertEquals(monde5.nbEtapes(), 5);
    }

    @Test
    void nbEtapes() {
        System.out.println(monde1.toString());
        System.out.println(monde2.toString());
        assertEquals(monde1.nbEtapes(), 8);
        assertEquals(monde2.nbEtapes(), 2);
        assertEquals(monde3.nbEtapes(), 2);
    }

    @Test
    void nbGuichets() {

        assertEquals(monde1.nbGuichets(), 3);
        assertEquals(monde2.nbGuichets(), 0);
        assertEquals(monde3.nbGuichets(), 0);
    }

    @Test
    void iterator() {
        ArrayList<Etape> checkSteps = new ArrayList<>(8);
        checkSteps.addAll(Arrays.asList(monde1.getEntry(),monde1.getExit(),guichet1,act1,guichet2,act2,guichet3,act3));

        int indx=0;
        for(Etape e : monde1) {
            assertTrue(e.equals(checkSteps.get(indx)));
            indx++;
        }

        checkSteps = new ArrayList<>(2);
        checkSteps.addAll(Arrays.asList(monde2.getEntry(),monde2.getExit()));

        indx=0;
        for(Etape e : monde2){
            assertTrue(e.equals(checkSteps.get(indx)));
            indx++;
        }

        checkSteps = new ArrayList<>(2);
        checkSteps.addAll(Arrays.asList(monde3.getEntry(), monde3.getExit()));
        indx = 0;
        for(Etape e : monde3) {
            assertTrue(e.equals(checkSteps.get(indx))); //test trivial
            indx++;
        }

    }

    @Test
    void aCommeEntree() {
        assertTrue(monde4.getEntry().iterator().next().getNom().equals("musee"));
    }

    @Test
    void aCommeSortie(){
        Iterator<Etape> it2 = monde4.iterator();
        Etape et;

        do{
            if(!it2.hasNext())
                return;
            et = it2.next();

        }while(!et.equals("toboggan") || et.iterator().next().getNom().equals("sortie"));
        throw new AssertionError("Bug qd l'étape de sortie n'a pas comme successeur le sas de sortie (exit) ");

    }


    @Test
    void testToC(){
        FabriqueNumero.getInstance().reset();
        Monde mnd = new Monde();


        Etape rando = new ActiviteRestreinte("Act_Randonnee",4,2);
        Guichet guichet1 = new Guichet("Parking_jetons",4);
        Activite guitar = new Activite("Guitare");
        Etape prog = new ActiviteRestreinte("jeux_programmes",2,3);
        Guichet guichet2 = new Guichet("attente_ordis_guichet",3);
        Activite act4 = new Activite("Toilettes",5,3);

        mnd.aCommeEntree(guichet1);
        guichet1.ajouterSuccesseur(rando);
        rando.ajouterSuccesseur(guitar);
        guitar.ajouterSuccesseur(guichet2);
        guichet2.ajouterSuccesseur(prog);
        prog.ajouterSuccesseur(act4);
        mnd.aCommeSortie(act4);
        mnd.ajouter(guichet1,rando,guitar,guichet2,prog,act4);

        assertEquals(mnd.toC(), "#include \"def.h\"\n" +
                "#include<time.h>\n" +
                "#include<unistd.h>\n" +
                "#include<stdio.h>\n" +
                "#include<stdlib.h>\n" +
                "#include<math.h>\n" +
                "#define SASENTREE 0\n" +
                "#define SASSORTIE 1\n" +
                "#define PARKING_JETONS 3\n" +
                "#define ACT_RANDONNEE 2\n" +
                "#define GUITARE 4\n" +
                "#define ATTENTE_ORDIS_GUICHET 6\n" +
                "#define JEUX_PROGRAMMES 5\n" +
                "#define TOILETTES 7\n" +
                "#define NO_SEMAPHORE_GUICHET1 1\n"+
                "#define NO_SEMAPHORE_GUICHET2 2\n"+
                "\nvoid simulation ( int ids ){ \n" +
                "\tentrer ( SASENTREE );\n" +
                "\tdelai ( 6, 4 ) ;\n" +
                "\ttransfert( SASENTREE, PARKING_JETONS ) ;\n" +
                "\tP( ids, NO_SEMAPHORE_GUICHET1 ) ;\n" +
                "\t\ttransfert( PARKING_JETONS, ACT_RANDONNEE ) ;\n" +
                "\t\tdelai( 4, 2 ) ;\n" +
                "\tV( ids, NO_SEMAPHORE_GUICHET1 ) ;\n" +
                "\ttransfert( ACT_RANDONNEE, GUITARE ) ;\n" +
                "\tdelai( 6, 4 ) ;\n" +
                "\ttransfert( GUITARE, ATTENTE_ORDIS_GUICHET );\n" +
                "\tP( ids, NO_SEMAPHORE_GUICHET2 ) ;\n" +
                "\t\ttransfert( ATTENTE_ORDIS_GUICHET, JEUX_PROGRAMMES ) ;\n" +
                "\t\tdelai( 2, 3 ) ;\n" +
                "\tV( ids, NO_SEMAPHORE_GUICHET2 ) ;\n" +
                "\ttransfert( JEUX_PROGRAMMES, TOILETTES ) ;\n" +
                "\tdelai( 5, 3 ) ;\n" +
                "\ttransfert( TOILETTES, SASSORTIE );\n" +
                "}"

        );
    }

    @Test
    void testToc2(){
        FabriqueNumero.getInstance().reset();
        Monde mnd = new Monde();

        Guichet guichet  = new Guichet("guich_cin");
        ActiviteRestreinte act_rest = new ActiviteRestreinte("cine",4,3);

        mnd.aCommeEntree(guichet);
        guichet.ajouterSuccesseur(act_rest);
        mnd.aCommeSortie(act_rest);
        mnd.ajouter(guichet,act_rest);


        assertEquals(mnd.toC(),"#include \"def.h\"\n" +
                "#include<time.h>\n" +
                "#include<unistd.h>\n" +
                "#include<stdio.h>\n" +
                "#include<stdlib.h>\n" +
                "#include<math.h>\n" +
                "#define SASENTREE 0\n" +
                "#define SASSORTIE 1\n" +
                "#define GUICH_CIN 2\n" +
                "#define CINE 3\n" +
                "#define NO_SEMAPHORE_GUICHET1 1\n" +
                "\nvoid simulation ( int ids ){ \n" +
                "\tentrer ( SASENTREE );\n" +
                "\tdelai ( 6, 4 ) ;\n" +
                "\ttransfert( SASENTREE, GUICH_CIN ) ;\n" +
                "\tP( ids, NO_SEMAPHORE_GUICHET1 ) ;\n" +
                "\t\ttransfert( GUICH_CIN, CINE ) ;\n" +
                "\t\tdelai( 4, 3 ) ;\n" +
                "\tV( ids, NO_SEMAPHORE_GUICHET1 ) ;\n" +
                "\ttransfert( CINE, SASSORTIE ) ;\n" +
                "}"
        );
    }
}
