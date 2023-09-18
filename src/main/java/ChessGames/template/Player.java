package ChessGames.template;

import lombok.Data;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

@Data
public class Player {
    public Config config = null;

    public Player (Config config) {
        this.config = config;
    }
    /**
     * @Date 18:38 2023/4/12
     * @Param 棋子ChessPieces
     * @Descrition 对弈一方执行本回合操作
     * @Return 棋子ChessPieces
     **/
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    }
}
