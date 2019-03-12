package plic.tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import plic.exceptions.DoubleDeclarationException;
import plic.exceptions.SemantiqueException;
import plic.repint.Entree;
import plic.repint.Symbole;
import plic.repint.TDS;

public class TestTDS {

	private TDS tds = TDS.getInstance();
	private Entree e1, e2, e3;
	private Symbole s1;
	
	@Before
	public void initialisation() {
		this.e1 = new Entree("a");
		this.e2 = new Entree("bonjour");
		this.e3 = new Entree("cava");
		
		this.s1 = new Symbole("entier");
		//this.s2 = new Symbole("blabla");
	}
	
	@Test
	public void test_Entree() {
		assertEquals("Entree n'est pas instancie correctement 1", this.e1.getIdf(), "a");
		assertEquals("Entree n'est pas instancie correctement 2", this.e2.getIdf(), "bonjour");
		assertEquals("Entree n'est pas instancie correctement 3", this.e3.getIdf(), "cava");
	}
	
	@Test
	public void test_Symbole() {
		assertEquals("Symbole n'est pas instancie correctement", this.s1.getType(), "entier");
		//assertEquals("Symbole ne devrait pas contenir autre chose que entier ou tableau", this.s2.getType(), "entier");
	}
	
	@Test
	public void test_not2differentTDS() {
		TDS tds2 = TDS.getInstance();
		assertEquals("Le Singleton n'est pas fonctionnel", tds2, tds);
	}
	
	@Test
	public void test_emptyMapAtBeginning() {
		assertEquals("La map doit être vide", 0, this.tds.getLongueurTab());
	}
	
	@Test
	public void test_insertionSansProbleme() throws DoubleDeclarationException {
		this.tds.ajouter(this.e1, this.s1);
		assertTrue("Ce premier couple doit fonctionner", this.tds.containsKey(e1));
		
		this.tds.ajouter(this.e2, this.s1);
		assertTrue("Le deuxieme couple doit être ajouté", this.tds.containsKey(e2));
	}
	
	@Test(expected = DoubleDeclarationException.class)
	public void test_insertionAvecProbleme() throws DoubleDeclarationException {
		this.tds.ajouter(new Entree("c"), new Symbole("entier"));
		this.tds.ajouter(new Entree("c"), new Symbole("entier"));
		assertTrue("Une exception est censé être levée", false);
	}
	
	@Test
	public void test_verifierTDS() throws DoubleDeclarationException {
		Entree e54 = new Entree("jojo");
		Symbole sym = new Symbole("entier");
		
		this.tds.ajouter(e54, sym);
		assertEquals("Jojo devrait etre trouve", this.tds.identifier(e54), sym);
		assertEquals("Jaja n'existe pas et doit etre null", this.tds.identifier(new Entree("jaja")), null);
	}

}
