package driver;

import java.awt.AlphaComposite;
import java.awt.Canvas;

/*
 * GameGUI.java
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
 * Implements Pokemon SafariZone Game GUI
 */

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
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
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import graphics.BitMap;
import graphics.InGameMenu;
import graphics.SplashScreen;
import maps.Map;
import maps.MapTypeOne;
import maps.MapTypeTwo;
import model.Player;
import model.Pokedex;
import songplayer.EndOfSongEvent;
import songplayer.EndOfSongListener;
import songplayer.SongPlayer;

public class GameGUI extends JFrame implements Runnable {
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= 60158402771325988L;
	private int							width				= 2944;
	private int							height				= 2688;
	private int							FPS					= 60;
	private BitMap						screen;
	private int							scale;
	private InGameMenu					menu;
	private boolean						running				= true;
	private ArrayList<Key>				keys				= new ArrayList<>();
	private InputHandler				inputHandler;
	private Map							world;
	
	private BufferedImage				msgBox,clouds,fog;
	private Player						player;
	private jPanel2						painting;
	private ObjectWaitingForSongToEnd	waiter				= new ObjectWaitingForSongToEnd();
	
	
	
	public GameGUI(int mapNum) {
		loadTransperantImages();
		scale = 1;
		screen = new BitMap(width, height);
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		inputHandler = new InputHandler(keys);
		player = Player.getInstance(keys, mapNum);
		menuListener myMenuListener = new menuListener();
		addKeyListener(inputHandler);
		addKeyListener(myMenuListener);
		addWindowListener(new myWindowListener());
		if (mapNum == 1)
			world = new MapTypeTwo(46, 42, player);
		else
			world = new MapTypeOne(46, 42, player);
		
		painting = new jPanel2();
		add(painting);
		
		// Menu Second for Layering
		menu = new InGameMenu(this);
		menu.setVisible(false);
		add(menu);
		
		pack();
		setResizable(true);
		Insets inset = getInsets();
		setSize(new Dimension(inset.left + inset.right + width * scale / 2,
		        inset.top + inset.bottom + height * scale / 2));
		setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setVisible(true);
		start();
		SongPlayer.playFile(waiter,
		        Pokedex.class.getResource("/art/sounds/101-opening.wav")
		                .toString().substring(6));
	}
	
