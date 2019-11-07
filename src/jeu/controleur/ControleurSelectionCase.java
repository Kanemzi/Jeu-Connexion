package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.vue.BoutonCase;
import jeu.vue.VueJeu;

/**
 * Contôleur s'éxécutant lors de la sélection d'une case par un joueur
 */
public class ControleurSelectionCase implements ActionListener {

	private Partie modele;
	private VueJeu vue;

	public ControleurSelectionCase(Partie modele, VueJeu vue) {
		this.modele = modele;
		this.vue = vue;
	}

	public void actionPerformed(ActionEvent ev) {
		BoutonCase selection = (BoutonCase) ev.getSource();
		Case caseCliquee = modele.getPlateau().getCase(selection.getCaseX(), selection.getCaseY());
		
		if (caseCliquee.getProprietaire() != null) {
			vue.getOverlay().setCasesSurbrillances(
					modele.getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));
			JOptionPane.showMessageDialog(null,
					"Le score de la composante sélectionnée est de "
							+ modele.getPlateau().AfficherScore(caseCliquee.getX(), caseCliquee.getY()),
					"Information Score", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		Joueur joueurTour = modele.getJoueurTour();
		// System.out.println("va relier :" +
		// modele.getPlateau().RelierComposantes(caseCliquee.getX(), caseCliquee.getY(),
		// joueurTour, false));

		modele.getPlateau().ColorerCase(caseCliquee.getX(), caseCliquee.getY(), joueurTour);

		// System.out.println("link :" + (modele.getPlateau().ExisteCheminCases(
		// modele.getPlateau().getCase(0, 2), modele.getPlateau().getCase(2, 0),
		// modele.getJoueurs()[1]) == null));

		// debug all cells
		System.out.println("______________________________________________________");
		for (Case c : modele.getPlateau().getCases()) {
			System.out.println(c + " ##### parent: " + c.getParent() + " ##### enfants: " + c.getEnfants());
		}

		vue.getOverlay()
				.setCasesSurbrillances(modele.getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));

		System.out.println("_ _ _ _ _ ");
		System.out.println(
				"composante : " + modele.getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));
		System.out.println("score : " + modele.getPlateau().AfficherScore(caseCliquee.getX(), caseCliquee.getY()));

		vue.getOverlay().setDernierCoup(caseCliquee);
		
		if (modele.terminee()) {
			JOptionPane.showMessageDialog(null, "Partie terminée", "Partie terminée", JOptionPane.INFORMATION_MESSAGE);
		} else {
			modele.addTour();			
		}
		
		vue.getInformations().mettreAJourCompteur(modele.getTour(), modele.getMaxTour(), modele.getJoueurTour());
	}
}
