package model;

import java.util.ArrayList;

import graphics.BitMap;

public class NPC extends Trainer{

	public NPC(ArrayList<Key> keys, int map, int winCondition) {
		super(keys, map, winCondition);
		this.player = BitMap.cut("/art/player/player.png", 64, 64, 0, 0);
	}
	
	public void tick() {
		if (!lockWalking) {
			if (xPosition == -TILE_WIDTH * 20 && yPosition == TILE_WIDTH * -18 && facing == 3) {
				// enterHome = true;
				// iteration 2
			}
			if (Math.random()>.05) {// up
				facing = 3;
				animationTick = 0;
				animationPointer = 0;
			} else if (Math.random()>.05) {// down
				facing = 0;
				animationTick = 0;
				animationPointer = 0;
			} else if (Math.random()>.05) {// left
				facing = 1;
				animationTick = 0;
				animationPointer = 0;
			} else if (Math.random()>.05) {// right
				facing = 2;
				animationTick = 0;
				animationPointer = 0;
			}
		}
		if (!lockWalking && !perimeterTile(xPosition, yPosition)) {
			if (Math.random()>.25) {
				if (facing == 3)
					yAccel = yAccel - 2;
				lockWalking = true;
			}
			else if (Math.random()>.25) {
				if (facing == 0)
					yAccel = yAccel + 2;
				lockWalking = true;
			}
			else if (Math.random()>.25) {
				if (facing == 1)
					xAccel = xAccel - 2;
				lockWalking = true;
			}
			else if (Math.random()>.25) {
				if (facing == 2)
					xAccel = xAccel + 2;
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

}
