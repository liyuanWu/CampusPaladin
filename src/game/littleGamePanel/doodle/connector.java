package game.littleGamePanel.doodle;


import java.awt.HeadlessException;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class connector{

	private world sand;
	private DoodleVer ver;
	totalGameInter frame;
	
	public connector(totalGameInter frame,int intelligence,int strength,int honour) throws HeadlessException {
		super();
		sand = new world(this,400,600,intelligence,strength,honour);
		ver = new DoodleVer(sand);
		this.frame = frame;

		
		   ver.setBounds(0, 0, 800,600);
		   //手动加入新线条
		   sand.addMLine(20, 2);
		  for(int i=1;i<100;i++){
			  if(Math.random()<=1){
				  int x = (int)(Math.random()*(sand.weight-world.mLineWeight));
				  sand.addMLine(x,3*i,(int)(Math.random()*4),sand.addSpring(x+20, 3*i, 0));
			  }else{
				  sand.addMLine( (int)(Math.random()*(sand.weight-world.mLineWeight)), 3*i, (int)(Math.random()*4));
			  }
		  }
		  sand.setVictoryBoard(0, 400,300);
		  sand.setVictoryBoard(0, 400,305);
		  sand.setVictoryBoard(0, 400,310);
		  sand.setVictoryBoard(0, 400,315);
		   sand.setSpeed(5);
		   sand.refreshTheFloor();
		   
		   Thread verThread=new Thread(ver);
		   verThread.start();
		   
		   Thread worldRunThread = new Thread(sand);
		   worldRunThread.start();
	}
	public JPanel getPanel(){
		return ver;
	}
	
	public void gameover(){
		frame.setFinished(3);
	}
	
	public void keyTyped(KeyEvent e) {
		sand.mrM.keyTyped(e);

	}

	public void keyPressed(KeyEvent e) {
		sand.mrM.keyPressed(e);

	}

	public void keyReleased(KeyEvent e) {
		sand.mrM.keyReleased(e);

	}
	
}
