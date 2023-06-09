package ChessGames.GoBang;

import ChessGames.template.ChessPieces;
import ChessGames.template.Player;


public class GoBangMan extends Player {

    @Override
    public ChessPieces play(ChessPieces pieces) {
        return new GoBangChessPieces(pieces.getX_coordinate(), pieces.getY_coordinate());
    }
}
