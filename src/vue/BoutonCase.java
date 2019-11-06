package vue;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.JButton;

public class BoutonCase extends JButton {
	
	private int caseX, caseY;
	
	public BoutonCase(int x, int y, String valeur) {
		super(valeur);
		this.caseX = x;
		this.caseY = y;
		
		setPreferredSize(new Dimension(64, 64));
		setBackground(Color.white);
	}
	
	public int getCaseX() {
		return caseX;
	}
	
	public int getCaseY() {
		return caseY;
	}
}
