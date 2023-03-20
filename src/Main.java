import javax.swing.*;
import java.awt.*;

public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("BorderLayout Example");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel = new JPanel(new BorderLayout());

        JButton northButton = new JButton("North");
        northButton.setBackground(Color.BLUE);
        panel.add(northButton, BorderLayout.NORTH);

        JButton southButton = new JButton("South");
        southButton.setBackground(Color.RED);
        panel.add(southButton, BorderLayout.SOUTH);

        JButton eastButton = new JButton("East");
        eastButton.setBackground(Color.YELLOW);
        panel.add(eastButton, BorderLayout.EAST);

        JButton westButton = new JButton("West");
        westButton.setBackground(Color.GREEN);
        panel.add(westButton, BorderLayout.WEST);

        JButton centerButton = new JButton("Center");
        centerButton.setBackground(Color.ORANGE);
        panel.add(centerButton, BorderLayout.CENTER);

        frame.getContentPane().add(panel);
        frame.pack();
        frame.setVisible(true);
    }
}