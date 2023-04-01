package BoardGames.goBang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import static BoardGames.goBang.GoBangConfig.*;

import BoardGames.template.*;

public class GoBangChessBoard extends chessBoard implements MouseListener {

    public GoBangRules gameStatus;
    public GoBangChessBoard() {//棋盘类构造函数
        gameStatus = new GoBangRules();
        GAMEMODE = "人 VS 人";
        AIMODE = "小白";
        AIDepth = 4;
        player1 = new GoBangMan();
        player2 = new GoBangMan();
        addMouseListener(this);//将棋盘类添加到鼠标事件监听器
        addMouseMotionListener(new MouseMotionListener() {//匿名内部类
            @Override
            public void mouseMoved(MouseEvent e) {//根据鼠标的移动所在的坐标来设置鼠标光标形状
                int x1 = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的x坐标进行转换
                int y1 = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//对鼠标光标的y坐标进行转换
                if (x1 < 0 || x1 > ROWS || y1 < 0 || y1 > COLS || GameOver || gameStatus.findChess(x1, y1)) {
                    setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
                } else {
                    setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
            }
        });
    }

    //画棋盘和棋子
    Graphics g;
    private Image BACKGROUND = CHESSBOARD;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        //添加背景图片
        g.drawImage(CHESSBOARD, 0, 0, this.getWidth(), this.getHeight(), this);

        //重绘出棋盘
        g.setColor(Color.black);//线条颜色：黑
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + GRID_SPAN * i, MARGIN + GRID_SPAN * (COLS - 1), MARGIN + GRID_SPAN * i);//横向画线
        }
        for (int j = 0; j < COLS; j++) {
            g.drawLine(MARGIN + GRID_SPAN * j, MARGIN, MARGIN + GRID_SPAN * j, MARGIN + GRID_SPAN * (ROWS - 1));//纵向画线
        }
        //画天元等
        g.setColor(Color.black);
        g.fillOval(MARGIN + 7 * GRID_SPAN - 5, MARGIN + 7 * GRID_SPAN - 5, 10, 10);
        g.fillOval(MARGIN + 3 * GRID_SPAN - 5, MARGIN + 3 * GRID_SPAN - 5, 10, 10);
        g.fillOval(MARGIN + 11 * GRID_SPAN - 5, MARGIN + 3 * GRID_SPAN - 5, 10, 10);
        g.fillOval(MARGIN + 3 * GRID_SPAN - 5, MARGIN + 11 * GRID_SPAN - 5, 10, 10);
        g.fillOval(MARGIN + 11 * GRID_SPAN - 5, MARGIN + 11 * GRID_SPAN - 5, 10, 10);
        //重绘出棋子
        for (int i = 0; i < chessCount; i++) {
            int countx = chessArray[i].getX_coordinate() * GRID_SPAN + MARGIN;//得到棋子x坐标
            int county = chessArray[i].getY_coordinate() * GRID_SPAN + MARGIN;//得到棋子y坐标
            Image img = chessArray[i].getChessImage();//得到棋子图片
            g.drawImage(img, countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER, null);
            if (i == chessCount - 1) {
                g.setColor(Color.red);//标记最后一个棋子为红色
                g.drawRect(countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER);
            }
        }
    }

    //游戏模式选择
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

    //鼠标点击事件
    @Override
    public void mousePressed(MouseEvent e) {
        String chessType = currentPlayer ? "黑棋" : "白棋";
        if (GameOver)//游戏结束，不能按
            return;
        int position_X = (e.getX() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子x坐标
        int position_Y = (e.getY() - MARGIN + GRID_SPAN / 2) / GRID_SPAN;//得到棋子y坐标
        if (GAMEMODE.equals("人 VS 人")) {
            GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
            if (!gameStatus.findChess(position_X, position_Y)) {
                gameStatus.Process(player1, player2, chess, gameStatus);
                repaint();
            }
        } else if (GAMEMODE.equals("人 VS AI")||GAMEMODE.equals("AI VS 人")) {
            GoBangChessPieces chess = new GoBangChessPieces(position_X, position_Y);
            if (!gameStatus.findChess(position_X, position_Y)) {
                gameStatus.Process(player1, player2, chess, gameStatus);//人下棋
                chessType = currentPlayer ? "黑棋" : "白棋";
                gameStatus.Process(player1, player2, chess, gameStatus);//AI下棋
                repaint();
            }
        } else if (GAMEMODE.equals("AI VS AI")) {
            System.out.println("gameStatus.currentPlayer的值是："+currentPlayer);
            while (!GameOver) {
                gameStatus.Process(player1, player2, null, gameStatus);//AI2下棋
                if(!GameOver)
                {
                    gameStatus.Process(player1, player2, null, gameStatus);//AI1下棋
                }

                repaint();
            }
            System.out.println("棋子数:"+chessCount);
        }


        if (GameOver == true) {
            String msg = String.format("恭喜 %s 赢了", chessType);
            JOptionPane.showMessageDialog(this, msg);
        }

        System.out.println("此时棋盘数组：");
        for (int i = 0; i < ROWS; i++) {
            for (int j = 0; j < COLS; j++) {
                System.out.print(board[i][j] + " ");
            }
            System.out.println();
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
