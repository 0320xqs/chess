package ChessGames.template;

import ChessGames.GoBang.GoBangChessPieces;

import javax.swing.*;
import java.util.HashMap;
import java.util.concurrent.Callable;


public abstract class Controller implements Callable {
    public ChessBoard chessBoard;//棋盘
    public ChessPieces chessPieces;//棋子
    public ChessRules chessRules;//棋规
    public Config config;//所需数据


    /**
     * @Date 18:39 2023/4/12
     * @Param null
     * @Descrition 对局开始
     * @Return null
     **/
    public abstract void StartGame();

    /**
     * @Date 18:39 2023/6/7
     * @Param null
     * @Descrition 对局记录
     * @Return null
     **/
    public abstract int[] GameRecord();


    /**
     * @Date 18:39 2023/6/7
     * @Param null
     * @Descrition 播放记录
     * @Return null
     **/
    public abstract void play(int xy, int Role);

    /**
     * @Date 18:39 2023/6/7
     * @Param null
     * @Descrition 返回对局结果
     * @Return String
     **/
    public abstract String GetResult();


    /**
     * @Date 18:39 2023/6/7
     * @Param null
     * @Descrition 获取展示面板，一般为棋盘
     * @Return JPanel
     **/
    public abstract JPanel GetBoard();

    public void init() {
    }

    ;

    public void GameModeSelect(String GameMode) {
    }

    ;

    public void AIModeSelect(String AIMode) {
    }

    ;
}
