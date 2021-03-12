package com.ggl.minesweeper.model;

import java.awt.Dimension;

public class MinesweeperGameTypeFactory {

	private static MinesweeperGameTypeFactory instance;

	private MinesweeperGameTypeFactory() {

	}

	public static MinesweeperGameType getGameType(int setup) {
		if (instance == null) {
			instance = new MinesweeperGameTypeFactory();
		}
		
		MinesweeperGameType type = new MinesweeperGameType();
		type.setSetup(setup);

		if (setup == 1) {
			type.setLevelName("Easy");
			type.setNumberOfBombs(10);
			type.setRow(10);
			type.setColumn(10);
			type.setPanelSize(new Dimension(300, 300));
			type.setMaximumSize(new Dimension(920, 530));
		} else if (setup == 2) {
			type.setLevelName("Medium");
			type.setNumberOfBombs(40);
			type.setRow(16);
			type.setColumn(16);
			type.setPanelSize(new Dimension(480, 480));
			type.setMaximumSize(new Dimension(920, 530));
		} else {
			type.setLevelName("Hard");
			type.setNumberOfBombs(99);
			type.setRow(17);
			type.setColumn(30);
			type.setPanelSize(new Dimension(900, 510));
			type.setMaximumSize(new Dimension(920, 530));
		}
		
		return type;
	}

}
