package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jeu.modele.Case;
import jeu.modele.Partie;
import jeu.modele.Plateau;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent.Etat;

/**
 * Niveau 2 Ordinateur choisissant toujours la case apportant le plus de points
 * localement. A la différence de l'ordinateur de niveau 1, celui-ci peut
 * s'étendre en diagonale afin de chercher plus rapidement les foyers de points
 * importants. Cet ordinateur sait se protéger des coupes de son adversaire sur
 * ses coups en diagonale
 * 
 * Lorsqu'aucun coup adjacent ou en diagonale n'est possible, un coup aléatoire
 * est joué
 */
public class OrdinateurExpansionRapide extends OrdinateurMeilleurCoupAdjacent {

	protected Map<Case, Case> coupsUrgents; // réactions immédiates à des coups de l'adersaire (menace de coupe par
											// exemple)

	public OrdinateurExpansionRapide(int id, String nom, Color couleur) {
		super(id, nom, couleur);
	}

	/**
	 * Initialise les variables de l'ordinateur
	 */
	public void initialiser(Partie partie) {
		super.initialiser(partie);
		coupsUrgents = new HashMap<>();
	}

	/**
	 * Permet à l'ordinateur de jouer un coup sur le plateau
	 * 
	 * @param partie la partie en cours
	 */
	public Case jouer(Partie partie) {
		Case coup = super.jouer(partie);
		supprimerCoupesComblees(coup); // on supprime les coupes comblées par le dernier coup joué par l'ordinateur
		// System.out.println(getNom() + " urgents : " + coupsUrgents);
		return coup;
	}

	/**
	 * Choisit le prochain coup à jouer
	 * 
	 * @param partie la partie en cours
	 * @return la case à jouer
	 */
	@Override
	public Case choisirCoup(Partie partie) {
		Case dernierCoup = partie.getDernierCoup();

		if (dernierCoup != null) { // obligatoirement un coup de l'adversaire if
			if (coupsUrgents.containsKey(dernierCoup)) { // réponse urgente à jouer Case
				return jouerCoupUrgent(dernierCoup);
			}
		}
		
		if (partie.getTour() <= 1) {
			return choisirPremierCoup(partie);
		}

		Case coup = null;

		switch (etat) {
		case EXPANSION:
			coup = meilleurCoupSansCoupe(partie);
			if (coup == null) etat = Etat.AGRESSIF;
			else break;
		case AGRESSIF:
			coup = meilleurCoupValeurContact(partie);
			// if (partie.getPlateau().getCasesAdjacentes(coup, adversaire).isEmpty())
			// etat = Etat.EXPANSION;
		}

		return coup;
	}

	/**
	 * Jouer le meilleur coup possible localement sur le plateau mais ne créant pas
	 * de coupe directe potentielle dans le groupe de l'ordinateur
	 */
	public Case meilleurCoupSansCoupe(Partie partie) {
		Case coup = null;
		Case liaison = null;
		float valeurMax = 0.0f;
		// System.out.println("skip ?" + groupePrincipal);
		for (Case c : groupePrincipal) {
			List<Case> adjacentesVides = partie.getPlateau().getCasesAutour(c, null);
			for (Case vide : adjacentesVides) {
				float valeur = AnalyseUtils.poidsEmplacement(partie.getPlateau(), vide);
				if (valeur <= valeurMax) continue;
				
				boolean sansCoupe = AnalyseUtils.coupLiable(partie.getPlateau(), c, vide);
				if (sansCoupe && verificationDoubleCoupe(partie.getPlateau(), vide, c)) {
					valeurMax = valeur;
					coup = vide;

					if (AnalyseUtils.coupEnDiagonaleVide(partie.getPlateau(), c, vide)) {
						liaison = c;
					} else { // le coup le plus rentable jusque la n'est pas une diagonale
						liaison = null;
					}
				}
			}
		}

		/*if (coup != null)
			return coupGroupe(coup);*/

		// return null;
/*
		for (Case c : coupsRestants) {
			float val = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);
			if (val <= valeurMax)
				continue;
			// le coup est le plus rentable trouvé jusque là
			List<Case> autour = partie.getPlateau().getCasesAutour(c, this);
			for (Case a : autour) {
				boolean sansCoupe = AnalyseUtils.coupLiable(partie.getPlateau(), a, c);
				if (sansCoupe && verificationDoubleCoupe(partie.getPlateau(), c, a)) { // si le coup peut se lier à un
																						// coup déjà joué sans coupe
																						// possible
					valeurMax = val;
					coup = c;
					if (AnalyseUtils.coupEnDiagonaleStricte(partie.getPlateau(), a, c)) {
						liaison = a;
					} else { // le coup le plus rentable n'est pas une diagonale
						liaison = null;
					}
					break;
				}
			}
		}*/

		if (coup != null) {
			// ajout des coups urgents en cas de coupe
			if (liaison != null) {
				int x1 = coup.getX(), y1 = coup.getY();
				int x2 = liaison.getX(), y2 = liaison.getY();

				Case diag1 = partie.getPlateau().getCase(x1, y2);
				Case diag2 = partie.getPlateau().getCase(x2, y1);

				// diag1 et diag2 ne sont théoriquement pas déjà présents dans le tableau
				coupsUrgents.put(diag1, diag2);
				coupsUrgents.put(diag2, diag1);
			}
			return coupGroupe(coup);
		}

		return null;
	}

	public Case jouerCoupUrgent(Case coup) {
		Case reponse = supprimerCoupesComblees(coup); // on supprime le coup opposé car celui-ci est à présent rempli
														// par un joueur
		return coupGroupe(reponse);
	}

	protected boolean verificationDoubleCoupe(Plateau p, Case coup1, Case coup2) {
		if (!AnalyseUtils.coupEnDiagonale(coup1, coup2)) return true;
		int x1 = coup1.getX(), y1 = coup1.getY();
		int x2 = coup2.getX(), y2 = coup2.getY();

		Case diag1 = p.getCase(x1, y2);
		Case diag2 = p.getCase(x2, y1);

		return !coupsUrgents.containsKey(diag1) && !coupsUrgents.containsKey(diag2);
	}

	protected Case supprimerCoupesComblees(Case coup) {
		Case reponse = coupsUrgents.remove(coup);
		if (reponse != null)
			coupsUrgents.remove(reponse);
		return reponse;
	}
}
