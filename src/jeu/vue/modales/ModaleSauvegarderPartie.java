package jeu.vue.modales;

import java.awt.GridLayout;
import java.util.Date;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import jeu.modele.Joueur;
import jeu.modele.Partie;

public class ModaleSauvegarderPartie {
	
	private JPanel formulaire;
	private JTextField nomFichier;
	
	private int resultat = JOptionPane.NO_OPTION;
	
	public ModaleSauvegarderPartie() {		
		nomFichier = new JTextField("");
		
		formulaire = new JPanel();
		
		formulaire.setLayout(new GridLayout(3, 2));

		formulaire.add(new JLabel("Nom de la partie :"));
		formulaire.add(nomFichier);
	}
	
	public void afficher(Partie partie) {

		Joueur[] joueurs = partie.getJoueurs();
		
		String nomAutomatique = joueurs[0].getNom() + "-" + joueurs[1].getNom() + "-" + new Date().getTime() + ".cnx";
		nomFichier.setText(nomAutomatique);
		
		resultat = JOptionPane.showConfirmDialog(null, formulaire, "Sauvegarder la partie", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public boolean valide() {
		return resultat == JOptionPane.OK_OPTION;
	}
	
	public String getNomFichier() {
		return nomFichier.getText();
	}
}
