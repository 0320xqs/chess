package BoardGames.goBang;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import BoardGames.template.*;

public class GoBangChessBoard extends chessBoard implements GoBangConfig {

    GoBangFrame.EpicycleData epicycleData=new GoBangFrame().EpicycleData;
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
        for (int i = 0; i < gameStatus.chessCount; i++) {
            int countx = chessArray[i].getX_coordinate() * GRID_SPAN + MARGIN;//得到棋子x坐标
            int county = chessArray[i].getY_coordinate() * GRID_SPAN + MARGIN;//得到棋子y坐标
            Image img = chessArray[i].getChessImage();//得到棋子图片
            g.drawImage(img, countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER, null);
            if (i == gameStatus.chessCount - 1) {
                g.setColor(Color.red);//标记最后一个棋子为红色
                g.drawRect(countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER);
            }
        }
    }






}
