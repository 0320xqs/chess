package ChessGames.template;

import ChessGames.GoBang.GoBangConfig;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class SecondPlayer extends Player{
    public Config config = null;

    public SecondPlayer (Config config) {
        super(config);
        this.config = config;
    }
    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    }
}
