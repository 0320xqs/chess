package BoardGames.goBang;

import com.sun.rowset.internal.Row;

import javax.swing.*;
import java.awt.*;

public interface GoBangConfig {
    int CHESSTYPENUM = 2;
    int CHESSTYPE1 = 1;
    int CHESSTYPE2 = 2;
    int X = 120;
    int Y = 35;
    int SIZE = 64;//一格长度
    int ROW = 15;//行数
    int COLUMN = 15;//列数
    int[][] board=new int[ROW][COLUMN];//棋盘记录

    Image BLACKCHESS = new ImageIcon("pic\\GoBang\\black.png").getImage();
    Image WHITECHESS = new ImageIcon("pic\\GoBang\\white.png").getImage();
    Image CHESSBOARD = new ImageIcon("pic\\GoBang\\ChessBoard.jpg").getImage();
    Dimension dim2 = new Dimension(145, 40);//设置登录按钮组件的大小
    Dimension dim3 = new Dimension(120, 120);//设置头像组件的大小
    Dimension dim4 = new Dimension(140, 45);//设置右边按钮组件的大小
}
