package jeu.vue;

import java.awt.GridLayout;

import javax.swing.JPanel;

import jeu.controleur.ControleurSelectionCase;
import jeu.modele.Case;
import jeu.modele.Partie;

public class VuePlateau extends JPanel {
	
	BoutonCase[] cases;
	GridLayout grille;
	ControleurSelectionCase controleur;
	
	public VuePlateau(Partie partie) {
		init(partie);
	}
	
	public void init(Partie partie) {
		int n = partie.getPlateau().getTaille();
		
		cases = new BoutonCase[n * n];
		grille = new GridLayout(n, n);
		controleur = new ControleurSelectionCase(partie, (VuePlateau) getParent());
		
		setLayout(grille);
		
		for (int y = 0; y < n; y++) {
			for(int x = 0; x < n; x++) {
				Case c = partie.getPlateau().getCase(x, y);
				BoutonCase cb = new BoutonCase(c, n);
				c.addObserver(cb);
				cb.addActionListener(controleur);
				cases[y * n + x] = cb;
				add(cb);
			}
		}
	}
}
