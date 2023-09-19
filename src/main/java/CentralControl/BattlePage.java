package CentralControl;

import ChessGames.template.*;
import ChessGames.template.Model.GameResult;
import Util.GetChess;

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
    String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};//游戏模式
    String[] AiMode = {"MinMax", "CNN"};
    String[] minMaxDepth = {"2", "4", "6", "8"};
    String chessName;//游戏名称
    Controller chess;//游戏逻辑控制器
    JButton RestartButton;//开始按钮
    JButton WithdrawButton;//悔棋按钮
    JButton ExitButton;//退出按钮
    JButton ChangeButton;
    JTextArea textArea;//对战信息文本框
    JComboBox<String> gameMode;//游戏模式下拉框
    JComboBox<String> AIMode;//AI模式下拉框
    JComboBox<String> MinMaxDepth;//MinMax思考深度下拉框
    MyButtonLister mb;//监听器
    Dimension dim = new Dimension(100, 200);
    Thread gameThread;//游戏进程


    public BattlePage(String chessName, Controller chess) {
        frame = new JFrame();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        changeList = new JFrame("修改列表");//动态修改参数模块
        changeList.setLayout(new GridLayout(0, 1));
        changeList.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.chessName = chessName;
        this.chess = chess;


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setPreferredSize(new Dimension(560, 760));

        JPanel panel2 = new JPanel();//模式选模块
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));

        panel2.setPreferredSize(new Dimension(200, 560));


        JPanel panel3 = new JPanel();//操作按钮模块（开始、悔棋、退出）
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(760, 200));

        //获取游戏模式
        gameMode = new JComboBox<>(GameMode);
        gameMode.setRenderer(new CenteredComboBoxRenderer());
        gameMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String GAMEMODE = evt.getItem().toString();
                System.out.println("切换到"+GAMEMODE+"模式");
                chess.GameModeSelect(GAMEMODE);
                if (gameThread != null){
                    gameThread.stop();//杀掉先前进程，点击start按钮重新建立新进程
                }
            }
        });
        panel2.add(gameMode);

        //获取指定棋类的AI
        AiMode = GetChess.getAIList(chessName);
        AIMode = new JComboBox<>(AiMode);
        AIMode.setRenderer(new CenteredComboBoxRenderer());
        AIMode.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String AIMODE = evt.getItem().toString();
                System.out.println("AI切换到："+AIMODE);
                chess.config.firstAI = AIMODE;
                chess.config.secondAI = AIMODE;
                if (gameThread != null){
                    gameThread.stop();//杀掉先前进程，点击start按钮重新建立新进程
                }
            }
        });
        panel2.add(AIMode);

        //获取 MinMax思考深度
        MinMaxDepth = new JComboBox<>(minMaxDepth);
        MinMaxDepth.setRenderer(new CenteredComboBoxRenderer());
        MinMaxDepth.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                String depth = evt.getItem().toString();
                System.out.println("MinMax思考深度切换到："+depth);
                chess.config.minMinDepth = depth;
                if (gameThread != null){
                    gameThread.stop();//杀掉先前进程，点击start按钮重新建立新进程
                }
            }
        });
        panel2.add(MinMaxDepth);

        textArea = new JTextArea("Battle:\n", 17, 30);//对战信息文本框
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
        System.out.println("加载棋盘成功！");
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
        new BattlePage(chessName, chess);

    }

    private class MyButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == RestartButton) {
                gameThread = new Thread(() -> {
                    try {
                        chess.StartGame();
                    } catch (ClassNotFoundException | InvocationTargetException | NoSuchMethodException | InstantiationException | IllegalAccessException ex) {
                        ex.printStackTrace();
                    }
                });
                gameThread.start();
                new Thread(() -> {
                    try {
                        gameThread.join();
                    } catch (InterruptedException ex) {
                        throw new RuntimeException(ex);
                    }
                    textArea.append(chess.GetResult() + "\n");

                }).start();
            }
            if (obj == WithdrawButton) {
                chess.chessRules.GoBack();
                chess.chessBoard.repaint();
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
