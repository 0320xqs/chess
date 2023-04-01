package BoardGames.goBang;


import BoardGames.template.Player;
import static BoardGames.goBang.GoBangConfig.*;

public class GoBangMan extends Player{
//    public int chessType;
//    public int x_index;
//    public int y_index;

    public GoBangMan() {
        x_index = -1;
        y_index = -1;
    }

    public void play(GoBangChessPieces pieces, GoBangRules gameStatus) {
        x_index = pieces.getX_coordinate();
        y_index = pieces.getY_coordinate();
        chessType = currentPlayer ? CHESSTYPE1 : CHESSTYPE2;//判断是什么颜色的棋子
        if (chessType == CHESSTYPE1) {
            pieces.setChessImage(BLACKCHESS);
            board[x_index][y_index] = chessType;//1黑棋
        } else {
            pieces.setChessImage(WHITECHESS);
            board[x_index][y_index] = chessType;//2白棋
        }
        chessArray[chessCount++] = pieces;//将棋子对象添加到棋子数组中
        //判赢
        if (gameStatus.win(x_index, y_index, currentPlayer)) {//判断是否胜利
            GameOver = true;
        } else if (chessCount == COLS * ROWS) {//判断是否全部下满
            GameOver = true;
        }
        currentPlayer = !currentPlayer;
    }
}
