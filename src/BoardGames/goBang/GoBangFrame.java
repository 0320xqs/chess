package BoardGames.goBang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class GoBangFrame implements GoBangConfig{

    public class EpicycleData {

        public int[][] Begin;//初始棋盘
        public boolean currentPlayer;// true：黑子 false：白子 开始默认黑子先下
        public boolean GameOver;//定义是否游戏结束
        public int chessCount;//棋子数目
    }
    EpicycleData epicycleData=new EpicycleData();



    class GoBangChessBoardListener implements MouseMotionListener, MouseListener {

        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {
            String chessType = epicycleData.currentPlayer ? "黑棋" : "白棋";
            if (epicycleData.GameOver)//游戏结束，不能按
                return;
            int position_X = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子x坐标
            int position_Y = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子y坐标
            if (GAMEMODE.equals("人 VS 人")) {
                GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
                if (!epicycleData.findChess(position_X, position_Y)) {
                    epicycleData.Process(player1, player2, chess, epicycleData);
                    repaint();
                }
            } else if (GAMEMODE.equals("人 VS AI")||GAMEMODE.equals("AI VS 人")) {
                GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
                if (!epicycleData.findChess(position_X, position_Y)) {
                    epicycleData.Process(player1, player2, chess, epicycleData);//人下棋
                    chessType = epicycleData.currentPlayer ? "黑棋" : "白棋";
                    epicycleData.Process(player1, player2, chess, epicycleData);//AI下棋
                    repaint();
                }
            } else if (GAMEMODE.equals("AI VS AI")) {
                System.out.println("gameStatus.currentPlayer的值是："+epicycleData.currentPlayer);
                while (!epicycleData.GameOver) {
                    epicycleData.Process(player1, player2, null, epicycleData);//AI2下棋
                    if(!epicycleData.GameOver)
                    {
                        epicycleData.Process(player1, player2, null, epicycleData);//AI1下棋
                    }

                    repaint();
                }
                System.out.println("棋子数:"+epicycleData.chessCount);
            }


            if (epicycleData.GameOver == true) {
                String msg = String.format("恭喜 %s 赢了", chessType);
                JOptionPane.showMessageDialog(this, msg);
            }

            System.out.println("此时棋盘数组：");
            for (int i = 0; i < ROWS; i++) {
                for (int j = 0; j < COLS; j++) {
                    System.out.print(epicycleData.board[i][j] + " ");
                }
                System.out.println();
            }

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
            if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || gameStatus.GameOver || gameStatus.findChess(x1, y1)) {
                setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
            } else {
                setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
            }

        }
    }


    public void GameModeSelect() {
        if (GAMEMODE.equals("人 VS 人")) {
            player1 = new GoBangMan();
            player2 = new GoBangMan();
        } else if (GAMEMODE.equals("人 VS AI")) {
            player1 = new GoBangMan();
        } else if (GAMEMODE.equals("AI VS 人")) {
            player2 = new GoBangMan();
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
            player2 = new GoBangAI(gameStatus, AIDepth);
        } else if (GAMEMODE.equals("AI VS 人")) {
            player1 = new GoBangAI(gameStatus, AIDepth);
            player1.play(null, gameStatus);
            repaint();
        } else if (GAMEMODE.equals("AI VS AI")) {
            player1 = new GoBangAI(gameStatus, AIDepth);
            player2 = new GoBangAI(gameStatus, AIDepth);
            player1.play(null, gameStatus);
            repaint();
        }
    }
    public void RestartGame() {//重新开始函数

        gameStatus.RestartGame();
        if(GAMEMODE.equals("AI VS 人")||GAMEMODE.equals("AI VS AI"))
        {
            player1.play(null, gameStatus);
        }

        repaint();
    }

    public void GoBack() {//悔棋函数

        gameStatus.GoBack();
        repaint();
    }

}
