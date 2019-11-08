package jeu.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jeu.modele.Joueur;
import jeu.modele.Partie;

public class VueInformations extends JPanel {
	
	private static Font font = new Font("sans-serif", Font.PLAIN, 20);	
	
	private Map<Joueur, JPanel> encartsJoueurs;
	private JPanel encartTour;
	private JLabel compteurTours;
	private VueJeu parent;
	
	public VueInformations(VueJeu parent, Partie partie) {
		this.parent = parent;
		Dimension size = getPreferredSize();
		size.height = 96;
		encartsJoueurs = new HashMap<>();
		setPreferredSize(size);
		setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.gray));

		setLayout(new GridLayout(1, 3));
		
		encartsJoueurs.put(partie.getJoueurs()[0], new JPanel()); 
		remplirEncartJoueur(partie.getJoueurs()[0]);
		
		encartsJoueurs.put(partie.getJoueurs()[1], new JPanel()); 
		remplirEncartJoueur(partie.getJoueurs()[1]);
		
		encartTour = new JPanel();
		encartTour.setLayout(new GridLayout(2, 1));
		encartTour.setBackground(Color.white);
		compteurTours = new JLabel();
		mettreAJourCompteur(partie.getTour(), partie.getMaxTour(), partie.getJoueurTour());
		compteurTours.setFont(font.deriveFont(30.0f));
		JLabel tourLabel = new JLabel("Tour :");
		tourLabel.setHorizontalAlignment(JLabel.CENTER);
		compteurTours.setHorizontalAlignment(JLabel.CENTER);
		encartTour.add(tourLabel);
		encartTour.add(compteurTours);
		
		add(encartsJoueurs.get(partie.getJoueurs()[0]));
		add(encartTour);
		add(encartsJoueurs.get(partie.getJoueurs()[1]));
	}
	
	public void remplirEncartJoueur(Joueur joueur) {
		JLabel labelNom = new JLabel(joueur.getNom());
		labelNom.setForeground(joueur.getCouleur());
		labelNom.setFont(font);
		encartsJoueurs.get(joueur).add(labelNom);
	}
	
	public void mettreAJourCompteur(int tour, int maxTour, Joueur joueur) {
		compteurTours.setText((tour+1) + " / " + maxTour);
		encartTour.setBorder(BorderFactory.createLineBorder(joueur.getCouleur(), 3));
	}
	
	public void mettreAJourVainqueur(Joueur vainqueur) {
		encartsJoueurs.get(vainqueur).setBackground(Color.yellow);
	}
}
