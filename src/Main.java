import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.plaf.basic.BasicComboBoxRenderer;

import BoardGames.goBang.*;
public class Main implements GoBangConfig{
    private static JFrame frame;
    private static GoBangChessBoard panel1;//棋盘panel
    private static JPanel panel2;
    private static JPanel panel3;
    private static Button RestartButton;//声明重新开始按钮
    private static Button WithdrawButton;//声明悔棋按钮
    private static Button ExitButton;//声明退出按钮
    static JComboBox  gameMode;
    static JComboBox AIMode;


    public static void main(String[] args) {// 创建一个顶层Frame，使用BorderLayout布局
        frame = new JFrame();
        frame.setLocation(600, 100);
        frame.setLayout(new BorderLayout());
        //棋盘面板
        panel1 = new GoBangChessBoard();
        panel1.setBackground(Color.WHITE);
        panel1.setPreferredSize(new Dimension(560, 560));
        //右面板
        panel2 = new JPanel();
        panel2.setBackground(Color.YELLOW);
        panel2.setPreferredSize(new Dimension(200, 760));

        gameMode = new JComboBox(GameMode);
        gameMode.setRenderer(new CenteredComboBoxRenderer());
//        gameMode.setPreferredSize(dim1);

        AIMode = new JComboBox(AI_Rate);
//        AIMode.setPreferredSize(dim1);
        AIMode.setRenderer(new CenteredComboBoxRenderer());

        gameMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        panel1.GAMEMODE = evt.getItem().toString();//选中的值
                        panel1.GameModeSelect();
                        panel1.AIModeSelect();
                    } catch (Exception e) {
                    }
                }
            }
        });
        panel2.add(gameMode);

        AIMode.addItemListener(new ItemListener() {
            public void itemStateChanged(ItemEvent evt) {
                if (evt.getStateChange() == ItemEvent.SELECTED) {
                    try {
                        panel1.AIMODE = evt.getItem().toString();//选中的值
                        panel1.AIModeSelect();
                    } catch (Exception e) {
                    }
                }
            }
        });
        panel2.add(AIMode);

        MyButtonLister mb = new MyButtonLister();//按钮事件处理对象
        RestartButton = new Button("ReStart");//设置开始按钮
        WithdrawButton = new Button("Withdraw");//设置悔棋按钮
        ExitButton = new Button("Exit");//设置退出游戏按钮
        RestartButton.setPreferredSize(dim2);
        WithdrawButton.setPreferredSize(dim2);
        ExitButton.setPreferredSize(dim2);
        // 设置按钮的样式
        RestartButton.setForeground(Color.WHITE);
        RestartButton.setBackground(new Color(59, 89, 182));
        WithdrawButton.setForeground(Color.WHITE);
        WithdrawButton.setBackground(new Color(59, 89, 182));
        ExitButton.setForeground(Color.WHITE);
        ExitButton.setBackground(new Color(59, 89, 182));
//        RestartButton.setFocusPainted(false);
        panel2.add(RestartButton);
        panel2.add(WithdrawButton);
        panel2.add(ExitButton);
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        RestartButton.addActionListener(mb);
        WithdrawButton.addActionListener(mb);
        ExitButton.addActionListener(mb);
        //下面板
        panel3 = new JPanel();
        panel3.setBackground(Color.GREEN);
        panel3.setPreferredSize(new Dimension(760, 200));

        // 将三个JPanel添加到Frame的不同区域
        frame.add(panel3, BorderLayout.SOUTH);
        frame.add(panel2, BorderLayout.EAST);
        frame.add(panel1, BorderLayout.CENTER);

        // 设置Frame的大小、可见性等其他属性
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

}