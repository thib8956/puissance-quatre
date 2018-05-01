package fr.uha.ensisa.puissance4.view;



import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import fr.uha.ensisa.puissance4.data.Humain;
import fr.uha.ensisa.puissance4.data.IA;
import fr.uha.ensisa.puissance4.data.Joueur;
import fr.uha.ensisa.puissance4.data.Partie;
import fr.uha.ensisa.puissance4.jeu.Main;
import fr.uha.ensisa.puissance4.util.Constantes;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;



public class PlayersViewController {
	
	Partie partie;


	@FXML
	private Label labelNomJ1;
	@FXML
	private Label labelNomJ2;
	@FXML
	private Label labelLvlJ1;
	@FXML
	private Label labelLvlJ2;
	@FXML
	private TextField nomJ1;
	@FXML
	private TextField nomJ2;
	@FXML
	private TextField lvlJ1;
	@FXML
	private TextField lvlJ2;
	@FXML
	private RadioButton humainJ1;
	@FXML
	private RadioButton humainJ2;
	@FXML
	private RadioButton iaJ1;
	@FXML
	private RadioButton iaJ2;
	@FXML
	private RadioButton miniMaxJ1;
	@FXML
	private RadioButton miniMaxJ2;
	@FXML
	private RadioButton alphaJ1;
	@FXML
	private RadioButton alphaJ2;
	@FXML
	private Button commencer;





	// Reference to the main application.
	private Main main;

	/**
	 * The constructor.
	 * The constructor is called before the initialize() method.
	 */
	public PlayersViewController() {
	}

	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 */
	@FXML
	private void initialize() {
		humainJ1.setVisible(true);
		humainJ2.setVisible(true);
		humainJ1.setSelected(true);
		humainJ2.setSelected(true);
		iaJ1.setVisible(true);
		iaJ2.setVisible(true);

		labelNomJ1.setVisible(true);
		labelNomJ2.setVisible(true);
		labelLvlJ1.setVisible(false);
		labelLvlJ2.setVisible(false);
		nomJ1.setVisible(true);
		nomJ2.setVisible(true);
		lvlJ1.setVisible(false);
		lvlJ2.setVisible(false);
		miniMaxJ1.setVisible(false);
		miniMaxJ2.setVisible(false);
		alphaJ1.setVisible(false);
		alphaJ2.setVisible(false);

	}

	@FXML
	private void estHumainJ1() {
		labelNomJ1.setVisible(true);
		nomJ1.setVisible(true);
		iaJ1.setSelected(false);
		
		labelLvlJ1.setVisible(false);
		lvlJ1.setVisible(false);
		miniMaxJ1.setVisible(false);
		alphaJ1.setVisible(false);
	}

	@FXML
	private void estHumainJ2() {
		labelNomJ2.setVisible(true);
		nomJ2.setVisible(true);
		iaJ2.setSelected(false);
		
		labelLvlJ2.setVisible(false);
		lvlJ2.setVisible(false);
		miniMaxJ2.setVisible(false);
		alphaJ2.setVisible(false);
	}

	@FXML
	private void estIaJ1() {
		labelNomJ1.setVisible(false);
		nomJ1.setVisible(false);
		humainJ1.setSelected(false);
		
		labelLvlJ1.setVisible(true);
		lvlJ1.setVisible(true);
		miniMaxJ1.setVisible(true);
		alphaJ1.setVisible(true);
	}

	@FXML
	private void estIaJ2() {
		labelNomJ2.setVisible(false);
		nomJ2.setVisible(false);
		humainJ2.setSelected(false);
		
		labelLvlJ2.setVisible(true);
		lvlJ2.setVisible(true);
		miniMaxJ2.setVisible(true);
		alphaJ2.setVisible(true);
	}
	
	@FXML
	private void miniMaxJ1() {
		alphaJ1.setSelected(false);
	}
	
	@FXML
	private void miniMaxJ2() {
		alphaJ2.setSelected(false);
	}
	
	@FXML
	private void alphaBetaJ1() {
		miniMaxJ1.setSelected(false);
	}
	
	@FXML
	private void alphaBetaJ2() {
		miniMaxJ2.setSelected(false);
	}



	@FXML
	private void commencer()  {
		Joueur J1 = null;
		Joueur J2 = null;

		if(iaJ1.isSelected()) {
			int algo;
			int lvl;
			String nomIA=Constantes.IA_NAMES[(int)Math.floor(Math.random()*Constantes.IA_NAMES.length)];
			
			if(miniMaxJ1.isSelected()) algo = Constantes.IA_MINIMAX;
			else algo = Constantes.IA_ALPHABETA;
			
			if(Integer.parseInt(lvlJ1.getText()) > 42) lvl = 42;
			else if(Integer.parseInt(lvlJ1.getText()) < 1) lvl = 1;
			else lvl = Integer.parseInt(lvlJ1.getText());
			
			J1 = new IA(nomIA,1,algo,lvl);
		}
		
		if(humainJ1.isSelected()) {
			String nom = nomJ1.getText();
			J1 = new Humain(nom,1);
		}
		
		if(iaJ2.isSelected()) {
			int algo;
			int lvl;
			String nomIA=Constantes.IA_NAMES[(int)Math.floor(Math.random()*Constantes.IA_NAMES.length)];
			
			if(miniMaxJ2.isSelected()) algo = Constantes.IA_MINIMAX;
			else algo = Constantes.IA_ALPHABETA;
			
			if(Integer.parseInt(lvlJ2.getText()) > 42) lvl = 42;
			else if(Integer.parseInt(lvlJ2.getText()) < 1) lvl = 1;
			else lvl = Integer.parseInt(lvlJ2.getText());
			
			J2 = new IA(nomIA,2,algo,lvl);
		}
		
		if(humainJ2.isSelected()) {
			String nom = nomJ2.getText();
			J2 = new Humain(nom,2);
		}
		
		System.out.println("J1 : "+J1.getNom());
		System.out.println("J2 : "+J2.getNom());
		
		partie = new Partie(J1,J2);
		main.setPartie(partie);
		main.showPartie();

	}

	/**
	 * Is called by the main application to give a reference back to itself.
	 * 
	 * @param mainApp
	 */
	public void setMainApp(Main main) {
		this.main = main;
	}






}
