package CentralControl.Battle;

import CentralControl.Home;
import ChessGames.GoBang.GoBangController;
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
    String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};
    String[] AI_Rate = {"小白", "新手", "普通"};
    Controller chess;
    JButton RestartButton;
    JButton WithdrawButton;
    public JButton ExitButton;
    JTextArea textArea;
    JComboBox<String> gameMode;
    JComboBox<String> AIMode;
    MyButtonLister mb;
    Dimension dim = new Dimension(100, 200);


    public JFrame GetBattlePage(Controller Chess) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        chess = Chess;

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setPreferredSize(new Dimension(560, 760));

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

        textArea = new JTextArea("Battle:\n", 17, 30);

        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);
        panel2.add(scrollPane);

        mb = new MyButtonLister();

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


        panel1.add(chess.GetBoard());
        panel1.add(panel3);


        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        return frame;
    }

    private class MyButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == RestartButton) {
                Thread thread = new Thread(() -> {
                    chess.StartGame();
                });
                thread.start();
                new Thread(() -> {
                    try {
                        thread.join();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea.append(chess.GetResult() + "\n");

                }).start();
            } else if (obj == WithdrawButton) {
                chess.chessRules.GoBack();
            } else if (obj == ExitButton) {
                frame.dispose();
                chess.init();
                new Home();
            }
        }
    }

    public class CenteredComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }


}
