package startPanel;

import game.GameBody;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;
import system.GameCommon;
import media.*;

public class StartPanel extends JPanel implements Runnable, KeyListener {
	public static final int CHOICE_A_X = GameCommon.GAME_WIDTH * 8 / 19;
	public static final int CHOICE_A_Y = GameCommon.GAME_HEIGHT * 5 / 10;
	public static final int CHOICE_SPACE = 15;
	public static final int CHOICE_WIDTH = 150;
	public static final int CHOICE_HEIGHT = 50;
	public static final String START_BACKGROUND_FILE = "start" + File.separator
			+ "background.png";
	public static final String NEW_GAME_FILE = "start" + File.separator
			+ "newGame.png";
	public static final String LOAD_GAME_FILE = "start" + File.separator
			+ "loadGame.png";
	public static final String ROLES_INTRODUCTION_FILE = "start"
			+ File.separator + "rolesIntroduction.png";
	public static final String STORY_INTRODUNTION_FILE = "start"
			+ File.separator + "storyIntroduction.png";
	public static final String EXIT_FILE = "start" + File.separator
			+ "exit.png";

	public static final String GENDER_FILE = "start" + File.separator
			+ "gender.png";
	public static final String ROLES_PICTURE_FILE = "start" + File.separator
			+ "rolesPicture.png";
	public static final String STORY_PICTURE_FILE = "start" + File.separator
			+ "storyPicture.png";
	public static final String MALE_FILE = "start" + File.separator
			+ "male.png";
	public static final String FEMALE_FILE = "start" + File.separator
			+ "female.png";

	public static final String CLOUD1_FILE = "start" + File.separator
			+ "cloud1.png";
	public static final String CLOUD2_FILE = "start" + File.separator
			+ "cloud2.png";
	public static final String CLOUD3_FILE = "start" + File.separator
			+ "cloud3.png";
	public static final String CLOUD4_FILE = "start" + File.separator
			+ "cloud4.png";
	public static final String FOG = "start" + File.separator + "fog.png";
	public static final int CHOICE_NUMBER = 5;

	private List<Choice> choiceList = new ArrayList<Choice>();
	private GameBody gamebody;
	private BufferedImage background;
	private BufferedImage newGame;
	private BufferedImage loadGame;
	private BufferedImage rolesIntroduction;
	private BufferedImage storyIntroduction;
	private BufferedImage exit;
	private BufferedImage gender;
	private BufferedImage rolesPicture;
	private BufferedImage storyPicture;
	private ImageIcon male = new ImageIcon(MALE_FILE);
	private ImageIcon female = new ImageIcon(FEMALE_FILE);
	private BufferedImage[] clouds = new BufferedImage[4];
	private JButton maleButton;
	private JButton femaleButton;
	// private BufferedImage fog;

	// private BufferedImage cloud2;
	private int choice = 0;
	private boolean up;
	private boolean down;
	private boolean starting = false;
	private int[] cloudX = { 0, 150, 100, 300 };
	private int[] cloudY = { 150, 150, 170, 170 };
	private int[] cloudWidth = { 400, 400, 400, 400 };
	private int[] cloudHeight = { 200, 200, 200, 200 };

	private int[] cloudSpeed = { 2, 2, 4, 4 };
	// soundEffect
	private SoundPlayer soundEffectPlayer = new SoundPlayer("curser", false);

