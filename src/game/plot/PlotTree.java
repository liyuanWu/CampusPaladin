package game.plot;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlotTree implements Serializable {
	private static final String PLOT_TREE_FILE = "plot" + File.separator
			+ "plotTree.txt";
	private static final int FIRST_LINE = 11;
	private static final int SECOND_LINE = 10;
	private List<Integer> currentList;
	private int currentLine;
	private List<Integer> plotList1 = new ArrayList<Integer>();
	private List<Integer> plotList2 = new ArrayList<Integer>();
	private List<Integer> plotList3 = new ArrayList<Integer>();
	private List<Integer> plotList4 = new ArrayList<Integer>();
	private List<Integer> plotList5 = new ArrayList<Integer>();
	private List<Integer> plotList6 = new ArrayList<Integer>();
	private List<List<Integer>> allPlotList = new ArrayList<List<Integer>>();

	public PlotTree() {
		try {
			Scanner sc = new Scanner(new File(PLOT_TREE_FILE));
			sc.next();
			for (int i = 0; i < FIRST_LINE; i++)
				plotList1.add(sc.nextInt());
			for (int i = 0; i < SECOND_LINE; i++)
				plotList2.add(sc.nextInt());
			sc.next();
			for (int i = 0; i < FIRST_LINE; i++)
				plotList3.add(sc.nextInt());
			for (int i = 0; i < SECOND_LINE; i++)
				plotList4.add(sc.nextInt());
			sc.next();
			for (int i = 0; i < FIRST_LINE; i++)
				plotList5.add(sc.nextInt());
			for (int i = 0; i < SECOND_LINE; i++)
				plotList6.add(sc.nextInt());
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		allPlotList.add(plotList1);
		allPlotList.add(plotList2);
		allPlotList.add(plotList3);
		allPlotList.add(plotList4);
		allPlotList.add(plotList5);
		allPlotList.add(plotList6);
	}

	public int current(int plotStep) {
		return currentList.get(plotStep);
	}

	public int next(int plotStep) {
		return currentList.get(++plotStep);
	}

	public void turn() {
		currentList = allPlotList.get(currentLine + 1);
		currentLine++;
	}

	public void setLine(int roleType) {
		currentList = allPlotList.get(roleType * 2 + 1);
	}
}
