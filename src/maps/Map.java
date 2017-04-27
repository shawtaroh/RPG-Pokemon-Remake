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
import model.MazeGenerator;
import model.NPC;
import model.Player;

public class Map {

	private final int width;
	private final int height;
	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	private int professorX=100,professorY=100;
	public int getProfessorX() {
		return professorX;
	}

	public void setProfessorX(int professorX) {
		this.professorX = professorX;
	}

	public int getProfessorY() {
		return professorY;
	}

	public void setProfessorY(int professorY) {
		this.professorY = professorY;
	}

	private int mapNum;
	
	public int getMapNum(){
		return mapNum;
	}
	
	public void setMapNum(int i){
		mapNum = i;
	}
	
	
	private Player myPlayer;
	private NPC NPC;
	protected static BitMap tile = BitMap.load("/art/floor/tile.png");
	protected static BitMap lillies = BitMap.load("/art/wall/lillies.png");
	protected static BitMap youth = BitMap.load("/art/wall/fountain of youth.png");
	protected static BitMap[][] house = BitMap.cut("/art/house/houseOne.png", 64, 64, 0, 0);
	protected static BitMap[][] waterfall = BitMap.cut("/art/wall/waterfall.png", 64, 64, 0, 0);
	protected static BitMap tree = BitMap.load("/art/wall/tree.png");
	protected static BitMap water = BitMap.load("/art/wall/water.png");
	protected static BitMap flowers = BitMap.load("/art/floor/flowersOne.png");
	protected static BitMap stone = BitMap.load("/art/floor/inside.png");
	protected static BitMap bolders = BitMap.load("/art/floor/highGround1.png");
	protected static BitMap bolders2 = BitMap.load("/art/wall/grassbolder.png");
	protected static BitMap pole = BitMap.load("/art/wall/poleOne.png");
	protected static BitMap professor = BitMap.load("/art/misc/mercer.png");


	private String name;
	private Random generator = new Random(335);
	private boolean areFlowers[][] = new boolean[42][46];
	private boolean areBolders[][] = new boolean[46][46];
	private boolean areBolders2[][] = new boolean[42][46];
	private boolean isWater[][] = new boolean[42][46];
	private boolean isHouse[][] = new boolean[42][46];
	private boolean isWaterFall[][] = new boolean[42][46];
	private boolean isLillies[][] = new boolean[42][46];
	private boolean isYouth[][] = new boolean[42][46];
	private boolean isStone[][] = new boolean[42][46];
	private boolean isProfessor[][]=new boolean[42][46];

	public Map(int w, int h, Player player, NPC NPC, String name) {
		this.width = w;
		this.height = h;
		this.myPlayer = player;
		this.NPC=NPC;
		this.name = name;
		randomizeFlowers();
		randomizeBolders();
		setWater();
		setHouse();

	}

	public void setHouse() {
		isHouse[11][11] = true;
		isHouse[12][11] = true;
		isHouse[13][11] = true;
		isHouse[11][12] = true;
		isHouse[12][12] = true;
		isHouse[13][12] = true;
		for (int i = 7; i < 18; i++)
			for (int j = 9; j < 15; j++) {
				isStone[i][j] = true;
				if ((i == 7 || i == 17 || j == 9) && j != 14) {
					areBolders2[i][j] = true;
				}
			}

	}

