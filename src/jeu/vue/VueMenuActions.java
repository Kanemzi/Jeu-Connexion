package jeu.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jeu.JeuConnexion;
import jeu.controleur.ControleurColorerCase;
import jeu.controleur.ControleurNouvellePartie;
import jeu.controleur.ControleurSauvegarderPartie;
import jeu.modele.Partie;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuPartie;
	private JMenuItem optionNouvellePartieJoueur;
	private JMenuItem optionNouvellePartieOrdinateur;
	private JMenuItem optionSauvegarderPartie;
	
	private JMenu menuActions;
	private JMenuItem optionColorerCase;
	private JMenuItem optionExisteCheminCases;
	private JMenuItem optionRelierComposante;
		
	public VueMenuActions(JeuConnexion jeu) {
		optionNouvellePartieJoueur = new JMenuItem("Joueur contre joueur");
		optionNouvellePartieOrdinateur = new JMenuItem("Joueur contre ordinateur");
		optionSauvegarderPartie = new JMenuItem("Sauvegarder la partie");
		menuPartie = new JMenu("Partie");
		
		menuPartie.add(optionNouvellePartieJoueur);
		menuPartie.add(optionNouvellePartieOrdinateur);
		menuPartie.add(optionSauvegarderPartie);
		optionSauvegarderPartie.setEnabled(false);
		
		optionColorerCase = new JMenuItem("ColorerCase");
		optionExisteCheminCases = new JMenuItem("ExisteCheminCases");
		optionRelierComposante = new JMenuItem("RelierComposante");
		menuActions = new JMenu("Actions");
		
		menuActions.add(optionColorerCase);
		menuActions.add(optionExisteCheminCases);
		menuActions.add(optionRelierComposante);
		
		add(menuPartie);
		add(menuActions);
		
		optionNouvellePartieJoueur.addActionListener(new ControleurNouvellePartie(jeu, false));
		optionNouvellePartieOrdinateur.addActionListener(new ControleurNouvellePartie(jeu, true));		
	}
	
	public void enregistrerControleurs(Partie partie) {
		optionSauvegarderPartie.setEnabled(true);
		
		optionColorerCase.addActionListener(new ControleurColorerCase(partie));
		optionSauvegarderPartie.addActionListener(new ControleurSauvegarderPartie(partie));
	}
}
