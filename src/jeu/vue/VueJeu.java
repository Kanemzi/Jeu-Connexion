package jeu.vue;

import java.awt.BorderLayout;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import jeu.modele.Partie;

public class VueJeu extends JPanel {
	
	private VueInformations informations;
	private VuePlateau plateau;
	private JMenuBar barreMenu;
	private JMenu menuActions;
	
	public VueJeu(Partie partie) {
		setLayout(new BorderLayout());
		
		// création des sous vues
		informations = new VueInformations();
		plateau = new VuePlateau(partie);
		
		add(informations, BorderLayout.NORTH);
		add(plateau, BorderLayout.CENTER);
		
	}
}
