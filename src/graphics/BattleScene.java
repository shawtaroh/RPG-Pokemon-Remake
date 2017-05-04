/*
 * BattleScene.java
 * ,'\
 * _.----. ____ ,' _\ ___ ___ ____
 * _,-' `. | | /`. \,-' | \ / | | \ |`.
 * \ __ \ '-. | / `. ___ | \/ | '-. \ | |
 * \. \ \ | __ | |/ ,','_ `. | | __ | \| |
 * \ \/ /,' _`.| ,' / / / / | ,' _`.| | |
 * \ ,-'/ / \ ,' | \/ / ,`.| / / \ | |
 * \ \ | \_/ | `-. \ `' /| | || \_/ | |\ |
 * \ \ \ / `-.`.___,-' | |\ /| \ / | | |
 * \ \ `.__,'| |`-._ `| |__| \/ | `.__,'| | | |
 * \_.-' |__| `-._ | '-.| '-.| | |
 * `' '-._|
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
 * Implements BattleScene with options for player to attempt to capture Pokemon
 */

package graphics;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.Serializable;
import java.net.MalformedURLException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

/*
						BattleScene.java
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
Implements Pokemon SafariZone BattleScene GUI
*/

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.border.LineBorder;

import driver.GameGUI;
import driver.GameGUI.ObjectWaitingForSongToEndFinal;
import model.Player;
import model.Pokedex;
import model.Pokemon;
//import graphics.InGameMenu.MenuListener;
//import graphics.InGameMenu.MenuListener;
import model.PokemonGame;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class BattleScene extends JPanel {
	
	private PokemonGame	model;
	private GameGUI		gui;
	private Pokemon pokemon;
	
	private JButton		pokeball;
	private JButton		rock;
	private JButton		run;
	private JButton		bait;
	
	private JLabel      playerStats2;
	private JLabel      playerStats1;
	private JLabel      pokemonStats2;
	private JLabel      pokemonStats1;
	private JLabel 		gameStatus;
	
	private JPanel 		container;
	private JPanel 		pokemonPanel;
	private JPanel 		playerPanel;
	private JPanel 		buttonPanel;
	
	private String		backgroundPNG	= "/art/battleground.png";
	
	private String		mapStr;
	private String		stepsStr;
	private String		hpStr;
	private String		safariballStr;
	private String		potionStr;
	private String		snacksStr;
	
	private Boolean isThrown, willRun, isCaught;
	
	private ImageIcon playerThrow;
	private JLabel pokemonStats3;
	private JLabel pokemonStats4;
	private JLabel pokemonStats5;
	
	private ObjectWaitingForSongToEndFinal finalWaiter = new ObjectWaitingForSongToEndFinal();
	
	public class ObjectWaitingForSongToEndFinal implements EndOfSongListener, Serializable {

		public void songFinishedPlaying(EndOfSongEvent eosEvent) {

		}
	}
	
	public BattleScene(PokemonGame model, GameGUI gui) {
		super();
				
		this.model = model;
		this.gui = gui;
		this.pokemon = model.getRandomPokemon();
		this.isThrown = false;
		willRun = false;
		isCaught = false;
		
		repaint();
		
		setupButtonPanel();
		
		setupPlayerPanel();

		setupPokemonPanel();
		
		//if(model.getPlayer() != null)
		updateStats();
		
		setupContainer();
		this.add(container);
       
	}	
	
	public void updateStats(){
		//Set player stats
		int playerHP = model.getPlayer().getHP();
		int playerMultiplier = 0;
		if(playerHP != 0)
			playerMultiplier = (int) Math.floor(playerHP / 10);
		String stats2 = "    HP [";
		if(playerMultiplier != 0)
			stats2 += "+++++++++++++++".substring(0, playerMultiplier);
		stats2 += "---------------".substring(0, 10-playerMultiplier);
		stats2 += "]";
		String stats1 = "Player  " + playerHP + "/100          "; 
		playerStats1.setText(stats1);
		playerStats2.setText(stats2);
		
		//Set Pokemon stats
		int pokemonHP = pokemon.getCurrentHP();
		int pokemonMultiplier = 0;
		if(pokemonHP != 0)
			pokemonMultiplier = (int) Math.floor(playerHP / 10);
		String hpBar = "HP [";
		if (pokemonMultiplier != 0)
			hpBar += "+++++++++++++++".substring(0, pokemonMultiplier);
		hpBar += "---------------".substring(0, 10-pokemonMultiplier);
		hpBar += "]  " + pokemon.getCurrentHP() + "/" + pokemon.getMaxHP();;
		pokemonStats1.setText(pokemon.getName());
		pokemonStats2.setText(hpBar);
		pokemonStats3.setText(pokemon.getType());
		pokemonStats4.setText("Chance to Run: " + String.format("%.2f",pokemon.getProbabilityToRun() * 100) + "%");
		pokemonStats5.setText("Chance of Capture: " + String.format("%.2f",pokemon.getProbabilityToCapture() * 100) + "%");
		pokemonPanel.validate();

	}
@Override
public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);

		//Background
		Image bkgnd = new ImageIcon(this.getClass().getResource(backgroundPNG)).getImage();
		g2.drawImage(bkgnd, 0, 0, this.getWidth(), this.getHeight(), null, null);
		
		//Game Title
		Font fontHdr = new Font("Verdana", Font.BOLD, 80);
		FontMetrics metricsHdr = g2.getFontMetrics(fontHdr);
		g2.setColor(Color.WHITE);
		g2.setFont(fontHdr);
		String str = "Fight!";
		g2.drawString(str,
		        this.getWidth() / 2 - metricsHdr.stringWidth(str) / 2,
		        50 + metricsHdr.getHeight() / 2);
		
		//Draw Pokemon
		if(!willRun && !isCaught)
			g2.drawImage(new ImageIcon(GameGUI.class.getResource("/art/pokemon/" + pokemon.getName() + ".gif")).getImage(),this.getSize().width/2,this.getSize().height/2,this);
		
		playerThrow = new ImageIcon(GameGUI.class.getResource("/art/player/ballThrow.gif"));
		//Draw Player
		if(isThrown){
			g2.drawImage(playerThrow.getImage(), 30, this.getSize().height-300, this);
		}else{
			g2.drawImage(new ImageIcon(GameGUI.class.getResource("/art/player/playerBattle.png")).getImage(), 30, this.getSize().height-300, this);
		}
		
	}

	public void on() {
	
	this.setFocusable(true);
	this.setVisible(true);
	
	mapStr = model.getWorld().getName();
	
	stepsStr = "x " + model.getPlayer().getSteps();
	hpStr = "x " + model.getPlayer().getHP();
	safariballStr = "x "
	        + model.getPlayer().getMyBag().getNumItem("Safari Balls");
	potionStr = "x " + model.getPlayer().getMyBag().getNumItem("Potions");
	snacksStr = "x " + model.getPlayer().getMyBag().getNumItem("Snacks");
	
	repaint();
	}
	
	public void off() {
		
		this.setFocusable(false);
		this.setVisible(false);
	}
	
	private class ButtonListener implements ActionListener {
		private JButton button;
		
		public ButtonListener(JButton btn) {
			this.button = btn;
		}
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			willRun = pokemon.checkIfRuns(); //Done before to calculate probability based on current stats
			playerThrow.getImage().flush();
			SongPlayer.playFile(finalWaiter, Pokedex.class.getResource("/art/sounds/item.wav").toString().substring(6));
			if (button == rock) {
				isThrown = true;
				if (!pokemon.throwRock()){
					willRun = true;
					updateStats();
					repaint();
					gameStatus.setText("You threw a rock, " + pokemon.getName() + " has fainted.");
					JOptionPane.showMessageDialog(null, pokemon.getName() + " has fainted.");
					gui.resumeFromBattle();
					return;
				}
				else{
					gameStatus.setText("You threw a rock, smart move.");
				}
				updateStats();
			}
			if (button == bait) {
				pokemon.giveBait();
				isThrown = true;
				gameStatus.setText("You threw bait, smart move.");
				updateStats();
			}
			if (button == run) {
				//TODO: Implement Run
				updateStats();
				gameStatus.setText("You ran away, smart move.");
				gui.resumeFromBattle();
				return;
			}
			if (button == pokeball) {
				//TODO: Implement Pokeball Throw
				if(!model.getPlayer().getMyBag().useItem("Safari Balls")){
					JOptionPane.showMessageDialog(null, "You are out of Safari Balls!");
					gui.resumeFromBattle();
					return;
				}
				isThrown = true;
				if(pokemon.throwBall()){
					isCaught = true;
					updateStats();
					repaint();
					model.getPlayer().getMyPokemon().add(pokemon);
					gameStatus.setText("You have caught " + pokemon.getName() + "!");
					JOptionPane.showMessageDialog(null, "You have caught " + pokemon.getName() + "!");
					gui.resumeFromBattle();
					return;
				}
				else{
					gameStatus.setText("Throw was unsuccessful, try again");
				}
				updateStats();
			}

			if(willRun){
				updateStats();
				repaint();
				gameStatus.setText(pokemon.getName() + " has ran off.");
				JOptionPane.showMessageDialog(null, pokemon.getName() + " has ran off."); 
				gui.resumeFromBattle();
				return;
			}
			
			repaint();
		}
		
	}
	public void setupPokemonPanel(){
		//JPanel Setup
		pokemonPanel = new JPanel();
		pokemonPanel.setBackground(new Color(230, 230, 230, 0));
		pokemonPanel.setBorder(new LineBorder(new Color(255, 255, 255, 0), 10, true));
		
		//Setup Contents
		//JPanel spacePanel = new JPanel();
		//spacePanel.setBackground(new Color(230, 230, 230, 0));
		//spacePanel.setBorder(new LineBorder(new Color(255, 255, 255, 0), 10, true));
		//spacePanel.setPreferredSize(new Dimension(400,100));
		
		JPanel pokemonStatus = new JPanel();
		pokemonStatus.setBackground(new Color(230, 230, 230, 123));
		pokemonStatus.setBorder(new LineBorder(new Color(255, 255, 255, 123), 10, true));
		pokemonStatus.setPreferredSize(new Dimension(420,200));
		pokemonStatus.setLayout(new BoxLayout(pokemonStatus, BoxLayout.PAGE_AXIS));
		pokemonStats1 = new JLabel();
		pokemonStats1.setFont(new Font("Arial", Font.BOLD, 25));
		pokemonStats1.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokemonStats2 = new JLabel();
		pokemonStats2.setFont(new Font("Arial", Font.BOLD, 25));
		pokemonStats2.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokemonStats3 = new JLabel();
		pokemonStats3.setFont(new Font("Arial", Font.BOLD, 25));
		pokemonStats3.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokemonStats4 = new JLabel();
		pokemonStats4.setFont(new Font("Arial", Font.BOLD, 25));
		pokemonStats4.setAlignmentX(Component.CENTER_ALIGNMENT);
		pokemonStats5 = new JLabel();
		pokemonStats5.setFont(new Font("Arial", Font.BOLD, 25));
		pokemonStats5.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		
		pokemonStatus.add(pokemonStats1);
		pokemonStatus.add(pokemonStats2);
		pokemonStatus.add(pokemonStats3);
		pokemonStatus.add(pokemonStats4);
		pokemonStatus.add(pokemonStats5);
		
		//Add Contents
//		pokemonPanel.add(spacePanel);
		pokemonPanel.add(pokemonStatus);
		}
	
	public void setupPlayerPanel(){
		//JPanel Setup
		playerPanel = new JPanel();
		playerPanel.setBackground(new Color(230, 230, 230, 123));
		playerPanel.setBorder(new LineBorder(new Color(255, 255, 255, 123), 10, true));
		
		//Contents Setup
		JPanel playerStatus = new JPanel();
		playerStatus.setPreferredSize(new Dimension(320,100));
		playerStatus.setBackground(new Color(230, 230, 230, 123));
		playerStatus.setBorder(new LineBorder(new Color(255, 255, 255, 123), 10, true));
		playerStats1 = new JLabel("Player  ");
		playerStats1.setFont(new Font("Arial", Font.BOLD, 25));
		playerStats2 = new JLabel("It didn't work");
		playerStats2.setFont(new Font("Arial", Font.BOLD, 25));
		playerStatus.add(playerStats1);
		playerStatus.add(playerStats2);
		
		JButton buttonspace3 = new JButton("             ");
		buttonspace3.setOpaque(false);
		buttonspace3.setEnabled(false);
		buttonspace3.setContentAreaFilled(false);
		buttonspace3.setBorderPainted(false);
		buttonspace3.setFont(new Font("Arial", Font.BOLD, 25));
		
		JButton buttonspace4 = new JButton("             ");
		buttonspace4.setEnabled(false);
		buttonspace4.setOpaque(false);
		buttonspace4.setContentAreaFilled(false);
		buttonspace4.setBorderPainted(false);
		buttonspace4.setFont(new Font("Arial", Font.BOLD, 25));
		
		gameStatus = new JLabel("The battle begins, make your selection!");
		gameStatus.setFont(new Font("Arial", Font.BOLD, 20));
		gameStatus.setForeground(Color.BLACK);
		gameStatus.setBackground(new Color(230, 230, 230, 0)); 
		
		playerPanel.add(playerStatus);
		playerPanel.add(buttonspace3);
		playerPanel.add(buttonspace4);
		playerPanel.add(gameStatus);
		
	}
	
	public void setupButtonPanel(){
		//JPanel Setup
		buttonPanel = new JPanel();
		buttonPanel.setBackground(new Color(230, 230, 230, 123));
		buttonPanel.setBorder(new LineBorder(new Color(255, 255, 255, 123), 10, true));
		
		//Contents Setup
		JButton buttonspace1 = new JButton("     ");
		buttonspace1.setOpaque(false);
		buttonspace1.setEnabled(false);
		buttonspace1.setContentAreaFilled(false);
		buttonspace1.setBorderPainted(false);
		
		JButton buttonspace2 = new JButton("     ");
		buttonspace2.setEnabled(false);
		buttonspace2.setOpaque(false);
		buttonspace2.setContentAreaFilled(false);
		buttonspace2.setBorderPainted(false);
		
		pokeball = new JButton("POKEBALL");
		pokeball.setForeground(Color.WHITE);
		pokeball.setBackground(Color.decode("#872A2A"));
		pokeball.setFont(new Font("Arial", Font.BOLD, 25));
		pokeball.setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.decode("#D23B3B"), 10),
	               BorderFactory.createLineBorder(Color.decode("#872A2A"), 40)));
		pokeball.addActionListener(new ButtonListener(pokeball));
		
		rock = new JButton("ROCK");
		rock.setForeground(Color.WHITE);
		rock.setBackground(Color.decode("#066407"));
		rock.setFont(new Font("Arial", Font.BOLD, 25));
		rock.setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.decode("#09990C"), 10),
	               BorderFactory.createLineBorder(Color.decode("#066407"), 24)));
		
		rock.addActionListener(new ButtonListener(rock));
		run = new JButton(" RUN ");
		run.setForeground(Color.WHITE);
		run.setBackground(Color.decode("#193551"));
		run.setFont(new Font("Arial", Font.BOLD, 25));
		run.setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.decode("#2B5B8C"), 10),
	               BorderFactory.createLineBorder(Color.decode("#193551"), 24)));
		run.addActionListener(new ButtonListener(run));
		
		bait = new JButton("BAIT");
		bait.setForeground(Color.WHITE);
		bait.setBackground(Color.decode("#8C5A2B"));
		bait.setFont(new Font("Arial", Font.BOLD, 25));
		bait.setBorder(BorderFactory.createCompoundBorder(
	               BorderFactory.createLineBorder(Color.decode("#CF8239"), 10),
	               BorderFactory.createLineBorder(Color.decode("#8C5A2B"), 24)));
		bait.addActionListener(new ButtonListener(bait));
		
		//Adding Contents
		buttonPanel.add(buttonspace1);
		buttonPanel.add(pokeball);
		buttonPanel.add(buttonspace2);
		buttonPanel.add(bait);
		buttonPanel.add(run);
		buttonPanel.add(rock);
		
	}
	
	public void setupContainer(){
		//Setup spaces to use in JPanel organization
		JPanel space1 = new JPanel();
		space1.setVisible(false);
		JPanel space2 = new JPanel();
		space2.setVisible(false);
		JPanel space3 = new JPanel();
		space3.setVisible(false);
		JPanel space4 = new JPanel();
		space4.setVisible(false);
		JPanel space5 = new JPanel();
		space5.setVisible(false);
		JPanel space6 = new JPanel();
		space6.setVisible(false);
		JPanel space7 = new JPanel();
		space7.setVisible(false);
		JPanel space8 = new JPanel();
		space8.setVisible(false);
		JPanel space9 = new JPanel();
		space9.setVisible(false);
		JPanel space10 = new JPanel();
		space10.setVisible(false);
		JPanel space11 = new JPanel();
		space11.setVisible(false);		
		
		//JPanel Setup
		container = new JPanel();
		container.setPreferredSize(new Dimension(1400, 1300));
		container.setLayout(new GridLayout(5, 8));
		container.setOpaque(false);
		container.setLocation(400, 400);
		container.setBackground(Color.BLACK);
		
		container.add(space1);
		container.add(space2);
		container.add(space3);
		container.add(space4);
		container.add(pokemonPanel);
		container.add(space6);
		container.add(space7);
		container.add(space8);
		container.add(space9);
		container.add(space10);
		//contents.add(space11);

		container.add(playerPanel);
		container.add(buttonPanel);
	}
	//TODO: Implement handoff back to Main map
}
