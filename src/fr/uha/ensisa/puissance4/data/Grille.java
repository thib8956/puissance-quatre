package fr.uha.ensisa.puissance4.data;


import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;

public class Grille {

	private final Case[][] grille;

	public Grille()
	{
		grille = new Case[Constantes.NB_COLONNES][Constantes.NB_LIGNES];
		for(int i=0;i<Constantes.NB_COLONNES;i++)
			for(int j=0;j<Constantes.NB_LIGNES;j++)
			{
				grille[i][j] = Case.V;
			}
	}

	/**
	 * Constructeur qui créé une copie de la grille donné en argument
	 * @param original
	 */
	private Grille(Grille original)
	{
		this.grille = new Case[original.grille.length][];
		for(int i = 0; i < grille.length; i++) {
			grille[i] = original.grille[i].clone();
		}
	}

	/**
	 * Renvoie le contenu de la case aux coordonnées données en argument
	 * @param ligne
	 * @param colonne
	 * @return
	 */
	public Case getCase(int ligne, int colonne)
	{
		return grille[colonne][ligne];
	}

	/**
	 * Indique s'il y a encore de la place dans la colonne indiquée
	 * @param colonne
	 * @return
	 */
	public boolean isCoupPossible(int colonne) {
		if(colonne>=0&&colonne<Constantes.NB_COLONNES)
		{
			return grille[colonne][Constantes.NB_LIGNES-1]==Case.V;
		}
		else
		{
			return false;
		}
	}

	/**
	 * Ajoute le symbole indiqué dans la colonne indiquée
	 * ce qui permet de jouer ce coup
	 * @param colonne
	 * @param symbole
	 */
	public void ajouterCoup(int colonne, Case symbole) {
		for(int j=0;j<Constantes.NB_LIGNES;j++)
		{
			if(grille[colonne][j] == Case.V)
			{
				grille[colonne][j]= symbole;
				break;
			}
		}

	}

