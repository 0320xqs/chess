package ChessGames.GoBang;

import ChessGames.GoBang.AI.GetAI;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.SecondPlayer;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class FirstPLayer extends SecondPlayer {
    private GoBangConfig config;
    private GetAI getAI;

    public FirstPLayer(GoBangConfig config) {
        super(config);
        this.config = (GoBangConfig) config;
    }

    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        GoBangChessPieces temp;
        System.out.println(config.firstPlayer);
        switch (config.firstPlayer) {
            case Man:
                temp = new GoBangChessPieces(to.x, to.y, ChessRole.BLACKCHESS);
                config.board[to.x][to.y] = temp;
                config.chessArray.add(temp);
                break;
            case AI:
                getAI = new GetAI(config, 1);
                Point point = getAI.play(config.secondAI);
                temp = new GoBangChessPieces(point.x, point.y, ChessRole.BLACKCHESS);
                config.board[point.x][point.y] = temp;
                config.chessArray.add(temp);
                break;
        }
    }
}
