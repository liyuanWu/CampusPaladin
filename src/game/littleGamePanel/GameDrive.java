package game.littleGamePanel;

import javax.swing.JFrame;

public class GameDrive extends JFrame{
	
	public GameDrive(){
	int[] pointsAdded = new int[4];
	this.setTitle("TankWar");
	setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	this.setSize(800,600);
	this.setLocation(100, 100);	
	this.setResizable(false);
	this.setVisible(true);	
	//TankWar tankWar=new TankWar(50,50,0,0);
	//this.add(tankWar);	
	//tankWar.requestFocusInWindow();
	//pointsAdded = tankWar.getFinishPoints();
	}
	public static void main(String[] args){
		GameDrive gameDrive = new GameDrive();
		 	
	}
	
}
