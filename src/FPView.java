import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Observer;

/**
 * Created by hanshenrik on 04/11/14.
 */
public class FPView implements Observer, ActionListener {
    private static final Dimension PANEL_SIZE = new Dimension(300,300);

    private FPModel model;
    private FPController controller;
    private JFrame frame;
    private JPanel panel;

    private JTextField redField = new JTextField(3);
    private JTextField amberField = new JTextField(3);
    private JTextField greenField = new JTextField(3);
    private JLabel redLabel = new JLabel("Red");
    private JLabel amberLabel = new JLabel("Amber");
    private JLabel greenLabel = new JLabel("Green");

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
        frame = new JFrame("Connect 4");
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
        redField.setText(model.getScore());
        amberField.setText(model.isFullBoard() + "");
        greenField.setText(model.getWinner() + "");
        frame.repaint();
    }

    private void createPanel() {
        panel = new JPanel();
        panel.setLayout(new GridLayout(5,2));

        redField.setEditable(false);
        amberField.setEditable(false);
        greenField.setEditable(false);

        panel.add(redLabel);
        panel.add(redField);
        panel.add(amberLabel);
        panel.add(amberField);
        panel.add(greenLabel);
        panel.add(greenField);

        newGame.addActionListener(this);
        panel.add(newGame);
        resetScore.addActionListener(this);
        panel.add(resetScore);
        endGame.addActionListener(this);
        panel.add(endGame);

        panel.setPreferredSize(PANEL_SIZE);
    }

    public void setEnableNewGame(boolean b) {
        newGame.setEnabled(b);
    }

    public void setEnableResetScore(boolean b) {
        resetScore.setEnabled(b);
    }

    public void setEnableEndGame(boolean b) {
        endGame.setEnabled(b);
    }

    public void actionPerformed(ActionEvent event) {
        if (event.getSource() == newGame)
            controller.newGame();
        else if (event.getSource() == endGame)
            controller.endGame();
        else if (event.getSource() == resetScore)
            controller.resetScore();
    }
}