	/**
	 * Renvoie l'état de la partie
	 * @param symboleJoueurCourant
	 * @param tour
	 * @return
	 */
	public int getEtatPartie(Case symboleJoueurCourant, int tour)
	{
		int victoire;
		if(symboleJoueurCourant==Constantes.SYMBOLE_J1)
		{
			victoire=Constantes.VICTOIRE_JOUEUR_1;
		}
		else
		{
			victoire=Constantes.VICTOIRE_JOUEUR_2;
		}
		int nbAlignes=0;
		//Vérification alignement horizontaux
		for(int i=0;i<Constantes.NB_LIGNES;i++)
		{
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement verticaux
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			for(int i=0;i<Constantes.NB_LIGNES;i++)
			{
				if(grille[j][i]==symboleJoueurCourant)
					nbAlignes++;
				else
					nbAlignes=0;
				if(nbAlignes==4)
				{
					return victoire;
				}
			}
			nbAlignes=0;
		}
		//Vérification alignement diagonaux (bas-droite vers haut-gauche)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=0;j<Constantes.NB_COLONNES-3;j++)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j+x<Constantes.NB_COLONNES;x++)
				{
					if(grille[j+x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		//Vérification alignement diagonaux (bas-gauche vers haut-droit)
		for(int i=0;i<Constantes.NB_LIGNES-3;i++)
			for(int j=Constantes.NB_COLONNES-1;j>=3;j--)
			{
				for(int x=0;i+x<Constantes.NB_LIGNES&&j-x>=0;x++)
				{
					if(grille[j-x][i+x]==symboleJoueurCourant)
						nbAlignes++;
					else
						nbAlignes=0;
					if(nbAlignes==4)
					{
						return victoire;
					}
				}
				nbAlignes=0;
			}

		if(tour==Constantes.NB_TOUR_MAX)
		{
			return Constantes.MATCH_NUL;
		}

		return Constantes.PARTIE_EN_COURS;
	}

	/**
	 * Donne un score à la grille en fonction du joueur 
	 * @param symboleJoueurCourant symbole du joueur courant
	 * @return
	 */
	public double evaluer(Case symboleJoueurCourant)
	{
		double eval = 0;
		for(int i=0;i<Constantes.NB_LIGNES;i++) eval += evalLigne(symboleJoueurCourant,i);
		for(int i = 0; i<Constantes.NB_COLONNES;i++) eval += evalColonne(symboleJoueurCourant,i);
		eval += evalDiagonale(symboleJoueurCourant);

		// Evaluation de l'adversaire
		double evalAdvers = 0;
		Case symbAdverse = getSymboleAdverse(symboleJoueurCourant);
		for(int i=0;i<Constantes.NB_LIGNES;i++) evalAdvers += evalLigne(symbAdverse,i);
		for(int i = 0; i<Constantes.NB_COLONNES;i++) evalAdvers += evalColonne(symbAdverse,i);
		evalAdvers += evalDiagonale(symbAdverse);
		return eval - 1.3 * evalAdvers;
	}

	/**
	 * Retourne pour une ligne la longueur de la plus longue chaîne d'un même pion.
	 * @param symb pion du joueur pour lequel evaluer la grille.
	 * @param ligne index de la ligne à évaluer
	 * @return longueur de la plus longue chaîne d'un même pion.
	 */
	private double evalLigne(Case symb, int ligne) {
		int nbAlignes;
		double result = 0;
		int milieu = Constantes.NB_COLONNES/2;
		final Case symbAdverse = getSymboleAdverse(symb);

		//Si symbole adverse en milieu de ligne, impossible d'avoir des points
		if(grille[milieu][ligne] != symb && grille[milieu][ligne] != Case.V) {
			return result;
		}

		for(int j = 0; j<Constantes.NB_COLONNES-3;j++) {
			// On regarde les 3 symboles suivants lorsqu'on trouve notre symbole
			if(grille[j][ligne] == symb) {
				// Si il y a un symbole adverse dans les 3 symboles suivants, la chaine est nulle
				if(grille[j+1][ligne] == symbAdverse
						|| grille[j+2][ligne] == symbAdverse
						|| grille[j+3][ligne] == symbAdverse) continue;
				// Autrement on regarde la longueur de la plus grande chaîne (nombre de pions alignés).
				for (nbAlignes = 0; nbAlignes < 4; ++nbAlignes) {
					if (grille[j+1][ligne] != symb) break;
				}

				if(nbAlignes > result) result = nbAlignes;
			}
		}

		return result;
	}

	/**
	 * Retourne pour une colonne la longueur de la plus longue chaîne d'un même pion.
	 * @param symb pion du joueur pour lequel evaluer la grille.
	 * @param colonne index de la colonne à évaluer
	 * @return longueur de la plus longue chaîne.
	 */
	private double evalColonne(Case symb, int colonne) {
		double nbAlignes = 0;
		Case symbAdverse = getSymboleAdverse(symb);
		
		for(int i = 0; i < Constantes.NB_LIGNES;i++) {
			if(grille[colonne][i] == symb) nbAlignes++;
			// Si la colonne est bloquée par un pion adverse, l'évaluation vaut 0
			if(grille[colonne][i] == symbAdverse) nbAlignes = 0;
		}
		return nbAlignes;
	}

	/**
	 * Retourne la somme de tous les alignements de pions réalisés en diagonale pour un même joueur.
	 * @param symb pion du joueur pour lequel évaluer la grille.
	 * @return somme des longueurs des chaînes dans le sens diagonal ascendant (/) et descendant (\)
	 */
	private double evalDiagonale(Case symb) {
		double result = 0;
		result += evalDiagonalAscendante(symb);
		result += evalDiagonalDescendante(symb);
		return result;
	}

	/**
	 * Clone la grille
	 */
	public Grille clone()
	{
		return new Grille(this);
	}

	/**
	 * Retourne le symbole correspondant au joueur adverse à partir du symbole passé en paramètre.
	 * @param symbJoueur symbole du joueur courant.
	 * @return symbole du joueur adverse.
	 */
	private Case getSymboleAdverse(Case symbJoueur) {
		if(symbJoueur.equals(Case.O)) {
			return Case.X;
		} else {
			return Case.O;
		}
	}

	/**
	 * Cherche la plus longue chaîne dans le sens diagonal ascendant (/)
	 * @param symb symbole du joueur pour lequel réaliser l'évaluation
	 * @return longueur de la chaîne
	 */
	private double evalDiagonalAscendante(Case symb) {
		//on evalue uniquement les diagonales qui peuvent réaliser 4 cases consécutives
		int nbAlignes = 0;
		double result = 0;
		Case symbJoueurAdverse = getSymboleAdverse(symb);
		
		for(int x = 0;x<4;x++) {
			for(int y = 0;y<3;y++) {
				if(grille[x][y] == symb) { //Verification si la case comporte notre symbole
					// Si une des trois prochaines cases en diagonale comporte un pion adverse, on saute l'évaluation
					// pour cette case car la diagonale est bloquée.
					if(grille[x+1][y+1] == symbJoueurAdverse || 
							grille[x+2][y+2] == symbJoueurAdverse || 
							grille[x+3][y+3] == symbJoueurAdverse) continue;
					// On vérifie les alignements de 2 pions du même joueur ou plus.
					if(grille[x+1][y+1] == symb) nbAlignes = 2;
					if(grille[x+2][y+2] == symb && nbAlignes == 2) nbAlignes = 3;
					if(grille[x+3][y+3] == symb && nbAlignes == 3) nbAlignes = 400;
				}
				if(nbAlignes > result) result = nbAlignes;
			}
		}

		return result;
	}

	/**
	 * Cherche la plus longue chaîne dans le sens diagonal descendant (\)
	 * @param symb symbole du joueur pour lequel réaliser l'évaluation
	 * @return longueur de la chaîne
	 */
	private double evalDiagonalDescendante(Case symb) {
		//on evalue uniquement les diagonales qui peuvent réaliser 4 cases consécutives
		int nbAlignes = 0;
		double result = 0;
		Case symbJoueurAdverse = getSymboleAdverse(symb);

		for(int x = 0;x<4;x++) {
			for(int y = 3; y < 6;y++) {
				if(grille[x][y] == symb) { // Verification si la case comporte notre symbole
					// Si une des trois prochaines cases en diagonale comporte un pion adverse, on saute l'évaluation
					// pour cette case car la diagonale est bloquée.
					if(grille[x+1][y-1] == symbJoueurAdverse ||
							grille[x+2][y-2] == symbJoueurAdverse ||
							grille[x+3][y-3] == symbJoueurAdverse) continue;
					// On vérifie les alignements de 2 pions du même joueur ou plus.
					if(grille[x+1][y-1] == symb) nbAlignes = 2;
					if(grille[x+2][y-2] == symb && nbAlignes == 2) nbAlignes = 3;
					if(grille[x+3][y-3] == symb && nbAlignes == 3) nbAlignes = 400;
				}
				if(nbAlignes > result) result = nbAlignes;
			}
		}

		return result;
	}
	

}
