package game.littleGamePanel.doodle;

import java.awt.Image;
import java.awt.Toolkit;
import java.awt.RenderingHints.Key;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

public class Mario implements KeyListener{

	Image[] jumper = new Image[10];
	private int proccess;
	
	public void setMapSpeed(int mapSpeed) {
		this.mapSpeed = mapSpeed;
	}

	public Mario(int nextX, int nextY, boolean onBoard, int gravity, int moveAbility,
			int jumpPower, int mapSpeed) {
		super();
		this.supposeX = nextX;
		this.supposeY = nextY;
		this.onBoard = onBoard;
		this.gravity = gravity;
		this.moveAbility = moveAbility;
		this.jumpPower = jumpPower;
		this.mapSpeed = mapSpeed;
		for(int i=0;i<10;i++){
			jumper[i] = Toolkit.getDefaultToolkit().createImage(getClass().getResource( "jumper"+(i+1)+".png"));
		}
	}

	private int x;
	private int y;
	private int supposeX;
	private int supposeY;
	
	public int getScore() {
		return score;
	}

	private int speedX;
	private int speedY;
	
	private boolean left;
	private boolean right;
	private boolean up;
	
	
	private boolean onBoard;
	private int boardY;

	private int gravity;
	
	private int moveAbility;
	
	private int jumpPower;
	
	
	private int mapSpeed;
	private int lineHight;
	private int screenWight;
	private int score;
	private int hightestScore;


	
	public void setScreenWight(int screenWight) {
		this.screenWight = screenWight;
	}

	public void setLineHight(int lineHight) {
		this.lineHight = lineHight;
	}

	public void surroundingReflect(){
		if(onBoard&&speedY>=0){
			speedY=0;
			supposeY=boardY;
			jump();
		}else{
			gravite();
		}
		frict();
	}
	public void precomputation(){
		movebranch();
		supposeX+=speedX;
		supposeY+=speedY;
		weightAdjust();
	}
	
	public void moveconfirm(){
		x=supposeX;
		y=supposeY;
	}
	
	private void weightAdjust() {
		if(supposeX>screenWight)
			supposeX-=screenWight;
		if(supposeX<0)
			supposeX+=screenWight;
	}

	private void movebranch(){
		if(left) 
			goLeft();
		if(right)
			goRight();
	}
	
	public Image getJumperImage(){
		if(speedX<=0){
			if(speedY>=0){
				return jumper[1];
			}
			return jumper[proccess];
		}else	if(speedX>0){
			if(speedY>=0){
				return jumper[5];
			}
			return jumper[proccess+4];
		}
		return null;
	}
	
	public void process(){
		if(proccess<4){
			proccess++;
		}
	}
	
	public void setOnBoard(boolean onBoard,int boardY) {
		this.onBoard = onBoard;
		this.boardY = boardY;
	}

	public void freeFromBoard(){
		this.onBoard = false;
	}
	
	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	private void gravite() {
		speedY+=gravity;
	}



	public void downWithMap(int speed) {
		supposeY+=speed;	
	}



	private void frict() {
		speedX*=0.8;
		if(speedX<1&&speedX>-1)
			speedX=0;
	}



	public int getHightestScore() {
		return hightestScore;
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
	//这里做过优化
		if(arg0.getKeyCode()==KeyEvent.VK_SPACE) 
			up=true;
		if(arg0.getKeyCode()==KeyEvent.VK_LEFT)
			left=true;
		if(arg0.getKeyCode()==KeyEvent.VK_RIGHT)
			right=true;
	}

	private void goRight() {
		speedX+=moveAbility;
	}

	private void goLeft() {	
		speedX-=moveAbility;
	}

	private void jump() {
		if(onBoard){
			speedY-=jumpPower;
			if(speedX<=0){
				proccess = 2;
			}else{
				proccess = 4;
			}

		}
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		up=false;
		right=false;
		left=false;
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	public int getSpeedY() {
		return speedY;
	}

	public boolean isOnBoard() {
		return onBoard;
	}
	//这里出过错！！方法命名不规范
	public int predictY(){
		return y+speedY;
	}
	public boolean canSee(){
		if(supposeY>lineHight){
			return true;
		}
		return false;
	}
	public void superJump(){
		speedY-=gravity*40;
	}

	public void calculateScore() {

			score+=-speedY;

		if(score<0){
			return;
		}
		if(hightestScore<=score)
			hightestScore=score;
	}
}
