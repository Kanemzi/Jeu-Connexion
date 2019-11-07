package jeu;

import java.awt.BorderLayout;

import javax.swing.JFrame;

import jeu.modele.Partie;
import jeu.vue.VueJeu;
import jeu.vue.VueMenuActions;

public class JeuConnexion extends JFrame {
	
	Partie partie;
	VueJeu vue;
	VueMenuActions menu;
	
	public JeuConnexion() {
		partie = null;
		vue = null;
		menu = new VueMenuActions(this);
		
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menu);
	}
	
	public void JouerDeuxHumains(String nomJoueur1, String nomJoueur2, int taille, int max) {
		if (vue != null) remove(vue);

		partie = new Partie(taille, max, nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		add(vue);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new JeuConnexion().JouerDeuxHumains("Roger", "Michel", 6, 4);
	}
}
