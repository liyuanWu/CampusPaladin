package game.littleGamePanel;

import game.GameBody;

import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.util.ArrayList;
import javax.swing.*;

import java.util.ArrayList;
import javax.swing.*;
import media.*;

public class PuzzleLayoutPanel extends JPanel {
	
	SoundPlayer clickSound = new SoundPlayer("decision",false);
	int[] addedPoints = new int[4];
	GameBody frame;
	ImageIcon iconbackground = new ImageIcon("background.jpg");
	Image background = iconbackground.getImage();
	ImageIcon checkImageIcon = new ImageIcon("click.PNG");
	Image checkImage = checkImageIcon.getImage();
	Image pic = null;
	long startTime;
	JButton checkButton = new JButton("Click to Check");
	public long mouseClickCount = 0;
	boolean finished = false;
	boolean canClickCheck = true;
	int numberOfRow;
	int size;
	int[] x; // 面板上点的横坐标
	int[] y;
	ArrayList randomNumber = new ArrayList();
	int[] random; // 将产生的不重复随机数赋值给它，用于随机排列图片位置

	int pieceHeight;
	int pieceWidth;

	int[] sourceX;
	int[] sourceY;
	int[] fillBoxX;
	int[] fillBoxY;
	int chosenPiece;
	int soursePiece; // 将原图片的碎片固定编号，此编号的0~24即完好图片。

	// 构造函数
	public PuzzleLayoutPanel(int crown, int sword, int wing, int money,
			GameBody puzzleLayoutFrame) {
		this.frame = puzzleLayoutFrame;
		this.setOpaque(true);

		if (crown == 0) {
			ImageIcon icon = new ImageIcon("25.png");
			pic = icon.getImage(); // test
		}

		if (crown <= 30 && crown > 0) {
			ImageIcon icon = new ImageIcon("hard.png");
			pic = icon.getImage(); // hard
		}
		if (crown >= 30 && crown < 40) {
			ImageIcon icon = new ImageIcon("medium1.png");
			pic = icon.getImage(); //
		}
		if (crown >= 40 && crown < 50) {
			ImageIcon icon = new ImageIcon("medium2.png");
			pic = icon.getImage();
		}
		if (crown >= 50) {
			ImageIcon icon = new ImageIcon("easy.png");
			pic = icon.getImage();
		}

		if (sword <= 40) {
			numberOfRow = 5;
			size = 60;
		} else {
			numberOfRow = 4;
			size = 80;
		}

		x = new int[numberOfRow * numberOfRow];
		y = new int[numberOfRow * numberOfRow];

		random = new int[numberOfRow * numberOfRow];

		pieceHeight = pic.getHeight(null) / numberOfRow;
		pieceWidth = pic.getWidth(null) / numberOfRow;

		sourceX = new int[numberOfRow * numberOfRow];
		sourceY = new int[numberOfRow * numberOfRow];
		fillBoxX = new int[numberOfRow * numberOfRow];
		fillBoxY = new int[numberOfRow * numberOfRow];

		// 分割面板和原图片，得小块顶点坐标
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			x[i] = (i % numberOfRow) * size;
			y[i] = (i / numberOfRow) * size;

		}

