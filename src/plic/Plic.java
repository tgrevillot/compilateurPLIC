package plic;

import java.io.File;
import java.io.IOException;

import plic.analyse.AnalyseurSyntaxique;
import plic.exceptions.DoubleDeclarationException;
import plic.exceptions.ErreurSyntaxique;
import plic.exceptions.SemantiqueException;
import plic.repint.Bloc;

public class Plic {

	public static void main(String[] args) {
		try {			
			if(!args[0].endsWith(".plic"))
				throw new ErreurSyntaxique("Suffixe incorrect");
			new Plic(args[0]);
		} catch(ErreurSyntaxique e) {
			System.out.println("ERREUR: " + e.getMessage());
		} catch(DoubleDeclarationException i) {
			System.out.println("ERREUR: " + i.getMessage());
		} catch(SemantiqueException h) {
			System.out.println("ERREUR: " + h.getMessage());
		} catch(IOException f) {
			System.out.println("ERREUR: " + f.getMessage());
		} catch(ArrayIndexOutOfBoundsException g) {
			System.out.println("ERREUR: Fichier source absent");
		}
	}
	
	public Plic(String nomFichier) throws IOException, ErreurSyntaxique, DoubleDeclarationException, SemantiqueException {
		File file = new File(nomFichier);
		//On instancie l'analyseur syntaxique
		AnalyseurSyntaxique as = new AnalyseurSyntaxique(file);
		//On commence l'analyse du texte source
		Bloc bloc = as.analyse();
		bloc.verifier();
	}

}
