package plic.repint;

import plic.exceptions.SemantiqueException;

public class Idf extends Expression {
	private Entree nom;
	
	public Idf(Entree nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return this.nom.getIdf();
	}
	
	public void verifier() throws SemantiqueException {
		TDS tds = TDS.getInstance();
		
		if(!tds.containsKey(this.nom))
			throw new SemantiqueException("Variable not implemented");
		
		Symbole sym = tds.identifier(this.nom);
		
		if(!sym.getType().equals("entier"))
			throw new SemantiqueException("Only integer variable authorized");
	}
}
