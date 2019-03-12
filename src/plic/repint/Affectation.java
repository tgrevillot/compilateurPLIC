package plic.repint;

import plic.exceptions.SemantiqueException;

public class Affectation extends Instruction {
	private Idf idf;
	private Expression expr;
	
	public Affectation(Idf idf, Expression expr) {
		this.idf = idf;
		this.expr = expr;
	}
	
	public String toString() {
		return this.idf.toString() + " := " + this.expr.toString() + " ;";
	}

	@Override
	public void verifier() throws SemantiqueException {
		// TODO Auto-generated method stub
		this.idf.verifier();
		this.expr.verifier();
	}
}
