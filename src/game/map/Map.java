package game.map;

import java.util.ArrayList;
import java.util.List;

import system.GameCommon;

public class Map {

	// ��ͼ��ȡ����MapReader
	private MapReader mapReader = new MapReader();
	protected int[][] mapFloor = new int[MapReader.floorX][MapReader.floorY];// map�ذ壬��������map

	public Map() {
	}// ���췽��

	public Map(String map) {
		mapFloor = mapReader.mapRead(map);
	}

	/**
	 * ����������Map
	 */
	public List<Map> getListMap() {
		List<Map> mapList = new ArrayList<Map>();
		for (int i = 0; i < GameCommon.MAP_NUMBER; i++) {
			mapList.add(new Map("map" + i));
		}
		return mapList;
	}
}
