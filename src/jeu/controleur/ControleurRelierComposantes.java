package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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

		
		Case c1 = partie.getPlateau().getCase(parametres.getX1(), parametres.getY1());
		Case c2 = partie.getPlateau().getCase(parametres.getX2(), parametres.getY2());
		Joueur joueur = partie.getJoueurs()[parametres.getProprietaire()];

		if(c1.getProprietaire() == c2.getProprietaire() && c1.getProprietaire().getId() == parametres.getProprietaire()) {
				
			Case[] representants = partie.getPlateau().ExisteCheminCases(c1, c2, joueur);
			boolean liees = (representants == null);
			
			if (!liees) {
				representants[0].union(representants[1]);
			}
		}
	}
}
