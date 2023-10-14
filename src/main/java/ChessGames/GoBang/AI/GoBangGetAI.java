package ChessGames.GoBang.AI;

import ChessGames.GoBang.GoBangConfig;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GoBangGetAI {
    public static String[] AIList = {"MinMax","CNN"};
    GoBangConfig config;
    int role;


    public GoBangGetAI(GoBangConfig config, int role) {
        this.config = config;
        this.role = role;
    }

    public JSONObject play(String AI) throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        switch (AI) {
            case "CNN":
                System.out.println(AI);
                return new CNN(config).play();
            case "MinMax":
                return new MinMax(config,(config.currentPlayer== Part.SECOND ? 1:2)).play();
        }
        return null;
    }


}
