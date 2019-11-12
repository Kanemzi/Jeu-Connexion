package jeu.modele;

import java.awt.Color;

public class Joueur {
	
	protected Color couleur;
	protected String nom;
	protected int id;
	protected boolean ordinateur;
	
	public Joueur(int id, String nom, Color couleur) {
		this.id = id;
		this.nom = nom;
		this.couleur = couleur;
		this.ordinateur = false;
	}
	
	public void jouer(Partie partie, Case cliquee) {
		partie.getPlateau().ColorerCase(cliquee.getX(), cliquee.getY(), this);
	}

	public Color getCouleur() {
		return couleur;
	}
	
	public String getNom() {
		return nom;
	}
	
	public int getId() {
		return id;
	}
	
	public boolean isOrdinateur() {
		return ordinateur;
	}
}
