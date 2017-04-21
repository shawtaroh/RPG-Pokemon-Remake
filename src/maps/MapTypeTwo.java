package maps;

import graphics.BitMap;
import model.Player;

/*
MapTypeTwo.java
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
Implements Pokemon SafariZone Map 2
*/

public class MapTypeTwo extends Map {

	public MapTypeTwo(int w, int h, Player player) {
		super(w, h, player, "Map 2");
		this.tile = BitMap.load("/art/floor/tileTwo.png");
		this.house = BitMap.cut("/art/house/houseTwo.png", 64, 64, 0, 0);
		this.tree = BitMap.load("/art/wall/treeTwo.png");
		this.flowers = BitMap.load("/art/floor/leaves.png");
		this.bolders = BitMap.load("/art/floor/highGround2.png");
		this.bolders2= BitMap.load("/art/wall/snow.png");;

	}

}