package jeu.vue.modales;

import java.awt.GridLayout;

import javax.swing.ButtonGroup;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

public class ModaleRelierComposantes {

	private JPanel formulaire;
	private JTextField coordX1;
	private JTextField coordY1;
	private JRadioButton bleu;
	private JRadioButton rouge;
	private ButtonGroup proprietaire;
	
	private int resultat = JOptionPane.NO_OPTION;
	
	public ModaleRelierComposantes(){
		coordX1 = new JTextField();
		coordY1 = new JTextField();
	
		rouge = new JRadioButton("rouge", true);
		bleu = new JRadioButton("bleu");
				
		proprietaire = new ButtonGroup();
		
		formulaire = new JPanel();
		
		formulaire.setLayout(new GridLayout(5, 2));
		
		proprietaire.add(rouge);
		proprietaire.add(bleu);

		formulaire.add(rouge);
		formulaire.add(bleu);
		
		formulaire.add(new JLabel("coordX1" + ":"));
		formulaire.add(coordX1);
		
		formulaire.add(new JLabel("coordY1" + ":"));
		formulaire.add(coordY1);
	}
	
	public void afficher() {
		resultat = JOptionPane.showConfirmDialog(null, formulaire, "Relier Composantes", JOptionPane.OK_CANCEL_OPTION);
	}
	
	public boolean valide() {
		return resultat == JOptionPane.OK_OPTION;
	}
	
	public int getX1() {
		int x = Integer.parseInt(coordX1.getText());
		return x >= 1 ? x-1 : 0;
	}
	public int getY1() {
		int y = Integer.parseInt(coordY1.getText());
		return y >= 1 ? y-1 : 0;
	}
	public int getProprietaire() {
		return rouge.isSelected() ? 0 : 1;
	}
}
