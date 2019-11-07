package jeu.modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;
import java.util.Set;

/**
 * Représente une case du plateau de jeu.
 * Cette classe ce comporte comme une cellule dans une structure Classe-Union mais également comme un arbre, ce qui signifie qu'il est possible de
 * parcourir les arbres de Cases dans les deux sens.
 *
 */
public class Case extends Observable {
	
	private int x, y, valeur;
	private Joueur proprietaire;
	
	private Case parent;
	private List<Case> enfants;

	// une estimation de la profondeur de l'arbre.
	// Calculer la hauteur exacte à chaque fois rajouterait une complexité supplémentaire, d'autant plus que l'arbre est régulièrement applati
	private int profondeur; 

	/**
	 * Crée une nouvelle case sur le plateau
	 * @param x la position horizontale de la case
	 * @param y la position verticale de la case
	 * @param valeur la valeur de la case
	 */
	public Case(int x, int y, int valeur) {
		this.x = x;
		this.y = y;
		this.valeur = valeur;
		
		proprietaire = null;
		parent = null;
		enfants = new LinkedList<>(); // ajouts et suppressions en temps constant
		profondeur = 0;
	}
	
	/**
	 * Relie la case avec une autre
	 * On suppose que les deux cases ne sont pas déjà liées (afin de conserver une complexité constante)
	 * On suppose également que la case et c sont des représentants de leur groupe
	 * @param c la case à relier
	 * @return retourne le représentant du nouveau groupe créé
	 */
	public Case union(Case c) {
		if (profondeur < c.profondeur) {
			setParent(c);
			return c;
		}
		
		c.setParent(this);
		if (profondeur == c.profondeur)
			profondeur = c.profondeur + 1;
		
		return this;
	}
	
	/**
	 * @return le représentant du groupe de la case
	 */
	public Case classe() {
		if (parent == null) return this;
		setParent(parent.classe());
		return parent;
	}

	/**
	 * O(c) : avec c le nombre de cases dans la composante
	 * @return l'ensemble des noeuds dans le sous arbre de la case (case inclue)
	 */
	public Set<Case> getNoeudsArbre(Set<Case> liste ) {
		liste.add(this);
		for (Case enfant : enfants) {
			enfant.getNoeudsArbre(liste);
		}
		return liste;
	}
	
	/**
	 * O(c) : avec c le nombre de cases dans la composante
	 * @return retourne le score contenu dans le sous arbre de la classe (case inclue)
	 */
	public int getScoreArbre() {
		int compteur = valeur;
		for (Case enfant : enfants) {
			compteur += enfant.getScoreArbre();
		}
		return compteur;
	}
	
	public Joueur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Joueur proprietaire) {
		this.proprietaire = proprietaire;
		setChanged();
		notifyObservers(proprietaire);
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValeur() {
		return valeur;
	}
	
	public Case getParent() {
		return parent;
	}
	
	public void setParent(Case parent) {
		parent.getEnfants().remove(this); // O(1)
		this.parent = parent;
		parent.getEnfants().add(this); // O(1)
	}
	
	public List<Case> getEnfants() {
		return enfants;
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	@Override
	public String toString() {
		return "Case [x=" + x + ", y=" + y + ", p=" + profondeur + "]";
	}
}
