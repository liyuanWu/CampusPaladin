package game;

import game.map.MapReader;
import inter.Drawable;
import system.GameCommon;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;
import javax.imageio.ImageIO;

public class Role implements Drawable, Serializable {
	// ������ʼ
	public static final String FIRSTXY_FILE = "script" + File.separator
			+ "firstXY.txt";
	private static final int FIRST_MONEY = 100;
	public static final int ROLE_WIDTH = 80;
	public static final int ROLE_HEIGHT = 80;
	// ��������
	// *Game����
	private Game game;

	// *����ͼƬ��Ϣ
	private String imageFile;
	private int[] imageLocationX = { 0, 80, 160, 240 };
	private int[] imageLocationY = { 0, 80, 160, 240 };
	private transient Image RoleImage;
	private transient Image RoleImage2;
	// *��������
	private int roleType;
	private int roleGender;
	private int crown;
	private int sword;
	private int swing;
	private int money;
	// *����λ��
	private int[] face = new int[8];
	private int outSmallMap = 0;
	private int firstX = 0;
	private int firstY = 0;
	private int x;// �����first_X��ֵ
	private int y;// �����first_Y��ֵ
	private int bigX;// ���ͼ�ϵ�Xֵ
	private int bigY;// ���ͼ�ϵ�Yֵ
	private int bigFirstX;// ���ͼ�ϵ�FirstXֵ
	private int bigFirstY;// ���ͼ�ϵ�FirstYֵ
	// *���ļ�����С��ͼ�ĳ����ص�
	private int[] smallFirstX = new int[GameCommon.MAP_NUMBER - 1];
	private int[] smallFirstY = new int[GameCommon.MAP_NUMBER - 1];
	private int[] smallX = new int[GameCommon.MAP_NUMBER - 1];
	private int[] smallY = new int[GameCommon.MAP_NUMBER - 1];
	// *�����ж�ͼƬλ��
	private int positionX;
	private int positionY;

	// *�����ж�����
	enum Direction {
		DOWN, LEFT, RIGHT, UP
	}

	private Direction direction = Direction.DOWN;
	private boolean stop = true;
	private int step = 0;// ÿһ����ͼƬ��ʾ
	private int stepInfo = 0;// ��ÿ����֮����4��С������
	private int oldX;
	private int oldY;
	// *�����ͼλ��
	private int mapNumber = 0;// ��ʾ��ǰ��ͼ���
	private MapReader mapReader = new MapReader();
	private int[][] floor = new int[MapReader.floorX][MapReader.floorY];
	// �����ڵ�ͼ�����е���������ʱ��������
	private int[] notWalk = { 0, 2, 3, 4, 5 };
	// ���������ͼ��
	private boolean standHere = false;
	private EndMap end = EndMap.NO;
	private EndMap endMap = EndMap.NO;

	public enum EndMap {
		UP, DOWN, RIGHT, LEFT, NO
	}

