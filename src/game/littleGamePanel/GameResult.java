package game.littleGamePanel;

import javax.swing.JOptionPane;

public class GameResult {
	  public  int[] gameAddPoints = new int[4]; //用于储存4个属性值
	    
	public int[] won(long startTime, long mouseClickCount) {
		
		long finishTime = System.currentTimeMillis();
		long costTimeInSeconds = (finishTime - startTime) / 1000;

		
		gameAddPoints[0] = 5;
		gameAddPoints[3] = 10;
		if (costTimeInSeconds <= 60)
			gameAddPoints[1] = 10;
		if (costTimeInSeconds > 60 && costTimeInSeconds <= 120)
			gameAddPoints[1] = 5;
		if (costTimeInSeconds >= 120)
			gameAddPoints[1] = 2;

		if (mouseClickCount <= 30)
			gameAddPoints[2] = 10;
		if (mouseClickCount > 30 && mouseClickCount <= 45)
			gameAddPoints[2] = 5;
		if (mouseClickCount > 45)
			gameAddPoints[2] = 2;
		
		JOptionPane.showMessageDialog(null, "Congratulations!\nYou spent "
				+ costTimeInSeconds
				+ " seconds to finish this game.\n You clicked the mouse "
				+ mouseClickCount + " times.\n Your sword adds "
				+ gameAddPoints[1] + " Points.\n Your wing adds "
				+ gameAddPoints[2] + " points.");
		return gameAddPoints;
		
	}

//	public int[] getAddedPoints(long startTime, long mouseClickCount){
//		long finishTime = System.currentTimeMillis();
//		long costTimeInSeconds = (finishTime - startTime) / 1000;
//
//		gameAddPoints[0] = 5;
//		gameAddPoints[3] = 10;
//		if (costTimeInSeconds <= 60)
//			gameAddPoints[1] = 10;
//		if (costTimeInSeconds > 60 && costTimeInSeconds <= 120)
//			gameAddPoints[1] = 5;
//		if (costTimeInSeconds >= 120)
//			gameAddPoints[1] = 2;
//
//		if (mouseClickCount <= 30)
//			gameAddPoints[2] = 10;
//		if (mouseClickCount > 30 && mouseClickCount <= 55)
//			gameAddPoints[2] = 5;
//		if (mouseClickCount > 55)
//			gameAddPoints[2] = 2;
//		return gameAddPoints;
//	}
	
	public void unfinished() {
		JOptionPane.showMessageDialog(null, "Not finished yet");

	}
}
