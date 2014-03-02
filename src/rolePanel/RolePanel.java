package rolePanel;

import game.GameBody;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

public class RolePanel extends JPanel {
	private static final String ROLE_FILE = "headPic" + File.separator
			+ "BE00AAA.png";
	
	private GameBody gamebody;
	private int[] points = new int[4];
	private Image background;
	private Image role;
	private Image[] pointsImage = new Image[4];
	private JSlider slider = new JSlider();
	BufferedImage bi = new BufferedImage(800, 800,
			BufferedImage.TYPE_4BYTE_ABGR_PRE);
	BufferedImage bi2 = new BufferedImage(800, 800,
			BufferedImage.TYPE_4BYTE_ABGR_PRE);

	public RolePanel() {
		try {
			role = ImageIO.read(new File(ROLE_FILE));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void draw(Graphics g) {

	}
}
