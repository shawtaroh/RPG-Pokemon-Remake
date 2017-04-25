package model;

/*
							PokemonGame.java
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
Implements a PokemonGame object that separates GUI elements from game elements
*/

import java.util.ArrayList;

import maps.Map;
import maps.MapTypeOne;
import maps.MapTypeTwo;

public class PokemonGame {
	private Player player;
	private NPC NPC;
	private ArrayList<Key> keys = new ArrayList<>();
	private Map world;

	public PokemonGame(int mapSelection, int winCondition) {
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		player = new Player(keys, mapSelection, winCondition);
		NPC = new NPC(null, mapSelection, winCondition);
		if (mapSelection == 0) {
			world = new MapTypeOne(90, 60, player,NPC);
		}
		if (mapSelection == 1) {
			world = new MapTypeTwo(180, 135, player,NPC);
		}
	}

	public Player getPlayer() {
		return player;
	}

	public void setPlayer(Player player) {
		this.player = player;
	}

	public ArrayList<Key> getKeys() {
		return keys;
	}

	public Map getWorld() {
		return world;
	}

	public int getPlayerXPos() {
		return player.getxPosition();
	}

	public int getPlayerYPos() {
		return player.getyPosition();
	}

}