package jeu.vue;

import java.awt.Dimension;

import javax.swing.JPanel;

public class VueInformations extends JPanel {
	
	public VueInformations() {
		Dimension size = getPreferredSize();
		size.height = 64;
		setPreferredSize(size);
	}
}
