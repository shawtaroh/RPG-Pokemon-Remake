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
import java.util.Random;

import javax.swing.JOptionPane;

import graphics.BitMap;
import maps.Map;

public class Player implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7403201549575621783L;
	private ArrayList<Key> keys;
	private int xPosition = -TILE_WIDTH * 0;
	private int yPosition = -TILE_HEIGHT * 0;
	private int xLastPosition = -TILE_WIDTH * 0;
	private int yLastPosition = -TILE_HEIGHT * 0;
	private int map;
	private boolean stepTracker = false;
	private static boolean enterHome = false;
	private Inventory myBag;
	private int hp = 100;
	private boolean hasAxe=true;
	private boolean[][] maze;

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
	private int winCondition;
	private boolean justFoundItem = false;

	boolean lockWalking;

	private static Player uniqueInstance;

	public static Player getInstance(ArrayList<Key> keys, int map, int winCondition) {
		if (uniqueInstance == null)
			uniqueInstance = new Player(keys, map, winCondition);
		return uniqueInstance;
	}

	public Player(ArrayList<Key> keys, int map, int winCondition) {
		this.keys = keys;
		this.map = map;
		this.winCondition = winCondition;
		loadRestrictions();
		player = BitMap.cut("/art/player/player.png", 64, 64, 0, 0);
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
			if (xPosition == -TILE_WIDTH * 20 && yPosition == TILE_WIDTH * -18 && facing == 3) {
				// enterHome = true;
				// iteration 2
			}
			if (keys.get(0).isTappedDown()) {// up
				facing = 3;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(1).isTappedDown()) {// down
				facing = 0;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(2).isTappedDown()) {// left
				facing = 1;
				animationTick = 0;
				animationPointer = 0;
			} else if (keys.get(3).isTappedDown()) {// right
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
		if (lockWalking) {
			screen.blit(player[turning][animationPointer], (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16, TILE_WIDTH, TILE_HEIGHT);
		} else
			screen.blit(player[facing][0], (screen.getWidth() - TILE_WIDTH * 2) / 2 + xPosition,
					(screen.getHeight() - TILE_HEIGHT) / 2 + yPosition + 16, TILE_WIDTH, TILE_HEIGHT);
	}

	private boolean perimeterTile(int x, int y) {
		for (Point p : restrictedX)
			if ((x == p.getY() && Math.abs(p.getX() - y) <= 64) || (y == p.getX() && Math.abs(p.getY() - x) <= 64)) {
				if (x == p.getY()) {
					if ((y > p.getX()) && facing == 3)
						return true;
					if ((y < p.getX()) && facing == 0)
						return true;
				} else {
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
		restrictedX.add(new Point(-10 * TILE_WIDTH, -9 * TILE_WIDTH));
		restrictedX.add(new Point(-10 * TILE_WIDTH, -10 * TILE_WIDTH));
		restrictedX.add(new Point(-10 * TILE_WIDTH, -11 * TILE_WIDTH));
		restrictedX.add(new Point(-9 * TILE_WIDTH, -9 * TILE_WIDTH));
		restrictedX.add(new Point(-9 * TILE_WIDTH, -10 * TILE_WIDTH));
		restrictedX.add(new Point(-9 * TILE_WIDTH, -11 * TILE_WIDTH));
		Random generator = new Random(420);
		for (int i = 0; i < 41; i++)
			for (int j = 0; j < 45; j++)
				if (generator.nextDouble() > .97)
					restrictedX.add(new Point((j - 21) * TILE_WIDTH, (i - 22) * TILE_WIDTH));
		maze = new model.MazeGenerator().randomMaze();

		for (int i = 22; i < 38; i++)
			for (int j = 6; j < 38; j++) {
				Point tmp1=new Point((j - 21) * TILE_WIDTH, (i - 22) * TILE_WIDTH);
				Point tmp2=new Point((i - 21) * TILE_WIDTH, (j - 22) * TILE_WIDTH);
				restrictedX.remove(tmp1);
				restrictedX.remove(tmp2);
				if (maze[j-6][i-22]){
					restrictedX.add(tmp1);
					restrictedX.add(tmp2);
				}
			}
		
		for(int i=7;i<18;i++)
			for(int j=9;j<15;j++){
				if((i==7||i==17||j==9)&&j!=14){
					restrictedX.add(new Point((j - 21) * TILE_WIDTH, (i - 22) * TILE_WIDTH));
				}
			}
	}

	private void handleMovement() {
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
			if (stepTracker && winCondition == 0) {
				// if corrupted save state doesnt save steps
				if (steps > 500)
					steps = 500;
				if (Math.abs(xLastPosition - xPosition) > 10 || Math.abs(yLastPosition - yPosition) > 10) {
					steps--;
					xLastPosition = xPosition;
					yLastPosition = yPosition;
				}
				stepTracker = false;
			}
			xAccel = 0;
			yAccel = 0;
			turning = facing;
		} else
			lockWalking = true;
	}

	public void useAxe() {
		if(this.facing==0){//down	
			System.out.println((this.yPosition - 21 * TILE_WIDTH)+"," +(this.xPosition - 22 * TILE_WIDTH));
			Point tmp1=new Point(this.yPosition - 21 * TILE_WIDTH-64, this.xPosition - 22 * TILE_WIDTH);
			Point tmp2=new Point(this.xPosition - 21 * TILE_WIDTH, this.yPosition - 22 * TILE_WIDTH-64);
			Point remove = null;
			for (Point p : restrictedX)
				if ((xPosition == p.getY() && yPosition-p.getX() <= 64)) 
					remove=p;
			if(restrictedX.remove(remove))
				System.out.println("Noice");
			for(Point p: restrictedX)
				if(p.getY()==(this.yPosition - 21 * TILE_WIDTH-64)&p.getX()==(this.xPosition - 22 * TILE_WIDTH))
					System.out.println("Weird");
					if(restrictedX.remove(tmp1))
						System.out.println("REMOVED");
					if(restrictedX.remove(tmp2))
						System.out.println("REMOVED");
		}

		if(this.facing==1){//right
			
		}
		if(this.facing==2){//left
			
		}
		if(this.facing==3){//up
			
		}
		
	}

	public int getFacing() {
		return facing;
	}

}