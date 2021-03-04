package com.ggl.minesweeper.model;

public class MinesweeperGameStatus {

	/** true if bomb is present on button [x][y] */
	private boolean bomb[][];
	/** true if flag is present at button [x][y] */
	private boolean flag[][];
	/** true if a 0 (no bombs around) has been exposed */
	private boolean exposed[][];
	/**
	 * true if the button has a number on it or it is a bomb (used for checking
	 * if game is over)
	 */
	private boolean checkwinbool[][];

	private int bombsFound;
	private int bombsRemaining;

	private long startTime;
	private long elapsedTime;

	public MinesweeperGameStatus(MinesweeperGameType type) {
		resetGameStatus(type);
	}

	public void resetGameStatus(MinesweeperGameType type) {
		this.bombsRemaining = type.getNumberOfBombs();
		this.bombsFound = 0;

		this.bomb = new boolean[type.getRow()][type.getColumn()];
		this.flag = new boolean[type.getRow()][type.getColumn()];
		this.exposed = new boolean[type.getRow()][type.getColumn()];
		this.checkwinbool = new boolean[type.getRow()][type.getColumn()];

		resetBooleans(type);
		setBombs(type);
	}

	private void resetBooleans(MinesweeperGameType type) {
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				bomb[x][y] = false;
				flag[x][y] = false;
				exposed[x][y] = false;
				checkwinbool[x][y] = false;
			}
		}
	}

	private void setBombs(MinesweeperGameType type) {
		int count = 0;
		while (count < bombsRemaining) {
			int randx = (int) (Math.random() * (type.getRow() - 2) + 1);
			int randy = (int) (Math.random() * (type.getColumn() - 2) + 1);
			if (bomb[randx][randy] == false) {
				bomb[randx][randy] = true;
				checkwinbool[randx][randy] = true;
				count++;
			}
		}
	}

	public boolean isBomb(int x, int y) {
		return bomb[x][y];
	}

	public boolean isFlag(int x, int y) {
		return flag[x][y];
	}

	public void setFlag(int x, int y, boolean flag) {
		this.flag[x][y] = flag;
	}

	public boolean isExposed(int x, int y) {
		return exposed[x][y];
	}

	public void setExposed(int x, int y, boolean exposed) {
		this.exposed[x][y] = exposed;
	}

	public boolean isBooleanWin(int x, int y) {
		return checkwinbool[x][y];
	}

	public void setBooleanWin(int x, int y, boolean win) {
		this.checkwinbool[x][y] = win;
	}

	public void setStartTime() {
		this.startTime = System.currentTimeMillis();
	}

	public long getStartTime() {
		return startTime;
	}

	public void setElapsedTime(long elapsedTime) {
		this.elapsedTime = elapsedTime;
	}

	public long getElapsedTime() {
		this.elapsedTime = System.currentTimeMillis() - getStartTime();
		return elapsedTime;
	}

	public long getElapsedTimeInSeconds() {
		return elapsedTime / 1000L;
	}

	public String getFormattedElapsedTime() {
		StringBuilder sb = new StringBuilder();

		long currTime = getElapsedTimeInSeconds();
		int hours = (int) (currTime / 3600L);
		currTime -= (long) hours * 3600L;
		int minutes = (int) (currTime / 60L);
		int seconds = (int) currTime - minutes * 60;

		if (hours > 0) {
			sb.append(hours);
			sb.append(":");
			if (minutes < 10) {
				sb.append("0");
			}
		}
		if (minutes > 0) {
			sb.append(minutes);
			sb.append(":");
			if (seconds < 10) {
				sb.append("0");
			}
		}
		sb.append(seconds);

		return sb.toString();
	}

	public int bombFound() {
		++this.bombsFound;
		return --this.bombsRemaining;
	}

	public int bombNotFound() {
		--this.bombsFound;
		return ++this.bombsRemaining;
	}

	public int getBombsRemaining() {
		return bombsRemaining;
	}

	public int getBombsFound() {
		return bombsFound;
	}

	/**
	 * Checks surrounding 8 squares for number of bombs (it does include itself,
	 * but has already been checked for a bomb so it won't matter)
	 * 
	 * @param type
	 *            - Small, medium, or large game
	 * @param x
	 *            - x position of center square
	 * @param y
	 *            - y position of center square
	 * @return number of bombs surrounding center square
	 */
	public int surroundingBombs(MinesweeperGameType type, int x, int y) {
		int surBombs = 0;
		for (int q = x - 1; q <= x + 1; q++) {
			for (int w = y - 1; w <= y + 1; w++) {
				while (true) {
					// makes sure that it wont have an error for buttons next to
					// the wall
					if (q < 0 || w < 0 || q >= type.getRow()
							|| w >= type.getColumn())
						break;
					if (bomb[q][w] == true)
						surBombs++;
					break;
				}
			}
		}
		return surBombs;
	}

}
