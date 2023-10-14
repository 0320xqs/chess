package ChessGames.template;

import ChessGames.ChineseChess.AI.CCGetAI;
import ChessGames.template.Model.GameResult;
import ChessGames.template.Model.Part;
import ChessGames.template.Model.PlayerType;
import lombok.Data;

import java.awt.*;
import java.util.ArrayList;
@Data
public class Config {
    public PlayerType firstPlayer = PlayerType.Man;//先手

    public PlayerType secondPlayer = PlayerType.Man;//后手

    //设置AI类型
    public String secondAI = CCGetAI.AIList[0];
    public String firstAI = CCGetAI.AIList[0];

    public static int ROWS = 100;//行数
    public static int COLS = 100;//列数

    public Part currentPlayer = Part.FIRST;//当前回合选手

    public GameResult gameResult = GameResult.NOTSTARTED;//对局结果

    public ChessPieces[][] pieceArray = new ChessPieces[COLS][ROWS];//棋盘上的棋子

    public ArrayList<ChessPieces> pieceList = new ArrayList<>();//存储下棋顺序

    public String minMinDepth = "2";//思考深度

    public Config() {

    }

    public Config(int ROWS, int COLS) {
        this.ROWS = ROWS;
        this.COLS = COLS;
    }

    public static Point convertPlaceToLocation(int x, int y) {
        return null;
    }

    public static Point convertLocationToPlace(Point point) {
        return null;
    }


}
