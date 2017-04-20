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
Implements a SafariBall class to track players use of SafariBalls and their behavior.
*/

import java.io.Serializable;

public class SafariBalls extends ItemList implements Serializable{

	public SafariBalls(int quantity) {
		super(quantity);
	}

	@Override
	public boolean isMenuUsable() {
		return false;
	}

	@Override
	public boolean isBattleUsable() {
		return true;
	}
	
	@Override
	public String toString(){
		return "Safari Balls";
	}

	@Override
	public Effect getEffect() {
		return null;
	}

	@Override
	public int getEffectAmount() {
		return 0;
	}
}
