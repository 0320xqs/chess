package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCConfig;
import ChessGames.template.Model.Part;

import java.lang.reflect.InvocationTargetException;

public class GetAI {
    public static String[] AIList = {"Minimax"};
    CCConfig config;
    int role;


    public GetAI(CCConfig config, int role) {
        this.config = config;
        this.role = role;
    }

    public StepBean play(String AI) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        switch (AI) {
            case "CNN":
                break;
            case "Minimax":
                System.out.println("Minmax");
                return new Minimax(config,(config.currentPlayer == Part.SECOND ? 1:2)).play();
        }
        return null;
    }
}
