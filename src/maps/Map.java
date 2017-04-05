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

public class Map {

	private final int width;
	private final int height;
	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	private Player myPlayer;
	private static BitMap tile = BitMap.load("/art/floor/tile.png");
	private static BitMap[][] house = BitMap.cut("/art/house/houseOne.png", 64, 64, 0, 0);
	private static BitMap tree = BitMap.load("/art/wall/tree.png");

	public Map(int w, int h, Player player) {
		this.width = w;
		this.height = h;
		this.myPlayer = player;

	}

	public void tick() {
		myPlayer.tick();
	}

	public void render(BitMap screen, int xScroll, int yScroll) {
		int gridX0 = xScroll / TILE_WIDTH - 1;
		int gridY0 = yScroll / TILE_HEIGHT - 1;
		int gridX1 = (xScroll + screen.getWidth()) / TILE_WIDTH + 1;
		int gridY1 = (yScroll + screen.getHeight()) / TILE_HEIGHT + 1;

		if (xScroll < 0)
			gridX0--;
		if (yScroll < 0)
			gridY0--;

		this.renderTiles(screen, gridX0, gridY0, gridX1, gridY1);

		myPlayer.render(screen);
	}

	private void renderTiles(BitMap screen, int x0, int y0, int x1, int y1) {
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
