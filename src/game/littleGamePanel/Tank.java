package game.littleGamePanel;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.math.*;
import java.awt.*;
import java.util.List;
import java.util.ArrayList;
import java.util.Vector;
import java.util.Random;

import javax.imageio.ImageIO;

public class Tank {
	public static final int WIDTH = 45, HEIGHT = 45;
	public static final int XSPEED = 5, YSPEED = 5;
	public static final int MAXSTEP = 25;
	public static final int FULL_LIFE = 50;
	private TankWar tankWar;
	private int x, y; // 坦克坐标
	private int oldX, oldY;// 坦克前一步坐标
	private Image myTank;
	private Image enemyTank;
	private int myImageWidthPiece;
	private int myImageHeightPiece;
	private int enemyImageWidthPiece;
	private int enemyImageHeightPiece;

	enum Direction {
		L, LU, U, RU, R, RD, D, LD, STOP
	};// 坦克的方向

	private static Random rand = new Random();// 方向随机值
	private int step;
	private Direction dir = Direction.STOP;
	private boolean good;// 坦克的好坏
	protected int bloodlength = FULL_LIFE;// 坦克的血量
	private Blood blood = new Blood(x, y - Blood.HEIGHT, bloodlength);// 坦克血量标示
	private boolean live = true;// 坦克的生死
	public Direction ptdir = Direction.R;// 炮筒方向
	private boolean L = false, U = false, R = false, D = false;// 按键的boolean值

	public Tank(int x, int y, boolean good, TankWar tankWar) {
		this.x = x;
		this.y = y;
		this.tankWar = tankWar;
		this.good = good;

	}

	public Tank(int x, int y, boolean good, Direction direction, TankWar tankWar) {
		this.x = x;
		this.y = y;
		this.good = good;
		this.dir = direction;
		this.tankWar = tankWar;

	}

