package ChessGames.ChineseChess;

import ChessGames.template.ChessPieces;
import ChessGames.template.Config;


import java.awt.*;
import java.util.ArrayList;

import static ChessGames.ChineseChess.CCChessBoard.*;

public class CCConfig extends Config{

    public static int ROWS = 10;//行数
    public static int COLS = 9;//列数

    public ArrayList<ChessPieces> eatenList = new ArrayList<>();//存储下棋顺序

    public Boolean checkFlag = true;//阻断man与AI

    public CCConfig() {

    }
    public CCConfig(int ROWS, int COLS) {
        super(ROWS, COLS);
        this.ROWS = ROWS;
        this.COLS = COLS;
    }

    public static Point convertPlaceToLocation(int x, int y) {
        return new Point(X_INIT + x * GRID_SPAN, Y_INIT + y * GRID_SPAN);
    }
    public static Point convertLocationToPlace(Point point) {
        final int x = (point.x - X_INIT) / GRID_SPAN;
        final int y = (point.y - Y_INIT) / GRID_SPAN;
        if (x >= COLS || y >= ROWS) {
            return null;
        }
        return new Point(x, y);
    }
}
