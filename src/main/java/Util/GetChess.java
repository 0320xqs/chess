package Util;


import ChessGames.ChineseChess.AI.CCGetAI;
import ChessGames.ChineseChess.CCController;
import ChessGames.GoBang.AI.GoBangGetAI;
import ChessGames.GoBang.GoBangController;
import ChessGames.template.*;

import javax.jws.Oneway;

public class GetChess {

    public static String[] ChessList = {"GoBang", "ChineseChess"};
    //获取棋类游戏列表
    public static Controller getChess(String chessname) {
        switch (chessname) {
            case "GoBang":
                return new GoBangController();
            case "ChineseChess":
                return new CCController();
        }
        return null;
    }
    //获取指定棋类游戏下的AI列表
    public static String[] getAIList (String chessName){
        switch (chessName){
            case "GoBang":
                return GoBangGetAI.AIList;
            case "ChineseChess":
                return CCGetAI.AIList;
        }
        return null;
    }
}
