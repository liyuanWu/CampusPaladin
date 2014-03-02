package game.littleGamePanel.TableBomb;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class board implements KeyListener{

	public int x;
	public int y;
	int speedX;
	int speedY;
	public int length;
	public int height;
	public int firingBullet;
	boolean up,down,left,right;
	public boolean releaseBomb;
	
	private boolean onBoard;
	private int boardY;

	public int moveAbility;
	
	private int jumpPower;
	
	
	private int mapSpeed;
	private int lineHight;
	private int screenWight;
	private int score;
	private int hightestScore;
	public boolean canMove;
	private double friction;
	
	
	
	
	
	public board(int x, int y, int length, int height, int moveAbility, double f) {
		super();
		this.x = x;
		this.y = y;
		this.length = length;
		this.height = height;
		this.moveAbility = moveAbility;
		this.friction = f;
	}

	public boolean hitBoard(double Px,double Py){
		Px -= x;
		Py -= y;
		if(Py<0){
			if(Px>0&&Px<length){
				return true;
			}
		}
		return false;
	}
	
	public int[] getNormalVector(){
		int[] t = new int[2];
		t[0] = 0;
		t[1] = 100;
		return t;
	}
	
	public void move(){
		accelerate();
		if(canMove){
			translate();
		}
		frict();
	}
	
	private void frict() {
		speedX*=friction;
	}
	
	private void translate() {
		x+=speedX;
		y+=speedY;
	}


	private void fire() {
		firingBullet++;
	}

	public void consumeBullet(){
		firingBullet--;
	}

	private final void accelerate() {
		if(left){
			speedX-=moveAbility;
		}else if(right){
			speedX+=moveAbility;
		}
		
	}

	public void speedUp(int p){
		moveAbility+=p;
	}

	public void longer(int p){
		length+=p;
	}

	@Override
	public void keyTyped(KeyEvent e) {

 	}

	@Override
	public void keyPressed(KeyEvent e) {
		switch (e.getKeyCode()) {
		case KeyEvent.VK_UP:
				up = true;
			break;
		case KeyEvent.VK_DOWN:
				down = true;
			break;
		case KeyEvent.VK_RIGHT:
				right = true;
			break;
		case KeyEvent.VK_LEFT:
				left = true;
			break;
		default:
			break;
		}
		
	}

	@Override
	public void keyReleased(KeyEvent e) {
		switch (e.getKeyCode()) {
			case KeyEvent.VK_SPACE:
					fire();
				break;
			case KeyEvent.VK_UP:
					up = false;
					fire();
				break;
			case KeyEvent.VK_DOWN:
					down = false;
				break;
			case KeyEvent.VK_RIGHT:
					right = false;
				break;
			case KeyEvent.VK_LEFT:
					left = false;
				break;
			case KeyEvent.VK_Z:
				releaseBomb = true;
				break;
			default:
				break;
		}
	}
	
	public int nextX(){
		return x+speedX;
	}
	public int nextY(){
		return y+speedY;
	}
	
}
