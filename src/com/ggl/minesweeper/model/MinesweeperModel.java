package com.ggl.minesweeper.model;

import com.ggl.minesweeper.MinesweeperHighScores;

public class MinesweeperModel {
	
	private final MinesweeperGameStatus gameStatus;
	
	private MinesweeperGameType gameType;
	
	private final MinesweeperHighScores highScores;
	
	public MinesweeperModel() {
		this.highScores = new MinesweeperHighScores();
		this.highScores.readScores();
		
		this.gameType = MinesweeperGameTypeFactory.getGameType(
				highScores.getGameType());
		this.gameStatus = new MinesweeperGameStatus(gameType);
		this.gameStatus.setStartTime();
	}
	
	public String setGameStatus() {
		StringBuilder sb = new StringBuilder();
		sb.append(getGameType().getLevelName());
		sb.append(" level: ");
		sb.append("Bombs left - ");
		sb.append(getGameStatus().getBombsRemaining());
		sb.append(", time - ");
		sb.append(getGameStatus().getFormattedElapsedTime());

		return sb.toString();
	}

	public MinesweeperGameStatus getGameStatus() {
		return gameStatus;
	}

	public void setGameType(MinesweeperGameType gameType) {
		this.gameType = gameType;
	}

	public MinesweeperGameType getGameType() {
		return gameType;
	}

	public MinesweeperHighScores getHighScores() {
		return highScores;
	}

}
