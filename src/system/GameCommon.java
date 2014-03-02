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
	// *检测碰撞时将人物变小的差值
	public static final int ROLE_PIECE = 5;
	public static final String MAP_FILE = "script" + File.separator;
	// *NPC参数开始
	public static final int NPC_NUMBER = 9;
	// *NPC参数结束
	// 剧情点参数
	public static final int PLOT_NUMBER = 19;
	// 记录了地图中那种数字表示触发剧情
	public static final int PLOT_MAP_NUMBER = 5;
	public static final int DOOR_MAP_NUMBER = 9;
	// 对话数目
	public static final int TALK_NUMBER = 11;
	// 小游戏数目 + 1
	public static final int LITTLEGAME_NUMBER = 10;
	// table Bomb游戏编号
	public static final int TABLEBOMB_GAME_NUMBER = 4;
	// 人物面板图片文件数组
	public static final String[] ROLE_PANEL_FILE = { "活动男.png",
			"活动女.png", "学霸男.png", "学霸女.png",
			"体育男.png", "体育女.png" };
	
}
