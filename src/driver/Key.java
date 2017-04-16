package driver;

/*
							Key.java
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
Implements Pokemon SafariZone helper class for Game GUI Input Handler
Mainly distinguishes key held down and key tapped
*/

import java.awt.event.KeyEvent;
import java.io.Serializable;

public class Key implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -2206297702059012724L;
	private boolean isTapped;
	private boolean isTappedDown;
	private boolean isPressedDown;
	private boolean nextState;
	private int key_code;

	public Key(String name) {
		// handles four cases for registered key input
		if (name.equals("up"))
			setKey_code(KeyEvent.VK_UP);
		if (name.equals("down"))
			setKey_code(KeyEvent.VK_DOWN);
		if (name.equals("left"))
			setKey_code(KeyEvent.VK_LEFT);
		if (name.equals("right"))
			setKey_code(KeyEvent.VK_RIGHT);
	}

	// advances state of key, distinguishes key held down and key tapped
	public void tick() {
		if (isTapped) {
			isTappedDown = nextState;
			isPressedDown = false;
		} else {
			isPressedDown = nextState;
			isTappedDown = false;
		}
	}

	/*
	 * Many getters and setters
	 */
	public void setTapped(boolean isTapped) {
		this.isTapped = isTapped;
	}

	public void setTappedDown(boolean isTappedDown) {
		this.isTappedDown = isTappedDown;
	}

	public void setPressedDown(boolean isPressedDown) {
		this.isPressedDown = isPressedDown;
	}

	public void setNextState(boolean nextState) {
		this.nextState = nextState;
	}

	public boolean isTapped() {
		return isTapped;
	}

	public boolean isTappedDown() {
		return isTappedDown;
	}

	public boolean isPressedDown() {
		return isPressedDown;
	}

	public boolean isNextState() {
		return nextState;
	}

	public int getKey_code() {
		return key_code;
	}

	public void setKey_code(int key_code) {
		this.key_code = key_code;
	}
}