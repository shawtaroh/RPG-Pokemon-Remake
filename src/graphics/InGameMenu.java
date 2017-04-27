
/*
 * InGameMenu.java
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
 * Implements In game menu including many options
 */

package graphics;

import java.awt.Color;

/*
 * InGameMenu.java
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
 * Implements Pokemon SafariZone in-game menu GUI
 */

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import driver.GameGUI;
import model.PokemonGame;

@SuppressWarnings("serial")
public class InGameMenu extends JPanel {
	
	private PokemonGame	model;
	private GameGUI		gui;
	
	private JButton		quit;
	private JButton		mapOne;
	private JButton		mapTwo;
	private JButton		save;
	private JButton		resume;
	
	private String		buttonPNG		= "/art/msgBox.png";
	private String		backgroundPNG	= "/art/background.png";
	private String		stepsPNG		= "/art/on_screen_icons/steps.png";
	private String		hpPNG			= "/art/on_screen_icons/hp.png";
	private String		safariballPNG	= "/art/on_screen_icons/safariball.png";
	private String		potionPNG		= "/art/on_screen_icons/potion.png";
	private String		snacksPNG		= "/art/on_screen_icons/snacks.png";
	
	private String		mapStr;
	private String		stepsStr;
	private String		hpStr;
	private String		safariballStr;
	private String		potionStr;
	private String		snacksStr;
	
	
	
	public InGameMenu(PokemonGame model, GameGUI gui) {
		super();
		
		this.model = model;
		this.gui = gui;
		
		repaint();
		
		JPanel contents = new JPanel();
		contents.setLayout(new GridLayout(0, 1));
		contents.setOpaque(false);
		contents.setLocation(400, 400);
		// contents.setAlignmentX(CENTER_ALIGNMENT);
		// contents.setAlignmentY(CENTER_ALIGNMENT);
		contents.setPreferredSize(new Dimension(400, 500));
		this.add(contents);
		
		Dimension buttonSize = new Dimension(200, 50);
		Color buttonColor = Color.BLACK;
		Color buttonText = Color.LIGHT_GRAY;
		Font buttonFont = new Font("Verdana", Font.BOLD, 25);
		
		JLabel spacer = new JLabel(" ");
		spacer.setPreferredSize(new Dimension((int) buttonSize.getWidth(),
		        (int) (buttonSize.getHeight() / 3)));
		contents.add(spacer);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		mapOne = new JButton("Go To Map One",
		        new ImageIcon(this.getClass().getResource(buttonPNG)));
		mapOne.setHorizontalTextPosition(SwingConstants.CENTER);
		mapOne.setVerticalTextPosition(SwingConstants.CENTER);
		// mapOne.setHorizontalAlignment(JButton.CENTER);
		mapOne.setMaximumSize(buttonSize);
		mapOne.addActionListener(new MenuListener(mapOne));
		mapOne.setFont(buttonFont);
		mapOne.setForeground(buttonText);
		contents.add(mapOne);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		mapTwo = new JButton("Go To Map Two",
		        new ImageIcon(this.getClass().getResource(buttonPNG)));
		mapTwo.setHorizontalTextPosition(SwingConstants.CENTER);
		mapTwo.setVerticalTextPosition(SwingConstants.CENTER);
		// mapTwo.setHorizontalAlignment(JButton.CENTER);
		mapTwo.setMaximumSize(buttonSize);
		mapTwo.addActionListener(new MenuListener(mapTwo));
		mapTwo.setFont(buttonFont);
		mapTwo.setBackground(buttonColor);
		mapTwo.setForeground(buttonText);
		contents.add(mapTwo);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		save = new JButton("Save Game",
		        new ImageIcon(this.getClass().getResource(buttonPNG)));
		save.setHorizontalTextPosition(SwingConstants.CENTER);
		save.setVerticalTextPosition(SwingConstants.CENTER);
		// save.setHorizontalAlignment(JButton.CENTER);
		save.setMaximumSize(buttonSize);
		save.addActionListener(new MenuListener(save));
		save.setFont(buttonFont);
		save.setBackground(buttonColor);
		save.setForeground(buttonText);
		contents.add(save);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		resume = new JButton("Resume",
		        new ImageIcon(this.getClass().getResource(buttonPNG)));
		resume.setHorizontalTextPosition(SwingConstants.CENTER);
		resume.setVerticalTextPosition(SwingConstants.CENTER);
		// resume.setHorizontalAlignment(JButton.CENTER);
		resume.setMaximumSize(buttonSize);
		resume.addActionListener(new MenuListener(resume));
		resume.setFont(buttonFont);
		resume.setBackground(buttonColor);
		resume.setForeground(buttonText);
		contents.add(resume);
		
		spacer = new JLabel(" ");
		spacer.setMaximumSize(buttonSize);
		contents.add(spacer);
		
		quit = new JButton("Quit",
		        new ImageIcon(this.getClass().getResource(buttonPNG)));
		quit.setHorizontalTextPosition(SwingConstants.CENTER);
		quit.setVerticalTextPosition(SwingConstants.CENTER);
		// quit.setHorizontalAlignment(JButton.CENTER);
		quit.setMaximumSize(buttonSize);
		quit.addActionListener(new MenuListener(quit));
		quit.setFont(buttonFont);
		quit.setBackground(buttonColor);
		quit.setForeground(buttonText);
		contents.add(quit);
		
	}
	