	/**
	 * ���췽��
	 */
	public Role(int crown, int sword, int swing, Game game, int gender) {
		this.roleGender = gender;
		this.game = game;
		this.crown = crown;
		this.sword = sword;
		this.swing = swing;
		this.money = FIRST_MONEY;
		this.positionX = imageLocationX[0];
		this.positionY = imageLocationY[0];
		setMapNumber(0);
		try {
			RoleImage = ImageIO.read(new File("maleRole.png"));
			RoleImage2 = ImageIO.read(new File("femaleRole.png"));
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		try {
			// if (gender == 0)
			// imageFile = "maleRole.png";
			// else
			// imageFile = "femaleRole.png";
			// RoleImage = ImageIO.read(new File(imageFile));
			Scanner sc = new Scanner(new File(FIRSTXY_FILE));
			for (int i = 0; i < GameCommon.MAP_NUMBER - 1; i++) {
				smallFirstX[i] = sc.nextInt() * GameCommon.GAME_CHIP_X;
				smallFirstY[i] = sc.nextInt() * GameCommon.GAME_CHIP_Y;
				smallX[i] = sc.nextInt() * GameCommon.GAME_CHIP_X;
				smallY[i] = sc.nextInt() * GameCommon.GAME_CHIP_Y;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * ���ع��췽��
	 */
	public Role(int authority, int activity, int athletics, Game game, int x,
			int y, int gender) {
		this(authority, activity, athletics, game, gender);
		this.x = x * GameCommon.GAME_CHIP_X;
		this.y = y * GameCommon.GAME_CHIP_Y;
	}

	//
	// ������߷���ͼƬ��ʾ���Լ���ײ
	//

	/**
	 * �����ж�
	 */
	public void move() {
		if (stop)
			return;
		oldX = x;
		oldY = y;
		if (direction == Direction.DOWN) {
			y += GameCommon.GAME_CHIP_Y / 8;
		} else if (direction == Direction.UP) {
			y -= GameCommon.GAME_CHIP_Y / 8;
		} else if (direction == Direction.LEFT) {
			x -= GameCommon.GAME_CHIP_X / 8;
		} else if (direction == Direction.RIGHT) {
			x += GameCommon.GAME_CHIP_Y / 8;
		}
		isStandHere();
		if (standHere)
			standHere();
		collidePath();
		imagePosition();
	}

	public Direction getDirection() {
		return direction;
	}

	protected boolean isStop() {
		return stop;
	}

	protected void setStop(boolean stop) {
		this.stop = stop;
	}

	/**
	 * ���������ص�ֵ�ǣ����Ƿ�ı��˷���
	 * 
	 * @param direction
	 * @return
	 */
	public boolean setDirection(Direction direction) {
		if (this.direction != direction) {
			this.direction = direction;
			imagePositionStop();
			return true;
		}
		return false;
	}

	/**
	 * �ж�������ͼƬ�ϵ�λ��
	 */
	private void imagePosition() {
		stepInfo++;
		if (stepInfo > 7) {
			stepInfo = 0;
			step++;
			if (step > 3)
				step = 0;
		}
		positionX = step * ROLE_WIDTH;
		positionY = direction.ordinal() * ROLE_HEIGHT;
	}

	/**
	 * ֹͣʱ��ת��
	 */
	private void imagePositionStop() {
		positionY = direction.ordinal() * ROLE_HEIGHT;
	}

	/**
	 * �����������ڵ�ͼ���,�����ص�ͼ��Ϣ
	 */
	public void setMapNumber(int mapNumber) {
		this.mapNumber = mapNumber;
		floor = mapReader.mapRead("map" + mapNumber);
	}

	/**
	 * ������������
	 */
	public void draw(Graphics g) {
		if (outSmallMap > 0) {
			outSmallMap--;
		} else
			move();
		outOfBound();
		if (this.roleGender == 0)
			g.drawImage(RoleImage, x + firstX, y + firstY, x + firstX
					+ GameCommon.GAME_CHIP_X, y + firstY
					+ GameCommon.GAME_CHIP_Y, positionX, positionY, positionX
					+ ROLE_WIDTH, positionY + ROLE_HEIGHT, this.game);
		else
			g.drawImage(RoleImage2, x + firstX, y + firstY, x + firstX
					+ GameCommon.GAME_CHIP_X, y + firstY
					+ GameCommon.GAME_CHIP_Y, positionX, positionY, positionX
					+ ROLE_WIDTH, positionY + ROLE_HEIGHT, this.game);
	}

	// ���ڵ�ͼ��

	public void setFirstX(int firstX) {
		this.firstX = firstX;
	}

	public void setFirstY(int firstY) {
		this.firstY = firstY;
	}

	public int getFirstX() {
		return firstX;
	}

	public int getFirstY() {
		return firstY;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getCrown() {
		return crown;
	}

	public void setCrown(int crown) {
		this.crown = crown;
	}

	public int getSword() {
		return sword;
	}

	public void setSword(int sword) {
		this.sword = sword;
	}

	public int getSwing() {
		return swing;
	}

	public void setSwing(int swing) {
		this.swing = swing;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

	public int getBigX() {
		return bigX;
	}

	public void setBigX(int bigX) {
		this.bigX = bigX;
	}

	public int getBigY() {
		return bigY;
	}

	public void setBigY(int bigY) {
		this.bigY = bigY;
	}

	public int getBigFirstX() {
		return bigFirstX;
	}

	public void setBigFirstX(int bigFirstX) {
		this.bigFirstX = bigFirstX;
	}

	public int getBigFirstY() {
		return bigFirstY;
	}

	public void setBigFirstY(int bigFirstY) {
		this.bigFirstY = bigFirstY;
	}

	public int getMapNumber() {
		return mapNumber;
	}

	/**
	 * ����������λ�ò�������ͼ��
	 */
	private void standHere() {
		firstX = firstX - x + oldX;
		firstY = firstY - y + oldY;
	}

	public boolean getStandHere() {
		return standHere;
	}

	private void isStandHere() {
		if (x + firstX < GameCommon.GAME_WIDTH / 12)
			end = EndMap.LEFT;
		else if (x + firstX > GameCommon.GAME_WIDTH * 11 / 12)
			end = EndMap.RIGHT;
		else if (y + firstY < GameCommon.GAME_HEIGHT / 9)
			end = EndMap.UP;
		else if (y + firstY > GameCommon.GAME_HEIGHT * 5 / 6)
			end = EndMap.DOWN;
		else
			end = EndMap.NO;
		if (end == EndMap.NO) {
			setStandHere(false);
		} else if (end == EndMap.LEFT) {
			setStandHere(true);
			if (endMap == EndMap.LEFT)
				setStandHere(false);
		} else if (end == EndMap.RIGHT) {
			setStandHere(true);
			if (endMap == EndMap.RIGHT)
				setStandHere(false);
		} else if (end == EndMap.UP) {
			setStandHere(true);
			if (endMap == EndMap.UP)
				setStandHere(false);
		} else if (end == EndMap.DOWN) {
			setStandHere(true);
			if (endMap == EndMap.DOWN)
				setStandHere(false);
		}
	}

	public void setStandHere(boolean standHere) {
		this.standHere = standHere;
	}

	public void setEndMap(EndMap endMap) {
		this.endMap = endMap;
	}

	// ��ͼ������

	/**
	 * ������ﳬԽ�߽�
	 */
	public void outOfBound() {
		if (x + firstX < 0)
			x = 0;
		else if (x + firstX > GameCommon.GAME_WIDTH - GameCommon.GAME_CHIP_X)
			x = GameCommon.GAME_WIDTH - GameCommon.GAME_CHIP_X - firstX;
		else if (y + firstY < 0)
			y = 0 - firstY;
		else if (y + firstY > GameCommon.GAME_HEIGHT - GameCommon.GAME_CHIP_Y
				* 2)
			y = GameCommon.GAME_HEIGHT - GameCommon.GAME_CHIP_Y * 2 - firstY;
	}

	/**
	 * ����·�����߷�����ײ��
	 */
	public boolean collidePath() {
		int a1, a2, a3, a4;// ��¼����4�����������е�λ��x
		int b1, b2, b3, b4;// ��¼����4�����������е�λ��y
		a1 = (x + GameCommon.ROLE_PIECE) / GameCommon.GAME_CHIP_X;
		a2 = (x + GameCommon.GAME_CHIP_X - GameCommon.ROLE_PIECE)
				/ GameCommon.GAME_CHIP_X;
		a3 = a1;
		a4 = a2;
		b1 = (y + GameCommon.ROLE_PIECE) / GameCommon.GAME_CHIP_Y;
		b2 = b1;
		b3 = (y + GameCommon.GAME_CHIP_Y - GameCommon.ROLE_PIECE)
				/ GameCommon.GAME_CHIP_Y;
		b4 = b3;

		for (int i = 0; i < notWalk.length; i++) {
			for (int j = 2; j < GameCommon.MAP_MAX_NUMBER; j++) {
				if ((floor[a1][b1] == j || floor[a2][b2] == j
						|| floor[a3][b3] == j || floor[a4][b4] == j)
						&& (j != GameCommon.PLOT_MAP_NUMBER)
						&& (j != GameCommon.DOOR_MAP_NUMBER)) {
					mapNumber = j - 1;
					this.setMapNumber(mapNumber);
					this.game.setMapNumber(mapNumber);
					this.game.turnMap();
					bigX = oldX;
					bigY = oldY;
					bigFirstX = firstX;
					bigFirstY = firstY;
					firstX = smallFirstX[j - 2];
					firstY = smallFirstY[j - 2];
					x = smallX[j - 2];
					y = smallY[j - 2];
					game.getMapDraw().setFirstX(firstX);
					game.getMapDraw().setFirstY(firstY);
					return true;
				} else if ((floor[a1][b1] == GameCommon.DOOR_MAP_NUMBER
						|| floor[a2][b2] == GameCommon.DOOR_MAP_NUMBER
						|| floor[a3][b3] == GameCommon.DOOR_MAP_NUMBER || floor[a4][b4] == GameCommon.DOOR_MAP_NUMBER)) {
					mapNumber = 0;
					this.setMapNumber(mapNumber);
					this.game.setMapNumber(mapNumber);
					this.game.turnMap();
					x = bigX;
					y = bigY;
					firstX = bigFirstX;
					firstY = bigFirstY;
					switch (face[mapNumber]) {
					case 1:
						this.setDirection(Direction.DOWN);
						outSmallMap = 20;
						move();
						break;
					case 2:
						this.setDirection(Direction.LEFT);
						outSmallMap = 20;
						move();
						break;
					case 3:
						this.setDirection(Direction.RIGHT);
						outSmallMap = 20;
						move();
						break;
					case 4:
						this.setDirection(Direction.UP);
						outSmallMap = 20;
						move();
						break;
					default:
					}
					game.getMapDraw().setFirstX(firstX);
					game.getMapDraw().setFirstY(firstY);
					return true;
				}
			}
			if (floor[a1][b1] == GameCommon.PLOT_MAP_NUMBER)
				game.plotStart(mapNumber, a1, b1);
			else if (floor[a2][b2] == GameCommon.PLOT_MAP_NUMBER)
				game.plotStart(mapNumber, a2, b2);
			else if (floor[a3][b3] == GameCommon.PLOT_MAP_NUMBER)
				game.plotStart(mapNumber, a3, b3);
			else if (floor[a4][b4] == GameCommon.PLOT_MAP_NUMBER)
				game.plotStart(mapNumber, a4, b4);
			if (floor[a1][b1] == notWalk[i] || floor[a2][b2] == notWalk[i]
					|| floor[a3][b3] == notWalk[i]
					|| floor[a4][b4] == notWalk[i]) {
				x = oldX;
				y = oldY;
				return true;
			}
		}
		return false;
	}

	/**
	 * ��NPC����ײ
	 */
	public void collideNpc() {
		x = oldX;
		y = oldY;
		// ��һ�������ֲ���ײ��Role���˵�bug
		if (stop) {
			if (direction == Direction.DOWN) {
				y += GameCommon.GAME_CHIP_Y / 8;
			} else if (direction == Direction.UP) {
				y -= GameCommon.GAME_CHIP_Y / 8;
			} else if (direction == Direction.LEFT) {
				x -= GameCommon.GAME_CHIP_X / 8;
			} else if (direction == Direction.RIGHT) {
				x += GameCommon.GAME_CHIP_Y / 8;
			}
		}
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, GameCommon.GAME_CHIP_X
				- GameCommon.GAME_SMALL_CHIP_X, GameCommon.GAME_CHIP_Y
				- GameCommon.GAME_SMALL_CHIP_Y);
	}

	//
	// ��������Զ�Ӧ�ķ���
	//

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getRoleGender() {

		return roleGender;
	}

	public void setRoleGender(int roleGender) {
		this.roleGender = roleGender;
	}

	public int[] getPoint() {
		int[] point = new int[4];
		point[0] = crown;
		point[1] = sword;
		point[2] = swing;
		point[3] = money;
		return point;
	}

	public void setPoint(int[] point) {
		crown = point[0];
		sword = point[1];
		swing = point[2];
		money = point[3];
	}

	public void addPoint(int[] addPoint) {
		crown += addPoint[0];
		sword += addPoint[1];
		swing += addPoint[2];
		money += addPoint[3];
	}

	public void addMoney(int money) {
		money += money;
	}
}
