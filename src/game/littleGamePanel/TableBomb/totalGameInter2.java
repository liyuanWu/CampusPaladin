package game.littleGamePanel.TableBomb;


public interface totalGameInter2 {
	boolean[] littleGameFinished = new boolean[GameCommon.LITTLEGAME_NUMBER + 1];
	public void setFinished(int gameNumber);
}
