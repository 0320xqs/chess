package CentralControl;

import CentralControl.Home;
import ChessGames.GoBang.GoBangController;
import ChessGames.template.Controller;
import Util.GetChess;
import Util.Write;


import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;


public class ExamplePage {
    public List<int[]> Example;
    public String[] AI_Rate = {"10", "50", "100", "1000"};
    public Controller chess;
    public JButton StartButton;
    public JButton SaveButton;
    public JButton ExitButton;
    JTextArea textArea;
    JComboBox<String> AIMode;
    int num;
    Dimension dim = new Dimension(100, 200);
    String Chess;
    JFrame frame;

    public ExamplePage(String Chess) {
        this.Chess = Chess;
        Example = new ArrayList<>();
        num = 10;
        frame = new JFrame();
        frame.setLayout(new BorderLayout());


        chess = GetChess.getChess(this.Chess);
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
        //panel3.add(SaveButton);
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
        frame.setVisible(true);

    }

    private class MyButtonLister implements ActionListener {
        //按钮处理事件类
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == StartButton) {
                new Thread(() -> {
                    TaskScheduler scheduler = new TaskScheduler(10);
                    try {
                        scheduler.start();
                    } catch (ExecutionException | InterruptedException | IOException ex) {
                        ex.printStackTrace();
                    }

                }).start();


            } else if (obj == SaveButton) {
                chess.chessRules.GoBack();
            } else if (obj == ExitButton) {
                frame.dispose();
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

    class TaskScheduler {
        private ExecutorService executor;


        public TaskScheduler(int numThreads) {
            this.executor = Executors.newFixedThreadPool(numThreads);

        }

        public void start() throws ExecutionException, InterruptedException, IOException {
            List<FutureTask> futures = new ArrayList<>();
            for (int i = 0; i < num; i++) {
                Controller temp = new GoBangController();
                temp.GameModeSelect("AI VS AI");
                FutureTask future = new FutureTask<>(temp);
                executor.execute(future);
                futures.add(future);
            }

            executor.shutdown();
            for (int i = 0; i < futures.size(); i++) {
                Example.add((int[]) futures.get(i).get());
                textArea.append("完成一局，目前进度" + (i + 1) + "/" + num + "\n");
            }
            textArea.append("结束");
            Write.write(Example, Chess);
        }
    }
}



