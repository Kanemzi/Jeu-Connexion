package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.JeuConnexion;
import jeu.vue.modales.ModaleNouvellePartie;

public class ControleurNouvellePartieJoueur implements ActionListener {

	JeuConnexion jeu;
	
	public ControleurNouvellePartieJoueur(JeuConnexion jeu) {
		this.jeu = jeu;
	}
	
	public void actionPerformed(ActionEvent ev) {
		ModaleNouvellePartie parametres = new ModaleNouvellePartie(false);
		parametres.afficher();
		if (!parametres.valide()) return;
		
		
		jeu.JouerDeuxHumains(parametres.getNomJoueur1(), parametres.getNomJoueur2(), parametres.getTaillePlateau(), parametres.getValeurMax());
	}

}
