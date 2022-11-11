package tn.esprit.rh.achat;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import tn.esprit.rh.achat.entities.*;
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
import tn.esprit.rh.achat.services.*;
import tn.esprit.rh.achat.repositories.*;

@ExtendWith(SpringExtension.class)
@Slf4j
@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
 class FactureServiceImplTest {

    @Autowired
    IFactureService factureService;

    @Autowired
    ICategorieProduitService categorieProduitService;

    @Autowired
    IStockService stockService;

    @Autowired
    IProduitService produitService;

    @Autowired
    IOperateurService operateurService;

    @Autowired
    IReglementService reglementService;


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

    @Test
    @Order(4)
     void testAddDetailsFacture() {
        log.info("test : testAddDetailsFacture" );

        Float sum = 0F;
        CategorieProduit cProduit = CategorieProduit.builder().codeCategorie("code1")
                .libelleCategorie("libelle categorie").idCategorieProduit(1L).build();
        cProduit=categorieProduitService.addCategorieProduit(cProduit);
        Stock stock = Stock.builder().libelleStock("lib stock").qte(10).qteMin(50).idStock(1L).build();
        stock=stockService.addStock(stock);
        Produit p1 = Produit.builder().codeProduit("code1").libelleProduit("lib1").prix(50.9F).categorieProduit(cProduit).stock(stock)
                .idProduit(1L).build();
        p1.setCategorieProduit(cProduit);
        p1=produitService.addProduit(p1);
        log.info("expected p1="+ p1 );
        DetailFacture detailFacture = DetailFacture.builder().idDetailFacture(1L).montantRemise(0F).pourcentageRemise(0).qteCommandee(9).produit(p1).build();

        Facture facture = Facture.builder().idFacture(1L).dateCreationFacture(new Date()).build();
        HashSet<DetailFacture> detailsList = new HashSet<DetailFacture>();
        detailsList.add(detailFacture);
        log.info("expected detailsList="+ detailsList );  
      
        facture = factureService.addDetailsFacture(facture, detailsList);

        for (DetailFacture detailFacture2 : detailsList) {
            sum += detailFacture2.getProduit().getPrix()* detailFacture2.getQteCommandee();
        }

        log.info("expected sum="+ sum );
        log.info("actual sum="+ facture.getMontantFacture() );
        log.info("test : finished sommeFactureTest" );


        assertEquals(sum, facture.getMontantFacture());
    }

    @Test
    @Order(5)
     void assignOperateurToFacture() {

        CategorieProduit cProduit = CategorieProduit.builder().codeCategorie("code1")
                .libelleCategorie("libelle categ").idCategorieProduit(1L).build();
        cProduit=categorieProduitService.addCategorieProduit(cProduit);
        Stock stock = Stock.builder().libelleStock("lib stock").qte(10).qteMin(50).idStock(1L).build();
        stock=stockService.addStock(stock);
        Produit p1 = Produit.builder().codeProduit("code1").libelleProduit("lib1").prix(50.9F).categorieProduit(cProduit).stock(stock)
                .idProduit(1L).build();
        p1.setCategorieProduit(cProduit);
        p1=produitService.addProduit(p1);
        DetailFacture detailFacture = DetailFacture.builder().idDetailFacture(1L).montantRemise(0F).pourcentageRemise(0).qteCommandee(9).produit(p1).build();

        Facture facture = Facture.builder().idFacture(1L).dateCreationFacture(new Date()).build();
        HashSet<DetailFacture> detailsList = new HashSet<DetailFacture>();
        detailsList.add(detailFacture);
        log.info("expected detailsList="+ detailsList );
        //facture = factureService.addDetailsFacture(facture, detailsList);
        log.info("expected facture="+ facture );
        Facture savedFacture= factureService.addFacture(facture);
        log.info("expected savedFacture="+ savedFacture );
        assertNotNull(savedFacture.getIdFacture());


        Operateur o2 = new Operateur();
        o2.setNom("said");
        o2.setPrenom("bahaeddine");
        o2.setPassword("passwd");
        Operateur savedOperateurr= operateurService.addOperateur(o2);

        factureService.assignOperateurToFacture(savedOperateurr.getIdOperateur(),savedFacture.getIdFacture());

        Operateur savedOperateurr2= operateurService.retrieveOperateur(savedOperateurr.getIdOperateur());


        log.info("expected operateur="+ savedOperateurr2 );

    }

    @Test
    @Order(6)
     void pourcentageRecouvrement(){
        log.info("========> In Test 6 pourcentageRecouvrement");
        Reglement reglement = new Reglement();
        reglement.setIdReglement(1L);
        reglement.setMontantPaye((float)100);
        Date m = new Date(2014,02,11);
        reglement.setDateReglement(m);
        //Reglement regsaved=reglementService.addReglement(reglement);

        Facture o = new Facture();
        o.setMontantFacture((float) 12.5);
        o.setMontantRemise((float) 12.3);
        Date m2 = new Date(2014,02,11);
        o.setDateCreationFacture(m);
        o.setArchivee(false);

        Facture savedFacture= factureService.addFacture(o);
        assertNotNull(savedFacture.getIdFacture());

        reglement.setFacture(savedFacture);
        Reglement regsaved=reglementService.addReglement(reglement);

        log.info("actual value="+ regsaved );

        Date m3 = new Date(2014,02,11);
        Date m4 = new Date(2015,02,11);
        Float res=factureService.pourcentageRecouvrement(m3,m4);



        assertFalse(savedFacture.getArchivee());
        assertEquals((float)((100/12.5)*100), res);
        log.info("expected value="+ (100/12.5)*100 );
        log.info("actual value="+ res );
        log.info("========> Ending In Test 6 pourcentageRecouvrement");

    }


}
