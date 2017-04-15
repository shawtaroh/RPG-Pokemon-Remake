package model;

public class SafariBalls extends ItemList{

	public SafariBalls(int quantity) {
		super(quantity);
	}

	@Override
	public boolean isMenuUsable() {
		return false;
	}

	@Override
	public boolean isBattleUsable() {
		return true;
	}
	
	@Override
	public String toString(){
		return "Safari Balls";
	}

	@Override
	public Effect getEffect() {
		return null;
	}

	@Override
	public int getEffectAmount() {
		return 0;
	}
}
