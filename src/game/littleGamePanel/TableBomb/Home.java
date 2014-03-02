package game.littleGamePanel.TableBomb;

import java.awt.event.KeyEvent;

import game.GameBody;

import javax.swing.JPanel;

public class Home implements connector2 {

	basalPlane bp;
	Version ver;

	public Home(GameBody testFrame, int i, int j, int k) {
		// TODO Auto-generated constructor stub
		bp = new basalPlane(testFrame);
		ver = new Version(bp);
		testFrame.getContentPane().add(ver, "5");
		ver.setLocation(0, 0);

		Thread bpT = new Thread(bp);
		bpT.start();

		Thread verT = new Thread(ver);
		verT.start();
	}

	@Override
	public JPanel getPanel() {
		return ver;
	}

	public void removePanel(GameBody gameBody) {

	}

	public void keyPressed(KeyEvent e) {
		ver.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		ver.keyReleased(e);
	}
}
