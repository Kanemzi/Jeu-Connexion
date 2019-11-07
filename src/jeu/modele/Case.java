package jeu.modele;

import java.util.LinkedList;
import java.util.List;
import java.util.Observable;

public class Case extends Observable {
	
	private int x, y, valeur;
	private Joueur proprietaire;
	
	private Case parent;
	private List<Case> enfants;

	// une estimation de la profondeur de l'arbre.
	// Calculer la hauteur exacte à chaque fois rajouterait une compexité inutile, d'autant plus que l'arbre est régulièrement applati
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
		this.proprietaire = null;
		this.parent = null;
		this.enfants = new LinkedList<>(); // ajouts et suppressions en temps constant
		this.profondeur = 0;
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
		} else {
			c.setParent(this);
			if (profondeur == c.profondeur) {
				profondeur = c.profondeur + 1;    
			}
			return this;
		}
	}
	
	/**
	 * @return le représentant du groupe de la case
	 */
	public Case classe() {
		// @TODO: spécifier la complexité
		Case p = this;
		while (p.parent != null) {
			p = p.parent;
		}
		if (parent != null) { // remonter de parent uniquement si le noeud n'est pas déjà un représentant
			setParent(p); // applatissement de l'arbre
		}
		return p;
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
	
	public List<Case> getEnfants() {
		return enfants;
	}
	
	public void setParent(Case parent) {
		parent.getEnfants().remove(this); // O(1)
		this.parent = parent;
		parent.getEnfants().add(this); // O(1)
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}

	@Override
	public String toString() {
		return "Case [x=" + x + ", y=" + y + ", valeur=" + valeur + "]";
	}
}
