package model;

public class Potions extends ItemList{

	public Potions(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean menuUsable() {
		return true;
	}

	@Override
	public boolean battleUsable() {
		return false;
	}

}
