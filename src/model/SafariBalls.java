package model;

public class SafariBalls extends ItemList{

	public SafariBalls(int quantity) {
		super(quantity);
	}

	@Override
	public boolean menuUsable() {
		return false;
	}

	@Override
	public boolean battleUsable() {
		return true;
	}

}
