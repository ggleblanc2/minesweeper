package com.ggl.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import com.ggl.minesweeper.controller.GameMouseListener;
import com.ggl.minesweeper.controller.GridMouseListener;
import com.ggl.minesweeper.model.MinesweeperGameType;
import com.ggl.minesweeper.model.MinesweeperModel;

public class MinesweeperPanel {

	private Font largeFont;
	private Font smallFont;

	private GridLayout gridLayout;
	
	private final GridMouseListener gridMouseListener;

	private JButton easyButton;
	private JButton mediumButton;
	private JButton hardButton;

	private JButton[][] buttonTable;

	private JLabel gameStatus;

	private JPanel flowPanel;
	private JPanel gridPanel;
	private JPanel mainPanel;
	
	private final MinesweeperFrame frame;
	
	private final MinesweeperModel model;

	public MinesweeperPanel(MinesweeperFrame frame, MinesweeperModel model) {
		this.frame = frame;
		this.model = model;
		this.gridMouseListener = new GridMouseListener(frame, model);
		createGUIControl();
	}

	private void createGUIControl() {
		mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.PAGE_AXIS));

		largeFont = MinesweeperFont.getBoldFont(18);
		smallFont = MinesweeperFont.getBoldFont(12);

		JPanel controlPanel = new JPanel(new BorderLayout());

		JPanel labelPanel = new JPanel();

		JLabel newGame = new JLabel("New Game", JLabel.CENTER);
		newGame.setFont(largeFont);

		labelPanel.add(newGame);

		JPanel statusPanel = new JPanel();

		gameStatus = new JLabel(model.setGameStatus(), JLabel.RIGHT);
		gameStatus.setFont(largeFont);

		statusPanel.add(gameStatus);

		JPanel buttonPanel = new JPanel();
		
		GameMouseListener gameMouseListener = 
				new GameMouseListener(frame, model);

		easyButton = new JButton("Easy");
		mediumButton = new JButton("Medium");
		hardButton = new JButton("Hard");

		easyButton.addMouseListener(gameMouseListener);
		mediumButton.addMouseListener(gameMouseListener);
		hardButton.addMouseListener(gameMouseListener);

		easyButton.setFont(smallFont);
		mediumButton.setFont(smallFont);
		hardButton.setFont(smallFont);

		buttonPanel.add(easyButton);
		buttonPanel.add(mediumButton);
		buttonPanel.add(hardButton);

		controlPanel.add(labelPanel, BorderLayout.NORTH);
		controlPanel.add(buttonPanel, BorderLayout.CENTER);
		controlPanel.add(statusPanel, BorderLayout.SOUTH);
		
		flowPanel = new JPanel(new FlowLayout());
		flowPanel.setLayout(new BoxLayout(flowPanel, BoxLayout.PAGE_AXIS));
		flowPanel.setPreferredSize(model.getGameType().getMaximumSize());

		gridLayout = new GridLayout(0, model.getGameType().getColumn());
		gridPanel = new JPanel(gridLayout);
		addGridButtons(model.getGameType());
		
		flowPanel.add(gridPanel);

		mainPanel.add(controlPanel);
		mainPanel.add(flowPanel);
	}

	public void addGridButtons(MinesweeperGameType type) {
		calculateBorder(type);
		gridLayout.setColumns(type.getColumn());
		gridPanel.setPreferredSize(type.getPanelSize());
		buttonTable = new JButton[type.getRow()][type.getColumn()];
		Insets insets = new Insets(0, 0, 0, 0);

		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				buttonTable[x][y] = new JButton();
				buttonTable[x][y].addMouseListener(gridMouseListener);
				buttonTable[x][y].setFont(largeFont);
				buttonTable[x][y].setMargin(insets);
				gridPanel.add(buttonTable[x][y]);
			}
		}

	}

	public void removeGridButtons(MinesweeperGameType type) {
		for (int x = 0; x < type.getRow(); x++) {
			for (int y = 0; y < type.getColumn(); y++) {
				buttonTable[x][y].removeMouseListener(gridMouseListener);
				gridPanel.remove(buttonTable[x][y]);
			}
		}
	}
	
	private void calculateBorder(MinesweeperGameType type) {
		Dimension o = type.getMaximumSize();
		Dimension i = type.getPanelSize();
		
		int heightGap = o.height - i.height;
		int widthGap = o.width - i.width;
		
		int top = heightGap / 2;
		int bottom = heightGap - top;
		int left = widthGap / 2;
		int right = widthGap - left;
		
		flowPanel.setBorder(BorderFactory.createEmptyBorder(
				top, left, bottom, right));
	}

	public JButton[][] getButtonTable() {
		return buttonTable;
	}

	public JPanel getMainPanel() {
		return mainPanel;
	}

	public JLabel getGameStatus() {
		return gameStatus;
	}

	public JButton getEasyButton() {
		return easyButton;
	}

	public JButton getMediumButton() {
		return mediumButton;
	}

	public JButton getHardButton() {
		return hardButton;
	}

}
