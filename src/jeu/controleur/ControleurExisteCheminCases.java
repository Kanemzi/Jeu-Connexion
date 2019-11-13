package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.vue.modales.ModaleColorerCase;
import jeu.vue.modales.ModaleExisteCheminCases;
import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;

public class ControleurExisteCheminCases implements ActionListener {

	private ModaleExisteCheminCases parametres;
	private Partie partie;

	public ControleurExisteCheminCases(Partie partie) {
		this.partie = partie;
		parametres = new ModaleExisteCheminCases();
	}

	public void actionPerformed(ActionEvent ev) {
		parametres.afficher();
		if (!parametres.valide())
			return;

		// on appel ExisteCheminCases(case1, case2, proprietaire)
		// où les cases 1 et 2 sont demandées à l'utilisateur dans la fenetre modale
		// correspondante
		// le proprietaire est le Joueur avec la couleur soit rouge donc 0 soit bleu
		// donc 1
		
		Case c1 = partie.getPlateau().getCase(parametres.getX1(), parametres.getY1());
		Case c2 = partie.getPlateau().getCase(parametres.getX2(), parametres.getY2());
		
		if (partie.getPlateau().ExisteCheminCases(c1 ,c2 ,partie.getJoueurs()[parametres.getProprietaire()]) == null) {
			JOptionPane.showMessageDialog(null,
					"Il existe un chemin de la case(" + (parametres.getX1() + 1) + "," + (parametres.getY1() + 1)
							+ ") à la case(" + (parametres.getX2() + 1) + "," + (parametres.getY2() + 1)
							+ ") pour le joueur " + (parametres.getProprietaire() + 1) + "",
					"Information chemin", JOptionPane.INFORMATION_MESSAGE);
		} else {
			JOptionPane.showMessageDialog(null,
					"Il n'existe aucun chemin de la case(" + (parametres.getX1() + 1) + "," + (parametres.getY1() + 1)
							+ ") à la case(" + (parametres.getX2() + 1) + "," + (parametres.getY2() + 1)
							+ ") pour le joueur " + (parametres.getProprietaire() + 1) + "",
					"Information chemin erreur", JOptionPane.INFORMATION_MESSAGE);
		}
	}
}