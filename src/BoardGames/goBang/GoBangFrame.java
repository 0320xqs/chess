package BoardGames.goBang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

import static BoardGames.goBang.GoBangConfig.*;

public class GoBangFrame extends Frame{

    public GoBangChessBoard chessBoard;//棋盘
    public GoBangChessPieces chessPieces;//棋子
    public GoBangRules chessRules;//棋规
    public GoBangListener listener;//监听器
    public GoBangConfig gameStatus;//棋盘状态

    public GoBangFrame() {
        chessBoard = new GoBangChessBoard();
        chessPieces = new GoBangChessPieces();
        chessRules = new GoBangRules();
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(new MouseMotionListener() {//匿名内部类
            @Override
            public void mouseMoved(MouseEvent e) {//根据鼠标的移动所在的坐标来设置鼠标光标形状
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的x坐标进行转换
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的y坐标进行转换
                if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || GameOver || chessRules.findChess(x1, y1)) {
                    chessBoard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
                } else {
                    chessBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {

            }
        });
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        player1 = new GoBangMan(gameStatus, 1, 0);
        player2 = new GoBangMan(gameStatus, 2, 0);

        for (int i = 0; i < chessArray.length; i++)//设置为初始状态
            chessArray[i] = null;
        board=chessRules.GetBegin();
        currentPlayer = true;
        GameOpen=false;
        GameOver = false;
        chessCount = 0;
    }

    //游戏模式选择
    public void GameModeSelect() {
        if (GAMEMODE.equals("人 VS 人")) {
            player1 = new GoBangMan(gameStatus, 1, 0);
            player2 = new GoBangMan(gameStatus, 2, 0);
        } else if (GAMEMODE.equals("人 VS AI")) {
            player1 = new GoBangMan(gameStatus, 1, 0);
        } else if (GAMEMODE.equals("AI VS 人")) {
            player2 = new GoBangMan(gameStatus, 2, 0);
        } else if (GAMEMODE.equals("AI VS AI")) {
        }
    }

    //AI模式选择
    public void AIModeSelect() {
        if (GAMEMODE.equals("人 VS 人")) {
            return;
        }
        if (AIMODE.equals("小白")) {

            AIDepth = 4;

        } else if (AIMODE.equals("新手")) {

            AIDepth = 6;

        } else if (AIMODE.equals("普通")) {

            AIDepth = 8;
        }
        if (GAMEMODE.equals("人 VS AI")) {
            player2 = new GoBangAI(gameStatus, 2, AIDepth);
        } else if (GAMEMODE.equals("AI VS 人")) {
            player1 = new GoBangAI(gameStatus, 1, AIDepth);
            if (!GameOpen) {
                chessRules.Process(player1, player2, null);//AI下第一步棋
                GameOpen=true;
                chessBoard.repaint();
            }
        } else if (GAMEMODE.equals("AI VS AI")) {
            player1 = new GoBangAI(gameStatus, 1, AIDepth);
            player2 = new GoBangAI(gameStatus, 2, AIDepth);
            if (!GameOpen) {
                chessRules.Process(player1, player2, null);//AI下第一步棋
                GameOpen=true;
                chessBoard.repaint();
            }
        }
    }

    public void RestartGame() {//重新开始函数
        //设置为初始状态
        for (int i = 0; i < chessArray.length; i++)//设置为初始状态
            chessArray[i] = null;
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                board[i][j] = 0;
            }
        }
        currentPlayer = true;
        GameOpen=false;
        GameOver = false;
        chessCount = 0;
        if (GAMEMODE.equals("AI VS 人") || GAMEMODE.equals("AI VS AI")) {
            chessRules.Process(player1, player2, null);//AI下第一步棋
        }

        chessBoard.repaint();
    }



    public void ChessBoardPrint() {
        System.out.println("此时棋盘数组：");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
        }
    }

    /**
     * 鼠标监听
     **/
    private class GoBangListener extends Component implements MouseListener {
        //鼠标点击事件
        @Override
        public void mousePressed(MouseEvent e) {
            GameOpen=true;//已开局
            String chessType = currentPlayer ? "黑棋" : "白棋";
            if (GameOver)//游戏结束，不能按
                return;
            int position_X = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子x坐标
            int position_Y = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子y坐标
            GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
            if (GAMEMODE.equals("人 VS 人")) {
                if (!chessRules.findChess(position_X, position_Y)) {
                    chessRules.Process(player1, player2, chess);
                    chessBoard.repaint();
                }
            } else if (GAMEMODE.equals("人 VS AI") || GAMEMODE.equals("AI VS 人")) {
                if (!chessRules.findChess(position_X, position_Y)) {
                    chessRules.Process(player1, player2, chess); // 人下棋
                    chessBoard.repaint();
                    chessType = currentPlayer ? "黑棋" : "白棋";
                    // 在单独的线程中启动AI玩家的回合
                    String finalChessType = chessType;
                    new Thread(() -> {
                        chessRules.Process(player1, player2, null); // AI下棋
                        chessBoard.repaint();
                        if (GameOver == true) {
                            String msg = String.format("恭喜 %s 赢了", finalChessType);
                            JOptionPane.showMessageDialog(chessBoard, msg);
                        }
                    }).start();
                }
            } else if (GAMEMODE.equals("AI VS AI")) {
                String finalChessType1 = chessType;
                new Thread(() -> {
                    while (!GameOver) {
                        chessRules.Process(player1, player2, null); // AI2下棋
                        SwingUtilities.invokeLater(() -> chessBoard.repaint());
                        if (!GameOver) {
                            chessRules.Process(player1, player2, null); // AI1下棋
                            SwingUtilities.invokeLater(() -> chessBoard.repaint());
                        }
                        if (GameOver == true) {
                            String msg = String.format("恭喜 %s 赢了", finalChessType1);
                            JOptionPane.showMessageDialog(chessBoard, msg);
                        }
                    }
                }).start();
            }

            if (GameOver == true) {
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
    }


}
