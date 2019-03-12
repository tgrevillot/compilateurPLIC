package plic.repint;

public class Ecrire extends Instruction {
	
	private Expression expr;
	
	public Ecrire(Expression expr) {
		this.expr = expr;
	}
	
	public String toString() {
		return "ecrire " + this.expr.toString() + " ;";
	}
}
