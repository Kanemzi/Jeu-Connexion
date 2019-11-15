package jeu.modele;

import java.awt.Color;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JOptionPane;

import jeu.Config;
import jeu.modele.analyse.AnalyseUtils;
import jeu.modele.ordinateurs.Ordinateur;
import jeu.modele.ordinateurs.OrdinateurExpansionRapide;
import jeu.modele.ordinateurs.OrdinateurLimitationEnfermement;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent;
import jeu.modele.ordinateurs.OrdinateurPunitionRisque;
import jeu.vue.VueJeu;

public class Partie {
	private Plateau plateau;
	private Joueur[] joueurs;
	
	private int[] points;
	
	private int tour;
	private int maxTours;
	private int decalageTour;
	
	private Case dernierCoup;
	
	private boolean exited = false;

	public Partie() {
		tour = 0;
		joueurs = new Joueur[2];
		dernierCoup = null;
	}

	public Partie(int n, int max, String nom1, String nom2, boolean ordinateur, int decalageTour) {
		this(n, max, nom1, nom2, ordinateur);
		this.decalageTour = decalageTour;
	}
	
	public Partie(String nomFichier, String nom1, String nom2, boolean ordinateur, int decalageTour) {
		this(nomFichier, nom1, nom2, ordinateur);
		this.decalageTour = decalageTour;
	}
	
