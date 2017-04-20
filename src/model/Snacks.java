package model;

/*
@authors  
Eric Evans
Joey McClanahan
Matt Shaffer
Shawtaroh Granzier-Nakajima

@description
CS 335 Final Project
This is an item class that allows the game to create edible snacks which
increase the players available moves.
*/

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
