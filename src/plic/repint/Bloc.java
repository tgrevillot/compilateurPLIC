package plic.repint;

import java.util.LinkedList;

public class Bloc {
	private LinkedList<Instruction> instructions;
	
	public Bloc() {
		this.instructions = new LinkedList<Instruction>();
	}
	
	public void addInstruction(Instruction i) {
		this.instructions.add(i);
	}
	
	public void removeInstruction(int index) {
		this.instructions.remove(index);
	}
	
	public void verifier() {
		
	}
	
	public String toString() {
		String str = "{" + System.getProperty("line.separator");
		
		for(Instruction instru : this.instructions)
			str += instru.toString() + System.getProperty("line.separator");
		
		str += "}";
		return str;
	}
}
