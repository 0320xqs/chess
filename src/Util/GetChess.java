package Util;

import ChessGames.GoBang.GoBangController;
import ChessGames.template.*;

public class GetChess {

    public static String[] ChessList={"GoBang",   };

    public static Controller getChess(String chessname){
        switch (chessname){
            case "GoBang": return new GoBangController();
        }


        return null;
    }
}
