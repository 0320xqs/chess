package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Config;
import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class GoBangConfig extends Config {
    public GoBangConfig() {
        this.board = new int[ROWS][COLS];
        this.chessArray = new ArrayList<>();
    }

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
