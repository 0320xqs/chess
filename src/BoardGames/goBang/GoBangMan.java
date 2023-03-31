package BoardGames.goBang;

import BoardGames.template.*;

public class GoBangMan extends Player implements GoBangConfig {
    public GoBangMan() {
        x_index = -1;
        y_index = -1;
    }

    public void play(GoBangChessPieces pieces, GoBangRules gameStatus) {
        x_index = pieces.getX_coordinate();
        y_index = pieces.getY_coordinate();
        chessType = gameStatus.CURRENT_PLAYER ? CHESSTYPE1 : CHESSTYPE2;//判断是什么颜色的棋子
        if (chessType == CHESSTYPE1) {
            pieces.setChessImage(BLACKCHESS);
            board[x_index][y_index] = chessType;//1黑棋
        } else {
            pieces.setChessImage(WHITECHESS);
            board[x_index][y_index] = chessType;//2白棋
        }
        chessArray[CHESSCOUNT++] = pieces;//将棋子对象添加到棋子数组中
        //判赢
        if (gameStatus.win(x_index, y_index, gameStatus.CURRENT_PLAYER)) {//判断是否胜利
            gameStatus.GameOver = true;
        } else if (gameStatus.chessCount == COLS * ROWS) {//判断是否全部下满
            gameStatus.GameOver = true;
        }
        gameStatus.CURRENT_PLAYER = !gameStatus.CURRENT_PLAYER;
    }
}
