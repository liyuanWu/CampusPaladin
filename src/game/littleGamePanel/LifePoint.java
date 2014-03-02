package game.littleGamePanel;
import java.awt.*;
import java.util.Random;
public class LifePoint {
	public static final int WIDTH=5,HEIGHT=5;
	private static Random rand=new Random();
	private int x, y;
	private boolean live=true;
	public LifePoint(int x,int y){
		this.x=x;this.y=y;
	}
	public Rectangle getRectangle(){
		return new Rectangle(this.x,this.y,WIDTH,HEIGHT);
	}
	public void draw(Graphics g){
		if(!live)return;
		if(rand.nextInt(40)>20){
			Color c=g.getColor();
			g.setColor(Color.PINK);
			g.fillRect(x, y, WIDTH, HEIGHT);
			g.setColor(c);
		}
	}
	public void setLive(boolean live){
		this.live=live;
	}
	public boolean isLive(){
		return live;
	}
}
