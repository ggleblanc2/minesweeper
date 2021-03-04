package com.ggl.minesweeper.view;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.InputMap;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRootPane;
import javax.swing.KeyStroke;

import com.ggl.minesweeper.MinesweeperHighScores;
import com.ggl.minesweeper.model.MinesweeperScore;

public class HighScoresDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private static final Insets insets = new Insets(0, 0, 0, 0);

	private static final String ESCAPE = "ESCAPE";

	public HighScoresDialog(JFrame frame, String title, String gameLevel,
			MinesweeperHighScores scores) {
		super(frame, true);
		setTitle(title);
		createGUIControl(frame, gameLevel, scores);
	}

	private void createGUIControl(JFrame frame, String gameLevel, 
			MinesweeperHighScores scores) {
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());

		int gridy = 0;

		Font largeFont = MinesweeperFont.getBoldFont(18);
		Font subTitleFont = MinesweeperFont.getBoldFont(16);
		Font mediumFont = MinesweeperFont.getBoldFont(14);
		Font smallFont = MinesweeperFont.getBoldFont(12);

		JLabel subTitle = new JLabel(getSubTitle(gameLevel));
		subTitle.setFont(largeFont);
		subTitle.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, subTitle, 0, gridy++, 5, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		JLabel lastTitle = new JLabel("Recent wins");
		lastTitle.setFont(subTitleFont);
		lastTitle.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, lastTitle, 0, gridy++, 5, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		List<MinesweeperScore> list = scores.getScoresByDate();

		gridy = createGUIScoreTitle(panel, gridy, mediumFont);

		for (int lineCount = 0; (lineCount < list.size()) && (lineCount < 5); lineCount++) {
			MinesweeperScore score = list.get(lineCount);
			gridy = createGUIScoreLine(panel, gridy, smallFont, lineCount + 1,
					score);
		}

		JLabel fastTitle = new JLabel("Fastest wins");
		fastTitle.setFont(subTitleFont);
		fastTitle.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, fastTitle, 0, gridy++, 5, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		list = scores.getScoresByTime();

		gridy = createGUIScoreTitle(panel, gridy, mediumFont);

		for (int lineCount = 0; (lineCount < list.size()) && (lineCount < 5); lineCount++) {
			MinesweeperScore score = list.get(lineCount);
			gridy = createGUIScoreLine(panel, gridy, smallFont, lineCount + 1,
					score);
		}

		JButton okButton = new JButton("OK");
		okButton.setFont(smallFont);
		okButton.setHorizontalAlignment(JButton.CENTER);
		okButton.addActionListener(new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
				dispose();
			}

		});
		addComponent(panel, okButton, 0, gridy++, 5, 1,
				GridBagConstraints.CENTER, GridBagConstraints.NONE);

		add(panel);
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		pack();

		Dimension d = getSize();
		d.height = (d.height * 3) / 2;
		d.width = (d.width * 3) / 2;
		setSize(d);
		
		setLocationRelativeTo(frame);
		setVisible(true);
	}

	private int createGUIScoreTitle(JPanel panel, int gridy, Font mediumFont) {
		JLabel nullTitle = new JLabel(" ");
		nullTitle.setFont(mediumFont);
		nullTitle.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, nullTitle, 0, gridy, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		JLabel dateTitle = new JLabel("Name");
		dateTitle.setFont(mediumFont);
		dateTitle.setHorizontalAlignment(JLabel.LEADING);
		addComponent(panel, dateTitle, 1, gridy, 1, 1,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

		JLabel nameTitle = new JLabel("Date");
		nameTitle.setFont(mediumFont);
		nameTitle.setHorizontalAlignment(JLabel.LEADING);
		addComponent(panel, nameTitle, 2, gridy, 1, 1,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

		JLabel timeTitle = new JLabel("Time");
		timeTitle.setFont(mediumFont);
		timeTitle.setHorizontalAlignment(JLabel.TRAILING);
		addComponent(panel, timeTitle, 3, gridy, 1, 1,
				GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL);

		nullTitle = new JLabel(" ");
		nullTitle.setFont(mediumFont);
		nullTitle.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, nullTitle, 4, gridy++, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		return gridy;
	}

	private int createGUIScoreLine(JPanel panel, int gridy, Font smallFont,
			int count, MinesweeperScore score) {
		JLabel countLabel = new JLabel(Integer.toString(count) + ".");
		countLabel.setFont(smallFont);
		countLabel.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, countLabel, 0, gridy, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		JLabel dateLabel = new JLabel(score.getUserid());
		dateLabel.setFont(smallFont);
		dateLabel.setHorizontalAlignment(JLabel.LEADING);
		addComponent(panel, dateLabel, 1, gridy, 1, 1,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

		JLabel nameLabel = new JLabel(score.getShortTimestamp());
		nameLabel.setFont(smallFont);
		nameLabel.setHorizontalAlignment(JLabel.LEADING);
		addComponent(panel, nameLabel, 2, gridy, 1, 1,
				GridBagConstraints.LINE_START, GridBagConstraints.HORIZONTAL);

		JLabel timeLabel = new JLabel(score.getDisplayTime());
		timeLabel.setFont(smallFont);
		timeLabel.setHorizontalAlignment(JLabel.TRAILING);
		addComponent(panel, timeLabel, 3, gridy++, 1, 1,
				GridBagConstraints.LINE_END, GridBagConstraints.HORIZONTAL);

		JLabel nullLabel = new JLabel(" ");
		nullLabel.setFont(smallFont);
		nullLabel.setHorizontalAlignment(JLabel.CENTER);
		addComponent(panel, nullLabel, 0, gridy, 1, 1,
				GridBagConstraints.CENTER, GridBagConstraints.HORIZONTAL);

		return gridy;
	}

	private String getSubTitle(String gameLevel) {
		StringBuilder sb = new StringBuilder();
		sb.append(gameLevel);
		sb.append(" Minesweeper Game Level");
		return sb.toString();
	}

	private void addComponent(Container container, Component component,
			int gridx, int gridy, int gridwidth, int gridheight, int anchor,
			int fill) {
		GridBagConstraints gbc = new GridBagConstraints(gridx, gridy,
				gridwidth, gridheight, 1.0, 1.0, anchor, fill, insets, 0, 0);
		container.add(component, gbc);
	}

	@Override
	protected JRootPane createRootPane() {
		JRootPane rootPane = new JRootPane();
		KeyStroke stroke = KeyStroke.getKeyStroke(ESCAPE);
		Action actionListener = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent actionEvent) {
				setVisible(false);
				dispose();
			}
		};
		InputMap inputMap = rootPane
				.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
		inputMap.put(stroke, ESCAPE);
		rootPane.getActionMap().put(ESCAPE, actionListener);
		return rootPane;
	}

}

