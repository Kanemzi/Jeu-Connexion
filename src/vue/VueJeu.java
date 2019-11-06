package vue;

import java.awt.BorderLayout;

import javax.swing.JPanel;

import modele.Partie;

public class VueJeu extends JPanel {
	
	private VueInformations informations;
	private VuePlateau plateau;
	
	public VueJeu(Partie partie) {
		setLayout(new BorderLayout());
		
		// création des sous vues
		informations = new VueInformations();
		plateau = new VuePlateau(partie);
		
		add(informations, BorderLayout.NORTH);
		add(plateau, BorderLayout.CENTER);
	}
}
