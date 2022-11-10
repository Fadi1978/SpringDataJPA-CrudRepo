package tn.esprit.rh.achat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.mockito.Mock;
import org.junit.jupiter.api.Assertions;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import tn.esprit.rh.achat.AchatApplication;
import tn.esprit.rh.achat.entities.Fournisseur;

import tn.esprit.rh.achat.repositories.FournisseurRepository;

import tn.esprit.rh.achat.services.FournisseurServiceImpl;



@SpringBootTest(classes = AchatApplication.class)
@ExtendWith(MockitoExtension.class)
@RunWith(MockitoJUnitRunner.class)
public class FournisseurServiceMockito {
	 @Mock
	 FournisseurRepository produitRepositoryMock;
	 @InjectMocks
	 FournisseurServiceImpl produitService;
	 
	 
	 Fournisseur op = Fournisseur.builder().code("999").libelle("Selma").build();
	 List<Fournisseur> listOperateurs = new ArrayList<Fournisseur>(){
	     {
	         add(Fournisseur.builder().code("888").libelle("Amira").build());
	         add(Fournisseur.builder().code("666").libelle("nour").build());
	         add(Fournisseur.builder().code("522").libelle("sab").build());
	         add(Fournisseur.builder().code("1000").libelle("yass").build());

	     }

	 };
	 
	@Test
	public void testretrieveAllFournisseurs() {
		 Mockito.when(produitRepositoryMock.findAll()).thenReturn(listOperateurs);
	     List<Fournisseur> listOp = produitService.retrieveAllFournisseurs();
	     Assertions.assertNotNull(listOp);
			System.out.println("woooorkiiiiing all retrieve !");


	}

	@Test
	public void testretrieveFournisseur() {
		Mockito.when(produitRepositoryMock.findById(Mockito.anyLong())).thenReturn(Optional.of(op)); //find all
	    Fournisseur op1 = produitService.retrieveFournisseur(2L);
	    Assertions.assertNotNull(op1);
		System.out.println("woooorkiiiiing retrieve !");
	}

	@Test
	public void testaddFournisseur() {
		 Mockito.when(produitRepositoryMock.save(op)).thenReturn(op);
		 Fournisseur op1 = produitService.addFournisseur(op);
	     Assertions.assertNotNull(op1);
			System.out.println("woooorkiiiiing add !");

	}

	@Test
	public void testdeleteFournisseur() {
		Fournisseur op2 = Fournisseur.builder().code("999").libelle("Selma").build();
	     produitService.deleteFournisseur(op2.getIdFournisseur());
	     Mockito.verify(produitRepositoryMock).deleteById(op2.getIdFournisseur());
			System.out.println("woooorkiiiiing delete !");

	}

	@Test
	public void testupdateFournisseur() {
		op.setLibelle("khalil");
	     Mockito.when(produitRepositoryMock.save(op)).thenReturn(op);
	     Fournisseur op1 = produitService.updateFournisseur(op);
	     Assertions.assertEquals(op.getLibelle(),op1.getLibelle());
			System.out.println("woooorkiiiiing update !");

	}
}
