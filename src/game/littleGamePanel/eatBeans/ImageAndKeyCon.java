package game.littleGamePanel.eatBeans;

import game.GameBody;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import media.*;

@SuppressWarnings("serial")
public class ImageAndKeyCon extends JPanel {
	SoundPlayer eatSoundEffect = new SoundPlayer("eat",false);
	public static final int BEANS_GAME_NUMBER = 5;
	int eatenredNum;//
	int eatenblueNum;//
	int eatengreyNum;//
	int eatenspecNum;//
	int eatengenerNum;
	int step1 = 1;
	int step2 = 1;
	boolean isLose;
	private int x = 40;
	private int y = 470;
	int sizeB = 300;
	// boolean

	// int eatenNum=0;
	JLabel[] b = new JLabel[sizeB];
	JLabel[] b1 = new JLabel[19];
	JLabel[] b2 = new JLabel[19];
	JLabel[] b3 = new JLabel[19];
	int[] isnotBv = new int[sizeB];
	int[] isnotBAv = new int[19];
	int[] isnotBBv = new int[19];
	int[] isnotBCv = new int[19];
	ImageIcon wall_1 = new ImageIcon("wall-1.jpg");
	ImageIcon win = new ImageIcon("win.jpg");
	ImageIcon lose = new ImageIcon("lose.jpg");
	// ImageIcon BackGround = new ImageIcon("table.jpg");

	int x1 = 100, y1 = 180;
	int x2 = 700, y2 = 50;//
	MovePanel drawPanel = new MovePanel();
	MovePanel drawPanel2 = new MovePanel();//
	ImageIcon image1 = new ImageIcon("monster-r.png");//
	ImageIcon image2 = new ImageIcon("monster-u.png");//
	ImageIcon image3 = new ImageIcon("monster-l.png");//
	ImageIcon image4 = new ImageIcon("monster-d.png");//

	/*
	 * private static Image[]images={
	 * Toolkit.getDefaultToolkit().getImage("ball-l.png"),
	 * Toolkit.getDefaultToolkit().getImage("ball-u.png"),
	 * Toolkit.getDefaultToolkit().getImage("ball-r.png"),
	 * Toolkit.getDefaultToolkit().getImage("ball-d.png"),
	 * 
	 * };
	 */
	//
	ImageIcon ball1 = new ImageIcon("ball-l.png");
	JLabel baLabel1 = new JLabel(ball1);
	ImageIcon ball2 = new ImageIcon("ball-u.png");
	JLabel baLabel2 = new JLabel(ball2);
	ImageIcon ball3 = new ImageIcon("ball-r.png");
	JLabel baLabel3 = new JLabel(ball3);
	ImageIcon ball4 = new ImageIcon("ball-d.png");
	JLabel baLabel4 = new JLabel(ball4);
	//
	int size = 150;
	JLabel[] w = new JLabel[size];
	JLabel winLabel = new JLabel(win);
	JLabel loseLabel = new JLabel(lose);
	Timer time, time2;
	JLabel robotLabel1 = new JLabel(image1);
	JLabel robotLabel2 = new JLabel(image2);
	JLabel robotLabel3 = new JLabel(image3);
	JLabel robotLabel4 = new JLabel(image4);

	ImageIcon bkImage = new ImageIcon("table.jpg");
	JLabel jbl = new JLabel(bkImage);
	MovePanel mainPanel = new MovePanel();
	private GameBody test;

	public ImageAndKeyCon(GameBody test) {
		this.test = test;
		// test.setPoint();
		// test.setFinish(true);
		mainPanel.setOpaque(false);
		//

		mainPanel.add(baLabel3);
		mainPanel.add(baLabel1);
		mainPanel.add(baLabel2);

		mainPanel.add(baLabel4);
		//
		setLayout(null);
		this.setOpaque(true);
		mainPanel.setBounds(x, y, 40, 40);
		this.add(mainPanel);

		jbl.setBounds(0, 0, 800, 600);
		add(drawPanel);

		drawPanel.setOpaque(false);
		drawPanel.add(robotLabel1);
		drawPanel.add(robotLabel3);
		robotLabel3.setVisible(false);
		add(drawPanel2);

		drawPanel2.setOpaque(false);
		drawPanel2.add(robotLabel2);
		drawPanel2.add(robotLabel4);
		robotLabel2.setVisible(false);
		//
		drawPanel.setBounds(x1, y1, 40, 40);
		drawPanel2.setBounds(x2, y2, 40, 40);
		winLabel.setBounds(380, 280, 130, 130);
		winLabel.setVisible(false);
		add(winLabel);

		loseLabel.setBounds(380, 280, 130, 130);
		loseLabel.setVisible(false);
		add(loseLabel);

		setMap();

		time = new Timer(1, new TimeListener());
		time2 = new Timer(1, new TimeListener2());// 调速度

		time.start();
		time2.start();
		// playerControllor();
		this.add(jbl);
	}

