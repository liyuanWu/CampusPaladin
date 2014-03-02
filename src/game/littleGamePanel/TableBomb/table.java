package game.littleGamePanel.TableBomb;

import java.awt.Image;
import java.awt.Toolkit;

import javax.naming.directory.DirContext;

public class table {
	public int x,y;
	public int moveSpeed;
	public int width,height;
	public int life;
	public boolean dying;
	private int dyingProccess;
	public boolean alive;
	private Image appearance;
	
	
	public static table newTable(int x,int y,int type){
		switch (type) {
		case 0:
			return new table(x,y,type);
		case 1:
			return new table(x, y,200,100,300,2,type);
		default:
			break;
		}
		return null;
	}
	
	public table(int x,int y,int pic) {
		super();
		this.x = x;
		this.y = y;
		this.appearance = Toolkit.getDefaultToolkit().createImage(getClass().getResource(pic+".jpg"));
		this.moveSpeed =5;
		this.width = 100;
		this.height = 100;
		this.life =100;
		this.alive=true;
	}
	
	public table(int x,int y,int width,int height,int life,int moveSpeed,int pic) {
		super();
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
		this.appearance = Toolkit.getDefaultToolkit().createImage(getClass().getResource((10+pic)+".jpg"));
		this.moveSpeed =moveSpeed;
		this.life = life;
		this.alive=true;
	}
	
	
	
	public int nextY(){
		return y+moveSpeed;
	}
	public int nextYB(){
		return y+moveSpeed+height;
	}
	public int nextX(){
		return x;
	}
	public int nextXB(){
		return x+width;
	}
	
	public void move(){
		y+=moveSpeed;
	}
	
	public int bones(){
		return (int) (Math.random()*10);
	}
	
	public void hurt(int p){
		if( (life-p) > 0){
			life-=p;
		}else{
			die();
		}
	}

	private void die() {
		dyingProccess = 10;
		dying = true;
		alive = false;
	}
	
	public void dieProceed() {
		if(--dyingProccess>0){
			width -= width/10;
			height -= height/10;
		}else{
			dying = false;
		}

	}
	
	public Image getImage(){
		return appearance;
	}
	
	public int crush(){
		die();
		return life;
	}
	
}
