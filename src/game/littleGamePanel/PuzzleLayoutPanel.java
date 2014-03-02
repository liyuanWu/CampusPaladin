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
	int[] x; // ����ϵ�ĺ�����
	int[] y;
	ArrayList randomNumber = new ArrayList();
	int[] random; // �������Ĳ��ظ��������ֵ�����������������ͼƬλ��

	int pieceHeight;
	int pieceWidth;

	int[] sourceX;
	int[] sourceY;
	int[] fillBoxX;
	int[] fillBoxY;
	int chosenPiece;
	int soursePiece; // ��ԭͼƬ����Ƭ�̶���ţ��˱�ŵ�0~24�����ͼƬ��

	// ���캯��
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

		// �ָ�����ԭͼƬ����С�鶥������
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			x[i] = (i % numberOfRow) * size;
			y[i] = (i / numberOfRow) * size;

		}

		for (soursePiece = 0; soursePiece < numberOfRow * numberOfRow; soursePiece++) {
			sourceX[soursePiece] = (soursePiece % numberOfRow) * pieceWidth;
			sourceY[soursePiece] = (soursePiece / numberOfRow) * pieceHeight;
		}
		// ����������
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			randomNumber.add(i); // ��randomNumber ��arrayList�д���0~24
		}
		for (int k = 0; k < numberOfRow * numberOfRow; k++) {

			int m = (int) (Math.random() * randomNumber.size());// �������õ��Ĳ��������������arraylist��
			random[k] = (int) (Integer) (randomNumber.get(m)); // �Ѳ��ظ��������random[]
			randomNumber.remove(m);
		}

		// ����λ
		fillBoxX[0] = 400;
		fillBoxY[0] = 0;
		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			fillBoxX[i] = fillBoxX[0] + (i % numberOfRow) * size;
			fillBoxY[i] = fillBoxY[0] + (i / numberOfRow) * size;
		}
	}// end of constructor

	// �����������
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

	// ����϶�ע�������
	public void MouseDragged() {
		this.addMouseMotionListener(new MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {

				x[chosenPiece] = e.getX() - size / 2;
				y[chosenPiece] = e.getY() - size / 2;

				repaint();
				// ������Ļ�߽�
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
		// ����ͷţ��Զ�����

	public void MouseReleased() {
		this.addMouseListener(new MouseAdapter() {

			public void mouseReleased(MouseEvent e) {
				// �����Ǽ�����
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
				// ������
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
	 * ���Ӽ�鰴ť: ԭ����paintcomponent�����x[i]��ӦsourceX[random[i]]��
	 * ��ƴͼƴ�õĳ�Ҫ�����ǣ�ÿ��sourceX
	 * [random[i]]��fillBoxX[random[i]]һһ��Ӧ�����Լ�����Ӧ���ǣ����x[i]��fillBoxX
	 * [random[i]]һһ��Ӧ
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

		// ��������

		for (int i = 0; i < numberOfRow * numberOfRow; i++) {
			g.drawRect(fillBoxX[i], fillBoxY[i], size, size);
		}
		g.setColor(color);

	}
}
