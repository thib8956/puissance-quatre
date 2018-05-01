package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;


public class AlphaBeta extends Algorithm {

	public AlphaBeta(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
	}

	@Override
	public int choisirCoup() {
		// Colonne choisie pour ce coup par l'algorithme
		int meilleureColonne = 0;
		double meilleurScore = Constantes.SCORE_MAX_NON_DEFINI;
		double alpha = Constantes.SCORE_MAX_NON_DEFINI;
		double beta = Constantes.SCORE_MIN_NON_DEFINI;
		// Pour toutes les colonnes
		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			// On regarde si on peut jouer dans cette colonne
			if (grilleDepart.isCoupPossible(i)) {
				Grille courante = grilleDepart.clone();
				courante.ajouterCoup(i, symboleMax);
				// Obtenir l'évaluation pour cette configuration par des appels récursifs à minScore
				// et maxScore
				double score = minScore(courante, 0, tourDepart, alpha, beta);
				if (score >= meilleurScore) {
					meilleurScore = score;
					meilleureColonne = i;
				}
			}
		}
		return meilleureColonne;
	}

	/**
	 * Tour du joueur courant : selectionne le score le plus élevé parmi les états enfants.
	 * Utilisation de l'élagage alpha-beta pour éviter de développer les noeuds inutiles.
	 * @param grilleCourante grille de départ à partir de laquelle rechercher le meilleur coup
	 * @param profondeur profondeur actuelle dans l'arbre de recherche.
	 * @param tour tour courant
	 * @param alpha plus grande valeur pour MAX parmi les enfants
	 * @param beta plus petite valeur pour MIN parmi les enfants
	 * @return score le plus élevé parmi les états enfants.
	 */
	private double maxScore(Grille grilleCourante, int profondeur, int tour, double alpha, double beta) {
		double scoreMax = Constantes.SCORE_MAX_NON_DEFINI;
		// Si la grille actuelle correspond à une feuille de l'arbre de jeu, on retourne son évaluation.
		// On limite également la profondeur de recherche (niveau de l'IA)
		if (profondeur == levelIA || verifierPartieTerminee(grilleCourante, symboleMax, tour)) {
			return grilleCourante.evaluer(symboleMax);
		}

		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			if (grilleCourante.isCoupPossible(i)) {
				Grille g = grilleCourante.clone();
				g.ajouterCoup(i, symboleMax);
				double score = minScore(g, profondeur + 1, tour + 1, alpha, beta);

				// Élagage de cette branche
				if (score > beta) {
					return score;
				}
				scoreMax = Math.max(score, scoreMax);
				// On met à jour la valeur d'alpha si le score est supérieur.
				alpha = Math.max(alpha, score);
			}
		}

		return scoreMax;
	}

	/**
	 * Tour du joueur adverse : selectionne le score le plus faible parmi les états enfants.
	 * Utilisation de l'élagage alpha-beta pour éviter de développer les noeuds inutiles.
	 * @param grilleCourante grille de départ à partir de laquelle rechercher le meilleur coup pour l'adversaire
	 * @param profondeur profondeur actuelle dans l'arbre de recherche.
	 * @param tour tour courant
	 * @param alpha plus grande valeur pour MAX parmi les enfants
	 * @param beta plus petite valeur pour MIN parmi les enfants
	 * @return score le plus faible parmi les états enfants
	 */
	private double minScore(Grille grilleCourante, int profondeur, int tour, double alpha, double beta) {
		double scoreMin = Constantes.SCORE_MIN_NON_DEFINI;
		// Si la grille actuelle correspond à une feuille de l'arbre de jeu, on retourne son évaluation.
		if (profondeur == levelIA || verifierPartieTerminee(grilleCourante, symboleMin, tour)) {
			return grilleCourante.evaluer(symboleMin);
		}

		for (int i=0; i < Constantes.NB_COLONNES; i++) {
			if (grilleCourante.isCoupPossible(i)) {
				Grille g = grilleCourante.clone();
				g.ajouterCoup(i, symboleMin);
				double score = maxScore(g, profondeur + 1, tour + 1, alpha, beta);

				// Élagage de cette branche
				if (score < alpha) {
					return score;
				}

				scoreMin = Math.min(score, scoreMin);
				beta = Math.min(beta, score);
			}
		}

		return scoreMin;
	}
	
}
