package modele;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Plateau {
	
	private int n;
	private int max;
	private List<Case> cases;
	
	/**
	 * Générer un plateau aléatoire
	 * @param n la taille du plateau
	 * @param max la valeur maximale d'une cellule
	 */
	public Plateau RemplirGrilleAleatoire(int n, int max) {
		this.n = n;
		this.max = max;
		cases = new ArrayList<>(n * n);
		
		Random ran = new Random();
		for (int y = 0; y < n; y++) {
			for(int x = 0; x < n; x++) {
				cases.add(new Case(x, y, ran.nextInt(max) + 1));
			}
		}
		
		return this;
	}
	
	/**
	 * Génère un plateau à partir d'un fichier
	 * @param file le nom du fichier à charger
	 */
	public Plateau RemplirGrilleFichier(String nomFichier) {
		File fichier = new File(nomFichier);
		return this;
	}
	
	public int getTaille() {
		return n;
	}

	public List<Case> getCases() {
		return cases;
	}
	
	public Case getCase(int x, int y) {
		return cases.get(y * n + x);
	}
	
	public Case getCase(int i) {
		return cases.get(i);
	}
}
