package main.java;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * Created by hanshenrik on 04/11/14.
 */
public class FPView implements Observer, ActionListener {
    private static final Dimension STATS_PANEL_SIZE = new Dimension(400, 400);
    private static final Dimension BUTTON_PANEL_SIZE = new Dimension(64, 64);
    private static final Dimension BOARD_PANEL_SIZE = new Dimension(448, 384);

    private FPModel model;
    private FPController controller;
    private JFrame gameFrame;
    private JPanel statsPanel;
    private JPanel buttonPanel;
    private BoardPanel boardPanel;

    // stats fields and labels
    private JTextField scoreField = new JTextField();
    private JTextField nextDiscField = new JTextField();
    private JTextField startingDiscField = new JTextField();
    private JTextField winnerField = new JTextField();
    private JTextField messageField = new JTextField();
    private JLabel scoreLabel = new JLabel("Score");
    private JLabel nextDiscLabel = new JLabel("Who's next?");
    private JLabel startingDiscLabel = new JLabel("Who started?");
    private JLabel winnerLabel = new JLabel("Who won last time?");
    private JLabel messageLabel = new JLabel("Info");

    // buttons
    private JButton newGame = new JButton("New game");
    private JButton resetScore = new JButton("Reset score");
    private JButton endGame = new JButton("End game");
    private JButton aiMode = new JButton("Toggle AI Mode");

    private boolean aiToggle = true;

    public FPView(FPModel model, FPController controller)  {
        this.model = model;
        model.addObserver(this);
        this.controller = controller;
        createControls();
        controller.setView(this);
        update(model, null);
    }

    public void createControls() {
        gameFrame = new JFrame("Connect 4");
        gameFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        Container contentPane = gameFrame.getContentPane();
        createStatsPanel();
        createBoardPanel();
        createButtonPanel();

        contentPane.add(statsPanel, BorderLayout.WEST);
        contentPane.add(buttonPanel, BorderLayout.SOUTH);
        contentPane.add(boardPanel, BorderLayout.EAST);

        gameFrame.pack();
        gameFrame.setLocationRelativeTo(null);
        gameFrame.setResizable(false);
        gameFrame.setVisible(true);
    }

    private void createStatsPanel() {
        statsPanel = new JPanel();
        statsPanel.setLayout(new GridLayout(8,2));

        scoreField.setEditable(false);
        nextDiscField.setEditable(false);
        startingDiscField.setEditable(false);
        winnerField.setEditable(false);
        messageField.setEditable(false);

        statsPanel.add(scoreLabel);
        statsPanel.add(scoreField);
        statsPanel.add(nextDiscLabel);
        statsPanel.add(nextDiscField);
        statsPanel.add(startingDiscLabel);
        statsPanel.add(startingDiscField);
        statsPanel.add(winnerLabel);
        statsPanel.add(winnerField);
        statsPanel.add(messageLabel);
        statsPanel.add(messageField);

        statsPanel.setPreferredSize(STATS_PANEL_SIZE);
    }

    private void createButtonPanel() {
        buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(1,4));

        newGame.addActionListener(this);
        endGame.addActionListener(this);
        resetScore.addActionListener(this);
        aiMode.addActionListener(this);

        buttonPanel.add(newGame);
        buttonPanel.add(endGame);
        buttonPanel.add(resetScore);
        buttonPanel.add(aiMode);

        buttonPanel.setPreferredSize(BUTTON_PANEL_SIZE);
    }

    public void createBoardPanel() {
        boardPanel = new BoardPanel(model, controller);
        boardPanel.setPreferredSize(BOARD_PANEL_SIZE);
    }

    public void update(java.util.Observable o, Object arg) {
        scoreField.setText(model.getScore());
        nextDiscField.setText(model.getNextDisc() + "");
        startingDiscField.setText(model.getStartingDisc() + "");
        winnerField.setText(model.getPreviousWinner() + "");
        gameFrame.repaint();
    }

    public void setMessageField(String message) {
        messageField.setText(message);
    }

    public void setEnableNewGame(boolean b) {
        newGame.setEnabled(b);
    }

    public void setEnableEndGame(boolean b) {
        endGame.setEnabled(b);
    }

    public void setEnableResetScore(boolean b) {
        resetScore.setEnabled(b);
    }
    public void setEnableAIMode(boolean b) {
        aiMode.setEnabled(b);
    }

    public void setEnableBoardInteraction(boolean b) {

    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newGame) {
            controller.newGame();
        }
        else if (event.getSource() == endGame) {
            controller.endGame();
            // TODO make clear that board interaction is disabled: put grey layer over board
        }
        else if (event.getSource() == resetScore)
            controller.resetScore();
        else if (event.getSource() == aiMode) {
            controller.setAIMode(aiToggle);
            aiToggle = !aiToggle;
        }
    }
}
