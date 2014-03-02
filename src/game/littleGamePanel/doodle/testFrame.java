package game.littleGamePanel.doodle;


import javax.swing.JFrame;

public class testFrame extends JFrame implements totalGameInter{

	
	public void run(){

		this.setSize(800,600); 
		   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		   this.setTitle("Show puzzle layout");
		   this.getContentPane().setLayout(null);
		   connector o = new connector(this, 40, 40, 40);
		   this.getContentPane().add(o.getPanel());
		   this.setVisible(true);
	}
	@Override
	public void setFinished(int gameNumber) {
		System.exit(0);
		
	}

}
