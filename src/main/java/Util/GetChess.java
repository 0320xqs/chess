package Util;

import ChessGames.ChineseChess.CCController;
import ChessGames.GoBang.GoBangController;
import ChessGames.template.*;

public class GetChess {

    public static String[] ChessList = {"GoBang", "ChineseChess"};

    public static Controller getChess(String chessname) {
        switch (chessname) {
            case "GoBang":
                return new GoBangController();
            case "ChineseChess":
                return new CCController();
        }


        return null;
    }
}
