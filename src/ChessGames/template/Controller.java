package ChessGames.template;

import ChessGames.GoBang.GoBangChessPieces;

import java.util.concurrent.Callable;


public abstract class Controller implements Callable {
    public ChessBoard chessBoard;//棋盘
    public ChessPieces chessPieces;//棋子
    public ChessRules chessRules;//棋规

    public Config config;

    public abstract void GameModeSelect(String GameMode);

    public abstract void AIModeSelect(String AIMode);

    /**
     * @Date 18:39 2023/4/12
     * @Param null
     * @Descrition 游戏重开
     * @Return null
     **/
    public abstract void StartGame();

    public abstract int[] GameRecord();

    public void play(int xy, int Role) {
    }


    public String GetResult() {
        return null;
    }
}
