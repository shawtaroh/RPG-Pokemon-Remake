package model;

import java.io.Serializable;

public abstract class ItemList implements Serializable {
	private int quantity;

	public ItemList(int quantity) {
		this.quantity = quantity;
	}

	public int getQuantity() {
		return quantity;
	}

	public void add(int amount) {
		quantity += amount;
	}

	public boolean decrement() {
		if (quantity > 0) {
			quantity--;
			return true;
		}

		return false;
	}

	public abstract boolean isMenuUsable();

	public abstract boolean isBattleUsable();

	public abstract Effect getEffect();

	public abstract int getEffectAmount();

}
