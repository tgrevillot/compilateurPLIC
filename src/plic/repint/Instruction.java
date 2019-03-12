package plic.repint;

import plic.exceptions.SemantiqueException;

public abstract class Instruction {
	public abstract void verifier() throws SemantiqueException;
}
