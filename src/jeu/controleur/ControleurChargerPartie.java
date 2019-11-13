package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import jeu.JeuConnexion;
import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.vue.modales.ModaleChargerPartie;
import jeu.vue.modales.ModaleNouvellePartie;

public class ControleurChargerPartie  implements ActionListener {

	private JeuConnexion jeu;
	private boolean ordinateur;
	private ModaleChargerPartie parametres;
	
	public ControleurChargerPartie(JeuConnexion jeu, boolean ordinateur) {
		this.jeu = jeu;
		this.ordinateur = ordinateur;
		parametres = new ModaleChargerPartie(ordinateur);
	}

	public void actionPerformed(ActionEvent ev) {
		parametres.afficher();
		if (!parametres.valide()) return;
		
		if(parametres.getNomPartie() != null) {
			if (ordinateur) {
				Partie p = jeu.chargerOrdiHumain(parametres.getNomJoueur1(), parametres.getNomJoueur2(), parametres.getNomPartie(), parametres.isOrdinateurCommence());
				if (parametres.isOrdinateurCommence()) {
					p.jouerTour(null, jeu.getVue());
				}
			} else 
				jeu.chargerDeuxHumains(parametres.getNomJoueur1(), parametres.getNomJoueur2(), parametres.getNomPartie());
		}                                                                                                                                                                
		
		
	}
}
