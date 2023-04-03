package BoardGames.goBang;

import BoardGames.template.Config;
import BoardGames.template.Player;

import javax.swing.*;
import java.awt.*;

public class GoBangConfig extends Config {
    /**
     * UI
     **/
    static int DIAMETER = 30;
    static int MARGIN = 35;
    static int GRID_SPAN = 35;//一格长度
    static int ROWS = 15;//行数
    static int COLS = 15;//列数

    /**
     * 模式
     **/
    public static String GAMEMODE;//游戏模式
    public static String AIMODE;//AI模式
    public static  int AIDepth;//AI难度
    public static String[] GameMode = {"人 VS 人", "人 VS AI", "AI VS 人", "AI VS AI"};//游戏模式选择
    public static String[] AI_Rate = {"小白", "新手", "普通"};//AI难度选择
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
    static int CHESSTYPENUM = 2;
    static int CHESSTYPE1 = 1;
    static int CHESSTYPE2 = 2;
    static Image BLACKCHESS = new ImageIcon("pic\\GoBang\\black.png").getImage();
    static Image WHITECHESS = new ImageIcon("pic\\GoBang\\white.png").getImage();
    static Image CHESSBOARD = new ImageIcon("pic\\GoBang\\ChessBoard.jpg").getImage();


}