	/*
	 * to view Player's Pokemon
	 *
	 * private void setupPlayersPokemon() {
	 * 
	 * int width = 75; int height = 75;
	 * 
	 * int x = this.getWidth() - width * 4; int y = 350;
	 * 
	 * int rows = 6; int cols = 4;
	 * 
	 * JPanel tiles = new JPanel(); tiles.setLocation(x, y); tiles.setSize(width
	 * * cols, height * rows); this.add(tiles); tiles.setLayout(new
	 * GridLayout(rows, cols));
	 * 
	 * for (int i = 0; i < rows * cols; i++) { JButton slot = new JButton("" +
	 * i); slot.setPreferredSize(new Dimension(width, height)); tiles.add(slot);
	 * } }
	 */
	
	
	
	/*
	 * Listens for Buttons
	 */
	private class MenuListener implements ActionListener {
		
		private JButton button;
		
		
		
		public MenuListener(JButton btn) {
			this.button = btn;
		}
		
		
		
		@Override
		public void actionPerformed(ActionEvent arg0) {
			
			if (button == quit) {
				gui.closeWindow();
			}
			else if (button == mapOne) {
				System.out.println("\nChange to World 1");
				model.changeWorld(1);
				gui.resumeFromMenu();
			}
			else if (button == mapTwo) {
				System.out.println("\nChange to World 2");
				model.changeWorld(2);
				gui.resumeFromMenu();
			}
			else if (button == save) {
				try {
					gui.saveState();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (button == resume) {
				gui.resumeFromMenu();
			}
		}
		
	}
	
	
	
	/*
	 * Background Image
	 * 
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	public void paintComponent(Graphics g) {
		
		super.paintComponent(g);
		// g.setColor(Color.white);
		// g.fillRect(0, 0, this.getWidth(), this.getHeight());
		
		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
		        RenderingHints.VALUE_ANTIALIAS_ON);
		
		/*
		 * Background
		 */
		Image bkgnd = new ImageIcon(this.getClass().getResource(backgroundPNG))
		        .getImage();
		g2.drawImage(bkgnd, 0, 0, this.getWidth(), this.getHeight(), null,
		        null);
		
		/*
		 * Game Title
		 */
		Font fontHdr = new Font("Verdana", Font.BOLD, 80);
		FontMetrics metricsHdr = g2.getFontMetrics(fontHdr);
		g2.setColor(Color.WHITE);
		g2.setFont(fontHdr);
		String str = "Pokemon Safari Zone";
		g2.drawString(str,
		        this.getWidth() / 2 - metricsHdr.stringWidth(str) / 2,
		        50 + metricsHdr.getHeight() / 2);
		
		/*
		 * Map Label
		 */
		Font fontMap = new Font("Verdana", Font.BOLD, 50);
		FontMetrics metricsMap = g2.getFontMetrics(fontMap);
		g2.setColor(Color.WHITE);
		g2.setFont(fontMap);
		g2.drawString(mapStr,
		        this.getWidth() / 2 - metricsMap.stringWidth(mapStr) / 2,
		        this.getHeight() - 100 + metricsMap.getHeight() / 2);
		
		/*
		 * Player Stats and Inventory
		 */
		
		drawStatsAndItems(g2);
		
		/*
		 * Pokedex Inventory
		 */
		drawPokedex(g2);
		
	}
	
	
	
