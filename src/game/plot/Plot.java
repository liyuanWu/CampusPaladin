package game.plot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.Scanner;

public class Plot implements Serializable {
	private String plotFile = "plot" + File.separator + "plot";
	private String role;
	private boolean stable = false;
	private boolean locationable = false;
	protected int map;
	protected int mapX;
	protected int mapY;
	private boolean littleGamable = false;
	private int littleGame;
	private boolean talkable = false;
	private int talkNumber;
	private boolean choiceable;
	private int yes;
	private int no;
	private boolean usable = false;
	private int changeOfMoney = 0;

	public Plot(int number) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(plotFile + number + ".txt"));
		sc.next();
		role = sc.next();
		sc.next();
		stable = sc.nextBoolean();
		sc.next();
		locationable = sc.nextBoolean();
		if (locationable) {
			sc.next();
			map = sc.nextInt();
			mapX = sc.nextInt();
			mapY = sc.nextInt();
		}
		sc.next();
		littleGamable = sc.nextBoolean();
		if (littleGamable) {
			sc.next();
			littleGame = sc.nextInt();
		}
		sc.next();
		talkable = sc.nextBoolean();
		if (talkable) {
			sc.next();
			talkNumber = sc.nextInt();
		}
		sc.next();
		choiceable = sc.nextBoolean();
		if (choiceable) {
			sc.next();
			yes = sc.nextInt();
			sc.next();
			no = sc.nextInt();
		}
		sc.next();
		changeOfMoney = sc.nextInt();
		sc.close();
	}

	public boolean isStable() {
		return stable;
	}

	public boolean isLocationable() {
		return locationable;
	}

	public boolean isLittleGamable() {
		return littleGamable;
	}

	public int getLittleGame() {
		return littleGame;
	}

	public boolean isTalkable() {
		return talkable;
	}

	public int getTalkNumber() {
		return talkNumber;
	}

	public boolean isUsable() {
		return usable;
	}

	public int getChangeOfMoney() {
		return changeOfMoney;
	}

}
