package game.littleGamePanel.TableBomb;

import java.awt.HeadlessException;

import javax.swing.JFrame;

public class testFrame extends JFrame implements totalGameInter2{

	
	
	
	public testFrame() throws HeadlessException {
		super();
		//connector2 o = new Home(this, 40, 40, 40);
		this.setSize(800, 600);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setTitle("Table Bomb!");
		this.getContentPane().setLayout(null);
		this.setVisible(true);
	}
	
	@Override
	public void setFinished(int gameNumber) {
		System.exit(0);
	}

}
