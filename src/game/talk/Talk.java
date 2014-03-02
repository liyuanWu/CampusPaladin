package game.talk;

import game.Game;

import interludeAnimation.interludeFactory;

import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import javax.imageio.ImageIO;

import system.GameCommon;

public class Talk {
	private static final String BACKGROUND_FILE = "talk" + File.separator
			+ "background.png";
	private static final String IMAGE_FILE_HEAD = "talk" + File.separator
			+ "image" + File.separator;
	private static final String file = "talk" + File.separator + "talk";
	// 对话的第几个位置是对话内容
	private static final int dialoguePlace = 3;
	// 对话的4个角位置
	private static final int TALK_X_A = GameCommon.GAME_WIDTH / 8;
	private static final int TALK_X_B = GameCommon.GAME_WIDTH * 7 / 8;
	private static final int TALK_Y_A = GameCommon.GAME_HEIGHT * 7 / 11;
	private static final int TALK_Y_B = GameCommon.GAME_WIDTH * 8 / 11;
	private static final int lineSpace = 40;
	private static final int LINE_MAX_CHAE = 20;
	private static final int TALK_PAUSE = 3;
	private String talkFile;
	// 对话编号
	private int number;
	// 对话的String链表
	private List<ArrayList<String>> dialogues = new ArrayList<ArrayList<String>>();
	// 对话是否结束
	private boolean endTalk = true;
	private int step;
	private int lineStep;
	private int charStep;
	private int pause;
	private ArrayList<StringBuffer> offStrings = new ArrayList<StringBuffer>();
	// 对话显示位置
	private int x;
	private int y;
	// 对话底层图片
	private Image backgroundImage;
	// 对话是否切换
	private boolean turnTalk;
	// role的属性和性别
	private int roleType;
	private int roleGender;
	// ss专用
	private Image roleImage;
	private Image npcImage;
	private int imageWidth = 200;
	private int imageHeight = 200;
	private boolean isleft;
	private int imageLeftX = TALK_X_A - 40;
	private int imageY = TALK_Y_A - imageHeight - 25;
	private int imageRightX = TALK_X_B - imageWidth;
	private boolean isNpc;

	public Talk(int number) throws FileNotFoundException {
		this.number = number;
		try {
			backgroundImage = ImageIO.read(new File(BACKGROUND_FILE));

			roleImage = ImageIO.read(new File(IMAGE_FILE_HEAD + 0 + ".png"));
			npcImage = ImageIO.read(new File(IMAGE_FILE_HEAD + (number + 1)
					+ ".png"));

		} catch (IOException e) {
			e.printStackTrace();
		}
		talkFile = file + number + ".txt";
		Scanner sc = new Scanner(new File(talkFile));
		StringBuffer stb = new StringBuffer();
		while (sc.hasNext()) {
			stb.append(sc.next());
		}
		String[] parts = stb.toString().split("@");
		for (int i = 1; i < parts.length; i++) {
			ArrayList<String> part = new ArrayList<String>();
			String[] partPart = parts[i].split(":");
			for (String optionOption : partPart) {
				part.add(optionOption);
			}
			dialogues.add(part);
		}
		x = TALK_X_A;
		y = TALK_Y_A;
		offStrings.add(new StringBuffer());

	}

	public void draw(Graphics e, Game game) {
		endTalk = false;
		Graphics2D g = (Graphics2D) e;
		g.setFont(new Font("全新硬笔行书简", Font.BOLD | Font.ITALIC, 24));
		g.drawImage(backgroundImage, TALK_X_A - 40, TALK_Y_A - 40, TALK_X_B,
				TALK_Y_B, 0, 0, backgroundImage.getWidth(game),
				backgroundImage.getHeight(game), game);
		pause++;
		if (pause >= TALK_PAUSE) {
			talkGoing();
			pause = 0;
		}
		// ss专用，画头像
		if (step != dialogues.size()) {

			// if (dialogues.get(step).get(2).equals("1"))
			// isleft = true;
			// else if (dialogues.get(step).get(2).equals("2"))
			// isleft = false;
			// if (dialogues.get(step).get(1).equals("0"))
			// isNpc = false;
			// else if (!dialogues.get(step).get(1).equals("0"))
			// isNpc = true;

			//
			if (isleft && isNpc) {
				g.drawImage(npcImage, imageLeftX, imageY, imageLeftX
						+ imageWidth, imageY + imageHeight, 0, 0,
						npcImage.getWidth(game), npcImage.getHeight(game), game);
			} else if ((!isleft) && isNpc) {
				g.drawImage(npcImage, imageRightX, imageY, imageRightX
						+ imageWidth, imageY + imageHeight, 0, 0,
						npcImage.getWidth(game), npcImage.getHeight(game), game);
			} else if (isleft && !isNpc) {
				g.drawImage(roleImage, imageLeftX, imageY, imageLeftX
						+ imageWidth, imageY + imageHeight, 0, 0,
						npcImage.getWidth(game), npcImage.getHeight(game), game);
			} else if (!isleft && !isNpc) {
				g.drawImage(roleImage, imageRightX, imageY, imageRightX
						+ imageWidth, imageY + imageHeight, 0, 0,
						npcImage.getWidth(game), npcImage.getHeight(game), game);
			}
		}
		//
		for (int i = 0; i < offStrings.size(); i++)
			g.drawString(offStrings.get(i).toString(), TALK_X_A, TALK_Y_A
					+ lineSpace * i);
	}

	/**
	 * 该方法一步一步处理对话数组，机制复杂
	 */
	private void talkGoing() {
		if (step == dialogues.size()) {
			endTalk = true;
			offStrings.removeAll(offStrings);
			return;
		}
		if (charStep < dialogues.get(step).get(dialoguePlace).length()) {
			if (charStep < (lineStep + 1) * LINE_MAX_CHAE) {
				offStrings.get(lineStep)
						.append(dialogues.get(step).get(dialoguePlace)
								.charAt(charStep));
				charStep++;
			} else {
				y += lineSpace;
				lineStep++;
				offStrings.add(new StringBuffer());
			}
		} else if (turnTalk) {
			offStrings.removeAll(offStrings);
			offStrings.add(new StringBuffer());
			step++;
			charStep = 0;
			lineStep = 0;
			turnTalk = false;
		}
		if (step != dialogues.size() ) {
			if (dialogues.get(step).get(2).equals("1"))
				isleft = true;
			else if (dialogues.get(step).get(2).equals("2"))
				isleft = false;
			if (dialogues.get(step).get(1).equals("0"))
				isNpc = false;
			else if (!dialogues.get(step).get(1).equals("0"))
				isNpc = true;
		}
	}

	public boolean isEnd() {
		return endTalk;
	}

	public void init() {
		this.endTalk = false;
		this.charStep = 0;
		this.step = 0;
		this.offStrings.removeAll(offStrings);
		this.offStrings.add(new StringBuffer());
		this.lineStep = 0;
	}

	public void setTurnTalk(boolean turnTalk) {
		this.turnTalk = turnTalk;
	}

	public boolean getTurnTalk() {
		return turnTalk;
	}

	public int getRoleType() {
		return roleType;
	}

	public void setRoleType(int roleType) {
		this.roleType = roleType;
	}

	public int getRoleGender() {
		return roleGender;
	}

	public void setRoleGender(int roleGender) {
		this.roleGender = roleGender;
	}

}
