package ChessGames.GoBang.Model;

import javax.swing.*;
import java.awt.*;

public enum ChessImage {
    BLACKCHESS(new ImageIcon("pic\\GoBang\\black.png").getImage()),
    WHITECHESS(new ImageIcon("pic\\GoBang\\white.png").getImage()),
    CHESSBOARD(new ImageIcon("pic\\ChessBoard.jpg").getImage());
    Image image;

    ChessImage(Image image) {
        this.image = image;
    }
}
