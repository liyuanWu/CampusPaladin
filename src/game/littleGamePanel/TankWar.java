package game.littleGamePanel;

import game.GameBody;

import java.awt.Color;
import java.awt.Image;
import java.awt.event.*;
import java.awt.Graphics;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Random;

public class TankWar extends JPanel {
	public static final int TANK_GAME_NUMBER = 7;
	private GameBody gameBody;
	private int[] finishPoints = new int[4];
	public static final int WIDTH = 800, HEIGHT = 600;
	public int enemyTankAmount;
	public static final int WALL_SQUARE = 100;
	public static final int ADDTANKS = 5;
	public int lifePointAmount;
	private static Random rand = new Random();
	protected Tank mytank = new Tank(200, 450, true, this);
	protected List<Tank> enemyTanks = new ArrayList<Tank>();
	protected List<Missile> missiles = new ArrayList<Missile>(); // 装炮弹的容器
	protected List<Explode> explodes = new ArrayList<Explode>();
	protected List<Wall> walls = new ArrayList<Wall>();
	private List<LifePoint> lifePoints = new ArrayList<LifePoint>();
	 ImageIcon icon = new ImageIcon("bc.jpg");
	Image background;

	public TankWar(int crown, int sword, int wing, int money, GameBody gameBody) {
		this.gameBody = gameBody;

		if (crown <= 50)
			enemyTankAmount = 10;
		else if (crown <= 65)
			enemyTankAmount = 8;
		else if (crown <= 80)
			enemyTankAmount = 5;
		else
			enemyTankAmount = 4;

		if (wing <= 50)
			lifePointAmount = 4;
		else if (crown <= 65)
			lifePointAmount = 4;
		else if (crown <= 80)
			lifePointAmount = 3;
		else
			lifePointAmount = 2;

		try {
			background = ImageIO.read(new File("bc.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < enemyTankAmount; i++) {
			enemyTanks.add(new Tank(40 + 50 * i, 50, false, Tank.Direction.D,
					this));
		}
		lifePoints.add(new LifePoint(300, 500));
		lifePoints.add(new LifePoint(700, 200));
		walls.add(new Wall(200, 100, 400, 35, this));
		walls.add(new Wall(200, 400, 400, 35, this));

		// this.addKeyListener(new Key());
		 this.setBackground(Color.green);
		Removemissiles rm = new Removemissiles();
		Thread romoveMissile = new Thread(rm);
		romoveMissile.start();
		 Thread removeLifePoints = new Thread(new RemoveLifePoint());
		 removeLifePoints.start();
		RemoveTanks rt = new RemoveTanks();
		Thread removeTanks = new Thread(rt);
		removeTanks.start();
		PaintThread Pt = new PaintThread();
		Thread Paint = new Thread(Pt);
		Paint.start();
		Thread endGame = new Thread(new EndGame());
		endGame.start();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(background, 0, 0, getWidth(), getHeight(), 0, 0,
				getWidth(), getHeight(), null);
		synchronized (lifePoints) {
			for (int i = 0; i < lifePoints.size(); i++) {
				LifePoint lifePo = lifePoints.get(i);
				lifePo.draw(g);
			}
		}
		for (int i = 0; i < walls.size(); i++) {
			Wall wall = walls.get(i);
			wall.draw(g);
		}
		mytank.collidesWithWalls(walls);
		mytank.collidesWithTanks(enemyTanks);
		mytank.eatLive(lifePoints);
		if (mytank.isLive())
			mytank.draw(g);
		g.drawString("敌人坦克有" + enemyTanks.size(), 5, 23);
		if (mytank.isGood())
			g.drawString("炮弹有:" + missiles.size(), 5, 10);
		synchronized (missiles) {
			for (Missile i : missiles) {
				i.hitTank(mytank);
				i.hitTanks(enemyTanks);
				i.hitWall(walls);
				i.draw(g);
			}
		}
		for (int i = 0; i < enemyTanks.size(); i++) {
			Tank tank = enemyTanks.get(i);
			tank.collidesWithWalls(walls);
			tank.collidesWithTanks(enemyTanks);
			tank.collidesWithTank(mytank);
			tank.draw(g);
		}
		for (int i = 0; i < explodes.size(); i++) {
			Explode e = explodes.get(i);
			e.draw(g);
		}
	}

	private class RemoveTanks implements Runnable {

		public void run() {
			while (true) {
				synchronized (enemyTanks) {
					for (int i = 0; i < enemyTanks.size(); i++) {
						if (!enemyTanks.get(i).isLive())
							enemyTanks.remove(i);
					}
				}
			}

		}
	}

	private class Removemissiles implements Runnable {
		public void run() {
			while (true) {
				synchronized (missiles) {
					if (!missiles.isEmpty()) {
						for (int i = 0; i < missiles.size(); i++) {
							if (!missiles.get(i).islive())
								missiles.remove(i);
						}
					}
				}
			}
		}

	}

	private class RemoveLifePoint implements Runnable {
		public void run() {
			while (true) {
				synchronized (lifePoints) {
					if (!lifePoints.isEmpty()) {
						for (int i = 0; i < lifePoints.size(); i++) {
							if (!lifePoints.get(i).isLive())
								lifePoints.remove(i);
						}
					}
				}
			}
		}
	}

	private class PaintThread implements Runnable {
		public void run() {
			while (true) {
				repaint();
				try {
					Thread.currentThread().sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
	}

	private class EndGame implements Runnable {
		public void run() {
			while (true) {
				synchronized (mytank) {
					if (!mytank.isLive()) {
						int endsum;
						finishPoints[0] = 10;
						finishPoints[1] = 30 - enemyTanks.size();
						finishPoints[2] = missiles.size();
						finishPoints[3] = 20;
						gameBody.setAddedPoints(finishPoints[0],
								finishPoints[1], finishPoints[2],
								finishPoints[3]);
						gameBody.setFinished(TANK_GAME_NUMBER);
						// JOptionPane endGame = new JOptionPane();
						// endsum = endGame.showConfirmDialog(TankWar.this,
						// "您输了,想继续吗？", "游戏结束", JOptionPane.YES_NO_OPTION);
						// if (endsum == JOptionPane.NO_OPTION)
						// System.exit(0);
						// else {
						// mytank = new Tank(200, 200, true, TankWar.this);
						// }
					}
				}

			}
		}
	}

	// private class Key extends KeyAdapter {
	public void keyPressed(KeyEvent e) {
		mytank.keypressed(e);
	}

	public void keyReleased(KeyEvent e) {
		mytank.keyreleased(e);
		synchronized (enemyTanks) {
			if (e.getKeyCode() == KeyEvent.VK_SHIFT)
				for (int i = 0; i < ADDTANKS; i++) {
					enemyTanks.add(new Tank(100 + 50 * i, 300, false,
							Tank.Direction.D, TankWar.this));
				}
		}
	}

	// }

	public int[] getFinishPoints() {
		return finishPoints;
	}
}
