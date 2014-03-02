package game.littleGamePanel;

import java.awt.*;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import media.*;

public class Explode {
	SoundPlayer dieSoundEffect = new SoundPlayer("die",false);
	private TankWar tankWar;
	private int x, y;
	private boolean live;
	private Image explodeWind;
	private int explodeSize = 65;
	private int[] explodeSource = new int[14];
	private int step = 0;

	public Explode(int x, int y, boolean live, TankWar tankWar) {
		this.x = x;
		this.y = y;
		this.live = live;
		this.tankWar = tankWar;
	}

	public void draw(Graphics g) {
		try {
			explodeWind = ImageIO.read(new File("Darkness.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int pieceWidth = (int) (explodeWind.getWidth(null) / 5);
		int pieceHeight = (int) (explodeWind.getHeight(null) / 3);

		if (!live) {
			tankWar.explodes.remove(this);
			dieSoundEffect.play();
			return;
		}
		if (step == explodeSource.length) {
			live = false;
			step = 0;
			return;
		}
		Color c = g.getColor();
		g.setColor(Color.ORANGE);
		// g.fillOval(x, y, explode[step], explode[step]);
		g.drawImage(explodeWind, x, y, x + explodeSize, y + explodeSize,
				(int) ((step % 5) * pieceWidth),
				(int) ((step / 5) * pieceHeight),
				(int) ((step % 5) * pieceWidth) + pieceWidth,
				(int) ((step / 5) * pieceHeight) + pieceHeight, null);
		step++;
	}
}
