package plic.repint;

import plic.exceptions.SemantiqueException;

public class Ecrire extends Instruction {
	
	private Expression expr;
	
	public Ecrire(Expression expr) {
		this.expr = expr;
	}
	
	public String toString() {
		return "ecrire " + this.expr.toString() + " ;";
	}

	@Override
	public void verifier() throws SemantiqueException {
		// TODO Auto-generated method stub
		this.expr.verifier();
	}
}
