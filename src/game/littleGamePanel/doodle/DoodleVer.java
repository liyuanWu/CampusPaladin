package game.littleGamePanel.doodle;


import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.net.NetPermission;
import java.util.ArrayList;

import javax.swing.JPanel;

public class DoodleVer extends JPanel implements Runnable{

	private Image iBuffer;
	private Image iBackBuffer;
	private Graphics gBackBuffer;
	private Graphics gBuffer;
	
	
	private world gameWorld;
	private static final int FPS = 24;
	private Image[] backPicImage =  new Image[52];
	private Image stair =  Toolkit.getDefaultToolkit().createImage(getClass().getResource( "stair4.jpg "));
	private Image stair2 =  Toolkit.getDefaultToolkit().createImage(getClass().getResource( "stair5.jpg "));
	private Image spring =  Toolkit.getDefaultToolkit().createImage(getClass().getResource( "spring.png"));

	private int procession;
	
	public DoodleVer(world gameWorld) {
		super();
		this.gameWorld = gameWorld;
		this.addKeyListener(gameWorld.mrM);
		this.setFocusable(true);
		for(int i=0;i<52;i++){
			backPicImage[i] = Toolkit.getDefaultToolkit().createImage(getClass().getResource( "backPic_"+(52-i)+".gif "));
		}
	}



	public void paint(Graphics g) {

		if(iBuffer==null){
			iBuffer = createImage(this.getSize().width,this.getSize().height);
			gBuffer = iBuffer.getGraphics();
		}
		gBuffer.setColor(Color.black);
		gBuffer.fillRect(0, 0, 400, 600);
//		gBuffer.setColor(Color.black);
//		gBuffer.fillRect(0, 0, 600, 600);
//		gBuffer.drawImage(backPicImage[k],0,0,this);
//		gBuffer.drawImage(backPicImage[k+1],0,0,this);

		

//		g.drawImage(backPicImage[k],0,0,this);
//		g.drawImage(backPicImage[k+1],0,0,this);
	//	g.setColor(Color.red);

	//	g.setColor(Color.white);
		for(int i=0;i<gameWorld.linesShowList.size();i++){
			if(gameWorld.linesShowList.get(i).isHasTouched()){
				gBuffer.drawImage( gameWorld.linesShowList.get(i).getCandle().getBackImage(), gameWorld.linesShowList.get(i).getX1()-45, gameWorld.linesShowList.get(i).getY()-120,
						this);	
				gBuffer.drawImage(stair2, gameWorld.linesShowList.get(i).getX1(), gameWorld.linesShowList.get(i).getY(),
							gameWorld.linesShowList.get(i).getX2()-gameWorld.linesShowList.get(i).getX1(),18, this);
				gBuffer.drawImage( gameWorld.linesShowList.get(i).getCandle().getImage(), gameWorld.linesShowList.get(i).getCandleX(), gameWorld.linesShowList.get(i).getY()-30,
							20,40, this);
			}
			else{
				gBuffer.drawImage(stair, gameWorld.linesShowList.get(i).getX1(), gameWorld.linesShowList.get(i).getY(),
							gameWorld.linesShowList.get(i).getX2()-gameWorld.linesShowList.get(i).getX1(),18, this);
				gBuffer.drawImage( gameWorld.linesShowList.get(i).getCandle().getCanelImage0(), gameWorld.linesShowList.get(i).getCandleX(), gameWorld.linesShowList.get(i).getY()-30,
							20,40, this);
			}
		}
		for(int i=0;i<gameWorld.itemShowList.size();i++){
			gBuffer.drawImage(spring, gameWorld.itemShowList.get(i).getX1(), gameWorld.itemShowList.get(i).getY()-30, this);
		}
		gBuffer.setColor(Color.red);
		gBuffer.drawImage(gameWorld.mrM.getJumperImage(), gameWorld.mrM.getX()-40, gameWorld.mrM.getY()-55, this);
		gBuffer.drawString(String.valueOf(gameWorld.mrM.getHightestScore()), 10, 10);

			g.drawImage(iBuffer, 200, 0,this);
			g.setColor(Color.gray);
			g.fillRect(0, 0, 200, 600);
			g.fillRect(600, 0, 200, 600);
	}

	public void update(Graphics scr){

	}
	


	@Override
	public void run() {
		while(true){
			repaint();
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	
}
