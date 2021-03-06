package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;


public class Minimax extends Algorithm {


	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		int meilleureColonne = 0;
		double meilleurScore = Constantes.SCORE_MAX_NON_DEFINI;
		// Pour toutes les colonnes
		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			// On regarde si on peut jouer dans cette colonne
			if (grilleDepart.isCoupPossible(i)) {
				Grille courante = grilleDepart.clone();
				courante.ajouterCoup(i, symboleMax);
				double score = minScore(courante, 0, tourDepart);
				if (score > meilleurScore) {
					meilleurScore = score;
					meilleureColonne = i;
				}
			}
		}

		return meilleureColonne;
	}

	/**
	 * Tour du joueur adverse : selectionne le score le plus faible parmi les états enfants.
	 */
	private double minScore(Grille grilleCourante, int profondeur, int tour) {
		double scoreMin = Constantes.SCORE_MIN_NON_DEFINI;
		// Si la grille actuelle correspond à une feuille de l'arbre de jeu, on retourne son évaluation.
		if (profondeur == levelIA || verifierPartieTerminee(grilleCourante, symboleMin, tour)) {
			return grilleCourante.evaluer(symboleMin);
		}

		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			if (grilleCourante.isCoupPossible(i)) {
				Grille g = grilleCourante.clone();
				g.ajouterCoup(i, symboleMin);
				double score = maxScore(g, profondeur + 1, tour + 1);
				if (score < scoreMin) {
					scoreMin = score;
				}
			}
		}

		return scoreMin;
	}

	/**
	 * Tour du joueur courant : selectionne le score le plus élevé parmi les états enfants.
	 */
	private double maxScore(Grille grilleCourante, int profondeur, int tour) {
		double scoreMax = Constantes.SCORE_MAX_NON_DEFINI;
		// Si la grille actuelle correspond à une feuille de l'arbre de jeu, on retourne son évaluation.
		if (profondeur == levelIA || verifierPartieTerminee(grilleCourante, symboleMax, tour)) {
			return grilleCourante.evaluer(symboleMax);
		}

		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			if (grilleCourante.isCoupPossible(i)) {
				Grille g = grilleCourante.clone();
				g.ajouterCoup(i, symboleMax);
				double score = minScore(g, profondeur + 1 , tour + 1);
				if (score > scoreMax) {
					scoreMax = score;
				}
			}
		}

		return scoreMax;
	}
}
