package graphics;

/*
						InGameMenu.java
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
Implements Pokemon SafariZone in-game menu GUI
*/

import java.awt.Dimension;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils.Text;

public class InGameMenu extends JPanel {

	private JLabel test;

	public InGameMenu() {
		// TODO: Rico Suave (Eric) implement this part. Class may need to be
		// changed restructured so that game information is passed to the panel
		super();
		test = new JLabel("MENU GOES HERE\n steps: 500");
		test.setPreferredSize(new Dimension(300, 100));
		test.setFont(new Font("Verdana", Font.BOLD, 14));
		add(test);
	}

	public void setText(int steps) {
		test.setText("MENU GOES HERE \n Steps: " + steps);
	}
}
