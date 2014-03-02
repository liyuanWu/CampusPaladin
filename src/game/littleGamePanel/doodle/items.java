package game.littleGamePanel.doodle;

import java.awt.Image;
import java.util.ArrayList;

public class items extends animation{
	
	public Mario mrM;
	public int x1;
	public int x2;
	private int y;
	private int type;
	private int frameHigh;

	
	public items(Mario mrM, int x1, int x2, int floor, int type,int frameHigh,ArrayList<Image> imageSource) {
		super(imageSource);
		this.mrM = mrM;
		this.x1 = x1;
		this.x2 = x2;
		this.y = y;
		this.type = type;
		this.frameHigh=frameHigh;
		y=-floor*(frameHigh/10)+frameHigh;
	}

	public int getX1() {
		return x1;
	}

	public int getX2() {
		return x2;
	}

	public	int getY(){
		return y;
	}
	
	public void act(){
		switch (type) {
		case 0:
			mrM.superJump();
			break;

		default:
			break;
		}
	}

	public void die(){
		y=frameHigh+32;
	}
	
	public void downTheFloor(int fallingSpeed) {
		y+=fallingSpeed;
	}
}
