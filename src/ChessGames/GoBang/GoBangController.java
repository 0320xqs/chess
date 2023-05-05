package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Controller;

import java.awt.*;
import java.awt.event.*;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangController extends Controller {

    @Override
    public int[] call() {
        StartGame();
        return GameRecord();
    }

    public GoBangConfig gameStatus = new GoBangConfig();
    GoBangListener listener;
    String GAMEMODE;
    String AIMODE;
    int AIDepth;


    public GoBangController() {
        this.chessBoard = new GoBangChessBoard(gameStatus);
        this.chessPieces = new GoBangChessPieces();
        this.chessRules = new GoBangRules(gameStatus);
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        gameStatus.player1 = new GoBangMan();
        gameStatus.player2 = new GoBangMan();
        gameStatus.board = chessRules.GetBegin();
        gameStatus.currentPlayer = true;
        gameStatus.GameOver = 0;
    }

    public String GetResult() {
        switch (gameStatus.GameOver) {
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
                gameStatus.player1 = new GoBangMan();
                gameStatus.player2 = new GoBangMan();
                break;
            case "人 VS AI":
                gameStatus.player1 = new GoBangMan();
                gameStatus.player2 = new GoBangAI(gameStatus, 2, AIDepth);
                break;
            case "AI VS 人":
                gameStatus.player1 = new GoBangAI(gameStatus, 1, AIDepth);
                gameStatus.player2 = new GoBangMan();
                break;
            case "AI VS AI":
                gameStatus.player1 = new GoBangAI(gameStatus, 1, AIDepth);
                gameStatus.player2 = new GoBangAI(gameStatus, 2, AIDepth);
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
        Point point = new Point();
        ChessPieces chessPieces = new ChessPieces();
        chessBoard.repaint();
        gameStatus.chessArray.clear();
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                gameStatus.board[i][j] = 0;
            }
        }
        gameStatus.currentPlayer = true;
        gameStatus.GameOver = 0;

        switch (GAMEMODE) {
            case "人 VS 人":
                while (gameStatus.GameOver == 0) {
                point = listener.waitForClick();
                X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);

                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(gameStatus.player1, gameStatus.player2, new ChessPieces(X, Y));
                        chessBoard.repaint();
                    }
                }
                break;
            case "人 VS AI":
                while (gameStatus.GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(gameStatus.player1, gameStatus.player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(gameStatus.player1, gameStatus.player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS 人":
                chessRules.Process(gameStatus.player1, gameStatus.player2, null);//AI下第一步棋
                chessBoard.repaint();
                while (gameStatus.GameOver == 0) {
                    point = listener.waitForClick();
                    X = (int) ((point.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    Y = (int) ((point.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN);
                    if (!chessRules.findChess(X, Y)) {
                        chessRules.Process(gameStatus.player1, gameStatus.player2, new ChessPieces(X, Y)); // 人下棋
                        chessBoard.repaint();
                        chessRules.Process(gameStatus.player1, gameStatus.player2, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS AI":
                chessRules.Process(gameStatus.player1, gameStatus.player2, null);//AI下第一步棋
                while (gameStatus.GameOver == 0) {
                    chessRules.Process(gameStatus.player1, gameStatus.player2, null); // AI2下棋
                    chessBoard.repaint();
                    if (gameStatus.GameOver == 0) {
                        chessRules.Process(gameStatus.player1, gameStatus.player2, null); // AI1下棋
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
        gameStatus.chessArray.add(tempChess);
        gameStatus.board[x][y] = Role;
        chessBoard.repaint();
    }

    @Override
    public int[] GameRecord() {
        int[] temp = new int[gameStatus.chessArray.size() + 1];
        for (int i = 0; i < gameStatus.chessArray.size(); i++) {
            temp[i] = gameStatus.chessArray.get(i).getX_coordinate() * gameStatus.ROWS + gameStatus.chessArray.get(i).getY_coordinate();
        }
        temp[gameStatus.chessArray.size()] = gameStatus.currentPlayer ? 1 : 2;
        return temp;
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
            int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的x坐标进行转换
            int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的y坐标进行转换
            if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameStatus.GameOver != 0 || chessRules.findChess(x1, y1)) {
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
