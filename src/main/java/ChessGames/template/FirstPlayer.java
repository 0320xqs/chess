package ChessGames.template;


import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class FirstPlayer extends Player{
    public Config config = null;

    public FirstPlayer (Config config) {
        super(config);
        this.config = config;
    }
    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {

    }
}
