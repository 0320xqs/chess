package ChessGames.ChineseChess;

import ChessGames.template.ChessPieces;

import java.awt.*;

public class CCChessPieces extends ChessPieces {
    public boolean getPart() {
        return part;
    }

    public void setPart(boolean part) {
        this.part = part;
    }

    private boolean part;//true代表红方，false代表黑方

    public CCChessPieces(boolean part, Image image, int x_coordinate, int y_coordinate) {
        super(x_coordinate, y_coordinate);
        super.setChessImage(image);
        this.part = part;
    }


}
