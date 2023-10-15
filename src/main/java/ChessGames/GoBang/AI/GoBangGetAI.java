package ChessGames.GoBang.AI;

import ChessGames.GoBang.GoBangChessPieces;
import ChessGames.GoBang.GoBangConfig;
import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSONArray;
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
//                方式一：使用config类
//                return new CNN(config).play();
                //方式二：使用json对象
                JSONObject json = convertToJson(config);
                return new CNN(json).play();
            case "MinMax":
                //方式一：使用config类
                return new MinMax(config,(config.currentPlayer == Part.SECOND ? 2:1)).play();
                //方式二：使用json对象
//                JSONObject json = convertToJson(config);
//                return new AI选择(json).play();
        }
        return null;
    }

    //将棋盘和当前选手信息数据转为json对象（或自定义传输信息）
    public JSONObject convertToJson(GoBangConfig config){
        int[][] board = new int [config.COLS][config.ROWS];
        for (int i = 0; i < config.COLS; i++) {
            for (int j = 0; j < config.ROWS; j++) {
                if (config.pieceArray[i][j] != null){
                    ChessRole chessRole = ((GoBangChessPieces) config.pieceArray[i][j]).getChessRole();
                    switch(chessRole){
                        case WHITECHESS:
                            board[i][j] = 1;
                            break;
                        case BLACKCHESS:
                            board[i][j] = 2;
                            break;
                    }
                }else{
                    board[i][j] = 0;
                }
            }
        }
        String currentPlayer = config.currentPlayer.toString();
        //构造json字符串
        JSONArray jsonElements = new JSONArray();
        for (int i = 0; i < config.COLS; i++) {
            for (int j = 0; j < config.ROWS; j++) {
                jsonElements.add(board[i][j]);
            }
        }
        JSONObject BOARD = new JSONObject();
        BOARD.put("board",jsonElements);
        BOARD.put("currentPlay", config.currentPlayer.toString());
        BOARD.put("ROWS", config.ROWS);
        BOARD.put("COLS", config.COLS);
        System.out.println(BOARD);
        return BOARD;
    }


}
