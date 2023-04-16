package CentralControl.Example;

import CentralControl.Battle.BattlePage;
import CentralControl.Home;
import ChessGames.template.Controller;
import Util.GetChess;


import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;


public class ExamplePage {

    public String[] AI_Rate = {"10", "50", "100", "1000"};
    private Controller chess;
    private JButton StartButton;
    private JButton SaveButton;
    private JButton ExitButton;
    JComboBox<String> AIMode;
    int num;
    Dimension dim = new Dimension(100, 200);

    public ExamplePage(String Chess) {
        num=10;
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());


        chess = GetChess.getChess(Chess);
        assert chess != null;
        chess.GameModeSelect("AI VS AI");
        chess.chessBoard.setPreferredSize(new Dimension(560, 560));


        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setPreferredSize(new Dimension(200, 560));

        AIMode = new JComboBox<>(AI_Rate);
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                num = Integer.parseInt(evt.getItem().toString());
            }
        });
        panel2.add(AIMode);


        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(760, 200));


        MyButtonLister mb = new MyButtonLister();

        StartButton = new JButton("Start");
        StartButton.setPreferredSize(dim);
        StartButton.setForeground(Color.WHITE);
        StartButton.setBackground(new Color(59, 89, 182));
        StartButton.addActionListener(mb);

        SaveButton = new JButton("Withdraw");
        SaveButton.setPreferredSize(dim);
        SaveButton.setForeground(Color.WHITE);
        SaveButton.setBackground(new Color(59, 89, 182));
        SaveButton.addActionListener(mb);

        ExitButton = new JButton("Exit");
        ExitButton.setPreferredSize((dim));
        ExitButton.setForeground(Color.WHITE);
        ExitButton.setBackground(new Color(59, 89, 182));
        ExitButton.addActionListener(mb);

        panel3.add(Box.createHorizontalGlue());
        panel3.add(StartButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(SaveButton);
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

    }

    private class MyButtonLister implements ActionListener {
        //按钮处理事件类
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == StartButton) {
                while (num-- != 0)
                    chess.StartGame();
            } else if (obj == SaveButton) {
                chess.chessRules.GoBack();
            } else if (obj == ExitButton) {
                new Home();
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

