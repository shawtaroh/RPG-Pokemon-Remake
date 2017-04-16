package model;

import java.util.ArrayList;

import driver.Key;
import maps.Map;
import maps.MapTypeOne;
import maps.MapTypeTwo;

public class PokemonGame {
	private Player player;
	private ArrayList<Key> keys = new ArrayList<>();
	private Map world;

	public PokemonGame(int mapSelection) {
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		player = new Player(keys, mapSelection);
		if (mapSelection == 0) {
			world = new MapTypeOne(90, 60, player);
		}
		if (mapSelection == 1) {
			world = new MapTypeTwo(180, 135, player);
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