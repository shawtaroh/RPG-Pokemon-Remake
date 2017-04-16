/*
 * @author
 * 
 * In game menu including many options
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
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import driver.GameGUI;

public class InGameMenu extends JPanel {

	private GameGUI gui;

	private JLabel header;
	private JButton quit;
	private JLabel mapLabel;
	private JButton mapOne;
	private JButton mapTwo;
	private JButton save;
	private JButton resume;

	private String backgroundPNG = "/art/background.png";
	private String stepsPNG = "/art/on_screen_icons/steps.png";
	private String hpPNG = "/art/on_screen_icons/hp.png";
	private String pokeballPNG = "/art/on_screen_icons/pokeball.png";
	private String potionPNG = "/art/on_screen_icons/potion.png";
	private String safariballPNG = "/art/on_screen_icons/safariball.png";

	public InGameMenu(GameGUI gui) {
		super();

		this.gui = gui;

		repaint();

		JPanel contents = new JPanel();
		contents.setLayout(new BoxLayout(contents, BoxLayout.PAGE_AXIS));
		contents.setOpaque(false);
		contents.setSize(200, 400);
		this.add(contents);

		header = new JLabel("Pokemon Safari Zone");
		header.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		header.setFont(new Font("Verdana", Font.BOLD, 50));
		header.setForeground(Color.WHITE);
		header.setOpaque(false);
		header.setHorizontalAlignment(SwingConstants.CENTER);
		contents.add(header);

		mapLabel = new JLabel("  ");
		mapLabel.setAlignmentX(JLabel.CENTER_ALIGNMENT);
		mapLabel.setFont(new Font("Verdana", Font.BOLD, 30));
		mapLabel.setForeground(Color.WHITE);
		mapLabel.setOpaque(false);
		mapLabel.setHorizontalAlignment(SwingConstants.CENTER);
		contents.add(mapLabel);

		Dimension buttonSize = new Dimension(300, 50);
		Color buttonColor = Color.LIGHT_GRAY;
		Color buttonText = Color.BLACK;
		Font buttonFont = new Font("Verdana", Font.BOLD, 20);

		JLabel spacer = new JLabel(" ");
		spacer.setPreferredSize(buttonSize);
		contents.add(spacer);

		mapOne = new JButton("Go To World One");
		mapOne.setAlignmentX(JButton.CENTER_ALIGNMENT);
		mapOne.setMaximumSize(buttonSize);
		mapOne.addActionListener(new MenuListener(mapOne));
		mapOne.setFont(buttonFont);
		mapOne.setBackground(buttonColor);
		mapOne.setForeground(buttonText);
		contents.add(mapOne);

		spacer = new JLabel(" ");
		spacer.setPreferredSize(buttonSize);
		contents.add(spacer);

		mapTwo = new JButton("Go To World Two");
		mapTwo.setAlignmentX(JButton.CENTER_ALIGNMENT);
		mapTwo.setMaximumSize(buttonSize);
		mapTwo.addActionListener(new MenuListener(mapTwo));
		mapTwo.setFont(buttonFont);
		mapTwo.setBackground(buttonColor);
		mapTwo.setForeground(buttonText);
		contents.add(mapTwo);

		spacer = new JLabel(" ");
		spacer.setPreferredSize(buttonSize);
		contents.add(spacer);

		save = new JButton("Save Game");
		save.setAlignmentX(JButton.CENTER_ALIGNMENT);
		save.setMaximumSize(buttonSize);
		save.addActionListener(new MenuListener(save));
		save.setFont(buttonFont);
		save.setBackground(buttonColor);
		save.setForeground(buttonText);
		contents.add(save);

		spacer = new JLabel(" ");
		spacer.setPreferredSize(buttonSize);
		contents.add(spacer);

		resume = new JButton("Resume");
		resume.setAlignmentX(JButton.CENTER_ALIGNMENT);
		resume.setMaximumSize(buttonSize);
		resume.addActionListener(new MenuListener(resume));
		resume.setFont(buttonFont);
		resume.setBackground(buttonColor);
		resume.setForeground(buttonText);
		contents.add(resume);

		spacer = new JLabel(" ");
		spacer.setPreferredSize(buttonSize);
		contents.add(spacer);

		quit = new JButton("Quit");
		quit.setAlignmentX(JButton.CENTER_ALIGNMENT);
		quit.setMaximumSize(buttonSize);
		quit.addActionListener(new MenuListener(quit));
		quit.setFont(buttonFont);
		quit.setBackground(buttonColor);
		quit.setForeground(buttonText);
		contents.add(quit);
	}

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
			} else if (button == mapOne) {
				System.out.println("map changing not implemented");
			} else if (button == mapTwo) {
				System.out.println("map changing not implemented");
			} else if (button == save) {
				try {
					gui.saveState();
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (button == resume) {
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

		Image bkgnd = new ImageIcon(this.getClass().getResource(backgroundPNG)).getImage();
		g.drawImage(bkgnd, 0, 0, this.getWidth(), this.getHeight(), Color.RED, null);

		int x = 40;
		int y = 80;
		int spacing = 10;
		int size = 75;

		g.setColor(Color.WHITE);
		g.setFont(new Font("Verdana", Font.BOLD, 40));

		Image steps = new ImageIcon(this.getClass().getResource(stepsPNG)).getImage();
		g.drawImage(steps, x, y + 0 * (size + spacing), size, size, Color.RED, null);
		g.drawString("x ##", x + size + spacing, y + 1 * (size + spacing) - 2 * spacing);

		Image hp = new ImageIcon(this.getClass().getResource(hpPNG)).getImage();
		g.drawImage(hp, x, y + 1 * (size + spacing), size, size, Color.RED, null);
		g.drawString("x ##", x + size + spacing, y + 2 * (size + spacing) - 2 * spacing);

		Image pokeball = new ImageIcon(this.getClass().getResource(pokeballPNG)).getImage();
		g.drawImage(pokeball, x, y + 2 * (size + spacing), size, size, Color.RED, null);
		g.drawString("x ##", x + size + spacing, y + 3 * (size + spacing) - 2 * spacing);

		Image potion = new ImageIcon(this.getClass().getResource(potionPNG)).getImage();
		g.drawImage(potion, x, y + 3 * (size + spacing), size, size, Color.RED, null);
		g.drawString("x ##", x + size + spacing, y + 4 * (size + spacing) - 2 * spacing);

		Image safariball = new ImageIcon(this.getClass().getResource(safariballPNG)).getImage();
		g.drawImage(safariball, x, y + 4 * (size + spacing), size, size, Color.RED, null);
		g.drawString("x ##", x + size + spacing, y + 5 * (size + spacing) - 2 * spacing);
	}

	/*
	 * For showing menu panel
	 */
	public void on() {

		this.setFocusable(true);
		this.setVisible(true);
		// mapLabel.setText(gui.getWorld().getName());

	}

	/*
	 * For hiding menu panel
	 */
	public void off() {

		this.setFocusable(false);
		this.setVisible(false);
	}
}