package model;

public class RPoint extends java.awt.Point {

	private boolean removable;

	public RPoint(int x, int y, boolean removable) {
		super(x,y);
		this.removable = removable;
	}

	public boolean isRemovable() {
		return removable;
	}

	public void setRemovable(boolean removable) {
		this.removable = removable;
	}

}
