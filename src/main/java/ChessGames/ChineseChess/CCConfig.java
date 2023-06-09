package ChessGames.ChineseChess;

import ChessGames.template.ChessPieces;
import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class CCConfig {

    static int RANGE_X = 9;//9列
    static int RANGE_Y = 10;//10行

    static int X_INIT = 50;//棋盘起始x坐标
    static int Y_INIT = 20;//棋盘起始y坐标
    static int GRID_SPAN = 57;//一格长度

    /**
     * 玩家
     **/

    public Player player1;//先手玩家1
    public Player player2;//后手玩家2

    /**
     * 棋盘
     **/
    public boolean currentPlayer;
    public int GameOver;//-1:游戏未开始 0:游戏未结束 1:游戏结束，平局 2:游戏结束，胜方为player1 3：游戏结束，胜方为player2

    /**
     * 棋子
     **/
    public HashMap<CCChessPieces,String> arrayList;//棋子列表
    public CCChessPieces[][] pieceArray = new CCChessPieces[9][10];//棋子索引
    public static Image BACKGROUND = new ImageIcon("pic\\ChineseChess\\00.jpg").getImage();
    public static Image CHESSBOARD = new ImageIcon("pic\\ChineseChess\\01.jpg").getImage();
    public static Image BLACK_KING = new ImageIcon("pic\\ChineseChess\\images\\BK.GIF").getImage();
    public static Image BLACK_MINISTER = new ImageIcon("pic\\ChineseChess\\images\\BM.GIF").getImage();
    public static Image BLACK_ELEPHANT = new ImageIcon("pic\\ChineseChess\\images\\BE.GIF").getImage();
    public static Image BLACK_HORSE = new ImageIcon("pic\\ChineseChess\\images\\BH.GIF").getImage();
    public static Image BLACK_ROOK = new ImageIcon("pic\\ChineseChess\\images\\BR.GIF").getImage();
    public static Image BLACK_CANNON = new ImageIcon("pic\\ChineseChess\\images\\BC.GIF").getImage();
    public static Image BLACK_SOLDIER = new ImageIcon("pic\\ChineseChess\\images\\BS.GIF").getImage();
    public static Image RED_KING = new ImageIcon("pic\\ChineseChess\\images\\RK.GIF").getImage();
    public static Image RED_MINISTER = new ImageIcon("pic\\ChineseChess\\images\\RM.GIF").getImage();
    public static Image RED_ELEPHANT = new ImageIcon("pic\\ChineseChess\\images\\RE.GIF").getImage();
    public static Image RED_HORSE = new ImageIcon("pic\\ChineseChess\\images\\RH.GIF").getImage();
    public static Image RED_ROOK = new ImageIcon("pic\\ChineseChess\\images\\RR.GIF").getImage();
    public static Image RED_CANNON = new ImageIcon("pic\\ChineseChess\\images\\RC.GIF").getImage();
    public static Image RED_SOLDIER = new ImageIcon("pic\\ChineseChess\\images\\RS.GIF").getImage();

    CCChessPieces red_rook1 = new CCChessPieces(true, RED_ROOK, 0, 9);
    CCChessPieces red_horse1 = new CCChessPieces(true, RED_HORSE, 1, 9);
    CCChessPieces red_elephant1 = new CCChessPieces(true, RED_ELEPHANT, 2, 9);
    CCChessPieces red_minister1 = new CCChessPieces(true, RED_MINISTER, 3, 9);
    CCChessPieces red_king = new CCChessPieces(true, RED_KING, 4, 9);
    CCChessPieces red_minister2 = new CCChessPieces(true, RED_MINISTER, 5, 9);
    CCChessPieces red_elephant2 = new CCChessPieces(true, RED_ELEPHANT, 6, 9);
    CCChessPieces red_horse2 = new CCChessPieces(true, RED_HORSE, 7, 9);
    CCChessPieces red_rook2 = new CCChessPieces(true, RED_ROOK, 8, 9);
    CCChessPieces red_cannon1 = new CCChessPieces(true, RED_CANNON, 1, 7);
    CCChessPieces red_cannon2 = new CCChessPieces(true, RED_CANNON, 7, 7);
    CCChessPieces red_solider1 = new CCChessPieces(true, RED_SOLDIER, 0, 6);
    CCChessPieces red_solider2 = new CCChessPieces(true, RED_SOLDIER, 2, 6);
    CCChessPieces red_solider3 = new CCChessPieces(true, RED_SOLDIER, 4, 6);
    CCChessPieces red_solider4 = new CCChessPieces(true, RED_SOLDIER, 6, 6);
    CCChessPieces red_solider5 = new CCChessPieces(true, RED_SOLDIER, 8, 6);

    CCChessPieces black_rook1 = new CCChessPieces(false, BLACK_ROOK, 0, 0);
    CCChessPieces black_horse1 = new CCChessPieces(false, BLACK_HORSE, 1, 0);
    CCChessPieces black_elephant1 = new CCChessPieces(false, BLACK_ELEPHANT, 2, 0);
    CCChessPieces black_minister1 = new CCChessPieces(false, BLACK_MINISTER, 3, 0);
    CCChessPieces black_king = new CCChessPieces(false, BLACK_KING, 4, 0);
    CCChessPieces black_minister2 = new CCChessPieces(false, BLACK_MINISTER, 5, 0);
    CCChessPieces black_elephant2 = new CCChessPieces(false, BLACK_ELEPHANT, 6, 0);
    CCChessPieces black_horse2 = new CCChessPieces(false, BLACK_HORSE, 7, 0);
    CCChessPieces black_rook2 = new CCChessPieces(false, BLACK_ROOK, 8, 0);
    CCChessPieces black_cannon1 = new CCChessPieces(false, BLACK_CANNON, 1, 2);
    CCChessPieces black_cannon2 = new CCChessPieces(false, BLACK_CANNON, 7, 2);
    CCChessPieces black_solider1 = new CCChessPieces(false, BLACK_SOLDIER, 0, 3);
    CCChessPieces black_solider2 = new CCChessPieces(false, BLACK_SOLDIER, 2, 3);
    CCChessPieces black_solider3 = new CCChessPieces(false, BLACK_SOLDIER, 4, 3);
    CCChessPieces black_solider4 = new CCChessPieces(false, BLACK_SOLDIER, 6, 3);
    CCChessPieces black_solider5 = new CCChessPieces(false, BLACK_SOLDIER, 8, 3);




    public static Point convertPlaceToLocation(int x, int y) {
        return new Point(X_INIT + x * GRID_SPAN, Y_INIT + y * GRID_SPAN);
    }

    public static Point convertLocationToPlace(Point point) {
        final int x = (point.x - X_INIT) / GRID_SPAN;
        final int y = (point.y - Y_INIT) / GRID_SPAN;
        if (x >= RANGE_X || y >= RANGE_Y) {
            return null;
        }
        return new Point(x, y);
    }

}
