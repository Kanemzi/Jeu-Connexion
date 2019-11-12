package jeu.modele;

import java.awt.Color;

public class Ordinateur {
	
	private Color couleur;
	private String nom;
	private int id;
	
	public Ordinateur(int id, String nom, Color couleur) {
		this.id = id;
		this.nom = nom;
		this.couleur = couleur;
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
	
	public Case choisirCoup(Partie partie) {
		
		return null;
	}
}
