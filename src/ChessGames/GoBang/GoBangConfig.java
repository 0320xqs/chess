package ChessGames.GoBang;

import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;

public class GoBangConfig {
    /**
     * UI
     **/
    public static int DIAMETER = 30;
    public static int MARGIN = 35;
    public static int GRID_SPAN = 35;//一格长度
    public static int ROWS = 15;//行数
    public static int COLS = 15;//列数

    /**
     * 模式
     **/
    public static String GAMEMODE;//游戏模式
    public static String AIMODE;//AI模式
    public static  int AIDepth;//AI难度

    /**
     * 玩家
     **/
    public static  Player player1;//玩家1
    public static  Player player2;//玩家2
    /**
     * 棋盘
     **/
    public static boolean currentPlayer;// true：黑子 false：白子 开始默认黑子先下
    public static boolean GameOpen;//定义游戏是否开局
    public static boolean GameOver;//定义是否游戏结束
    public static int chessCount;//棋子数目
    public static int[][] board = new int[ROWS][COLS];//棋盘记录
    public static GoBangChessPieces[] chessArray = new GoBangChessPieces[ROWS * COLS];//存储下棋顺序
    /**
     * 棋子
     **/
    public static int CHESSTYPENUM = 2;
    public static int CHESSTYPE1 = 1;
    public static int CHESSTYPE2 = 2;
    public static Image BLACKCHESS = new ImageIcon("pic\\GoBang\\black.png").getImage();
    public static Image WHITECHESS = new ImageIcon("pic\\GoBang\\white.png").getImage();
    public static Image CHESSBOARD = new ImageIcon("pic\\GoBang\\ChessBoard.jpg").getImage();

}
