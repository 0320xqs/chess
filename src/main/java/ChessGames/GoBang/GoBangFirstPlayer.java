package ChessGames.GoBang;

import ChessGames.GoBang.AI.GoBangGetAI;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.FirstPlayer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;

public class GoBangFirstPlayer extends FirstPlayer {
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
//                Point point = getAI.play(config.secondAI);
                JSONObject jsonObject = getAI.play(config.secondAI);
                JSONArray jsonArray = (JSONArray) jsonObject.get("next");
                Point point = new Point((int)jsonArray.get(0),(int)jsonArray.get(1));
                temp = new GoBangChessPieces(point.x, point.y, ChessRole.WHITECHESS);
                config.pieceArray[point.x][point.y] = temp;
                config.pieceList.add(temp);
                break;
        }
    }
}
