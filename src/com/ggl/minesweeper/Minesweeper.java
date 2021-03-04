package com.ggl.minesweeper;

import javax.swing.SwingUtilities;

import com.ggl.minesweeper.model.MinesweeperModel;
import com.ggl.minesweeper.view.MinesweeperFrame;

public class Minesweeper implements Runnable {

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Minesweeper());
	}

	@Override
	public void run() {
		new MinesweeperFrame(new MinesweeperModel());
	}

}
