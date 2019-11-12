package jeu.controleur;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JOptionPane;

import jeu.modele.Case;
import jeu.modele.Joueur;
import jeu.modele.Partie;
import jeu.vue.modales.ModaleSauvegarderPartie;

public class ControleurSauvegarderPartie implements ActionListener {

	private Partie partie;
	private ModaleSauvegarderPartie sauvegarder;

	public ControleurSauvegarderPartie(Partie partie) {
		this.partie = partie;
		sauvegarder = new ModaleSauvegarderPartie();
	}

	public void actionPerformed(ActionEvent ev) {
		sauvegarder.afficher(partie);
		if (!sauvegarder.valide())
			return;

		try {
			sauvegarderPartieFichier(sauvegarder.getNomFichier());
			JOptionPane.showMessageDialog(null,
					"La partie a été sauvegardée dans le répertoire res/plateaux",
					"Sauvegarder la partie", JOptionPane.INFORMATION_MESSAGE);
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null,
					"Une erreur s'est produite lors de la sauvegarde de la partie",
					"Sauvegarder la partie", JOptionPane.ERROR_MESSAGE);
			e.printStackTrace();
		}

	}

	private void sauvegarderPartieFichier(String nomFichier) throws IOException {
		int n = partie.getPlateau().getTaille();

		String premiereLigne = n + " " + partie.getPlateau().getMax() + "\n";
		String valeurCases = "";
		String proprietaireCases = "";

		for (int y = 0; y < n; y++) {
			for (int x = 0; x < n; x++) {
				Case c = partie.getPlateau().getCase(x, y);
				Joueur proprietaire = c.getProprietaire();
				valeurCases += c.getValeur() + " ";
				proprietaireCases += ((proprietaire == null) ? 0 : proprietaire.getId() + 1) + " ";
			}
			valeurCases += "\n";
			proprietaireCases += "\n";
		}

		File dossier = new File("res/plateaux/");
		if (!dossier.exists()) dossier.mkdirs();
		
		File sauvegarde = new File("res/plateaux/" + nomFichier);
		if (!sauvegarde.exists())
			sauvegarde.createNewFile();

		sauvegarde.setWritable(true);
		FileWriter fw = new FileWriter(sauvegarde);

		fw.write(premiereLigne);
		fw.write(valeurCases);
		fw.write(proprietaireCases);

		fw.close();
	}
}
