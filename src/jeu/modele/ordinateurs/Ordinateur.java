package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.HashSet;
import java.util.Set;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;

public abstract class Ordinateur extends Joueur {
	protected Set<Case> coupsRestants; // l'ensemble des cases encore vides sur le plateau
	
	public Ordinateur(int id, String nom, Color couleur) {
		super(id, nom, couleur);
		ordinateur = true;
		coupsRestants = null;
	}
	
	public void initialiser(Partie partie) {
		coupsRestants = new HashSet<>();
		coupsRestants.addAll(partie.getPlateau().getCases());
	}
	
	/**
	 * Permet à l'ordinateur de jouer un coup sur le plateau
	 * 
	 * @param partie
	 *            la partie en cours
	 */
	public Case jouer(Partie partie) {
		if (coupsRestants == null)
			initialiser(partie);

		Case dernierCoup = partie.getDernierCoup();
		if (dernierCoup != null) {
			coupsRestants.remove(dernierCoup);
		}

		Case coup = choisirCoup(partie);
		super.jouer(partie, coup);

		coupsRestants.remove(coup);

		return coup;
	}
	
	public abstract Case choisirCoup(Partie partie);
}
