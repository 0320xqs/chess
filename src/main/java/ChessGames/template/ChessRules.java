package ChessGames.template;

import ChessGames.GoBang.GoBangChessPieces;



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

    public abstract void Process(Player player1, Player player2, ChessPieces chess);

    /**
     * @Date 18:44 2023/4/12
     * @Param null
     * @Descrition 游戏结束
     * @Return boolean
     **/

    public abstract Boolean End(ChessPieces chessPieces);

    /**
     * @Date 18:45 2023/4/12
     * @Param
     * @Descrition
     * @Return
     **/

    public abstract boolean win(int x, int y, boolean start);


    /**
     * @Date 18:47 2023/4/12
     * @Param 坐标x，y
     * @Descrition 判断一步是否合理
     * @Return
     **/

    public abstract boolean check(int position_X, int position_Y);

    /**
     * @Date 18:48 2023/4/12
     * @Param null
     * @Descrition 悔棋
     * @Return null
     **/

    public abstract void GoBack();


}
