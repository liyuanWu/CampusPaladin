package game.littleGamePanel.TableBomb;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

import javax.swing.JPanel;

public class Version extends JPanel implements Runnable{

	private basalPlane bp;
	public static final int FPS = 24;

	public Version(basalPlane bp) {
		super();
		this.setSize(800,600);
		this.bp = bp;
		//this.addKeyListener(bp.flat);
		this.setFocusable(true);
	}
	

	public void paintComponent(Graphics g){
		g.setColor(Color.gray);
		g.fillRect(100, 0, 600,600);
		g.setColor(Color.white);
		for(int i=0;i<bp.items.size();i++){
			g.drawOval(bp.items.get(i).x+100, bp.items.get(i).y, bp.items.get(i).width, bp.items.get(i).height);
		}
		for(int i=0;i<bp.tablesOnScreen.size();i++){
			g.drawImage(bp.tablesOnScreen.get(i).getImage(),bp.tablesOnScreen.get(i).x+100, bp.tablesOnScreen.get(i).y, bp.tablesOnScreen.get(i).width, bp.tablesOnScreen.get(i).height,this);
		}
		if(bp.attackLaser!=null){
			g.drawLine(bp.attackLaser.x1+100,bp.attackLaser.y1,bp.attackLaser.x2+100,bp.attackLaser.y2);
		}
		g.drawRect(bp.flat.x+100, bp.flat.y, bp.flat.length, bp.flat.height);
		g.drawString(String.valueOf(bp.playerLife), 110, 10);
		
		g.setColor(Color.orange);
		g.fillRect(0, 0, 100, 600);
		
		g.fillRect(700, 0, 100, 600);
		g.setColor(Color.darkGray);
		g.drawRect(725, 300, 50, 150);
		g.drawString(String.valueOf(bp.crownBombNum), 725, 100);
		g.drawString("长度:  "+String.valueOf(bp.flat.length), 725, 125);
		g.drawString("移动速度:   "+String.valueOf(bp.flat.moveAbility), 710, 150);
		g.drawString("火力"+String.valueOf(bp.firepower), 725, 175);
		g.fillRect(725, 450-bp.continuousAttack*(150/bp.BombNeed), 50, bp.continuousAttack*(150/bp.BombNeed));
	}
	
	@Override
	public void run() {
		while(true){
			repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void keyPressed(KeyEvent e) {
		bp.flat.keyPressed(e);
	}

	public void keyReleased(KeyEvent e) {
		bp.flat.keyReleased(e);
	}
}
