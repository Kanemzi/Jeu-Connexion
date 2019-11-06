package modele;

import java.awt.Color;

public class Joueur {
	
	private Color couleur;
	private String nom;

	public Joueur(String nom, Color couleur) {
		this.nom = nom;
		this.couleur = couleur;
	}

	public Color getCouleur() {
		return couleur;
	}
	
	public String getNom() {
		return nom;
	}
}
