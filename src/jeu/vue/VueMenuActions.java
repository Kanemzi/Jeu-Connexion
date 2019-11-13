package jeu.vue;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import jeu.JeuConnexion;
import jeu.controleur.ControleurChargerPartie;
import jeu.controleur.ControleurColorerCase;
import jeu.controleur.ControleurExisteCheminCases;
import jeu.controleur.ControleurNouvellePartie;
import jeu.controleur.ControleurRelierComposantes;
import jeu.controleur.ControleurSauvegarderPartie;
import jeu.modele.Partie;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuPartie;
	private JMenuItem optionNouvellePartieJoueur;
	private JMenuItem optionNouvellePartieOrdinateur;
	private JMenuItem optionSauvegarderPartie;
	private JMenuItem optionChargerPartie;
	
	private JMenu menuActions;
	private JMenuItem optionColorerCase;
	private JMenuItem optionExisteCheminCases;
	private JMenuItem optionRelierComposantes;
		
	public VueMenuActions(JeuConnexion jeu) {
		optionNouvellePartieJoueur = new JMenuItem("Joueur contre joueur");
		optionNouvellePartieJoueur.setMnemonic(KeyEvent.VK_J);
		
		optionNouvellePartieOrdinateur = new JMenuItem("Joueur contre ordinateur");
		optionNouvellePartieOrdinateur.setMnemonic(KeyEvent.VK_O);
		
		optionSauvegarderPartie = new JMenuItem("Sauvegarder la partie");
		optionChargerPartie = new JMenuItem("Charger une partie");
		
		menuPartie = new JMenu("Partie");
		menuPartie.setMnemonic(KeyEvent.VK_P);
		
		menuPartie.add(optionNouvellePartieJoueur);
		menuPartie.add(optionNouvellePartieOrdinateur);
		menuPartie.add(optionSauvegarderPartie);
		menuPartie.add(optionChargerPartie);
		optionSauvegarderPartie.setEnabled(false);
		
		optionColorerCase = new JMenuItem("ColorerCase");
		optionExisteCheminCases = new JMenuItem("ExisteCheminCases");
		optionRelierComposantes = new JMenuItem("RelierComposante");
		menuActions = new JMenu("Actions");
		
		menuActions.add(optionColorerCase);
		menuActions.add(optionExisteCheminCases);
		menuActions.add(optionRelierComposantes);
		
		add(menuPartie);
		add(menuActions);

		optionNouvellePartieJoueur.addActionListener(new ControleurNouvellePartie(jeu, false));
		optionNouvellePartieOrdinateur.addActionListener(new ControleurNouvellePartie(jeu, true));
		optionChargerPartie.addActionListener(new ControleurChargerPartie(jeu, false));
	}
	
	public void enregistrerControleurs(Partie partie) {
		optionSauvegarderPartie.setEnabled(true);
		
		optionColorerCase.addActionListener(new ControleurColorerCase(partie));
		optionSauvegarderPartie.addActionListener(new ControleurSauvegarderPartie(partie));
		optionExisteCheminCases.addActionListener(new ControleurExisteCheminCases(partie));
		optionRelierComposantes.addActionListener(new ControleurRelierComposantes(partie));
		
	}
}
