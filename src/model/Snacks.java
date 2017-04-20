package model;

public class Snacks extends ItemList {

	public Snacks(int quantity) {
		super(quantity);
	}

	@Override
	public boolean isMenuUsable() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isBattleUsable() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Effect getEffect() {
		return Effect.WALKDISTANCE;
	}

	@Override
	public int getEffectAmount() {
		return 50;
	}

}
