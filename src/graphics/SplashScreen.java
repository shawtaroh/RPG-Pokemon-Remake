package graphics;

/*
							SplashScreen.java
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
Implements Pokemon SafariZone SplashScreens (pre-game, post-game) GUI
*/

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.HeadlessException;
import java.awt.Image;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JWindow;
import javax.swing.Timer;

public class SplashScreen extends JWindow {

	/**
	 * 
	 */
	private static final long serialVersionUID = 385604463790435742L;
	private JProgressBar progressBar = new JProgressBar();
	private int count;
	private Timer timer1;
	private String filepath, message;

	public SplashScreen(String filepath, String message) {

		this.filepath = filepath;
		this.message = message;
		Container container = getContentPane();
		container.setLayout(null);

		DrawingPanel panel = new DrawingPanel();
		panel.setBorder(new javax.swing.border.EtchedBorder());
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(10, 10, 360, 288);
		panel.setLayout(null);
		container.add(panel);

		JLabel label = new JLabel(message);
		label.setFont(new Font("Verdana", Font.BOLD, 14));
		label.setBounds(95, 225, 360, 170);

		add(label);

		progressBar.setMaximum(100);
		progressBar.setBounds(15, 360, 345, 15);
		container.add(progressBar);
		loadProgressBar();
		setSize(380, 426);
		setLocationRelativeTo(null);
		setVisible(true);
	}

	private class DrawingPanel extends JPanel {

		public void paintComponent(Graphics g) {

			super.paintComponent(g);
			g.setColor(Color.white);
			g.fillRect(0, 0, this.getWidth(), this.getHeight());

			Image icon = new ImageIcon(this.getClass().getResource(filepath)).getImage();
			g.drawImage(icon, 0, 0, null);

			repaint();
		}
	}

	private void loadProgressBar() {
		ActionListener al = new ActionListener() {

			public void actionPerformed(java.awt.event.ActionEvent evt) {
				count++;

				progressBar.setValue(count);

				System.out.println(count);

				if (count == 100) {

					createFrame();
					timer1.stop();
					setVisible(false);
				}

			}

			private void createFrame() throws HeadlessException {
				if (message.equals("Thanks for playing!!"))
					System.exit(0);
			}
		};
		timer1 = new Timer(50, al);
		timer1.start();
	}
}
