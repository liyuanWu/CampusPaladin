package game.littleGamePanel.TableBomb;

import java.util.ArrayList;

import system.GameCommon;

public class basalPlane implements Runnable {

	public static final int FPS = 28;
	public totalGameInter2 tt;
	public int playerLife = 1000;
	public laser attackLaser;
	public int firepower;
	public int continuousAttack = 0;
	ArrayList<item> items = new ArrayList<item>();
	ArrayList<table> tables = new ArrayList<table>();
	ArrayList<table> tablesOnScreen = new ArrayList<table>();
	public int crownBombNum = 0;
	public final int BombNeed = 10;
	public board flat;

	int width, height;

	public basalPlane(totalGameInter2 tt) {
		super();
		this.width = 600;
		this.height = 600;
		this.flat = new board(300, 550, 100, 10, 3, 0.8);
		this.firepower = 30;
		this.tt = tt;
		test();

	}

	private void test() {
		this.items.add(new item(30, 30, (int) (Math.random() * 10)));
		for (int i = 0; i < 100; i++) {
			tables.add(new table((int) (Math.random() * 500), (i - 100) * 150,
					(int) (Math.random() * 10)));
		}
		for (int i = 0; i < 10; i++) {
			tables.add(table.newTable((int) (Math.random() * 500),
					(i - 9) * 150, 1));
		}
		for (int i = 0; i < 10; i++) {
			tables.add(table.newTable((int) (Math.random() * 500),
					(i - 9) * 150 - 3000, 1));
		}

	}

	public boolean atBottomDetection(int nextY) {
		if (nextY >= height) {
			return true;
		}
		return false;
	}

	@Override
	public void run() {
		while (true) {
			if (playerLife <= 0) {
				tt.setFinished(GameCommon.TABLEBOMB_GAME_NUMBER);
				break;
			}
			
			if (tables.size() == 0) {
				tt.setFinished(GameCommon.TABLEBOMB_GAME_NUMBER);
				break;
			}

			
			if (flat.releaseBomb) {
				crownExplode();
				flat.releaseBomb = false;
			}
			if (flat.firingBullet > 0) {
				table SameRow[] = new table[10];
				int line = 0;
				table focus;
				while (flat.firingBullet > 0) {
					for (int i = 0; i < tablesOnScreen.size(); i++) {
						if (!tablesOnScreen.get(i).dying) {
							if (flat.x > (tablesOnScreen.get(i).x - flat.length)
									&& flat.x < tablesOnScreen.get(i).nextXB()) {
								SameRow[line] = tablesOnScreen.get(i);
								line++;
							}
						}
					}
					if (line == 0) {
						flat.consumeBullet();
						break;
					}
					focus = SameRow[0];
					for (int i = 0; i < 10; i++) {
						if (SameRow[i] == null) {
							break;
						}
						if (SameRow[i].y > focus.y) {
							focus = SameRow[i];
						}
					}
					attackLaser = new laser(focus.x + 50, focus.nextYB(),
							focus.x + 50, flat.y);
					focus.hurt(firepower);
					flat.consumeBullet();
				}

			} else {
				attackLaser = null;
			}

			for (int i = 0; i < items.size(); i++) {
				if (!items.get(i).alive) {
					items.remove(i);
					i--;
					continue;
				}
				items.get(i).gravityAffect();
				if (flat.y <= items.get(i).nextYB()
						&& flat.x <= items.get(i).nextXB()
						&& (flat.x + flat.length) >= items.get(i).nextX()) {
					items.get(i).consume(this);
				}
				if (atBottomDetection(items.get(i).nextYB())) {
					items.get(i).touchBottom();
				}
				items.get(i).move();
			}
			tablesFall();
			limitBoard();
			flat.move();

			try {
				Thread.sleep(1000 / FPS);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

	}

	private void tablesFall() {
		for (int i = 0; i < tables.size(); i++) {
			if (!tables.get(i).alive && !tables.get(i).dying) {
				tables.remove(i);
				i--;
				continue;
			}
			if (tables.get(i).dying) {
				tables.get(i).dieProceed();
			} else {
				tables.get(i).move();
			}
			if (tables.get(i).y > 0 && tables.get(i).y < (width - 100)) {
				if (tablesOnScreen.indexOf(tables.get(i)) == -1) {
					tablesOnScreen.add(tables.get(i));
				}

			}
		}
		for (int i = 0; i < tablesOnScreen.size(); i++) {
			if (!tablesOnScreen.get(i).alive && !tablesOnScreen.get(i).dying) {
				continuousAttack++;
				if (continuousAttack >= BombNeed) {
					crownBombNum += continuousAttack / BombNeed;
					continuousAttack = continuousAttack % BombNeed;
				}
				if ((tablesOnScreen.get(i).bones()) % 3 == 0) {
					items.add(new item(tablesOnScreen.get(i).x + 50,
							tablesOnScreen.get(i).y + 50,
							(int) (Math.random() * 10)));
				}
				tablesOnScreen.remove(i);
				i--;
				continue;
			}
			if (tablesOnScreen.get(i).y > width - 100) {
				crushBottom(tablesOnScreen.get(i));
				tablesOnScreen.remove(i);
			}
		}
	}

	private void crushBottom(table table) {
		continuousAttack = 0;
		playerLife -= table.crush();
	}

	private void crownExplode() {
		if (crownBombNum <= 0) {
			return;
		}
		crownBombNum--;
		for (int i = tablesOnScreen.size() - 1; i >= 0; i--) {
			do {
				tablesOnScreen.get(i).hurt(firepower);
			} while (tablesOnScreen.get(i).alive);
		}
	}

	public void enhanceFirePower(int p) {
		firepower += p;
	}

	private void limitBoard() {
		if (flat.nextX() + flat.length > width || flat.nextX() < 0) {
			flat.canMove = false;
		} else {
			flat.canMove = true;
		}
	}
}
