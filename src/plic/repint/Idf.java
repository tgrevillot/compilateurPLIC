package plic.repint;

public class Idf extends Expression {
	private String nom;
	
	public Idf(String nom) {
		this.nom = nom;
	}
	
	public String toString() {
		return this.nom;
	}
}
