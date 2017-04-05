package model;

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
Implements Pokemon SafariZone Player, manages player specific information
*/

import java.util.ArrayList;

import driver.Key;
import graphics.BitMap;
import maps.Map;

public class Player {

	private ArrayList<Key> keys;
	public int xPosition = -TILE_WIDTH * 10;
	public int yPosition = -TILE_HEIGHT * 10;
	private static int TILE_WIDTH = 64;
	private static int TILE_HEIGHT = 64;
	int facing = 0;
	int turning = 0;
	byte animationTick = 0;
	byte animationPointer = 0;
	private static BitMap[][] player;
	int testValue = 0;

	int xAccel;
	int yAccel;

	boolean lockWalking;

	public Player(ArrayList<Key> keys) {
		this.keys = keys;
		player = BitMap.cut("/art/player/player.png", 64, 64, 0, 0);
	}

	public void setCenter(BitMap screen) {
	}

	public void initialize(Map world) {

	}

	public void tick() {
		if (!lockWalking) {
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
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition, TILE_WIDTH, TILE_HEIGHT);
		} else
			screen.blit(player[facing][0], (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition, TILE_WIDTH, TILE_HEIGHT);

	}

	private void handleMovement() {
		if (lockWalking) {
			xPosition += xAccel;
			yPosition += yAccel;
		}
		if (xPosition % TILE_WIDTH == 0 && yPosition % TILE_HEIGHT == 0) {
			lockWalking = false;
			xAccel = 0;
			yAccel = 0;
			turning = facing;
		} else
			lockWalking = true;
	}
}
