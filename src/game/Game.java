package game;

import java.awt.Color;

import game.Role.Direction;
import game.littleGamePanel.*;
import game.map.MapDraw;
import game.npc.NpcList;
import game.plot.PlotList;
import game.plot.PlotTree;
import game.talk.TalkList;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.KeyEventDispatcher;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import javax.swing.*;

import rolePanel.RolePointsPanel;
import system.GameCommon;
import inter.Drawable;

public class Game extends JPanel implements Drawable, Runnable, Serializable {
	private GameBody gameBody;
	// *游戏运行状态
	private boolean gaming = true;// 是否在运行
	private boolean pause = false;// 是否暂停
	// *游戏保存文件名
	private static final String SAVE_FILE = "save" + File.separator;
	// *主角人物
	private Role role;
	// *NPC
	private NpcList npcList = new NpcList(this);
	// *是否要重绘地图
	private boolean repaintMap = true;
	// *map绘制工具
	private MapDraw mapDraw;
	// *当前地图编号
	private int mapNumber = 0;
	// *是否需要更换地图
	private boolean turnMap = false;
	// *是否启动小游戏
	private boolean startLittleGame = false;
	// *PLOT剧情列表
	private PlotList plotList;
	// *对话参数
	private TalkList talkList = new TalkList();
	// *是否启动对话
	private boolean startTalk = false;
	// *对话响应按键
	private boolean endTalk = true;
	private boolean talking = false;
	// *对话的编号
	private int talkNumber = 0;
	// *保存存档名的string
	private String[] saveString = new String[RolePointsPanel.SAVE_NUMBER];

	public Game(GameBody gameBody) throws FileNotFoundException {
		for (int i = 0; i < RolePointsPanel.SAVE_NUMBER; i++) {
			this.saveString[i] = "空";
		}
		role = new Role(0, 0, 0, this, 7,2, 0);
		mapDraw = new MapDraw(this, this.role, this.npcList);
		this.gameBody = gameBody;
		this.plotList = new PlotList(this);
		plotList.setRoleType(role.getRoleType());
		this.setBackground(Color.WHITE);
		this.setLayout(null);
		this.setFocusable(gaming);
	}

	public void boss() {
		plotList.isBOSS = true;
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.draw(g);
	}

	public void littleGameBegin() {
		if (startLittleGame) {
			synchronized (this) {
				gameBody.turnPanel();
			}
			startLittleGame = false;
		}
	}

	public void talkBegin(Graphics g) {
		if (startTalk)
			talkList.talk(g, this);
		if (talkList.isEndTalk()) {
			this.endTalk = true;
			this.endTalking();
			startTalk = false;
		} else
			this.endTalk = false;
	}

	public void draw(Graphics g) {
		mapDraw.draw(g, mapNumber, turnMap);
		role.draw(g);
		npcList.draw(g);
		talkBegin(g);
		mapDraw.roleSeted();
	}

	public void setPause(boolean pause) {
		this.pause = pause;
	}

