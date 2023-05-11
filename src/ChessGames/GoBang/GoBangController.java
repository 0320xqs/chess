package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.concurrent.atomic.AtomicInteger;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangController extends Controller {

    @Override
    public int[] call() {
        StartGame();
        return GameRecord();
    }

    GoBangConfig config = new GoBangConfig();
    GoBangListener listener;
    String GAMEMODE;
    String AIMODE;
    JButton ChangeButton;
    int AIDepth;


    public GoBangController() {
        this.chessBoard = new GoBangChessBoard();
        this.chessPieces = new GoBangChessPieces();
        this.chessRules = new GoBangRules();
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        player1 = new GoBangMan();
        player2 = new GoBangMan();
        board = chessRules.GetBegin();
        currentPlayer = true;
        GameOver = 0;
    }

    public String GetResult() {
        switch (GameOver) {
            case 0:
                return "对局未结束";
            case 1:
                return "游戏结束，平局";
            case 2:
                return "游戏结束，先手赢";
            case 3:
                return "游戏结束，后手赢";
        }
        return null;
    }


    @Override
    public void GameModeSelect(String GameMode) {
        this.GAMEMODE = GameMode;
        switch (GAMEMODE) {
            case "人 VS 人":
                player1 = new GoBangMan();
                player2 = new GoBangMan();
                break;
            case "人 VS AI":
                player1 = new GoBangMan();
                player2 = new GoBangAI(config, 2, AIDepth);
                break;
            case "AI VS 人":
                player1 = new GoBangAI(config, 1, AIDepth);
                player2 = new GoBangMan();
                break;
            case "AI VS AI":
                player1 = new GoBangAI(config, 1, AIDepth);
                player2 = new GoBangAI(config, 2, AIDepth);
                break;
        }
    }


    @Override
    public void AIModeSelect(String AIMode) {
        this.AIMODE = AIMode;
        if (GAMEMODE.equals("人 VS 人")) {
            return;
        }
        switch (AIMODE) {
            case "小白":

                AIDepth = 4;

                break;
            case "新手":

                AIDepth = 6;

                break;
            case "普通":

                AIDepth = 8;
                break;
        }

    }


    @Override
    public void StartGame() {
        int X, Y;
        Point point;
        chessBoard.repaint();
        chessArray.clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = true;
        GameOver = 0;

        switch (GAMEMODE) {
            case "人 VS 人":
                while (GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() + GRID_SPAN / 4) / GRID_SPAN);
                    Y = (int) ((point.getY() + GRID_SPAN / 4) / GRID_SPAN);

                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(player1, player2, new ChessPieces(X, Y));
                        chessBoard.repaint();
                    }
                }
                break;
            case "人 VS AI":
                while (GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX()  + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY()  + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(player1, player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(player1, player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS 人":
                chessRules.Process(player1, player2, null);//AI下第一步棋
                chessBoard.repaint();
                while (GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY()  + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(player1, player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(player1, player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS AI":
                chessRules.Process(player1, player2, null);//AI下第一步棋
                while (GameOver == 0) {
                    chessRules.Process(player1, player2, null); // AI2下棋
                    chessBoard.repaint();
                    if (GameOver == 0) {
                        chessRules.Process(player1, player2, null); // AI1下棋
                        chessBoard.repaint();
                    }
                }

                break;
        }


    }

    public void play(int xy, int Role) {
        int x = xy / ROWS;
        int y = xy % ROWS;
        GoBangChessPieces tempChess = new GoBangChessPieces(x, y);
        tempChess.setChessImage(Role / 2 == 0 ? BLACKCHESS : WHITECHESS);
        chessArray.add(tempChess);
        board[x][y] = Role;
        chessBoard.repaint();
    }

    @Override
    public int[] GameRecord() {
        int[] temp = new int[chessArray.size() + 1];
        for (int i = 0; i < chessArray.size(); i++) {
            temp[i] = chessArray.get(i).getX_coordinate() * ROWS + chessArray.get(i).getY_coordinate();
        }
        temp[chessArray.size()] = currentPlayer ? 1 : 2;
        return temp;
    }

    public JFrame ChangeList() {

        JFrame frame = new JFrame("修改列表");
        frame.setLocationRelativeTo(chessBoard);
        frame.setLocation(chessBoard.getX() + chessBoard.getWidth(), (int) (chessBoard.getY() * 1.5));
        frame.setLayout(new GridLayout(0, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        ChangeButton = new JButton("应用");
        String[] list = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        JComboBox<String> row = new JComboBox<>(list);
        row.setSelectedIndex(14);
        row.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                ROWS = Integer.parseInt(evt.getItem().toString());

            }
        });

        JComboBox<String> col = new JComboBox<>(list);
        col.setSelectedIndex(14);
        col.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                COLS = Integer.parseInt(evt.getItem().toString());

            }
        });

        ChangeButton.addActionListener(e -> {
            chessBoard.repaint();

        });

        frame.add(new Label("ROWS:"));
        frame.add(row);
        frame.add(new Label("COLS:"));
        frame.add(col);
        frame.add(ChangeButton);
        frame.pack();
        return frame;

    }

    @Override
    public JPanel GetBoard() {
        JPanel panel = new BackGround();
        panel.setLayout(null);
        chessBoard.setBounds(MARGIN, MARGIN, chessBoard.getPreferredSize().width, chessBoard.getPreferredSize().height);
        panel.add(chessBoard);
        return panel;
    }

    private class BackGround extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            //添加背景图片
            g.drawImage(new ImageIcon("pic\\ChessBoard.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private class GoBangListener extends Component implements MouseListener, MouseMotionListener {

        private Object lock = new Object();
        private Point clickPoint;

        @Override
        public void mouseClicked(MouseEvent e) {
            synchronized (lock) {
                clickPoint = e.getPoint();
                lock.notify();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            int x1 = (e.getX() + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的x坐标进行转换
            int y1 = (e.getY() + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的y坐标进行转换
            if ( GameOver != 0 || chessRules.findChess(x1, y1)) {
                chessBoard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
            } else {
                chessBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
            }

        }

        public Point waitForClick() {
            // 等待事件发生
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException c) {
                    c.printStackTrace();
                }
            }
            return clickPoint;
        }
    }


}
