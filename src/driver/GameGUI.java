package driver;

/*
 							GameGUI.java
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
	Implements Pokemon SafariZone Game GUI
*/

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Insets;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.WindowConstants;

import graphics.BitMap;
import graphics.SplashScreen;
import graphics.InGameMenu;
import maps.Map;
import model.Player;

public class GameGUI extends JFrame implements Runnable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 60158402771325988L;
	private int width = 1920;
	private int height = 1728;
	private int FPS = 60;
	private BitMap screen;
	private int scale;
	private static InGameMenu menu;
	private boolean running = true;
	private ArrayList<Key> keys = new ArrayList<>();
	private InputHandler inputHandler;
	private Map world;
	private Player player;

	public GameGUI() {
		scale = 1;
		screen = new BitMap(width, height);
		keys.add(new Key("up"));
		keys.add(new Key("down"));
		keys.add(new Key("left"));
		keys.add(new Key("right"));
		inputHandler = new InputHandler(keys);
		menuListener myMenuListener = new menuListener();
		addKeyListener(inputHandler);
		addKeyListener(myMenuListener);
		addWindowListener(new myWindowListener());
		player = new Player(keys);
		world = new Map(90, 60, player);
		menu = new InGameMenu();
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
	}

	public static void main(String[] args) {
		SplashScreen execute = new SplashScreen("/art/splash/pika loading.gif", "Loading Safari World!");
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		GameGUI game = new GameGUI();

	}

	private class menuListener implements KeyListener {

		@Override
		public void keyPressed(KeyEvent arg0) {
		}

		@Override
		public void keyReleased(KeyEvent arg0) {
			if (arg0.getKeyCode() == KeyEvent.VK_SPACE) {
				if (running) {
					stop();
					menu.setFocusable(true);
					menu.setVisible(true);
					repaint();
				} else {
					running = true;
					System.out.println("SecondTime");
					menu.setFocusable(false);
					menu.setVisible(false);
					start();
					repaint();
				}
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
				for (Key k : keys)
					k.tick();
				world.tick();
				shouldRender = true;
			}
			BufferStrategy bufferStrategy = this.getBufferStrategy();
			if (bufferStrategy == null) {
				this.createBufferStrategy(4);
				continue;
			}

			if (shouldRender) {
				Graphics g = bufferStrategy.getDrawGraphics();
				g.setColor(Color.LIGHT_GRAY);
				g.fillRect(0, 0, this.getWidth(), this.getHeight());
				g.translate((((this.getWidth() - (width) * scale)) / 2) - this.player.xPosition * scale,
						((this.getHeight() - (height) * scale) / 2) - this.player.yPosition * scale);
				g.clipRect(0, 0, width * scale, height * scale);
				if (world != null) {
					int xScroll = (player.xPosition);
					int yScroll = (player.yPosition);
					world.render(screen, xScroll, yScroll);
				}
				g.drawImage(screen.getBufferedImage(), 0, 0, width * scale, height * scale, null);
			}

			long now = System.nanoTime();
			unprocessed += (now - lastTime) / nsPerTick;
			lastTime = now;

			if (shouldRender) {
				if (bufferStrategy != null) {
					bufferStrategy.show();
				}
			}
		}
	}

	private class InputHandler implements KeyListener {

		private ArrayList<Key> keys;
		private int tick = 0;

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

	private class myWindowListener implements WindowListener {

		@Override
		public void windowClosing(WindowEvent e) {
			setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
			stop();
			System.out.println("HELLO");
			SplashScreen close = new SplashScreen("/art/splash/gamecredits.gif", "Thanks for playing!!");

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

}
