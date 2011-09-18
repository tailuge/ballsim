package org.oxtail.game.numberguess.simulation;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.LayoutManager;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

public class NumberGuessGameView extends JFrame {

	private LayoutManager experimentLayout = new FlowLayout(3);
	private final JPanel compsToExperiment = new JPanel();

	private List<JLabel> labels = new ArrayList<JLabel>();
	
	private Dimension iconSize = new Dimension(64,64);
	
	private void addButtons() {
		Font font = new Font("Tahoma", Font.BOLD, 20);
		for (int i = 1; i < 10; ++i) {
			JLabel label = new JLabel(String.valueOf(i));
			label.setMaximumSize(iconSize);
			label.setMinimumSize(iconSize);
			label.setPreferredSize(iconSize);
			label.setFont(font);
			label.setText(""+i);
			compsToExperiment.add(label);
			labels.add(label);
		}
	}
	
	private void addComponentsToPane(final Container pane) {

		compsToExperiment.setLayout(experimentLayout);
		compsToExperiment.setPreferredSize(new Dimension(640, 64));
		compsToExperiment.setMinimumSize(new Dimension(640, 64));
		
		addButtons();
		// Add controls to set up horizontal and vertical gaps
		pane.add(compsToExperiment);
	}
	
	public NumberGuessGameView(String title) {
		super(title);
		addComponentsToPane(getContentPane());
		setResizable(false);
		pack();
		setVisible(true);
	}

	public void notifyGuess(int number, String player) {
		JLabel label = labels.get(number-1);
		label.setIcon(loadIcon(player));
		label.repaint();
	}
	
	private Icon loadIcon(String player) {
		Resource resource = new ClassPathResource("icons/"+player+".jpg",getClass());
		try {
			return new ImageIcon(resource.getURL(),player);
		} catch (IOException e) {
			e.printStackTrace();
			return new ImageIcon(player);
		}
	}
	
	

}
