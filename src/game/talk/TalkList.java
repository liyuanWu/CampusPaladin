package game.talk;

import game.Game;

import java.awt.Graphics;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import system.GameCommon;

public class TalkList {
	private Talk talk;
	private List<Talk> talkList = new ArrayList<Talk>();
	private int talkNumber = 0;
	// *�ж϶Ի��Ƿ����
	private boolean endTalk = true;
	// *�ж��Ƿ��л��Ի�
	private boolean turnTalk = false;

	public TalkList() throws FileNotFoundException {
		for (int i = 0; i < GameCommon.TALK_NUMBER; i++)
			talkList.add(new Talk(i));
	}

	public void setTalkNumber(int talkNumber) {
		this.talkNumber = talkNumber;
		talk = talkList.get(talkNumber);
	}

	public void talk(Graphics g, Game game) {
		talk.setRoleType(game.getRoleType());
		talk.setRoleGender(game.getRoleGender());
		talk.draw(g, game);
		if (talk.isEnd()) {
			this.endTalk = true;
			talk.init();
		}
		if (turnTalk) {
			talk.setTurnTalk(true);
			turnTalk = false;
		}
	}

	public void TalkBegin() {
		endTalk = false;
	}

	public boolean isEndTalk() {
		return endTalk;
	}

	public boolean isTurnTalk() {
		return turnTalk;
	}

	public void turnTalk() {
		this.turnTalk = true;
	}

}