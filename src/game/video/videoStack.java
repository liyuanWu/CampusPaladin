package game.video;

import java.io.FileNotFoundException;

import inter.totalGameInter;

import javax.swing.JPanel;

import game.Role;

public class videoStack {
	int finishGame;
	int playerRoleType;
	JPanel videoPanel;
	
	public boolean set(int section, int playerType,totalGameInter gamebody){
		finishGame = section;
		playerRoleType = playerType;
		switch (finishGame) {
		case 0:
			break;

		default:
			try {
				videoPanel = interludeFactory.getInterludePanel(1, gamebody);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
			break;
		}
		return true;
	}
	
	public JPanel getPanel(){
		return videoPanel;
	}
}
