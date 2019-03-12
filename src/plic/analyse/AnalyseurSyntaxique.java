package plic.analyse;

import java.io.File;
import java.io.IOException;

import plic.exceptions.DoubleDeclarationException;
import plic.exceptions.ErreurSyntaxique;
import plic.repint.Affectation;
import plic.repint.Bloc;
import plic.repint.Ecrire;
import plic.repint.Entree;
import plic.repint.Idf;
import plic.repint.Nombre;
import plic.repint.Symbole;
import plic.repint.TDS;

/**
 * Classe analysant la syntaxe d'un programme plic
 * @author terry
 */
public class AnalyseurSyntaxique {
	/**
	 * Analyseur lecical fournissant les mots
	 */
	private AnalyseurLexical al;
	/**
	 * Mot en cours
	 */
	private String uniteCourante;
	
	/**
	 * Initialise un analyseur syntaxique
	 * @param file
	 * 		Fichier à compiler
	 * @throws IOException
	 */
	public AnalyseurSyntaxique(File file) throws IOException {
		this.al = new AnalyseurLexical(file);
	}
	
	/**
	 * Procedure lancant les differentes parties de l'analyse du fichier plic
	 * @throws ErreurSyntaxique
	 */
	public Bloc analyse() throws ErreurSyntaxique, DoubleDeclarationException {
		//On récupère le premier mot
		this.uniteCourante = this.al.next();
		//On traite ce premier mot
		this.analyseProg();
		
		Bloc bloc = this.analyseBlock();
		
		if(!this.uniteCourante.equals("EOF"))
			throw new ErreurSyntaxique("Expecting EOF");
		
		return bloc;
	}
	
	/**
	 * Analyse un identificateur et renvoie une exception si celui-ci n'est pas conforme
	 * @throws ErreurSyntaxique
	 */
	private boolean analyseIdf() throws ErreurSyntaxique {
		if(this.uniteCourante.equals("entier") || this.uniteCourante.equals("ecrire"))
			throw new ErreurSyntaxique(this.uniteCourante + " is'nt allowed here");
		
		if(!(this.uniteCourante.matches("[a-zA-Z]+")) || this.uniteCourante.equals("EOF"))
			throw new ErreurSyntaxique(this.uniteCourante + " identificateur non valide");
		
		return true;
	}
	
	/**
	 * Indique si l'unité courante est un identificateur ou non 
	 * @return bool
	 * 			vrai s'il s'agit d'un identificateur
	 * @throws ErreurSyntaxique
	 */
	private boolean estIdf() throws ErreurSyntaxique {		
		return this.uniteCourante.matches("[a-zA-Z]+");
	}
	
	/**
	 * Analyse l'unite courante avec un mot clef attendu et renvoie une exception si elles sont differentes
	 * @param mot
	 * 		Mot a verifier
	 * @throws ErreurSyntaxique
	 */
	private void analyseKeyWord(String mot) throws ErreurSyntaxique {
		if(!this.uniteCourante.equals(mot))
			throw new ErreurSyntaxique("Expecting "+ mot);
		this.uniteCourante = this.al.next();
	}
	
	/**
	 * Lance l'analyse du début du programme
	 * @throws ErreurSyntaxique
	 */
	private void analyseProg() throws ErreurSyntaxique {
		this.analyseKeyWord("programme");
		this.analyseIdf();
		this.uniteCourante = this.al.next();
	}
	
	/**
	 * Lanc l'analyse du contenu du programme (bloc)
	 * @throws ErreurSyntaxique
	 */
	private Bloc analyseBlock() throws ErreurSyntaxique, DoubleDeclarationException {
		//Dans Plic0, il est impossible d'avoir des déclarations, des insertions, puis re des déclarations.
		this.analyseKeyWord("{");
		
		//Itérer sur analyse Déclaration tant qu'il y a des déclaration
		this.analyseDeclaration();
		
		//Itérer sur analyse Instructions tant qu'il y a des instructions
		Bloc bloc = this.analyseInstruction();
		
		this.analyseKeyWord("}");
		
		return bloc;
	}
	
	/**
	 * Lance l'analyse de declarations du programme a compiler
	 * @throws ErreurSyntaxique
	 */
	private void analyseDeclaration() throws ErreurSyntaxique, DoubleDeclarationException {
		//On récupère la table pour y ajouter notre déclaration
		TDS tds = TDS.getInstance();
		
		while(this.al.hasNext() && this.uniteCourante.equals("entier")) {
			this.uniteCourante = this.al.next();
			
			if(this.analyseIdf())
				tds.ajouter(new Entree(this.uniteCourante), new Symbole("entier"));
		
			this.uniteCourante = this.al.next();
			this.analyseKeyWord(";");
		}
		
		if(this.uniteCourante.equals("EOF"))
			throw new ErreurSyntaxique("Unexpected End of File");
	}
	
	/**
	 * Lance l'analyse d'instructions du programme a compiler
	 * @throws ErreurException
	 */
	private Bloc analyseInstruction() throws ErreurSyntaxique{
		//On initialise un bloc
		Bloc bloc = new Bloc();
		
		//On boucle tant que le premier mot est un identificateur 
 		while(this.al.hasNext() && this.estIdf() && !this.uniteCourante.equals("EOF")) {
 			switch(this.uniteCourante) {
 				case "ecrire":
 						bloc = this.analyseEcriture(bloc);
 					break;
 				default :
 						bloc = this.analyseAffectation(bloc);
 			}
 		}
 		
 		if(this.uniteCourante.equals("EOF"))
 			throw new ErreurSyntaxique("Unexpected End of File");
 		return bloc;
	}
	
	private Bloc analyseAffectation(Bloc bloc) throws ErreurSyntaxique {
			this.analyseIdf();
			String idf = this.uniteCourante;
			this.uniteCourante = this.al.next();
			this.analyseKeyWord(":=");
			//On test que le nombre fourni soit bien un entier
			try {
				Integer.parseInt(uniteCourante);
			}catch(NumberFormatException e) {
				//Si ce n'est pas le nom d'une autre variable alors on lève une exception
				if(!this.analyseIdf())
					throw new ErreurSyntaxique("Entier ou nom de variable attendu");
			}
			String value = this.uniteCourante;
			this.uniteCourante = this.al.next();
			this.analyseKeyWord(";");
			
			//On va ajouter l'expression dans le bloc
			if(Nombre.estNombre(value))
				bloc.add(new Affectation(new Idf(new Entree(idf)), new Nombre(Integer.parseInt(value))));
			else
				bloc.add(new Affectation(new Idf(new Entree(idf)), new Idf(new Entree(value))));
			
			return bloc;
	}
	
	private Bloc analyseEcriture(Bloc bloc) throws ErreurSyntaxique {
		this.uniteCourante = this.al.next();
		this.analyseIdf();
		//On enregistre la valeur dans une variable dans le cas où l'instruction est bien écrite
		String value = this.uniteCourante;
		this.uniteCourante = this.al.next();
		this.analyseKeyWord(";");
		
		//On ajoute dans l'arbre l'instruction ecrire
		//On regarde s'il s'agit bien d'un bloc pour savoir quelle classe utilisée
		if(Nombre.estNombre(value))
			bloc.add(new Ecrire(new Nombre(Integer.parseInt(value))));
		else
			bloc.add(new Ecrire(new Idf(new Entree(value))));
		
		return bloc;
	}
}
