package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCConfig;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;

public class CCGetAI {
    public static String[] AIList = {"MinMax"};
    CCConfig config;
    int role;


    public CCGetAI(CCConfig config, int role) {
        this.config = config;
        this.role = role;
    }

    public JSONObject play(String AI) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {
        switch (AI) {
            case "CNN":
                break;
            case "MinMax":
                System.out.println("Minmax");
                return new MinMax(config,(config.currentPlayer == Part.SECOND ? 1:2)).play();
        }
        return null;
    }
}