	public Partie(int n, int max, String nom1, String nom2, boolean ordinateur) {
		this();
		
		if (!Config.TEST) joueurs[0] = new Joueur(0, nom1, Color.red);
		else {
			try {
				joueurs[0] = (Joueur) Config.ORDI_ROUGE.getDeclaredConstructor(int.class, String.class, Color.class)
								.newInstance(0, nom1, Color.red);
			} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		
		if (ordinateur) {
			if (!Config.TEST) joueurs[1] = new OrdinateurPunitionRisque(1, nom2, Color.blue);
			else {
				try {
					joueurs[1] = (Joueur) Config.ORDI_BLEU.getDeclaredConstructor(int.class, String.class, Color.class)
									.newInstance(1, nom2, Color.blue);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		} else {
			joueurs[1] = new Joueur(1, nom2, Color.blue);					
		}
		plateau = new Plateau().RemplirGrilleAleatoire(n, max);
		maxTours = plateau.getTaille() * plateau.getTaille();
		
		System.out.println("poids : " +Arrays.toString(AnalyseUtils.calculerPoidsQuarts(this.getPlateau(), Config.COEF_BORDURES_GRAND)));
	}
	
	public Partie(String nomFichier, String nom1, String nom2, boolean ordinateur) {
		this();
		joueurs[0] = new Joueur(0, nom1, Color.red);
		if (ordinateur) {
			joueurs[1] = new OrdinateurMeilleurCoupAdjacent(1, nom2, Color.blue);
		} else {
			joueurs[1] = new Joueur(1, nom2, Color.blue);					
		}
		plateau = new Plateau().RemplirGrilleFichier(nomFichier, joueurs);
		tour = plateau.getPlacees();
		maxTours = plateau.getTaille() * plateau.getTaille();
	}

	public Plateau getPlateau() {
		return plateau;
	}
	
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	
	public Joueur getJoueurTour() {
		return joueurs[(tour + decalageTour) % joueurs.length];
	}
	
	public int getTour() {
		return tour;
	}
	
	public int getMaxTour() {
		return maxTours;
	}
	
	public Case getDernierCoup() {
		return dernierCoup;
	}
	
	public void addTour() {
		tour++;
	}
	
	public boolean terminee() {
		return tour >= maxTours - 1;
	}
	
	/**
	 * Joue le tour en sélectionnant une certaine case. Si un bot joue le tour,
	 * caseCliquee doit être égale à null.
	 * 
	 * La fonction met également à jour les différentes vues du jeu
	 * 
	 * @param caseCliquee
	 * @param vue la vue du jeu à mettre à jour
	 */
	public void jouerTour(Case caseCliquee, final VueJeu vue) {
		
		if (isExited()) return;
		
		/**
		 * Clic sur une case non occupée
		 */
		Joueur joueurTour = getJoueurTour();

		if (caseCliquee == null)
			caseCliquee = ((Ordinateur) joueurTour).jouer(this);
		else
			joueurTour.jouer(this, caseCliquee);

		// ------------ debug all cells ------------
		/**
		System.out.println("______________________________________________________");
		for (Case c : modele.getPlateau().getCases()) {
			System.out.println(c + " ##### parent: " + c.getParent() + " ##### enfants: " + c.getEnfants());
		}
		System.out.println("_ _ _ _ _ ");
		System.out.println(
				"composante : " + modele.getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));
		System.out.println("score : " + modele.getPlateau().AfficherScore(caseCliquee.getX(), caseCliquee.getY())); 
		**/
		// -----------------------------------------

		dernierCoup = caseCliquee;
		
		vue.getOverlay()
				.setCasesSurbrillances(getPlateau().AfficherComposante(caseCliquee.getX(), caseCliquee.getY()));

		vue.getOverlay().setDernierCoup(dernierCoup);

		int[] points = compterPoints();
		vue.getInformations().mettreAJourScores(points);

		/**
		 * Le dernier coup vient d'être joué
		 */
		if (terminee()) {
			// id du joueur qui vient de gagner la partie, si égalité, l'id est négatif
			int idgagnant = (points[0] > points[1]) ? 0 : (points[1] > points[0]) ? 1 : -1;

			String affichageScores = getJoueurs()[0].getNom() + " : " + points[0] + " points\n"
					+ getJoueurs()[1].getNom() + " : " + points[1] + " points";

			if (idgagnant < 0) {
				vue.getInformations().mettreAJourVainqueur(getJoueurs()[0]);
				vue.getInformations().mettreAJourVainqueur(getJoueurs()[1]);

				if (!Config.TEST) JOptionPane.showMessageDialog(null, "Partie terminée à égalité ! \n\n" + affichageScores,
						"Partie terminée", JOptionPane.INFORMATION_MESSAGE);
			} else {
				Joueur gagnant = getJoueurs()[idgagnant];
				vue.getInformations().mettreAJourVainqueur(gagnant);

				if (!Config.TEST) JOptionPane.showMessageDialog(null, gagnant.getNom() + " gagne la partie ! \n\n" + affichageScores,
						"Partie terminée", JOptionPane.INFORMATION_MESSAGE);
			}

		} else {
			addTour();
			joueurTour = getJoueurTour();

			/**
			 * Simuler un délai de réflexion pour l'ordinateur pour laisser le temps au
			 * joueur d'examiner le jeu
			 */
			if (joueurTour.isOrdinateur()) {
				new Timer().schedule(new TimerTask() {
					public void run() {
						jouerTour(null, vue);
					}
				}, Config.TEMPS_REPONSE_ORDINATEUR);
			}
		}

		/**
		 * Mettre à jour le compteur de tours et l'affichage du joueur qui doit jouer le
		 * prochain coup
		 */
		vue.getInformations().mettreAJourCompteur(getTour(), getMaxTour(), getJoueurTour());
	}
	
	/**
	 * Fait le calcul des points des deux joueurs en fonction de leur plus grosse composante
	 * @return un tableau contenant en premier indice le score de rouge et en second indice le score de bleu
	 */
	public int[] compterPoints() {
		int[] points = {0, 0};
		
		for (int i = 0; i < getMaxTour(); i++) {
			Case c = getPlateau().getCase(i);
			if (c.getParent() != null || c.getProprietaire() == null) continue;
			
			int idJoueur = c.getProprietaire().getId();
			
			int pointsComposante = getPlateau().AfficherScore(c.getX(), c.getY());
			System.out.println("____\n regu: " + pointsComposante + "\nzob: " + c.classe().scoreArbre);
			
			if( pointsComposante > points[idJoueur])
				points[idJoueur] = pointsComposante;
		}
		
		this.points = points;
		
		return points;
	}
	
	
	
	public boolean isExited() {
		return exited;
	}

	public void exit() {
		this.exited = true;
	}

}
