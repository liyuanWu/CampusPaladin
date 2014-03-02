package rolePanel;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

public class RolePointsPanel extends JPanel implements Runnable, Serializable {
	private int witchPanel = 1;
	// 任务面板
	private Image role;
	private Image strip;
	private String money;
	private int stripX;
	private int stripY;
	private int stripWidth;
	private int stripHeight;
	private int startBaseX;
	// private int startCrownX;
	// private int startSwordX;
	// private int startWingX;
	private int crownLength;
	private int swordLength;
	private int wingLength;
	private int startCrownY;
	private int startSwordY;
	private int startWingY;
	private int moneyY;
	private String fileName;
	// 存档面板
	public static final int SAVE_NUMBER = 16;
	public static final int FIRST_X = 50;
	public static final int FIRST_Y = 200;
	public static final int SAVE_WIDTH = 150;
	public static final int SAVE_HEIGHT = 30;
	public static final int W_SPACE = 40;
	public static final int H_SPACE = 50;
	public static final String[] story = new String[4];

	private int step = 0;
	private boolean isSave = true;
	private int saveNumber = 0;
	private String[] saveString = new String[SAVE_NUMBER];
	private int x;
	private int y;
	private int width = SAVE_WIDTH + 7;
	private int height = SAVE_HEIGHT + 4;

	public RolePointsPanel(int[] points, String fileName, String[] saveString)
			throws IOException {
		for (int i = 0; i < SAVE_NUMBER; i++) {
			this.saveString[i] = "空";
		}
		this.saveString = saveString;
		Scanner sc = new Scanner(
				new File("plot" + File.separator + "story.txt"));
		story[0] = sc.nextLine();
		story[1] = sc.nextLine();
		story[2] = sc.nextLine();
		story[3] = sc.nextLine();
		// 人物面板
		Font font = new Font("Times New Roman", Font.ITALIC, 25);
		this.setFont(font);
		this.fileName = fileName;
		role = ImageIO.read(new File(fileName));
		// ImageIcon icon = new ImageIcon("activityBoy.png");
		// role = icon.getImage();
		startBaseX = (int) role.getWidth(null) / 2;
		// System.out.println(role.getWidth(null));
		strip = ImageIO.read(new File("strip.png"));

		stripWidth = role.getWidth(null) / 500;
		stripHeight = role.getHeight(null) / 50;
		System.out.println(stripWidth);
		crownLength = (int) (((points[0]) * stripWidth));
		swordLength = (int) (points[1] * stripWidth);
		wingLength = (int) (points[0] * stripWidth);
		startCrownY = (int) (role.getHeight(null) / 40);
		startSwordY = (int) (role.getHeight(null) / 40 * 5);
		startWingY = (int) (role.getHeight(null) / 40 * 9);
		moneyY = (int) (int) (role.getHeight(null) / 40 * 13);
		money = "" + points[3];
		// 存档面板

		saveString[0] = "自动存档";
		x = FIRST_X;
		y = FIRST_Y - SAVE_HEIGHT;

		Thread th = new Thread(this);
		th.start();

	}

	public int getSaveNumber() {
		return saveNumber;
	}

	public boolean isSave() {
		return isSave;
	}

	public void setSave(boolean isSave) {
		this.isSave = isSave;
	}

	public void addWitchPanel() {
		witchPanel++;
		if (witchPanel > 2) {
			witchPanel = 0;
		}
	}

	public void minusWitchPanel() {
		witchPanel--;
		if (witchPanel < 0)
			witchPanel = 2;
	}

	public void setSaveName(int plotNumber) {
		switch (plotNumber) {
		case 1:
		case 2:
			saveString[saveNumber] = story[0];
			break;
		case 3:
		case 4:
		case 5:
		case 6:
		case 7:
		case 8:
			saveString[saveNumber] = story[1];
			break;
		case 9:
		case 10:
		case 11:
		case 12:
			saveString[saveNumber] = story[2];
			break;
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
			saveString[saveNumber] = story[3];
		default:
		}
		saveString[0] = "自动存档";
	}

	public String[] getSaveString() {
		return saveString;
	}

	public void setSaveString(String[] saveString) {
		this.saveString = saveString;
	}

