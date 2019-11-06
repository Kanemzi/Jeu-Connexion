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
		menu = new VueMenuActions();
		
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menu);
	}
	
	public void nouvellePartie() {
		Partie p = new Partie("res/plateaux/exemple.plat", "Rouge", "Bleu");
		Partie partie = new Partie(4, 3, "Rouge", "Bleu");
		VueJeu vue = new VueJeu(partie);
		
		add(vue);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new JeuConnexion().nouvellePartie();
	}
}
