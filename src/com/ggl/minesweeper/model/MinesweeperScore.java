package com.ggl.minesweeper.model;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class MinesweeperScore implements Cloneable {

	private static final String DELIMITER = "; ";

	private static final DateFormat LONG_DATE_FORMAT = DateFormat
			.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG);
	private static final DateFormat SHORT_DATE_FORMAT = DateFormat
			.getDateTimeInstance(DateFormat.MEDIUM, DateFormat.SHORT);

	private int gameLevel;

	private long milliseconds;

	private Calendar timestamp;

	private String userid;
	private String displayTime;

	public MinesweeperScore() {

	}

	public MinesweeperScore(int gameLevel, long milliseconds, String displayTime) {
		this.gameLevel = gameLevel;
		this.milliseconds = milliseconds;
		this.displayTime = displayTime;
		setTimestamp();
		setUserid();
	}

	public long getMilliseconds() {
		return milliseconds;
	}

	public void setMilliseconds(long milliseconds) {
		this.milliseconds = milliseconds;
	}

	public int getGameLevel() {
		return gameLevel;
	}

	public void setGameLevel(int gameLevel) {
		this.gameLevel = gameLevel;
	}

	public Calendar getTimestamp() {
		return timestamp;
	}

	public String getLongTimestamp() {
		return LONG_DATE_FORMAT.format(timestamp.getTime());
	}

	public String getShortTimestamp() {
		return SHORT_DATE_FORMAT.format(timestamp.getTime());
	}

	public void setTimestamp() {
		this.timestamp = Calendar.getInstance();
	}

	public String getUserid() {
		return userid;
	}

	public void setUserid() {
		this.userid = System.getProperty("user.name");
	}

	public String getDisplayTime() {
		return displayTime;
	}

	public void setDisplayTime(String displayTime) {
		this.displayTime = displayTime;
	}

	@Override
	public String toString() {
		return "MinesweeperScore [gamelevel=" + gameLevel + ", milliseconds="
				+ milliseconds + ", timestamp=" + getLongTimestamp()
				+ ", userid=" + userid + ", displayTime=" + displayTime + "]";
	}

	public String setFileString() {
		return getLongTimestamp() + DELIMITER + userid + DELIMITER + gameLevel
				+ DELIMITER + displayTime + DELIMITER + milliseconds;
	}

	public void getFileString(String cdString) {
		String[] fields = cdString.split(DELIMITER);
		try {
			Date date = LONG_DATE_FORMAT.parse(fields[0]);
			setTimestamp();
			this.timestamp.setTime(date);
			this.userid = fields[1];
			this.gameLevel = Integer.parseInt(fields[2]);
			this.displayTime = fields[3];
			this.milliseconds = Integer.parseInt(fields[4]);
		} catch (ParseException e) {
			e.printStackTrace();
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
	}

	@Override
	public MinesweeperScore clone() {
		MinesweeperScore score = new MinesweeperScore();
		score.timestamp = this.timestamp;
		score.userid = this.userid;
		score.gameLevel = this.gameLevel;
		score.displayTime = this.displayTime;
		score.milliseconds = this.milliseconds;
		return score;
	}

}

