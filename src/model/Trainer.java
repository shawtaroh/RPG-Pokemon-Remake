package model;

import java.io.Serializable;

/*
 * Player.java
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
 * Implements Pokemon Trainer
 */

import java.util.ArrayList;
import java.util.Random;

import graphics.BitMap;

public class Trainer implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7403201549575621783L;
	private ArrayList<Key>		keys;
	protected int				xPosition			= -TILE_WIDTH * 0;
	protected int				yPosition			= -TILE_HEIGHT * 0;
	private int					xLastPosition		= -TILE_WIDTH * 0;
	private int					yLastPosition		= -TILE_HEIGHT * 0;
	private int					map;
	private boolean				stepTracker			= false;
	private static boolean		enterHome			= false;
	private Inventory			myBag;
	private int					hp					= 100;
	private int					speed				= 30;
	private boolean				hasAxe				= false;
	private boolean[][]			maze;
	
	private ArrayList<Pokemon>	myPokemon			= new ArrayList<>();
	
	
	
	public void changeWorld(int i) {
		
		if (i < 1 || i > 2)
			return;
		map = i;
	}
	
	
	
	public void setMyBag(Inventory myBag) {
		this.myBag = myBag;
	}



	public void setSpeed(int speed) {
		
		this.speed = speed;
	}
	
	
	
	public ArrayList<Pokemon> getMyPokemon() {
		
		return this.myPokemon;
	}
	
	
	
	public void setMyPokemon(ArrayList<Pokemon> myPokemon) {
		
		this.myPokemon = myPokemon;
	}
	
	
	
	public boolean isHasAxe() {
		
		return hasAxe;
	}
	
	
	
	public void setHasAxe(boolean hasAxe) {
		
		this.hasAxe = hasAxe;
	}
	
	
	
	public static boolean isEnterHome() {
		
		return enterHome;
	}
	
	
	
	public Inventory getMyBag() {
		
		return this.myBag;
	}
	
	
	
	public static void setEnterHome(boolean the) {
		
		enterHome = the;
	}
	
	
	
	public int getxPosition() {
		
		return xPosition;
	}
	
	
	
	public void setxPosition(int xPosition) {
		
		this.xPosition = xPosition;
	}
	
	
	
	public int getyPosition() {
		
		return yPosition;
	}
	
	
	
	public void setyPosition(int yPosition) {
		
		this.yPosition = yPosition;
	}
	
	
	
	public int getxLastPosition() {
		
		return xLastPosition;
	}
	
	
	
	public void setxLastPosition(int xLastPosition) {
		
		this.xLastPosition = xLastPosition;
	}
	
	
	
	public int getyLastPosition() {
		
		return yLastPosition;
	}
	
	
	
	public void setyLastPosition(int yLastPosition) {
		
		this.yLastPosition = yLastPosition;
	}
	
	
	
	public int getSteps() {
		
		return steps;
	}
	
	
	
	public void setSteps(int steps) {
		
		this.steps = steps;
	}
	
	
	
	public int getHP() {
		
		return hp;
	}
	
	public void setHP(int newHP) {
		hp = newHP;
	}

	protected static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	private int steps = 500;
	protected int facing = 0;
	private int turning = 0;
	protected byte animationTick = 0;
	protected byte animationPointer = 0;
	private ArrayList<RPoint> restrictedX = new ArrayList<>();

	protected int xAccel;
	protected int yAccel;
	private int winCondition;

	boolean lockWalking;

	private static Player uniqueInstance;

	public static Player getInstance(ArrayList<Key> keys, int map, int winCondition) {

		if (uniqueInstance == null)
			uniqueInstance = new Player(keys, map, winCondition);
		return uniqueInstance;
	}
	
	
	
	public Trainer(ArrayList<Key> keys, int map, int winCondition) {
		this.keys = keys;
		this.map = map;
		this.winCondition = winCondition;
		loadRestrictions();
		myBag = new Inventory();
		
	}
	
	
	
	public int getWinCondition() {
		
		return winCondition;
	}
	
	
	
	public void setWinCondition(int winCondition) {
		
		this.winCondition = winCondition;
	}
	
	
	
	public int getMap() {
		
		return map;
	}
	
	
	
	public void tick() {
		
		if (!lockWalking) {
			if (xPosition == -TILE_WIDTH * 20 && yPosition == TILE_WIDTH * -18
			        && facing == 3) {
				// enterHome = true;
				// iteration 2
			}
			if (keys.get(0).isTappedDown()) {// up
				facing = 3;
				animationTick = 0;
				animationPointer = 0;
			}
			else if (keys.get(1).isTappedDown()) {// down
				facing = 0;
				animationTick = 0;
				animationPointer = 0;
			}
			else if (keys.get(2).isTappedDown()) {// left
				facing = 1;
				animationTick = 0;
				animationPointer = 0;
			}
			else if (keys.get(3).isTappedDown()) {// right
				facing = 2;
				animationTick = 0;
				animationPointer = 0;
			}
		}
		if (!lockWalking && !perimeterTile(xPosition, yPosition)) {
			if (keys.get(0).isPressedDown()) {
				if (facing == 3)
					yAccel = yAccel - 2;
				else
					facing = 2;
				lockWalking = true;
			}
			if (keys.get(1).isPressedDown()) {
				if (facing == 0)
					yAccel = yAccel + 2;
				else
					facing = 0;
				lockWalking = true;
			}
			if (keys.get(2).isPressedDown()) {
				if (facing == 1)
					xAccel = xAccel - 2;
				else
					facing = 1;
				lockWalking = true;
			}
			if (keys.get(3).isPressedDown()) {
				if (facing == 2)
					xAccel = xAccel + 2;
				else
					facing = 3;
				lockWalking = true;
				
			}
		}
		handleMovement();
		animationTick++;
		if (animationTick >= 8) {
			animationTick = 0;
			animationPointer++;
			if (animationPointer > 3) {
				animationPointer = 0;
			}
		}
	}
	
	
	
	public void render(BitMap screen) {
		BitMap[][]player = BitMap.cut("/art/player/NPC.png", 64, 64, 0, 0);
		if (lockWalking) {
			screen.blit(player[turning][animationPointer],
			        (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
			        (screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16,
			        TILE_WIDTH, TILE_HEIGHT);
		}
		else
			screen.blit(player[facing][0],
			        (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
			        (screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16,
			        TILE_WIDTH, TILE_HEIGHT);
	}
	
	
	
	protected boolean perimeterTile(int x, int y) {
		
		for (RPoint p : restrictedX)
			if ((x == p.getY() && Math.abs(p.getX() - y) <= 64)
			        || (y == p.getX() && Math.abs(p.getY() - x) <= 64)) {
				if (x == p.getY()) {
					if ((y > p.getX()) && facing == 3)
						return true;
					if ((y < p.getX()) && facing == 0)
						return true;
				}
				else {
					if ((x > p.getY()) && facing == 1)
						return true;
					if ((x < p.getY()) && facing == 2)
						return true;
				}
			}
		return false;
	}
	
	
	
	// perimeter squares
	private void loadRestrictions() {
		
		// house
		restrictedX.add(new RPoint(-10 * TILE_WIDTH, -9 * TILE_WIDTH, false));
		restrictedX.add(new RPoint(-10 * TILE_WIDTH, -10 * TILE_WIDTH, false));
		restrictedX.add(new RPoint(-10 * TILE_WIDTH, -11 * TILE_WIDTH, false));
		restrictedX.add(new RPoint(-9 * TILE_WIDTH, -9 * TILE_WIDTH, false));
		restrictedX.add(new RPoint(-9 * TILE_WIDTH, -10 * TILE_WIDTH, false));
		restrictedX.add(new RPoint(-9 * TILE_WIDTH, -11 * TILE_WIDTH, false));
		Random generator = new Random(420);
		for (int i = 0; i < 41; i++)
			for (int j = 0; j < 45; j++)
				if (generator.nextDouble() > .97)
					restrictedX.add(new RPoint((j - 21) * TILE_WIDTH,
					        (i - 22) * TILE_WIDTH, false));
		maze = new model.MazeGenerator().randomMaze();
		
		for (int i = 22; i < 38; i++)
			for (int j = 6; j < 38; j++) {
				RPoint tmp1 = new RPoint((j - 22) * TILE_WIDTH,
				        (i - 21) * TILE_WIDTH, true);
				RPoint tmp2 = new RPoint((i - 21) * TILE_WIDTH,
				        (j - 22) * TILE_WIDTH, true);
				// restrictedX.remove(tmp1);
				restrictedX.remove(tmp2);
				if (maze[j - 6][i - 22]) {
					// restrictedX.add(tmp1);
					restrictedX.add(tmp2);
				}
			}
		// house perimeter
		for (int i = 7; i < 18; i++)
			for (int j = 9; j < 15; j++) {
				if ((i == 7 || i == 17 || j == 9) && j != 14) {
					restrictedX.add(new RPoint((j - 21) * TILE_WIDTH,
					        (i - 22) * TILE_WIDTH, false));
				}
			}
		// water-zone
		for (int i = 27; i < 41; i++)
			for (int j = 1; j < 11; j++) {
				if ((((double) i - 33.5) * ((double) i - 33.5)
				        + ((double) j - 6.0) * ((double) j - 6.0)) > 25.0) {
					restrictedX.add(new RPoint((j - 21) * TILE_WIDTH,
					        (i - 22) * TILE_WIDTH, false));
				}
				else
					restrictedX.remove(new RPoint((j - 21) * TILE_WIDTH,
					        (i - 22) * TILE_WIDTH, false));
				if ((((double) i - 33.5) * ((double) i - 33.5)
				        + ((double) j - 6.0) * ((double) j - 6.0)) <= 1.0)
					restrictedX.add(new RPoint((j - 21) * TILE_WIDTH,
					        (i - 22) * TILE_WIDTH, false));
			}
	}
	
	
	
	public void handleMovement() {
		
		if (perimeterTile(xPosition, yPosition)) {
			lockWalking = false;
			return;
		}
		if (lockWalking) {
			stepTracker = true;
			if (xPosition > -TILE_WIDTH * 21 && xPosition < TILE_WIDTH * 22)
				xPosition += xAccel;
			else {
				if (xPosition == -TILE_WIDTH * 21 && xAccel > 0)
					xPosition += xAccel;
				else if (xPosition == TILE_WIDTH * 22 && xAccel < 0)
					xPosition += xAccel;
			}
			if (yPosition > -TILE_WIDTH * 20 && yPosition < TILE_WIDTH * 19)
				yPosition += yAccel;
			else {
				if (yPosition == -TILE_WIDTH * 20 && yAccel > 0)
					yPosition += yAccel;
				else if (yPosition == TILE_WIDTH * 19 && yAccel < 0)
					yPosition += yAccel;
			}
		}
		if (xPosition % TILE_WIDTH == 0 && yPosition % TILE_HEIGHT == 0) {
			lockWalking = false;
			if (stepTracker) {
				// if corrupted save state doesnt save steps
				if (steps > 500)
					steps = 500;
				if (Math.abs(xLastPosition - xPosition) > 10
				        || Math.abs(yLastPosition - yPosition) > 10) {
					steps--;
					xLastPosition = xPosition;
					yLastPosition = yPosition;
				}
				stepTracker = false;
			}
			xAccel = 0;
			yAccel = 0;
			turning = facing;
		}
		else
			lockWalking = true;
	}
	
	
	
	public void useAxe() {
		
		if (!hasAxe)
			return;
		if (this.facing == 0) {// down
			System.out.println((this.yPosition - 21 * TILE_WIDTH) + ","
			        + (this.xPosition - 22 * TILE_WIDTH));
			RPoint remove = null;
			for (RPoint p : restrictedX)
				if ((xPosition == p.getY() && yPosition - p.getX() == -64))
					remove = p;
			if (remove != null && remove.isRemovable()
			        && restrictedX.remove(remove))
				System.out.println("removed");
		}
		
		if (this.facing == 2) {// right
			System.out.println((this.yPosition - 21 * TILE_WIDTH) + ","
			        + (this.xPosition - 22 * TILE_WIDTH));
			RPoint remove = null;
			for (RPoint p : restrictedX)
				if ((yPosition == p.getX() && xPosition - p.getY() == -64))
					remove = p;
			if (remove != null && remove.isRemovable()
			        && restrictedX.remove(remove))
				System.out.println("removed");
		}
		if (this.facing == 1) {// left
			System.out.println((this.yPosition - 21 * TILE_WIDTH) + ","
			        + (this.xPosition - 22 * TILE_WIDTH));
			RPoint remove = null;
			for (RPoint p : restrictedX)
				if ((yPosition == p.getX() && xPosition - p.getY() == 64))
					remove = p;
			if (remove != null && remove.isRemovable()
			        && restrictedX.remove(remove))
				System.out.println("removed");
		}
		if (this.facing == 3) {// up
			System.out.println((this.yPosition - 21 * TILE_WIDTH) + ","
			        + (this.xPosition - 22 * TILE_WIDTH));
			RPoint remove = null;
			for (RPoint p : restrictedX)
				if ((xPosition == p.getY() && yPosition - p.getX() == 64))
					remove = p;
			if (remove != null && remove.isRemovable()
			        && restrictedX.remove(remove))
				System.out.println("removed");
		}
		
	}
	
	
	
	public int getFacing() {
		
		return facing;
	}
	
	
	
	public void setLockWalking(boolean b) {
		
		lockWalking = b;
		
	}
	
}
