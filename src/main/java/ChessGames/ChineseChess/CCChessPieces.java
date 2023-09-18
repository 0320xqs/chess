package ChessGames.ChineseChess;

import ChessGames.ChineseChess.Model.ChessRole;
import ChessGames.template.ChessPieces;
import lombok.Data;

import java.awt.*;
@Data
public class CCChessPieces extends ChessPieces {

    public ChessRole chessRole;

    public CCChessPieces(ChessRole chessRole, int x_coordinate, int y_coordinate) {
        super(x_coordinate, y_coordinate);
        this.chessRole = chessRole;
    }
}
