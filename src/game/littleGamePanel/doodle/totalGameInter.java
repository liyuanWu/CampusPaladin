package game.littleGamePanel.doodle;



public interface totalGameInter {
	boolean[] littleGameFinished = new boolean[GameCommon.LITTLEGAME_NUMBER + 1];
	public void setFinished(int gameNumber);
}
