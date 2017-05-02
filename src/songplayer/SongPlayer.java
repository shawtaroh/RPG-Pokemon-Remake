package songplayer;

import java.io.Serializable;

/**
 * SongPlayer has two static methods that allow an audio file to be played
 * through the output device. The first one
 * 
 * This small class was added as a bridge to a more complicated type in an
 * effort to make it easier to use. It also exists so 335 can postpone coverage
 * of concurrency with Threads. It has an interface similar to import
 * javax.swing.Timer in that you create a listener first than call the one
 * method playFile with that listener as the first argument.
 */

public class SongPlayer implements Serializable{
	/**
	 * Play the song stored in filename in a new thread where waiter will be
	 * sent
	 * 
	 * @param waiter
	 *            A reference to the EndOfSongEvent object that becomes
	 *            registered as a listener waiting for the song to end.
	 * @param audioFileName
	 *            The name of the file to be written to your output device.
	 */
	private static Thread playing;
	
	public static void playFile(final EndOfSongListener waiter, final String audioFileName) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
			public void run() {

				Thread player = new AudioFilePlayer(audioFileName);
				if(playing==null)
					playing=player;
				((AudioFilePlayer) player).addEndOfSongListener(waiter);

				// AudioFilePlayer extends Thread. When start is called,
				// the overridden run method in AudioFilePlayer executes.
				// If the song is not played in a separate thread, your GUI
				// stops working
				player.start();
			}
		});
	}
	public static void stopPlaying(){
		if(playing!=null){
			playing.stop();
			playing=null;
		}
	}	public static void pausePlaying(){
		if(playing!=null)
			try {
				playing.wait(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
}