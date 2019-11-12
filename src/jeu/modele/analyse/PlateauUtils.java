package jeu.modele.analyse;

import jeu.modele.Case;
import jeu.modele.Plateau;

public class PlateauUtils {
	
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
	 * Retourne un tableau des poids de chaque zone 
	 * @param p
	 * @return
	 */
	public static int[][] poidsCases(Plateau p) {
		return new int[1][1];
	}
}
