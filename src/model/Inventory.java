package model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

/*
							Inventory.java
                                  ,'\
    _.----.        ____         ,'  _\   ___    ___     ____
_,-'       `.     |    |  /`.   \,-'    |   \  /   |   |    \  |`.
\      __    \    '-.  | /   `.  ___    |    \/    |   '-.   \ |  |
 \.    \ \   |  __  |  |/    ,','_  `.  |          | __  |    \|  |
   \    \/   /,' _`.|      ,' / / / /   |          ,' _`.|     |  |
    \     ,-'/  /   \    ,'   | \/ / ,`.|         /  /   \  |     |
     \    \ |   \_/  |   `-.  \    `'  /|  |    ||   \_/  | |\    |
      \    \ \      /       `-.`.___,-' |  |\  /| \      /  | |   |
       \    \ `.__,'|  |`-._    `|      |__| \/ |  `.__,'|  | |   |
        \_.-'       |__|    `-._ |              '-.|     '-.| |   |
                                `'                            '-._|


@authors  
Eric Evans
Joey McClanahan
Matt Shaffer
Shawtaroh Granzier-Nakajima

@description
CS 335 Final Project
Implements Pokemon SafariZone inventory object (manages three items)
*/

public class Inventory implements Serializable{

	private TreeMap<String, ItemList> items;
	
	public Inventory() {
		items = new TreeMap<String, ItemList>();
		items.put("Safari Balls", new SafariBalls(30));
		items.put("Potions", new Potions(0));
		items.put("Snacks", new Snacks(0));

	}

	public int getNumItem(String name){
		return items.get(name).getQuantity();
	}

	public void addItems(String name, int amount) {
		items.get(name).add(amount);
	}
	
	public boolean useItem(String name){
		return items.get(name).decrement();
	}

	public boolean isItemMenuUsable(String name){
		return items.get(name).isMenuUsable();
	}

	public boolean isItemBattleUsable(String name){
		return items.get(name).isBattleUsable();
	}
	
	public String getItemNameAtLocation(int index){
		Collection<ItemList> list = items.values();
		return list.toArray()[index].toString();
	}
	
	public int getNumItems(){
		return items.size();
	}

	
}
