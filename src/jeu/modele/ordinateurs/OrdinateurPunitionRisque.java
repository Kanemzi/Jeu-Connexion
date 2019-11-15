package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.List;

import jeu.modele.Case;
import jeu.modele.Partie;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent.Etat;

/**
 * Niveau 4 Ordinateur adoptant la stratégie de l'ordinateur de Niveau 3 mais en
 * punissant les coups risqués joués par l'adversaire (par exemple un coup de
 * réduction pouvant être coupé)
 */
public class OrdinateurPunitionRisque extends OrdinateurLimitationEnfermement {

	public OrdinateurPunitionRisque(int id, String nom, Color couleur) {
		super(id, nom, couleur);
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
		Case dernierCoup = partie.getDernierCoup();

		if (dernierCoup != null) { // obligatoirement un coup de l'adversaire
			if (coupsUrgents.containsKey(dernierCoup)) { // réponse urgente à jouer Case
				return jouerCoupUrgent(dernierCoup);
			}
		}

		if (partie.getTour() <= 1) {
			return choisirPremierCoup(partie);
		}

		Case coupe = chercherCoupe(partie, dernierCoup);
		if (coupe != null) {
			return coupe;
			// TODO vérifier si la coupe n'est pas directement liée au groupe principal
			// TODO vérifier également les coupes en saut de 1 et keima
		}

		Case coup = null;

		switch (etat) {
		case EXPANSION:
			coup = meilleurCoupSansCoupe(partie);
			if (coup == null)
				etat = Etat.AGRESSIF;
			else
				break;
		case AGRESSIF:
			coup = meilleurCoupValeurContact(partie);
		}

		return coup;
	}

	/**
	 * Cherche une éventuelle coupe sur le dernier coup de l'adversaire
	 * 
	 * @param partie
	 *            la partie en cours
	 * @param dernierCoup
	 *            le coup à analyser
	 * @return un coup qui coupe si possible, null sinon
	 */
	private Case chercherCoupe(Partie partie, Case dernierCoup) {
		List<Case> autour = partie.getPlateau().getCasesAutour(dernierCoup, dernierCoup.getProprietaire());
		for (Case c : autour) {
			if (AnalyseUtils.coupLiable(partie.getPlateau(), c, dernierCoup)) return null;
			List<Case> diagonalesInversees = diagonalesInversees(partie, dernierCoup, c);
			for(Case diag : diagonalesInversees) {
				if (diag.getProprietaire() == null) return diag;
			}
		}
		return null;
	}
}
