package BoardGames.goBang;

import com.sun.rowset.internal.Row;

import javax.swing.*;
import java.awt.*;

public interface GoBangConfig {
    int CHESSTYPENUM = 2;
    int CHESSTYPE1 = 1;
    int CHESSTYPE2 = 2;
    int DIAMETER = 30;//棋子图片缩放？
    int MARGIN = 35;//棋盘左上角坐标
    int GRID_SPAN = 35;//一格长度
    int ROWS = 15;//行数
    int COLS = 15;//列数
    int UIWIDTH = 1265;//UI显示长度
    int UIHIGHTH = 985;//UI显示宽度
    int[][] board = new int[ROWS][COLS];//棋盘记录
    GoBangChessPieces[] chessArray = new GoBangChessPieces[ROWS * COLS];//存储下棋顺序

    String GameMode[] = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};//游戏模式选择
    String AI_Rate[] = {"小白", "新手", "普通"};//AI难度选择


    Image BLACKCHESS = new ImageIcon("pic\\GoBang\\black.png").getImage();
    Image WHITECHESS = new ImageIcon("pic\\GoBang\\white.png").getImage();
    Image CHESSBOARD = new ImageIcon("pic\\GoBang\\ChessBoard.jpg").getImage();

    Dimension dim1 = new Dimension(150, 20);//设置下拉框组件的大小
    Dimension dim2 = new Dimension(120, 40);//设置按钮组件的大小
    Dimension dim3 = new Dimension(140, 45);//设置右边按钮组件的大小
}
