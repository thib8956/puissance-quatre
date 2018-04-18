package fr.uha.ensisa.puissance4.jeu.algosIA;

import java.util.ArrayList;
import java.util.List;

import fr.uha.ensisa.puissance4.data.ArbreDeJeu;
import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Noeud;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;



public class Minimax extends Algorithm {
	private Joueur joueur;



	public Minimax(int levelIA, Grille grilleDepart, Joueur joueurActuel, int tour) {
		super(levelIA, grilleDepart, joueurActuel, tour);
		joueur = joueurActuel;
		construireArbre();

	}

	@Override
	public int choisirCoup() {
		if(levelIA % 2 == 0) {
			
		}



		return 1;
	}

	public void max(Noeud noeud){
		double bestVal = Constantes.SCORE_MAX_NON_DEFINI;
		for(Noeud curr : noeud.getChilds()) {
			curr.setEval(curr.getGrille().evaluer(symboleMax));
			if(curr.getEval() > bestVal) {
				bestVal = curr.getEval();
			}
		}
		noeud.setEval(bestVal);
	}
	
	public void min(Noeud noeud) {
		double lesVal = Constantes.SCORE_MIN_NON_DEFINI;
		for(Noeud curr : noeud.getChilds()) {
			curr.setEval(curr.getGrille().evaluer(symboleMin));
			if(curr.getEval() < lesVal) {
				lesVal = curr.getEval();
			}
		}
		noeud.setEval(lesVal);
	}


	public void construireArbre() {
		Case symb;
		Noeud noeud = arbre.getRacine();
		parcoursEnfants(joueur.getSymbole(), noeud);
		
		for(int profondeur = 1; profondeur<levelIA;profondeur++) {
			for(Noeud curr : noeud.getChilds()) {
				if (profondeur % 2 == 0) {
					 symb = joueur.getSymbole();
				}
				else {
					if(joueur.getSymbole() == Constantes.SYMBOLE_J1) {
						symb = Constantes.SYMBOLE_J2;
					}
					else {
						symb = Constantes.SYMBOLE_J1;
					}
				}
				noeud = parcoursEnfants(symb,curr);
				for(Noeud it : noeud.getChilds()) {
					afficheGrille(it.getGrille());
				}

			}

		}
	}


	private void afficheGrille(Grille grille) {
		String s="";
		for(int i=Constantes.NB_LIGNES-1;i>=0;i--)
		{
			s+="|";
			for(int j=0;j<Constantes.NB_COLONNES;j++)
			{
				String symbol;
				if(grille.getCase(i, j)==Case.V)
					symbol=" ";
				else
					symbol = grille.getCase(i, j).toString();

				s+=symbol+"|";
			}
			s+="\n";
		}
		s+="=";
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			s+="==";
		}
		s+="\n";
		for(int j=0;j<Constantes.NB_COLONNES;j++)
		{
			s+=" "+(j+1);
		}
		System.out.println(s);

	}

	private Noeud parcoursEnfants(Case symb, Noeud noeud) {
		Grille grille;
		for(int i=0; i<7;i++) {
			grille = noeud.getGrille().clone();
			if(!grille.isCoupPossible(i)) {
				continue;
			}
			grille.ajouterCoup(i, symb);
			noeud.addChild(new Noeud(grille));
		}
		return noeud;
	}



}
