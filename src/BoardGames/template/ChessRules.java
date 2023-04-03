package BoardGames.template;

import BoardGames.goBang.GoBangChessPieces;

import static BoardGames.goBang.GoBangConfig.*;
import static BoardGames.goBang.GoBangConfig.currentPlayer;

public class ChessRules {


    /**
     * 棋盘初始化
     * **/
    public int[][] GetBegin() {
        return new int[0][0];

    }

    /**
     * 行棋过程-一步
     * **/
    public void Process(Player player1, Player player2, GoBangChessPieces chess) {

    }

    public Boolean End() {

        return true;
    }

    /**
     * 判赢
     * **/
    public boolean win(int x, int y, boolean start) {
        return false;

    }

    /**
     * 查找所在位置是否有棋子
     * **/
    public  boolean findChess(int position_X, int position_Y) {

        return false;

    }

    /**
     * 悔棋
     * **/
    public void GoBack() {

    }

}
