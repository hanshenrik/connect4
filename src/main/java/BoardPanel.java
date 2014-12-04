package main.java;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private static final int ROWS = FPModel.ROWS;
    private static final int COLS = FPModel.COLS;
    private static final int W = 64;
    private static final int H = W;
    private static final Dimension PREF_SIZE = new Dimension(W, H);
    protected static final Color SELECTION_COLOR = Color.pink;
    private JPanel selectedPanel = null;
    private Color originalColor = null;

    private FPModel model;
    private FPController controller;

    public BoardPanel(FPModel model, final FPController controller) {
        this.model = model;
        this.controller = controller;
        initializeBoard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel panel = (JPanel) getComponentAt(e.getPoint());
                if (panel == null || panel == BoardPanel.this)
                    return;
                int col = panel.getX() / W;
                for (int i = 0; i < ROWS; i++) {
//                    getComponent(ROWS*i +i + col).setBackground(Color.pink);
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JPanel panel = (JPanel) getComponentAt(e.getPoint());
                if (panel == null || panel == BoardPanel.this)
                    return;
                int col = panel.getX() / W;
                if (selectedPanel != null) {
//                    selectedPanel.setBackground(originalColor);
                    selectedPanel.removeAll();
                }
                selectedPanel = panel;
                selectedPanel.setBackground(SELECTION_COLOR);
                selectedPanel.add(new JLabel(selectedPanel.getName()));
                controller.playDisc(col);
                revalidate();
                repaint();
            }
        });
    }

    @Override
    public void paintComponent(Graphics g) {
        Disc[][] board = model.getBoard();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                if (board[i][j] == Disc.PLAYER1)
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(Color.BLACK);
                else if (board[i][j] == Disc.PLAYER2)
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(Color.WHITE);
                else
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(Color.LIGHT_GRAY);
            }
        }
    }

    private void initializeBoard() {
        setLayout(new GridLayout(ROWS, COLS, 1, 1));
        setBackground(Color.black);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                JPanel panel = new JPanel();
                panel.setPreferredSize(PREF_SIZE);
                add(panel);
            }
        }
    }
}