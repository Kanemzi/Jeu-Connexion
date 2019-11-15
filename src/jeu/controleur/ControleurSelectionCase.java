package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import jeu.Config;
import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent;
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

		/**
		 * Clic sur une case déjà occupée -> on met en surbrillance le groupe sur lequel
		 * le joueur a cliqué et on affiche le score du groupe
		 */
		if (caseCliquee.getProprietaire() != null) {
			vue.getOverlay().setCasesSurbrillances(
					modele.getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));

			JOptionPane.showMessageDialog(null,
					"Le score de la composante sélectionnée est de "
							+ modele.getPlateau().AfficherScore(caseCliquee.getX(), caseCliquee.getY()),
					"Information Score", JOptionPane.INFORMATION_MESSAGE);
			return;
		}

		/**
		 * On ne laisse pas le jouer cliquer sur une case vide si c'est au tour du bot
		 */
		Joueur joueurTour = modele.getJoueurTour();
		if (joueurTour.isOrdinateur()) {
			JOptionPane.showMessageDialog(null,
					"L'ordinateur est en train de réfléchir...\nVeuillez attendre avant de jouer", "Connexion",
					JOptionPane.ERROR_MESSAGE);

			return;
		}
		
		modele.jouerTour(caseCliquee, vue);
	}
}
