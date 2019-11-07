package jeu.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jeu.JeuConnexion;
import jeu.controleur.ControleurNouvellePartieJoueur;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuNouvellePartie;
	private JMenuItem optionNouvellePartieJoueur;
	
	private JMenu menuActions;
		
	public VueMenuActions(JeuConnexion jeu) {
		optionNouvellePartieJoueur = new JMenuItem("Joueur contre joueur");
		menuNouvellePartie = new JMenu("Nouvelle partie");
		menuNouvellePartie.add(optionNouvellePartieJoueur);
		
		menuActions = new JMenu("Actions");
		
		add(menuNouvellePartie);
		add(menuActions);
		
		
		optionNouvellePartieJoueur.addActionListener(new ControleurNouvellePartieJoueur(jeu));
	}
}
