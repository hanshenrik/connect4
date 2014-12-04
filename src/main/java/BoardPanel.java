package main.java;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;

public class BoardPanel extends JPanel {
    private static final int ROWS = FPModel.ROWS;
    private static final int COLS = FPModel.COLS;
    private static final int W = 64;
    private static final int H = W;
    private static final Dimension PREF_SIZE = new Dimension(W, H);
    private Color black = new Color(30, 29, 28);
    private Color red = new Color(223, 23, 23);
    private Color white = new Color(252, 251, 250);

    private FPModel model;

    public BoardPanel(FPModel model, final FPController controller) {
        this.model = model;
        initializeBoard();

        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                JPanel panel = (JPanel) getComponentAt(e.getPoint());
                if (panel == null || panel == BoardPanel.this)
                    return;
                int col = panel.getX() / W;
                for (int i = 0; i < ROWS; i++) {
                    // TODO slightly paint every panel in column
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                JPanel panel = (JPanel) getComponentAt(e.getPoint());
                if (panel == null || panel == BoardPanel.this)
                    return;
                int col = panel.getX() / W;
                controller.playDisc(col, false);
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
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(black);
                else if (board[i][j] == Disc.PLAYER2)
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(red);
                else
                    this.getComponent( (ROWS*COLS-1) - (i*7) - (COLS-1-j) ).setBackground(white);
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