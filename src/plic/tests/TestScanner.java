package plic.tests;

//import java.util.Scanner;
import java.io.File;
import java.io.IOException;

import plic.analyse.AnalyseurLexical;

public class TestScanner {

	public static void main(String[] args) throws IOException {
		AnalyseurLexical sc = new AnalyseurLexical(new File("src/plic/sources/testSuperSimple.plic"));

		String val = "";
		while(!val.equals("EOF")) {
			val = sc.next();
			System.out.print(val + " ");
		}
	}

}
