package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

import jeu.JeuConnexion;
import jeu.vue.modales.ModaleNouvellePartie;

public class ControleurNouvellePartie implements ActionListener {

	private JeuConnexion jeu;
	private boolean ordinateur;
	private ModaleNouvellePartie parametres;
	
	public ControleurNouvellePartie(JeuConnexion jeu, boolean ordinateur) {
		this.jeu = jeu;
		this.ordinateur = ordinateur;
		parametres = new ModaleNouvellePartie(ordinateur);
	}
	
	public void actionPerformed(ActionEvent ev) {
		parametres.afficher();
		if (!parametres.valide()) return;
		
		jeu.JouerDeuxHumains(parametres.getNomJoueur1(), parametres.getNomJoueur2(), parametres.getTaillePlateau(), parametres.getValeurMax());
	}
}
