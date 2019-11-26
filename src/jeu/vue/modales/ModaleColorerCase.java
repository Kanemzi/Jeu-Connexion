package jeu.vue.modales;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModaleColorerCase {
	
	private JPanel formulaire;
	private JTextField coordX;
	private JTextField coordY;
	private ButtonGroup proprietaire;
	private JRadioButton bleu;
	private JRadioButton rouge;
	
	private int resultat = JOptionPane.NO_OPTION;
	
	public ModaleColorerCase() {
		coordX = new JTextField("1");
		coordY = new JTextField("1");
		proprietaire = new ButtonGroup();
		rouge = new JRadioButton("rouge", true);
		bleu = new JRadioButton("bleu");
		proprietaire.add(bleu);
		proprietaire.add(rouge);
		formulaire = new JPanel();
		
		formulaire.setLayout(new GridLayout(4, 2));

		formulaire.add(new JLabel("coordX" + ":"));
		formulaire.add(coordX);
		
		formulaire.add(new JLabel("coordY" + ":"));
		formulaire.add(coordY);
		
		formulaire.add(rouge);
		formulaire.add(bleu);
		
	}
	public void afficher() {
		resultat = JOptionPane.showConfirmDialog(null, formulaire, "Colorer Case", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public boolean valide() {
		return resultat == JOptionPane.OK_OPTION;
	}
	
	public int getX() {
		int x = Integer.parseInt(coordX.getText());
		return x >= 1 ? x-1 : 0;
	}
	public int getY() {
		int y = Integer.parseInt(coordY.getText());
		return y >= 1 ? y-1 : 0;
	}
	public int getProprietaire() {
		return rouge.isSelected() ? 0 : 1;
	}
	
	
}


