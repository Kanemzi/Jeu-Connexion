package jeu.modele;

import java.awt.Color;
import java.util.Random;

public class Partie {
	private Plateau plateau;
	private Joueur[] joueurs;
	
	private int tour;
	
	public Partie() {
		joueurs = new Joueur[2];
	}
	
	public Partie(int n, int max, String nom1, String nom2) {
		this();
		joueurs[0] = new Joueur(nom1, Color.red);
		joueurs[1] = new Joueur(nom2, Color.blue);		
		plateau = new Plateau().RemplirGrilleAleatoire(n, max);
	}
	
	public Partie(String nomFichier, String nom1, String nom2) {
		this();
		joueurs[0] = new Joueur(nom1, Color.red);
		joueurs[1] = new Joueur(nom2, Color.blue);		
		plateau = new Plateau().RemplirGrilleFichier(nomFichier, joueurs);
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
	
	public void addTour() {
		tour++;
	}
	
}
