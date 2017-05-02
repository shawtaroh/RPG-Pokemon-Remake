package model;

/*
 * PokemonGame.java
 * ,'\
 * _.----. ____ ,' _\ ___ ___ ____
 * _,-' `. | | /`. \,-' | \ / | | \ |`.
 * \ __ \ '-. | / `. ___ | \/ | '-. \ | |
 * \. \ \ | __ | |/ ,','_ `. | | __ | \| |
 * \ \/ /,' _`.| ,' / / / / | ,' _`.| | |
 * \ ,-'/ / \ ,' | \/ / ,`.| / / \ | |
 * \ \ | \_/ | `-. \ `' /| | || \_/ | |\ |
 * \ \ \ / `-.`.___,-' | |\ /| \ / | | |
 * \ \ `.__,'| |`-._ `| |__| \/ | `.__,'| | | |
 * \_.-' |__| `-._ | '-.| '-.| | |
 * `' '-._|
 * 
 * @authors
 * Eric Evans
 * Joey McClanahan
 * Matt Shaffer
 * Shawtaroh Granzier-Nakajima
 * 
 * @description
 * CS 335 Final Project
 * Implements a PokemonGame object that separates GUI elements from game
 * elements
 */

import java.util.ArrayList;

import maps.Map;
import maps.MapTypeOne;
import maps.MapTypeTwo;

public class PokemonGame {
	
	private Player			player;
	private ArrayList<Key>	keys	= new ArrayList<>();
	private Map				world;
	
	
	
	public PokemonGame(int mapSelection, int winCondition) {
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		player = new Player(keys, mapSelection, winCondition);
		if (mapSelection == 0) {
			world = new MapTypeOne(90, 60, player);
		}
		if (mapSelection == 1) {
			world = new MapTypeTwo(180, 135, player);
		}
	}
	
	
	/*
	 * for changing current world / map
	 */
	public void changeWorld(int i) {
		
		if (i < 1 || i > 2)
			return;
			
		// Change to Map One
		if(i == 1){
			if(world.getMapNum() == 1){
				System.out.println("\tAlready in World 1");
				return;
			}
			else{
				world = new MapTypeOne(90, 60, player);
				player.changeWorld(1);
				System.out.println("\tGo To World 1");
			}
		}
		// Change to Map Two
		else{
			if(world.getMapNum() == 2){
				System.out.println("\tAlready in World 2");
				return;
			}
			else{
				world = new MapTypeTwo(180, 135, player);
				player.changeWorld(2);
				System.out.println("\tGo To World 2");
			}
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
