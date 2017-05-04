package tests;
import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import org.junit.Test;

import com.sun.glass.events.KeyEvent;

import graphics.BitMap;
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
import model.RPoint;
import model.SafariBalls;
import model.Snacks;

/*
							ModelTests.java
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
This is a testing class that tests all classes that are part of the model
*/

public class ModelTests {

	@Test
	   public void testFullGame() {	
	      PokemonGame game1 = new PokemonGame(0,0);
	      PokemonGame game2 = new PokemonGame(0,1);
	      PokemonGame game3 = new PokemonGame(1,0);
	      PokemonGame game4 = new PokemonGame(1,1);
	      game1.changeWorld(99);
	      game1.changeWorld(1);
	      game1.changeWorld(2);
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
	      player.setSpeed(100);
	      player.useAxe();
	      player.changeWorld(1);
	      player.changeWorld(999);
	      player.changeWorld(2);
	      player.setMyPokemon(player.getMyPokemon());
	      player.getFacing();
	      player.isHasAxe();
	      player.setHasAxe(true);
	      player.setHP(10);
	      player.useAxe();
	      player.setEnterHome(false);
	      player.setxLastPosition(50);
	      player.useAxe();
	      player.setyLastPosition(50);
	      player.setxPosition(40);
	      player.setyPosition(40);
	      player.useAxe();
	      player.setSteps(500);
	      assertTrue(player.getHP() >= 0);
	      assertTrue(player.getSteps() >= 0);
	      assertTrue(player.getxLastPosition() == 50);
	      assertTrue(player.getyLastPosition() == 50);
	      assertTrue(player.isEnterHome() == false);
	      assertTrue(player.getMyBag() != null);
	      assertTrue(player.getMyBag() != null);
	      assertTrue(player.getInstance(keys, 0,0) != null);
	      player.setWinCondition(0);
	      assertTrue(player.getWinCondition() == 0);
	      assertTrue(player.getMap() == 2);
	      player.setLockWalking(true);
	      BitMap screen = new BitMap(500, 500);
	      player.render(screen);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.render(screen);
	      keys.get(0).setTappedDown(true);
	      player.setLockWalking(false);
	      player.tick();
	      player.handleMovement();
	      player.useAxe();
	      keys.get(0).setTappedDown(false);
	      keys.get(2).setTappedDown(true);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.tick();
	      player.handleMovement();
	      player.useAxe();
	      keys.get(2).setTappedDown(false);
	      keys.get(1).setTappedDown(true);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.tick();
	      keys.get(1).setTappedDown(false);
	      keys.get(3).setTappedDown(true);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.tick();
	      keys.get(0).setPressedDown(true);
	      player.setLockWalking(false);
	      player.tick();
	      keys.get(0).setPressedDown(false);
	      keys.get(2).setPressedDown(true);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.tick();
	      keys.get(2).setPressedDown(false);
	      keys.get(1).setPressedDown(true);
	      player.setLockWalking(false);
	      player.tick();
	      keys.get(1).setPressedDown(false);
	      keys.get(3).setPressedDown(true);
	      player.handleMovement();
	      player.useAxe();
	      player.setLockWalking(false);
	      player.tick();
	      player.setxPosition(1344);
	      player.setyPosition(1344);
	      player.setLockWalking(false);
	      keys.get(2).setTappedDown(false);
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(0).setTappedDown(true);
	      player.tick();
	      keys.get(1).setTappedDown(false);
	      player.tick();
	      keys.get(2).setTappedDown(true);player.handleMovement();
	      player.useAxe();
	      player.tick();
	      keys.get(3).setTappedDown(true);player.handleMovement();
	      player.useAxe();
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();
	      player.setxPosition(-1344);
	      player.setyPosition(-1344);
	      player.setLockWalking(false);
	      keys.get(2).setTappedDown(false);
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(0).setTappedDown(true);
	      player.tick();
	      keys.get(1).setTappedDown(false);
	      player.tick();
	      keys.get(2).setTappedDown(true);player.handleMovement();
	      player.useAxe();
	      player.tick();
	      keys.get(3).setTappedDown(true);player.handleMovement();
	      player.useAxe();
	      player.tick();
	      keys.get(0).setTappedDown(false);
	      player.tick();player.handleMovement();
	      player.useAxe();
	      keys.get(1).setTappedDown(true);
	      player.tick();
	      keys.get(2).setTappedDown(true);
	      player.tick();
	      keys.get(3).setTappedDown(false);
	      player.tick();
	      player.useAxe(); 
	      //player.getMyBag().addItems("Snack", 100);
	      //player.eatSnack();
	   }
	
	@Test
	   public void testLoader() {
		Loader loader = new Loader();
	}
	
	@Test
	   public void testPokemon() {
		BufferedImage aNull = new BufferedImage(10, 10, 10);
		Pokemon pokemon = new Pokemon(0,"abra","abra");
		pokemon.setCurrentHP(100);
		assertTrue(pokemon.getPokeNumber() == 0);
		assertTrue(pokemon.getName() == "abra");
		assertTrue(pokemon.getSprite() != aNull);
		int x =  pokemon.getMaxHP();
		Double prob = pokemon.getProbabilityToRun();
		assertTrue(pokemon.getCurrentHP() == 100);
		assertTrue(pokemon.getType() == "abra");
		pokemon.giveBait();
		pokemon.throwRock();
		pokemon.checkIfRuns();
		pokemon.throwBall();
		pokemon.throwRock();
		pokemon.throwRock();
		pokemon.throwBall();
		pokemon.checkIfRuns();
		pokemon.throwRock();
		pokemon.throwBall();
		pokemon.checkIfRuns();
		pokemon.throwRock();
		pokemon.checkIfRuns();
		pokemon.throwBall();
		pokemon.throwRock();
		pokemon.checkIfRuns();
		pokemon.throwRock();
		pokemon.throwBall();
		pokemon.checkIfRuns();
		pokemon.throwRock();
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
	
	@Test
	   public void testSnacks() {
		Snacks snack = new Snacks(100);
		assertTrue(snack.isMenuUsable());
		assertFalse(snack.isBattleUsable());
		assertFalse(snack.getEffect() == null);
		assertFalse(snack.getEffectAmount() != 50);
	}
	
	@Test
	   public void testMaps() {
		ArrayList<Key> keys = new ArrayList<Key>();
	      keys.add(new Key("up"));
	      keys.add(new Key("down"));
	      keys.add(new Key("left"));
	      keys.add(new Key("right"));
		Player player = new Player(keys, 0, 0);
	}
	
	@Test 
		public void testRPoint(){
		RPoint r = new RPoint(0, 0, false);
		r.setRemovable(true);
		assertTrue(r.isRemovable() == true);
		
		
	}
}
