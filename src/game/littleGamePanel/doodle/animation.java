package game.littleGamePanel.doodle;

import java.awt.Image;
import java.util.ArrayList;


public class animation {

	private ArrayList<Image>imageSource;
	private int process;

	public animation() {
		super();
		this.process = 0;
	}
	
	public void setImageSource(ArrayList<Image> imageSource) {
		this.imageSource = imageSource;
	}

	public animation(ArrayList<Image> imageSource) {
		super();
		this.imageSource = imageSource;
		this.process = 0;
	}
	public Image getImage(){
		return imageSource.get(process);
	}
	public void process(){
		process++;
		if(process>=imageSource.size()){
			process = 0;
		}
	}
	
	
	
}
