package com.ggl.minesweeper.model;

import java.awt.Dimension;

public class MinesweeperGameType {

	private int row;
	private int column;
	private int numberOfBombs;
	private int setup;
	
	private Dimension panelSize;
	private Dimension maximumSize;
	
	private String levelName;

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public int getColumn() {
		return column;
	}

	public void setColumn(int column) {
		this.column = column;
	}

	public int getNumberOfBombs() {
		return numberOfBombs;
	}

	public void setNumberOfBombs(int numberOfBombs) {
		this.numberOfBombs = numberOfBombs;
	}

	public Dimension getPanelSize() {
		return panelSize;
	}

	public void setPanelSize(Dimension panelSize) {
		this.panelSize = panelSize;
	}

	public Dimension getMaximumSize() {
		return maximumSize;
	}

	public void setMaximumSize(Dimension maximumSize) {
		this.maximumSize = maximumSize;
	}

	public int getSetup() {
		return setup;
	}

	public void setSetup(int setup) {
		this.setup = setup;
	}

	public String getLevelName() {
		return levelName;
	}

	public void setLevelName(String levelName) {
		this.levelName = levelName;
	}

}

