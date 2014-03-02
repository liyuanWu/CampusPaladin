package game.map;

import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

import system.GameCommon;

public class MapReader {
	// ������ʼ
	public static final int floorX = 50;
	public static final int floorY = 50;
	// ��ͼ����λ��
	private String mapName;
	private String mapScript;
	// ��ͼscanner
	private Scanner mapScan;
	// 2��ذ�
	private int[][] floor = new int[floorX][floorY];// �ײ�
	private int[][] thing = new int[floorX][floorY];// ��Ʒ��
	
	public MapReader() {
	}

	/**
	 * ��ʼ��ȡ
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
