package twisk.outils.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;
import twisk.outils.KitC;

import static org.junit.jupiter.api.Assertions.*;

class KitCTest {


    KitC kit;
    @BeforeEach
    void setUp() {
        this.kit = new KitC();
    }

    @Test
    void creerFichier() {
        FabriqueNumero.getInstance().reset();
        Monde mnd = new Monde();


        Etape rando = new ActiviteRestreinte("Act Randonnee",4,2);
        Guichet guichet1 = new Guichet("Parking_jetons");
        Activite guitar = new Activite("Guitare");
        Etape prog = new ActiviteRestreinte("jeux programmés",2,3);
        Guichet guichet2 = new Guichet("attente_ordis_guichet");
        Activite act4 = new Activite("Toilettes",5,3);

        mnd.aCommeEntree(guichet1);
        guichet1.ajouterSuccesseur(rando);
        rando.ajouterSuccesseur(guitar);
        guitar.ajouterSuccesseur(guichet2);
        guichet2.ajouterSuccesseur(prog);
        prog.ajouterSuccesseur(act4);
        mnd.aCommeSortie(act4);
        mnd.ajouter(guichet1,rando,guitar,guichet2,prog,act4);
        this.kit.creerEnvironnement();
        this.kit.creerFichier(mnd.toC());

        assertEquals(kit.getOperatingSystem(),"linux");
    }
}