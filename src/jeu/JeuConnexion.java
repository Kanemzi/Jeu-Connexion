package jeu;

import java.awt.Dimension;
import java.util.Arrays;

import javax.swing.JFrame;

import jeu.modele.Partie;
import jeu.modele.analyse.AnalyseUtils;
import jeu.vue.VueJeu;
import jeu.vue.VueMenuActions;

public class JeuConnexion extends JFrame {
	
	Partie partie;
	VueJeu vue;
	VueMenuActions menu;
	
	public JeuConnexion() {
		partie = null;
		vue = null;
		menu = new VueMenuActions(this);
		
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setJMenuBar(menu);
		
		setSize(new Dimension(Config.WINDOW_SIZE, Config.WINDOW_SIZE));
		
		setLocationRelativeTo(null);
		setVisible(true);
	}
	
	public Partie JouerDeuxHumains(String nomJoueur1, String nomJoueur2, int taille, int max) {
		if (vue != null) remove(vue);

		partie = new Partie(taille, max, nomJoueur1, nomJoueur2, false, 1);
		//partie = new Partie("res/plateaux/Joueur 1-Joueur 2-1573566204059.cnx", nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		float[][] poids = AnalyseUtils.calculerPoidsPlateau(partie.getPlateau()).poids;
		for (int x = 0; x < poids[0].length; x++) {
			System.out.println(Arrays.toString(poids[x]));
		}
		
		menu.enregistrerControleurs(partie);
		
		add(vue);
		pack();
		
		return partie;
	}
	
	public Partie JouerOrdiHumain(String nomJoueur, String nomOrdinateur, int taille, int max, boolean ordinateurCommence) {
		if (vue != null) remove(vue);

		partie = new Partie(taille, max, nomJoueur, nomOrdinateur, true, ordinateurCommence ? 1 : 0);
		//partie = new Partie("res/plateaux/Joueur 1-Joueur 2-1573566204059.cnx", nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		menu.enregistrerControleurs(partie);
		
		add(vue);
		pack();
		
		return partie;
	}
	

	public Partie chargerOrdiHumain(String nomJoueur, String nomOrdinateur, String nomPartie,	boolean ordinateurCommence) {
		if (vue != null) remove(vue);

		partie = new Partie(nomPartie, nomJoueur, nomOrdinateur, true, ordinateurCommence ? 1 : 0);
		//partie = new Partie("res/plateaux/Joueur 1-Joueur 2-1573566204059.cnx", nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		menu.enregistrerControleurs(partie);
		
		add(vue);
		pack();
		
		return partie;
	}
	
	public Partie chargerDeuxHumains(String nomJoueur1, String nomJoueur2, String nomPartie) {
		if (vue != null) remove(vue);

		partie = new Partie(nomPartie, nomJoueur1, nomJoueur2, false, 1);
		//partie = new Partie("res/plateaux/Joueur 1-Joueur 2-1573566204059.cnx", nomJoueur1, nomJoueur2);
		vue = new VueJeu(partie);
		
		float[][] poids = AnalyseUtils.calculerPoidsPlateau(partie.getPlateau()).poids;
		for (int x = 0; x < poids[0].length; x++) {
			System.out.println(Arrays.toString(poids[x]));
		}
		
		menu.enregistrerControleurs(partie);
		
		add(vue);
		pack();
		
		return partie;
	}
	public VueJeu getVue() {
		return vue;
	}
	
	public static void main(String[] args) {
		if (Config.TEST) {
			float parties = 1;
			float rouge = 0;
			float bleu = 0;
			float egalite = 0;
			
			Partie p = new Partie(Config.TAILLE_GRILLE, Config.MAX_VALEUR, "Rouge", "Bleu", true, Config.BLEU_COMMENCE ? 1 : 0);
			p.jouerTour(null, new VueJeu(p));
			
			while (parties <= Config.TAILLE_ECHANTILLON) {
				try {
					Thread.sleep(10);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if (p.terminee()) {
					parties++;
					int[] points = p.compterPoints();
					if (points[0] > points[1])
						rouge++;
					else if (points[1] > points[0])
						bleu++;
					else egalite++;
					
					System.out.println("_________________\npartie " + (parties-1) + "\nrouge: " + rouge/(parties-1) + "\nbleu: " + bleu/(parties-1) + "\negalite: " + egalite/(parties-1));
					
					p = new Partie(Config.TAILLE_GRILLE, Config.MAX_VALEUR, "Rouge", "Bleu", true, Config.BLEU_COMMENCE ? 1 : 0);
					p.jouerTour(null, new VueJeu(p));
				}
			}
				
		} else 
			new JeuConnexion();
	}



}
