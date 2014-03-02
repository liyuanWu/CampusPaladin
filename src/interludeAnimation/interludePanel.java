package interludeAnimation;

import game.littleGamePanel.doodle.totalGameInter;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.Scanner;
import java.util.regex.Pattern;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class interludePanel extends JPanel implements Runnable{
	
	public static final int FPS = 24;
	public int frame;
	public totalGameInter controler;
	private boolean running;
	private Image backImage;
	private boolean hasBackImage;
	private String title;
	private String[] text;
	int paintingLine;
	int waitingTime;
	Scanner textLoader;
	
	public interludePanel(int i,totalGameInter p) throws FileNotFoundException, URISyntaxException {
		super();
		this.setSize(800, 600);
		this.controler = p;
		textLoader = new Scanner(new File(getClass().getResource(i+".txt").toURI()));
		this.text = new String[50];
		this.backImage = Toolkit.getDefaultToolkit().createImage(getClass().getResource("backPic.jpg"));
		this.running = true;
		Thread interludeT = new Thread(this);
		interludeT.start();
	}

	public void paintComponent(Graphics g){
		if(!hasBackImage){
			g.drawImage(backImage, 0, 0,800,600, this);
			hasBackImage = false;
		}
		
		
		g.setFont(new Font("全新硬笔行书简", Font.BOLD | Font.ITALIC, 35));
		if(title!=null)
			g.drawString(title, 40,50);
		g.setFont(new Font("全新硬笔行书简",Font.PLAIN, 32));
		for(int i=0;i<paintingLine-1;i++){
			g.drawString(text[i], 70, 100+40*i);
		}
	}
	
	@Override
	public void run() {
		while(running){
			if(frame%36==0){
				addTXT();
				repaint();
			}
			frame++;
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		controler.setFinished(10);
	}

	private void addTXT() {
		if(paintingLine == 0){
			title = textLoader.nextLine();
			paintingLine++;
		}else{
			if(text[paintingLine-1]==null){
				text[paintingLine-1] = "";
			}
			if(!textLoader.hasNext()){
				waitingTime++;
				if(waitingTime==5){
					controler.setFinished(10);
				}
			}else{
				text[paintingLine-1] = textLoader.nextLine();
				paintingLine++;
			}
		}
		
		
	}
	
	
	
	

}
