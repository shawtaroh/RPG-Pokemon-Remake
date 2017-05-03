package model;
/*
							Loader.java
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
* 
* 
* @authors
* Eric Evans
* Joey McClanahan
* Matt Shaffer
* Shawtaroh Granzier-Nakajima
* 
* @description
* CS 335 Final Project
* Example of Loading GIFS.
*/

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import javax.imageio.ImageIO;

public class Loader {

	public Loader() {

		// Types
		final String Rare = "Rare";
		final String Common = "Common";
		final String Uncommon = "Uncommon";

		Random generator = new Random(314);
		ArrayList<Pokemon> pokedex = new ArrayList<>();
		File[] files = new File(Pokedex.class.getResource("/art/pokemon").getFile()).listFiles();
		int pokeNum = 1;
		for (File file : files) {
				// consistent random types with ratios 6:3:1 like in the spec
				double rand = generator.nextDouble();
				if (rand > .4)
					pokedex.add(new Pokemon(pokeNum, file.getName(), Common));
				if (rand < .3)
					pokedex.add(new Pokemon(pokeNum, file.getName(), Uncommon));
				else
					pokedex.add(new Pokemon(pokeNum, file.getName(), Rare));
		}
		for (Pokemon p : pokedex) {
			// System.out.println(p.getName());
		}
	}
}