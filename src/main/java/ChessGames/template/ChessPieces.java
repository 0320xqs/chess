package ChessGames.template;

import ChessGames.GoBang.Model.ChessRole;
import jdk.nashorn.internal.objects.annotations.Constructor;
import lombok.Data;

@Data
public class ChessPieces {

    private int X_coordinate;//棋子x坐标
    private int Y_coordinate;//棋子y坐标

    public ChessPieces() {

    }
    public ChessPieces(int x_coordinate, int y_coordinate) {
        X_coordinate = x_coordinate;
        Y_coordinate = y_coordinate;
    }

}
