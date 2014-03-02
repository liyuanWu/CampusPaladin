package game.littleGamePanel;

import game.GameBody;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.*;
import media.SoundPlayer;

public class ShowDirectionPanel extends JPanel{
	SoundPlayer hitSoundEffect = new SoundPlayer("hit",false);
	SoundPlayer missSoundEffect = new SoundPlayer("miss",false);
	GameBody frame;
	short numOfPic;
	short timeGoneInCycle = 0;
	short showPic[];
	short activeIcon = 0;

	int right = 0;
	int wrong = 0;
	int zhen = 0;
	int runnerX = 750;
	int runnerY;
	int runnerWidth = 100;
	int runnerHeight = 100;
	int runnerSetBack;
	int refreshCycle = 100; // 100 ms 级 刷新周期

	ImageIcon icon = new ImageIcon("running.png");
	Image backGroundImage = icon.getImage();
	ImageIcon icon2 = new ImageIcon("boat1.png");
	Image runner = icon2.getImage();
	ImageIcon icon3 = new ImageIcon("finish.png");
	Image finishImage = icon3.getImage();

	int backGroundSourceX = 0;
	int backGroundSourceY = 0;
	int backGroundSpeed;
	int backGroundMoveX;
	int crown = 0;
	int sword = 0;
	int wing = 0;
	int money = 0;

	Image[] icons = {
			(new ImageIcon("up1.png")).getImage(),
			(new ImageIcon("down1.png")).getImage(),
			(new ImageIcon("left1.png")).getImage(),
			(new ImageIcon("right1.png")).getImage(),
			// 按对的图标
			(new ImageIcon("up3.png")).getImage(),
			(new ImageIcon("down3.png")).getImage(),
			(new ImageIcon("left3.png")).getImage(),
			(new ImageIcon("right3.png")).getImage(),
			// 按错的图标
			(new ImageIcon("up2.png")).getImage(),
			(new ImageIcon("down2.png")).getImage(),
			(new ImageIcon("left2.png")).getImage(),
			(new ImageIcon("right2.png")).getImage() };

	Timer showDirectionTimer = new Timer(20, new ActionListener() {
		public void actionPerformed(ActionEvent e) {
			if (zhen % refreshCycle == 0) {
				refreshLabels();
				activeIcon = 0;
				timeGoneInCycle = 0;
				runnerX -= runnerSetBack * numOfPic / 2;
				// 此为游戏结束时的情形。
				if ((backGroundSourceX >= 2400) || runnerX <= 0) {
					frame.setFinished(2);
					showDirectionTimer.stop();
					calculateScores();
				}
			}
			if (zhen % 5 == 0)
				runnerY = 217;
			else
				runnerY = 220;

			repaint();
			// endJLabel.setBounds(0, 0, 800, 600);
			// endJLabel.setVisible(true);
			zhen++;
		}
	});

	// 皇冠小：失误位移大；宝剑小：周期图片多；翅膀小：背景移动速度慢
	public ShowDirectionPanel(int crown, int sword, int wing, int money,
			GameBody showMainFrame) {

		this.frame = showMainFrame;
		Font font = new Font("Comic Sans MS", Font.ITALIC, 15);

		showDirectionTimer.start();
		// endJLabel.setBounds(0, 0, 800, 600);
		// endJLabel.setVisible(true);
		this.setFont(font);
		this.setFocusable(true);
		if (crown <= 20 && crown >= 0)
			runnerSetBack = 30;
		if (crown >= 30 && crown < 40)
			runnerSetBack = 20;
		if (crown >= 40 && crown < 50)
			runnerSetBack = 15;
		if (crown >= 50)
			runnerSetBack = 10;

		if (sword <= 15)
			numOfPic = 7;
		else if (sword <= 30)
			numOfPic = 6;
		else if (sword > 30)
			numOfPic = 5;

		if (wing <= 20)
			backGroundSpeed = 1000;
		else if (wing <= 30)
			backGroundSpeed = 2000;
		else if (wing > 30)
			backGroundSpeed = 3000;
		backGroundMoveX = (int) (backGroundImage.getWidth(null)
				/ backGroundSpeed / 2 + 1);

		showPic = new short[100];
	}

	public void refreshLabels() {
		Random random = new Random();
		for (int i = 0; i < numOfPic; i++)
			showPic[i] = (short) random.nextInt(4);
	}

	public void calculateScores() {
		if (runnerX < 100)
			wing = 5;
		else if (runnerX < 400) {
			wing = 10;
		} else if (runnerX < 500) {
			wing = 15;
		} else if (runnerX < 700) {
			wing = 25;
		} else if (runnerX >= 700) {
			wing = 35;
		}

		crown = (int) right / (wrong + 1) / 2;

		sword = 5;
		money = 10;
		frame.setAddedPoints(crown, sword, wing, money);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		Graphics2D g2 = (Graphics2D) g;
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
				RenderingHints.VALUE_ANTIALIAS_ON);
		if (runnerX >= 700)
			runnerX = 700;

		g2.drawImage(backGroundImage, 0, 0, getWidth(), getHeight(),
				backGroundSourceX, backGroundSourceY, backGroundSourceX
						+ backGroundImage.getWidth(null) / 5, backGroundSourceY
						+ backGroundImage.getHeight(null), this);

		backGroundSourceX = backGroundSourceX + backGroundMoveX;
		// System.out.println("width:" + backGroundImage.getWidth(null));
		// System.out.println(backGroundSourceX);

		g2.drawImage(runner, runnerX, runnerY, runnerX + runnerWidth, runnerY
				+ runnerHeight, 0, 0, getWidth(), getHeight(), this);

		Color color = g2.getColor();
		Color timeColor = Color.pink;
		Color stringColor = Color.blue;
		g2.setColor(timeColor);
		for (int i = 0; i < numOfPic; i++)
			g2.drawImage(icons[showPic[i]], 100 * i, 10, 100 * i + 200, 200, 0,
					0, getWidth(), getHeight(), this);
		g2.fillRoundRect(550, 75, 2 * timeGoneInCycle, 25, 5, 5);
		g2.drawRoundRect(550, 75, 2 * refreshCycle, 25, 5, 5);

		g2.setColor(stringColor);
		g2.drawString("Dead", 750, 75);
		g2.setColor(color);

		timeGoneInCycle++;

	}


	public void keyTyped2(KeyEvent e) {

	}


	public void keyPressed2(KeyEvent e) {

	}


	public void keyReleased2(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
			pressTheKey(0);
			
			break;
		case KeyEvent.VK_DOWN:
			pressTheKey(1);
			
			break;
		case KeyEvent.VK_LEFT:
			pressTheKey(2);
			
			break;
		case KeyEvent.VK_RIGHT:
			pressTheKey(3);
			
			break;
		}
	}

	private void pressTheKey(int i) {
		if (showPic[activeIcon] == i) {
			showPic[activeIcon] += 4;
			right++;
			runnerX += runnerSetBack;
			hitSoundEffect.play();
		} else {
			showPic[activeIcon] += 8;
			wrong++;
			runnerX -= runnerSetBack;
			missSoundEffect.play();
			if (runnerX < 0)
				runnerX = 0;
		}
		activeIcon++;
		if (activeIcon > numOfPic) { // 如果--activeIcon>0, 那么不执行{}中的内容~
			--activeIcon;
			return;// 跳出函数
		}
		

	}

}
