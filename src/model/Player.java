package model;

import java.awt.Point;
import java.io.Serializable;

/*
								Player.java
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
Implements Pokemon Trainer
*/

import java.util.ArrayList;

import driver.Key;
import graphics.BitMap;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7403201549575621783L;
	private ArrayList<Key> keys;
	private int xPosition = -TILE_WIDTH * 0;
	private int yPosition = -TILE_HEIGHT * 0;
	private int map;
	private boolean stepTracker = false;
	private static boolean enterHome = false;

	public static boolean isEnterHome() {
		return enterHome;
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

	public int getSteps() {
		return steps;
	}

	public void setSteps(int steps) {
		this.steps = steps;
	}

	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	private int steps = 500;
	private int facing = 0;
	private int turning = 0;
	private byte animationTick = 0;
	private byte animationPointer = 0;
	private static BitMap[][] player;
	private ArrayList<Point> restrictedX = new ArrayList<>();

	private int xAccel;
	private int yAccel;

	boolean lockWalking;

	private static Player uniqueInstance;

	public static Player getInstance(ArrayList<Key> keys, int map) {
		if (uniqueInstance == null)
			uniqueInstance = new Player(keys, map);
		return uniqueInstance;
	}

	public Player(ArrayList<Key> keys, int map) {
		this.keys = keys;
		this.map = map;
		loadRestrictions();
		player = BitMap.cut("/art/player/player.png", 64, 64, 0, 0);
	}

	public int getMap() {
		return map;
	}

	public void tick() {
		if (!lockWalking) {
			if (xPosition == -TILE_WIDTH * 20 && yPosition == TILE_WIDTH * -18 && facing == 3)
				enterHome = true;
			if (keys.get(0).isTappedDown()) {
				facing = 3;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(1).isTappedDown()) {
				facing = 0;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(2).isTappedDown()) {
				facing = 1;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(3).isTappedDown()) {
				facing = 2;
				animationTick = 0;
				animationPointer = 0;
			}
		}
		if (!lockWalking) {
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
		if (lockWalking) {
			screen.blit(player[turning][animationPointer], (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16, TILE_WIDTH, TILE_HEIGHT);
		} else
			screen.blit(player[facing][0], (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16, TILE_WIDTH, TILE_HEIGHT);
	}

	private boolean restrictedTile(int x, int y) {
		for (Point p : restrictedX)
			if (x == p.getY() && y == p.getX())
				return true;
		return false;
	}

	// perimeter squares
	private void loadRestrictions() {
		restrictedX.add(new Point(-21 * TILE_WIDTH, -18 * TILE_WIDTH));
		restrictedX.add(new Point(-20 * TILE_WIDTH, -18 * TILE_WIDTH));
		restrictedX.add(new Point(-19 * TILE_WIDTH, -18 * TILE_WIDTH));
		// restrictedX.add(new Point(-18*TILE_WIDTH,-18*TILE_WIDTH));
		restrictedX.add(new Point(-18 * TILE_WIDTH, -19 * TILE_WIDTH));
		restrictedX.add(new Point(-18 * TILE_WIDTH, -20 * TILE_WIDTH));

	}

	private void handleMovement() {
		if (lockWalking) {
			stepTracker = true;
			if (xPosition > -TILE_WIDTH * 21 && xPosition < TILE_WIDTH * 22 && !restrictedTile(xPosition, yPosition))
				xPosition += xAccel;
			else {
				if ((xPosition == -TILE_WIDTH * 21 || restrictedTile(xPosition, yPosition)) && xAccel > 0)
					xPosition += xAccel;
				else if (xPosition == TILE_WIDTH * 22 && xAccel < 0)
					xPosition += xAccel;
			}
			if (yPosition > -TILE_WIDTH * 20 && yPosition < TILE_WIDTH * 19 && !restrictedTile(xPosition, yPosition))
				yPosition += yAccel;
			else {
				if ((yPosition == -TILE_WIDTH * 20 || restrictedTile(xPosition, yPosition)) && yAccel > 0)
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
				steps--;
				stepTracker = false;
			}
			xAccel = 0;
			yAccel = 0;
			turning = facing;
		} else
			lockWalking = true;
	}
}