package model;

import java.util.Collections;
import java.util.Random;
import java.util.Arrays;

/*
 * recursive backtracking algorithm
 * shamelessly borrowed from the ruby at
 * http://weblog.jamisbuck.org/2010/12/27/maze-generation-recursive-backtracking
 * Significantly modified though! had to change formatting quite a bit, but algorithm itself is borrowed
 */
public class MazeGenerator {
	private int x;
	private int y;
	private int[][] maze;
	private static String[] stringMap;
	private Random generator = new Random(420);

	private static boolean[][] realMap = new boolean[32][16];

	public MazeGenerator(int x, int y) {
		this.x = x;
		this.y = y;
		maze = new int[this.x][this.y];
		generateMaze(0, 0);
	}

	public MazeGenerator() {
		// TODO Auto-generated constructor stub
	}

	public boolean[][] randomMaze() {
		realMap = new boolean[32][16];
		stringMap = new String[32];
		MazeGenerator maze = new MazeGenerator(8, 16);
		maze.display();
		return realMap;
	}

	public void display() {
		for (int i = 0; i < y; i++) {
			// draw the north edge
			for (int j = 0; j < x; j++) {
				if (stringMap[2 * i] == null)
					stringMap[2 * i] = "";
				stringMap[2 * i] += (maze[j][i] & 1) == 0 ? "XX" : "XO";
			}
			// draw the west edge
			for (int j = 0; j < x; j++) {
				if (stringMap[2 * i + 1] == null)
					stringMap[2 * i + 1] = "";
				stringMap[2 * i + 1] += (maze[j][i] & 8) == 0 ? "XO" : "OO";
			}
		}

		// for (String s : stringMap)
		// System.out.println(s);

		int j = 0;
		for (String s : stringMap) {
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				if (c == 'O')
					realMap[j][i] = true;
				else
					realMap[j][i] = false;

			}
			j++;
		}

		for (boolean b[] : realMap) {
			for (boolean c : b) {
				//if (c)
					//System.out.print("O");
				//else
					//System.out.print("X");
			}
			//System.out.println("");

		}
	}

	private void generateMaze(int cx, int cy) {
		DIR[] dirs = DIR.values();
		// Collections.shuffle(Arrays.asList(dirs), new Random(420));
		Collections.shuffle(Arrays.asList(dirs), generator);
		for (DIR dir : dirs) {
			int nx = cx + dir.dx;
			int ny = cy + dir.dy;
			if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
				maze[cx][cy] |= dir.bit;
				maze[nx][ny] |= dir.opposite.bit;
				generateMaze(nx, ny);
			}
		}
	}

	private static boolean between(int v, int upper) {
		return (v >= 0) && (v < upper);
	}

	private enum DIR {
		N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
		private final int bit;
		private final int dx;
		private final int dy;
		private DIR opposite;

		// use the static initializer to resolve forward references
		static {
			N.opposite = S;
			S.opposite = N;
			E.opposite = W;
			W.opposite = E;
		}

		private DIR(int bit, int dx, int dy) {
			this.bit = bit;
			this.dx = dx;
			this.dy = dy;
		}
	};

}