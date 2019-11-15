package jeu.modele.ordinateurs;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jeu.Config;
import jeu.modele.Case;
import jeu.modele.Partie;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent.Etat;

/**
 * Niveau 1 Ordinateur un peu plus avancé, ne joue que des coups adjacents à son
 * groupe principal. Le coup choisi est celui apportant le plus de points
 * localement (somme des valeurs des cases à une distance de 1)
 * 
 * Lorsqu'aucun coup adjacent n'est possible, l'ordinateur passe en mode
 * "agressif" et va uniquement tenter de couper les groupes de l'adversaire sans
 * forcément donner de l'importance à la formation de nouveaux groupes
 * 
 */
public class OrdinateurMeilleurCoupAdjacent extends OrdinateurAleatoire {

	protected enum Etat {
		EXPANSION, AGRESSIF
	}

	protected Etat etat;

	protected Set<Case> groupePrincipal; // l'ensemble des cases liées directement ou indirectement au groupe principal
											// construit

	// memoire de l'IA
	protected List<Case> coupsImportants; // coups à jouer de préférence avant l'adversaire (coupe de deux groupes)

	public OrdinateurMeilleurCoupAdjacent(int id, String nom, Color couleur) {
		super(id, nom, couleur);
		etat = Etat.EXPANSION;
	}

	/**
	 * Initialise les variables de l'ordinateur
	 */
	public void initialiser(Partie partie) {
		super.initialiser(partie);
		coupsImportants = new ArrayList<>();
		groupePrincipal = new HashSet<>();
	}

	/**
	 * Choisit le prochain coup à jouer
	 * 
	 * @param partie la partie en cours
	 * @return la case à jouer
	 */
	@Override
	public Case choisirCoup(Partie partie) {
		if (partie.getTour() <= 1) {
			return choisirPremierCoup(partie);
		}

		Case coup = null;

		switch (etat) {
		case EXPANSION:
			coup = meilleurCoupAdjacent(partie);
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
	 * Choisit le premier coup à jouer. Le premier coup est très important et peut
	 * être décisif, il possède donc une fonction dédiée.
	 * 
	 * @param plateau le plateau du jeu
	 * @return le premier coup à jouer
	 */
	public Case choisirPremierCoup(Partie partie) {
		int centrePlateau = partie.getPlateau().getTaille() / 2;
		int parite = partie.getPlateau().getTaille() % 2;

		if (parite == 1) { // tenter de prendre le centre
			Case c = partie.getPlateau().getCase(centrePlateau, centrePlateau);
			if (c.getProprietaire() == null)
				return coupGroupe(c);
		}

		float coef = (partie.getPlateau().getTaille() <= Config.LIMITE_PETIT) ? Config.COEF_BORDURES_PETIT
				: Config.COEF_BORDURES_GRAND;
		float[] poidsQuarts = AnalyseUtils.calculerPoidsQuarts(partie.getPlateau(), coef);

		// besoin de connaître les deux meilleurs coups (si le meilleur est déjà pris)
		int idmeilleur = -1, idsecond = -1;
		for (int i = 0; i < 4; i++) {
			if (idmeilleur == -1 || poidsQuarts[i] > poidsQuarts[idmeilleur]) {
				idsecond = idmeilleur;
				idmeilleur = i;
			} else if (idsecond == -1 || poidsQuarts[i] > poidsQuarts[idsecond]) {
				idsecond = i;
			}
		}

		int xmeilleur = idmeilleur % 2;
		int ymeilleur = idmeilleur / 2;

		Case meilleurCoup = partie.getPlateau().getCase(centrePlateau + xmeilleur - 1, centrePlateau + ymeilleur - 1);
		if (meilleurCoup.getProprietaire() == null)
			return coupGroupe(meilleurCoup);

		int xsecond = idsecond % 2;
		int ysecond = idsecond / 2;

		Case secondCoup = partie.getPlateau().getCase(centrePlateau + xsecond - 1, centrePlateau + ysecond - 1);
		if (secondCoup.getProprietaire() == null)
			return coupGroupe(secondCoup);

		// sécurité même si impossible théoriquement
		return coupGroupe(meilleurCoupAdjacent(partie));
	}

	/**
	 * Jouer le meilleur coup possible localement sur le plateau et adjacent à un
	 * groupe déjà créé (sans coupe indirecte)
	 */
	public Case meilleurCoupAdjacent(Partie partie) {
		Case coup = null;
		float valeurMax = 0.0f;

		for (Case c : groupePrincipal) {
			List<Case> adjacentesVides = partie.getPlateau().getCasesAdjacentes(c);
			for (Case vide : adjacentesVides) {
				float valeur = AnalyseUtils.poidsEmplacement(partie.getPlateau(), vide);
				if (valeur > valeurMax) {
					valeurMax = valeur;
					coup = vide;
				}
			}
		}

		if (coup != null)
			return coupGroupe(coup);

		return null;
	}

	/**
	 * Retourne l'un des meilleurs coup sur le plateau par rapport à la valeur
	 * moyenne de ses coups voisins
	 */
	public Case meilleurCoupValeur(Partie partie) {
		Case coup = null;
		float valeurCoup = 0.0f;
		for (Case c : coupsRestants) {
			float valeur = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);
			if (valeur > valeurCoup) {
				valeurCoup = valeur;
				coup = c;
			}
		}

		return coup;
	}

	/**
	 * Retourne l'un des meilleurs coup sur le plateau par rapport à la valeur
	 * moyenne de ses coups voisins Joue en priorité (à une valeur de k près) un
	 * coup adjacent à un groupe adversaire afin de maximiser les chances de bloquer
	 * son expansion Une plus grande importance est donnée aux coups ayant 2 ou 3
	 * cases adversaire adjacentes (plus grande probabilité de de couper des groupes
	 * chez un joueur agressif) 4 cases adversaire adjacentes peuvent à l'inverse
	 * plus souvent être dues à une valeur de case peu intéressante (on ignore ces
	 * cases)
	 */
	public Case meilleurCoupValeurContact(Partie partie) {
		Case coup = null;
		float valeurCoup = 0.0f;

		for (Case c : coupsRestants) {
			float valeur = AnalyseUtils.poidsEmplacement(partie.getPlateau(), c);

			List<Case> adjacents = partie.getPlateau().getCasesAdjacentes(c, adversaire); // cases adversaires
																							// adjacentes
			if (!adjacents.isEmpty()) {
				valeur += partie.getPlateau().getMax(); // on attribue une plus grande valeur à une case adjacente

				int nbAdj = adjacents.size();
				if (nbAdj < 4)
					valeur *= nbAdj;
			}

			if (valeur > valeurCoup) {
				valeurCoup = valeur;
				coup = c;
			}
		}

		return coup;
	}

	/**
	 * Ajoute un coup au groupe principal
	 */
	protected Case coupGroupe(Case c) {
		groupePrincipal.add(c);
		// System.out.println(groupePrincipal);
		return c;
	}
}
