package game.map;

// �������Ҫ�õ�Role��һЩ���ԣ���ȷ���Ƿ�Ҫ������ͼ�͵�ͼ��
import game.Game;
import game.Role;
import game.Role.EndMap;
import game.npc.Npc;
import game.npc.NpcList;
import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

import system.GameCommon;

public class MapDraw {
	private static final String MAP_IMAGE_FILE = "script" + File.separator;
	private Game game;
	private Role role;
	private NpcList npcList;
	private List<Map> mapList = new ArrayList<Map>();// ����map��map�б�
	private Map map;
	private Image floor;
	private List<Image> floorList = new ArrayList<Image>();
	// �ж������Ƿ��Ѿ���������Ϊ����npc����ײ��
	private boolean RoleHasSeted = false;
	// �жϵ�ͼ�Ƿ�Ҫ�ػ�
	private boolean repaintMap = true;
	// ��ͼ����
	/*
	 * private BufferedImage offMap = new BufferedImage(MapReader.floorX
	 * GameCommon.GAME_CHIP_X, MapReader.floorY * GameCommon.GAME_CHIP_Y,
	 * BufferedImage.TYPE_4BYTE_ABGR_PRE);
	 */
	// ��ͼ�����Ͻǵľ���λ��
	private int firstX = 0;
	private int firstY = 0;
	// ���ͼ
	private Image[] mapImage = new Image[GameCommon.MAP_NUMBER];

	public MapDraw(Game game, Role role, NpcList npcList) {
		for (int i = 0; i < GameCommon.MAP_NUMBER; i++) {
			try {
				mapImage[i] = ImageIO.read(new File(MAP_IMAGE_FILE + "map" + i
						+ ".png"));
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		this.game = game;
		this.role = role;
		this.npcList = npcList;
		Map map = new Map();
		mapList = map.getListMap();
		for (int i = 0; i < GameCommon.MAP_FLOOR_NUMBER; i++) {
			String test = GameCommon.MAP_FILE + "fl" + (i + 1) + ".png";
			floor = (new ImageIcon(test)).getImage();
			floorList.add(floor);
		}
	}

	/**
	 * ����������
	 */
	public void roleSeted() {
		this.RoleHasSeted = true;
	}

	/**
	 * ������ͼ
	 */
	public void draw(Graphics g, int mapNumber, boolean turnMap) {
		setNpcFirstXY();
		if (RoleHasSeted)
			RoleCollideNpc();
		if (turnMap == true) {
			repaintMap = true;
			game.turnMapFinish();
			npcList.setMapNpc(mapNpc(mapNumber));
		}
		map = mapList.get(mapNumber);
		isEndMap();
		if (role.getStandHere()) {
			this.firstX = role.getFirstX();
			this.firstY = role.getFirstY();
		}

		/*
		 * ���δ������������ײ��ͼ�������� if (repaintMap) { Graphics goffMap =
		 * offMap.createGraphics(); for (int i = 0; i < MapReader.floorX; i++) {
		 * for (int j = 0; j < MapReader.floorY; j++) {
		 * goffMap.drawImage(floorList.get(map.mapFloor[i][j]), i
		 * GameCommon.GAME_CHIP_X, j GameCommon.GAME_CHIP_Y,
		 * GameCommon.GAME_CHIP_Y, GameCommon.GAME_CHIP_Y, this.game); } }
		 * repaintMap = false; } g.drawImage(offMap, firstX, firstY,
		 * MapReader.floorX GameCommon.GAME_CHIP_X, MapReader.floorY
		 * GameCommon.GAME_CHIP_Y, game);
		 */
		game.turnMapFinish();
		g.drawImage(mapImage[mapNumber], this.firstX, this.firstY, this.firstX
				+ MapReader.floorX * GameCommon.GAME_CHIP_X, this.firstY
				+ MapReader.floorY * GameCommon.GAME_CHIP_Y, 0, 0,
				mapImage[mapNumber].getWidth(game),
				mapImage[mapNumber].getHeight(game), game);
	}

	/**
	 * �ж�map������ЩNPC
	 */
	private int[] mapNpc(int mapNumber) {
		switch (mapNumber) {
		case 0:
			return new int[] { 1, 2, 3, 4, 6, 9 };
		case 1:
			//��npc
			//return new int[] { 1, 5, 4 };
		case 2:
			return new int[] {7};
		case 3:
		case 4:
		case 5:
			return new int[]{8};
		case 6:
			return new int[]{5};
		case 7:
		case 8:
		case 9:
		case 10:
		case 11:
		case 12:
		case 13:
		case 14:
		case 15:
		case 16:
		case 17:
		case 18:
		case 19:
			return new int[] { 1, 3 };
		default:
			return null;
		}
	}


	/**
	 * �ж�Npc��Role����ײ����
	 */
	public void RoleCollideNpc() {
		Npc[] npc = npcList.getNowMapNpc();
		for (int o = 0; o < npc.length; o++) {
			Npc i = npc[o];
			if(i == null) {
				int a = 1;
			}
			if (role.getRectangle().intersects(i.getRectangle())) {
				i.collideRole();
				role.collideNpc();
			}
		}
	}

	private void setNpcFirstXY() {
		npcList.setFirstX(firstX);
		npcList.setFirstY(firstY);
	}

	public int getFirstX() {
		return firstX;
	}

	public void setFirstX(int firstX) {
		this.firstX = firstX;
	}

	public int getFirstY() {
		return firstY;
	}

	public void setFirstY(int firstY) {
		this.firstY = firstY;
	}

	/**
	 * �жϵ�ͼ�Ƿ��Ѿ����˱߽�
	 */
	public void isEndMap() {
		if (firstX + GameCommon.GAME_SMALL_CHIP_X > 0)
			role.setEndMap(EndMap.LEFT);
		else if (firstY + GameCommon.GAME_SMALL_CHIP_Y > 0)
			role.setEndMap(EndMap.UP);
		else if (firstX + MapReader.floorX * GameCommon.GAME_CHIP_X
				- GameCommon.GAME_SMALL_CHIP_X < GameCommon.GAME_WIDTH)
			role.setEndMap(EndMap.RIGHT);
		else if (firstY + MapReader.floorY * GameCommon.GAME_HEIGHT
				- GameCommon.GAME_SMALL_CHIP_Y < GameCommon.GAME_CHIP_Y)
			role.setEndMap(EndMap.DOWN);
		else
			role.setEndMap(EndMap.NO);
	}
}