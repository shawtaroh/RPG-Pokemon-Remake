package model;

import java.util.ArrayList;

import driver.Key;
import maps.Map;
import maps.MapOne;
import maps.MapTwo;

public class PokemonGame {
	private Player player;
	private ArrayList<Key> keys = new ArrayList<>();
	private Map world;
	
	public PokemonGame(int mapSelection){
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		player = new Player(keys);
		if(mapSelection == 0){
			world = new MapOne(90, 60, player);
		}
		if(mapSelection == 1){
			world = new MapTwo(180, 135, player);
		}
	}
	
	public ArrayList<Key> getKeys(){
		return keys;
	}
	
	public Map getWorld(){
		return world;
	}
	
	public int getPlayerXPos(){
		return player.xPosition;
	}
	
	public int getPlayerYPos(){
		return player.yPosition;
	}
	
}
