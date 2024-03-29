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
		//Case coup = null, liaison = null;
		//float valeurCoup = 0.0f;
/*
		for (Case c : coupsRestants) {
			float val = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);
			
			List<Case> adjacents = partie.getPlateau().getCasesAdjacentes(c, adversaire);
			int mult = partie.getPlateau().getMax();
			if (!adjacents.isEmpty()) val += 1000 * mult * mult; // grande priorité aux coups de contact en début de partie
			//System.out.println(c + ": " + val + " -> " + adjacents);
			if (val <= valeurCoup)
				continue;
			//System.out.println("OK");
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
		
		Case coup = null;
		Case liaison = null;
		float valeurMax = 0.0f;
		// System.out.println("skip ?" + groupePrincipal);
		// System.out.println("NEW TOUR");
		for (Case c : groupePrincipal) {
			List<Case> adjacentesVides = partie.getPlateau().getCasesAutour(c, null);
			for (Case vide : adjacentesVides) {
				float valeur = AnalyseUtils.poidsEmplacement(partie.getPlateau(), vide);
				// System.out.println("_________Case : " + vide);
				// System.out.println("valeur brute : " + valeur);
				List<Case> adjacents = partie.getPlateau().getCasesAdjacentes(vide, adversaire);
				int moyenne = partie.getPlateau().getMax();
				if (!adjacents.isEmpty()) {
					valeur += AnalyseUtils.influenceCase(partie.getPlateau(), vide) * moyenne; // grande priorité aux coups de contact en début de partie
					// System.out.println("+ adjacence : " + (AnalyseUtils.influenceCase(partie.getPlateau(), vide) * moyenne));
				}
				// System.out.println("Valeur finale : " + valeur);
				
				if (valeur <= valeurMax)
					continue;

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
}