	public void paintComponent(Graphics g) {

		super.paintComponent(g);
		Color color = g.getColor();
		if (witchPanel == 0) {
			//System.out.println("Has drawn.");
			g.drawImage(role, 0, 0, this.getWidth(), this.getHeight(), null);
			// //g.drawImage(strip, startBaseX, startSwordY, startBaseX +
			// stripWidth,
			// startSwordY + stripHeight, 0, 0, getWidth(), getHeight(), this);
			// //g.drawImage(strip, startBaseX, startWingY, startBaseX +
			// stripWidth,
			// startWingY + stripHeight, 0, 0, getWidth(), getHeight(), this);
			// //g.drawImage(strip, startBaseX, startCrownY, startBaseX +
			// stripWidth,
			// startCrownY + stripHeight, 0, 0, getWidth(), getHeight(), this);
			g.setColor(Color.yellow);
			g.fillRoundRect(startBaseX, startCrownY, crownLength, stripHeight,
					10, 10);
			g.setColor(Color.red);
			g.fillRoundRect(startBaseX, startSwordY, swordLength, stripHeight,
					10, 10);
			g.setColor(Color.blue);
			g.fillRoundRect(startBaseX, startWingY, wingLength, stripHeight,
					10, 10);
			g.drawString(money, startBaseX, moneyY);
			Image image2 = Toolkit.getDefaultToolkit().getImage("up-save.png");
			Image image3 = Toolkit.getDefaultToolkit().getImage("up2-save.png");

			if (step == 3) {
				g.drawImage(image3, 50, 20, null);
				step = 0;
			} else {
				g.drawImage(image2, 50, 20, null);
			}
			step++;
		} else if (witchPanel == 1)
			drawSave(g);
		else if (witchPanel == 2) {
			isSave = false;
			drawLoad(g);
		}
	}

	public void drawSave(Graphics e) {
		Graphics2D g = (Graphics2D) e;
		Color c = g.getColor();
		Image image = Toolkit.getDefaultToolkit().getImage("save.jpg");
		g.drawImage(image, 0, 0, null);

		Image image2 = Toolkit.getDefaultToolkit().getImage("down-save.png");
		Image image3 = Toolkit.getDefaultToolkit().getImage("down2-save.png");
		Image image4 = Toolkit.getDefaultToolkit().getImage("up-save.png");
		Image image5 = Toolkit.getDefaultToolkit().getImage("up2-save.png");
		if (step == 3) {
			g.drawImage(image3, 50, 480, null);
			g.drawImage(image4, 50, 50, null);
			step = 0;
		} else {
			g.drawImage(image2, 50, 480, null);
			g.drawImage(image5, 50, 50, null);
		}
		step++;

		g.setFont(new Font("全新硬笔行书简", Font.BOLD | Font.ITALIC, 24));
		g.setColor(Color.yellow);
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				g.drawString(saveString[i * 4 + j], FIRST_X + j
						* (SAVE_WIDTH + W_SPACE), FIRST_Y + i
						* (SAVE_HEIGHT + H_SPACE));
		g.drawRoundRect(x, y, width, height, 4, 4);
		g.setColor(c);
	}

	public void drawLoad(Graphics e) {
		Graphics2D g = (Graphics2D) e;
		Color c = g.getColor();
		g.setColor(Color.YELLOW);
		Image image = Toolkit.getDefaultToolkit().getImage("load.png");
		g.drawImage(image, 0, 0, null);
		Image image2 = Toolkit.getDefaultToolkit().getImage("down-save.png");
		Image image3 = Toolkit.getDefaultToolkit().getImage("down2-save.png");
		Image image4 = Toolkit.getDefaultToolkit().getImage("up-save.png");
		Image image5 = Toolkit.getDefaultToolkit().getImage("up2-save.png");
		if (step == 3) {
			g.drawImage(image3, 50, 480, null);
			g.drawImage(image4, 50, 50, null);
			step = 0;
		} else {
			g.drawImage(image2, 50, 480, null);
			g.drawImage(image5, 50, 50, null);
		}
		step++;
		g.setFont(new Font("全新硬笔行书简", Font.BOLD | Font.ITALIC, 24));
		for (int i = 0; i < 4; i++)
			for (int j = 0; j < 4; j++)
				g.drawString(saveString[i * 4 + j], FIRST_X + j
						* (SAVE_WIDTH + W_SPACE), FIRST_Y + i
						* (SAVE_HEIGHT + H_SPACE));
		g.drawRoundRect(x, y, width, height, 4, 4);
		g.setColor(c);
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
			saveNumber++;
		} else if (e.getKeyCode() == KeyEvent.VK_LEFT) {
			saveNumber--;
		} else if (e.getKeyCode() == KeyEvent.VK_UP) {
			saveNumber -= 4;
		} else if (e.getKeyCode() == KeyEvent.VK_DOWN) {
			saveNumber += 4;
		}
		if (saveNumber >= SAVE_NUMBER)
			saveNumber -= SAVE_NUMBER;
		else if (saveNumber < 0)
			saveNumber += SAVE_NUMBER;
		x = FIRST_X + (saveNumber % 4) * (SAVE_WIDTH + W_SPACE);
		y = FIRST_Y - SAVE_HEIGHT + (saveNumber / 4) * (SAVE_HEIGHT + H_SPACE);
	}

	@Override
	public void run() {
		while (true) {
			try {
				Thread.currentThread().sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			repaint();
		}
	}
}
