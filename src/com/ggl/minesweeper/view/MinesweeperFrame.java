package com.ggl.minesweeper.view;

import java.awt.BorderLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;

import com.ggl.minesweeper.controller.MinesweeperTimer;
import com.ggl.minesweeper.model.MinesweeperGameType;
import com.ggl.minesweeper.model.MinesweeperModel;

public class MinesweeperFrame {
	
	private JFrame frame;
	
	private MinesweeperModel model;
	
	private MinesweeperPanel minesweeperPanel;
	
	private	MinesweeperTimer timer;

	public MinesweeperFrame(MinesweeperModel model) {
		this.model = model;
		createAndShowGUI();
	}
	
	private void createAndShowGUI() {
		frame = new JFrame("Minesweeper");
		frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		frame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                exitProcedure();
            }
        });
		
		minesweeperPanel = new MinesweeperPanel(this, model);
		frame.add(minesweeperPanel.getMainPanel(), BorderLayout.CENTER);
		
		frame.pack();
		frame.setLocationByPlatform(true);
		frame.setVisible(true);
		
		System.out.println(frame.getSize());
		
		timer = new MinesweeperTimer(this, model);
		new Thread(timer).start();
	}
	
    private void exitProcedure() {
    	model.getHighScores().writeScores();
        frame.setVisible(false);
        frame.dispose();
        System.exit(0);
    }
	
	public JFrame getFrame() {
		return frame;
	}

	public MinesweeperPanel getMinesweeperPanel() {
		return minesweeperPanel;
	}
	
	public JButton[][] getButtonTable() {
		return minesweeperPanel.getButtonTable();
	}
	
	public void setGameStatus(String text) {
		minesweeperPanel.getGameStatus().setText(text);
	}
	
	public void removeGridButtons(MinesweeperGameType type) {
		minesweeperPanel.removeGridButtons(type);
	}
	
	public void addGridButtons(MinesweeperGameType type) {
		minesweeperPanel.addGridButtons(type);
	}
	
	public JButton getEasyButton() {
		return minesweeperPanel.getEasyButton();
	}

	public JButton getMediumButton() {
		return minesweeperPanel.getMediumButton();
	}

	public JButton getHardButton() {
		return minesweeperPanel.getHardButton();
	}
	
	public void resetTimer() {
		timer.resetTimer();
	}
	
	public void pauseTimer() {
		timer.pauseTimer();
	}

}
