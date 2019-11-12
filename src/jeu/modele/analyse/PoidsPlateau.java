package jeu.modele.analyse;

import java.util.Vector;

public class PoidsPlateau {
	public int poids[][];
	public int poidsMax;
	public int xmax, ymax;
	
	public PoidsPlateau(int taille) {
		poids = new int[taille][taille];
	}
}
