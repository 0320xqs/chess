package CentralControl;

import Util.GetChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Home {
    JFrame homepage;
    private JButton Battle;
    private JButton Play;
    private JButton Example;

    public Home() {
        Listener listener = new Listener();
        homepage = new JFrame();
        homepage.setSize(new Dimension(800, 800));
        homepage.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        homepage.setLayout(new BoxLayout(homepage.getContentPane(), BoxLayout.Y_AXIS));
        homepage.setLocationRelativeTo(null);
        ImageIcon bg = new ImageIcon("pic\\home.png");
        JLabel label = new JLabel(bg);
        label.setBounds(0, 0, 800, 800);
        homepage.getLayeredPane().add(label, new Integer(Integer.MIN_VALUE));
        JPanel jp = (JPanel) homepage.getContentPane();
        jp.setOpaque(false);


        JPanel title = new JPanel();
        title.setLayout(null);
        title.setBackground(Color.WHITE);
        title.setOpaque(false);
        JLabel label1 = new JLabel("开放式棋类以及AI对弈框架");
        label1.setFont(new Font("Serif", Font.BOLD, 50));
        label1.setBounds(100, 0, 700, 200);
        label1.setForeground(new Color(155, 152, 152, 255));
        title.add(label1);


        JPanel option = new JPanel();
        option.setLayout(null);
        option.setBackground(Color.BLUE);
        option.setOpaque(false);

        Battle = new JButton("开始对战");
        Battle.setForeground(new Color(155, 152, 152, 255));
        Battle.setBounds(650, 40, 100, 50);
        Battle.setBackground(Color.cyan);
        Battle.addActionListener(listener);
        Battle.setOpaque(false);
        Battle.setBorder(null);
        Battle.setFocusPainted(false);
        Battle.setFont(new Font("Serif", Font.BOLD, 20));


        Play = new JButton("对局回放");
        Play.setForeground(new Color(155, 152, 152, 255));
        Play.setBounds(650, 100, 100, 50);
        Play.setBackground(new Color(59, 89, 182));
        Play.addActionListener(listener);
        Play.setOpaque(false);
        Play.setBorder(null);
        Play.setFocusPainted(false);
        Play.setFont(new Font("Serif", Font.BOLD, 20));


        Example = new JButton("用例生成");
        Example.setForeground(new Color(155, 152, 152, 255));
        Example.setBounds(650, 160, 100, 50);
        Example.setBackground(new Color(59, 89, 182));
        Example.addActionListener(listener);
        Example.setOpaque(false);
        Example.setBorder(null);
        Example.setFocusPainted(false);
        Example.setFont(new Font("Serif", Font.BOLD, 20));


        option.add(Battle);
        option.add(Play);
        option.add(Example);


        homepage.getContentPane().add(title);
        homepage.getContentPane().add(option);
        homepage.setVisible(true);
    }

    private class Listener implements ActionListener {

        @Override
        public void actionPerformed(ActionEvent e) {
            homepage.dispose();
            String i = (String) JOptionPane.showInputDialog(null, "棋类选择",
                    null, JOptionPane.QUESTION_MESSAGE, null, GetChess.ChessList, "");
            if (i == null) {
                new Home();
                return;
            }

            if (e.getSource() == Battle) {
                new BattlePage(i,GetChess.getChess(i));

            }
            if (e.getSource() == Play) {
                new PlayPage();
            }
            if (e.getSource() == Example) {
                new ExamplePage(i);
            }


        }
    }

}

