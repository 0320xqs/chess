package CentralControl.Example;

import CentralControl.Battle.BattlePage;
import CentralControl.Home;
import ChessGames.template.Controller;
import Util.GetChess;
import Util.Write;


import javax.imageio.IIOException;
import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


public class ExamplePage {
    public List<int[]> Example;
    public String[] AI_Rate = {"10", "50", "100", "1000"};
    private Controller chess;
    private JButton StartButton;
    private JButton SaveButton;
    private JButton ExitButton;
    JTextArea textArea;
    JComboBox<String> AIMode;
    int num;
    Dimension dim = new Dimension(100, 200);

    public ExamplePage(String Chess) {
        Example = Collections.synchronizedList(new ArrayList<>());
        ;
        num = 10;
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());


        chess = GetChess.getChess(Chess);
        assert chess != null;
        chess.GameModeSelect("AI VS AI");
        chess.chessBoard.setPreferredSize(new Dimension(560, 560));

        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setPreferredSize(new Dimension(560, 760));


        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setPreferredSize(new Dimension(200, 560));


        textArea = new JTextArea("Example:\n", 7, 30);
        textArea.setSize(200, 400);
        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);


        AIMode = new JComboBox<>(AI_Rate);
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                num = Integer.parseInt(evt.getItem().toString());
            }
        });
        panel2.add(AIMode);
        panel2.add(scrollPane);


        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(560, 200));


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


        panel1.add(chess.chessBoard);
        panel1.add(panel3);


        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
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
                while (num-- > 0) {
                    new Thread(() -> {
                        Controller temp = GetChess.getChess("GoBang");
                        temp.GameModeSelect("AI VS AI");
                        temp.start();
                        try {
                            temp.join();
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        Example.add(temp.GameRecord());

                    }).start();
                }
                try {
                    Write.write(Example);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }

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

