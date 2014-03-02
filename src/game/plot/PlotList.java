package game.plot;

import game.Game;
import game.GameBody;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import system.GameCommon;

public class PlotList implements Runnable, Serializable {
	private static final String PLOT_TREE = "plot" + File.separator
			+ "plot.txt";
	public boolean isBOSS = false;
	private boolean gaming = true;
	private Game game;
	private List<Plot> plotList = new ArrayList<Plot>();
	private int plotNumber = GameCommon.PLOT_NUMBER;
	// *PLOT是否触发
	private boolean plotStrat = false;
	// *PLOT地点属性
	private int mapNumber;
	private int x;
	private int y;
	// *当前的PLOT
	private int roleType;
	private Plot plot;
	private int plotStep = 0;
	// *Plot树
	private PlotTree plotLine = new PlotTree();
	private int[] plotTree = new int[GameCommon.PLOT_NUMBER];
	// *状态
	private boolean talking = false;

	public PlotList(Game game) throws FileNotFoundException {
		Scanner sc = new Scanner(new File(PLOT_TREE));
		for (int i = 0; i < GameCommon.PLOT_NUMBER; i++)
			plotTree[i] = sc.nextInt();
		this.game = game;
		for (int i = 1; i <= plotNumber; i++) {
			plotList.add(new Plot(i));
		}
		plotLine.setLine(roleType);
		Thread plotListThread = new Thread(this);
		plotListThread.start();
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public void plotStart(int mapNumber, int x, int y) {
		this.mapNumber = mapNumber;
		this.x = x;
		this.y = y;
		synchronized (this) {
			this.plotStrat = true;
		}
	}

	public void run() {
		while (gaming)
			synchronized (this) {
				if (plotStrat) {
					int plotNumber = 0;
					for (Plot option : plotList) {
						plotNumber++;
						if (option.map == mapNumber) {
							if (option.mapX == x && option.mapY == y) {
								this.plot = option;
								break;
							}
						}
					}
					// 本行代码十分重要
					System.out.println(plotLine.current(plotStep) - 1);
					if (!isBOSS) {
						if (plotTree[plotLine.current(plotStep) - 1] == 1
								&& (plotLine.current(plotStep) == plotNumber)) {
							if (plotRealized()) {
								plotTree[plotLine.current(plotStep) - 1] = 0;
								plotTree[plotLine.next(plotStep) - 1] = 1;
								plotStep++;
							}
						}
					}
					else {
						plotRealized();
					}
					this.plotStrat = false;
				}
			}
	}

	// 接下来是PLOT的实现
	private boolean plotRealized() {
		if (littleGame()) {
			game.addRoleMoney(plot.getChangeOfMoney());
			return true;
		}
		if (game.getTalking()) {
			talk();
			if (game.getTalking()) {
				game.addRoleMoney(plot.getChangeOfMoney());
				return true;
			} else
				return false;
		}

		exit();
		return false;
	}

	private boolean littleGame() {
		if (plot.isLittleGamable()) {
			game.setLittleGameNumber(plot.getLittleGame());
			return true;
		}
		return false;
	}

	private void talk() {
		if (plot.isTalkable()) {
			game.talkStart();
			game.talk(plot.getTalkNumber());
		}
	}

	private void exit() {

	}

	// PLOT的实现结束

	public int getPlotStep() {
		return plotStep;
	}

	public void setPlotStep(int plotStep) {
		this.plotStep = plotStep;
	}

	public PlotTree getPlotLine() {
		return plotLine;
	}

	public void setPlotLine(PlotTree plotLine) {
		this.plotLine = plotLine;
	}

	public void useNumberToSetPlotLine(int plotType) {

	}

	public int[] getPlotTree() {
		return plotTree;
	}

	public void setPlotTree(int[] plotTree) {
		this.plotTree = plotTree;
	}

}
