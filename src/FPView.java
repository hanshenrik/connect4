import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * Created by hanshenrik on 04/11/14.
 */
public class FPView implements Observer, ActionListener {
    private static final Dimension PANEL_SIZE = new Dimension(300, 300);

    private FPModel model;
    private FPController controller;
    private JFrame frame;
    private JPanel panel;

    private JTextField scoreField = new JTextField();
    private JTextField messageField = new JTextField();
    private JTextField nextDiscField = new JTextField();
    private JTextField startingDiscField = new JTextField();
    private JTextField winnerField = new JTextField();
    private JLabel scoreLabel = new JLabel("Score");
    private JLabel messageLabel = new JLabel("Message");
    private JLabel nextDiscLabel = new JLabel("Next player");
    private JLabel startingDiscLabel = new JLabel("Who started?");
    private JLabel winnerLabel = new JLabel("Winner");

    private JButton newGame = new JButton("New game");
    private JButton resetScore = new JButton("Reset score");
    private JButton endGame = new JButton("End game");

    public FPView(FPModel model, FPController controller)  {
        this.model = model;
        model.addObserver(this);
        this.controller = controller;
        createControls();
        controller.setView(this);
        update(model, null);
    }

    public void createControls()
    {
        frame = new JFrame("Connect 4 - The info");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
        createPanel();
        contentPane.add(panel);

        frame.pack();
        frame.setResizable(false);
        frame.setVisible(true);
    }

    public void update(java.util.Observable o, Object arg) {
        scoreField.setText(model.getScore());
        messageField.setText(model.getMessage());
        nextDiscField.setText(model.getNextDisc() + "");
        startingDiscField.setText(model.getStartingDisc() + "");
        winnerField.setText(model.getWinner() + "");
        frame.repaint();
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(8,2));

        scoreField.setEditable(false);
        messageField.setEditable(false);
        nextDiscField.setEditable(false);
        startingDiscField.setEditable(false);
        winnerField.setEditable(false);

        panel.add(scoreLabel);
        panel.add(scoreField);
        panel.add(messageLabel);
        panel.add(messageField);
        panel.add(nextDiscLabel);
        panel.add(nextDiscField);
        panel.add(startingDiscLabel);
        panel.add(startingDiscField);
        panel.add(winnerLabel);
        panel.add(winnerField);

        newGame.addActionListener(this);
        endGame.addActionListener(this);
        resetScore.addActionListener(this);
        panel.add(newGame);
        panel.add(endGame);
        panel.add(resetScore);

        panel.setPreferredSize(PANEL_SIZE);
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

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newGame) {
            controller.newGame();
            setEnableEndGame(true);
            setEnableResetScore(true);
        }
        else if (event.getSource() == endGame) {
            setEnableNewGame(true);
            setEnableEndGame(false);
            setEnableResetScore(false);
            // TODO make board interaction disabled
        }
        else if (event.getSource() == resetScore)
            controller.resetScore();
    }
}
