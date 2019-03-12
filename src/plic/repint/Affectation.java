package plic.repint;

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
}
