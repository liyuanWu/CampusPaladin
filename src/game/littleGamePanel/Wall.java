package game.littleGamePanel;
import java.awt.*;
public class Wall {
	private TankWar tankWar;
	private int x, y, width, height;
	public Wall(int x,int y,int width,int height,TankWar tankWar){
		this.x=x;this.y=y;this.width=width;this.height=height;this.tankWar=tankWar;
	}
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(new Color(36 ,122, 31));
		g.fillRect(x, y, width, height);
		g.setColor(c);
	}
	public Rectangle getRectangle(){
		return new Rectangle(x,y,width,height);
	}
}
