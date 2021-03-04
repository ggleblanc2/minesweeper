package com.ggl.minesweeper.controller;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JButton;

import com.ggl.minesweeper.MinesweeperHighScores;
import com.ggl.minesweeper.model.MinesweeperGameStatus;
import com.ggl.minesweeper.model.MinesweeperGameType;
import com.ggl.minesweeper.model.MinesweeperModel;
import com.ggl.minesweeper.view.HighScoresDialog;
import com.ggl.minesweeper.view.MinesweeperFrame;

public class GridMouseListener extends MouseAdapter {
	
	private final MinesweeperFrame frame;
	
	private final MinesweeperModel model;

	public GridMouseListener(MinesweeperFrame frame, MinesweeperModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		MinesweeperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (mouseEvent.getSource() == buttonTable[x][y]) {
					if (mouseEvent.getButton() == MouseEvent.BUTTON1) {
						leftButtonPressed(mouseEvent, x, y);
					} else if (mouseEvent.getButton() == MouseEvent.BUTTON3) {
						rightButtonPressed(mouseEvent, x, y);
					}
					checkWin();
					break;
				}
			}
		}
	}

	private void leftButtonPressed(MouseEvent mouseEvent, int x, int y) {
		MinesweeperGameStatus status = model.getGameStatus();
		MinesweeperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		if (!status.isFlag(x, y)) {
			if (status.isBomb(x, y)) {
				gameOver("You Lose!");
				frame.pauseTimer();
				frame.setGameStatus(model.setGameStatus());
				model.getHighScores().setGameType(type.getSetup());
			} else {
				status.setExposed(x, y, true);
				status.setBooleanWin(x, y, true);

				buttonTable[x][y].setBackground(Color.BLACK);
				int surroundingBombs = status.surroundingBombs(type, x, y);
				if (surroundingBombs > 0) {
					String text = Integer.toString(surroundingBombs);
					buttonTable[x][y].setText(text);
					setColor(surroundingBombs, x, y);
				} else {
					expose(x, y);
				}
			}
		}
	}

	private void rightButtonPressed(MouseEvent mouseEvent, int x, int y) {
		MinesweeperGameStatus status = model.getGameStatus();
		JButton[][] buttonTable = frame.getButtonTable();
		
		// if there is a flag already present set it so that there is no
		// flag
		if (status.isFlag(x, y)) {
			buttonTable[x][y].setText("");
			buttonTable[x][y].setForeground(Color.white);
			status.setFlag(x, y, false);
			status.setBooleanWin(x, y, false);
			status.bombNotFound();
			frame.setGameStatus(model.setGameStatus());
		}
		// if there is no flag, set it so there is a flag
		else if (!status.isBooleanWin(x, y) || status.isBomb(x, y)) {
			buttonTable[x][y].setText("|>");
			buttonTable[x][y].setForeground(Color.red);
			status.setFlag(x, y, true);
			status.setBooleanWin(x, y, true);
			status.bombFound();
			frame.setGameStatus(model.setGameStatus());
		}
	}

	/** Checks if all the button without bombs have been pressed */
	private void checkWin() {
		MinesweeperGameStatus status = model.getGameStatus();
		MinesweeperGameType type = model.getGameType();
		
		boolean allexposed = true;
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (status.isFlag(x, y) && !status.isBomb(x, y)) {
					allexposed = false;
					break;
				}
				if (!status.isBooleanWin(x, y)) {
					allexposed = false;
					break;
				}
			}
		}
		if (allexposed) {
			MinesweeperHighScores highScores = model.getHighScores();
			gameOver("You Win!!");
			frame.pauseTimer();
			frame.setGameStatus(model.setGameStatus());
			highScores.setGameType(type.getSetup());
			highScores.addScore(type.getSetup(), status.getElapsedTime(),
					status.getFormattedElapsedTime());
			new HighScoresDialog(frame.getFrame(), "Minesweeper High Scores",
					type.getLevelName(), highScores);
		}
	}

	/**
	 * Exposes the surrounding 8 buttons
	 * 
	 * @param x
	 *            - x location of center button
	 * @param y
	 *            - y location of center button
	 * */
	private void expose(int x, int y) {
		MinesweeperGameStatus status = model.getGameStatus();
		MinesweeperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		status.setExposed(x, y, true);
		for (int q = x - 1; q <= x + 1; q++) {
			for (int w = y - 1; w <= y + 1; w++) {
				while (true) {
					// makes sure that it wont have an error for buttons
					// next to the wall
					if (q < 0 || w < 0 || q >= type.getRow()
							|| w >= type.getColumn())
						break;
					if (status.isFlag(q, w))
						break;

					status.setBooleanWin(q, w, true);
					buttonTable[q][w].setBackground(Color.BLACK);
					int surroundingBombs = status.surroundingBombs(type, q,
							w);
					if (surroundingBombs > 0) {
						String surrbombs = Integer
								.toString(surroundingBombs);
						buttonTable[q][w].setText(surrbombs);
						setColor(surroundingBombs, q, w);
					} else if (!status.isExposed(q, w)) {
						expose(q, w);
					}
					break;
				}
			}
		}
	}

	private void setColor(int surroundingBombs, int x, int y) {
		JButton[][] buttonTable = frame.getButtonTable();
		
		if (surroundingBombs < 3)
			buttonTable[x][y].setForeground(Color.white);
		else if (surroundingBombs < 5)
			buttonTable[x][y].setForeground(Color.yellow);
		else
			buttonTable[x][y].setForeground(Color.red);
	}

	private void gameOver(String message) {
		MinesweeperGameStatus status = model.getGameStatus();
		MinesweeperGameType type = model.getGameType();
		JButton[][] buttonTable = frame.getButtonTable();
		
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				if (status.isBomb(x, y)) {
					buttonTable[x][y].setBackground(Color.RED);
					buttonTable[x][y].setText("*"); // exposes all bombs
				}
				buttonTable[x][y].setEnabled(false); // disable all buttons
			}
		}

		int x2 = (int) type.getRow() / 2;
		int y2 = (int) type.getColumn() / 2;
		int z2 = (int) message.length() / 2;

		int index = 0;

		for (int i = -z2 - 1; i < z2; i++) {
			String text = String.valueOf(message.charAt(index++));
			buttonTable[x2 - 1][y2 + i].setBackground(Color.BLACK);
			buttonTable[x2 - 1][y2 + i].setForeground(Color.GRAY);
			buttonTable[x2 - 1][y2 + i].setText(text);
		}
	}
}