package CentralControl;

import ChessGames.template.*;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.lang.reflect.InvocationTargetException;

public class BattlePage {
    JFrame frame;
    JFrame changeList;
    String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};
    String Chessname;
    Controller chess;
    JButton RestartButton;
    JButton WithdrawButton;
    JButton ExitButton;
    JButton ChangeButton;
    JTextArea textArea;
    JComboBox<String> gameMode;
    JComboBox<String> AIMode;
    MyButtonLister mb;
    Dimension dim = new Dimension(100, 200);


    public BattlePage(String Chessname, Controller chess) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        changeList = new JFrame("修改列表");
        changeList.setLayout(new GridLayout(0, 1));
        changeList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.Chessname = Chessname;
        this.chess = chess;


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
                System.out.println("切换到"+GAMEMODE+"模式");
                chess.GameModeSelect(GAMEMODE);
            }
        });
        panel2.add(gameMode);

        AIMode = new JComboBox<>();
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String AIMODE = evt.getItem().toString();

            }
        });
        panel2.add(AIMode);

        textArea = new JTextArea("Battle:\n", 17, 30);

        textArea.setLineWrap(true);//设置自动换行
//        textArea.setEnabled(false);//设置禁止手动输入
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

        ChangeButton = new JButton("应用");
        ChangeButton.addActionListener(mb);

        panel3.add(Box.createHorizontalGlue());
        panel3.add(RestartButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(WithdrawButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(ExitButton);
        panel3.add(Box.createHorizontalGlue());


        panel1.add(chess.GetBoard());
        System.out.println("成功！");
        panel1.add(panel3);

        changeList.add(chess.ChangeList());
        changeList.add(ChangeButton);
        changeList.pack();


        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        changeList.setLocationRelativeTo(frame);
        changeList.setLocation(frame.getX() + frame.getWidth(), (int) (frame.getY() * 1.5));

        frame.setVisible(true);
        changeList.setVisible(true);
    }

    public void ChessChange() {
        frame.dispose();
        changeList.dispose();
        chess = chess.changeGame();
        new BattlePage(Chessname, chess);

    }

    private class MyButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == RestartButton) {
                Thread thread = new Thread(() -> {
                    try {
                        chess.StartGame();
                    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
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
            }
            if (obj == WithdrawButton) {
                chess.chessRules.GoBack();
            }
            if (obj == ExitButton) {
                frame.dispose();
                chess.init();
                new Home();
            }
            if (obj == ChangeButton) {
                ChessChange();
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
