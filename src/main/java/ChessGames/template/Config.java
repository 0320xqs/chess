package ChessGames.template;

import java.awt.*;

public abstract class Config {
    public Player player1;//先手玩家1
    public Player player2;//后手玩家2
    public boolean currentPlayer;//当前回合玩家
    public int GameOver;//-1:游戏未开始 0:游戏未结束 1:游戏结束，平局 2:游戏结束，胜方为player1 3：游戏结束，胜方为player2
    public abstract Point convertPlaceToLocation(int x, int y);

    public abstract Point convertLocationToPlace(Point point);


}