	/*
	 * Draw Player's Pokedex Inventory
	 */
	private void drawPokedex(Graphics2D g2) {
		
		int width = 75;
		int height = 75;
		
		int x = this.getWidth() - width * 4 - 50;
		int y = 160;
		
		g2.setFont(new Font("Verdana", Font.BOLD, 20));
		g2.drawString("Pokedex", x + 15, y - 5);
		
		int rows = 6;
		int cols = 4;
		
		Image tile = new ImageIcon(this.getClass()
		        .getResource("/art/on_screen_icons/pokemon_placeholder.png"))
		                .getImage();
		
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r < rows; r++) {
				g2.drawImage(tile, x + c * width, y + r * height, width, height,
				        Color.GRAY, null);
			}
		}
		
		g2.setFont(new Font("Verdana", Font.BOLD, 20));
		g2.drawString(
		        "Pokemon Caught: " + model.getPlayer().getMyPokemon().size(),
		        x + 15, y + rows * height + 20);
		
	}
	
	
	
	/*
	 * Draw Player Stats and Items
	 */
	private void drawStatsAndItems(Graphics2D g2) {
		
		int x = 40;
		int y = 150;
		int spacing = 10;
		int size = 75;
		
		g2.setColor(Color.WHITE);
		g2.setFont(new Font("Verdana", Font.BOLD, 40));
		
		Image steps = new ImageIcon(this.getClass().getResource(stepsPNG))
		        .getImage();
		g2.drawImage(steps, x, y + 0 * (size + spacing), size, size, null,
		        null);
		g2.drawString(stepsStr, x + size + spacing,
		        y + 1 * (size + spacing) - 2 * spacing);
		
		Image hp = new ImageIcon(this.getClass().getResource(hpPNG)).getImage();
		g2.drawImage(hp, x, y + 1 * (size + spacing), size, size, null, null);
		g2.drawString(hpStr, x + size + spacing,
		        y + 2 * (size + spacing) - 2 * spacing);
		
		Image safariball = new ImageIcon(
		        this.getClass().getResource(safariballPNG)).getImage();
		g2.drawImage(safariball, x, y + 2 * (size + spacing), size, size, null,
		        null);
		g2.drawString(safariballStr, x + size + spacing,
		        y + 3 * (size + spacing) - 2 * spacing);
		
		Image potion = new ImageIcon(this.getClass().getResource(potionPNG))
		        .getImage();
		g2.drawImage(potion, x, y + 3 * (size + spacing), size, size, null,
		        null);
		g2.drawString(potionStr, x + size + spacing,
		        y + 4 * (size + spacing) - 2 * spacing);
		
		Image snacks = new ImageIcon(this.getClass().getResource(snacksPNG))
		        .getImage();
		g2.drawImage(snacks, x, y + 4 * (size + spacing), size, size, null,
		        null);
		g2.drawString(snacksStr, x + size + spacing,
		        y + 5 * (size + spacing) - 2 * spacing);
	}
	
	
	
	/*
	 * For showing menu panel
	 */
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
	
	
	
	/*
	 * For hiding menu panel
	 */
	public void off() {
		
		this.setFocusable(false);
		this.setVisible(false);
	}
}
