package ChessGames.template;

import ChessGames.GoBang.GoBangChessPieces;

public abstract class Config {

    /**
     * UI
     **/
    public int DIAMETER;
    public int MARGIN;//棋盘左上角坐标
    public int GRID_SPAN;//一格长度
    public int ROWS;//行数
    public int COLS;//列数
    /**
     * 玩家
     **/
    public Player player1;//玩家1
    public Player player2;//玩家2
    /**
     * 棋盘
     **/
    public boolean currentPlayer;// true：黑子 false：白子 开始默认黑子先下
    public boolean GameOpen;//定义游戏是否开局
    public boolean GameOver;//定义是否游戏结束
    public int chessCount;//棋子数目
    public int[][] board;
    public ChessPieces[] chessArray;//存储下棋顺序
}
