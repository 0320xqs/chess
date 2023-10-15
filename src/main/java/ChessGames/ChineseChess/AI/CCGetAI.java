package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCChessPieces;
import ChessGames.ChineseChess.CCConfig;
import ChessGames.ChineseChess.Model.ChessRole;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
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
                //方式一：传输config类
//                return new MinMax(config,(config.currentPlayer == Part.SECOND ? 2:1)).play();
                //方式二：json对象
                 JSONObject json = convertToJson(config);
                //return new AI选择(json)
        }
        return null;
    }

    //将棋盘和当前选手信息数据转为json对象（或自定义传输信息）
    public JSONObject convertToJson(CCConfig config){
        int[][] board = new int [9][10];
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (config.pieceArray[i][j] != null){
                    ChessRole chessRole = ((CCChessPieces) config.pieceArray[i][j]).getChessRole();
                    switch(chessRole){
                        case RED_ROOK:
                            board[i][j] = 1;
                            break;
                        case RED_HORSE:
                            board[i][j] = 2;
                            break;
                        case RED_ELEPHANT:
                            board[i][j] = 3;
                            break;
                        case RED_MINISTER:
                            board[i][j] = 4;
                            break;
                        case RED_KING:
                            board[i][j] = 5;
                            break;
                        case RED_CANNON:
                            board[i][j] = 6;
                            break;
                        case RED_SOLDIER:
                            board[i][j] = 7;
                            break;
                        case BLACK_ROOK:
                            board[i][j] = 8;
                            break;
                        case BLACK_HORSE:
                            board[i][j] = 9;
                            break;
                        case BLACK_ELEPHANT:
                            board[i][j] = 10;
                            break;
                        case BLACK_MINISTER:
                            board[i][j] = 11;
                            break;
                        case BLACK_KING:
                            board[i][j] = 12;
                            break;
                        case BLACK_CANNON:
                            board[i][j] = 13;
                            break;
                        case BLACK_SOLDIER:
                            board[i][j] = 14;
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
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                jsonElements.add(board[i][j]);
            }
        }
        JSONObject BOARD = new JSONObject();
        BOARD.put("board",jsonElements);
        BOARD.put("currentPlay", config.currentPlayer.toString());
        System.out.println(BOARD);
        return BOARD;
    }
}
