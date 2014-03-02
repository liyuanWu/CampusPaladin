package media;

import java.awt.Component;
import java.io.File;
import javax.media.ControllerEvent;
import javax.media.ControllerListener;
import javax.media.EndOfMediaEvent;
import javax.media.Manager;
import javax.media.MediaLocator;
import javax.media.Player;
import javax.media.RealizeCompleteEvent;
import javax.swing.JPanel;

//import system.TankFrame;

public class VideoPlayer extends JPanel implements ControllerListener {
	/**
	 * 
	 */
	private static final long serialVersionUID = -4922678429132980979L;
	
	private Player player = null;
	private Component videoComponent;
//	private TankFrame tkFrame;
	
	public VideoPlayer(String name) {
//		tkFrame=tfFrame;
		File videoFile = new File("video/"+name+".mpg");
		MediaLocator  locator=new MediaLocator("file:"+videoFile.getAbsolutePath());
		
		try {
			player = Manager.createPlayer(locator);
			player.prefetch();
			player.addControllerListener(this);
		} catch (Exception e) {
			System.err.println("找不到文件 " + e);
		}
				
	}
	public void play() {
		player.start();
	}
	public void stop() {
		player.stop();
		player.close();
	}
  
    public synchronized void controllerUpdate(ControllerEvent event) {
       if (event instanceof RealizeCompleteEvent) {
           if ((videoComponent = player.getVisualComponent()) != null){
        	   	videoComponent.setSize(800, 600);
           		videoComponent.setVisible(true);
           		videoComponent.setFocusable(false);
 //          		tkFrame.add(videoComponent);
           }else if (event instanceof EndOfMediaEvent) {
        	   stop();
 //       	   tkFrame.animeEnd();
           }
       }
    }
	public Component getVideoComponent() {
		return videoComponent;
	}
	
}


