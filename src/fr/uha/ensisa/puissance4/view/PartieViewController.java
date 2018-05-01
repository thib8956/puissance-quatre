package fr.uha.ensisa.puissance4.view;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.uha.ensisa.puissance4.data.Grille;
import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.jeu.Main;
import fr.uha.ensisa.puissance4.util.Constantes;
import fr.uha.ensisa.puissance4.util.Constantes.Case;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;



public class PartieViewController {
	

	List<Circle> listCircle;


	@FXML
	private Label message;
	
	@FXML
	private Button accueil;

	@FXML
	private Button but1;
	@FXML
	private Button but2;
	@FXML
	private Button but3;
	@FXML
	private Button but4;
	@FXML
	private Button but5;
	@FXML
	private Button but6;
	@FXML
	private Button but7;

	@FXML
	private GridPane grid;
	





	// Reference to the main application.
	private Main main;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public PartieViewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		listCircle = new ArrayList<>();
		for(Node it : grid.getChildren()) {
			if(it instanceof Circle) listCircle.add((Circle) it);
		}
		accueil.setVisible(false);

	}

	@FXML
	private void but1() {
		main.getPartie().jouerCoup(0, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();

	}
	@FXML
	private void but2() {
		main.getPartie().jouerCoup(1, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();

	}
	@FXML
	private void but3() {
		main.getPartie().jouerCoup(2, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();
	}
	@FXML
	private void but4() {
		main.getPartie().jouerCoup(3, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();
	}
	@FXML
	private void but5() {
		main.getPartie().jouerCoup(4, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();
	}
	@FXML
	private void but6() {
		main.getPartie().jouerCoup(5, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();
	}
	@FXML
	private void but7() {
		main.getPartie().jouerCoup(6, 0);
		this.afficherGrille();
		affichageInfo();
		joueurSuivant();		
	}
	
	@FXML
	private void retourAccueil() {
		main.showPlayers();	
	}
	


	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Main main) {
		this.main = main;
		if(main.getPartie().getJoueur1() instanceof IA) {
			joueurSuivant();
		}
	}
	
	public void afficherGrille() {
		for(int colonne = 0;colonne<Constantes.NB_COLONNES;colonne++) {
			for(int ligne = 0; ligne< Constantes.NB_LIGNES;ligne++) {
				if(main.getPartie().getGrille().getCase(ligne, colonne) == Case.X ) {
					listCircle.get(ligne * Constantes.NB_COLONNES + colonne).setFill(Color.RED);
				}
				else if(main.getPartie().getGrille().getCase(ligne, colonne) == Case.O) {
					listCircle.get(ligne * Constantes.NB_COLONNES + colonne).setFill(Color.GREEN);
				}
			}
		}
	}
	
	public void affichageInfo() {
		message.setText("Joueur "+main.getPartie().getJoueurCourant().getNom()+", c'est à vous !");
		if(main.getPartie().getJoueurCourant() instanceof IA) {
			message.setText("L'Ia "+main.getPartie().getJoueurCourant().getNom()+" va jouer !");
		}
		if(main.getPartie().isPartieFinie()) {
			if(main.getPartie().getEtatPartie() == Constantes.MATCH_NUL) {
				message.setText("C'est un match nul en "+main.getPartie().getTour());
				but1.setVisible(false);
				but2.setVisible(false);
				but3.setVisible(false);
				but4.setVisible(false);
				but5.setVisible(false);
				but6.setVisible(false);
				but7.setVisible(false);
			}
			else {
				but1.setVisible(false);
				but2.setVisible(false);
				but3.setVisible(false);
				but4.setVisible(false);
				but5.setVisible(false);
				but6.setVisible(false);
				but7.setVisible(false);
				message.setText("Le joueur gagnant est : "+main.getPartie().getJoueurCourant().getNom() + " en "+main.getPartie().getTour()+" tours");
			}

		}	
	}
	
	public void joueurSuivant() {
		if(main.getPartie().getJoueurCourant() instanceof IA) {
			int coup = ((IA) main.getPartie().getJoueurCourant()).joue(main.getPartie().getGrille(), main.getPartie().getTour());
			main.getPartie().jouerCoup(coup, 0);
			afficherGrille();
			affichageInfo();
			if(main.getPartie().getJoueurCourant() instanceof IA && main.getPartie().getEtatPartie() == Constantes.PARTIE_EN_COURS) {
				joueurSuivant();
			}
		}
	}





}
