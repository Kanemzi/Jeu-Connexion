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
 * Niveau 3 Ordinateur se comportant de la même manière que l'ordinateur de
 * Niveau 2 mais contrôlant son enfermement par l'adversaire en début de partie
 */
public class OrdinateurLimitationEnfermement extends OrdinateurExpansionRapide {

	public OrdinateurLimitationEnfermement(int id, String nom, Color couleur) {
		super(id, nom, couleur);
	}

	/**
	 * Jouer le meilleur coup possible localement sur le plateau mais ne créant pas
	 * de coupe directe potentielle dans le groupe de l'ordinateur
	 */
	public Case meilleurCoupSansCoupe(Partie partie) {
		Case coup = null, liaison = null;
		float valeurCoup = 0.0f;

		for (Case c : coupsRestants) {
			float val = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);
			
			List<Case> adjacents = partie.getPlateau().getCasesAdjacentes(c, adversaire);
			if (!adjacents.isEmpty()) val *= 4; // grande priorité aux coups de contact en début de partie
			
			if (val <= valeurCoup)
				continue;
			// le coup est le plus rentable trouvé jusque là
			List<Case> autour = partie.getPlateau().getCasesAutour(c, this);
			for (Case a : autour) {
				boolean sansCoupe = AnalyseUtils.coupLiable(partie.getPlateau(), a, c);
				if (sansCoupe && verificationDoubleCoupe(partie.getPlateau(), c, a)) { // si le coup peut se lier à un
																						// coup déjà joué sans coupe
																						// possible
					valeurCoup = val;
					coup = c;
					if (AnalyseUtils.coupEnDiagonaleStricte(partie.getPlateau(), a, c)) {
						liaison = a;
					} else { // le coup le plus rentable n'est pas une diagonale
						liaison = null;
					}
					break;
				}
			}
		}

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
			return coup;
		}

		return null;
	}

	public Case jouerCoupUrgent(Case coup) {
		Case reponse = supprimerCoupesComblees(coup); // on supprime le coup opposé car celui-ci est à présent rempli
														// par un joueur
		return reponse;
	}

	protected boolean verificationDoubleCoupe(Plateau p, Case coup1, Case coup2) {
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