		for (soursePiece = 0; soursePiece < numberOfRow * numberOfRow; soursePiece++) {
			sourceX[soursePiece] = (soursePiece % numberOfRow) * pieceWidth;
			sourceY[soursePiece] = (soursePiece / numberOfRow) * pieceHeight;
		}
		// 产生随机编号
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			randomNumber.add(i); // 在randomNumber 的arrayList中存入0~24
		}
		for (int k = 0; k < numberOfRow * numberOfRow; k++) {

			int m = (int) (Math.random() * randomNumber.size());// 这样不用担心产生的随机数不在arraylist里
			random[k] = (int) (Integer) (randomNumber.get(m)); // 把不重复随机数给random[]
			randomNumber.remove(m);
		}

		// 填充框定位
		fillBoxX[0] = 400;
		fillBoxY[0] = 0;
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			fillBoxX[i] = fillBoxX[0] + (i % numberOfRow) * size;
			fillBoxY[i] = fillBoxY[0] + (i / numberOfRow) * size;
		}
	}// end of constructor

	// 点击鼠标监听器
	public void MousePressed() {
		this.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (!finished)
					mouseClickCount++;
				if (mouseClickCount == 1)
					startTime = System.currentTimeMillis();

				for (int i = 0; i < numberOfRow * numberOfRow; i++) {
					if (e.getX() >= x[i] && e.getX() < x[i] + size
							&& e.getY() >= y[i] && e.getY() <= y[i] + size)

						chosenPiece = i;
				}

			}

		});
	}

	// 鼠标拖动注册监听器
	public void MouseDragged() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {

				x[chosenPiece] = e.getX() - size / 2;
				y[chosenPiece] = e.getY() - size / 2;

				repaint();
				// 设置屏幕边界
				if (e.getX() < 0)
					x[chosenPiece] = 0;
				if (e.getY() < 0)
					y[chosenPiece] = 0;
				if (e.getX() > 800)
					x[chosenPiece] = 740;
				if (e.getY() > 600)
					y[chosenPiece] = 540;
				// break;
			}

		});
	}// end of mousedragged method
		// 鼠标释放，自动对齐

	public void MouseReleased() {
		this.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				// 以下是检查机制
				if (e.getX() >= 300 && e.getX() <= 600 && e.getY() >= 400
						&& getY() <= 600) {
					clickSound.play();
					GameResult myGameResult = new GameResult();

					int matchcount = 0;
					for (int i = 0; i < numberOfRow * numberOfRow; i++) {

						if (x[i] == fillBoxX[random[i]]
								&& y[i] == fillBoxY[random[i]]) {
							matchcount++;

						}
					}
					if (matchcount == numberOfRow * numberOfRow)
						finished = true;

					if (finished) {
						if (canClickCheck) {
							addedPoints = myGameResult.won(startTime,
									mouseClickCount);
							frame.setAddedPoints(addedPoints[0],
									addedPoints[1], addedPoints[2],
									addedPoints[3]);

							canClickCheck = false;
							frame.setFinished(1);
						}
					} else
						myGameResult.unfinished();
				}
				// 检查完成
				for (int m = 0; m < numberOfRow * numberOfRow; m++) {
					if (e.getX() >= fillBoxX[m]
							&& e.getX() < fillBoxX[m] + size
							&& e.getY() >= fillBoxY[m]
							&& e.getY() <= fillBoxY[m] + size) {
						x[chosenPiece] = fillBoxX[m];
						y[chosenPiece] = fillBoxY[m];
						repaint();
						break;
					}

				}
			}
		});
	}// end of mousereleased method

	/**
	 * 增加检查按钮: 原理：在paintcomponent方法里，x[i]对应sourceX[random[i]]。
	 * 而拼图拼好的充要条件是：每个sourceX
	 * [random[i]]与fillBoxX[random[i]]一一对应。所以检查机制应该是，检查x[i]与fillBoxX
	 * [random[i]]一一对应
	 */

	protected void paintComponent(Graphics g) {
		Color color = g.getColor();
		super.paintComponent(g);

		g.drawImage(background, 0, 0, getWidth(), getHeight(), this);
		g.drawImage(pic, 0, getHeight() - 200, 200, getHeight(), 0, 0,
				getWidth(), getHeight(), this);
		g.drawImage(checkImage, 300, 400, 600, 600, 0, 0, getWidth(),
				getHeight(), this);

		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			g.drawImage(pic, x[chosenPiece], y[chosenPiece], x[chosenPiece]
					+ size, y[chosenPiece] + size,
					sourceX[random[chosenPiece]], sourceY[random[chosenPiece]],
					sourceX[random[chosenPiece]] + pieceWidth,
					sourceY[random[chosenPiece]] + pieceHeight, this);
			g.drawImage(pic, x[i], y[i], x[i] + size, y[i] + size,
					sourceX[random[i]], sourceY[random[i]], sourceX[random[i]]
							+ pieceWidth, sourceY[random[i]] + pieceHeight,
					this);

		}

		g.setColor(Color.gray);

		// 画出填充框

		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			g.drawRect(fillBoxX[i], fillBoxY[i], size, size);
		}
		g.setColor(color);

	}
}
