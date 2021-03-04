package com.ggl.minesweeper.controller;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import com.ggl.minesweeper.model.MinesweeperGameStatus;
import com.ggl.minesweeper.model.MinesweeperGameType;
import com.ggl.minesweeper.model.MinesweeperGameTypeFactory;
import com.ggl.minesweeper.model.MinesweeperModel;
import com.ggl.minesweeper.view.MinesweeperFrame;

public class GameMouseListener extends MouseAdapter {
	
	private final MinesweeperFrame frame;
	
	private final MinesweeperModel model;

	public GameMouseListener(MinesweeperFrame frame, MinesweeperModel model) {
		this.frame = frame;
		this.model = model;
	}

	@Override
	public void mousePressed(MouseEvent mouseEvent) {
		MinesweeperGameType type = model.getGameType();
		MinesweeperGameStatus status = model.getGameStatus();
		frame.removeGridButtons(type);
		
		if (mouseEvent.getSource() == frame.getEasyButton()) {
			type = MinesweeperGameTypeFactory.getGameType(1);
		} else if (mouseEvent.getSource() == frame.getMediumButton()) {
			type = MinesweeperGameTypeFactory.getGameType(2);
		} else if (mouseEvent.getSource() == frame.getHardButton()) {
			type = MinesweeperGameTypeFactory.getGameType(3);
		} else {
			System.err.println("Unknown game size");
		}
		
		model.setGameType(type);
		status.setStartTime();
		status.setElapsedTime(0L);
		status.resetGameStatus(type);
		
		frame.addGridButtons(type);
		frame.resetTimer();
		frame.setGameStatus(model.setGameStatus());
	}

}
