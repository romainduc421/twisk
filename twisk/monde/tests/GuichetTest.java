package twisk.monde.tests;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import twisk.monde.*;
import twisk.outils.FabriqueNumero;

import static org.junit.jupiter.api.Assertions.*;

class GuichetTest {
    Monde monde;
    Guichet guichet;
    Activite act1;
    Etape etape1;
    Etape etape2;
    Monde monde2;
    Guichet guichet1;
    Guichet guichet2;
    Guichet guichet3;
    Activite act2;
    Etape etape3;
    Monde monde3;
    Guichet guichet4;
    Guichet guichet5;
    Guichet guichet6;
    Activite act3;
    Etape act4;
    Etape etape;
    Etape sortie;
    Monde monde4;
    Activite act5;
    Monde monde5;
    Activite zoo;
    Activite tob;
    Guichet guichet7;
    Monde monde6;
    Activite magasin;
    Activite sortie1;
    Activite sortie2;
    Activite sortie3;
    Activite sortieMagasin1;
    Activite sortieMagasin2;
    Activite suivCabine1;
    Activite suivCabine2;
    Activite encoreAutreSortie1;
    Activite encoreAutreSortie2;
    Guichet cabine1;
    Guichet cabine2;
    Guichet cabine3;
    Guichet cabine4;
    Guichet cabine5;
    Guichet cabine6;

