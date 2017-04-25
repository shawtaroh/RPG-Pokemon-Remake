package driver;

import java.awt.AlphaComposite;
import java.awt.Canvas;

/*
 * GameGUI.java
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
 * Implements Pokemon SafariZone Game GUI
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.WindowConstants;

import graphics.BitMap;
import graphics.InGameMenu;
import graphics.SplashScreen;
import model.Player;
import model.Pokedex;
import model.PokemonGame;
import model.Key;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class GameGUI extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 60158402771325988L;
	private int width = 2944;
	private int height = 2688;
	private int FPS = 240;
	private BitMap screen;
	private int scale;
	private InGameMenu menu;
	private boolean running = true;
	private InputHandler inputHandler;
	private PokemonGame pokemonGame;
	private String message, message2, message3;
	private boolean isNextPage = false;
	private int currentSteps = 500;
	private boolean win=false;

	private BufferedImage msgBox, clouds, fog;
	private jPanel2 painting;
	private ObjectWaitingForSongToEnd waiter = new ObjectWaitingForSongToEnd();
	private ObjectWaitingForSongToEndWin WinWaiter = new ObjectWaitingForSongToEndWin();

	public GameGUI(int mapNum, int winCondition) {
		loadTransperantImages();
		pokemonGame = new PokemonGame(mapNum, winCondition);
		scale = 1;
		screen = new BitMap(width, height);
		message = "Welcome to Safari Zone. To turn, tap the arrow keys. To move, hold-down the arrow ";
		message2 = "keys. To open menu, space-bar. Have fun! Hint: Home is North-East";
		message3 = "Press Enter for next page.";

		inputHandler = new InputHandler(pokemonGame.getKeys());

		menuListener myMenuListener = new menuListener();
		addKeyListener(inputHandler);
		addKeyListener(myMenuListener);
		addWindowListener(new myWindowListener());

		painting = new jPanel2();
		painting.setFocusable(false);
		add(painting);

		// Menu Second for Layering
		menu = new InGameMenu(pokemonGame, this);
		menu.setVisible(false);
		add(menu);

		pack();
		setResizable(true);
		Insets inset = getInsets();
		setSize(new Dimension(inset.left + inset.right + width * scale / 2,
				inset.top + inset.bottom + height * scale / 2));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		setVisible(true);
		start();
		SongPlayer.playFile(waiter, Pokedex.class.getResource("/art/sounds/101-opening.wav").toString().substring(6));
	}

	public static void main(String[] args) {
		int choice = JOptionPane.showConfirmDialog(null, "Load previous save state?");
		if (choice == 0) {
			try {
				ObjectInputStream inFile = new ObjectInputStream(new FileInputStream("game.save"));
				SplashScreen execute = new SplashScreen("/art/splash/pika loading.gif", "Loading Safari World!");
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				Player loaded = (Player) inFile.readObject();
				GameGUI game = new GameGUI(loaded.getMap(), loaded.getWinCondition());
				game.getPokemonGame().getPlayer().setSteps(loaded.getSteps());
				game.getPokemonGame().getPlayer().setxPosition(loaded.getxPosition());
				game.getPokemonGame().getPlayer().setyPosition(loaded.getyPosition());
				game.getPokemonGame().getPlayer().setxLastPosition(loaded.getxLastPosition());
				game.getPokemonGame().getPlayer().setyLastPosition(loaded.getyLastPosition());
				game.getPokemonGame().getPlayer().setWinCondition(loaded.getWinCondition());

			} catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		} else if (choice == 1) {
			Object[] options = { "Map One", "Map Two" };
			int map = JOptionPane.showOptionDialog(null, "Choose a map", "Choose a map",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			Object[] options2 = { "Normal", "Story" };
			int winCondition = JOptionPane.showOptionDialog(null, "choose a mode", "choose a mode",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options2, options2[0]);

			SplashScreen execute = new SplashScreen("/art/splash/pika loading.gif", "Loading Safari World!");
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			GameGUI game = new GameGUI(map, winCondition);
		}

	}

	// makes art files transperant
	public void loadTransperantImages() {
		try {
			msgBox = ImageIO.read(GameGUI.class.getResource("/art/msgBox.png"));
			fog = ImageIO.read(GameGUI.class.getResource("/art/clouds2.png"));
			clouds = ImageIO.read(GameGUI.class.getResource("/art/clouds3.png"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BufferedImage tmpImg2 = new BufferedImage(fog.getWidth(), fog.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d2 = (Graphics2D) tmpImg2.getGraphics();
		g2d2.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		// set the transparency level in range 0.0f - 1.0f
		g2d2.drawImage(fog, 0, 0, null);
		fog = tmpImg2;

		BufferedImage tmpImg3 = new BufferedImage(clouds.getWidth(), clouds.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d3 = (Graphics2D) tmpImg3.getGraphics();
		g2d3.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		// set the transparency level in range 0.0f - 1.0f
		g2d3.drawImage(clouds, 0, 0, null);
		clouds = tmpImg3;

		BufferedImage tmpImg = new BufferedImage(msgBox.getWidth(), msgBox.getHeight(), BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) tmpImg.getGraphics();
		g2d.setComposite(AlphaComposite.SrcOver.derive(0.5f));
		// set the transparency level in range 0.0f - 1.0f
		g2d.drawImage(msgBox, 0, 0, null);
		msgBox = tmpImg;

	}

	// returns game
	public PokemonGame getPokemonGame() {
		return this.pokemonGame;
	}

	// song waiter
	public class ObjectWaitingForSongToEnd implements EndOfSongListener, Serializable {

		public void songFinishedPlaying(EndOfSongEvent eosEvent) {

			SongPlayer.playFile(waiter,
					Pokedex.class.getResource("/art/sounds/101-opening.wav").toString().substring(6));
		}
	}

	
	public class ObjectWaitingForSongToEndWin implements EndOfSongListener, Serializable {

		public void songFinishedPlaying(EndOfSongEvent eosEvent) {

			SongPlayer.playFile(WinWaiter,
					Pokedex.class.getResource("/art/sounds/win.wav").toString().substring(6));
		}
	}
	
	private class menuListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {

		}

		@Override
		public void keyReleased(KeyEvent arg0) {

			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				if (running)
					pauseToMenu();
				else
					resumeFromMenu();
			}

		}

		@Override
		public void keyTyped(KeyEvent arg0) {

		}

	}

	// Starts new graphics thread
	public void start() {

		running = true;
		Thread thread = new Thread(this);
		thread.start();
	}

	/*
	 * resuming game-play from menu
	 */
	public void resumeFromMenu() {

		running = true;
		menu.off();
		painting.setVisible(true);
		menu.setFocusable(false);
		this.requestFocus();
		start();
		repaint();
	}

	/*
	 * invoke menu
	 */
	private void pauseToMenu() {

		stop();
		menu.on();
		painting.setVisible(false);
		repaint();
	}

	// stops graphics rendering thread
	public void stop() {

		running = false;
	}

	// updates graphics in separate thread
	public void run() {

		long lastTime = System.nanoTime();
		double unprocessed = 0.0;
		int tick = 0;

		while (running) {
			double nsPerTick = 1000000000.0 / (double) FPS;
			boolean shouldRender = false;
			while (unprocessed >= 1) {
				tick++;
				unprocessed -= 1;
			}
			int toTick = tick;
			if (tick > 0 && tick < 3)
				toTick = 1;
			if (tick > 20)
				toTick = 20;

			for (int i = 0; i < toTick; i++) {
				tick--;
				for (Key k : pokemonGame.getKeys())
					k.tick();
				pokemonGame.getWorld().tick();
				shouldRender = true;
			}
			BufferStrategy bufferStrategy = painting.getBufferStrategy();
			if (bufferStrategy == null) {
				painting.createBufferStrategy(4);
				continue;
			}

			if (shouldRender && running) {
				Graphics g = bufferStrategy.getDrawGraphics();
				g.setColor(Color.BLACK);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.translate(
						(((this.getWidth() - (width) * scale)) / 2) - pokemonGame.getPlayer().getxPosition() * scale,
						((this.getHeight() - (height) * scale) / 2) - pokemonGame.getPlayer().getyPosition() * scale);
				g.clipRect(0, 0, width * scale, height * scale);
				if (pokemonGame.getWorld() != null) {
					int xScroll = (pokemonGame.getPlayer().getxPosition());
					int yScroll = (pokemonGame.getPlayer().getyPosition());
					pokemonGame.getWorld().render(screen, xScroll, yScroll);
				}
				System.out.println(pokemonGame.getPlayer().getxPosition() + "," + pokemonGame.getPlayer().getyPosition());
				if(win){
					message = "You Win!";
					message2="";
				}
				if(!win&&Math.abs(pokemonGame.getPlayer().getxPosition()-pokemonGame.getWorld().getProfessorX())<64&&Math.abs(pokemonGame.getPlayer().getyPosition()-pokemonGame.getWorld().getProfessorY())<64){
					SongPlayer.playFile(WinWaiter, Pokedex.class.getResource("/art/sounds/win.wav").toString().substring(6));
					message = "You Win!";
					message2="";
					win=true;
				}
				if (pokemonGame.getPlayer().getxPosition() >=640 && pokemonGame.getPlayer().getxPosition() <=832 && pokemonGame.getPlayer().getyPosition() <= -896&&pokemonGame.getPlayer().getyPosition() >= -1024) {
					pokemonGame.getPlayer().setSpeed(120);
					FPS=240;
					message = "SPEED!";
					message2="";
				}
				if (pokemonGame.getPlayer().getxPosition() == -640 && pokemonGame.getPlayer().getyPosition() == -512) {
						pokemonGame.getPlayer().setHasAxe(true);
						message = "You found an axe! Press 'z' to clear obsticles in-front of you in the maze.";
						message2="";
				}
				// draws main screen
				g.drawImage(screen.getBufferedImage(), 0, 0, width * scale, height * scale, null);
				// draws environmental effects
				renderMsgBoxAndClouds(g);
				if ((pokemonGame.getPlayer().getSteps() % 15 == 0)) {
					if (pokemonGame.getPlayer().getSteps() % 2 == 0) {
						message = "You found a pokemon! TODO: Iteration 2";
						message2 = "";
					} else {
						if (pokemonGame.getPlayer().getSteps() % 5 == 0)
							message = "You found an potion! TODO: Iteration 1";
						if (currentSteps != pokemonGame.getPlayer().getSteps())
							pokemonGame.getPlayer().getMyBag().addItems("Potions", 1);
						message2 = "";
					}
					currentSteps = pokemonGame.getPlayer().getSteps();
				}
				if (pokemonGame.getPlayer().getSteps() <= 0) {
					JOptionPane.showMessageDialog(null,
							"You ran out of steps, and caught no pokemon. This is only iteration one, goodbye.");
					stop();
					System.exit(0);
				}

				// TODO for iteration 2
				if (Player.isEnterHome()) {
					g.setColor(Color.WHITE);
					g.setFont(new Font("Verdana", Font.BOLD, 25));
					g.drawString("Please purchase the DLC", 75, 100);
					// Image test = new
					// ImageIcon(getClass().getResource("/res/pokemon/amaura.gif")).getImage();

					try {
						Thread.sleep(500);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					Player.setEnterHome(false);
				}
			}

			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (shouldRender && running) {
				if (bufferStrategy != null) {
					bufferStrategy.show();
				}
			}
		}
	}

	private void renderMsgBoxAndClouds(Graphics g) {
		int offsetX = 0;
		int offsetY = 0;
		if (pokemonGame.getPlayer().getxPosition() < -896)
			offsetX = 896 + pokemonGame.getPlayer().getxPosition();
		if (pokemonGame.getPlayer().getxPosition() > 896)
			offsetX = -896 + pokemonGame.getPlayer().getxPosition();
		if (pokemonGame.getPlayer().getyPosition() > 832)
			offsetY = pokemonGame.getPlayer().getyPosition() - 832;
		g.drawImage(msgBox, pokemonGame.getPlayer().getxPosition() + 64 * 15 - offsetX,
				pokemonGame.getPlayer().getyPosition() + 64 * 26 - (1000 - this.getHeight()) / 2 - offsetY,
				width * scale / 3, height * scale / 32, null);
		g.setFont(new Font("Arial", Font.BOLD, 24));
		g.setColor(Color.WHITE);
		g.drawString("Steps: " + pokemonGame.getPlayer().getSteps(),
				pokemonGame.getPlayer().getxPosition() + 64 * 15 - offsetX,
				pokemonGame.getPlayer().getyPosition() + 64 * 26 - (1000 - this.getHeight()) / 2 - offsetY);
		if (!isNextPage) {
			g.drawString(message, pokemonGame.getPlayer().getxPosition() + 64 * 15 + 15 - offsetX,
					pokemonGame.getPlayer().getyPosition() + 64 * 26 + 30 - (1000 - this.getHeight()) / 2 - offsetY);
			g.drawString(message2, pokemonGame.getPlayer().getxPosition() + 64 * 15 + 15 - offsetX,
					pokemonGame.getPlayer().getyPosition() + 64 * 26 + 60 - (1000 - this.getHeight()) / 2 - offsetY);
		} else if (pokemonGame.getPlayer().getWinCondition() == 1) {
			g.drawString("A fog has fallen upon the Safari Zone. Quick, find Professor Mercer to teach the",
					pokemonGame.getPlayer().getxPosition() + 64 * 15 + 15 - offsetX,
					pokemonGame.getPlayer().getyPosition() + 64 * 26 + 30 - (1000 - this.getHeight()) / 2 - offsetY);
			g.drawString("game developers how to turn the fog component invisible!",
					pokemonGame.getPlayer().getxPosition() + 64 * 15 + 15 - offsetX,
					pokemonGame.getPlayer().getyPosition() + 64 * 26 + 60 - (1000 - this.getHeight()) / 2 - offsetY);
		} else {
			isNextPage = false;
		}
		g.setFont(new Font("Arial", Font.BOLD, 12));
		g.setColor(Color.LIGHT_GRAY);
		g.drawString(message3, pokemonGame.getPlayer().getxPosition() + 64 * 15 + 420 - offsetX,
				pokemonGame.getPlayer().getyPosition() + 64 * 26 + 75 - (1000 - this.getHeight()) / 2 - offsetY);
		g.drawImage(clouds,
				(((this.getWidth() - (width) * scale)) / 2) - pokemonGame.getPlayer().getxPosition() * scale,
				((this.getHeight() - (height) * scale) / 2) - pokemonGame.getPlayer().getyPosition() * scale,
				width * scale, height * scale / 4, null);
		g.drawImage(clouds,
				(((this.getWidth() - (width) * scale)) / 2) + 1500 - pokemonGame.getPlayer().getxPosition() * scale,
				((this.getHeight() - (height) * scale) / 2) + 750 - pokemonGame.getPlayer().getyPosition() * scale,
				width * scale, height * scale / 4, null);
		g.drawImage(clouds,
				(((this.getWidth() - (width) * scale)) / 2) + 2000 - pokemonGame.getPlayer().getxPosition() * scale,
				((this.getHeight() - (height) * scale) / 2) + 1750 - pokemonGame.getPlayer().getyPosition() * scale,
				width * scale, height * scale / 4, null);
		g.drawImage(clouds,
				(((this.getWidth() - (width) * scale)) / 2) - 500 - pokemonGame.getPlayer().getxPosition() * scale,
				((this.getHeight() - (height) * scale) / 2) + 1500 - pokemonGame.getPlayer().getyPosition() * scale,
				width * scale, height * scale / 4, null);
		if (pokemonGame.getPlayer().getWinCondition() == 1)
			g.drawImage(fog,
					(((this.getWidth() - (width) * scale * 2)) / 2) - pokemonGame.getPlayer().getxPosition() * scale,
					((this.getHeight() - (height) * scale) / 2) - pokemonGame.getPlayer().getyPosition() * scale - 600,
					width * scale * 3, height * scale * 2, null);

	}

	/*
	 * for interacting with player
	 */
	private class InputHandler implements KeyListener {

		private ArrayList<Key> keys;
		private int tick = 0;
		private int lastX=0;
		private int lastY=0;

		public InputHandler(ArrayList<Key> keys) {
			this.keys = keys;
		}

		public void toggle(KeyEvent event, boolean value) {

			int myKey = event.getKeyCode();
			Key key = null;
			for (Key k : keys) {
				if (k.getKey_code() == myKey) {
					key = k;
				}
			}

			if (myKey == KeyEvent.VK_Z) {
				if (pokemonGame.getPlayer().isHasAxe()) {
					pokemonGame.getPlayer().useAxe();
					pokemonGame.getWorld().useAxe(pokemonGame.getPlayer().getFacing(), pokemonGame.getPlayerXPos(),
							pokemonGame.getPlayerYPos());
					lastX=pokemonGame.getPlayerXPos();
					lastY=pokemonGame.getPlayerYPos();
					message = "The axe broke! You can't use it aynmore!";
					message2="";
					pokemonGame.getPlayer().setHasAxe(false);
				} else if(Math.abs(pokemonGame.getPlayerXPos()-lastX)>=64&&Math.abs(pokemonGame.getPlayerYPos()-lastY)>=64){
					message = "You don't have an axe!";
					message2="";
				}
				System.out.println("AXED");
			}

			if (myKey == KeyEvent.VK_ENTER) {
				isNextPage = true;
			}

			if (key != null) {
				if (tick > 0)
					key.setTapped(false);
				else
					key.setTapped(true);
				key.setNextState(value);
			}
		}

		@Override
		public void keyTyped(KeyEvent e) {

		}

		@Override
		public void keyPressed(KeyEvent e) {
			if (!running)
				return;

			toggle(e, true);
			tick++;
			if (tick > 10)
				tick = 10;
		}

		@Override
		public void keyReleased(KeyEvent e) {

			toggle(e, false);
			tick = 0;
		}

	}

	/*
	 * closing window -- separate from listener so it can be invoked anywhere
	 */
	public void closeWindow() {

		int choice = JOptionPane.showConfirmDialog(null, "Save State?");
		if (choice == 0) {
			try {
				saveState();
				@SuppressWarnings("unused")
				SplashScreen close = new SplashScreen("/art/splash/gamecredits.gif", "Thanks for playing!!");
			} catch (FileNotFoundException e1) {
				e1.printStackTrace();
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} else if (choice == 1) {
			@SuppressWarnings("unused")
			SplashScreen close = new SplashScreen("/art/splash/gamecredits.gif", "Thanks for playing!!");
		}

	}

	/*
	 * write object state
	 */
	public void saveState() throws IOException {

		FileOutputStream savefile = new FileOutputStream("game.save");
		ObjectOutputStream outFile = new ObjectOutputStream(savefile);
		outFile.writeObject(pokemonGame.getPlayer());
		outFile.close();

	}

	private class myWindowListener implements WindowListener {

		@Override
		public void windowClosing(WindowEvent e) {

			closeWindow();
		}

		@Override
		public void windowOpened(WindowEvent e) {

		}

		@Override
		public void windowActivated(WindowEvent arg0) {

		}

		@Override
		public void windowClosed(WindowEvent arg0) {

		}

		@Override
		public void windowDeactivated(WindowEvent arg0) {

		}

		@Override
		public void windowDeiconified(WindowEvent arg0) {

		}

		@Override
		public void windowIconified(WindowEvent arg0) {

		}

	}

	/*
	 * for drawing/ animation
	 */
	class jPanel2 extends Canvas {

		jPanel2() {
			pack();
			setResizable(true);
			Insets inset = getInsets();
			setSize(new Dimension(inset.left + inset.right + width * scale / 2,
					inset.top + inset.bottom + height * scale / 2));
			setLocationRelativeTo(null);
			setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setVisible(true);
		}

		public void paintComponent(Graphics g) {
		}

	}
}