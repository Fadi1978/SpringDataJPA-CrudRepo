package tn.esprit.rh.achat;


import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;


import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;
import tn.esprit.rh.achat.services.*;
import org.junit.jupiter.api.Test;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class FactureServiceImplTest {

    @Autowired
    IFactureService factureService;

  

    //testing Add method
    @Test
    @Order(1)
     void testAddFacture(){
        log.info("========> In Test 1 testAddFacture");
        int expected=factureService.retrieveAllFactures().size();
        Facture o = new Facture();
        o.setMontantFacture((float) 12.5);
        o.setMontantRemise((float) 12.3);
        Date m = new Date(2014,02,11);
        o.setDateCreationFacture(m);
        o.setDateDerniereModificationFacture(m);
        Facture savedFacture= factureService.addFacture(o);
        assertNotNull(savedFacture.getIdFacture());
        assertEquals(expected+1, factureService.retrieveAllFactures().size());

    }

    @Test
    @Order(2)
     void testRetrieveFactures() {
        Facture o = new Facture();
        o.setMontantFacture((float) 12.5);
        o.setMontantRemise((float) 12.3);
        Date m = new Date(2014,02,11);
        o.setDateCreationFacture(m);
        o.setDateDerniereModificationFacture(m);
        Facture savedFacture= factureService.addFacture(o);
        Facture getFacture= factureService.retrieveFacture(savedFacture.getIdFacture());
        assertNotNull(savedFacture.getIdFacture());
        assertEquals(savedFacture.getIdFacture(),getFacture.getIdFacture());

        factureService.cancelFacture(savedFacture.getIdFacture());
    }

    @Test
    @Order(3)
     void testDeleteFacture() {
        Facture o = new Facture();
        o.setMontantFacture((float) 12.5);
        o.setMontantRemise((float) 12.3);
        Date m = new Date(2014,02,11);
        o.setDateCreationFacture(m);
        o.setDateDerniereModificationFacture(m);
        Facture savedFacture= factureService.addFacture(o);
        factureService.cancelFacture(savedFacture.getIdFacture());
        assertNotNull(savedFacture.getIdFacture());

    }

    }
