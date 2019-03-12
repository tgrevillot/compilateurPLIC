package plic.repint;

import java.util.LinkedList;

import plic.exceptions.SemantiqueException;

public class Bloc {
	private LinkedList<Instruction> instructions;
	
	public Bloc() {
		this.instructions = new LinkedList<Instruction>();
	}
	
	public void add(Instruction i) {
		this.instructions.add(i);
	}
	
	public void verifier() throws SemantiqueException {
		for(Instruction i : this.instructions)
			i.verifier();
	}
	
	public String toString() {
		String str = "{" + System.getProperty("line.separator");
		
		for(Instruction instru : this.instructions)
			str += instru.toString() + System.getProperty("line.separator");
		
		str += "}";
		return str;
	}
}
