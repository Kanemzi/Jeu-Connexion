package vue;

import java.awt.GridLayout;

import javax.swing.JPanel;

import controleur.ControleurSelectionCase;
import modele.Partie;
import modele.Plateau;

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
				 BoutonCase cb = new BoutonCase(x, y, Integer.toString(partie.getPlateau().getCase(x, y).getValeur()));
				 cb.addActionListener(controleur);
				 cases[y * n + x] = cb;
				 add(cb);
			}
		}
	}
}
