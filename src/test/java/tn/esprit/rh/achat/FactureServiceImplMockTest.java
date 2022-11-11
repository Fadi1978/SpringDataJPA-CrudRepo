package tn.esprit.rh.achat;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import tn.esprit.rh.achat.entities.*;
import tn.esprit.rh.achat.repositories.*;
import tn.esprit.rh.achat.services.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class FactureServiceImplMockTest {

    @Mock
    FactureRepository factureRepository;

    @Mock
    FournisseurRepository fournisseurRepository;

    @InjectMocks
    FactureServiceImpl factureService;

    @Test
    @Order(1)
     void retrieveAllFactures() {
        when(factureRepository.findAll()).thenReturn(Stream
                .of(new Facture(1L, 10,1000,new Date(),null,null, null, null, null), new Facture(2L, 10,1000,new Date(),null,null, null, null, null))
                .collect(Collectors.toList())
        );
        assertEquals(2, factureService.retrieveAllFactures().size());
    }

    @Test
    @Order(2)
     void addFacture() {

        Facture facture = new Facture(1L, 10,1000,new Date(),null,null, null, null, null);
        when(factureRepository.save(facture)).thenReturn(facture);
        assertEquals(facture, factureService.addFacture(facture));
    }

    @Test
    @Order(3)
     void cancelFacture() {
        Facture f = new Facture(1L, 10,1000,new Date(),null,false, null, null, null);
        when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(f));
        factureService.cancelFacture((long)1);
        Facture f1 = factureService.retrieveFacture((long) 1);
        assertEquals(true,f1.getArchivee());
    }

    @Test
    @Order(4)
     void retrieveFacture() {
        Facture f = new Facture(1L, 10,1000,new Date(),null,false, null, null, null);
        when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(f));
        Facture f1 = factureService.retrieveFacture((long) 2);
        Assertions.assertNotNull(f1);
    }

    @Test
    @Order(5)
     void getFacturesByFournisseur() {
        Fournisseur fournisseur=new Fournisseur(1L,"test","test");
        when(fournisseurRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(fournisseur));
        Facture f = new Facture(1L, 10,1000,new Date(),null,false, null, null, null);
        f.setFournisseur(fournisseur);
        HashSet<Facture> factList = new HashSet<Facture>();
        factList.add(f);
        fournisseur.setFactures(factList);
        when(factureRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(f));
        Facture f1 = factureService.retrieveFacture((long) 1);
        Assertions.assertNotNull(f1);
        HashSet<Facture> lf=factureService.getFacturesByFournisseur(fournisseur.getIdFournisseur());
        assertEquals(1,lf.size());
        log.info("size :"+lf.size() );

    }



}
