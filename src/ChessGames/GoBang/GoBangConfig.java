package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoBangConfig {


    /**
     * UI
     **/
    public static int DIAMETER = 30;
    public static int MARGIN = 35;
    public static int GRID_SPAN = 35;
    public static int ROWS = 15;
    public static int COLS = 15;

    /**
     * 玩家
     **/

    public static Player player1;//先手玩家1
    public static Player player2;//后手玩家2

    /**
     * 棋盘
     **/
    public static boolean currentPlayer;
    public static int GameOver;//0:游戏未结束 1:游戏结束，平局 2:游戏结束，胜方为player1 3：游戏结束，胜方为player2
    public static int[][] board = new int[ROWS][COLS];
    public static ArrayList<ChessPieces> chessArray = new ArrayList<>();//存储下棋顺序

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
