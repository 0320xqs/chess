package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Config;
import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoBangConfig extends Config {

    /**
     * UI
     **/
    public static int DIAMETER = 30;//棋子大小
    public static int MARGIN = 20;//棋盘左上角坐标
    public static int GRID_SPAN = 35;//一格长度

    public static int ROWS = 15;
    public static int COLS = 15;

    /**
     * 棋盘
     **/
    public int[][] board = new int[COLS][ROWS];
    public ArrayList<ChessPieces> chessArray = new ArrayList<>();//存储下棋顺序

    /**
     * 棋子
     **/
    public static int CHESSTYPE1 = 1;
    public static int CHESSTYPE2 = 2;
    public static Image BLACKCHESS = new ImageIcon("pic\\GoBang\\black.png").getImage();
    public static Image WHITECHESS = new ImageIcon("pic\\GoBang\\white.png").getImage();
    public static Image CHESSBOARD = new ImageIcon("pic\\ChessBoard.jpg").getImage();


    @Override
    public Point convertPlaceToLocation(int x, int y) {
        return null;
    }

    @Override
    public Point convertLocationToPlace(Point point) {
        return null;
    }
}
