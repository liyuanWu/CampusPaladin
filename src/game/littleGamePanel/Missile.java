package game.littleGamePanel;
import java.awt.*;
import java.util.List;
public class Missile {
	private TankWar tankWar;
	private int x,y;
	public static final int XSPEED=20,YSPEED=20;
	public static final int WIDTH=8,HEIGHT=8;
	private Tank.Direction dir;
	private boolean live=true;
	private boolean myMissile=true;
	public Missile(int x,int y,Tank.Direction dir,TankWar tankWar){
		this.x=x;this.y=y;this.dir=dir;this.tankWar=tankWar;
	}
	public Missile(int x,int y,Tank.Direction dir,boolean myMissile,TankWar tankWar){
		this.x=x;this.y=y;this.dir=dir;this.myMissile=myMissile;this.tankWar=tankWar;
	}
	public void draw(Graphics g){
		Color c=g.getColor();
		Color  myColor= new Color(230, 140, 20);
		Color  enemyColor = new Color(50, 120, 170);
		if(myMissile)g.setColor(myColor);
		else g.setColor(enemyColor);
		g.fillOval(x, y, WIDTH, HEIGHT);
		g.setColor(c);
		move();
	}
	private void move(){
		switch(dir){
		case L:x-=XSPEED;break;
		case LU:x-=XSPEED;y-=YSPEED;break;
		case U:y-=YSPEED;break;
		case RU:x+=XSPEED;y-=YSPEED;break;
		case R:x+=XSPEED;break;
		case RD:x+=XSPEED;y+=YSPEED;break;
		case D:y+=YSPEED;break;
		case LD:x-=XSPEED;y+=YSPEED;break;
		case STOP:break;
		}
		if(x<0||x>TankWar.WIDTH||y<0||y>TankWar.HEIGHT){
			live=false;
		}
	}
	private Rectangle getRectangle(){
		return new Rectangle(x,y,WIDTH,HEIGHT);
	}
	public boolean hitTank(Tank tank){
		if(this.getRectangle().intersects(tank.getRectangle())&&tank.isLive()){			
				if(!tank.isGood()&&myMissile){
					this.live=false;
					tank.setLive(false);
					tankWar.explodes.add(new Explode(x,y,true,tankWar));
					return true;								
				}
				else if(tank.isGood()&&!myMissile){
					this.live=false;
					tank.bloodlength-=15;
					if(tank.bloodlength<=0)
						tank.setLive(false);
					tankWar.explodes.add(new Explode(x,y,true,tankWar));
					return true;	
				}
		}
		return false;
	}
	public boolean hitTanks(List<Tank> enemyTanks){
		for(int i=0;i<enemyTanks.size();i++){
			if(hitTank(enemyTanks.get(i)))
				return true;
		}
		return false;
	}
	public boolean hitWall(List<Wall> walls){
		for(int i=0;i<tankWar.walls.size();i++)
			if(this.getRectangle().intersects(tankWar.walls.get(i).getRectangle())){
				live=false;
				return true;	
			}
		return false;
	}
	public boolean islive(){
		return live;
	}
}
