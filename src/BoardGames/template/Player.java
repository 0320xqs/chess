package BoardGames.template;

import BoardGames.goBang.GoBangChessPieces;
import BoardGames.goBang.GoBangConfig;

public abstract class Player {
    public GoBangConfig Board;
    public int Role;
    public int Depth;

    public Player(GoBangConfig board, int role, int depth) {
        Board = board;
        Role = role;
        Depth = depth;
    }
    public GoBangChessPieces play(GoBangChessPieces pieces){
        return new GoBangChessPieces(0,0);
    }
}
