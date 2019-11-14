package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.Random;

import jeu.modele.Case;
import jeu.modele.Partie;

/**
 * Niveau 0
 * Ordinateur adoptant la stratégie la plus basique possible, consistant à jouer un coup aléatoirement sur le plateau à chaque tour
 */
public class OrdinateurAleatoire extends Ordinateur {
	
	private Random ran;
	
	public OrdinateurAleatoire(int id, String nom, Color couleur) {
		super(id, nom, couleur);
		ran = new Random();
	}
	
	public Case choisirCoup(Partie partie) {
		return coupAleatoire(partie);
	}

	/**
	 * Jouer un coup aléatoire libre sur le plateau
	 */
	public Case coupAleatoire(Partie partie) {
		int id = ran.nextInt(coupsRestants.size());
		for (Case c : coupsRestants) {
			if(id-- == 0) {
				return c;
			}
		}
		return null;
	}
}
