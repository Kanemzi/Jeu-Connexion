package jeu;

import jeu.modele.ordinateurs.Ordinateur;
import jeu.modele.ordinateurs.OrdinateurExpansionRapide;
import jeu.modele.ordinateurs.OrdinateurLimitationEnfermement;
import jeu.modele.ordinateurs.OrdinateurMeilleurCoupAdjacent;

public class Config {
	public static final int WINDOW_SIZE = 600;
	public static final int TEMPS_REPONSE_ORDINATEUR = 0;
	
	// Ordinateur
	public static final float COEF_BORDURES_PETIT = .45f;
	public static final float COEF_BORDURES_GRAND = .25f;
	public static final int LIMITE_PETIT = 5;
	
	public static final boolean TEST = true;
	public static final boolean VISIBLE = false;
	public static final int TAILLE_ECHANTILLON = 1000;
	public static final int TAILLE_GRILLE = 8;
	public static final int MAX_VALEUR = 2;
	public static final boolean BLEU_COMMENCE = false;
	public static final Class<? extends Ordinateur> ORDI_ROUGE = OrdinateurExpansionRapide.class;
	public static final Class<? extends Ordinateur> ORDI_BLEU = OrdinateurMeilleurCoupAdjacent.class;
}
