package jeu.modele.analyse;

public class PoidsPlateau {
	public int poids[][];
	public int poidsMax;
	public int xmax, ymax;
	
	public PoidsPlateau(int taille) {
		poids = new int[taille][taille];
		poidsMax = -1;
	}
}
