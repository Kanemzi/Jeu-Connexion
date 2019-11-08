package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.vue.modales.ModaleColorerCase;
import jeu.modele.Partie;

public class ControleurColorerCase  implements ActionListener {
	
	private ModaleColorerCase parametres;
	private Partie partie;
	
	public ControleurColorerCase(Partie partie) {
		this.partie = partie;
		parametres = new ModaleColorerCase();
	}

	public void actionPerformed(ActionEvent ev) {
		parametres.afficher();
		if (!parametres.valide()) return;
		
		if(partie.getPlateau().getCase(parametres.getX(), parametres.getY()).getProprietaire() != null) {
			return;
		}
		partie.getPlateau().ColorerCase(parametres.getX(), parametres.getY(), partie.getJoueurs()[parametres.getProprietaire()]);
		
	}
	
}