    @BeforeEach
    void setUp() {
        monde = new Monde();
        guichet = new Guichet("ticket", 2);
        act1 = new ActiviteRestreinte("toboggan", 2, 1);
        etape1 = new Activite("musee");
        etape2 = new Activite("boutique");
        etape1.ajouterSuccesseur(this.etape2);
        etape2.ajouterSuccesseur(this.guichet);
        guichet.ajouterSuccesseur(this.act1);
        monde.ajouter(this.etape1, this.etape2);
        monde.ajouter(this.guichet);
        monde.ajouter(this.act1);
        monde.aCommeEntree(this.etape1);
        monde.aCommeSortie(this.act1);
        FabriqueNumero.getInstance().reset();
        monde2 = new Monde();
        guichet1 = new Guichet("caisse", 5);
        guichet2 = new Guichet("file_VIP", 6);
        guichet3 = new Guichet("file_normal", 5);
        act2 = new ActiviteRestreinte("Grand_8", 10, 2);
        etape3 = new Activite("Boutique");
        guichet1.ajouterSuccesseur(this.act2);
        act2.ajouterSuccesseur(this.guichet2, this.guichet3);
        guichet2.ajouterSuccesseur(this.etape3);
        guichet3.ajouterSuccesseur(this.etape3);
        monde2.ajouter(this.guichet1, this.act2, this.guichet2, this.guichet3, this.etape3);
        monde2.aCommeEntree(this.guichet1);
        monde2.aCommeSortie(this.etape3);
        FabriqueNumero.getInstance().reset();
        monde3 = new Monde();
        guichet4 = new Guichet("file_d_attente_1", 5);
        guichet5 = new Guichet("file_d_attente_2", 5);
        guichet6 = new Guichet("caisse", 1);
        act3 = new ActiviteRestreinte("Cabine_1", 10, 2);
        act4 = new ActiviteRestreinte("Cabine_2", 10, 2);
        etape = new Activite("Magasin");
        sortie = new Activite("Sortie_du_magasin");
        etape.ajouterSuccesseur(this.guichet4, this.guichet5);
        guichet4.ajouterSuccesseur(this.act3);
        guichet5.ajouterSuccesseur(this.act4);
        act3.ajouterSuccesseur(this.guichet6);
        act4.ajouterSuccesseur(this.guichet6);
        guichet6.ajouterSuccesseur(this.sortie);
        monde3.ajouter(this.etape, this.guichet4, this.guichet5, this.act3, this.act4, this.guichet6, this.sortie);
        monde3.aCommeEntree(this.etape);
        monde3.aCommeSortie(this.sortie);
        FabriqueNumero.getInstance().reset();
        monde4 = new Monde();
        act5 = new ActiviteRestreinte("activite", 6, 3);
        monde4.ajouter(this.act5);
        monde4.aCommeEntree(this.act5);
        monde4.aCommeSortie(this.act5);
        FabriqueNumero.getInstance().reset();
        monde5 = new Monde();
        zoo = new Activite("balade_au_zoo", 3, 1);
        tob = new ActiviteRestreinte("toboggan", 2, 1);
        guichet7 = new Guichet("acces_au_toboggan", 2);
        zoo.ajouterSuccesseur(this.guichet7);
        guichet7.ajouterSuccesseur(this.tob);
        monde5.ajouter(this.zoo, this.tob, this.guichet7);
        monde5.aCommeEntree(this.zoo);
        monde5.aCommeSortie(this.tob);
        FabriqueNumero.getInstance().reset();
        monde6 = new Monde();
        magasin = new Activite("Magasin", 3, 1);
        sortie1 = new ActiviteRestreinte("Sortie_Cabine_1", 2, 1);
        sortie2 = new ActiviteRestreinte("Sortie_Cabine_2", 2, 1);
        sortie3 = new ActiviteRestreinte("Sortie_Cabine_3", 2, 1);
        sortieMagasin1 = new ActiviteRestreinte("Sortie_Magasin_1", 5, 1);
        sortieMagasin2 = new ActiviteRestreinte("Sortie_Magasin_2", 5, 1);
        suivCabine1 = new ActiviteRestreinte("Suiv_Sortie_Cabine_1", 3, 1);
        suivCabine2 = new ActiviteRestreinte("Suiv_Sortie_Cabine_2", 3, 1);
        encoreAutreSortie1 = new ActiviteRestreinte("Encore_Autre_Sortie_1", 3, 1);
        encoreAutreSortie2 = new ActiviteRestreinte("Encore_Autre_Sortie_2", 3, 1);
        cabine1 = new Guichet("Cabine_1", 1);
        cabine2 = new Guichet("Cabine_2", 1);
        cabine3 = new Guichet("Cabine_3", 1);
        cabine4 = new Guichet("Cabine_4", 1);
        cabine5 = new Guichet("Cabine_5", 1);
        cabine6 = new Guichet("Cabine_6", 1);
        magasin.ajouterSuccesseur(this.cabine1, this.cabine2, this.cabine3, this.cabine4, this.cabine5, this.cabine6);
        cabine1.ajouterSuccesseur(this.sortie1);
        cabine2.ajouterSuccesseur(this.sortie2);
        cabine3.ajouterSuccesseur(this.sortie3);
        cabine4.ajouterSuccesseur(this.sortie1);
        cabine5.ajouterSuccesseur(this.sortie2);
        cabine6.ajouterSuccesseur(this.sortie3);
        sortie1.ajouterSuccesseur(this.suivCabine1, this.suivCabine2);
        sortie2.ajouterSuccesseur(this.sortieMagasin1, this.sortieMagasin2);
        sortie3.ajouterSuccesseur(this.sortieMagasin1, this.sortieMagasin2);
        suivCabine1.ajouterSuccesseur(this.encoreAutreSortie1, this.encoreAutreSortie2);
        suivCabine2.ajouterSuccesseur(this.sortieMagasin1, this.cabine1);
        encoreAutreSortie1.ajouterSuccesseur(this.sortieMagasin1, this.sortieMagasin2);
        encoreAutreSortie2.ajouterSuccesseur(this.sortieMagasin1, this.sortieMagasin2);
        monde6.ajouter(this.magasin, this.cabine1, this.cabine2, this.cabine3, this.cabine4, this.cabine5, this.cabine6, this.sortie1, this.sortie2, this.sortie3, this.sortieMagasin1, this.sortieMagasin2, this.suivCabine1, this.suivCabine2, this.encoreAutreSortie1, this.encoreAutreSortie2);
        monde6.aCommeEntree(this.magasin);
        monde6.aCommeSortie(this.sortieMagasin1, this.sortieMagasin2);
        FabriqueNumero.getInstance().reset();
    }

    @Test
    void estUnGuichet() {
        assertTrue(guichet1.estUnGuichet());
        assertFalse( act1.estUnGuichet());
        assertFalse( etape1.estUnGuichet());
        assertFalse( act4.estUnGuichet());
        assertFalse(act3.estUnGuichet());
    }

    @Test
    void estUneActivite() {
        assertFalse(guichet1.estUneActivite());
        assertTrue(act1.estUneActivite());
        assertTrue(etape1.estUneActivite());
        assertTrue(act4.estUneActivite());
        assertTrue(act3.estUneActivite());
        assertTrue(encoreAutreSortie2.estUneActivite());
    }

    @Test
    void getNoSemaphore() {
        assertEquals(guichet.getNoSemaphore(),1);
        assertEquals (guichet1.getNoSemaphore(), 1);
        assertEquals(guichet3.getNoSemaphore(), 3);
    }
}