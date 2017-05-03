package model;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

import graphics.BitMap;

/*
							Pokedex.java
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
Implements Pokemon SafariZone pokedex object (list of many pokemon)
*/

public class Pokedex {

	// Types
	final String Rare = "Rare";
	final String Common = "Common";
	final String Uncommon = "Uncommon";

	private ArrayList<Pokemon> pokedex;

	public Pokedex() {
		Random generator = new Random();
		pokedex = new ArrayList<>();
		File[] files = new File(Pokedex.class.getResource("/art/pokemon/").getFile()).listFiles();
		int pokeNum = 1;

		for (File file : files) {
			try {
				// consistent random types with ratios 6:3:1 like in the spec
				double rand = generator.nextDouble();
				String rarity;
				if (rand > .4)
					rarity = Common;
				else if (rand < .3)
					rarity = Uncommon;
				else
					rarity = Rare;
				String name = file.getName().replaceFirst(".gif", "");
				name = name.substring(0, 1).toUpperCase() + name.substring(1);
				pokedex.add(new Pokemon(pokeNum, name, ImageIO.read(file), rarity));
				pokeNum++;
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public Pokemon getRandomPokemon(){
		Random generator = new Random();
		Pokemon pokemon = pokedex.get(generator.nextInt(pokedex.size()));
		return new Pokemon(pokemon.getPokeNumber(), pokemon.getName(), pokemon.getSprite(), pokemon.getType());
	}
}
