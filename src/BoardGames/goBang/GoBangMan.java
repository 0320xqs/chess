package BoardGames.goBang;

import BoardGames.template.Player;


public class GoBangMan extends Player {

    public GoBangMan(GoBangConfig board, int role, int depth) {
        super(board, role, depth);
    }

    @Override
    public GoBangChessPieces play(GoBangChessPieces pieces) {
        return new GoBangChessPieces(pieces.getX_coordinate(),pieces.getY_coordinate());

    }
}
