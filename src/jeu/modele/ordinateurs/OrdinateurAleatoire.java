package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent.Etat;

public class OrdinateurAleatoire extends Ordinateur {
	public OrdinateurAleatoire(int id, String nom, Color couleur) {
		super(id, nom, couleur);
	}
	
	public Case choisirCoup(Partie partie) {
		return coupAleatoire(partie);
	}
	
	/**
	 * Jouer un coup aléatoire libre sur le plateau
	 */
	public Case coupAleatoire(Partie partie) {
		// prend le premier élément stocké dans les coups restants
		for (Case c : coupsRestants) {
			return c;
		}
		
		// ne doit en théorie jamais arriver
		return null;
	}	
}
