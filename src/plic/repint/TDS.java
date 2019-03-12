package plic.repint;

import java.util.HashMap;

import plic.exceptions.DoubleDeclarationException;

/**
 * Classe generant la representation intermediaire
 * @author terry
 */
public class TDS {
	/**
	 * Compte le nb de deplacement
	 */
	private int cptDeplacement;
	/**
	 * Stocke les differentes declarations de variables
	 */
	private HashMap<Entree, Symbole> table;
	/**
	 * Singleton de cette classe assurant l'unicite
	 */
	private static TDS tds;
	
	/**
	 * Constructeur prive initialisant le singleton
	 */
	private TDS() {
		this.cptDeplacement = 0;
		this.table = new HashMap<>();
	}
	
	/**
	 * Instancie ou retourne l'element unique tds
	 * @return tds
	 * 		element unique encapsulant une table intermediaire
	 */
	public static TDS getInstance() {
		if(tds == null)
			tds = new TDS();
		
		return tds;
	}
	
	/**
	 * Ajoute un couple de cle valeur dans la table representant le type et le nom de la variable
	 * @param e
	 * 		Entree : nom de la variable
	 * @param s
	 * 		Symbole : type de la variable
	 * @throws DoubleDeclarationException
	 * 		Levee lors d'une double declaration de variables
	 */
	public void ajouter(Entree e, Symbole s) throws DoubleDeclarationException {
		if(this.table.containsKey(e))
			throw new DoubleDeclarationException("Variable already defined");
		
		//On modifie le déplacement dans symbole
		s.setDeplacement(this.cptDeplacement);
		
		//On ajoute le couple dans la table
		this.table.put(e, s);
		
		//On décrémente le déplacement
		this.cptDeplacement -= 4;
	}
	
	public Symbole identifier(Entree e) {
		return this.table.get(e);
	}
	
	/**
	 * Utile pour les tests
	 * @param key
	 * 		Cle a verifier
	 * @return bool
	 * 		vrai si la cle est presente dans la table
	 */
	public boolean containsKey(Entree key) {
		return this.table.containsKey(key);
	}
	
	/**
	 * Taille de la table
	 * @return size
	 * 		taille de la table
	 */
	public int getLongueurTab() {
		return this.table.size();
	}
	
	/**
	 * Renvoie le compteur de deplacement
	 * @return cptDeplacement
	 * 		Compteur de deplacement 
	 */
	public int getCptDeplacement() {
		return this.cptDeplacement;
	}
}