	public void draw(Graphics g) {

		if (live != true)
			return;
		Color c = g.getColor();
		if (good == true) {
			// g.setColor(Color.RED);
			try {
				myTank = ImageIO.read(new File("mytank.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			myImageWidthPiece = (int) (myTank.getWidth(null) / 4);
			myImageHeightPiece = (int) (myTank.getHeight(null) / 4);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 0, 0,
					myImageWidthPiece, myImageHeightPiece, null);
		}

		else {
			try {
				enemyTank = ImageIO.read(new File("enemytank.png"));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			enemyImageWidthPiece = (int) (enemyTank.getWidth(null) / 4);
			enemyImageHeightPiece = (int) (enemyTank.getHeight(null) / 4);
			g.drawImage(enemyTank, x, y, x + WIDTH, y + HEIGHT, 0, 0,
					enemyImageWidthPiece, enemyImageHeightPiece, null);

		}
		// g.setColor(Color.BLUE);
		// g.fillOval(x, y, WIDTH, HEIGHT);

		g.setColor(c);
		if (good) {
			blood.setBlood(bloodlength);
			blood.setXY(x, y);
			blood.draw(g);
		}
		switch (ptdir) {
		case L:
			// g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT / 2);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 0,
					myImageHeightPiece, myImageWidthPiece,
					2 * myImageHeightPiece, null);
			break;
		case LU:
			// g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT,
					3 * myImageWidthPiece, 1 * myImageHeightPiece,
					4 * myImageWidthPiece, 2 * myImageHeightPiece, null);
			break;
		case U:
			// g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 0,
					3 * myImageHeightPiece,  myImageWidthPiece,
					4 * myImageHeightPiece, null);
			;
			break;
		case RU:
			//g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 3*myImageWidthPiece,
					2 * myImageHeightPiece, 4 * myImageWidthPiece,
					3 * myImageHeightPiece, null);
			break;
		case R:
			//g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT / 2);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 2*myImageWidthPiece,
					2 * myImageHeightPiece, 3 * myImageWidthPiece,
					3 * myImageHeightPiece, null);
			break;
		case RD:
			//g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH, y + HEIGHT);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, myImageWidthPiece,
					2 * myImageHeightPiece, 2 * myImageWidthPiece,
					3 * myImageHeightPiece, null);
			break;
		case D:
			//g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x + WIDTH / 2, y + HEIGHT);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, 0,
					0, myImageWidthPiece,
					 myImageHeightPiece, null);
			break;
		case LD:
			//g.drawLine(x + WIDTH / 2, y + HEIGHT / 2, x, y + HEIGHT);
			g.drawImage(myTank, x, y, x + WIDTH, y + HEIGHT, myImageWidthPiece,
					myImageHeightPiece, 2 * myImageWidthPiece,
					2 * myImageHeightPiece, null);
			break;
		}
		move();
	}

	protected Rectangle getRectangle() {
		return new Rectangle(x, y, WIDTH, HEIGHT);
	}

	protected void setLive(boolean live) {
		this.live = live;
	}

	protected boolean isLive() {
		return live;
	}

	protected void toBeLive() {
		this.bloodlength = FULL_LIFE;
	}

	protected boolean isGood() {
		return good;
	}

	private void move() {
		oldX = x;
		oldY = y;
		switch (dir) {
		case L:
			x -= XSPEED;
			break;
		case LU:
			x -= XSPEED;
			y -= YSPEED;
			break;
		case U:
			y -= YSPEED;
			break;
		case RU:
			x += XSPEED;
			y -= YSPEED;
			break;
		case R:
			x += XSPEED;
			break;
		case RD:
			x += XSPEED;
			y += YSPEED;
			break;
		case D:
			y += YSPEED;
			break;
		case LD:
			x -= XSPEED;
			y += YSPEED;
			break;
		case STOP:
			break;
		}
		if (x < 0)
			x = 0;
		if (x > TankWar.WIDTH - WIDTH - 5)
			x = TankWar.WIDTH - WIDTH - 5;
		if (y < 0)
			y = 0;
		if (y > (TankWar.HEIGHT - HEIGHT - 30))
			y = TankWar.HEIGHT - HEIGHT - 30;
		if (dir != Direction.STOP)
			this.ptdir = dir;
		if (!good) {
			synchronized (tankWar.missiles) {
				if (rand.nextInt(40) >= 38)
					fire();
			}
			if (step == 0) {
				Direction[] dirs = Direction.values();
				int rn = rand.nextInt(dirs.length);
				dir = dirs[rn];
				step = rand.nextInt(MAXSTEP) + 3;
			} else
				step--;
		}
	}

	private void stay() {
		this.x = oldX;
		this.y = oldY;
	}

	public void refreshStep() {
		step = 0;
	}

	private void fire() {
		if (!live)
			return;
		Missile ms = new Missile(x + WIDTH / 2 - Missile.WIDTH / 2, y + HEIGHT
				/ 2 - Missile.HEIGHT / 2, ptdir, this.good, tankWar);
		tankWar.missiles.add(ms);
	}

	public boolean collidesWithWalls(List<Wall> walls) {
		for (int i = 0; i < walls.size(); i++) {
			Wall wall = walls.get(i);
			if (this.getRectangle().intersects(wall.getRectangle())) {
				stay();
				refreshStep();
				return true;
			}
		}
		return false;
	}

	public boolean collidesWithTank(Tank tank) {
		if (this.getRectangle().intersects(tank.getRectangle())) {
			stay();
			refreshStep();
			return true;
		}
		return false;
	}

	public boolean collidesWithTanks(List<Tank> tanks) {
		for (int i = 0; i < tanks.size(); i++) {
			Tank tank = tanks.get(i);
			if (tank != this)
				collidesWithTank(tank);
		}
		return false;
	}

	public boolean eatLive(List<LifePoint> lifePoints) {
		for (int i = 0; i < lifePoints.size(); i++) {
			LifePoint lifePoint = lifePoints.get(i);
			if (this.getRectangle().intersects(lifePoint.getRectangle())) {
				lifePoint.setLive(false);
				this.bloodlength = FULL_LIFE;
				return true;
			}
		}
		return false;
	}

	private void direction() {
		if (L && !R && !U && !D)
			dir = Direction.L;
		else if (!L && R && !U && !D)
			dir = Direction.R;
		else if (!L && !R && U && !D)
			dir = Direction.U;
		else if (!L && !R && !U && D)
			dir = Direction.D;
		else if (L && !R && U && !D)
			dir = Direction.LU;
		else if (L && !R && !U && D)
			dir = Direction.LD;
		else if (!L && R && U && !D)
			dir = Direction.RU;
		else if (!L && R && !U && D)
			dir = Direction.RD;
		else if (!L && !R && !U && !D)
			dir = Direction.STOP;
	}

	public void keypressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			R = true;
			direction();
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			L = true;
			direction();
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			U = true;
			direction();
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			D = true;
			direction();
		}
	}

	public void keyreleased(KeyEvent e) {
		synchronized (tankWar.missiles) {
			if (e.getKeyCode() == KeyEvent.VK_CONTROL) {
				fire();
			} else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
				R = false;
				direction();
			} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
				L = false;
				direction();
			} else if (e.getKeyCode() == KeyEvent.VK_UP) {
				U = false;
				direction();
			} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
				D = false;
				direction();
			}
		}
	}

}
