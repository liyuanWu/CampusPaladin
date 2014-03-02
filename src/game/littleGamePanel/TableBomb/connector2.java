package game.littleGamePanel.TableBomb;

import java.awt.event.KeyEvent;

import game.GameBody;

import javax.swing.JPanel;

public interface connector2 {
	public JPanel getPanel();
	public void keyPressed(KeyEvent e);
	public void keyReleased(KeyEvent e);
}
