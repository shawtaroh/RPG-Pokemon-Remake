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

	@SuppressWarnings("unused")
	private ItemList safariBalls, potions;
	
	public Inventory() {
		safariBalls = new SafariBalls(30);
		potions = new Potions(0);
	}

	public int getNumPotions() {
		return potions.getQuantity();
	}

	public void addPotions(int numPotions) {
		potions.add(numPotions);
	}
	
	public boolean usePotion(){
		return potions.decrement();
	}
	

	public int getNumSafariballs() {
		return safariBalls.getQuantity();
	}

	public void addSafariballs(int numSafariballs) {
		safariBalls.add(numSafariballs);
	}

	public boolean useSafariBall(){
		return safariBalls.decrement();
	}
}
