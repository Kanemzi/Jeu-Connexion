package jeu.modele.analyse;

public class PoidsPlateau {
	public float poids[][];
	public float poidsMax;
	public int xmax, ymax;
	
	public PoidsPlateau(int taille) {
		poids = new float[taille][taille];
		poidsMax = -1;
	}
}
