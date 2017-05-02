package model;

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
import java.util.Random;

public class Pokemon {

	private int pokeNumber;
	private String name;
	private BufferedImage sprite;
	private String type;
	private int maxHP;
	private double probabilityToRun;
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

	public Pokemon(int pokeNumber, String name, BufferedImage sprite, String type) {
		this.pokeNumber = pokeNumber;
		this.name = name;
		this.sprite = sprite;
		this.type = type;
		// type dependent + gaussian randomness
		if (this.type == "Rare") {
			probabilityToRun = .8;
			maxHP = (int) (140 + 20 * (new Random().nextGaussian()));
			currentHP = maxHP;
		} else if (this.type == "Uncommon") {
			probabilityToRun = .6;
			maxHP = (int) (70 + 20 * (new Random().nextGaussian()));
			currentHP = maxHP;
		} else {
			probabilityToRun = .2;
			maxHP = (int) (35 + 20 * (new Random().nextGaussian()));
			currentHP = maxHP;
		}
	}

	public int getPokeNumber() {
		return pokeNumber;
	}

	public String getName() {
		return name;
	}

	public BufferedImage getSprite() {
		return sprite;
	}

}
