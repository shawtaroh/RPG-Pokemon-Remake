package model;

/*
							Inventory.java
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
Implements Pokemon SafariZone inventory object (manages three items)
*/

public class Inventory {

	private int numPokeballs;
	private int numPotions;
	private int numSafariballs;

	public Inventory() {
		numPokeballs = 30;
	}

	public int getNumPokeballs() {
		return numPokeballs;
	}

	public void setNumPokeballs(int numPokeballs) {
		this.numPokeballs = numPokeballs;
	}

	public int getNumPotions() {
		return numPotions;
	}

	public void setNumPotions(int numPotions) {
		this.numPotions = numPotions;
	}

	public int getNumSafariballs() {
		return numSafariballs;
	}

	public void setNumSafariballs(int numSafariballs) {
		this.numSafariballs = numSafariballs;
	}

}