	public StartPanel(GameBody gameBody) {
		this.gamebody = gameBody;
		try {
			background = ImageIO.read(new File(START_BACKGROUND_FILE));
			newGame = ImageIO.read(new File(NEW_GAME_FILE));
			loadGame = ImageIO.read(new File(LOAD_GAME_FILE));
			rolesIntroduction = ImageIO.read(new File(ROLES_INTRODUCTION_FILE));
			storyIntroduction = ImageIO.read(new File(STORY_INTRODUNTION_FILE));
			exit = ImageIO.read(new File(EXIT_FILE));
			clouds[0] = ImageIO.read(new File(CLOUD1_FILE));
			clouds[1] = ImageIO.read(new File(CLOUD2_FILE));
			clouds[2] = ImageIO.read(new File(CLOUD3_FILE));
			clouds[3] = ImageIO.read(new File(CLOUD4_FILE));
			gender = ImageIO.read(new File(GENDER_FILE));
			rolesPicture = ImageIO.read(new File(ROLES_PICTURE_FILE));
			storyPicture = ImageIO.read(new File(STORY_PICTURE_FILE));

			// fog = ImageIO.read(new File(FOG));

		} catch (IOException e) {
			e.printStackTrace();
		}
		this.setLayout(null);
		maleButton = new JButton(male);
		femaleButton = new JButton(female);
		this.add(maleButton);
		this.add(femaleButton);

		maleButton.setBounds(150, 150, 200, 200);
		femaleButton.setBounds(450, 150, 200, 200);

		maleButton.setVisible(false);
		femaleButton.setVisible(false);

		// 按键下去之后的监听
		maleButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {	
				choice = 0;
				gamebody.setPressChoice(true);
				maleButton.setVisible(false);
				maleButton.setVisible(false);
				gamebody.setGender(0);
				gamebody.setEntering(false);
				gamebody.enter(10);
				gamebody.requestFocusInWindow();
			}
		});

		femaleButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				choice = 0;
				gamebody.setPressChoice(true);
				maleButton.setVisible(false);
				maleButton.setVisible(false);
				gamebody.setGender(1);
				gamebody.setEntering(false);
				gamebody.enter(10);
				gamebody.requestFocusInWindow();
			}
		});

		choiceList.add(new Choice(newGame, CHOICE_A_X, CHOICE_A_Y, 0));
		choiceList.add(new Choice(loadGame, CHOICE_A_X, CHOICE_A_Y
				+ CHOICE_HEIGHT + CHOICE_SPACE, 1));
		choiceList.add(new Choice(rolesIntroduction, CHOICE_A_X, CHOICE_A_Y
				+ (CHOICE_HEIGHT + CHOICE_SPACE) * 2, 2));
		choiceList.add(new Choice(storyIntroduction, CHOICE_A_X, CHOICE_A_Y
				+ (CHOICE_HEIGHT + CHOICE_SPACE) * 3, 3));
		choiceList.add(new Choice(exit, CHOICE_A_X, CHOICE_A_Y
				+ (CHOICE_HEIGHT + CHOICE_SPACE) * 4, 4));
		this.addKeyListener(this);
	}

	public int getChoice() {
		for (int i = 0; i < CHOICE_NUMBER; i++) {
			if (choiceList.get(i).getPositon() == 0)
				return i;
		}
		return 0;
	}

	private void up() {
		for (Choice choice : choiceList) {
			choice.up();
			if (choice.getStep() == 0) {
				this.up = false;
				this.down = false;
			}
		}
	}

	private void down() {
		for (Choice choice : choiceList) {
			choice.down();
			if (choice.getStep() == 0) {
				this.up = false;
				this.down = false;
			}
		}
	}

	public boolean isDown() {
		return down;
	}

	public boolean getStarting() {
		return starting;
	}

	public void setStrating(boolean starting) {
		this.starting = starting;
	}

	@SuppressWarnings("static-access")
	public void run() {
		while (gamebody.isEntering()) {
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
		gamebody.startPanelThread();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		switch (choice) {
		case 0:
			drawInitImage(g);
			break;
		case 1:
			drawGenderChoice(g);
			break;
		case 2:
			drawRoleIntroduction(g);
			break;
		case 3:
			drawGameIntroduction(g);
			break;

		default:
			break;
		}

	}

	public void drawInitImage(Graphics g) {
		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);

		for (int i = 0; i < 4; i++) {
			if (cloudX[i] >= this.getWidth())
				cloudX[i] = 0;
			g.drawImage(clouds[i], cloudX[i], cloudY[i], cloudX[i]
					+ cloudWidth[i], cloudY[i] + cloudHeight[i], 0, 0,
					getWidth(), getHeight(), this);
			cloudX[i] += cloudSpeed[i];

		}
		for (int i = 0; i < 4; i++)
			if (cloudX[i] == this.getWidth())
				cloudX[i] = 0;

		if (this.up) {
			up();
		}
		if (this.down) {
			down();
		}
		for (Choice choice : choiceList)
			choice.draw(g, this);
		// g.drawImage(fog, 0, 200, getWidth(), getHeight(), 0, 0, getWidth(),
		// getHeight(), this);
	}

	public void turn(int outsideChoice) {
		choice = outsideChoice;
	}

	public void drawGenderChoice(Graphics g) {
		g.drawImage(gender, 0, 0, getWidth(), getHeight(), 0, 0, getWidth(),
				getHeight(), this);
		maleButton.setVisible(true);
		femaleButton.setVisible(true);

	}

	public void drawRoleIntroduction(Graphics g) {
		g.drawImage(rolesPicture, 0, 0, getWidth(), getHeight(), 0, 0,
				rolesPicture.getWidth(), rolesPicture.getHeight(), gamebody);
	}

	public void drawGameIntroduction(Graphics g) {
		g.drawImage(storyPicture, 0, 0, getWidth(), getHeight(), 0, 0,
				storyPicture.getWidth(), storyPicture.getHeight(), gamebody);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			this.choice = 0;
			this.starting = false;
			gamebody.setPressChoice(true);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (!down && !up) {
			if (e.getKeyCode() == KeyEvent.VK_UP)
				this.down = true;
			if (e.getKeyCode() == KeyEvent.VK_DOWN)
				this.up = true;
			if (e.getKeyCode() == KeyEvent.VK_ENTER) {
				this.starting = true;
				gamebody.setPressChoice(false);
			}
		}
		soundEffectPlayer.play();
	}

	public void keyTyped(KeyEvent arg0) {

	}

	// public static void main(String[] args) {
	// StartPanel startPanel = new StartPanel();
	// JFrame frame = new JFrame();
	// frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	// frame.setLocation(100, 100);
	// frame.setSize(800, 600);
	// frame.getContentPane().add(startPanel);
	// frame.setVisible(true);
	// startPanel.setFocusable(true);
	// startPanel.requestFocusInWindow();
	// Thread th = new Thread(startPanel);
	// th.start();
	// }

}

