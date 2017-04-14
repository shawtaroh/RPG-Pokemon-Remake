package model;

public class Potions extends ItemList{

	public Potions(int quantity) {
		super(quantity);
		// TODO Auto-generated constructor stub
	}

	@Override
	public boolean isMenuUsable() {
		return true;
	}

	@Override
	public boolean isBattleUsable() {
		return false;
	}

	@Override
	public String toString(){
		return "Potions";
	}
}
