package game.littleGamePanel.doodle;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class candles extends animation{

	Image canel0 = Toolkit.getDefaultToolkit().createImage(getClass().getResource( "candle0.png "));
	private ArrayList<Image>imageSource;
	ArrayList<Image> candleB;
	private int proccess,proccessT;
	
	public candles() {
		super();
		ArrayList<Image> im = new ArrayList<Image>();
		for(int i=1;i<=4;i++){
			im.add(Toolkit.getDefaultToolkit().createImage(getClass().getResource( "candle"+i+".png")));
		}
		super.setImageSource(im);
		candleB = new ArrayList<Image>();
		for(int i=1;i<=6;i++){
			candleB.add(Toolkit.getDefaultToolkit().createImage(getClass().getResource( "candleBB"+i+".jpg ")));
		}

	}
	
	public Image getCanelImage0(){
		return canel0;
	}
	public void process(){
		super.process();
		proccessT++;
		if(proccessT>=candleB.size()){
			proccessT = 0;
		}
	}
	public Image getBackImage(){
		return candleB.get(proccessT);
	}

}
