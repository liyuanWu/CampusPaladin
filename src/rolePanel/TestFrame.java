package rolePanel;

import java.awt.Font;
import java.io.IOException;

import javax.swing.JFrame;

public class TestFrame extends JFrame{
	RolePointsPanel rolePointsPanel ;
	String fileName;
	public TestFrame(){
		int[] points = {50,50,50,50};
		fileName = "activityBoy.png";
		//try {
		 //rolePointsPanel = new RolePointsPanel(points,fileName);
		//} catch (IOException e) {
		//	e.printStackTrace();
		//}
		
		this.add(rolePointsPanel);
		this.setSize(800, 600);
		this.setTitle("Role Panel");
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Font font = new Font("Times New Roman", Font.ITALIC, 25);
		this.setFont(font);
		
		
	}
	public static void main(String[] args) {
		TestFrame ts=new TestFrame();

	}

}
