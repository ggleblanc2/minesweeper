package com.ggl.minesweeper.view;

import java.awt.Font;

public class MinesweeperFont {

	protected static final String FONT_NAME = "Comic Sans MS";

	public static Font getBoldFont(int pointSize) {
		return new Font(FONT_NAME, Font.BOLD, pointSize);
	}

}

