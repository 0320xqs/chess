package ChessGames.template;

import ChessGames.GoBang.GoBangChessPieces;

import java.util.ArrayList;

public abstract class Config {

    /**
     * UI
     **/
    public int MARGIN;//棋盘左上角坐标
    public int GRID_SPAN;//一格长度
    public int ROWS;//行数
    public int COLS;//列数

    /**
     * 棋盘
     **/
    public boolean currentPlayer;
    public int GameOver;//0:游戏未结束 1:游戏结束，平局 2:游戏结束，胜方为player1 3：游戏结束，胜方为player2
    public int[][] board;
    public ArrayList<ChessPieces> chessArray;//存储下棋顺序
}
