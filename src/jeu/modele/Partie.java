package jeu.modele;

import java.awt.Color;
import java.util.Random;

public class Partie {
	private Plateau plateau;
	private Joueur[] joueurs;
	
	private int tour;
	private int maxTours;
	
	public Partie() {
		tour = 0;
		joueurs = new Joueur[2];	
	}

	public Partie(int n, int max, String nom1, String nom2) {
		this();
		joueurs[0] = new Joueur(0, nom1, Color.red);
		joueurs[1] = new Joueur(1, nom2, Color.blue);		
		plateau = new Plateau().RemplirGrilleAleatoire(n, max);
		maxTours = plateau.getTaille() * plateau.getTaille();
	}
	
	public Partie(String nomFichier, String nom1, String nom2) {
		this();
		joueurs[0] = new Joueur(0, nom1, Color.red);
		joueurs[1] = new Joueur(1, nom2, Color.blue);		
		plateau = new Plateau().RemplirGrilleFichier(nomFichier, joueurs);
		tour = plateau.getPlacees();
		maxTours = plateau.getTaille() * plateau.getTaille();
	}
	
	/**
	 * Lancer la partie
	 */
	public void lancer() {
		
	}

	public Plateau getPlateau() {
		return plateau;
	}
	
	public Joueur[] getJoueurs() {
		return joueurs;
	}
	
	public Joueur getJoueurTour() {
		return joueurs[tour % joueurs.length];
	}
	
	public int getTour() {
		return tour;
	}
	
	public int getMaxTour() {
		return maxTours;
	}
	
	public void addTour() {
		tour++;
	}
	
	public boolean terminee() {
		return tour >= maxTours - 1;
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
			System.out.println();
			if( pointsComposante > points[idJoueur])
				points[idJoueur] = pointsComposante;
		}
		
		return points;
	}
}
