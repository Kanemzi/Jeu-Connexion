package jeu.vue.overlay;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.util.Set;

import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.plaf.LayerUI;

import jeu.modele.Case;
import jeu.vue.VuePlateau;

public class OverlayPlateau extends LayerUI<JPanel> {
	private static final int largeurContour = 4;
	private static final Stroke styleContour = new BasicStroke(largeurContour);

	private int tailleBouton;
	private VuePlateau plateau;

	private Set<Case> casesSurbrillance;
	private Case dernierCoup;

	public OverlayPlateau(VuePlateau plateau) {
		this.plateau = plateau;
		this.tailleBouton = plateau.getTailleBouton();
		casesSurbrillance = null;
		dernierCoup = null;
	}

	public void setCasesSurbrillances(Set<Case> cases) {
		casesSurbrillance = cases;
		plateau.repaint();
	}

	public void setDernierCoup(Case coup) {
		dernierCoup = coup;
		plateau.repaint();
	}

	@Override
	public void paint(Graphics g, JComponent c) {
		Graphics2D g2 = (Graphics2D) g;
		super.paint(g2, c);

		if (casesSurbrillance != null)
			for (Case cs : casesSurbrillance) {
				g2.setColor(Color.YELLOW);
				g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.4f));
				g2.fillRect(cs.getX() * tailleBouton, cs.getY() * tailleBouton, tailleBouton, tailleBouton);
			}

		if (dernierCoup != null) {
			g2.setStroke(styleContour);
			g2.setColor(Color.ORANGE);
			g2.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 1f));
			g2.drawRect(dernierCoup.getX() * tailleBouton + largeurContour / 2,
					dernierCoup.getY() * tailleBouton + largeurContour / 2, tailleBouton - largeurContour,
					tailleBouton - largeurContour);
		}

		g2.dispose();
	}
}