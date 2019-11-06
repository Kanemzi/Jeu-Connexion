package jeu.vue;

import javax.swing.JMenu;
import javax.swing.JMenuBar;

public class VueMenuActions extends JMenuBar {
	
	private JMenu menuActions;
		
	public VueMenuActions() {
		menuActions = new JMenu("Actions");
		add(menuActions);
	}
}
