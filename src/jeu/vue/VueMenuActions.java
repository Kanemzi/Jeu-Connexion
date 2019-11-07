package jeu.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jeu.JeuConnexion;
import jeu.controleur.ControleurNouvellePartie;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuNouvellePartie;
	private JMenuItem optionNouvellePartieJoueur;
	private JMenuItem optionNouvellePartieOrdinateur;
	
	private JMenu menuActions;
		
	public VueMenuActions(JeuConnexion jeu) {
		optionNouvellePartieJoueur = new JMenuItem("Joueur contre joueur");
		optionNouvellePartieOrdinateur = new JMenuItem("Joueur contre ordinateur");
		menuNouvellePartie = new JMenu("Nouvelle partie");
		
		menuNouvellePartie.add(optionNouvellePartieJoueur);
		menuNouvellePartie.add(optionNouvellePartieOrdinateur);
		
		menuActions = new JMenu("Actions");
		
		add(menuNouvellePartie);
		add(menuActions);
		
		
		optionNouvellePartieJoueur.addActionListener(new ControleurNouvellePartie(jeu, false));
		optionNouvellePartieOrdinateur.addActionListener(new ControleurNouvellePartie(jeu, true));
	}
}
