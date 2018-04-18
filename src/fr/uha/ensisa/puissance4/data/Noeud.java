package fr.uha.ensisa.puissance4.data;

import java.util.ArrayList;
import java.util.List;

public class Noeud {
	private List<Noeud> childs;
	private Grille grille;
	private double eval;
	
	public Noeud(Grille grille) {
		childs = new ArrayList<>();
		this.grille = grille;
	}
	
	public void addChild(Noeud child) {
		this.childs.add(child);
	}
	
	public List<Noeud> getChilds(){
		return childs;
	}
	
	public Grille getGrille() {
		return grille;
	}
	
	public void setEval(double eval) {
		this.eval=eval;
	}
	
	public double getEval() {
		return this.eval;
	}

}
