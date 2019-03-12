package plic.repint;

import plic.exceptions.SemantiqueException;

public abstract class Expression {
	public abstract void verifier() throws SemantiqueException;
}
