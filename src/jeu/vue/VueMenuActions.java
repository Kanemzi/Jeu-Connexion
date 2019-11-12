package jeu.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jeu.JeuConnexion;
import jeu.controleur.ControleurColorerCase;
import jeu.controleur.ControleurExisteCheminCases;
import jeu.controleur.ControleurNouvellePartie;
import jeu.modele.Partie;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuNouvellePartie;
	private JMenuItem optionNouvellePartieJoueur;
	private JMenuItem optionNouvellePartieOrdinateur;
	
	private JMenu menuActions;
	private JMenuItem optionColorerCase;
	private JMenuItem optionExisteCheminCases;
	private JMenuItem optionRelierComposante;
		
	public VueMenuActions(JeuConnexion jeu) {
		optionNouvellePartieJoueur = new JMenuItem("Joueur contre joueur");
		optionNouvellePartieOrdinateur = new JMenuItem("Joueur contre ordinateur");
		menuNouvellePartie = new JMenu("Nouvelle partie");
		
		menuNouvellePartie.add(optionNouvellePartieJoueur);
		menuNouvellePartie.add(optionNouvellePartieOrdinateur);
		
		optionColorerCase = new JMenuItem("ColorerCase");
		optionExisteCheminCases = new JMenuItem("ExisteCheminCases");
		optionRelierComposante = new JMenuItem("RelierComposante");
		menuActions = new JMenu("Actions");
		
		menuActions.add(optionColorerCase);
		menuActions.add(optionExisteCheminCases);
		menuActions.add(optionRelierComposante);
		
		add(menuNouvellePartie);
		add(menuActions);
	
		
		optionNouvellePartieJoueur.addActionListener(new ControleurNouvellePartie(jeu, false));
		optionNouvellePartieOrdinateur.addActionListener(new ControleurNouvellePartie(jeu, true));
		
		
		
		
	}
	
	public void enregistrerControleurs(Partie partie) {
		optionColorerCase.addActionListener(new ControleurColorerCase(partie));
		
		optionExisteCheminCases.addActionListener(new ControleurExisteCheminCases(partie));
	}
}
