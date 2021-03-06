package model;

import java.awt.Image;

/*
							Pokemon.java
                                  ,'\
    _.----.        ____         ,'  _\   ___    ___     ____
_,-'       `.     |    |  /`.   \,-'    |   \  /   |   |    \  |`.
\      __    \    '-.  | /   `.  ___    |    \/    |   '-.   \ |  |
 \.    \ \   |  __  |  |/    ,','_  `.  |          | __  |    \|  |
   \    \/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |
    \     ,-'/  /   \    ,'   | \/ / ,`.|         /  /   \  |     |
     \    \ |   \_/  |   `-.  \    `'  /|  |    ||   \_/  | |\    |
      \    \ \      /       `-.`.___,-' |  |\  /| \      /  | |   |
       \    \ `.__,'|  |`-._    `|      |__| \/ |  `.__,'|  | |   |
        \_.-'       |__|    `-._ |              '-.|     '-.| |   |
                                `'                            '-._|

@authors  
Eric Evans
Joey McClanahan
Matt Shaffer
Shawtaroh Granzier-Nakajima

@description
CS 335 Final Project
Implements Pokemon SafariZone pokemon object
*/

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import driver.GameGUI;

public class Pokemon implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8784965869253570309L;
	private int pokeNumber;
	private String name;
	private String type;
	private int maxHP;
	private double probabilityToRun, probabilityToCapture, baseProbabilityToCapture;
	
	// null for pokedex types
	private int currentHP;

	public int getCurrentHP() {
		return currentHP;
	}

	public void setCurrentHP(int currentHP) {
		this.currentHP = currentHP;
	}

	public String getType() {
		return type;
	}

	public int getMaxHP() {
		return maxHP;
	}

	public double getProbabilityToRun() {
		return probabilityToRun;
	}
	
	public double getProbabilityToCapture(){
		return probabilityToCapture;
	}

	public Pokemon(int pokeNumber, String name, String type) {
		this.pokeNumber = pokeNumber;
		this.name = name;
		this.type = type;
		// type dependent + gaussian randomness
		if (this.type.equals("Rare")) {
			probabilityToRun = .5;
			baseProbabilityToCapture = 0.1;
			maxHP = (int) (140 + 20 * (new Random().nextGaussian()));
		} else if (this.type.equals("Uncommon")) {
			probabilityToRun = .3;
			baseProbabilityToCapture = 0.3;
			maxHP = (int) (70 + 20 * (new Random().nextGaussian()));
		} else {
			probabilityToRun = .1;
			baseProbabilityToCapture = 0.5;
			maxHP = (int) (35 + 20 * (new Random().nextGaussian()));
		}
		
		if(maxHP <= 0){
			maxHP = 1;
		}
		
		currentHP = maxHP;
		
		updateProbabilityToCapture(0);
	}

	public int getPokeNumber() {
		return pokeNumber;
	}

	public String getName() {
		return name;
	}

	public Image getSprite() {
			return new ImageIcon(GameGUI.class.getResource("/art/pokemon/" + name + ".gif")).getImage();

	}

	public void giveBait() {
		incrementProbabilityToRun(-0.075);
		updateProbabilityToCapture(-0.05);
	}


	public Boolean throwRock() {
		incrementProbabilityToRun(0.05);
		incrementHP(-10);
		updateProbabilityToCapture(0.0);	
		
		if(currentHP == 0){
			return false;
		}else{
			return true;
		}
	}


	private void incrementHP(int value) {
		currentHP += value;
		
		if(currentHP > maxHP){
			currentHP = maxHP;
		}else if(currentHP < 0){
			currentHP = 0;
		}
	}
	
	private void incrementProbabilityToRun(double value) {
		probabilityToRun += value;
		if(probabilityToRun > 1.0){
			probabilityToRun = 1.0;
		}else if(probabilityToRun < 0.0){
			probabilityToRun = 0.0;
		}
		
	}

	private void updateProbabilityToCapture(double value){
		baseProbabilityToCapture += value;
		probabilityToCapture = baseProbabilityToCapture + (maxHP-currentHP)/(double)maxHP;
		if(probabilityToCapture > 1.0){
			probabilityToCapture = 1.0;
		}else if (probabilityToCapture < 0.0){
			probabilityToCapture = 0.0;
		}
	}

	public boolean checkIfRuns() {
		Random rand = new Random();
		if (rand.nextDouble() < probabilityToRun){
			return true;
		}else{
			return false;
		}
	}

	public boolean throwBall() {
		Random rand = new Random();
		if (rand.nextDouble() < probabilityToCapture){
			return true;
		}else{
			return false;
		}
	}

	public void givePotion() {
		incrementHP(10);
	}
	
}
