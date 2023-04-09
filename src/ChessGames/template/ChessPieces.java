package ChessGames.template;

import java.awt.*;

public class ChessPieces {

    private int X_coordinate, Y_coordinate;//棋子xy坐标
    private Image ChessImage;//棋子图像

    public ChessPieces() {
        X_coordinate = -1;
        Y_coordinate = -1;
        ChessImage = null;
    }

    public ChessPieces(int x_coordinate, int y_coordinate) {
        X_coordinate = x_coordinate;
        Y_coordinate = y_coordinate;
    }

    public Image getChessImage() {
        return ChessImage;
    }

    public void setChessImage(Image chessImage) {
        ChessImage = chessImage;
    }


    public int getX_coordinate() {
        return X_coordinate;
    }

    public void setX_coordinate(int x_coordinate) {
        X_coordinate = x_coordinate;
    }

    public int getY_coordinate() {
        return Y_coordinate;
    }

    public void setY_coordinate(int y_coordinate) {
        Y_coordinate = y_coordinate;
    }
}
