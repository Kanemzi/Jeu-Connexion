package jeu.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import jeu.modele.Joueur;
import jeu.modele.Partie;

public class VueInformations extends JPanel {
	
	private static Font font = new Font("sans-serif", Font.PLAIN, 20);	
	
	private JPanel encartJoueur1, encartJoueur2, encartTour;
	private JLabel compteurTours;
	private VueJeu parent;
	
	public VueInformations(VueJeu parent, Partie partie) {
		this.parent = parent;
		Dimension size = getPreferredSize();
		size.height = 96;
		setPreferredSize(size);
		setBorder(BorderFactory.createMatteBorder(0, 0, 4, 0, Color.gray));

		setLayout(new GridLayout(1, 3));
		
		encartJoueur1 = new JPanel();
		remplirEncartJoueur(encartJoueur1, partie.getJoueurs()[0]);
		
		encartJoueur2 = new JPanel();
		remplirEncartJoueur(encartJoueur2, partie.getJoueurs()[1]);
		
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
		
		add(encartJoueur1);
		add(encartTour);
		add(encartJoueur2);
	}
	
	public void remplirEncartJoueur(JPanel encart, Joueur joueur) {
		JLabel labelNom = new JLabel(joueur.getNom());
		labelNom.setForeground(joueur.getCouleur());
		labelNom.setFont(font);
		encart.add(labelNom);
	}
	
	public void mettreAJourCompteur(int tour, int maxTour, Joueur joueur) {
		compteurTours.setText((tour+1) + " / " + maxTour);
		encartTour.setBorder(BorderFactory.createLineBorder(joueur.getCouleur(), 3));
	}
}