	/**
	 * 人物线程
	 */
	@SuppressWarnings("static-access")
	public void run() {
		while (gaming) {
			littleGameBegin();
			repaint();
			try {
				Thread.currentThread().sleep(40);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 让人物停止走动
	 */
	public void roleStop() {
		role.setStop(true);
	}

	/**
	 * 返回mapDraw
	 */
	public MapDraw getMapDraw() {
		return this.mapDraw;
	}

	/**
	 * 返回地图编号
	 */
	public int getMapNumber() {
		return mapNumber;
	}

	public void setMapNumber(int mapNumber) {
		this.mapNumber = mapNumber;
	}

	public void turnMap() {
		this.turnMap = true;
	}

	public void turnMapFinish() {
		this.turnMap = false;
	}

	/**
	 * 启动小游戏
	 */
	public void setLittleGameNumber(int littleGameNumber) {
		gameBody.setPanel(littleGameNumber + 1);
		startLittleGame();
	}

	public void startLittleGame() {
		this.startLittleGame = true;
	}

	public void plotStart(int mapNumber, int x, int y) {
		this.plotList.plotStart(mapNumber, x, y);
	}

	public void talkStart() {
		this.startTalk = true;
		talkList.TalkBegin();
	}

	public void talk(int talkNumber) {
		talkList.setTalkNumber(talkNumber);
	}

	public void talkEnd() {
		this.startTalk = false;
	}

	public boolean isTalk() {
		return startTalk;
	}

	public boolean getTalking() {
		return talking;
	}

	public void endTalking() {
		this.talking = false;
	}

	public boolean getEndTalk() {
		return endTalk;
	}

	// 人物属性方法
	public void addedPoints(int[] addPoint) {
		role.addPoint(addPoint);
	}

	public int[] getPoints() {
		return role.getPoint();
	}

	/**
	 * 键盘控制器
	 */
	public void keyPressed0(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE) {
			this.talking = true;
			role.setStop(false);
			role.move();
			role.setStop(true);
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			if (!role.setDirection(Direction.RIGHT)) {
				// role.setStop(false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			if (!role.setDirection(Direction.LEFT)) {
				// role.setStop(false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			if (!role.setDirection(Direction.UP)) {
				// role.setStop(false);
			}
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			if (!role.setDirection(Direction.DOWN)) {
				// role.setStop(false);
			}
		}
		role.setStop(false);
		if (e.getKeyCode() != KeyEvent.VK_UP
				&& e.getKeyCode() != KeyEvent.VK_DOWN
				&& e.getKeyCode() != KeyEvent.VK_LEFT
				&& e.getKeyCode() != KeyEvent.VK_RIGHT) {
			role.setStop(true);
		}

	}

	public void KeyReleasedTalk(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_SPACE)
			talkList.turnTalk();
	}

	public void keyReleased0(KeyEvent e) {
		if (gameBody.getIsRolePaneling()) {
			if (e.getKeyCode() == KeyEvent.VK_Z) {
				gameBody.getRolePointsPanel().minusWitchPanel();
				return;
			} else if (e.getKeyCode() == KeyEvent.VK_X) {
				gameBody.getRolePointsPanel().addWitchPanel();
				return;
			}
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				if (gameBody.isLoading()) {
					load(gameBody.getLoadPanel().getSaveNumber());
					// gameBody.closeRolePanel();
					gameBody.closeLoadPanel();
					gameBody.setLoading(false);
					return;
				}
				if (gameBody.getRolePointsPanel().isSave()) {
					save(gameBody.getRolePointsPanel().getSaveNumber());
					gameBody.getRolePointsPanel().setSaveName(
							plotList.getPlotStep() + 1);
				}
				if (!gameBody.getRolePointsPanel().isSave())
					load(gameBody.getRolePointsPanel().getSaveNumber());
				gameBody.closeRolePanel();
				gameBody.setLoading(false);
			}
			if (!gameBody.isLoading()) {
				gameBody.getRolePointsPanel().keyReleased(e);
			} else {
				gameBody.getLoadPanel().keyReleased(e);
			}
		}
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE && !gameBody.isLittleGaming()) {
			if (!gameBody.getIsRolePaneling())
				gameBody.openRolePanel();
			else
				gameBody.closeRolePanel();
		}
		if (e.getKeyCode() == KeyEvent.VK_RIGHT
				|| e.getKeyCode() == KeyEvent.VK_LEFT
				|| e.getKeyCode() == KeyEvent.VK_DOWN
				|| e.getKeyCode() == KeyEvent.VK_UP)
			role.setStop(true);
		// if (e.getKeyCode() == KeyEvent.VK_SPACE)
		// this.talking = false;
		if (e.getKeyCode() != KeyEvent.VK_UP
				&& e.getKeyCode() != KeyEvent.VK_DOWN
				&& e.getKeyCode() != KeyEvent.VK_LEFT
				&& e.getKeyCode() != KeyEvent.VK_RIGHT) {
			role.setStop(true);
		}
	}

	public void keyTyped0(KeyEvent e) {

	}

	public void allStop() {
		role.setStop(true);
	}

	/*
	 * 游戏的保存与读取
	 */
	public void save(int saveNumber) {
		try {
			ObjectOutputStream oos = new ObjectOutputStream(
					new FileOutputStream(new File(SAVE_FILE + "save"
							+ saveNumber + ".txt")));
			oos.writeObject(plotList.getPlotLine());
			oos.writeInt(plotList.getPlotStep());
			oos.writeObject(plotList.getPlotTree());
			oos.writeObject(saveString);
			oos.writeInt(mapNumber);
			oos.writeInt(role.getRoleGender());
			oos.writeInt(role.getRoleType());
			oos.writeInt(role.getCrown());
			oos.writeInt(role.getSwing());
			oos.writeInt(role.getSword());
			oos.writeInt(role.getMoney());
			oos.writeInt(role.getMapNumber());
			oos.writeInt(role.getFirstX());
			oos.writeInt(role.getFirstY());
			oos.writeInt(role.getX());
			oos.writeInt(role.getY());
			oos.writeInt(role.getBigFirstX());
			oos.writeInt(role.getBigFirstY());
			oos.writeInt(role.getBigX());
			oos.writeInt(role.getBigY());
			oos.writeInt(mapDraw.getFirstX());
			oos.writeInt(mapDraw.getFirstY());
			oos.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void load(int loadNumber) {
		try {
			ObjectInputStream ois = new ObjectInputStream(new FileInputStream(
					new File(SAVE_FILE + "save" + loadNumber + ".txt")));
			this.plotList.setPlotLine((PlotTree) ois.readObject());
			this.plotList.setPlotStep(ois.readInt());
			this.plotList.setPlotTree((int[]) ois.readObject());
			this.saveString = (String[]) ois.readObject();
			this.setMapNumber(ois.readInt());
			this.role.setRoleGender(ois.readInt());
			this.role.setRoleType(ois.readInt());
			this.role.setCrown(ois.readInt());
			this.role.setSwing(ois.readInt());
			this.role.setSword(ois.readInt());
			this.role.setMoney(ois.readInt());
			this.role.setMapNumber(ois.readInt());
			this.role.setFirstX(ois.readInt());
			this.role.setFirstY(ois.readInt());
			this.role.setX(ois.readInt());
			this.role.setY(ois.readInt());
			this.role.setBigFirstX(ois.readInt());
			this.role.setBigFirstY(ois.readInt());
			this.role.setBigX(ois.readInt());
			this.role.setBigY(ois.readInt());
			this.mapDraw.setFirstX(ois.readInt());
			this.mapDraw.setFirstY(ois.readInt());
			ois.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void initPlot() {
		this.plotList.setPlotLine(new PlotTree());
		plotList.getPlotLine().setLine((int) (Math.random() * 3));
	}

	public int getRoleGender() {
		return role.getRoleGender();
	}

	public int getRoleType() {
		return role.getRoleType();
	}

	public void setRoleGender(int roleGender) {
		this.role.setRoleGender(roleGender);
	}

	public void setRoleType(int roleType) {
		this.role.setRoleType(roleType);
	}

	public void addRoleMoney(int money) {
		role.addMoney(money);
	}

	public String[] getSaveString() {
		return saveString;
	}

	public void setSaveString(String[] saveString) {
		this.saveString = saveString;
	}

}
