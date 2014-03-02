package game.littleGamePanel;
import java.awt.*;
public class Blood {
	public static final int HEIGHT=3;
	private int length;
	private int height;
	private int x;
	private int y;
	public Blood(int x,int y,int lenght){
		this.x=x;this.y=y;this.length=length;
	}
	public void setBlood(int length){
		this.length=length;
	}
	public void draw(Graphics g){
		Color c=g.getColor();
		g.setColor(Color.ORANGE);
		g.fillRect(x, y, length, HEIGHT);
		g.setColor(c);
	}
	public void setXY(int x,int y){
		this.x=x;this.y=y;
	}
}
