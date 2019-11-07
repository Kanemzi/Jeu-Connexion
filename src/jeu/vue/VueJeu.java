package jeu.vue;

import java.awt.BorderLayout;

import javax.swing.JLayer;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JPanel;

import jeu.modele.Partie;
import jeu.vue.overlay.OverlayPlateau;

public class VueJeu extends JPanel {
	
	private VueInformations informations;
	private VuePlateau plateau;
	
	private OverlayPlateau overlay;
	
	private JMenuBar barreMenu;
	private JMenu menuActions;
	
	public VueJeu(Partie partie) {
		setLayout(new BorderLayout());
		
		// création des sous vues
		informations = new VueInformations();
		plateau = new VuePlateau(this, partie);
		overlay = new OverlayPlateau(plateau);
		
		add(informations, BorderLayout.NORTH);
		
		JLayer<JPanel> layer = new JLayer<>(plateau, overlay);
		add(layer, BorderLayout.CENTER);
	}
	
	public OverlayPlateau getOverlay() {
		return overlay;
	}
}
