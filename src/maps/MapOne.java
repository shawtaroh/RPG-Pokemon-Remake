package maps;

/*
							TestWorld.java
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
Implements Pokemon SafariZone map, loads up tiles and art in hard-coded manner
*/

import graphics.BitMap;
import model.Player;

public class MapOne extends Map{

	private static BitMap tile = BitMap.load("/art/floor/tile.png");
	private static BitMap[][] house = BitMap.cut("/art/house/houseOne.png", 64, 64, 0, 0);
	private static BitMap tree = BitMap.load("/art/wall/tree.png");

	public MapOne(int w, int h, Player player) {
		super(w,h,player);
	}
	
	public void renderTiles(BitMap screen, int x0, int y0, int x1, int y1) {
		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				screen.blit(tile, x * TILE_WIDTH, y * TILE_HEIGHT);
				if (x == 0 || y == 0 || y == height || x == width) {
					screen.blit(tree, x * TILE_WIDTH, y * TILE_HEIGHT);
				}
			}
		}
		screen.blit(house[0][0], 1 * TILE_WIDTH, 1 * TILE_HEIGHT);
		screen.blit(house[1][0], 2 * TILE_WIDTH, 1 * TILE_HEIGHT);
		screen.blit(house[2][0], 3 * TILE_WIDTH, 1 * TILE_HEIGHT);
		screen.blit(house[0][1], 1 * TILE_WIDTH, 2 * TILE_HEIGHT);
		screen.blit(house[1][1], 2 * TILE_WIDTH, 2 * TILE_HEIGHT);
		screen.blit(house[2][1], 3 * TILE_WIDTH, 2 * TILE_HEIGHT);

	}
}
