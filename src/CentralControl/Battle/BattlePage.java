package CentralControl.Battle;

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
import static ChessGames.GoBang.GoBangConfig.AIMODE;

public class BattlePage {
    public String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};
    public String[] AI_Rate = {"小白", "新手", "普通"};
    private  Controller chess;
    private  JButton RestartButton;//声明重新开始按钮
    private  JButton WithdrawButton;//声明悔棋按钮
    private  JButton ExitButton;//声明退出按钮
    JComboBox<String> gameMode;
    JComboBox<String> AIMode;
    Dimension dim=new Dimension(70,40);


    public BattlePage(String Chess) {
        // 创建一个顶层Frame，使用BorderLayout布局
        JFrame frame = new JFrame();
        frame.setLocation(600, 100);
        frame.setLayout(new BorderLayout());

        //棋盘面板
        chess = GetChess.getChess(Chess);
        assert chess != null;
        chess.chessBoard.setPreferredSize(new Dimension(560, 560));

        //右面板
        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2,BoxLayout.Y_AXIS));
        panel2.setPreferredSize(new Dimension(200, 560));

        //下面板
        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3,BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(760, 200));


        gameMode = new JComboBox<>(GameMode);
        gameMode.setRenderer(new CenteredComboBoxRenderer());
        gameMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                GAMEMODE = evt.getItem().toString();//选中的值
                chess.GameModeSelect();
                chess.AIModeSelect();
            }
        });
        panel2.add(gameMode);

        AIMode = new JComboBox<>(AI_Rate);
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                AIMODE = evt.getItem().toString();//选中的值
                chess.AIModeSelect();
            }
        });
        panel2.add(AIMode);

        MyButtonLister mb = new MyButtonLister();//按钮事件处理对象

        RestartButton = new JButton("Start");//设置开始按钮
        RestartButton.setPreferredSize(dim);
        RestartButton.setForeground(Color.WHITE);
        RestartButton.setBackground(new Color(59, 89, 182));
        RestartButton.addActionListener(mb);

        WithdrawButton = new JButton("Withdraw");//设置悔棋按钮
        WithdrawButton.setPreferredSize(dim);
        WithdrawButton.setForeground(Color.WHITE);
        WithdrawButton.setBackground(new Color(59, 89, 182));
        WithdrawButton.addActionListener(mb);

        ExitButton = new JButton("Exit");//设置退出游戏按钮
        ExitButton.setPreferredSize((dim));
        ExitButton.setForeground(Color.WHITE);
        ExitButton.setBackground(new Color(59, 89, 182));
        ExitButton.addActionListener(mb);


        panel3.add(Box.createHorizontalGlue()); // 在按钮之前添加占位组件
        panel3.add(RestartButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(WithdrawButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(ExitButton);
        panel3.add(Box.createHorizontalGlue()); // 在按钮之后添加占位组件






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
            if (obj == RestartButton) {
                System.out.println("重新开始");
                chess.RestartGame();
            } else if (obj == WithdrawButton) {
                System.out.println("悔棋！");
                chess.chessRules.GoBack();
            } else if (obj == ExitButton) {
                System.exit(0);
            }
        }
    }

    class CenteredComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }
}
