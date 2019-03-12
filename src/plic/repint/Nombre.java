package plic.repint;

public class Nombre extends Expression {
	private int val;
	
	public Nombre(int val) {
		this.val = val;
	}
	
	public String toString() {
		return Integer.toString(this.val);
	}
	
	public static boolean estNombre(String str) {
		try {
			Integer.parseInt(str);
		}catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
	
	public void verifier() {
		//Nothing to do
	}
}
