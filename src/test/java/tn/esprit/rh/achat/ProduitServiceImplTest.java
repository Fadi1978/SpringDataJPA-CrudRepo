package tn.esprit.rh.achat;

import java.util.List;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import lombok.extern.slf4j.Slf4j;
import tn.esprit.rh.achat.AchatApplication;
import tn.esprit.rh.achat.entities.SecteurActivite;
import tn.esprit.rh.achat.services.ISecteurActiviteService;

@Slf4j
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = AchatApplication.class)
@RunWith(SpringRunner.class)
public class ProduitServiceImplTest {
	
	 @Autowired
	    ISecteurActiviteService sas;
	    @Test
	    @Order(2)
	    public void testretrieveAllSecteurs(){
	       int listSecteur = sas.retrieveAllSecteurActivite().size();
	        List<SecteurActivite> listSecteurs = sas.retrieveAllSecteurActivite();
	        Assertions.assertEquals(listSecteur,listSecteurs.size());
	    }

	    @Test
	    @Order(3)
	     public void testretrieveSecteurActivite(){
	    	SecteurActivite sa = sas.addSecteurActivite(SecteurActivite.builder()
	               .codeSecteurActivite("code1secteur")
	               .libelleSecteurActivite("sect1")
	               .build());
	        Assertions.assertEquals(sa.getIdSecteurActivite(), sas.retrieveSecteurActivite(sa.getIdSecteurActivite()).getIdSecteurActivite()) ;
			System.out.println("retrieve junit !");

	    }

	    @Test
	    @Order(1)
	    public void testaddSecteurActivite(){
	    	SecteurActivite sa = sas.addSecteurActivite(SecteurActivite.builder()
		               .codeSecteurActivite("code12secteur")
		               .libelleSecteurActivite("sect12")
		               .build());
	        Assertions.assertNotNull(sa);
			System.out.println("add junit !");



	    }

	    @Test
	    @Order(5)
	    public void testdeleteSecteur(){
	    	SecteurActivite sa = sas.addSecteurActivite(SecteurActivite.builder()
		               .codeSecteurActivite("code122secteur")
		               .libelleSecteurActivite("sect122")
		               .build());
	        sas.deleteSecteurActivite(sa.getIdSecteurActivite());
	        //Assertions.assertEquals(- 1,os.retrieveAllOperateurs().size());
	        Assertions.assertNull(sas.retrieveSecteurActivite(sa.getIdSecteurActivite()));
			System.out.println("delete junit !");


	    }

	    @Test
	    @Order(4)
	    public void tesupdateSecteur(){
	    	SecteurActivite sa = sas.addSecteurActivite(SecteurActivite.builder()
		               .codeSecteurActivite("code123secteur")
		               .libelleSecteurActivite("sect123")
		               .build());
	        sa.setCodeSecteurActivite("test");
	        sas.updateSecteurActivite(sa);
	        Assertions.assertEquals("test", sas.updateSecteurActivite(sa).getCodeSecteurActivite());
			System.out.println("update junit !");


	    }



}