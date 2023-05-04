package CentralControl.Battle;

import CentralControl.Home;
import ChessGames.template.*;
import Util.GetChess;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

import static ChessGames.GoBang.GoBangConfig.*;

public class BattlePage {
    JFrame frame;
    public String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};
    public String[] AI_Rate = {"小白", "新手", "普通"};
    private Controller chess;
    private JButton RestartButton;
    private JButton WithdrawButton;
    private JButton ExitButton;
    JButton ChangeButton;
    JTextField rows;
    JTextField cols;
    JComboBox<String> gameMode;
    JComboBox<String> AIMode;
    Dimension dim = new Dimension(100, 200);


    public BattlePage(String Chess) {

        frame = new JFrame();
        frame.setLayout(new BorderLayout());


        chess = GetChess.getChess(Chess);
        assert chess != null;
        chess.chessBoard.setPreferredSize(new Dimension(560, 560));


        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setPreferredSize(new Dimension(200, 560));


        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(760, 200));


        gameMode = new JComboBox<>(GameMode);
        gameMode.setRenderer(new CenteredComboBoxRenderer());
        gameMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String GAMEMODE = evt.getItem().toString();
                chess.GameModeSelect(GAMEMODE);
            }
        });
        panel2.add(gameMode);

        AIMode = new JComboBox<>(AI_Rate);
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String AIMODE = evt.getItem().toString();
                chess.AIModeSelect(AIMODE);
            }
        });
        panel2.add(AIMode);

        MyButtonLister mb = new MyButtonLister();

        RestartButton = new JButton("Start");
        RestartButton.setPreferredSize(dim);
        RestartButton.setForeground(Color.WHITE);
        RestartButton.setBackground(new Color(59, 89, 182));
        RestartButton.addActionListener(mb);

        WithdrawButton = new JButton("Withdraw");
        WithdrawButton.setPreferredSize(dim);
        WithdrawButton.setForeground(Color.WHITE);
        WithdrawButton.setBackground(new Color(59, 89, 182));
        WithdrawButton.addActionListener(mb);

        ExitButton = new JButton("Exit");
        ExitButton.setPreferredSize((dim));
        ExitButton.setForeground(Color.WHITE);
        ExitButton.setBackground(new Color(59, 89, 182));
        ExitButton.addActionListener(mb);

        panel3.add(Box.createHorizontalGlue());
        panel3.add(RestartButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(WithdrawButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(ExitButton);
        panel3.add(Box.createHorizontalGlue());


        frame.getContentPane().add(panel3, BorderLayout.SOUTH);
        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.getContentPane().add(chess.chessBoard, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JFrame frame1 = new JFrame("修改列表");
        frame1.setVisible(true);
        frame1.setLocationRelativeTo(frame);
        frame1.setLocation(frame.getX() + frame.getWidth(), (int) (frame.getY() * 1.5));
        frame1.setLayout(new GridLayout(0, 2));

        ChangeButton = new JButton("OK");
        rows=new JTextField(String.valueOf(chess.config.ROWS));
        cols=new JTextField(String.valueOf(chess.config.COLS));
        frame1.add(new Label("ROWS:"));
        frame1.add(rows);
        frame1.add(new Label("COLS:"));
        frame1.add(cols);
        frame1.add(ChangeButton);

        frame1.pack();
    }

    private class MyButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == RestartButton) {
                new Thread(() -> {
                    chess.StartGame();
                }).start();
            } else if (obj == WithdrawButton) {
                chess.chessRules.GoBack();
            } else if (obj == ExitButton) {
                frame.setVisible(false);
                new Home();
            } else if (obj == ChangeButton) {

            }
        }
    }

    private class CenteredComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }
}
