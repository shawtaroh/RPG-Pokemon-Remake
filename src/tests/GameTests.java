package tests;
import static org.junit.Assert.*;

import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.Test;

import junit.framework.*;
import maps.Map;
import model.Effect;
import model.Inventory;
import model.ItemList;
import model.Key;
import model.Loader;
import model.Player;
import model.Pokedex;
import model.Pokemon;
import model.PokemonGame;
import model.Potions;
import model.SafariBalls;

public class GameTests {

	@Test
	   public void testFullGame() {	
	      PokemonGame game1 = new PokemonGame(0,0);
	      PokemonGame game2 = new PokemonGame(0,1);
	      PokemonGame game3 = new PokemonGame(1,0);
	      PokemonGame game4 = new PokemonGame(1,1);
	      Player player = game1.getPlayer();
	      Map world = game1.getWorld();
	      game1.setPlayer(player);
	      ArrayList<Key> keys = game1.getKeys();
	      assertTrue(player != null);
	      assertTrue(keys != null);
	      assertTrue(world != null);
	      int X = game1.getPlayerXPos();
	      int Y = game1.getPlayerYPos();
	      //game2.invertory();
	   }
	@Test
	   public void testBalls() {	
	      SafariBalls balls = new SafariBalls(50);
	      assertTrue(balls.isMenuUsable() == false);
	      assertTrue(balls.isBattleUsable() == true);
	      assertTrue(balls.toString() == "Safari Balls");
	      assertTrue(balls.getEffect() == null);
	      assertTrue(balls.getEffectAmount() == 0);
	   }
	@Test
	   public void testPotions() {	
		  Potions potions = new Potions(50);
	      assertTrue(potions.isMenuUsable() == true);
	      assertTrue(potions.isBattleUsable() == false);
	      assertTrue(potions.toString() == "Potions");
	      assertTrue(potions.getEffect() == Effect.HEALING);
	      assertTrue(potions.getEffectAmount() == 50);  
	      potions.add(50);
	      assertTrue(potions.getQuantity() == 100);  
	      assertTrue(potions.decrement() == true);  
	      assertTrue(potions.getQuantity() == 99);  
	      for(int i = 0; i < 99; i++){
		      potions.decrement();
	      }
	      assertTrue(potions.decrement() == false);
	   }
	@Test
	   public void testKey() {
	      Key key = new Key("up");
	      key.tick();
	      key.setTapped(true);
	      key.tick();
	      key.setTappedDown(true);
	      key.setPressedDown(true);
	      key.setNextState(true);
	      assertTrue(key.isNextState() == true);
	      assertTrue(key.isTapped() == true);
	      assertTrue(key.isTappedDown() == true);
	      assertTrue(key.isPressedDown() == true);
	      assertTrue(key.getKey_code() == KeyEvent.VK_UP);
	   }
	
	@Test
	   public void testPokedex() {
	      Pokedex pokedex = new Pokedex();
	      
	   }
	
	@Test
	   public void testPlayer() {
	      ArrayList<Key> keys = new ArrayList<Key>();
	      keys.add(new Key("up"));
	      keys.add(new Key("down"));
	      keys.add(new Key("left"));
	      keys.add(new Key("right"));
	      Player player = new Player(keys, 0, 0);
	      //player.lock
	      keys.get(0).setTappedDown(true);
	      player.tick();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(true);
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();
	      keys.get(0).setTappedDown(true);
	      player.tick();
	      keys.get(1).setTappedDown(false);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(true);
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();
	   }
	
	@Test
	   public void testLoader() {
		Loader loader = new Loader();
	}
	
	@Test
	   public void testPokemon() {
		BufferedImage aNull = new BufferedImage(10, 10, 10);
		Pokemon pokemon = new Pokemon(0,"Guy",aNull,"CoolGuy");
		pokemon.setCurrentHP(100);
		assertTrue(pokemon.getPokeNumber() == 0);
		assertTrue(pokemon.getName() == "Guy");
		assertTrue(pokemon.getSprite() == aNull);
		int x =  pokemon.getMaxHP();
		Double prob = pokemon.getProbabilityToRun();
		assertTrue(pokemon.getCurrentHP() == 100);
		assertTrue(pokemon.getType() == "CoolGuy");
	}
	@Test
	   public void testInventory() {
		Inventory inventory = new Inventory();
		inventory.addItems("Safari Balls", 100);
		assertTrue(inventory.getNumItem("Safari Balls") == 130);
		inventory.useItem("Safari Balls");
		inventory.isItemMenuUsable("Safari Balls");
		inventory.isItemBattleUsable("Safari Balls");
		assertTrue(inventory.getNumItems() > 0);
		inventory.getItemNameAtLocation(0);
	}
}
