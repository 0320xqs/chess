package ChessGames.ChineseChess;

import ChessGames.template.ChessPieces;
import ChessGames.template.ChessRules;
import ChessGames.template.Player;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static ChessGames.ChineseChess.CCConfig.*;

public class CCRules extends ChessRules {

    private CCConfig ccConfig;

    public CCRules(CCConfig ccConfig) {
        this.ccConfig = ccConfig;
    }

    @Override
    public void GetBegin() {
        // 添加红色棋子
        ccConfig.arrayList.put(ccConfig.red_rook1, "红车1");
        ccConfig.arrayList.put(ccConfig.red_horse1, "红马1");
        ccConfig.arrayList.put(ccConfig.red_elephant1, "红相1");
        ccConfig.arrayList.put(ccConfig.red_minister1, "红士1");
        ccConfig.arrayList.put(ccConfig.red_king, "红帅");
        ccConfig.arrayList.put(ccConfig.red_minister2, "红士2");
        ccConfig.arrayList.put(ccConfig.red_elephant2, "红相2");
        ccConfig.arrayList.put(ccConfig.red_horse2, "红马2");
        ccConfig.arrayList.put(ccConfig.red_rook2, "红车2");
        ccConfig.arrayList.put(ccConfig.red_cannon1, "红炮1");
        ccConfig.arrayList.put(ccConfig.red_cannon2, "红炮2");
        ccConfig.arrayList.put(ccConfig.red_solider1, "红兵1");
        ccConfig.arrayList.put(ccConfig.red_solider2, "红兵2");
        ccConfig.arrayList.put(ccConfig.red_solider3, "红兵3");
        ccConfig.arrayList.put(ccConfig.red_solider4, "红兵4");
        ccConfig.arrayList.put(ccConfig.red_solider5, "红兵5");

        ccConfig.pieceArray[0][9] = ccConfig.red_rook1;
        ccConfig.pieceArray[1][9] = ccConfig.red_horse1;
        ccConfig.pieceArray[2][9] = ccConfig.red_elephant1;
        ccConfig.pieceArray[3][9] = ccConfig.red_minister1;
        ccConfig.pieceArray[4][9] = ccConfig.red_king;
        ccConfig.pieceArray[5][9] = ccConfig.red_minister2;
        ccConfig.pieceArray[6][9] = ccConfig.red_elephant2;
        ccConfig.pieceArray[7][9] = ccConfig.red_horse2;
        ccConfig.pieceArray[8][9] = ccConfig.red_rook2;
        ccConfig.pieceArray[1][7] = ccConfig.red_cannon1;
        ccConfig.pieceArray[7][7] = ccConfig.red_cannon2;
        ccConfig.pieceArray[0][6] = ccConfig.red_solider1;
        ccConfig.pieceArray[2][6] = ccConfig.red_solider2;
        ccConfig.pieceArray[4][6] = ccConfig.red_solider3;
        ccConfig.pieceArray[6][6] = ccConfig.red_solider4;
        ccConfig.pieceArray[8][6] = ccConfig.red_solider5;
        // 添加黑色棋子
        ccConfig.arrayList.put(ccConfig.black_rook1, "黑车1");
        ccConfig.arrayList.put(ccConfig.black_horse1, "黑马1");
        ccConfig.arrayList.put(ccConfig.black_elephant1, "黑象1");
        ccConfig.arrayList.put(ccConfig.black_minister1, "黑士1");
        ccConfig.arrayList.put(ccConfig.black_king, "黑将");
        ccConfig.arrayList.put(ccConfig.black_minister2, "黑士2");
        ccConfig.arrayList.put(ccConfig.black_elephant2, "黑象2");
        ccConfig.arrayList.put(ccConfig.black_horse2, "黑马2");
        ccConfig.arrayList.put(ccConfig.black_rook2, "黑车2");
        ccConfig.arrayList.put(ccConfig.black_cannon1, "黑炮1");
        ccConfig.arrayList.put(ccConfig.black_cannon2, "黑炮2");
        ccConfig.arrayList.put(ccConfig.black_solider1, "黑卒1");
        ccConfig.arrayList.put(ccConfig.black_solider2, "黑卒2");
        ccConfig.arrayList.put(ccConfig.black_solider3, "黑卒3");
        ccConfig.arrayList.put(ccConfig.black_solider4, "黑卒4");
        ccConfig.arrayList.put(ccConfig.black_solider5, "黑卒5");

        ccConfig.pieceArray[0][0] = ccConfig.black_rook1;
        ccConfig.pieceArray[1][0] = ccConfig.black_horse1;
        ccConfig.pieceArray[2][0] = ccConfig.black_elephant1;
        ccConfig.pieceArray[3][0] = ccConfig.black_minister1;
        ccConfig.pieceArray[4][0] = ccConfig.black_king;
        ccConfig.pieceArray[5][0] = ccConfig.black_minister2;
        ccConfig.pieceArray[6][0] = ccConfig.black_elephant2;
        ccConfig.pieceArray[7][0] = ccConfig.black_horse2;
        ccConfig.pieceArray[8][0] = ccConfig.black_rook2;
        ccConfig.pieceArray[1][2] = ccConfig.black_cannon1;
        ccConfig.pieceArray[7][2] = ccConfig.black_cannon2;
        ccConfig.pieceArray[0][3] = ccConfig.black_solider1;
        ccConfig.pieceArray[2][3] = ccConfig.black_solider2;
        ccConfig.pieceArray[4][3] = ccConfig.black_solider3;
        ccConfig.pieceArray[6][3] = ccConfig.black_solider4;
        ccConfig.pieceArray[8][3] = ccConfig.black_solider5;


    }

    @Override
    public void Process(Player player1, Player player2, ChessPieces chess) {

    }

    @Override
    public Boolean End(ChessPieces chessPieces) {
        return null;
    }


    @Override
    public boolean win(int x, int y, boolean start) {
        return false;
    }

    @Override
    public boolean check(int position_X, int position_Y) {
        return false;
    }

    @Override
    public void GoBack() {

    }

    public Boolean check(CCChessPieces chessPieces, Point from, Point to) {
        switch (ccConfig.arrayList.get(chessPieces)) {
            case "黑将":
            case "红帅":
                // 一次只能走一格
                if (1 != Math.abs(to.x - from.x) + Math.abs(to.y - from.y)) {
                    return false;
                }
                // 必须在 3 * 3营地
                if (to.x > 5 || to.x < 3) {
                    return false;
                }
                // 此处默认 Place 的 y 在 [0,9] 之间。
                if (chessPieces.getPart()) {
                    return to.y >= 7;
                } else {
                    return to.y <= 2;
                }
            case "红士1":
            case "红士2":
            case "黑士1":
            case "黑士2":
                // 斜着走
                if (1 != Math.abs(to.x - from.x) || 1 != Math.abs(to.y - from.y)) {
                    return false;
                }
                // 必须在 3 * 3营地
                if (to.x > 5 || to.x < 3) {
                    return false;
                }
                if (chessPieces.getPart()) {
                    return to.y >= 7;
                } else {
                    return to.y <= 2;
                }


        }

        return null;
    }
}
