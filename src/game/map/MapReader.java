package game.map;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import system.GameCommon;

public class MapReader {
	// 常量开始
	public static final int floorX = 50;
	public static final int floorY = 50;
	// 地图数据位置
	private String mapName;
	private String mapScript;
	// 地图scanner
	private Scanner mapScan;
	// 2层地板
	private int[][] floor = new int[floorX][floorY];// 底层
	private int[][] thing = new int[floorX][floorY];// 物品层
	
	public MapReader() {
	}

	/**
	 * 开始读取
	 */
	public int[][] mapRead(String mapName) {
		this.mapName = mapName;
		mapScript = GameCommon.MAP_FILE + mapName + ".txt";
		try {
			mapScan = new Scanner(new File(mapScript));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < floorY; i++)
			for (int j = 0; j < floorX; j++) {
				floor[j][i] = mapScan.nextInt();
			}
		return floor;
	}
}