	public void loadTransperantImages(){
		try {
			msgBox = ImageIO.read(GameGUI.class.getResource("/art/msgBox.png"));
			fog = ImageIO.read(GameGUI.class.getResource("/art/clouds2.png"));
			clouds = ImageIO.read(GameGUI.class.getResource("/art/clouds3.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
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
	
	
	public static void main(String[] args) {
		
		int choice = JOptionPane.showConfirmDialog(null,
		        "Load previous save state?");
		
		if (choice == 0) {
			try {
				ObjectInputStream inFile = new ObjectInputStream(
				        new FileInputStream("game.save"));
				SplashScreen execute = new SplashScreen(
				        "/art/splash/pika loading.gif",
				        "Loading Safari World!");
				try {
					Thread.sleep(1000);
				}
				catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				Player loaded = (Player) inFile.readObject();
				GameGUI game = new GameGUI(loaded.getMap());
				game.player = Player.getInstance(game.keys, loaded.getMap());
				game.player.setxPosition(loaded.getxPosition());
				game.player.setyPosition(loaded.getyPosition());
				game.player.setSteps(loaded.getSteps());
			}
			catch (ClassNotFoundException | IOException e) {
				e.printStackTrace();
			}
		}
		else {
			Object[] options = { "Map One", "Map Two" };
			int map = JOptionPane.showOptionDialog(null, "Choose a map", "Choose a map",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			Object[] options2 = { "Normal", "Story" };
			int winCondition = JOptionPane.showOptionDialog(null, "choose a mode", "choose a mode",
					JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
			if (winCondition==1){
				//TODO
			}

			SplashScreen execute = new SplashScreen(
			        "/art/splash/pika loading.gif", "Loading Safari World!");
			try {
				Thread.sleep(1000);
			}
			catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			GameGUI game = new GameGUI(map);
		}
		
	}
	
	
	
	// song waiter
	public class ObjectWaitingForSongToEnd
	        implements EndOfSongListener, Serializable {
		
		public void songFinishedPlaying(EndOfSongEvent eosEvent) {
			
			SongPlayer.playFile(waiter,
			        Pokedex.class.getResource("/art/sounds/101-opening.wav")
			                .toString().substring(6));
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
		//System.out.println("menu close");
		menu.off();
		painting.setVisible(true);
		this.setFocusable(true);
		start();
		repaint();
	}
	
	
	
	/*
	 * invoke menu
	 */
	private void pauseToMenu() {
		
		//System.out.println("menu open");
		stop();
		menu.on();
		painting.setVisible(false);
		repaint();
	}
	
	
	
	// stops graphics rendering thread
	public void stop() {
		
		running = false;
	}
	
	
	
	/*
	 * getter used by menu jpanel
	 */
	public Map getWorld() {
		
		return this.world;
	}
	
	
	
	/*
	 * setter used by menu jpanel
	 */
	public void setWorld(Map world) {
		
		this.world = world;
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
				for (Key k : keys)
					k.tick();
				world.tick();
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
				        (((this.getWidth() - (width) * scale)) / 2)
				                - this.player.getxPosition() * scale,
				        ((this.getHeight() - (height) * scale) / 2)
				                - this.player.getyPosition() * scale);
				g.clipRect(0, 0, width * scale, height * scale);
				if (world != null) {
					int xScroll = (player.getxPosition());
					int yScroll = (player.getyPosition());
					world.render(screen, xScroll, yScroll);
				}
				// currentImage = screen.getBufferedImage();
				// painting.repaint();
				g.drawImage(screen.getBufferedImage(), 0, 0, width * scale, height * scale, null);
				g.drawImage(msgBox, this.player.getxPosition() + 64 * 15, this.player.getyPosition() + 64 * 26,
						width * scale / 3, height * scale / 32, null);
				g.setFont(new Font("Arial", Font.BOLD, 24));
				String message = "Hey! Get good at this game!";
				g.setColor(Color.WHITE);
				g.drawString("Message Box", this.player.getxPosition() + 64 * 15, this.player.getyPosition() + 64 * 26);
				g.drawString(message, this.player.getxPosition() + 64 * 15, this.player.getyPosition() + 64 * 26 + 30);
				g.drawImage(clouds, (((this.getWidth() - (width) * scale)) / 2) - this.player.getxPosition() * scale,
						((this.getHeight() - (height) * scale) / 2) - this.player.getyPosition() * scale, width * scale,
						height * scale / 4, null);

				// g.drawImage(msgBox, this.player.getxPosition()+64*11+25,this.player.getyPosition()+64*26, width * scale/2, height *scale/4, null);
				// g.drawImage(fog, (((this.getWidth() - (width) * scale)) / 2)- this.player.getxPosition() * scale, ((this.getHeight() -(height) * scale) / 2) - this.player.getyPosition() * scale,width * scale, height * scale / 4, null);

				if (Player.isEnterHome()) {
					g.setColor(Color.WHITE);
					g.setFont(new Font("Verdana", Font.BOLD, 25));
					g.drawString("Please purchase the DLC", 75, 100);
					// Image test = new
					// ImageIcon(getClass().getResource("/res/pokemon/amaura.gif")).getImage();
					
					try {
						Thread.sleep(500);
					}
					catch (InterruptedException e) {
						// TODO Auto-generated catch block
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
	
	
	
	/*
	 * for interacting with player
	 */
	private class InputHandler implements KeyListener {
		
		private ArrayList<Key>	keys;
		private int				tick	= 0;
		
		
		
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
		
		setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
		stop();
		int choice = JOptionPane.showConfirmDialog(null, "Save State?");
		if (choice == 0) {
			try {
				saveState();
				
			}
			catch (FileNotFoundException e1) {
				e1.printStackTrace();
			}
			catch (IOException e1) {
				e1.printStackTrace();
			}
		}
		@SuppressWarnings("unused")
		SplashScreen close = new SplashScreen("/art/splash/gamecredits.gif",
		        "Thanks for playing!!");
	}
	
	
	
	/*
	 * write object state
	 */
	public void saveState() throws IOException {
		
		FileOutputStream savefile = new FileOutputStream("game.save");
		ObjectOutputStream outFile = new ObjectOutputStream(savefile);
		outFile.writeObject(player);
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
			
			// g.drawImage(currentImage, 0, 0, width * scale, height * scale,
			// null);
		}
		
	}
}