package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

import jeu.Config;
import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.analyse.PoidsPlateau;

public class OrdinateurMeilleurCoupAdjacent extends OrdinateurAleatoire {

	enum Etat {
		INFLUENCE, // L'ordinateur cherche à se développer au maximum sur le plateau et à réduire
					// l'adversaire
		REMPLISSAGE // L'ordinateur cherche à rejoindre les groupes de cases les plus "lourds" tout
					// en réduisant l'intérieur du territoire adversaire si cela vaut plus de points
	}

	protected Etat etat;

	// memoire de l'IA
	protected Map<Case, Case> coupsUrgents; // coup à jouer le plus vite possible (protection de coupes)
	protected List<Case> coupsImportants; // coups à jouer de préférence avant l'adversaire (coupe de deux groupes)
	

	public OrdinateurMeilleurCoupAdjacent(int id, String nom, Color couleur) {
		super(id, nom, couleur);
		etat = Etat.INFLUENCE;
	}

	/**
	 * Initialise les variables de l'ordinateur
	 */
	public void initialiser(Partie partie) {
		super.initialiser(partie);
		coupsUrgents = new HashMap<>();
		coupsImportants = new ArrayList<>();
	}

	/**
	 * Choisit le prochain coup à jouer
	 * 
	 * @param partie
	 *            la partie en cours
	 * @return la case à jouer
	 */
	@Override
	public Case choisirCoup(Partie partie) {
		/*
		 * Case dernierCoup = partie.getDernierCoup();
		 * 
		 * if (dernierCoup != null) { // obligatoirement un coup de l'adversaire if
		 * (coupsUrgents.containsKey(dernierCoup)) { // réponse urgente à jouer Case
		 * reponse = coupsUrgents.remove(dernierCoup); coupsUrgents.remove(reponse);
		 * return reponse; } }
		 */
		if (partie.getTour() <= 1) {
			System.out.println("premier coup");
			return choisirPremierCoup(partie);
		}

		return coupAleatoireSansCoupe(partie);
	}

	/**
	 * Choisit le premier coup à jouer. Le premier coup est très important et peut
	 * être décisif, il possède donc une fonction dédiée.
	 * 
	 * @param plateau
	 *            le plateau du jeu
	 * @return le premier coup à jouer
	 */
	public Case choisirPremierCoup(Partie partie) {
		int centrePlateau = partie.getPlateau().getTaille() / 2;
		int parite = partie.getPlateau().getTaille() % 2;

		if (parite == 1) { // tenter de prendre le centre
			Case c = partie.getPlateau().getCase(centrePlateau, centrePlateau);
			if (c.getProprietaire() == null)
				return c;
		}

		float coef = (partie.getPlateau().getTaille() <= Config.LIMITE_PETIT) ? Config.COEF_BORDURES_PETIT
				: Config.COEF_BORDURES_GRAND;
		float[] poidsQuarts = AnalyseUtils.calculerPoidsQuarts(partie.getPlateau(), coef);

		// besoin de connaître les deux meilleurs coups (si le meilleur est déjà pris)
		int idmeilleur = 3, idsecond = 3;
		for (int i = 0; i < 4; i++) {
			if (poidsQuarts[i] > poidsQuarts[idmeilleur]) {
				idsecond = idmeilleur;
				idmeilleur = i;
			} else if (poidsQuarts[i] > poidsQuarts[idsecond]) {
				idsecond = i;
			}
		}

		int xmeilleur = idmeilleur % 2;
		int ymeilleur = idmeilleur / 2;

		Case meilleurCoup = partie.getPlateau().getCase(centrePlateau + xmeilleur - 1, centrePlateau + ymeilleur - 1);
		if (meilleurCoup.getProprietaire() == null)
			return meilleurCoup;

		int xsecond = idsecond % 2;
		int ysecond = idsecond / 2;

		Case secondCoup = partie.getPlateau().getCase(centrePlateau + xsecond, centrePlateau + ysecond);
		if (secondCoup.getProprietaire() == null)
			return secondCoup;

		// sécurité même si impossible théoriquement
		return coupAleatoireSansCoupe(partie);
	}

	/**
	 * Jouer le meilleur coup possible localement sur le plateau mais ne créant pas de coupe
	 * potentielle dans le groupe de l'ordinateurs
	 */
	public Case coupAleatoireSansCoupe(Partie partie) {
		Case coup = null;
		float valeurCoup = 0.0f;
		
		for (Case c : coupsRestants) {
			List<Case> adjacents = partie.getPlateau().getCasesAdjacentes(c, this);
			if (!adjacents.isEmpty()) {
				float val = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);
				if (val > valeurCoup)
					valeurCoup = val;
					coup = c;
			}
		}

		if (coup != null)
			return coup;

		System.out.println("coup random");
		// si aucun coup safe possible, jouer un coup aléatoire
		return coupAleatoire(partie);
	}
}