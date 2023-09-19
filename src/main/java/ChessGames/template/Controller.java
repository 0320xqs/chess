package ChessGames.template;


import ChessGames.template.Model.PlayerType;

import javax.swing.*;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.concurrent.Callable;


public abstract class Controller implements Callable {
    public ChessBoard chessBoard;//棋盘
    public ChessRules chessRules;
    public Config config = new Config();
    //游戏模式
    public String GAMEMODE = "人 VS 人";
    protected Player player1, player2;


    /**
     * @Date 18:39 2023/4/12
     * @Param null
     * @Descrition 对局开始
     * @Return null
     **/
    public abstract void StartGame() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    /**
     * @Date 18:39 2023/4/12
     * @Param null
     * @Descrition 游戏模式选择
     * @Return null
     **/
    public void GameModeSelect(String GameMode){
        this.GAMEMODE=GameMode;
        switch (GameMode) {
            case "人 VS 人":
                config.firstPlayer = PlayerType.Man;
                config.secondPlayer = PlayerType.Man;
                break;
            case "人 VS AI":
                config.firstPlayer = PlayerType.Man;
                config.secondPlayer = PlayerType.AI;
                break;
            case "AI VS 人":
                config.firstPlayer = PlayerType.AI;
                config.secondPlayer = PlayerType.Man;
                break;
            case "AI VS AI":
                config.firstPlayer = PlayerType.AI;
                config.secondPlayer = PlayerType.AI;
                break;
        }
    }

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
    public abstract void playRecond(int xy, int Role);

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

    @Override
    public int[] call() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException, Exception {
        StartGame();
        return GameRecord();
    }

    public abstract JPanel ChangeList();

    public abstract Controller changeGame();

    public void init() {
    }
}
