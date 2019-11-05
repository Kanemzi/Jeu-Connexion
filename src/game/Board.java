package game;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private List<Cell> cells;
	/**
	 * Générer un plateau aléatoire
	 * @param w la largeur du plateau à générer
	 * @param h la hauteur du plateau à générer
	 */
	public Board(int w, int h) {
		cells = new ArrayList<>(w * h);
	}
	
	/**
	 * Génère un plateau à partir d'un fichier
	 * @param file le nom du fichier à charger
	 */
	public Board(String filename) {
		File file = new File(filename);
	}
}
