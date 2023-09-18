package ChessGames.template;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;


public abstract class ChessRules {

    protected Config config;

    /**
     * @Date 18:41 2023/4/12
     * @Param null
     * @Descrition 获取开局棋盘状态
     * @Return null
     **/

    public abstract void GetBegin();

    /**
     * @Date 18:42 2023/4/12
     * @Param 对弈先手角色player1，对弈后手角色player2，本回合上一轮落下的棋子chess
     * @Descrition 对弈进行一回合
     * @Return null
     **/

    public abstract void Process(Player player1, Player player2, Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException;

    /**
     * @Date 18:44 2023/4/12
     * @Param null
     * @Descrition 游戏结束
     * @Return boolean
     **/

    public abstract Boolean End(Point from, Point to);

    /**
     * @Date 18:45 2023/4/12
     * @Param
     * @Descrition
     * @Return
     **/

    public abstract boolean win(Point from, Point to);


    /**
     * @Date 18:47 2023/4/12
     * @Param 选择棋子坐标from，落子坐标to
     * @Descrition 判断一步是否合理
     * @Return
     **/

    public abstract boolean check(Point from, Point to);

    /**
     * @Date 18:48 2023/4/12
     * @Param null
     * @Descrition 悔棋
     * @Return null
     **/

    public abstract void GoBack();


}
