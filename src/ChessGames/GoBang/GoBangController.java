package ChessGames.GoBang;

import ChessGames.template.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Objects;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangController extends Controller {
    GoBangConfig gameStatus = new GoBangConfig();


    public GoBangController() {
        this.chessBoard = new GoBangChessBoard();
        this.chessPieces = new GoBangChessPieces();
        this.chessRules = new GoBangRules();
        GoBangListener listener = new GoBangListener();
        chessBoard.addMouseListener((MouseListener) listener);
        chessBoard.addMouseMotionListener((MouseMotionListener) listener);
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        player1 = new GoBangMan();
        player2 = new GoBangMan();
        Arrays.fill(chessArray, null);
        board = chessRules.GetBegin();
        currentPlayer = true;
        GameOpen = false;
        GameOver = false;
        chessCount = 0;
    }


    @Override
    public void GameModeSelect() {
        switch (GAMEMODE) {
            case "人 VS 人":
                player1 = new GoBangMan();
                player2 = new GoBangMan();
                break;
            case "人 VS AI":
                player1 = new GoBangMan();
                player2 = new GoBangAI(gameStatus, 2, AIDepth);
                break;
            case "AI VS 人":
                player1 = new GoBangAI(gameStatus, 1, AIDepth);
                player2 = new GoBangMan();
                break;
            case "AI VS AI":
                player1 = new GoBangAI(gameStatus, 1, AIDepth);
                player2 = new GoBangAI(gameStatus, 2, AIDepth);
                break;
        }
    }


    @Override
    public void AIModeSelect() {
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
        //设置为初始状态
        Arrays.fill(chessArray, null);
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = true;
        GameOpen = true;
        GameOver = false;
        chessCount = 0;

        switch (GAMEMODE) {
            case "AI VS 人":
                chessRules.Process(player1, player2, null);//AI下第一步棋
                GameOpen = true;
                chessBoard.repaint();
                break;
            case "AI VS AI":
                chessRules.Process(player1, player2, null);//AI下第一步棋
                GameOpen = true;
                chessBoard.repaint();
                String finalChessType1 = currentPlayer ? "黑棋" : "白棋";
                ;
                new Thread(() -> {
                    while (!GameOver) {
                        chessRules.Process(player1, player2, null); // AI2下棋
                        SwingUtilities.invokeLater(() -> chessBoard.repaint());
                        if (!GameOver) {
                            chessRules.Process(player1, player2, null); // AI1下棋
                            SwingUtilities.invokeLater(() -> chessBoard.repaint());
                        }
                        if (GameOver) {
                            String msg = String.format("恭喜 %s 赢了", finalChessType1);
                            JOptionPane.showMessageDialog(chessBoard, msg);
                        }
                    }
                }).start();
                break;
        }
        chessBoard.repaint();

    }

    private class GoBangListener extends Component implements MouseListener, MouseMotionListener {
        //鼠标点击事件
        @Override
        public void mousePressed(MouseEvent e) {
            if (!(!GameOver & GameOpen))
                return;
            String chessType = currentPlayer ? "黑棋" : "白棋";
            int position_X = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子x坐标
            int position_Y = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子y坐标
            GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
            switch (GAMEMODE) {
                case "人 VS 人":
                    if (!chessRules.findChess(position_X, position_Y)) {
                        chessRules.Process(player1, player2, chess);
                        chessBoard.repaint();
                    }
                    break;
                case "人 VS AI":
                case "AI VS 人":
                    if (!chessRules.findChess(position_X, position_Y)) {
                        chessRules.Process(player1, player2, chess); // 人下棋
                        chessBoard.repaint();
                        chessType = currentPlayer ? "黑棋" : "白棋";
                        // 在单独的线程中启动AI玩家的回合
                        String finalChessType = chessType;
                        new Thread(() -> {
                            chessRules.Process(player1, player2, null); // AI下棋
                            chessBoard.repaint();
                            if (GameOver) {
                                String msg = String.format("恭喜 %s 赢了", finalChessType);
                                JOptionPane.showMessageDialog(chessBoard, msg);
                            }
                        }).start();
                    }
                    break;
                case "AI VS AI":
                    break;
            }
            if (GameOver) {
                String msg = String.format("恭喜 %s 赢了", chessType);
                JOptionPane.showMessageDialog(chessBoard, msg);
            }

        }


        @Override
        public void mouseClicked(MouseEvent e) {

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
            if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || GameOver || chessRules.findChess(x1, y1)) {
                chessBoard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
            } else {
                chessBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
            }

        }
    }


}
