package game.video;

import game.littleGamePanel.doodle.totalGameInter;

import java.io.FileNotFoundException;
import java.net.URISyntaxException;

import javax.swing.JPanel;

public class interludeFactory {
	
	public static JPanel getInterludePanel(int i,inter.totalGameInter p) throws FileNotFoundException{
		JPanel j=null;
		try {
			j = new interludePanel(i,p);
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return (j);
	}
}
