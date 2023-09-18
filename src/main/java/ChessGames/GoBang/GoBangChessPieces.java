package ChessGames.GoBang;

import ChessGames.GoBang.Model.ChessRole;
import ChessGames.template.ChessPieces;
import lombok.Data;

@Data
public class GoBangChessPieces extends ChessPieces {

    private ChessRole chessRole;

    public GoBangChessPieces(int x_coordinate, int y_coordinate, ChessRole chessRole) {
        super(x_coordinate, y_coordinate);
        this.chessRole = chessRole;
    }

}
