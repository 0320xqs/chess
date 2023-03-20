import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        // 创建一个顶层Frame，使用BorderLayout布局
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());

        // 创建五个JPanel，并设置不同的背景色
        JPanel panel1 = new JPanel();
        panel1.setBackground(Color.RED);

        JPanel panel2 = new JPanel();
        panel2.setBackground(Color.GREEN);
        panel2.setPreferredSize(new Dimension(1600,200));

        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.BLUE);

        JPanel panel4 = new JPanel();
        panel4.setBackground(Color.YELLOW);
        panel4.setPreferredSize(new Dimension(400,1000));

        JPanel panel5 = new JPanel();
        panel5.setBackground(Color.WHITE);
        panel5.setPreferredSize(new Dimension(1200,1000));


        // 将五个JPanel添加到Frame的不同区域
        frame.add(panel2, BorderLayout.SOUTH);
        frame.add(panel4, BorderLayout.EAST);
        frame.add(panel5, BorderLayout.CENTER);

        // 设置Frame的大小、可见性等其他属性
        frame.setSize(1600, 1200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}