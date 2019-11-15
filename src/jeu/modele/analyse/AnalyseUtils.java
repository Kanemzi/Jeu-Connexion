package jeu.modele.analyse;

import java.util.List;
import java.util.Set;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Plateau;

public class AnalyseUtils {

	/**
	 * Retourne la hauteur d'un coup sur le plateau (le coup au centre étant le coup
	 * le plus haut et les coups sur les bords ayant une hauteur de 0)
	 * 
	 * @param p
	 *            le plateau de la partie en cours
	 * @param c
	 *            la case dont il faut récupérer la hauteur
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
	 * Retourne la force d'une position par rapport à la proportion du plateau qu'elle couvre
	 * 
	 * @param p
	 *            le plateau de la partie en cours
	 * @param c
	 *            la case dont il faut récupérer l'influence
	 * @return l'influence de la case c
	 */
	public static int influenceCase(Plateau p, Case c) {
		int taille = p.getTaille();
		int x = c.getX();
		int y = c.getY();

		int hx = ((x < taille / 2) ? x : taille - x - 1) + 1;
		int hy = ((y < taille / 2) ? y : taille - y - 1) + 1;

		return hx * hy;
	}

	/**
	 * Calcule le poids d'un emplacement sur le plateau à un certain tour de la
	 * partie O(1) : car 9 cases évaluées au maximum
	 * 
	 * 
	 * @param p
	 *            le plateau de la partie en cours
	 * @param c
	 *            la case dont on souhaite connaître le poids
	 * @return le poids de l'emplacement à la case c
	 */
	public static float poidsEmplacement(Plateau p, Case c) {
		int taille = p.getTaille();
		int cX = c.getX(), cY = c.getY();
		int debX = Math.max(0, cX - 1), debY = Math.max(0, cY - 1);
		int finX = Math.min(taille - 1, cX + 1), finY = Math.min(taille - 1, cY + 1);

		float poids = 0.0f;
		for (int y = debY; y <= finY; y++) {
			for (int x = debX; x <= finX; x++) {
				Case adj = p.getCase(x, y);
				if (adj.getProprietaire() == null)
					poids += adj.getValeur();
			}
		}
		return poids / 2; // seule la moitié des cases peut en théorie être prise contre un joueur agressif
	}

	/**
	 * Retourne un tableau des poids de chaque zone
	 * 
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
	 * Calcule le poids d'une zone sur le plateau
	 * 
	 * @param coefBordures
	 *            le coeficient d'importance des cases en bordure de plateau
	 *            (considérées comme normalement difficilement accessibles)
	 */
	public static float calculerPoidsZone(Plateau p, int x0, int y0, int x1, int y1, float coefBordures) {
		float poids = 0;
		for (int x = x0; x <= x1; x++) {
			for (int y = y0; y <= y1; y++) {
				Case c = p.getCase(x, y);
				float valeur = (float) c.getValeur();
				if (hauteurCase(p, c) == 0)
					valeur *= coefBordures;
				poids += valeur;
			}
		}
		return poids;
	}

	/**
	 * Calcule le poids des quarts du plateau O(n) : avec n le nombre de cases sur
	 * le plateau
	 * 
	 * @param p
	 * @return
	 */
	public static float[] calculerPoidsQuarts(Plateau p, float coefBordures) {
		int taille = p.getTaille();
		int parite = 1 - taille % 2;
		int centre = taille / 2 - parite;
		float[] poidsQuarts = new float[4];

		System.out.println("taille: " + taille);
		System.out.println("centre: " + centre);
		System.out.println("parite: " + parite);

		poidsQuarts[0] = calculerPoidsZone(p, 0, 0, centre, centre, coefBordures);
		poidsQuarts[1] = calculerPoidsZone(p, centre + parite, 0, taille - 1, centre, coefBordures);
		poidsQuarts[2] = calculerPoidsZone(p, 0, centre + parite, centre, taille - 1, coefBordures);
		poidsQuarts[3] = calculerPoidsZone(p, centre + parite, centre + parite, taille - 1, taille - 1, coefBordures);

		return poidsQuarts;
	}

	/**
	 * Vérifie si un coup est joué en diagonale
	 */
	public static boolean coupEnDiagonale(Case coup1, Case coup2) {
		int deltaX = Math.abs(coup1.getX() - coup2.getX());
		int deltaY = Math.abs(coup1.getY() - coup2.getY());

		return deltaX + deltaY == 2;
	}
	
	/**
	 * Vérifie si un coup est joué en diagonale sans autre case adjacente
	 */
	public static boolean coupEnDiagonaleStricte(Plateau p, Case coup1, Case coup2) {
		if (!coupEnDiagonale(coup1, coup2)) return false; // mouvement adjacent sans coupes
		Joueur joueurCoup = coup1.getProprietaire();
		int x1 = coup1.getX(), y1 = coup1.getY();
		int x2 = coup2.getX(), y2 = coup2.getY();
		
		Case diag1 = p.getCase(x1, y2);
		Case diag2 = p.getCase(x2, y1);
		
		return diag1.getProprietaire() != joueurCoup && diag2.getProprietaire() != joueurCoup;
	}
	
	/**
	 * Vérifie si un coup est joué en diagonale sans autre case adjacente
	 */
	public static boolean coupEnDiagonaleVide(Plateau p, Case coup1, Case coup2) {
		if (!coupEnDiagonale(coup1, coup2)) return false; // mouvement adjacent sans coupes
		int x1 = coup1.getX(), y1 = coup1.getY();
		int x2 = coup2.getX(), y2 = coup2.getY();
		
		Case diag1 = p.getCase(x1, y2);
		Case diag2 = p.getCase(x2, y1);
		
		return diag1.getProprietaire() == null && diag2.getProprietaire() == null;
	}

	/**
	 * Vérifie si deux coup joués sont sur une diagonale vide ou adjacents
	 * /!\ retourne true si le coup n'est pas en diagonale car la fonction ne sera appelée que sur des cases adjacentes
	 * (si true, on peut considérer le coup safe -> exception pour les doubles coupes à effectuer du côté du bot)
	 * @param coup1 un coup déjà existant posé par un joueur
	 * @param coup2 le coup que le joueur souhaiter jouer
	 */
	public static boolean coupLiable(Plateau p, Case coup1, Case coup2) {
		if (coup1.getProprietaire() == coup2.getProprietaire()) return false;
		if (!coupEnDiagonale(coup1, coup2)) return true; // mouvement adjacent sans coupes
		Joueur joueurCoup = coup1.getProprietaire();
		int x1 = coup1.getX(), y1 = coup1.getY();
		int x2 = coup2.getX(), y2 = coup2.getY();
	
		Case diag1 = p.getCase(x1, y2);
		Case diag2 = p.getCase(x2, y1);
		
		return !coupEnDiagonaleStricte(p, coup1, coup2) || (diag1.getProprietaire() == null && diag2.getProprietaire() == null);
	}

	/**
	 * 
	 */
	public static List<Case> listerCoupsForme(Plateau p, Set<Case> coupsRestants, Case coup1, Case coup2) {
		return null;
	}
}
