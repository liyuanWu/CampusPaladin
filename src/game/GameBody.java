package game;

import java.awt.Cursor;

import game.Try.Table;
import game.littleGamePanel.PuzzleLayoutPanel;
import game.littleGamePanel.ShowDirectionPanel;
import game.littleGamePanel.TankWar;
import game.littleGamePanel.TableBomb.Home;
import game.littleGamePanel.TableBomb.connector2;
import game.littleGamePanel.TableBomb.totalGameInter2;
import game.littleGamePanel.doodle.connector;
import game.littleGamePanel.doodle.totalGameInter;
import game.littleGamePanel.eatBeans.ImageAndKeyCon;
import game.plot.PlotTree;
import game.video.videoStack;

import java.awt.CardLayout;
import java.awt.Container;
import java.awt.Font;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.Serializable;

import javax.swing.*;

import media.SoundPlayer;

import rolePanel.LoadPanel;
import rolePanel.RolePointsPanel;
import startPanel.StartPanel;
import system.GameCommon;

public class GameBody extends JFrame implements Runnable, Serializable,
		KeyListener, totalGameInter, totalGameInter2,inter.totalGameInter {
	private boolean gaming = true;
	private boolean turnPanel = false;
	private Game game = new Game(this);
	private Thread gameThread;
	private int[] pointsAdded = new int[4];
	// gamebody线程
	private Thread panelThread;
	// 是否开始启动game线程
	private boolean isStartPanelThread = false;
	// 光标(盈盈)
	private Toolkit tk = Toolkit.getDefaultToolkit();
	// 光标图片
	private Image img = tk.getDefaultToolkit().getImage("光标3.png");
	private Cursor cu = tk.createCustomCursor(img, new Point(16, 16), "stick");
	// 开始面板(盈盈+硕)
	private StartPanel startPanel;
	// 是否仍在开始面板徘徊
	private boolean entering = true;
	// 是否在某个choice(按esc键退出的监听)
	private boolean pressChoice = true;
	// 人物面板
	private RolePointsPanel rolePointsPanel;
	// 性别选择面板(盈盈)
	private boolean videoOnStack;
	//视频面板
	private boolean videoInit;
	//视频是否初始化
	private videoStack videoBox;
	//视频栈
	
	private JFrame frame = new JFrame("性别选择");
	private JPanel genderPanel;// =new JPanel();
	private JLabel front;
	private JButton male;
	private JButton female;
	// load面板
	private LoadPanel loadPanel;
	private boolean loading = false;
	// 是否正显示人物面板
	private boolean isRolePaneling;
	// 拼图游戏(硕)
	private PuzzleLayoutPanel imagePanel;
	// 跑步游戏(硕)
	private ShowDirectionPanel showDirectionPanel;
	// doodle jump(元子)
	private connector o;
	// 打桌子游戏(元子)
	private connector2 o2;
	// 吃豆子游戏(盈盈)
	private ImageAndKeyCon keyboardPanel;
	// 辩论赛(tankwar)
	private TankWar tankWar;
	private int panel = 1;
	private CardLayout layOut = new CardLayout();
	private Container contentPane = this.getContentPane();
	private boolean littleGaming = false;
	private boolean starting = false;

	public Object obj = new Object();
	public Object obj2 = new Object();
	// 游戏是否结束
	private boolean[] littleGameFinished = new boolean[GameCommon.LITTLEGAME_NUMBER + 1];

	// 游戏音乐
	private String[] musicIndex = new String[9];
	private SoundPlayer soundPlayer;

	public GameBody() throws InterruptedException, FileNotFoundException {
		musicIndex[1] = "main";
		musicIndex[2] = "puzzle";
		musicIndex[3] = "running";
		musicIndex[4] = "doodle";
		musicIndex[5] = "beatTable";
		musicIndex[6] = "eatBean";
		musicIndex[7] = "nineRooms";
		musicIndex[8] = "tank";
		// 在构造的时候就开启主界面背景音乐
		soundPlayer = new SoundPlayer("bgm0_title", true);
		soundPlayer.play();
		this.setSize(GameCommon.GAME_WIDTH, GameCommon.GAME_HEIGHT);
		contentPane.setLayout(layOut);
		this.setResizable(false);
		this.setCursor(cu);
		this.setLocation(GameCommon.GAME_X, GameCommon.GAME_Y);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.addKeyListener(this);
		this.requestFocusInWindow();
		for (boolean a : littleGameFinished)
			a = false;
		// 开始界面出现
		starting = true;
		startPanel = new StartPanel(this);
		Thread th = new Thread(startPanel);
		th.start();
		contentPane.add(startPanel, "0");
		contentPane.getComponent(0).setVisible(true);
		layOut.show(contentPane, "0");
		this.setVisible(true);
		// while (starting) {
		// synchronized (obj) {
		// if (!startPanel.getStarting())
		// starting = false;
		// }
		// }
		while (true) {
			if (startPanel.getStarting()) {
				enter(startPanel.getChoice());
				startPanel.setStrating(false);
			}
			if (!entering) {
				break;
			}
		}
		startPanel.setVisible(false);
		layOut.removeLayoutComponent(contentPane.getComponent(0));
		contentPane.remove(startPanel);
		this.setFont(new Font("全新硬笔行书简", Font.BOLD | Font.ITALIC, 24));
		if (loading) {
			try {
				loadPanel = new LoadPanel(game.getPoints(),
						GameCommon.ROLE_PANEL_FILE[game.getRoleType()],
						game.getSaveString());
				contentPane.add(loadPanel, "11");
			} catch (IOException e) {
				e.printStackTrace();
			}
			Font font = new Font("Times New Roman", Font.ITALIC, 25);
			this.setFont(font);
			layOut.show(contentPane, "11");

			// contentPane.getComponent(0).setVisible(false);
			// contentPane.getComponent(1).setVisible(true);
			isRolePaneling = true;
			// openRolePanel();
		}
	}

	public CardLayout getCardLayOut() {
		return layOut;
	}

	/**
	 * 当前的panel
	 */
	public void setPanel(int panel) {
		this.panel = panel;
	}

	public void turnPanel() {
		this.turnPanel = true;
	}

	public void run() {
		while (gaming) {
			if(videoOnStack){
				if(!videoInit){
					initVideoPanel();
					videoInit=false;
				}
				layOut.show(contentPane, String.valueOf(panel));
				game.roleStop();
				game.setVisible(false);
				videoOnStack = false;
			}
			
			if (littleGameFinished[panel - 1]) {
				if(panel == 6) {
					int[] points = game.getPoints();
					int max = 0;
					if(points[0] < points[1])
						max = 1;
					if(points[max] < points[2])
						max = 2;
					game.setRoleType(max * 2);
				}
				layOut.show(contentPane, "1");
				contentPane.getComponent(0).setVisible(true);
				// if (panel == 5) {
				// o2.removePanel(this);
				// } else {
				layOut.removeLayoutComponent(contentPane.getComponent(1));
				contentPane.remove(contentPane.getComponent(1));
				// }
				littleGameFinished[panel - 1] = false;
				littleGaming = false;

				// stop little game music
				soundPlayer.stop();
				panel = 1;
				// restart main music
				soundPlayer = new SoundPlayer(musicIndex[1], true);
				soundPlayer.play();

			}
			if (turnPanel) {
				if (!littleGaming) {
					startLittleGame(panel);
					littleGaming = true;
				}
				// if (panel == 5) {
				// layOut.show(contentPane, "20");
				// layOut.show(contentPane, "21");
				// layOut.show(contentPane, "22");
				// } else
				layOut.show(contentPane, String.valueOf(panel));
				game.roleStop();
				game.setVisible(false);
				turnPanel = false;

				// 播放小游戏音乐，关闭主音乐
				soundPlayer.stop();
				soundPlayer = new SoundPlayer(musicIndex[panel], true);
				soundPlayer.play();
			}
		}
	}

	public void enter(int enterNumber) {
		switch (enterNumber) {
		// 开始新游戏
		case 0:
			startPanel.turn(1);
			break;
		// 加载游戏
		case 1:
			gameThread = new Thread(game);
			gameThread.start();
			contentPane.add(game, "1");
			layOut.show(contentPane, "1");
			game.setVisible(true);
			panelThread = new Thread(this);
			panelThread.start();
			game.load(0);
			this.loading = true;
			this.entering = false;
			break;
		case 2:
			startPanel.turn(2);
			break;
		case 3:
			startPanel.turn(3);
			break;
		case 4:
			System.exit(0);
			break;
		default:
			gameThread = new Thread(game);
			gameThread.start();
			contentPane.add(game, "1");
			layOut.show(contentPane, "1");
			game.setVisible(true);
			panelThread = new Thread(this);
			panelThread.start();
			game.initPlot();
			this.entering = false;
			break;
		}
	}

	public boolean isPressChoice() {
		return pressChoice;
	}

	public void setPressChoice(boolean pressChoice) {
		this.pressChoice = pressChoice;
	}

	public boolean isEntering() {
		return entering;
	}

	public void setEntering(boolean entering) {
		this.entering = entering;
	}

	public void startPanelThread() {
		this.isStartPanelThread = true;
	}

	public void genderChoice() {
		Table t = new Table(this);
		t.enterGame();
	}

	public void setGender(int gender) {
		game.setRoleGender(gender);
	}

	public void openRolePanel() {
		try {
			rolePointsPanel = new RolePointsPanel(game.getPoints(),
					GameCommon.ROLE_PANEL_FILE[game.getRoleType()],
					game.getSaveString());
		} catch (IOException e) {
			e.printStackTrace();
		}
		contentPane.add(rolePointsPanel, "10");
		Font font = new Font("Times New Roman", Font.ITALIC, 25);
		this.setFont(font);
		layOut.show(contentPane, "10");
		// contentPane.getComponent(0).setVisible(false);
		// contentPane.getComponent(1).setVisible(true);
		isRolePaneling = true;
		game.save(0);
	}

	public void closeRolePanel() {
		game.setSaveString(rolePointsPanel.getSaveString());
		contentPane.setVisible(true);
		layOut.show(contentPane, "1");
		contentPane.getComponent(0).setVisible(true);
		layOut.removeLayoutComponent(contentPane.getComponent(1));
		isRolePaneling = false;
	}
	
	public void closeLoadPanel() {		
		contentPane.setVisible(true);
		layOut.show(contentPane, "1");
		contentPane.getComponent(0).setVisible(true);
		layOut.removeLayoutComponent(contentPane.getComponent(1));
		isRolePaneling = false;
	}
	

	public void setAddedPoints(int crown, int sword, int wing, int money) {
		pointsAdded[0] = crown;
		pointsAdded[1] = sword;
		pointsAdded[2] = wing;
		pointsAdded[3] = money;
		game.addedPoints(pointsAdded);
		for (int i = 0; i < 4; i++)
			System.out.println(pointsAdded[i]);
	}

	public boolean getIsRolePaneling() {
		return isRolePaneling;
	}

	public void setFinished(int gameNumber) {
		if(gameNumber!=8){
			littleGameFinished[gameNumber] = true;
			videoBox = new videoStack();
			if(videoBox.set(gameNumber, game.getRoleType(),this)){
				setPanel(9);
				videoOnStack = true;
				videoInit = false;
			}else{
				videoBox = null;
			}
		}else{
			littleGameFinished[gameNumber] = true;
		}
	}

	public boolean getFinished(int gameNumber) {
		return littleGameFinished[gameNumber];
	}

	private void initVideoPanel() {
		contentPane.add(videoBox.getPanel(), "30");
	}
	
	public boolean startGame1() {
		int[] points = game.getPoints();
		imagePanel = new PuzzleLayoutPanel(points[0], points[1], points[2],
				points[3], this);
		imagePanel.MousePressed();
		imagePanel.MouseDragged();
		imagePanel.MouseReleased();
		contentPane.add(imagePanel, "2");
		return true;
	}

	public boolean startGame2() {
		int[] points = game.getPoints();
		ImageIcon icon3 = new ImageIcon("finish.png");
		this.showDirectionPanel = new ShowDirectionPanel(points[0], points[1],
				points[2], points[3], this);
		showDirectionPanel.setBounds(0, 0, getWidth(), getHeight());
		contentPane.add(showDirectionPanel, "3");
		return true;
	}

	public boolean startGame3() {
		o = new connector(this, 40, 40, 40);
		contentPane.add(o.getPanel(), "4");
		return true;
	}

	public boolean startGame4() {
		o2 = new Home(this, 40, 40, 40);
		return true;
	}

	public boolean startGame5() {
		keyboardPanel = new ImageAndKeyCon(this);
		contentPane.add(keyboardPanel, "5");
		return true;
	}

	public boolean startGame7() {
		int[] points = game.getPoints();
		tankWar = new TankWar(points[0], points[1], points[2], points[3], this);
		contentPane.add(tankWar, "7");
		return true;
	}

	public void startLittleGame(int panel) {
		switch (panel) {
		case 1:
			break;
		case 2:
			startGame1();
			break;
		case 3:
			startGame2();
			break;
		case 4:
			startGame3();
			break;
		case 5:
			startGame4();
			break;
		case 6:
			startGame5();
			break;
		case 8:
			startGame7();
			break;
		default:
		}
	}

	public boolean isLittleGaming() {
		return littleGaming;
	}

	public void keyPressed(KeyEvent e) {
		if(e.getKeyCode() == KeyEvent.VK_B)
			game.boss();
		if (!pressChoice && entering) {
			startPanel.keyPressed(e);
		} else {
			if (!game.getEndTalk()) {
				game.allStop();
			} else
				switch (panel) {
				case 1:
					game.keyPressed0(e);
					break;
				case 2:
					break;
				case 3:
					if (littleGaming)
						showDirectionPanel.keyPressed2(e);
					break;
				case 4:
					if (littleGaming)
						o.keyPressed(e);
					break;
				case 5:
					if (littleGaming)
						o2.keyPressed(e);
					break;
				case 6:
					if (littleGaming)
						keyboardPanel.keyPressed(e);
					break;
				case 8:
					if (littleGaming)
						tankWar.keyPressed(e);
					break;
				default:
				}
		}
	}

	public void keyReleased(KeyEvent e) {
		synchronized (obj) {
			if (pressChoice && entering) {
				startPanel.keyReleased(e);
			} else {
				if (!game.getEndTalk()) {
					game.KeyReleasedTalk(e);
					game.allStop();
				} else if (starting && entering) {
					startPanel.keyReleased(e);
				} else
					switch (panel) {
					case 1:
						game.keyReleased0(e);
						break;
					case 2:
						break;
					case 3:
						if (littleGaming)
							showDirectionPanel.keyReleased2(e);
						break;
					case 4:
						if (littleGaming)
							o.keyReleased(e);
						break;
					case 5:
						if (littleGaming)
							o2.keyReleased(e);
						break;
					case 8:
						if (littleGaming)
							tankWar.keyReleased(e);
						break;
					default:
					}
			}
		}
	}

	public void keyTyped(KeyEvent e) {
		if (pressChoice) {

		} else if (!game.getEndTalk()) {
			game.allStop();
		} else
			switch (panel) {
			case 1:
				game.keyTyped0(e);
				break;
			case 2:
				break;
			case 3:
				if (littleGaming)
					showDirectionPanel.keyTyped2(e);
				break;
			case 4:
				if (littleGaming)
					o.keyTyped(e);
				break;
			default:
			}
	}

	public RolePointsPanel getRolePointsPanel() {
		return rolePointsPanel;
	}

	public LoadPanel getLoadPanel() {
		return loadPanel;
	}

	public boolean isLoading() {
		return loading;
	}

	public void setLoading(boolean loading) {
		this.loading = loading;
	}

	public static void main(String[] args) throws InterruptedException,
			FileNotFoundException {
		GameBody gamebody = new GameBody();
	}

}