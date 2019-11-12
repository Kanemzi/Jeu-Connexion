package jeu.modele.analyse;

import java.util.List;

import jeu.modele.Case;
import jeu.modele.Plateau;

public class AnalyseUtils {
	
	/**
	 * Retourne la hauteur d'un coup sur le plateau (le coup au centre étant le coup le plus haut et les coups sur les bords ayant une hauteur de 0)
	 * @param p le plateau de la partie en cours
	 * @param c la case dont il faut récupérer la hauteur
	 * @return la hauteur de la case c
	 */
	public static int hauteurCase(Plateau p, Case c) {
		int taille = p.getTaille();
		int x = c.getX();
		int y = c.getY();
		
		int hx = (x < taille / 2) ? x : taille - x - 1;
		int hy = (y < taille / 2) ? y : taille - y - 1;
		
		return Math.min(hx, hy);
	}
	
	/**
	 * Calcule le poids d'un emplacement sur le plateau
	 * O(1) : car 9 cases évaluées au maximum
	 * @param p le plateau de la partie en cours
	 * @param c la case dont on souhaite connaître le poids
	 * @return le poids de l'emplacement à la case c
	 */
	public static int poidsEmplacement(Plateau p, Case c) {
		int taille = p.getTaille();
		int cX = c.getX(), cY = c.getY();
		int debX = Math.max(0, cX - 1), debY = Math.max(0, cY -  1);
		int finX = Math.min(taille - 1, cX + 1), finY = Math.min(taille - 1, cY + 1);
		
		int poids = 0;
		for (int y = debY; y <= finY; y++) {
			for (int x = debX; x <= finX; x++) {
				poids += p.getCase(x, y).getValeur();
			}
		}
		return poids;
	}
	
	/**
	 * Retourne un tableau des poids de chaque zone 
	 * @param p
	 * @return
	 */
	public static PoidsPlateau calculerPoidsPlateau(Plateau p) {
		int taille = p.getTaille();

		PoidsPlateau pp = new PoidsPlateau(p.getTaille());
		
		for (int y = 0; y < taille; y++) {
			for (int x = 0; x < taille; x++) {
				pp.poids[y][x] = poidsEmplacement(p, p.getCase(x, y));
				if (pp.poids[y][x] > pp.poidsMax) {
					pp.poidsMax = pp.poids[y][x];
					pp.xmax = x;
					pp.ymax = y;
				}
			}
		}

		return pp;
	}
	
	
	/**
	 * 
	 * @param c
	 * @return
	 */
	public static List<Case> getCasesAutour(Case c) {
		return null;
	}
}
