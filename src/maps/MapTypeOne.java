package maps;

import graphics.BitMap;
import model.Player;

/*
 * MapTypeOne.java
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
 * 
 * @authors
 * Eric Evans
 * Joey McClanahan
 * Matt Shaffer
 * Shawtaroh Granzier-Nakajima
 * 
 * @description
 * CS 335 Final Project
 * Implements Pokemon SafariZone Map 1
 */

public class MapTypeOne extends Map {
	
	public MapTypeOne(int w, int h, Player player, model.NPC npc) {
		super(w, h, player, npc, "Map 1");
		
		this.tile = BitMap.load("/art/floor/tile.png");
		this.house = BitMap.cut("/art/house/houseOne.png", 64, 64, 0, 0);
		this.tree = BitMap.load("/art/wall/tree.png");
		this.flowers = BitMap.load("/art/floor/flowersOne.png");
		this.bolders2 = BitMap.load("/art/wall/grassbolder.png");
		this.bolders=BitMap.load("/art/floor/highGround1.png");
		
		setMapNum(1);
	}
	
}
