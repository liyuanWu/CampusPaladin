package game.littleGamePanel.doodle;


import java.awt.Image;

public class VictoryBoard extends lines{
	public int x1;
	public int x2;
	public int height;
	public int width;
	public Image appearance;
	public boolean hasTouched;
	
	public VictoryBoard(int x1,int width,int floor , int frameHigh ,int screenWeith) {
		super(x1, x1+width,floor, 0,frameHigh , screenWeith);
		this.x1=x1;
		this.width = width;
		this.x2 = x1+width;
		this.height = 5;
		//TODO
	}
	
	public void touch() {
		hasTouched = true;
	}
	
	
	
}
