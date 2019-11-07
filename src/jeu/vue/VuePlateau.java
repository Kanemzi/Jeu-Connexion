package jeu.vue;

import java.awt.GridLayout;

import javax.swing.JPanel;

import jeu.Config;
import jeu.controleur.ControleurSelectionCase;
import jeu.modele.Case;
import jeu.modele.Partie;

public class VuePlateau extends JPanel {
	
	private VueJeu parent;
	
	private BoutonCase[] cases;
	private GridLayout grille;
	private ControleurSelectionCase controleur;
	
	private int tailleBouton = 0;
	
	public VuePlateau(VueJeu parent, Partie partie) {
		this.parent = parent;
		init(partie);
	}
	
	public void init(Partie partie) {
		int n = partie.getPlateau().getTaille();

		cases = new BoutonCase[n * n];
		grille = new GridLayout(n, n);
		controleur = new ControleurSelectionCase(partie, parent);
		
		tailleBouton = Config.WINDOW_SIZE / n;
		setLayout(grille);
		
		for (int y = 0; y < n; y++) {
			for(int x = 0; x < n; x++) {
				Case c = partie.getPlateau().getCase(x, y);
				BoutonCase cb = new BoutonCase(c, n, tailleBouton);
				c.addObserver(cb);
				cb.addActionListener(controleur);
				cases[y * n + x] = cb;
				add(cb);
			}
		}
	}
	
	public int getTailleBouton() {
		return tailleBouton;
	}
}
