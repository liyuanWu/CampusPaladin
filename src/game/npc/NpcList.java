package game.npc;

import game.Game;

import java.awt.Color;
import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import system.GameCommon;

public class NpcList implements Serializable{
	private Game game;
	public List<Npc> npcList = new ArrayList<Npc>();
	private Npc[] npc;
	private int firstX;
	private int firstY;
	private int mapNumber;

	// 当前地图拥有的NPC
	private int[] mapNpc = { 1, 2, 4 };

	public NpcList(Game game) {
		this.game = game;
		this.npc = new Npc[mapNpc.length];
		for (int i = 1; i <= GameCommon.NPC_NUMBER; i++)
			try {
				npcList.add(new Npc(i, game));
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			}
	}

	public void setMapNumber(int MapNumber) {
		this.mapNumber = MapNumber;
	}

	public void draw(Graphics g) {
		Color c = g.getColor();
		for (int i = 0; i < mapNpc.length; i++) {
			npc[i] = npcList.get(mapNpc[i] - 1);
			npc[i].setFirstX(firstX);
			npc[i].setFirstY(firstY);
			npc[i].draw(g);
		}
		g.setColor(c);
	}

	public void setFirstX(int firstX) {
		this.firstX = firstX;
	}

	public void setFirstY(int firstY) {
		this.firstY = firstY;
	}

	public void setMapNpc(int[] mapNpc) {
		this.mapNpc = mapNpc;
		this.npc = new Npc[mapNpc.length];
	}

	public Npc[] getNowMapNpc() {
		return npc;
	}

}
