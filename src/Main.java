import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import static BoardGames.goBang.GoBangConfig.*;

import BoardGames.goBang.*;
public class Main{
    private JFrame frame;
    private GoBangChessBoard panel1;//棋盘panel
    private JPanel panel2;
    private JPanel panel3;
    private Button RestartButton;//声明重新开始按钮
    private Button WithdrawButton;//声明悔棋按钮
    private Button ExitButton;//声明退出按钮
    JComboBox gameMode;
    JComboBox AIMode;
    Dimension dim1 = new Dimension(150, 20);//设置下拉框组件的大小
    Dimension dim2 = new Dimension(120, 40);//设置按钮组件的大小
    Dimension dim3 = new Dimension(140, 45);//设置右边按钮组件的大小

    public Main() {
        // 创建一个顶层Frame，使用BorderLayout布局
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
                        GAMEMODE = evt.getItem().toString();//选中的值
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
                        AIMODE = evt.getItem().toString();//选中的值
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

    private class MyButtonLister implements ActionListener {
        //按钮处理事件类
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();//获取事件源
            if (obj == RestartButton) {//事件源是重新开始按钮
                System.out.println("重新开始");
                panel1.RestartGame();
            } else if (obj == WithdrawButton) {//事件源是悔棋按钮
                System.out.println("悔棋！");
                panel1.GoBack();
            } else if (obj == ExitButton) {//事件源是退出按钮
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

    public static void main(String[] args) {
        Main jf = new Main();//声明框架对象
    }

}