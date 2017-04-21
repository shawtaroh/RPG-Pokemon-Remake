package maps;
/*
Map.java
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
* Implements Pokemon SafariZone map, loads up tiles and art in hard-coded
* manner
*/

import java.util.Random;

import graphics.BitMap;
import model.Player;

public class Map {

	private final int width;
	private final int height;
	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	private Player myPlayer;
	protected static BitMap tile = BitMap.load("/art/floor/tile.png");
	protected static BitMap[][] house = BitMap.cut("/art/house/houseOne.png", 64, 64, 0, 0);
	protected static BitMap tree = BitMap.load("/art/wall/tree.png");
	protected static BitMap flowers = BitMap.load("/art/floor/flowersOne.png");
	protected static BitMap bolders = BitMap.load("/art/floor/highGround1.png");
	private String name;
	private Random generator = new Random(335);
	private boolean areFlowers[][] = new boolean[42][46];
	private boolean areBolders[][] = new boolean[42][46];

	public Map(int w, int h, Player player, String name) {
		this.width = w;
		this.height = h;
		this.myPlayer = player;
		this.name = name;
		randomizeFlowers();
		randomizeBolders();

	}

	public void randomizeFlowers() {
		for (int i = 0; i < 41; i++)
			for (int j = 0; j < 45; j++)
				if (generator.nextDouble() > .9)
					areFlowers[i][j] = true;
	}

	public void randomizeBolders() {
		generator = new Random(420);
		for (int i = 0; i < 41; i++)
			for (int j = 0; j < 45; j++)
				if (generator.nextDouble() > .97)
					areBolders[i][j] = true;
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
				if (x == 0 || y == 0 || y == 41 || x == 45) {
					screen.blit(tree, x * TILE_WIDTH, y * TILE_HEIGHT);
				} else {
					if (areFlowers[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(flowers, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (areBolders[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(bolders, x * TILE_WIDTH, y * TILE_HEIGHT);
				}

			}
		}
		screen.blit(house[0][0], 11 * TILE_WIDTH, 11 * TILE_HEIGHT);
		screen.blit(house[1][0], 12 * TILE_WIDTH, 11 * TILE_HEIGHT);
		screen.blit(house[2][0], 13 * TILE_WIDTH, 11 * TILE_HEIGHT);
		screen.blit(house[0][1], 11 * TILE_WIDTH, 12 * TILE_HEIGHT);
		screen.blit(house[1][1], 12 * TILE_WIDTH, 12 * TILE_HEIGHT);
		screen.blit(house[2][1], 13 * TILE_WIDTH, 12 * TILE_HEIGHT);
	}

	/*
	 * return map name
	 */
	public String getName() {

		return name;
	}
}