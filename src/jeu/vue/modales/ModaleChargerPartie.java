package jeu.vue.modales;

import java.awt.GridLayout;

import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class ModaleChargerPartie {
	
	private JPanel formulaire;
	private JTextField champJoueur1;
	private JTextField champJoueur2;
	private JCheckBox ordinateurCommence;
	private boolean ordinateur;
	private int resultat = JOptionPane.NO_OPTION;
	
	public ModaleChargerPartie(boolean ordi) {
		ordinateur = ordi;
		champJoueur1 = new JTextField("Joueur" + (ordinateur ? "" : " 1"));
		champJoueur2 = new JTextField(ordinateur ? "Bip Boop" : "Joueur 2");
		formulaire = new JPanel();
		
		formulaire.setLayout(new GridLayout(5, 2));

		formulaire.add(new JLabel(ordi ? "Nom joueur" : "Nom joueur 1" + ":"));
		formulaire.add(champJoueur1);
		
		formulaire.add(new JLabel(ordi ? "Nom ordinateur" : "Nom joueur 2" + ":"));
		formulaire.add(champJoueur2);
		
		if (ordinateur) {
			ordinateurCommence = new JCheckBox("L'Ordinateur commence");
			formulaire.add(ordinateurCommence);
			formulaire.add(new JLabel(""));
		}
	}
	
	public void afficher() {
		resultat = JOptionPane.showConfirmDialog(null, formulaire, "Paramètres de la partie", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public boolean valide() {
		return resultat == JOptionPane.OK_OPTION;
	}
	
	public String getNomJoueur1() {
		String nom = champJoueur1.getText();
		return nom.isEmpty() ? "Joueur" + (ordinateur ? "" : " 1") : nom;
	}
	
	public String getNomJoueur2() {
		String nom = champJoueur2.getText();
		return nom.isEmpty() ? (ordinateur ? "Bip Boop" : "Joueur 2") : nom;
	}
	
	public boolean isOrdinateurCommence() {
		return ordinateur && ordinateurCommence.isSelected();
	}
}
