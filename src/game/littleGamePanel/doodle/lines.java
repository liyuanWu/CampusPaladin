package game.littleGamePanel.doodle;

import java.awt.Image;
import java.awt.Toolkit;
import java.util.ArrayList;

public class lines {

	public int x1;
	public int x2;
	private int y;
	private int type;
	private boolean hasTouched;

	private items itemOnBoard;
	
	private candles candle;
	
	private int screenHight;
	private int screenWeith;
	private int moveSpeed=3;
	private int life=100;
	private int shortF=2;//越小缩小的越快
	
	
	
	public int getCandleX() {
		return (x2+x1)/2-10;
	}

	public lines(int x1, int x2, int floor,int type,int frameHigh,int screenWeith) {
		this.x1 = x1;
		this.x2 = x2;
		this.type=type;
		//这里出过错！！！
		this.screenHight = frameHigh;
		this.screenWeith=screenWeith;
		this.y=-floor*(frameHigh/10)+frameHigh;
		ArrayList<Image> im = new ArrayList<Image>();
		for(int i=1;i<=4;i++){
			im.add(Toolkit.getDefaultToolkit().createImage(getClass().getResource( "candle"+i+".jpg")));
		}
		this.candle = new candles();
	}

	public lines(int x1, int x2, int floor,int type,int frameHigh,int screenWeith,items newItem){
		this.x1 = x1;
		this.x2 = x2;
		this.type=type;
		//这里出过错！！！
		this.screenHight = frameHigh;
		this.screenWeith=screenWeith;
		this.y=-floor*(frameHigh/10)+frameHigh;
		this.itemOnBoard=newItem;
		this.candle = new candles();

	}
	public candles getCandle() {
		return candle;
	}

	public boolean isHasTouched() {
		return hasTouched;
	}
	
	public void process(){
		candle.process();
	}

	public void downTheFloor(int speed){
		y+=speed;
	}
	
	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public int getY() {
		return y;
	}
	
	public void act(){
		switch (type) {
		case 1:
			moveX();
			break;
		case -1:
			cheatMoveY();
			break;
		case 2:
			dying();
			//这里出过错！！break没有写
			break;
		case 3:
			shorting();
			//这里出过错！！break没有写
			break;
		default:
			break;
		}
	}
	
	private void shorting() {
		if(hasTouched)
			return;
		
			if(x1<x2&&shortF<0){
				x1++;
				x2--;
				if(itemOnBoard!=null)
					if(x1>itemOnBoard.getX1()||x2<itemOnBoard.getX2())
						itemOnBoard.die();
				
				shortF=5;
			}else{
				shortF--;
			}
	}

	private void dying() {
		if(life<=0){
			y=screenHight+32;
			if(itemOnBoard!=null)
				itemOnBoard.die();
			return;
		}
		life--;
	}

	public int getType() {
		return type;
	}

	private void cheatMoveY() {
		if(y<0||y>screenHight)
			moveSpeed=-moveSpeed;
		y+=moveSpeed;
		
	}

	public void moveX(){
		if(x1<0||x2>screenWeith)
			moveSpeed=-moveSpeed;
		x1+=moveSpeed;
		x2+=moveSpeed;
		if(itemOnBoard!=null){
				itemOnBoard.x1+=moveSpeed;
				itemOnBoard.x2+=moveSpeed;
		}

	}
	public void touch() {
		hasTouched = true;
	}
}
