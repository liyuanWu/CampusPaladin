package game.npc;

import game.Game;
import game.map.MapReader;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.Serializable;
import java.util.Random;
import java.util.Scanner;

import javax.imageio.ImageIO;

import system.GameCommon;

public class Npc implements Serializable {
	public static final String npcImage = "npc" + File.separator;
	public static final int npcMostStep = 8;
	public static final int notWalk = 0;
	private Game game;
	private String fileName;
	private String name;// NPC名称
	private String imgName;// NPC图片名称
	private Image img;// NPC图片
	private int mapNumber;// NPC所在地图编号
	private boolean stay;// 是否是静态NPC
	private int npcWidth;
	private int npcHeight;
	private int BirthX;
	private int BirthY;
	private int[][] actionRoad = new int[MapReader.floorX][MapReader.floorY];// NPC能走的路
	private int speed;// NPC的速度
	private int x;
	private int y;
	private int firstX;
	private int firstY;

	// NPC行走
	private Random rand = new Random();

	// NPC移动
	enum Direction {
		DOWN, LEFT, RIGHT, UP
	}

	private Direction direction = Direction.LEFT;
	private int step;
	private int positionX;
	private int positionY;
	private int oldX;
	private int oldY;

	// NPC移动结束
	public Npc(int npcNumber, Game game) throws FileNotFoundException {
		this.game = game;
		fileName = "npc" + File.separator + npcNumber + ".txt";
		Scanner sc = new Scanner(new File(fileName));
		sc.next();
		name = sc.next();
		sc.next();
		imgName = sc.next();
		sc.next();
		mapNumber = sc.nextInt();
		sc.next();
		stay = sc.nextBoolean();
		sc.next();
		BirthX = sc.nextInt();
		BirthY = sc.nextInt();
		x = BirthX * GameCommon.GAME_CHIP_X;
		y = BirthY * GameCommon.GAME_CHIP_Y;
		sc.next();
		for (int i = 0; i < MapReader.floorX; i++)
			for (int j = 0; j < MapReader.floorY; j++)
				actionRoad[j][i] = sc.nextInt();
		sc.next();
		speed = sc.nextInt();
		sc.close();
		try {
			String a = npcImage + imgName;
			img = ImageIO.read(new File(npcImage + imgName + ".png"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		npcWidth = img.getWidth(game) / 4;
		npcHeight = img.getHeight(game) / 4;
	}

	/**
	 * npc的移动
	 */
	public void move() {
		oldX = x;
		oldY = y;
		if (rand.nextInt(100) > 98 && step == 0) {
			direction = getRandomDirection();
		}
		if (rand.nextInt(100) > 98 && step == 0) {
			step = 3;
		}
		if (step != 0) {
			if (direction == Direction.DOWN) {
				y += GameCommon.GAME_CHIP_Y / 8;
			} else if (direction == Direction.UP) {
				y -= GameCommon.GAME_CHIP_Y / 8;
			} else if (direction == Direction.LEFT) {
				x -= GameCommon.GAME_CHIP_X / 8;
			} else if (direction == Direction.RIGHT) {
				x += GameCommon.GAME_CHIP_Y / 8;
			}
			step--;
		}
		collidePath();
	}

	private Direction getRandomDirection() {
		int option = rand.nextInt(4);
		switch (option) {
		case 0:
			return Direction.UP;
		case 1:
			return Direction.DOWN;
		case 2:
			return Direction.LEFT;
		case 3:
			return Direction.RIGHT;
		default:
			return Direction.DOWN;
		}
	}

	/**
	 * 决定Npc图片位置
	 */
	private void imagePosition() {
		positionX = step * npcWidth;
		positionY = direction.ordinal() * npcHeight;
	}

	// NPC移动方法结束

	/**
	 * 画出NPC
	 */
	public void draw(Graphics g) {		
			move();
			imagePosition();
			g.drawImage(img, x + firstX, y + firstY, x + firstX
					+ GameCommon.GAME_CHIP_X, y + firstY
					+ GameCommon.GAME_CHIP_X, positionX, positionY, positionX
					+ npcWidth, positionY + npcHeight, game);		

	}

	/**
	 * NPC碰撞问题
	 */
	public boolean collidePath() {
		int a1, a2, a3, a4;// 记录人物4个角在数组中的位置x
		int b1, b2, b3, b4;// 记录人物4个角在数组中的位置y
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
		if (actionRoad[a1][b1] == notWalk || actionRoad[a2][b2] == notWalk
				|| actionRoad[a3][b3] == notWalk
				|| actionRoad[a4][b4] == notWalk) {
			x = oldX;
			y = oldY;
			step = 0;
			return true;
		}
		return false;
	}

	public void setFirstX(int firstX) {
		this.firstX = firstX;
	}

	public void setFirstY(int firstY) {
		this.firstY = firstY;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	/**
	 * 与Role的碰撞
	 */
	public void collideRole() {
		x = oldX;
		y = oldY;
		step = 0;
	}

	public Rectangle getRectangle() {
		return new Rectangle(x, y, GameCommon.GAME_CHIP_X,
				GameCommon.GAME_CHIP_Y);
	}
}