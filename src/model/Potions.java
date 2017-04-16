package model;

import java.io.Serializable;

public class Potions extends ItemList implements Serializable{

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

	@Override
	public Effect getEffect() {
		return Effect.HEALING;
	}

	@Override
	public int getEffectAmount() {
		return 50;
	}
	
}
