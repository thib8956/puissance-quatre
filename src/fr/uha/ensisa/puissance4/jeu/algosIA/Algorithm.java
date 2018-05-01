package fr.uha.ensisa.puissance4.jeu.algosIA;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public abstract class Algorithm {
	
	protected int levelIA;
	protected Grille grilleDepart;
	protected int tourDepart;
	protected int tourMax;
	/**
	 * Symbole adversaire
	 */
	protected Case symboleMin;
	/**
	 * Symbole joueur courant
	 */
	protected Case symboleMax;
	
	/**
	 * Instanciation de l'algorithme de Jeu
	 * @param levelIA
	 * @param grilleDepart
	 * @param joueurActuel
	 * @param tour
	 */
	public Algorithm(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour)
	{
		this.levelIA=levelIA;
		this.grilleDepart=grilleDepart;
		this.tourDepart=tour;
		this.tourMax= Math.min(tourDepart+levelIA, Constantes.NB_TOUR_MAX);
		if(joueurActuel.getOrder()==Constantes.JOUEUR_1)
		{
			symboleMax=Constantes.SYMBOLE_J1;
			symboleMin=Constantes.SYMBOLE_J2;
		}
		else
		{
			symboleMax=Constantes.SYMBOLE_J2;
			symboleMin=Constantes.SYMBOLE_J1;
		}
	}

	/**
	 * Lancement de l'algorithme de Jeu
	 * @return le coup choisi pour la grille indiqué lors de l'instantiation de l'algo
	 */
	public abstract int choisirCoup();

	/**
	 * Vérifie si la partie est terminée, soit par la vitoire d'un des deux joueurs, soit par un match nul.
	 * @param grille grille courante
	 * @param sym symbole du joueur courant
	 * @param tour tour de la partie
	 * @return true si la partie est terminée
	 */
	boolean verifierPartieTerminee(Grille grille, Case sym, int tour) {
		return grille.getEtatPartie(sym, tour) != Constantes.PARTIE_EN_COURS;
	}
}
