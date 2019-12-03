package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.vue.modales.ModaleRelierComposantes;

public class ControleurRelierComposantes  implements ActionListener {

	private ModaleRelierComposantes parametres;
	private Partie partie;
	
	public ControleurRelierComposantes(Partie partie) {
		this.partie = partie;
		parametres = new ModaleRelierComposantes();
	}

	public void actionPerformed(ActionEvent ev) {
		parametres.afficher();
		if (!parametres.valide()) return;

		Joueur j = partie.getJoueurs()[parametres.getProprietaire()];
		Case c = partie.getPlateau().getCase(parametres.getX1(), parametres.getY1());

	
		boolean relier = partie.getPlateau().RelierComposantes(parametres.getX1(), parametres.getY1(), j, false);
		JOptionPane.showMessageDialog(null,
				(relier) ? "le coup relie plusieurs composantes" : "le coup ne relie pas de composantes",
				"Relier composantes", JOptionPane.PLAIN_MESSAGE);
	}
}
