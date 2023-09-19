package ChessGames.GoBang;

import ChessGames.GoBang.AI.GoBangGetAI;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.Config;
import ChessGames.template.FirstPlayer;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GoBangSecondPlayer extends FirstPlayer {
    private GoBangConfig config;
    private GoBangGetAI getAI;

    public GoBangSecondPlayer(Config config) {
        super(config);
        this.config = (GoBangConfig) config;
    }

    @Override
    public void play(Point from,Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        switch (config.secondPlayer) {
            case Man:
                GoBangChessPieces temp = new GoBangChessPieces(to.x, to.y, ChessRole.BLACKCHESS);
                config.pieceArray[to.x][to.y] = temp;
                config.pieceList.add(temp);
                break;
            case AI:
                getAI = new GoBangGetAI(config, 1);
                Point point = getAI.play(config.secondAI);
                temp = new GoBangChessPieces(point.x, point.y, ChessRole.BLACKCHESS);
                config.pieceArray[point.x][point.y] = temp;
                config.pieceList.add(temp);
                break;
        }
    }
}
