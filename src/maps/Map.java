package maps;

import graphics.BitMap;
import model.Player;

public abstract class Map {
	
	protected final int width;
	protected final int height;
	private Player myPlayer;
	protected static int TILE_WIDTH = 64;
	protected static int TILE_HEIGHT = 64;
	
	
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

		renderTiles(screen, gridX0, gridY0, gridX1, gridY1);

		myPlayer.render(screen);
	}
	
	public abstract void renderTiles(BitMap screen, int x0, int y0, int x1, int y1);
}
