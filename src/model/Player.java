package model;

/*
							Player.java
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
Implements Pokemon Trainer
*/

import java.util.ArrayList;

import graphics.BitMap;

public class Player extends Trainer {

	public Player(ArrayList<Key> keys, int map, int winCondition) {
		super(keys, map, winCondition);
		this.player = BitMap.cut("/art/player/NPC.png", 64, 64, 0, 0);
	}

	/*
	 * Remove snack from inventory if applicable
	 */
	public void eatSnack() {
		
		if(this.getMyBag().useItem("Snacks"))
			this.setSteps(this.getSteps() + 14);
	}

}