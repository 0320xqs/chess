package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
    int AIDepth;


    public GoBangController() {
        this.chessBoard = new GoBangChessBoard(config);
        this.chessPieces = new GoBangChessPieces();
        this.chessRules = new GoBangRules(config);
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        config.player1 = new GoBangMan();
        config.player2 = new GoBangMan();
        chessRules.GetBegin();
        config.currentPlayer = true;
        config.GameOver = -1;
    }

    public GoBangController(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        this.chessBoard = new GoBangChessBoard(config);
        this.chessPieces = new GoBangChessPieces();
        this.chessRules = new GoBangRules(config);
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        config.player1 = new GoBangMan();
        config.player2 = new GoBangMan();
        chessRules.GetBegin();
        config.currentPlayer = true;
        config.GameOver = -1;
    }

    public String GetResult() {
        switch (config.GameOver) {
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
                config.player1 = new GoBangMan();
                config.player2 = new GoBangMan();
                break;
            case "人 VS AI":
                config.player1 = new GoBangMan();
                config.player2 = new GoBangAI(config, 2, AIDepth);
                break;
            case "AI VS 人":
                config.player1 = new GoBangAI(config, 1, AIDepth);
                config.player2 = new GoBangMan();
                break;
            case "AI VS AI":
                config.player1 = new GoBangAI(config, 1, AIDepth);
                config.player2 = new GoBangAI(config, 2, AIDepth);
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

    public void init() {
        chessBoard.repaint();
        config.chessArray.clear();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                config.board[i][j] = 0;
            }
        }
        config.currentPlayer = true;
        config.GameOver = -1;
        ROWS = 15;
        COLS = 15;
    }

    @Override
    public void StartGame() {
        int X, Y;
        Point point;
        chessBoard.repaint();
        config.chessArray.clear();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                config.board[i][j] = 0;
            }
        }
        config.currentPlayer = true;
        config.GameOver = 0;

        switch (GAMEMODE) {
            case "人 VS 人":
                while (config.GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.check(X, Y)) {
                        chessRules.Process(config.player1, config.player2, new ChessPieces(X, Y));
                        chessBoard.repaint();
                    }
                }
                break;
            case "人 VS AI":
                while (config.GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.check(X, Y)) {
                        chessRules.Process(config.player1, config.player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(config.player1, config.player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS 人":
                chessRules.Process(config.player1, config.player2, null);//AI下第一步棋
                chessBoard.repaint();
                while (config.GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.check(X, Y)) {
                        chessRules.Process(config.player1, config.player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(config.player1, config.player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS AI":
                chessRules.Process(config.player1, config.player2, null);//AI下第一步棋
                while (config.GameOver == 0) {
                    chessRules.Process(config.player1, config.player2, null); // AI2下棋
                    chessBoard.repaint();
                    if (config.GameOver == 0) {
                        chessRules.Process(config.player1, config.player2, null); // AI1下棋
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
        config.chessArray.add(tempChess);
        config.board[x][y] = Role;
        chessBoard.repaint();
    }

    @Override
    public int[] GameRecord() {
        int[] temp = new int[config.chessArray.size() + 1];
        for (int i = 0; i < config.chessArray.size(); i++) {
            temp[i] = config.chessArray.get(i).getX_coordinate() * ROWS + config.chessArray.get(i).getY_coordinate();
        }
        temp[config.chessArray.size()] = config.currentPlayer ? 1 : 2;
        return temp;
    }

    @Override
    public JPanel GetBoard() {
        JPanel panel = new BackGround();
        panel.setLayout(new FlowLayout());
        chessBoard.setPreferredSize(chessBoard.getPreferredSize());
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
            if (config.GameOver != 0 || chessRules.check(x1, y1)) {
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
