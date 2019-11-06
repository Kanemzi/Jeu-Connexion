import java.awt.BorderLayout;

import javax.swing.JFrame;

import modele.Partie;
import vue.VueJeu;

public class JeuConnexion extends JFrame {
	
	Partie partie;
	VueJeu vue;
	
	public JeuConnexion() {
		partie = null;
		vue = null;
		setResizable(false);
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void nouvellePartie() {
		Partie partie = new Partie(7, 3, "Rouge", "Bleu");
		VueJeu vue = new VueJeu(partie);
		
		add(vue);
		pack();
		setVisible(true);
	}
	
	public static void main(String[] args) {
		new JeuConnexion().nouvellePartie();
	}
}
