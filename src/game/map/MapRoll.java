package game.map;

import system.GameCommon;
import game.Role;

/**
 * ���������������Role���MapDraw��ĵ�ͼ������
 * 
 * @author lenovo
 * 
 */
//���������û���ã����Ǹĵ�Role��MapDraw����ʵ��
/*
 * public class MapRoll implements Runnable { Role role; MapDraw mapDraw;
 * 
 * public MapRoll(Role role, MapDraw mapDraw) { this.role = role; this.mapDraw =
 * mapDraw; Thread mapRoll = new Thread(this); mapRoll.start(); }
 * 
 * public void run() { while (true) { int x = role.getRelativeX(); int y =
 * role.getRelativeY(); if (x < GameCommon.GAME_WIDTH / 5 || x >
 * GameCommon.GAME_WIDTH * 4 / 5 || y < GameCommon.GAME_HEIGHT / 5 || y >
 * GameCommon.GAME_HEIGHT * 4 / 5) { role.setStandHere(true);
 * mapDraw.roleStand(role); } else { role.setStandHere(false); } } } }
 */