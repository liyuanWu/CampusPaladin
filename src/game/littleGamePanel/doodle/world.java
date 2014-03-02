package game.littleGamePanel.doodle;



import java.lang.reflect.Member;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Hashtable;

public class world implements Runnable{
//HashSet????
	private static final int FPS = 24;
	private int procession;
	private ArrayList<lines> linesList = new ArrayList<lines>();
	public ArrayList<lines> linesShowList = new ArrayList<lines>();
	private ArrayList<items> itemList = new ArrayList<items>();
	public ArrayList<items> itemShowList = new ArrayList<items>();
	public ArrayList<VictoryBoard> VB = new ArrayList<VictoryBoard>();;
	public Mario mrM;
	private int floorNow;
	private boolean gaming = true;
	public int height=600;
	public int weight=400;
	public static final int mLineWeight = 120;
	public static final int springWeight = 30;
	private int speed;
	connector connection;
	
	public void setSpeed(int speed) {
		mrM.setMapSpeed(speed);
		mrM.setScreenWight(weight);
		this.speed = speed;
	}

	public world(connector connection,int weight,int height, int intelligence, int strength, int honour) {
		super();
		this.connection = connection;
		this.height=height;
		this.weight=weight;
		mrM=new Mario(50, 0, false, 3, 4,(int)(strength), 0);
		mrM.setLineHight((int)(height*0.2));
	}

	public void addMLine(int x1,int floor){
		if(x1+mLineWeight>weight)
			return;
		lines newLine = new lines(x1,x1+mLineWeight,floor,0,height,weight);
		linesList.add(newLine);
	}
	
	public void addMLine(int x1,int floor,int type){
		if(x1+mLineWeight>weight)
			return;
		lines newLine = new lines(x1,x1+mLineWeight,floor,type,height,weight);
		linesList.add(newLine);
	}
	
	public void addMLine(int x1,int floor,items newItems){
		if(x1+mLineWeight>weight)
			return;
		lines newLine = new lines(x1,x1+mLineWeight,floor,0,height,weight,newItems);
		linesList.add(newLine);
	}
	
	public void addMLine(int x1,int floor,int type,items newItems){
		if(x1+mLineWeight>weight)
			return;
		lines newLine = new lines(x1,x1+mLineWeight,floor,type,height,weight,newItems);
		linesList.add(newLine);
	}

	public items addSpring(int x1,int floor,int type){
		if(x1+springWeight>weight)
			x1 = weight-springWeight;
		items newItems = new items(mrM, x1, x1+springWeight, floor, type,height,null);
		itemList.add(newItems);
		return newItems;
	}
	
	public void setVictoryBoard(int x,int width,int floor){
		VictoryBoard p =new VictoryBoard(x, width, floor, height, weight);
		VB.add(p);
		linesList.add(p);
	}
	
	@Override
	
	public void run() {

		while(gaming){
			for(int i=0;i<VB.size();i++){
				if(VB.get(i).hasTouched){
					gaming = false;
					connection.gameover();
				}
			}

			moveBoard();

			judgeOnboard();	
			judgeDead();
			judgeOnItem();
			
	
				if(!mrM.canSee()&&mrM.getSpeedY()<0){
					allDownTheFloor(-mrM.getSpeedY());
					mrM.downWithMap(-mrM.getSpeedY());
				}

			

			mrM.surroundingReflect();
			mrM.precomputation();
			mrM.moveconfirm();
			mrM.calculateScore();
			
			worldProccess();

			
			try {
				Thread.sleep(1000/FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void worldProccess() {
		procession++;
		if(procession%3==0){
			for(int i=0;i<linesShowList.size();i++){
				linesShowList.get(i).process();
			}
			mrM.process();
//			for(int i=0;i<itemShowList.size();i++){
//				itemShowList.get(i).process();
//			}
		}
		if(procession>=FPS){
			procession = 0;
		}
	}

	private void judgeOnItem() {
		for(int i=0;i<itemShowList.size();i++){
			if (mrM.getSpeedY() >= 0)
				if ((mrM.getY() < itemShowList.get(i).getY() && mrM.predictY() > itemShowList	.get(i).getY())
						|| mrM.getY() == itemShowList.get(i).getY()||mrM.predictY() == itemShowList.get(i).getY())
					// 这里出过错！！
					if (mrM.getX() > itemShowList.get(i).getX1()
							&& mrM.getX() <itemShowList.get(i).getX2()) {
						itemShowList.get(i).act();
					}
		}
		
	}

	private void moveBoard() {
		for(int i=0;i<linesShowList.size();i++){
			linesShowList.get(i).act();
		}
		
	}

	private void judgeDead() {
		if(mrM.getY()>height)
			connection.gameover();
		
	}

	private void judgeOnboard() {
		boolean onBoard=false;
	
		for (int i = 0; i < linesShowList.size(); i++) {
			// 这里出过错！！
			if (mrM.getSpeedY() >= 0)
				if ((mrM.getY() < linesShowList.get(i).getY() && mrM.predictY() > linesShowList	.get(i).getY())
						|| mrM.getY() == linesShowList.get(i).getY()||mrM.predictY() == linesShowList.get(i).getY())
					// 这里出过错！！
					if (mrM.getX() > linesShowList.get(i).getX1()
							&& mrM.getX() < linesShowList.get(i).getX2()) {
						
						mrM.setOnBoard(true, linesShowList.get(i).getY());
						onBoard = true;
						linesShowList.get(i).touch();
						break;
					}
		}
		
		if(!onBoard)
			mrM.freeFromBoard();
		
	}

	public void refreshTheFloor(){
		for(int i=0;i<linesList.size();i++){
			if(linesList.get(i).getY()>0&&!linesShowList.contains(linesList.get(i)))
				linesShowList.add(linesList.get(i));
		}
		for(int i=0;i<itemList.size();i++){
			if(itemList.get(i).getY()>0&&!itemShowList.contains(itemList.get(i)))
				itemShowList.add(itemList.get(i));
		}
	}
	
	
	public void allDownTheFloor(int fallingSpeed){
		for(int i=0;i<linesList.size();i++){
			linesList.get(i).downTheFloor(fallingSpeed);
			
			if(linesList.get(i).getY()>height){
				linesShowList.remove(linesList.get(i));
				linesList.remove(i);
				i--;
				continue;
			}
			
			if(linesList.get(i).getY()>0&&!linesShowList.contains(linesList.get(i)))
				linesShowList.add(linesList.get(i));
		}
		for(int i=0;i<itemList.size();i++){
			itemList.get(i).downTheFloor(fallingSpeed);
			
			if(itemList.get(i).getY()>height){
				itemShowList.remove(itemList.get(i));
				itemList.remove(i);
				i--;
				continue;
			}
			
			if(itemList.get(i).getY()>0&&!itemShowList.contains(itemList.get(i)))
				itemShowList.add(itemList.get(i));
		}
	}
	
	
}
