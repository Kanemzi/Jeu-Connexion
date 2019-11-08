package jeu;

import java.awt.Dimension;

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
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menu);
		
		setSize(new Dimension(Config.WINDOW_SIZE, Config.WINDOW_SIZE));
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public void JouerDeuxHumains(String nomJoueur1, String nomJoueur2, int taille, int max) {
		if (vue != null) remove(vue);

		partie = new Partie(taille, max, nomJoueur1, nomJoueur2);
		// partie = new Partie("res/plateaux/exemple.plat", nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		add(vue);
		pack();
	}
	
	public static void main(String[] args) {
		new JeuConnexion();
	}
}
