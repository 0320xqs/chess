package ChessGames.GoBang;

import ChessGames.GoBang.AI.GoBangGetAI;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.Config;
import ChessGames.template.SecondPlayer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GoBangSecondPlayer extends SecondPlayer {
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
//                Point point = getAI.play(config.secondAI);
                JSONObject jsonObject = getAI.play(config.secondAI);
                JSONArray jsonArray = (JSONArray) jsonObject.get("next");
                Point point = new Point((int)jsonArray.get(0),(int)jsonArray.get(1));
                temp = new GoBangChessPieces(point.x, point.y, ChessRole.BLACKCHESS);
                config.pieceArray[point.x][point.y] = temp;
                config.pieceList.add(temp);
                break;
        }
    }
}