class Choice {
	private BufferedImage image;
	private int x;
	private int y;
	private int addX = 0;
	private int addY = 0;
	private int step = 0;
	private int position;

	public Choice(BufferedImage image, int x, int y, int position) {
		this.image = image;
		this.x = x;
		this.y = y;
		this.position = position;
		if (position == 0) {
			addX = 20;
			addY = 13;
		}
	}

	public int getStep() {
		return step;
	}

	public void draw(Graphics g, StartPanel startPanel) {
		if (step != 0) {
			if (position == 0) {
				g.drawImage(image, x - addX * (4 - step) / 4, y - addY
						* (4 - step) / 4, x + StartPanel.CHOICE_WIDTH + addX
						* (4 - step) / 4, y + StartPanel.CHOICE_HEIGHT + addY
						* (4 - step) / 4, 0, 0, image.getWidth(),
						image.getHeight(), startPanel);
			} else if (position == 1 && !startPanel.isDown()) {
				g.drawImage(image, x - addX * step / 4, y - addY * step / 4, x
						+ StartPanel.CHOICE_WIDTH + addX * step / 4, y
						+ StartPanel.CHOICE_HEIGHT + addY * step / 4, 0, 0,
						image.getWidth(), image.getHeight(), startPanel);
			} else
				g.drawImage(image, x, y, x + StartPanel.CHOICE_WIDTH, y
						+ StartPanel.CHOICE_HEIGHT, 0, 0, image.getWidth(),
						image.getHeight(), startPanel);
		} else {
			if (position == 0) {
				g.drawImage(image, x - addX, y - addY, x
						+ StartPanel.CHOICE_WIDTH + addX, y
						+ StartPanel.CHOICE_HEIGHT + addY, 0, 0,
						image.getWidth(), image.getHeight(), startPanel);
			} else
				g.drawImage(image, x, y, x + StartPanel.CHOICE_WIDTH, y
						+ StartPanel.CHOICE_HEIGHT, 0, 0, image.getWidth(),
						image.getHeight(), startPanel);
		}
	}

	public void up() {
		if (step == 4) {
			step = 0;
			y += (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE);
			if (position == 0) {
				y += (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE) * 4;
				position = 4;
			} else {
				y -= StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE;
				position--;
			}
			if (position == 0) {
				addX = 20;
				addY = 13;
			}
		} else {
			step++;
			if (position == 0 || position == 1) {
				addX = 20;
				addY = 13;
			}
			y -= (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE) / 4;
		}
	}

	public void down() {
		if (step == 4) {
			step = 0;
			y -= (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE);
			if (position == 4) {
				y -= (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE) * 4;
				position = 0;
			} else {
				y += (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE);
				position++;
			}
			if (position == 0) {
				addX = 20;
				addY = 13;
			}
		} else {
			step++;
			if (position == 0) {
				addX = 20;
				addY = 13;
			}
			y += (StartPanel.CHOICE_HEIGHT + StartPanel.CHOICE_SPACE) / 4;
		}
	}

	public int getPositon() {
		return position;
	}

}
