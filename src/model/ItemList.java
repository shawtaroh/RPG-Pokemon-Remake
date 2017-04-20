package model;

import java.io.Serializable;

/*
							ItemList.java
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
This is a list object that allows items to be stacked in the player inventory.
*/

public abstract class ItemList implements Serializable {
	private int quantity;

	public ItemList(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void add(int amount) {
		quantity += amount;
	}

	public boolean decrement() {
		if (quantity > 0) {
			quantity--;
			return true;
		}

		return false;
	}

	public abstract boolean isMenuUsable();

	public abstract boolean isBattleUsable();

	public abstract Effect getEffect();

	public abstract int getEffectAmount();

}