	public void setMap() {
		for (int i = 0; i < w.length; i++) {
			w[i] = new JLabel(wall_1);
			add(w[i]);
		}
		// 墙
		int wloc1 = 0;
		for (int i = 0; i < 20; i++) {
			w[i].setBounds(wloc1, 520, 40, 40);
			wloc1 += 40;
		}
		int wloc2 = 0;
		for (int i = 20; i < 33; i++) {
			w[i].setBounds(0, wloc2, 40, 40);
			wloc2 += 40;
		}

		int wloc3 = 40;
		for (int i = 33; i < 48; i++) {
			w[i].setBounds(wloc3, 520, 40, 40);
			wloc3 += 40;
		}
		int wloc4 = 40;
		for (int i = 48; i < 67; i++) {
			w[i].setBounds(wloc4, 0, 40, 40);
			wloc4 += 40;
		}

		int wloc5 = 40;
		for (int i = 67; i < 79; i++) {
			w[i].setBounds(760, wloc5, 40, 40);
			wloc5 += 40;
		}

		w[80].setBounds(160, 140, 40, 40);
		w[81].setBounds(200, 140, 40, 40);
		w[82].setBounds(240, 140, 40, 40);
		w[83].setBounds(280, 140, 40, 40);
		w[84].setBounds(320, 140, 40, 40);
		w[85].setBounds(360, 140, 40, 40);
		w[86].setBounds(400, 140, 40, 40);
		w[87].setBounds(440, 140, 40, 40);
		w[88].setBounds(480, 140, 40, 40);
		w[89].setBounds(520, 140, 40, 40);
		w[90].setBounds(560, 140, 40, 40);
		w[91].setBounds(600, 140, 40, 40);
		w[92].setBounds(380, 180, 40, 40);
		w[93].setBounds(380, 220, 40, 40);
		w[94].setBounds(380, 260, 40, 40);
		w[95].setBounds(380, 300, 40, 40);
		w[96].setBounds(380, 340, 40, 40);
		w[97].setBounds(380, 380, 40, 40);
		w[98].setBounds(160, 420, 40, 40);
		w[99].setBounds(200, 420, 40, 40);
		w[100].setBounds(240, 420, 40, 40);
		w[101].setBounds(280, 420, 40, 40);
		w[102].setBounds(320, 420, 40, 40);
		w[103].setBounds(360, 420, 40, 40);
		w[104].setBounds(400, 420, 40, 40);
		w[105].setBounds(440, 420, 40, 40);
		w[106].setBounds(480, 420, 40, 40);
		w[107].setBounds(520, 420, 40, 40);
		w[108].setBounds(560, 420, 40, 40);
		w[109].setBounds(600, 420, 40, 40);

		w[110].setBounds(200, 260, 40, 40);
		w[111].setBounds(200, 300, 40, 40);
		w[112].setBounds(240, 260, 40, 40);
		w[113].setBounds(240, 300, 40, 40);

		w[114].setBounds(520, 260, 40, 40);
		w[115].setBounds(520, 300, 40, 40);
		w[116].setBounds(560, 260, 40, 40);
		w[117].setBounds(560, 300, 40, 40);

		w[118].setBounds(240, 0, 40, 40);
		w[119].setBounds(280, 0, 40, 40);
		w[120].setBounds(320, 0, 40, 40);
		w[121].setBounds(360, 0, 40, 40);
		w[122].setBounds(400, 0, 40, 40);
		w[123].setBounds(440, 0, 40, 40);
		w[124].setBounds(480, 0, 40, 40);
		w[125].setBounds(520, 0, 40, 40);
		w[126].setBounds(560, 0, 40, 40);
		w[127].setBounds(600, 0, 40, 40);
		w[128].setBounds(640, 0, 40, 40);
		w[129].setBounds(480, 260, 40, 40);
		w[130].setBounds(480, 300, 40, 40);
		w[131].setBounds(280, 260, 40, 40);
		w[132].setBounds(280, 300, 40, 40);

		ImageIcon generalBean = new ImageIcon("genBean.png");
		ImageIcon beanA = new ImageIcon("beanA.png");
		ImageIcon beanB = new ImageIcon("beanB.png");
		ImageIcon beanC = new ImageIcon("beanC.png");

		for (int i = 0; i < b.length; i++) {
			b[i] = new JLabel(generalBean); // general Bean
		}

		for (int i = 0; i < b1.length; i++) {
			b1[i] = new JLabel(beanA);
		}

		for (int i = 0; i < b2.length; i++) {
			b2[i] = new JLabel(beanB);
		}

		for (int i = 0; i < b3.length; i++) {
			b3[i] = new JLabel(beanC);
		}

		// setBackground(null);
		// setBackground(new Color(20,128,100));
		// 豆子
		for (int j = 0; j < 156; j++) {
			add(b[j]);
		}

		for (int j = 0; j < b1.length; j++) {
			add(b1[j]);
		}

		for (int j = 0; j < b2.length; j++) {
			add(b2[j]);
		}

		for (int j = 0; j < b3.length; j++) {
			add(b3[j]);
		}

		int loc1 = 80;
		for (int i = 0; i < 23; i++) {
			b[i].setBounds(loc1, 480, 15, 15);
			loc1 += 30;
		}

		int loc2 = 50;
		for (int i = 23; i < 27; i++) {
			b[i].setBounds(loc2, 430, 15, 15);
			loc2 += 30;
		}

		int loc3 = 650;
		for (int i = 27; i < 31; i++) {
			b[i].setBounds(loc3, 430, 15, 15);
			loc3 += 30;
		}
		/**/
		int loc4 = 50;
		for (int i = 32; i < 43; i++) {
			b[i].setBounds(loc4, 390, 15, 15);
			loc4 += 30;
		}

		// b[43].setBounds(50, 310, 15, 15);

		int loc5 = 440;
		for (int i = 44; i < 55; i++) {
			b[i].setBounds(loc5, 390, 15, 15);
			loc5 += 30;
		}

		// b[55].setBounds(50, 350, 15, 15);
		b[56].setBounds(110, 350, 15, 15);
		b[57].setBounds(350, 310, 15, 15);
		b[58].setBounds(350, 350, 15, 15);
		b[59].setBounds(350, 270, 15, 15);
		b[60].setBounds(350, 230, 15, 15);

		b[68].setBounds(230, 100, 15, 15);
		b[69].setBounds(260, 100, 15, 15);
		b[70].setBounds(290, 100, 15, 15);
		b[71].setBounds(320, 100, 15, 15);
		b[72].setBounds(380, 100, 15, 15);
		b[73].setBounds(410, 100, 15, 15);
		b[74].setBounds(440, 100, 15, 15);
		b[75].setBounds(350, 100, 15, 15);
		b[76].setBounds(470, 100, 15, 15);
		b[77].setBounds(500, 100, 15, 15);
		b[78].setBounds(530, 100, 15, 15);
		b[79].setBounds(560, 100, 15, 15);
		b[80].setBounds(590, 100, 15, 15);
		b[81].setBounds(620, 100, 15, 15);
		b[82].setBounds(650, 100, 15, 15);
		b[83].setBounds(680, 100, 15, 15);
		b[84].setBounds(710, 100, 15, 15);
		b[85].setBounds(740, 100, 15, 15);
		b[86].setBounds(50, 100, 15, 15);
		b[87].setBounds(80, 100, 15, 15);
		b[88].setBounds(110, 100, 15, 15);
		b[89].setBounds(140, 100, 15, 15);
		b[90].setBounds(170, 100, 15, 15);
		b[91].setBounds(200, 100, 15, 15);
		b[92].setBounds(50, 60, 15, 15);
		b[93].setBounds(80, 60, 15, 15);
		b[94].setBounds(110, 60, 15, 15);
		b[95].setBounds(140, 60, 15, 15);
		b[96].setBounds(170, 60, 15, 15);
		b[97].setBounds(200, 60, 15, 15);
		b[98].setBounds(230, 60, 15, 15);
		b[99].setBounds(260, 60, 15, 15);
		b[100].setBounds(290, 60, 15, 15);
		b[101].setBounds(320, 60, 15, 15);
		b[102].setBounds(350, 60, 15, 15);
		b[103].setBounds(380, 60, 15, 15);
		b[104].setBounds(410, 60, 15, 15);
		b[105].setBounds(440, 60, 15, 15);
		b[106].setBounds(470, 60, 15, 15);
		b[107].setBounds(500, 60, 15, 15);
		b[108].setBounds(530, 60, 15, 15);
		b[109].setBounds(560, 60, 15, 15);
		b[110].setBounds(590, 60, 15, 15);
		b[111].setBounds(620, 60, 15, 15);
		b[112].setBounds(650, 60, 15, 15);
		b[113].setBounds(680, 60, 15, 15);
		b[114].setBounds(710, 60, 15, 15);
		b[115].setBounds(740, 60, 15, 15);
		b[116].setBounds(50, 140, 15, 15);
		b[117].setBounds(110, 140, 15, 15);
		b[118].setBounds(80, 140, 15, 15);
		b[119].setBounds(140, 140, 15, 15);
		b[120].setBounds(260, 190, 15, 15);//
		b[121].setBounds(290, 190, 15, 15);
		b[122].setBounds(320, 190, 15, 15);
		b[123].setBounds(230, 190, 15, 15);
		b[61].setBounds(350, 190, 15, 15);
		b[62].setBounds(50, 190, 15, 15);
		b[63].setBounds(80, 190, 15, 15);
		b[64].setBounds(110, 190, 15, 15);
		b[65].setBounds(140, 190, 15, 15);
		b[66].setBounds(170, 190, 15, 15);
		b[67].setBounds(200, 190, 15, 15);
		// b[124].setBounds(50, 230, 15,15 );
		b[125].setBounds(110, 230, 15, 15);
		// b[126].setBounds(50, 270, 15,15 );
		b[127].setBounds(110, 310, 15, 15);
		b[128].setBounds(110, 270, 15, 15);
		b[129].setBounds(440, 190, 15, 15);
		b[130].setBounds(440, 230, 15, 15);
		b[131].setBounds(440, 270, 15, 15);
		b[132].setBounds(440, 310, 15, 15);
		b[133].setBounds(440, 350, 15, 15);
		b[134].setBounds(470, 190, 15, 15);
		b[135].setBounds(500, 190, 15, 15);
		b[136].setBounds(530, 190, 15, 15);
		b[137].setBounds(560, 190, 15, 15);
		b[138].setBounds(590, 190, 15, 15);
		b[139].setBounds(620, 190, 15, 15);
		b[140].setBounds(650, 190, 15, 15);
		b[141].setBounds(680, 190, 15, 15);
		b[142].setBounds(710, 190, 15, 15);
		b[143].setBounds(740, 190, 15, 15);
		b[144].setBounds(650, 150, 15, 15);
		b[145].setBounds(680, 150, 15, 15);
		b[146].setBounds(710, 150, 15, 15);
		b[147].setBounds(740, 150, 15, 15);
		b[148].setBounds(680, 230, 15, 15);
		b[149].setBounds(680, 270, 15, 15);
		b[150].setBounds(680, 310, 15, 15);
		b[151].setBounds(680, 350, 15, 15);
		// b[152].setBounds(740, 310, 15,15 );
		// b[153].setBounds(740, 350, 15,15 );
		// b[154].setBounds(740, 270, 15,15 );
		// b[155].setBounds(740, 230, 15,15 );

		// b1的坐标 红
		int loA1 = 230;
		for (int i = 0; i < 5; i++) {
			b1[i].setBounds(140, loA1, 15, 15);
			loA1 += 30;
		}

		int loA2 = 230;
		for (int i = 5; i < 10; i++) {
			b1[i].setBounds(620, loA2, 15, 15);
			loA2 += 30;
		}
		//

		b3[14].setBounds(620 - 25, 230, 15, 15);
		b2[15].setBounds(620 - 25 * 2, 230, 15, 15);
		b1[14].setBounds(620 - 25 * 3, 230, 15, 15);
		b3[15].setBounds(620 - 25 * 4, 230, 15, 15);
		b2[14].setBounds(620 - 25 * 5, 230, 15, 15);
		b1[15].setBounds(620 - 25 * 6, 230, 15, 15);

		b3[16].setBounds(620 - 25, 350, 15, 15);
		b2[17].setBounds(620 - 25 * 2, 350, 15, 15);
		b1[16].setBounds(620 - 25 * 3, 350, 15, 15);
		b3[17].setBounds(620 - 25 * 4, 350, 15, 15);
		b2[16].setBounds(620 - 25 * 5, 350, 15, 15);
		b1[17].setBounds(620 - 25 * 6, 350, 15, 15);
		// b2的坐标
		int loB1 = 230;
		for (int i = 0; i < 5; i++) {
			b2[i].setBounds(80, loB1, 15, 15);
			loB1 += 30;
		}

		int loB2 = 230;
		for (int i = 5; i < 10; i++) {
			b2[i].setBounds(710, loB2, 15, 15);
			loB2 += 30;
		}
		// b3的坐标
		int loC1 = 230;
		for (int i = 0; i < 5; i++) {
			b3[i].setBounds(170, loC1, 15, 15);
			loC1 += 30;
		}

		int loC2 = 230;
		for (int i = 5; i < 10; i++) {
			b3[i].setBounds(650, loC2, 15, 15);
			loC2 += 30;
		}

		//
		b1[10].setBounds(195, 230, 15, 15);
		b2[11].setBounds(220, 230, 15, 15);
		b3[10].setBounds(245, 230, 15, 15);
		b1[11].setBounds(270, 230, 15, 15);
		b2[10].setBounds(295, 230, 15, 15);
		b3[11].setBounds(320, 230, 15, 15);

		b1[12].setBounds(195, 350, 15, 15);
		b2[13].setBounds(220, 350, 15, 15);
		b3[12].setBounds(245, 350, 15, 15);
		b1[13].setBounds(270, 350, 15, 15);
		b2[12].setBounds(295, 350, 15, 15);
		b3[13].setBounds(320, 350, 15, 15);

	}

