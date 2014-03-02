package game.littleGamePanel.TableBomb;

public class item implements GravityEffect{
	public int x,y;
	public int speedX,speedY;
	public boolean atBottom=false;
	public int width,height;
	public int life;
	public boolean alive;
	private int type;
	
	
	public item(int x, int y, int type) {
		super();
		this.x = x;
		this.y = y;
		this.width = 50;
		this.height =50;
		this.type = type;
		this.life = 1000;
		this.alive = true;
	}

	public int nextY(){
		return y+speedY;
	}
	public int nextYB(){
		return y+speedY+height;
	}
	public int nextX(){
		return x+speedX;
	}
	public int nextXB(){
		return x+speedX+width;
	}
	public void move(){
		if(atBottom){
			speedY = 0;
			life-=8;
		}
		if(life<=0){
			alive=false;
			return;
		}
		x+=speedX;
		y+=speedY;
	}
	
	public void consume(basalPlane bp){
		alive = false;
		if(type%10==0){
			bp.flat.speedUp(1);
		}else if(type%5==0){
			bp.enhanceFirePower(10);
		}else{
			bp.flat.longer(5);
		}
	}
	
	@Override
	public void gravityAffect() {
		if(!atBottom){
			speedY += gravity;
		}
	}
	
	public void touchBottom(){
		atBottom = true;
	}

}
