package game.littleGamePanel.eatBeans;
import java.awt.*;

import javax.swing.*;
public class Test extends JFrame{
	private int[] addPoint = new int[4];
	private boolean finish;
private ImageAndKeyCon keyboardPanel;
	public Test(){
		ImageIcon bkImage=new ImageIcon("table.jpg");
		JLabel jbl=new JLabel(bkImage);
		//jbl.setBounds(0, 0, 600, 800);
		//this.getContentPane().add(jbl);
		//keyboardPanel=new ImageAndKeyCon(this);
		add(keyboardPanel);
		setBackground(Color.red);
		keyboardPanel.setFocusable(true);
	}
	public static void main(String[] args){
		Test frame=new Test();
		frame.setTitle("Eating Beans");
		frame.setSize(800,600);
		frame.getContentPane().setBackground(Color.BLACK);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
				
		//ImageIcon wall_1=new ImageIcon("wall-1.jpg");
		
		frame.setVisible(true);
	}
	
	public void setPoint(int a,int b,int c,int d) {
		addPoint[0]=a;
		addPoint[1]=b;
		addPoint[2]=c;
		addPoint[3]=d;
		for(int i=0;i<4;i++){
		System.out.println(addPoint[i]);
		}
	} 
	public void setFinish(boolean finish) {
		System.out.println("finish");
		this.finish= finish;
	}
}
