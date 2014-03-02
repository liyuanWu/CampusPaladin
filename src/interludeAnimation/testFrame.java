package interludeAnimation;

import game.littleGamePanel.doodle.totalGameInter;

import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class testFrame extends JFrame implements totalGameInter{

	
	public testFrame(){
			super();
			JPanel o = null;
			try {
				o = interludeFactory.getInterludePanel(1,this);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
		   this.setSize(800,600); 
		   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.getContentPane().setLayout(null);
		   this.getContentPane().add(o);
		   o.setLocation(0, 0);
		   this.setVisible(true);
	}
	@Override
	public void setFinished(int gameNumber) {
		System.exit(0);
	}

}
