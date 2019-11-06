package controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import modele.Case;
import modele.Joueur;
import modele.Partie;
import vue.BoutonCase;
import vue.VuePlateau;

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
		
		Joueur tour = modele.getJoueurTour();
		caseCliquee.setProprietaire(tour);
		
		selection.setBackground(tour.getCouleur());
		
		modele.addTour();
	}
}
