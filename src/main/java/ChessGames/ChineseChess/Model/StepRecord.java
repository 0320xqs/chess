package ChessGames.ChineseChess.Model;

import ChessGames.ChineseChess.CCChessPieces;

import java.awt.*;

public class StepRecord {
    private final CCChessPieces piece;
    private final Point from;
    private final Point to;
    private final CCChessPieces eatenPiece;

    public StepRecord(CCChessPieces piece, Point from, Point to, CCChessPieces eatenPiece) {
        this.piece = piece;
        this.from = from;
        this.to = to;
        this.eatenPiece = eatenPiece;
    }

}
