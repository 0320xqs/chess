package ChessGames.ChineseChess.Model;
import javax.swing.*;
import java.awt.*;

public enum ChessImage {
    BACKGROUND(new ImageIcon("pic\\ChineseChess\\00.jpg").getImage()),
    CHESSBOARD(new ImageIcon("pic\\ChineseChess\\01.jpg").getImage()),
    BLACK_KING(new ImageIcon("pic\\ChineseChess\\images\\BK.GIF").getImage()),
    BLACK_MINISTER(new ImageIcon("pic\\ChineseChess\\images\\BM.GIF").getImage()),
    BLACK_ELEPHANT(new ImageIcon("pic\\ChineseChess\\images\\BE.GIF").getImage()),
    BLACK_HORSE(new ImageIcon("pic\\ChineseChess\\images\\BH.GIF").getImage()),
    BLACK_ROOK(new ImageIcon("pic\\ChineseChess\\images\\BR.GIF").getImage()),
    BLACK_CANNON(new ImageIcon("pic\\ChineseChess\\images\\BC.GIF").getImage()),
    BLACK_SOLDIER(new ImageIcon("pic\\ChineseChess\\images\\BS.GIF").getImage()),
    RED_KING(new ImageIcon("pic\\ChineseChess\\images\\RK.GIF").getImage()),
    RED_MINISTER(new ImageIcon("pic\\ChineseChess\\images\\RM.GIF").getImage()),
    RED_ELEPHANT(new ImageIcon("pic\\ChineseChess\\images\\RE.GIF").getImage()),
    RED_HORSE(new ImageIcon("pic\\ChineseChess\\images\\RH.GIF").getImage()),
    RED_ROOK(new ImageIcon("pic\\ChineseChess\\images\\RR.GIF").getImage()),
    RED_CANNON(new ImageIcon("pic\\ChineseChess\\images\\RC.GIF").getImage()),
    RED_SOLDIER(new ImageIcon("pic\\ChineseChess\\images\\RS.GIF").getImage());


    public Image image;

    ChessImage(Image image) {
        this.image = image;
    }
}
