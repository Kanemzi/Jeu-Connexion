package jeu.vue.modales;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModaleChargerPartie {
	
	private JPanel formulaire;
	private JTextField champJoueur1;
	private JTextField champJoueur2;
	private JTextField nomPartie;
	private boolean ordinateur;
	private int resultat = JOptionPane.NO_OPTION;
	private JRadioButton bleu;
	private JRadioButton rouge;
	private ButtonGroup proprietaire;
	
	public ModaleChargerPartie(boolean ordi) {
		
		ordinateur = ordi;
		
		nomPartie = new JTextField();
		champJoueur1 = new JTextField("Joueur" + (ordinateur ? "" : " 1"));
		champJoueur2 = new JTextField(ordinateur ? "Bip Boop" : "Joueur 2");
		rouge = new JRadioButton("rouge", true);
		bleu = new JRadioButton("bleu");
				
		proprietaire = new ButtonGroup();
		formulaire = new JPanel();
		
		formulaire.setLayout(new GridLayout(5, 2));
		
		//formulaire.add(new JLabel("Qui commence ?"));
		
	
		proprietaire.add(rouge);
		proprietaire.add(bleu);
		
		formulaire.add(rouge);
		formulaire.add(bleu);
		
		formulaire.add(new JLabel ("Nom partie :"));
		formulaire.add(nomPartie);
		
		formulaire.add(new JLabel(ordi ? "Nom joueur" : "Nom joueur 1" + ":"));
		formulaire.add(champJoueur1);
		
		formulaire.add(new JLabel(ordi ? "Nom ordinateur" : "Nom joueur 2" + ":"));
		formulaire.add(champJoueur2);
		
		/*if (ordinateur) {
			ordinateurCommence = new JCheckBox("L'Ordinateur commence");
			formulaire.add(ordinateurCommence);
			formulaire.add(new JLabel(""));
		}*/
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
	
	public String getNomPartie() {
		String partie = nomPartie.getText();
		return partie;
	}
	public boolean isOrdinateurCommence() {
		return ordinateur && bleu.isSelected();
	}
	public boolean isJoueurCommence() {
		return bleu.isSelected();
	}
}
