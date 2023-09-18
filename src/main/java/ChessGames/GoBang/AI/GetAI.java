package ChessGames.GoBang.AI;

import ChessGames.GoBang.GoBangConfig;
import ChessGames.template.Model.Part;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GetAI {
    public static String[] AIList = {"Minimax","CNN"};
    GoBangConfig config;
    int role;


    public GetAI(GoBangConfig config, int role) {
        this.config = config;
        this.role = role;
    }

    public Point play(String AI) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        switch (AI) {
            case "CNN":
                return new CNN(config).play();
            case "Minimax":
                return new Minimax(config,(config.currentPlayer== Part.SECOND ? 1:2)).play();
        }
        return null;
    }


}
