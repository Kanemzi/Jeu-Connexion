package modele;

public class Case {
	
	private int x, y, valeur;
	private Joueur proprietaire;
	
	private Case parent;
	private int profondeur;

	public Case(int x, int y, int valeur) {
		this.x = x;
		this.y = y;
		this.valeur = valeur;
		this.proprietaire = null;
		this.parent = null;
	}

	public Joueur getProprietaire() {
		return proprietaire;
	}

	public void setProprietaire(Joueur proprietaire) {
		this.proprietaire = proprietaire;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getValeur() {
		return valeur;
	}
	
	public Case getParent() {
		return parent;
	}
	
	public void setParent(Case parent) {
		this.parent = parent;
	}
	
	public int getProfondeur() {
		return profondeur;
	}

	public void setProfondeur(int profondeur) {
		this.profondeur = profondeur;
	}
}
