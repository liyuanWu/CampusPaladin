package game.map;

import java.util.ArrayList;
import java.util.List;

import system.GameCommon;

public class Map {

	// 地图读取工具MapReader
	private MapReader mapReader = new MapReader();
	protected int[][] mapFloor = new int[MapReader.floorX][MapReader.floorY];// map地板，用来接受map

	public Map() {
	}// 构造方法

	public Map(String map) {
		mapFloor = mapReader.mapRead(map);
	}

	/**
	 * 工厂化生产Map
	 */
	public List<Map> getListMap() {
		List<Map> mapList = new ArrayList<Map>();
		for (int i = 0; i < GameCommon.MAP_NUMBER; i++) {
			mapList.add(new Map("map" + i));
		}
		return mapList;
	}
}