	class TimeListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			robotStart();

			// playerControllor();
			repaint();
		}
	}

	class TimeListener2 implements ActionListener {
		public void actionPerformed(ActionEvent e) {

			robot2Start();

			// playerControllor();
			repaint();
		}
	}

	public void robotStart() { // 改怪兽的一些动作
		// int q=(int)(Math.random()*150);
		// System.out.println("zu:"+drawPanel.getX());
		// System.out.println("zu:"+drawPanel.getY());
		if ((drawPanel.getX() > x - 30) && (drawPanel.getX() < x + 30)
				&& (drawPanel.getY() < y + 30) && (drawPanel.getY() > y - 35)) {
			time.stop();
			time2.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel.getX() > x - 30) && (drawPanel.getX() < x + 30)
				&& (drawPanel.getY() < y + 30) && (drawPanel.getY() > y - 35)) {
			time.stop();
			time2.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel.getX() > x - 30) && (drawPanel.getX() < x + 30)
				&& (drawPanel.getY() < y + 30) && (drawPanel.getY() > y - 35)) {
			time.stop();
			time2.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel.getX() > x - 30) && (drawPanel.getX() < x + 30)
				&& (drawPanel.getY() < y + 30) && (drawPanel.getY() > y - 35)) {
			time.stop();
			time2.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if (x1 >= 340) {
			robotLabel1.setVisible(false);
			robotLabel3.setVisible(true);
			step1 = -1;
		}
		if (x1 <= 40) {
			robotLabel1.setVisible(true);
			robotLabel3.setVisible(false);
			step1 = 1;
		}
		x1 += step1;
		// y1+=step;

		drawPanel.repaint();
		drawPanel.setBounds(x1, y1, 40, 40);
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}

	}

	public void robot2Start() { // 改怪兽的一些动作
		// int q=(int)(Math.random()*150);
		// System.out.println("zu:"+drawPanel.getX());
		// System.out.println("zu:"+drawPanel.getY());
		if ((drawPanel2.getX() > x - 30) && (drawPanel2.getX() < x + 30)
				&& (drawPanel2.getY() < y + 30) && (drawPanel2.getY() > y - 35)) {
			time2.stop();
			time.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel2.getX() > x - 30) && (drawPanel2.getX() < x + 30)
				&& (drawPanel2.getY() < y + 30) && (drawPanel2.getY() > y - 35)) {
			time2.stop();
			time.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel2.getX() > x - 30) && (drawPanel2.getX() < x + 30)
				&& (drawPanel2.getY() < y + 30) && (drawPanel2.getY() > y - 35)) {
			time2.stop();
			time.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if ((drawPanel2.getX() > x - 30) && (drawPanel2.getX() < x + 30)
				&& (drawPanel2.getY() < y + 30) && (drawPanel2.getY() > y - 35)) {
			time2.stop();
			time.stop();
			loseLabel.setVisible(true);
			isLose = true;
			test.setAddedPoints(0, 0, 0, 0);
			test.setFinished(BEANS_GAME_NUMBER);
		}
		if (y2 >= 480) {
			robotLabel2.setVisible(true);
			robotLabel4.setVisible(false);
			step2 = -1;
		}
		if (y2 <= 40) {
			robotLabel2.setVisible(false);
			robotLabel4.setVisible(true);
			step2 = 1;
		}
		y2 += step2;

		drawPanel2.repaint();
		drawPanel2.setBounds(x2, y2, 40, 40);
		try {
			Thread.sleep(10);
		} catch (Exception e) {
		}

	}

	// public void playerControllor() {
	// addKeyListener(new KeyAdapter() {
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_DOWN: {
			if (eatengenerNum == 147 && eatenspecNum == 30) {
				w[70].setVisible(false);
			}
			y += 10;
			if (y >= 520) {
				y = 520;
			}
			// 墙
			if ((x > 0 && x < 780 && y > 480 && y < 520)
					|| (x > 120 && x < 640 && y > 100 && y < 140)
					|| (x > 120 && x < 640 && y > 380 && y < 420)
					|| (x > 160 && x < 320 && y > 220 && y < 260)
					|| (x > 440 && x < 600 && y > 220 && y < 260)) {
				y -= 10;
			}
			// 豆子
			else {
				for (int i = 0; i < b.length; i++) {
					if ((x > b[i].getX() - 35) && (x < b[i].getX() + 15)
							&& (y > b[i].getY() - 35) && (y < b[i].getY() + 15)) {
						b[i].setVisible(false);
						eatSoundEffect.play();
						isnotBv[i] = 1;
					}
				}
				if (eatenspecNum < 30) {
					for (int i = 0; i < b1.length; i++) {
						if ((x > b1[i].getX() - 35) && (x < b1[i].getX() + 15)
								&& (y > b1[i].getY() - 35)
								&& (y < b1[i].getY() + 15)) {
							b1[i].setVisible(false);
							eatSoundEffect.play();
							isnotBAv[i] = 1;

						}
					}

					for (int i = 0; i < b2.length; i++) {
						if ((x > b2[i].getX() - 35) && (x < b2[i].getX() + 15)
								&& (y > b2[i].getY() - 35)
								&& (y < b2[i].getY() + 15)) {
							b2[i].setVisible(false);
							eatSoundEffect.play();
							isnotBBv[i] = 1;
						}
					}

					for (int i = 0; i < b3.length; i++) {
						if ((x > b3[i].getX() - 35) && (x < b3[i].getX() + 15)
								&& (y > b3[i].getY() - 35)
								&& (y < b3[i].getY() + 15)) {
							b3[i].setVisible(false);
							eatSoundEffect.play();
							isnotBCv[i] = 1;
						}
					}
				}
			}
			baLabel1.setVisible(false);
			baLabel2.setVisible(false);
			baLabel3.setVisible(false);
			baLabel4.setVisible(true);
		}
			break;

		case KeyEvent.VK_UP: {
			y -= 10;
			if (eatengenerNum == 147 && eatenspecNum == 30) {
				w[70].setVisible(false);
			}
			if (y <= 0) {
				y = 0;
			}
			if ((x > 0 && x < 780 && y > 0 && y < 40)
					|| (x > 120 && x < 640 && y > 140 && y < 180)
					|| (x > 120 && x < 640 && y > 420 && y < 460)
					|| (x > 160 && x < 320 && y > 300 && y < 340)
					|| (x > 440 && x < 600 && y > 300 && y < 340)) {
				y += 10;
			}
			for (int i = 0; i < b.length; i++) {
				if ((x > b[i].getX() - 35) && (x < b[i].getX() + 15)
						&& (y > b[i].getY() - 35) && (y < b[i].getY() + 15)) {
					b[i].setVisible(false);
					eatSoundEffect.play();
					isnotBv[i] = 1;
				}
			}
			if (eatenspecNum < 30) {
				for (int i = 0; i < b1.length; i++) {
					if ((x > b1[i].getX() - 35) && (x < b1[i].getX() + 15)
							&& (y > b1[i].getY() - 35)
							&& (y < b1[i].getY() + 15)) {
						b1[i].setVisible(false);
						eatSoundEffect.play();
						isnotBAv[i] = 1;
					}
				}

				for (int i = 0; i < b2.length; i++) {
					if ((x > b2[i].getX() - 35) && (x < b2[i].getX() + 15)
							&& (y > b2[i].getY() - 35)
							&& (y < b2[i].getY() + 15)) {
						b2[i].setVisible(false);
						eatSoundEffect.play();
						isnotBBv[i] = 1;
					}
				}

				for (int i = 0; i < b3.length; i++) {
					if ((x > b3[i].getX() - 35) && (x < b3[i].getX() + 15)
							&& (y > b3[i].getY() - 35)
							&& (y < b3[i].getY() + 15)) {
						b3[i].setVisible(false);
						eatSoundEffect.play();
						isnotBCv[i] = 1;
					}
				}
			}
			baLabel1.setVisible(false);
			baLabel2.setVisible(true);
			baLabel3.setVisible(false);
			baLabel4.setVisible(false);
		}
			break;

		case KeyEvent.VK_LEFT: {
			x -= 10;
			if (eatengenerNum == 147 && eatenspecNum == 30) {
				w[70].setVisible(false);
			}
			if (x <= 0) {
				x = 0;
			}
			if ((x > 0 && x < 40 && y > 0 && y < 520)
					|| (x > 380 && x < 420 && y > 160 && y < 400)
					|| (x > 240 && x < 320 && y > 220 && y < 340)
					|| (x > 560 && x < 600 && y > 220 && y < 340)
					|| (x > 600 && x < 640 && y > 100 && y < 180)
					|| (x > 600 && x < 640 && y > 380 && y < 460)) {
				x += 10;
			}
			for (int i = 0; i < b.length; i++) {
				if ((x > b[i].getX() - 35) && (x < b[i].getX() + 15)
						&& (y > b[i].getY() - 35) && (y < b[i].getY() + 15)) {
					b[i].setVisible(false);
					eatSoundEffect.play();
					isnotBv[i] = 1;
				}
			}
			if (eatenspecNum < 30) {
				for (int i = 0; i < b1.length; i++) {
					if ((x > b1[i].getX() - 35) && (x < b1[i].getX() + 15)
							&& (y > b1[i].getY() - 35)
							&& (y < b1[i].getY() + 15)) {
						b1[i].setVisible(false);
						eatSoundEffect.play();
						isnotBAv[i] = 1;
					}
				}

				for (int i = 0; i < b2.length; i++) {
					if ((x > b2[i].getX() - 35) && (x < b2[i].getX() + 15)
							&& (y > b2[i].getY() - 35)
							&& (y < b2[i].getY() + 15)) {
						b2[i].setVisible(false);
						eatSoundEffect.play();
						isnotBBv[i] = 1;
					}
				}

				for (int i = 0; i < b3.length; i++) {
					if ((x > b3[i].getX() - 35) && (x < b3[i].getX() + 15)
							&& (y > b3[i].getY() - 35)
							&& (y < b3[i].getY() + 15)) {
						b3[i].setVisible(false);
						eatSoundEffect.play();
						isnotBCv[i] = 1;
					}
				}
			}

			baLabel1.setVisible(true);
			baLabel2.setVisible(false);
			baLabel3.setVisible(false);
			baLabel4.setVisible(false);
		}
			break;

		case KeyEvent.VK_RIGHT: {
			x += 10;
			if (eatengenerNum == 147 && eatenspecNum == 30) {
				w[70].setVisible(false);
				if (x > 720 && y > 140 && y < 180) {
					winLabel.setVisible(true);
					time.stop();
					time2.stop();
					System.out.println("red:" + eatenredNum);
					System.out.println("blue::" + eatenblueNum);
					System.out.println("grey:" + eatengreyNum);
					test.setAddedPoints(eatenredNum, eatenblueNum,
							eatengreyNum, 0);
					test.setFinished(BEANS_GAME_NUMBER);
					/*
					 * robotLabel1.setVisible(false);
					 * robotLabel2.setVisible(false);
					 * robotLabel3.setVisible(false);
					 * robotLabel4.setVisible(false);
					 */
					// System.out.println("you win!");
				}
			}
			if (x >= 745) {
				x = 745;
			}

			if ((x > 720 && x < 760 && y > 0 && y < 520)
					|| (x > 340 && x < 380 && y > 160 && y < 400)
					|| (x > 160 && x < 240 && y > 220 && y < 340)
					|| (x > 440 && x < 480 && y > 220 && y < 340)
					|| (x > 120 && x < 160 && y > 100 && y < 180)
					|| (x > 120 && x < 160 && y > 380 && y < 460)) {
				x -= 10;
			}

			for (int i = 0; i < b.length; i++) {
				if ((x > b[i].getX() - 35) && (x < b[i].getX() + 15)
						&& (y > b[i].getY() - 35) && (y < b[i].getY() + 15)) {
					b[i].setVisible(false);
					eatSoundEffect.play();
					isnotBv[i] = 1;
				}
			}
			if (eatenspecNum < 30) {
				for (int i = 0; i < b1.length; i++) {
					if ((x > b1[i].getX() - 35) && (x < b1[i].getX() + 15)
							&& (y > b1[i].getY() - 35)
							&& (y < b1[i].getY() + 15)) {
						b1[i].setVisible(false);
						eatSoundEffect.play();
						isnotBAv[i] = 1;
					}
				}

				for (int i = 0; i < b2.length; i++) {
					if ((x > b2[i].getX() - 35) && (x < b2[i].getX() + 15)
							&& (y > b2[i].getY() - 35)
							&& (y < b2[i].getY() + 15)) {
						b2[i].setVisible(false);
						eatSoundEffect.play();
						isnotBBv[i] = 1;
					}
				}

				for (int i = 0; i < b3.length; i++) {
					if ((x > b3[i].getX() - 35) && (x < b3[i].getX() + 15)
							&& (y > b3[i].getY() - 35)
							&& (y < b3[i].getY() + 15)) {
						b3[i].setVisible(false);
						eatSoundEffect.play();
						isnotBCv[i] = 1;
					}
				}
			}
			baLabel1.setVisible(false);
			baLabel2.setVisible(false);
			baLabel3.setVisible(true);
			baLabel4.setVisible(false);
		}
			break;

		}

		eatengenerNum = 0;
		eatenredNum = 0;
		eatenblueNum = 0;
		eatengreyNum = 0;
		eatenspecNum = 0;
		for (int i = 0; i < isnotBv.length; i++) {
			eatengenerNum += isnotBv[i];
		}
		for (int i = 0; i < isnotBAv.length; i++) {
			eatenredNum += isnotBAv[i];
		}
		for (int i = 0; i < isnotBBv.length; i++) {
			eatenblueNum += isnotBBv[i];
		}
		for (int i = 0; i < isnotBCv.length; i++) {
			eatengreyNum += isnotBCv[i];
		}
		eatenspecNum = eatenredNum + eatenblueNum + eatengreyNum;

		mainPanel.repaint();
		mainPanel.setBounds(x, y, 40, 40);

	}

	// });
	/*
	 * add(drawPanel);//
	 * 
	 * for(int i=0;i<130;i++){ x1++; y1++;
	 * 
	 * drawPanel.repaint();// drawPanel.setBounds(x1, y1, 40, 40); try{
	 * Thread.sleep(10); }catch(Exception e){} }//
	 */
	// }

	class MovePanel extends JPanel {
		public void paintComponent(Graphics g) {
			// g.setColor(Color.pink);
			super.paintComponent(g);

			// g.drawImage(image,x1,y1,this);
			if (isLose) {
				g.drawImage(null, x, y, this);
			}
		}
	}

}
