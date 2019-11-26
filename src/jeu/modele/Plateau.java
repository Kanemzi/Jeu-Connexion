package jeu.modele;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Set;

public class Plateau {

	private int n;
	private int max;
	private List<Case> cases;
	private int placees; // nombre de cases déjà coloriées

	public Plateau() {
		placees = 0;
	}

	/**
	 * Générer un plateau aléatoire O(n) : avec n le nombre de cases sur le plateau
	 * (ne pas confondre avec le n passé en paramètre qui représente la taille du
	 * plateau)
	 * 
	 * @param n
	 *            la taille du plateau
	 * @param max
	 *            la valeur maximale d'une cellule
	 */
	public Plateau RemplirGrilleAleatoire(int n, int max) {
		this.n = n;
		this.max = max;
		cases = new ArrayList<>(n * n);

		Random ran = new Random();
		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				cases.add(new Case(x, y, ran.nextInt(max) + 1));
			}
		}

		return this;
	}

	/**
	 * Génère un plateau à partir d'un fichier O(n) : avec n le nombre de cases sur
	 * le plateau (ne pas confondre avec le n passé en paramètre qui représente la
	 * taille du plateau)
	 * 
	 * @param file
	 *            le nom du fichier à charger
	 */
	public Plateau RemplirGrilleFichier(String nomFichier, Joueur[] joueurs) {
		File fichier = new File(nomFichier);
		try {
			Scanner scan = new Scanner(fichier);

			n = Integer.parseInt(scan.next());
			max = Integer.parseInt(scan.next());
			cases = new ArrayList<>(n * n);

			// remplissage du plateau
			for (int y = 0; y < n; y++) {
				for (int x = 0; x < n; x++) {
					int valeur = Integer.parseInt(scan.next());
					cases.add(new Case(x, y, Math.min(valeur, max)));
				}
			}

			// attribution des cases aux joueurs
			for (int y = 0; y < n; y++) {
				for (int x = 0; x < n; x++) {
					int id = Integer.parseInt(scan.next());
					if (id == 0)
						continue;
					placees++;
					Joueur proprietaire = joueurs[id - 1];
					ColorerCase(x, y, proprietaire);
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		return this;
	}

	/**
	 * Change la couleur de la case en fonction de la couleur d'un des joueurs de la
	 * partie O(log max(cs)) : avec cs les tailles des composantes adjacentes à la
	 * case coloriée -> dans la pratique, complexité constante
	 * 
	 * @param x
	 *            la position horizontale de la case à colorier
	 * @param y
	 *            la position verticale de la case à colorier
	 * @param proprietaire
	 *            le joueur ayant pris la case
	 */
	public void ColorerCase(int x, int y, Joueur proprietaire) {
		Case nouvelleCase = getCase(x, y);
		nouvelleCase.setProprietaire(proprietaire);

		RelierComposantes(x, y, proprietaire, true);
	}

	/**
	 * Teste si il existe un chemin d'une couleur spécifique entre deux cases O(log
	 * max(c1, c2)) : avec c1 le nombre d'éléments dans la composante de la case c1
	 * et c2 le nombre d'éléments dans la composante de case c2 -> dans la pratique,
	 * complexité constante
	 * 
	 * @param c1
	 *            la première case à tester
	 * @param c2
	 *            la seconde case à tester
	 * @param proprietaire
	 *            le joueur de la couleur dont on souhaite tester le chemin
	 * @return un tableau contenant les deux représentants de c1 et c2 si ils sont
	 *         différents (pas de chemin). retourne null si les représentants sont
	 *         égaux (un chemin existe)
	 */
	public Case[] ExisteCheminCases(Case c1, Case c2, Joueur proprietaire) {
		Case representant1 = c1.classe();
		Case representant2 = c2.classe();

		if (c1.getProprietaire() != c2.getProprietaire() || c1.getProprietaire() != proprietaire)
			return new Case[] { representant1, representant2 };

		return (representant1 != representant2) ? new Case[] { representant1, representant2 } : null;
	}

	/**
	 * Teste si la coloration d'une certaine case de la couleur d'un joueur permet
	 * de relier deux composantes déjà présentes sur le plateau O(log max(cs)) :
	 * avec cs les tailles des composantes adjacentes à la case testée -> dans la
	 * pratique, complexité constante
	 * 
	 * @param x
	 *            la position horizontale de la case à tester
	 * @param y
	 *            la position verticale de la case à tester
	 * @param joueur
	 *            le joueur de la couleur dont on souhaite tester la liaison
	 * @param lier
	 *            si true, la liaison sera automatiquement effectuée, sinon, le test
	 *            de liaison sera retourné sans création de nouvelles liaisons
	 * 
	 * @return true si la coloration d'une case permet de relier plusieurs
	 *         composantes
	 */
	public boolean RelierComposantes(int x, int y, Joueur joueur, boolean lier) {
		Case nouvelleCase = getCase(x, y);
		List<Case> adjacentes = getCasesAdjacentes(nouvelleCase, joueur);
		if (adjacentes.size() == 0) {
			return false;
		}
		boolean toutesLiees = true;

		// complexité de la boucle constante car quelque soit N, la liste des adjacents
		// vérifiera toujours |adjacents| <= 4
		Case c1 = adjacentes.get(0);
		
		if (adjacentes.size() > 1) {
			for (int i = 1; i < adjacentes.size(); i++) {
				Case c2 = adjacentes.get(i);
	
				Case[] representants = ExisteCheminCases(c1, c2, joueur);
				boolean liees = (representants == null);
	
				if (lier && !liees) {
					representants[0].union(representants[1]);
				}
	
				toutesLiees = toutesLiees && liees;
			}
		}

		if (lier) {
				Case representant = c1.classe();
				nouvelleCase.union(representant);
		}

		return !toutesLiees;
	}

	/**
	 * Retourne l'ensemble des cases d'une composante O(n) : avec n le nombre de
	 * cases dans la composante
	 * 
	 * @param x
	 *            la position horizontale de la case à tester
	 * @param y
	 *            la position verticale de la case à tester
	 * @return la liste des cases de la composante ou null si la case ne se trouve
	 *         dans aucune composante
	 */
	public Set<Case> AfficherComposante(int x, int y) {
		Case representant = getCase(x, y).classe();
		if (representant.getProprietaire() == null)
			return null;
		return representant.getNoeudsArbre(new LinkedHashSet<Case>());
	}

	/**
	 * Retourne le score total d'une composante O(n) : avec n le nombre de cases
	 * dans la composante
	 * 
	 * @param x
	 *            la position horizontale de la case à tester
	 * @param y
	 *            la position verticale de la case à tester
	 * @return le score de la composante
	 */
	public int AfficherScore(int x, int y) {
		Case representant = getCase(x, y).classe();
		return representant.getScoreArbre();
	}

	/**
	 * Permet de récupérer les cases adjacentes à une case du plateau
	 * 
	 * @param c
	 *            la case à tester
	 * @param joueur,
	 *            si précisé, ne retourne que les cases adjacentes appartenant à ce
	 *            joueur
	 * @return la liste des cases adjacentes à c
	 */
	public List<Case> getCasesAdjacentes(Case c, Joueur joueur) {
		List<Case> cases = new ArrayList<>(4);
		int x = c.getX(), y = c.getY();

		Case gauche = getCase(x - 1, y);
		if (gauche != null && gauche.getProprietaire() == joueur)
			cases.add(gauche);

		Case droite = getCase(x + 1, y);
		if (droite != null && droite.getProprietaire() == joueur)
			cases.add(droite);

		Case haut = getCase(x, y - 1);
		if (haut != null && haut.getProprietaire() == joueur)
			cases.add(haut);

		Case bas = getCase(x, y + 1);
		if (bas != null && bas.getProprietaire() == joueur)
			cases.add(bas);

		return cases;
	}

	/**
	 * Permet de récupérer les cases autour d'une autre case du plateau (diagonales
	 * inclues)
	 * 
	 * @param c
	 *            la case à tester
	 * @param joueur,
	 *            si précisé, ne retourne que les cases adjacentes appartenant à ce
	 *            joueur
	 * @return la liste des cases adjacentes à c
	 */
	public List<Case> getCasesAutour(Case c, Joueur joueur) {
		List<Case> cases = new ArrayList<>(4);
		int x = c.getX(), y = c.getY();

		cases.addAll(getCasesAdjacentes(c, joueur));

		Case gauchehaut = getCase(x - 1, y - 1);
		if (gauchehaut != null && gauchehaut.getProprietaire() == joueur)
			cases.add(gauchehaut);

		Case droitehaut = getCase(x + 1, y - 1);
		if (droitehaut != null && droitehaut.getProprietaire() == joueur)
			cases.add(droitehaut);

		Case gauchebas = getCase(x - 1, y + 1);
		if (gauchebas != null && gauchebas.getProprietaire() == joueur)
			cases.add(gauchebas);

		Case droitebas = getCase(x + 1, y + 1);
		if (droitebas != null && droitebas.getProprietaire() == joueur)
			cases.add(droitebas);

		return cases;
	}

	public List<Case> getCasesAdjacentes(Case c) {
		return getCasesAdjacentes(c, null);
	}

	public int getTaille() {
		return n;
	}

	public int getMax() {
		return max;
	}

	public List<Case> getCases() {
		return cases;
	}

	public Case getCase(int x, int y) {
		if (x < 0 || y < 0 || x >= n || y >= n)
			return null;
		return cases.get(y * n + x);
	}

	public Case getCase(int i) {
		if (i < 0 || i > n * n)
			return null;
		return cases.get(i);
	}

	public int getPlacees() {
		return placees;
	}
}
