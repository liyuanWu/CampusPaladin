package game.Try;

import game.GameBody;
import java.awt.event.*;
import java.io.File;
import java.awt.CardLayout;
import javax.swing.*;

public class Table {

	GameBody frame;
	JPanel genderPanel;// =new JPanel();
	JLabel front;
	JButton male;
	JButton female;

	public Table(GameBody gameBody) {
		this.frame = gameBody;
		/*
		 * frame.setLocationRelativeTo(null);
		 * frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //
		 * frame.setLayout(null); frame.setSize(800, 600);
		 * frame.setVisible(true);
		 */
	}

	public void enterGame() {
		
	
		genderPanel = new JPanel();
		genderPanel.setLayout(null);
		frame.getContentPane().add(genderPanel, "gender");
		ImageIcon frontImage = new ImageIcon("script" + File.separator
				+ "front.png");
		front = new JLabel(frontImage);
		front.setBounds(0, 0, 800, 600);
		ImageIcon maleImage = new ImageIcon("script" + File.separator
				+ "male.png");
		male = new JButton(maleImage);
		male.setBounds(150, 150, 200, 200);
		male.addActionListener((ActionListener) new maleButtonListener());
		genderPanel.add(male);
		// panel.add(front);
		// male.setSize(40, 40);
		// male.setLocation(100, 100);
		ImageIcon femaleImage = new ImageIcon("script" + File.separator
				+ "female.png");
		female = new JButton(femaleImage);
		female.setBounds(450, 150, 200, 200);
		female.addActionListener((ActionListener) new femaleButtonListener());
		genderPanel.add(female);
		genderPanel.add(front);
		male.setVisible(true);
		female.setVisible(true);
		frame.getCardLayOut().show(frame.getContentPane(), "gender");
		genderPanel.setVisible(true);
	}

	class maleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			male.setVisible(false);
//			frame.setGender(0);
//			frame.getCardLayOut().removeLayoutComponent(
//					frame.getContentPane().getComponent(0));
//			frame.getContentPane().remove(genderPanel);
//			frame.startPanelThread();
		}
	}

	class femaleButtonListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			female.setVisible(false);
//			frame.setGender(1);
//			frame.getCardLayOut().removeLayoutComponent(
//					frame.getContentPane().getComponent(0));
//			frame.getContentPane().remove(genderPanel);
//			frame.startPanelThread();
		}
	}
}
