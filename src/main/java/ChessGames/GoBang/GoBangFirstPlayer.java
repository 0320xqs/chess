package ChessGames.GoBang;

import ChessGames.GoBang.AI.GoBangGetAI;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.SecondPlayer;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GoBangFirstPlayer extends SecondPlayer {
    private GoBangConfig config;
    private GoBangGetAI getAI;

    public GoBangFirstPlayer(GoBangConfig config) {
        super(config);
        this.config = (GoBangConfig) config;
    }

    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

        switch (config.firstPlayer) {
            case Man:
                GoBangChessPieces temp = new GoBangChessPieces(to.x, to.y, ChessRole.WHITECHESS);
                config.pieceArray[to.x][to.y] = temp;
                config.pieceList.add(temp);
                break;
            case AI:
                getAI = new GoBangGetAI(config, 1);
                Point point = getAI.play(config.secondAI);
                temp = new GoBangChessPieces(point.x, point.y, ChessRole.WHITECHESS);
                config.pieceArray[point.x][point.y] = temp;
                config.pieceList.add(temp);
                break;
        }
    }
}
