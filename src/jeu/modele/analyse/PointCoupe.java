package jeu.modele.analyse;

import java.util.List;

import jeu.modele.Case;

public class PointCoupe {
	public Case reponse;
	public List<Case> groupes;
	
	public PointCoupe(Case reponse, List<Case> groupes) {
		this.reponse = reponse;
		this.groupes = groupes;
	}
}