	public void setWater() {
		for (int i = 27; i < 41; i++)
			for (int j = 1; j < 11; j++) {
				if((((double)i-33.5)*((double)i-33.5)+((double)j-6.0)*((double)j-6.0))<25.0){
				isWater[i][j]=true;
					if((((double)i-33.5)*((double)i-33.5)+((double)j-6.0)*((double)j-6.0))<16.0&&(((double)i-33.5)*((double)i-33.5)+((double)j-6.0)*((double)j-6.0))>1.0){
						isLillies[i][j]=true;
					}
					else if ((((double)i-33.5)*((double)i-33.5)+((double)j-6.0)*((double)j-6.0))<16.0)
						isYouth[i][j]=true;
				}
				else
					areBolders2[i][j] = true;
			}
		isWaterFall[31][0] = true;
		isWaterFall[32][0] = true;
		isWaterFall[33][0] = true;
		isWaterFall[31][1] = true;
		isWaterFall[32][1] = true;
		isWaterFall[33][1] = true;
		isWaterFall[31][2] = true;
		isWaterFall[32][2] = true;
		isWaterFall[33][2] = true;
		isWaterFall[34][0] = true;
		isWaterFall[35][0] = true;
		isWaterFall[36][0] = true;
		isWaterFall[34][1] = true;
		isWaterFall[35][1] = true;
		isWaterFall[36][1] = true;
		isWaterFall[34][2] = true;
		isWaterFall[35][2] = true;
		isWaterFall[36][2] = true;
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
					areBolders2[i][j] = true;

		boolean[][] maze = new model.MazeGenerator().randomMaze();

		boolean professorSet=false;
		
		for (int i = 22; i < 38; i++)
			for (int j = 6; j < 38; j++) {
				areBolders2[j][i] = false;
				if (maze[j - 6][i - 22]) {
					areBolders[j][i] = true;
					//areBolders[i][j] = true;
				}
				else
					if(!professorSet&&Math.random()>.97&&i>27&&j>16){
						isProfessor[j][i]=true;		
						professorSet=true;
					}
			}
	}

	public void tick() {
		myPlayer.tick();
		if(Math.random()<.25)
			NPC.tick();
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
		NPC.render(screen);
	}

	private void renderTiles(BitMap screen, int x0, int y0, int x1, int y1) {

		for (int y = y0; y < y1; y++) {
			for (int x = x0; x < x1; x++) {
				screen.blit(tile, x * TILE_WIDTH, y * TILE_HEIGHT);
				if (x == 0 || y == 0 || y == 41 || x == 45) {
					screen.blit(tree, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (isWaterFall[Math.abs(x) % 41][Math.abs(y-1) % 46])
						screen.blit(waterfall[(Math.abs(x) +10) % 2][(Math.abs(y) ) % 3], x * TILE_WIDTH,
								y * TILE_HEIGHT);
				} else {
					if (isStone[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(stone, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (areFlowers[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(flowers, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (isProfessor[Math.abs(x) % 41][Math.abs(y) % 46]){
						screen.blit(professor, x * TILE_WIDTH, y * TILE_HEIGHT);
						professorX=x* TILE_WIDTH-1408;
						professorY=y* TILE_WIDTH-1344;
						//System.out.println("Prof x, Prof Y:"+professorX+","+professorY);
					}
					if (areBolders2[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(bolders2, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (areBolders[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(bolders, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (isWater[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(water, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (isHouse[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(house[(Math.abs(x) - 11) % 3][(Math.abs(y) - 11) % 2], x * TILE_WIDTH,
								y * TILE_HEIGHT);
					if (isWaterFall[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(waterfall[(Math.abs(x) +10) % 2][(Math.abs(y) ) % 3], x * TILE_WIDTH,
								y * TILE_HEIGHT);
					if (isLillies[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(lillies, x * TILE_WIDTH, y * TILE_HEIGHT);
					if (isYouth[Math.abs(x) % 41][Math.abs(y) % 46])
						screen.blit(youth, x * TILE_WIDTH, y * TILE_HEIGHT);
				}

			}
		}
		screen.blit(pole, 10 * TILE_WIDTH, 11 * TILE_HEIGHT);
		screen.blit(pole, 14 * TILE_WIDTH, 11 * TILE_HEIGHT);
	}

	/*
	 * return map name
	 */
	public String getName() {

		return name;
	}

	public void useAxe(int i, int x, int y) {
		if (i == 0) {//down
			areBolders[22 + x / 64][22 + y / 64] = false;
		}
		if (i == 2) {//left
			areBolders[23 + x / 64][21 + y / 64] = false;
		}
		if (i == 1) {//right
			areBolders[21 + x / 64][21 + y / 64] = false;
		}
		if (i == 3) {//up
			areBolders[22 + x / 64][20 + y / 64] = false;
		}

	}
}