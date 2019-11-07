package jeu.controleur;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.vue.BoutonCase;
import jeu.vue.VuePlateau;

/**
 * Contôleur s'éxécutant lors de la sélection d'une case par un joueur
 */
public class ControleurSelectionCase implements ActionListener {

	private Partie modele;
	private VuePlateau vue;
	
	public ControleurSelectionCase(Partie modele, VuePlateau vue) {
		this.modele = modele;
		this.vue = vue;
	}
	
	@Override
	public void actionPerformed(ActionEvent ev) {
		BoutonCase selection = (BoutonCase) ev.getSource();
		Case caseCliquee = modele.getPlateau().getCase(selection.getCaseX(), selection.getCaseY());
		
		if (caseCliquee.getProprietaire() != null) return;
		
		
		Joueur joueurTour = modele.getJoueurTour();
		System.out.println("va relier :" + modele.getPlateau().RelierComposantes(caseCliquee.getX(), caseCliquee.getY(), joueurTour, false));

		modele.getPlateau().ColorerCase(caseCliquee.getX(), caseCliquee.getY(), joueurTour);
		
		//System.out.println("link :" + (modele.getPlateau().ExisteCheminCases( modele.getPlateau().getCase(0, 2),  modele.getPlateau().getCase(2, 0), modele.getJoueurs()[1]) == null));
		
		// debug all cells
		for (Case c : modele.getPlateau().getCases()) {
			System.out.println(c + " parent: " + c.getParent());
		}
		
		modele.addTour();
	}
}
