package plic.analyse;

import java.io.File;
import java.io.IOException;
import java.util.Scanner;

/**
 * Classe servant d'Analyseur lexical au langage Plic
 * @author terry
 */
public class AnalyseurLexical {
	/**
	 * Variable servant à lire dans un fichier source
	 */
	private Scanner sc;

	/**
	 * Initialise le Scanner pour lire dans un fichier
	 * @param f
	 * 		Fichier à lire
	 */
	public AnalyseurLexical(File f) throws IOException {
			this.sc = new Scanner(f);
	}
	
	/**
	 * Indique si le fichier source contient des mots analysable
	 * @return	boolean
	 * 		vrai s'il existe encore un mot à traiter
	 */
	public boolean hasNext() {
		return sc.hasNext();
	}
	
	/**
	 * Renvoie le mot de fichier à analyser
	 * @return mot
	 * 		mot à analyser
	 */
	public String next() {
		//Lecture du mot
		if(sc.hasNext()) {
			String mot = sc.next();
			
			//Si le mot contient //
			if(mot.startsWith("//")) {
				//Si ce n'est pas la fin du fichier on saute d'une ligne
				if(sc.hasNextLine()) {
					sc.nextLine();
					mot = next();
				} else //Sinon on renvoie EOF
					mot = "EOF";
			}
			
			//On appelle récursivement next en cas de commentaire
			if(!sc.hasNext())
				return mot;
			return mot;
		} else {
			return "EOF";
		}
	}
}
