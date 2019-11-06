package jeu.vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Insets;
import java.util.Observable;
import java.util.Observer;

import javax.swing.JButton;

import jeu.Config;
import jeu.modele.Case;
import jeu.modele.Joueur;

public class BoutonCase extends JButton implements Observer {
	
	private static Font font = new Font("sans-serif", Font.PLAIN, 32);	
			
	private int caseX, caseY;
	
	public BoutonCase(Case c, int n) {
		super(Integer.toString(c.getValeur()));
		
		caseX = c.getX();
		caseY = c.getY();
		
		int taille = Config.WINDOW_SIZE / n;
		setPreferredSize(new Dimension(taille, taille));
		setBackground(Color.white);
		setFocusPainted(false);
		setMargin(new Insets(0, 0, 0, 0));
		setFont(font.deriveFont((float) (taille / 2)));
		setToolTipText( (caseX + 1) + ", " + (caseY + 1));
		
		update(null, c.getProprietaire());
	}
	
	public int getCaseX() {
		return caseX;
	}
	
	public int getCaseY() {
		return caseY;
	}

	@Override
	public void update(Observable o, Object value) {
		if (!(value instanceof Joueur)) return; 
		
		Joueur proprietaire = (Joueur) value;
		
		setBackground(proprietaire.getCouleur());
		setForeground(Color.white);	
	}
}
