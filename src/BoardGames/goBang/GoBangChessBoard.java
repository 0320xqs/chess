package BoardGames.goBang;

import javax.swing.*;
import java.awt.*;

import BoardGames.template.*;

public class GoBangChessBoard extends chessBoard implements GoBangConfig {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        //添加背景图片
        g.drawImage(CHESSBOARD, 0, 0, this.getWidth(), this.getHeight(), this);

        //重绘出棋盘
        g.setColor(Color.black);//线条颜色：黑
        for (int i = 0; i < ROW; i++) {
            g.drawLine(X, Y + SIZE * i, X + SIZE * (COLUMN - 1), Y + SIZE * i);//横向画线
        }
        for (int j = 0; j < COLUMN; j++) {
            g.drawLine(X + SIZE * j, Y, X + SIZE * j, Y + SIZE * (ROW - 1));//纵向画线
        }
        for (int i = 0; i < ROW; i++) {
            String number = Integer.toString(i);
            g.drawString(number, X + SIZE * i-3, Y-5);
        }
        for (int i = 1; i < COLUMN; i++) {
            String number = Integer.toString(i);
            g.drawString(number, X-15, Y + SIZE * i);
        }
        //重绘出棋子
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COLUMN; j++) {
                if (board[i][j] == CHESSTYPE1) {
                    int countx = SIZE * j + SIZE / 2;
                    int county = SIZE * i + SIZE / 2;
                    g.drawImage(BLACKCHESS, countx - SIZE + X, county - SIZE / 2, SIZE, SIZE, null);
                } else if (board[i][j] == CHESSTYPE2) {
                    int countx = SIZE * j + SIZE / 2;
                    int county = SIZE * i + SIZE / 2;
                    g.drawImage(WHITECHESS, countx - SIZE + X, county - SIZE / 2, SIZE, SIZE, null);
                }
            }
        }


    }
}
