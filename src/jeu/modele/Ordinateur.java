package jeu.modele;

import java.awt.Color;
import java.util.List;
import java.util.Map;
import java.util.Random;

import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.analyse.PoidsPlateau;

public class Ordinateur extends Joueur {

	// memoire de l'IA
	private Map<Case, Case> coupsUrgents; // coup à jouer le plus vite possible (protection de coupes)
	private List<Case> coupsImportants; // coups à jouer de préférence avant l'adversaire (coupe de deux groupes)

	public Ordinateur(int id, String nom, Color couleur) {
		super(id, nom, couleur);
		this.ordinateur = true;
	}

	/**
	 * Permet à l'ordinateur de jouer un coup sur le plateau
	 * 
	 * @param partie la partie en cours
	 */
	public Case jouer(Partie partie) {
		Case coup = choisirCoup(partie);
		super.jouer(partie, coup);
		return coup;
	}

	/**
	 * Choisit le prochain coup à jouer
	 * 
	 * @param partie la partie en cours
	 * @return la case à jouer
	 */
	public Case choisirCoup(Partie partie) {
		Case dernierCoup = partie.getDernierCoup();
		if (dernierCoup != null) { // obligatoirement un coup de l'adversaire
			if (coupsUrgents.containsKey(dernierCoup)) { // réponse urgente à jouer
				Case reponse = coupsUrgents.remove(dernierCoup);
				coupsUrgents.remove(reponse);
				return reponse;
			}
		}
		
		if (partie.getTour() <= 1) {
			System.out.println("premier coup");
			return choisirPremierCoup(partie.getPlateau());
		}
		
		return coupAleatoire(partie);
	}

	/**
	 * Choisit le premier coup à jouer. Le premier coup est très important et peut
	 * être décisif, il possède donc une fonction dédiée.
	 * 
	 * @param plateau le plateau du jeu
	 * @return le premier coup à jouer
	 */
	public Case choisirPremierCoup(Plateau plateau) {
		PoidsPlateau pp = AnalyseUtils.calculerPoidsPlateau(plateau);
		return plateau.getCase(pp.xmax, pp.ymax);
	}


	
	/**
	 * Jouer un coup aléatoire sur le plateau
	 */
	public Case coupAleatoire(Partie partie) {
		Case coup = null;

		Random r = new Random();
		while (coup == null || coup.getProprietaire() != null) {
			int x = r.nextInt(partie.getPlateau().getTaille());
			int y = r.nextInt(partie.getPlateau().getTaille());
			coup = partie.getPlateau().getCase(x, y);
		}
		return coup;
	}
}
