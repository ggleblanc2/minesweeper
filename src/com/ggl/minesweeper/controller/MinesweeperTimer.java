package com.ggl.minesweeper.controller;

import javax.swing.SwingUtilities;

import com.ggl.minesweeper.model.MinesweeperModel;
import com.ggl.minesweeper.view.MinesweeperFrame;

public class MinesweeperTimer implements Runnable {

	private boolean runFlag;

	private long elapsedTime;

	private MinesweeperFrame frame;

	private MinesweeperModel model;

	public MinesweeperTimer(MinesweeperFrame frame, MinesweeperModel model) {
		this.frame = frame;
		this.model = model;
		this.runFlag = true;
	}

	@Override
	public void run() {
		elapsedTime = 0L;
		while (true) {
			long testTime = model.getGameStatus().getElapsedTime() / 1000L;
			if (elapsedTime < testTime) {
				elapsedTime = testTime;
				if (runFlag) {
					setGameStatus();
				}
			}
			sleep(100L);
		}
	}

	private void setGameStatus() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				frame.setGameStatus(model.setGameStatus());
			}
		});
	}

	private void sleep(long duration) {
		try {
			Thread.sleep(duration);
		} catch (InterruptedException e) { /* do nothing */
			assert false;
		}
	}

	public void resetTimer() {
		this.elapsedTime = 0L;
		this.runFlag = true;
	}

	public void pauseTimer() {
		this.runFlag = false;
	}

}
