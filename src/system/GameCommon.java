package system;

import java.io.File;

public class GameCommon {
	public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600;
	public static final int GAME_X = 100, GAME_Y = 100;
	public static final int GAME_CHIP_X = 40;
	public static final int GAME_CHIP_Y = 40;
	public static final int GAME_SMALL_CHIP_X = 5;
	public static final int GAME_SMALL_CHIP_Y = 5;
	public static final int MAP_NUMBER = 8;
	public static final int MAP_MAX_NUMBER = 10;
	public static final int MAP_FLOOR_NUMBER = 6;
	// *�����ײʱ�������С�Ĳ�ֵ
	public static final int ROLE_PIECE = 5;
	public static final String MAP_FILE = "script" + File.separator;
	// *NPC������ʼ
	public static final int NPC_NUMBER = 9;
	// *NPC��������
	// ��������
	public static final int PLOT_NUMBER = 19;
	// ��¼�˵�ͼ���������ֱ�ʾ��������
	public static final int PLOT_MAP_NUMBER = 5;
	public static final int DOOR_MAP_NUMBER = 9;
	// �Ի���Ŀ
	public static final int TALK_NUMBER = 11;
	// С��Ϸ��Ŀ + 1
	public static final int LITTLEGAME_NUMBER = 10;
	// table Bomb��Ϸ���
	public static final int TABLEBOMB_GAME_NUMBER = 4;
	// �������ͼƬ�ļ�����
	public static final String[] ROLE_PANEL_FILE = { "���.png",
			"�Ů.png", "ѧ����.png", "ѧ��Ů.png",
			"������.png", "����Ů.png" };
	
}
