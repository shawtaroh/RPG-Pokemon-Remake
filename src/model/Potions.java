package model;

/*
							Potions.java
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
Implements a Potion class for player usable and storage potion items.
*/

import java.io.Serializable;

public class Potions extends ItemList implements Serializable{

	public Potions(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMenuUsable() {
		return true;
	}

	@Override
	public boolean isBattleUsable() {
		return false;
	}

	@Override
	public String toString(){
		return "Potions";
	}

	@Override
	public Effect getEffect() {
		return Effect.HEALING;
	}

	@Override
	public int getEffectAmount() {
		return 50;
	}
	
}
