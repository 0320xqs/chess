package ChessGames.ChineseChess;

import ChessGames.ChineseChess.AI.AnalysisBean;
import ChessGames.ChineseChess.AI.MyList;
import ChessGames.ChineseChess.Model.ChessRole;
import ChessGames.ChineseChess.Model.*;

import ChessGames.template.Model.GameResult;
import ChessGames.template.Model.Part;
import ChessGames.template.Player;
import Util.ArrayUtils;
import ChessGames.template.ChessRules;
import Util.ListPool;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.List;


public class CCRules extends ChessRules {
    int eatenNum;
    public static CCChessPieces eatenPiece;

    private CCConfig ccConfig;

    public CCRules(CCConfig ccConfig) {
        this.ccConfig = ccConfig;
    }

    @Override
    public void GetBegin() {
        // 添加红色棋子
        ccConfig.pieceArray[0][9] = new CCChessPieces(ChessRole.RED_ROOK, 0, 9);
        ccConfig.pieceArray[1][9] = new CCChessPieces(ChessRole.RED_HORSE, 1, 9);
        ccConfig.pieceArray[2][9] = new CCChessPieces(ChessRole.RED_ELEPHANT, 2, 9);
        ccConfig.pieceArray[3][9] = new CCChessPieces(ChessRole.RED_MINISTER, 3, 9);
        ccConfig.pieceArray[4][9] = new CCChessPieces(ChessRole.RED_KING, 4, 9);
        ccConfig.pieceArray[5][9] = new CCChessPieces(ChessRole.RED_MINISTER, 5, 9);
        ccConfig.pieceArray[6][9] = new CCChessPieces(ChessRole.RED_ELEPHANT, 6, 9);
        ccConfig.pieceArray[7][9] = new CCChessPieces(ChessRole.RED_HORSE, 7, 9);
        ccConfig.pieceArray[8][9] = new CCChessPieces(ChessRole.RED_ROOK, 8, 9);
        ccConfig.pieceArray[1][7] = new CCChessPieces(ChessRole.RED_CANNON, 1, 7);
        ccConfig.pieceArray[7][7] = new CCChessPieces(ChessRole.RED_CANNON, 7, 7);
        ccConfig.pieceArray[0][6] = new CCChessPieces(ChessRole.RED_SOLDIER, 0, 6);
        ccConfig.pieceArray[2][6] = new CCChessPieces(ChessRole.RED_SOLDIER, 2, 6);
        ccConfig.pieceArray[4][6] = new CCChessPieces(ChessRole.RED_SOLDIER, 4, 6);
        ccConfig.pieceArray[6][6] = new CCChessPieces(ChessRole.RED_SOLDIER, 6, 6);
        ccConfig.pieceArray[8][6] = new CCChessPieces(ChessRole.RED_SOLDIER, 8, 6);
        // 添加黑色棋子
        ccConfig.pieceArray[0][0] = new CCChessPieces(ChessRole.BLACK_ROOK, 0, 0);
        ccConfig.pieceArray[1][0] = new CCChessPieces(ChessRole.BLACK_HORSE, 1, 0);
        ccConfig.pieceArray[2][0] = new CCChessPieces(ChessRole.BLACK_ELEPHANT, 2, 0);
        ccConfig.pieceArray[3][0] = new CCChessPieces(ChessRole.BLACK_MINISTER, 3, 0);
        ccConfig.pieceArray[4][0] = new CCChessPieces(ChessRole.BLACK_KING, 4, 0);
        ccConfig.pieceArray[5][0] = new CCChessPieces(ChessRole.BLACK_MINISTER, 5, 0);
        ccConfig.pieceArray[6][0] = new CCChessPieces(ChessRole.BLACK_ELEPHANT, 6, 0);
        ccConfig.pieceArray[7][0] = new CCChessPieces(ChessRole.BLACK_HORSE, 7, 0);
        ccConfig.pieceArray[8][0] = new CCChessPieces(ChessRole.BLACK_ROOK, 8, 0);
        ccConfig.pieceArray[1][2] = new CCChessPieces(ChessRole.BLACK_CANNON, 1, 2);
        ccConfig.pieceArray[7][2] = new CCChessPieces(ChessRole.BLACK_CANNON, 7, 2);
        ccConfig.pieceArray[0][3] = new CCChessPieces(ChessRole.BLACK_SOLDIER, 0, 3);
        ccConfig.pieceArray[2][3] = new CCChessPieces(ChessRole.BLACK_SOLDIER, 2, 3);
        ccConfig.pieceArray[4][3] = new CCChessPieces(ChessRole.BLACK_SOLDIER, 4, 3);
        ccConfig.pieceArray[6][3] = new CCChessPieces(ChessRole.BLACK_SOLDIER, 6, 3);
        ccConfig.pieceArray[8][3] = new CCChessPieces(ChessRole.BLACK_SOLDIER, 8, 3);
    }

    @Override
    public void Process(Player player1, Player player2, Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        if (from != null){
            if (!check(from, to)){//检测不通过
                System.out.println("不符合棋规，检测不通过！");
                ccConfig.checkFlag = false;
                return;
            }
            System.out.println("检测通过!"+ccConfig.currentPlayer+from.getX()+" "+from.getY()+" "+to.getX()+" "+to.getY());
            ccConfig.checkFlag = true;
            eatenPiece = ccConfig.pieceArray[to.x][to.y];
        }
        switch (ccConfig.currentPlayer) {
            case SECOND:
                player2.play(from, to);
                break;
            case FIRST:
                player1.play(from, to);
                break;
        }
        if (!End(from, to)) {
            System.out.println("没结束！");
            System.out.println("交换角色结束，之前是："+ccConfig.currentPlayer);
            ccConfig.currentPlayer = ccConfig.currentPlayer.Exchange(ccConfig.currentPlayer);
            System.out.println("交换角色结束，现在是："+ccConfig.currentPlayer);
        }
    }

    @Override
    public Boolean End(Point from, Point to) {
//        CCChessPieces eatenPiece = ccConfig.pieceArray[to.x][to.y];
        if (win(from, to)){
            System.out.println(ccConfig.gameResult+"赢了");
            return true;
        }
        //和棋:1、当双方棋子中无过河棋子时判定和棋
//           2、当250步棋之内没有发生吃子判定和棋
        boolean isDraw = true;
//        打印当前棋盘中活着的棋子
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                {
                    CCChessPieces piece = ccConfig.pieceArray[i][j];
                    if (piece != null && (piece.getChessRole().equals(ChessRole.RED_ROOK) || piece.getChessRole().equals(ChessRole.RED_HORSE) || piece.getChessRole().equals(ChessRole.RED_CANNON) || piece.getChessRole().equals(ChessRole.RED_SOLDIER)
                            || piece.getChessRole().equals(ChessRole.BLACK_ROOK) || piece.getChessRole().equals(ChessRole.BLACK_HORSE) || piece.getChessRole().equals(ChessRole.BLACK_CANNON) || piece.getChessRole().equals(ChessRole.BLACK_SOLDIER))) {
                        isDraw = false;
                        break;
                    } else {
                        continue;
                    }
                }
            }
            if (eatenPiece != null) {
                eatenNum = 0;
            } else {
                eatenNum++;
                if (eatenNum == 250) {
//                判定和棋
                    isDraw = true;
                }
            }
            if (isDraw) {
                ccConfig.gameResult = GameResult.DRAW;

                return true;
            }
        }
        return false;
    }

    @Override
    public boolean win(Point from, Point to) {
//        CCChessPieces eatenPiece = ccConfig.pieceArray[to.x][to.y];
        System.out.println("判断是否赢");
        // 判断是否吃掉了对方的 King
        if (eatenPiece != null && (eatenPiece.getChessRole() == ChessRole.RED_KING || eatenPiece.getChessRole() == ChessRole.BLACK_KING)) {
            if (ccConfig.currentPlayer == Part.SECOND) {
                ccConfig.gameResult = GameResult.SECONDWIN;
            } else {
                ccConfig.gameResult = GameResult.FIRSTWIN;
            }
            return true;
        }
        return false;
    }


    @Override
    public boolean check(Point from, Point to) {
        Boolean result = false;
        System.out.println("检测from："+from.getX()+" "+from.getY());
        System.out.println("检测to："+to.getX()+" "+to.getY());
        CCChessPieces a = ccConfig.pieceArray[from.x][from.y];
        switch (a.getChessRole()) {
            case BLACK_KING:
            case RED_KING:
                // 一次只能走一格
                if (1 != Math.abs(to.x - from.x) + Math.abs(to.y - from.y)) {
                    result = false;
                    break;
                }
                // 必须在 3 * 3营地
                if (to.x > 5 || to.x < 3) {
                    result = false;
                    break;
                }
                // 此处默认 Place 的 y 在 [0,9] 之间。
                if (a.getChessRole().getPart() == Part.FIRST) {
                    result = to.y >= 7;
                } else {
                    result = to.y <= 2;
                }
                break;
            case RED_MINISTER:
            case BLACK_MINISTER:
                // 斜着走
                if (1 != Math.abs(to.x - from.x) || 1 != Math.abs(to.y - from.y)) {
                    result = false;
                    break;
                }
                // 必须在 3 * 3营地
                if (to.x > 5 || to.x < 3) {
                    result = false;
                    break;
                }
                if (a.getChessRole().getPart() == Part.FIRST) {
                    result = to.y >= 7;
                } else {
                    result = to.y <= 2;
                }
                break;
            case RED_ELEPHANT:
            case BLACK_ELEPHANT:
                int xSub = to.x - from.x;
                int ySub = to.y - from.y;
                // 斜着走2步, 象眼处无棋子
                if (2 != Math.abs(xSub) || 2 != Math.abs(ySub) || ccConfig.pieceArray[from.x + xSub / 2][from.y + ySub / 2] != null) {
                    result = false;
                    break;
                }
                // 必须在本营地
                if (a.getChessRole().getPart() == Part.FIRST) {
                    result = to.y >= 5;
                    break;
                } else {
                    result = to.y <= 4;
                }
                break;
            case RED_HORSE:
            case BLACK_HORSE:
                // 斜着走2步
                xSub = to.x - from.x;
                ySub = to.y - from.y;
                // 为日字, 且道路通畅
                if ((Math.abs(xSub) == 2 && Math.abs(ySub) == 1)) {
                    result = ccConfig.pieceArray[from.x + (xSub / 2)][from.y] == null;
                    break;
                }
                if ((Math.abs(ySub) == 2 && Math.abs(xSub) == 1)) {
                    result = ccConfig.pieceArray[from.x][from.y + (ySub / 2)] == null;
                    break;
                }
                break;
            case RED_ROOK:
            case BLACK_ROOK:
                // 直走且道路畅通
                if (to.x == from.x && to.y != from.y) {
                    result = ArrayUtils.nullInMiddle(ccConfig.pieceArray[from.x], to.y, from.y);
                }
                if (to.x != from.x && to.y == from.y) {
                    result = ArrayUtils.nullInMiddle(ccConfig.pieceArray, to.y, to.x, from.x);
                }
                break;
            case RED_CANNON:
            case BLACK_CANNON:
                if (from == to) {
                    result = false;
                    break;
                }
                if (ccConfig.pieceArray[to.x][to.y] == null) {
                    if (to.x == from.x) {
                        result = ArrayUtils.nullInMiddle(ccConfig.pieceArray[from.x], to.y, from.y);
                        break;
                    }
                    if (to.y == from.y) {
                        result = ArrayUtils.nullInMiddle(ccConfig.pieceArray, to.y, to.x, from.x);
                        break;
                    }
                } else {
                    if (to.x == from.x) {
                        result = ArrayUtils.oneInMiddle(ccConfig.pieceArray[from.x], to.y, from.y);
                        break;
                    }
                    if (to.y == from.y) {
                        result = ArrayUtils.oneInMiddle(ccConfig.pieceArray, to.y, to.x, from.x);
                        break;
                    }
                }
                break;
            case RED_SOLDIER:
            case BLACK_SOLDIER:
                // 一次只能走一格
                if (1 != Math.abs(to.x - from.x) + Math.abs(to.y - from.y)) {
                    result = false;
                    break;
                }
                if (a.getChessRole().getPart() == Part.FIRST) {
                    // 不能后退
                    if (to.y > from.y) {
                        System.out.println(a.getChessRole().getPart());
                        System.out.println(to.y > from.y);
                        result = false;
                        break;
                    }
                    // 过河前不能左右走
                    result = from.y < 5 || to.x == from.x;
                } else {
                    // 不能后退
                    if (to.y < from.y) {
                        System.out.println(a.getChessRole().getPart());
                        System.out.println(to.y < from.y);
                        result = false;
                        break;
                    }
                    // 过河前不能左右走
                    result = from.y > 4 || to.x == from.x;
                }
                break;
        }

        // 如果走棋后, 导致两个 KING 对面, 则返回
        if (isKingF2F(a, from, to)) {
            result = false;
        }
        return result;
    }


    @Override
    public void GoBack() {

    }

    Boolean isKingF2F(CCChessPieces curFromPiece, Point from, Point to) {

        Point blackKing = null, redKing = null;
        int flag = 0;
        for (int i = 0; i < 9; i++) {
            ok:
            for (int j = 0; j < 10; j++) {
                if (ccConfig.pieceArray[i][j] != null && ccConfig.pieceArray[i][j].getChessRole().equals(ChessRole.BLACK_KING) ){
                    blackKing = new Point(i, j);
                    flag ++;
                }
                if (ccConfig.pieceArray[i][j] != null && ccConfig.pieceArray[i][j].getChessRole().equals(ChessRole.RED_KING)){
                    redKing = new Point(i, j);
                    flag ++;
                }
                if (flag == 2)break ok;
            }
        }

        //如果移动的棋子是王
        if (ChessRole.BLACK_KING == curFromPiece.getChessRole() || ChessRole.RED_KING == curFromPiece.getChessRole()) {
            Point oppCoordinate = ccConfig.currentPlayer == Part.FIRST ? blackKing : redKing;
            // 若两个 king 棋子坐标不一致, 则直接返回 false
            if (to.x != oppCoordinate.x) {
                return false;
            }
            //王棋移动后中间没有棋子，则返回true
            return ArrayUtils.nullInMiddle(ccConfig.pieceArray[to.x], to.y, oppCoordinate.y);
        } else {
            // 如果 两个 king 不是面对面 或者 当前位置不在两个king中间, 则直接返回false
            if (redKing.x != blackKing.x || from.x != redKing.x || from.y > redKing.y || from.y < blackKing.y) {
                return false;
            }
            //当两个king面对面，且中间唯一的子是要移动的子,且移动后位置不在两个king中间,返回false
            if (ArrayUtils.oneInMiddle(ccConfig.pieceArray[redKing.x], redKing.y, blackKing.y))
                if (redKing.x == blackKing.x && to.x != redKing.x)
                    return true;
            return false;

        }
    }
    /**
     * 指定角色可走位置
     */
    public static MyList<Point> find(CCChessPieces[][] pieces, Part part, Point coordinate){
        MyList<Point> list = ListPool.localPool().getAPlaceList(17);
        int xInit;
        int yInit;
        int x;
        int y;
        int yCenter;
        switch (pieces[coordinate.x][coordinate.y].getChessRole()) {
            case BLACK_KING:
            case RED_KING:
                AnalysisBean analysisBean = new AnalysisBean(pieces);
                x = coordinate.x;
                y = coordinate.y;
                // x轴移动
                Point oppoKingCoordinate = analysisBean.getOppoKingPlace(part);
                if (x == 4) {
                    // 对方king x坐标为3, 且移动后双 king 不会对面
                    if (checkPlace(pieces[3][y], part) >= 0 && !(oppoKingCoordinate.x == 3 && analysisBean.kingF2FAfterKingMove(part, new Point(3, y)))) {
                        list.add(new Point(3, y));
                    }
                    if (checkPlace(pieces[5][y], part) >= 0 && !(oppoKingCoordinate.x == 5 && analysisBean.kingF2FAfterKingMove(part, new Point(5, y)))) {
                        list.add(new Point(5, y));
                    }
                } else {
                    if (checkPlace(pieces[4][y], part) >= 0 && !(oppoKingCoordinate.x == 4 && analysisBean.kingF2FAfterKingMove(part, new Point(4, y)))) {
                        list.add(new Point(4,y));
                    }
                }
                if (Part.FIRST == part) {
                    if (y == 8) {
                        if (checkPlace(pieces[x][7], part) >= 0) {
                            list.add(new Point(x,7));
                        }
                        if (checkPlace(pieces[x][9], part) >= 0) {
                            list.add(new Point(x,9));
                        }
                    } else {
                        if (checkPlace(pieces[x][8], part) >= 0) {
                            list.add(new Point(x,8));
                        }
                    }
                } else {
                    if (y == 1) {
                        if (checkPlace(pieces[x][0], part) >= 0) {
                            list.add(new Point(x,0));
                        }
                        if (checkPlace(pieces[x][2], part) >= 0) {
                            list.add(new Point(x, 2));
                        }
                    } else {
                        if (checkPlace(pieces[x][1], part) >= 0) {
                            list.add(new Point(x, 1));
                        }
                    }
                }
                return list;
            case RED_MINISTER:
            case BLACK_MINISTER:
                x = coordinate.x;
                yCenter = Part.FIRST == part ? 8 : 1;
                if (x != 4) {
                    if (checkPlace(pieces[4][yCenter], part) >= 0) {
                        final MyList<Point> coordinates = ListPool.localPool().getAPlaceList(1);
                        coordinates.add(new Point(4, yCenter));
                        return coordinates;
                    }
                    return ListPool.getEmptyList();
                }
                yCenter += 1;
                if (checkPlace(pieces[3][yCenter], part) >= 0) {
                    list.add(new Point(3, yCenter));
                }
                if (checkPlace(pieces[5][yCenter], part) >= 0) {
                    list.add(new Point(5, yCenter));
                }
                yCenter -= 2;
                if (checkPlace(pieces[3][yCenter], part) >= 0) {
                    list.add(new Point(3, yCenter));
                }
                if (checkPlace(pieces[5][yCenter], part) >= 0) {
                    list.add(new Point(5, yCenter));
                }
                return list;
            case RED_ELEPHANT:
            case BLACK_ELEPHANT:
                x = coordinate.x;
                y = coordinate.y;
                yCenter = Part.FIRST == part ? 7 : 2;
                if (x == 0) {
                    if (pieces[1][yCenter - 1] == null && checkPlace(pieces[2][yCenter - 2], part) >= 0) {
                        list.add(new Point(2, yCenter - 2));
                    }
                    if (pieces[1][yCenter + 1] == null && checkPlace(pieces[2][yCenter + 2], part) >= 0) {
                        list.add(new Point(2, yCenter + 2));
                    }
                } else if (x == 2) {
                    int yEye = (y + yCenter) / 2;
                    if (pieces[1][yEye] == null && checkPlace(pieces[0][yCenter], part) >= 0) {
                        list.add(new Point(0, yCenter));
                    }
                    if (pieces[3][yEye] == null && checkPlace(pieces[4][yCenter], part) >= 0) {
                        list.add(new Point(4, yCenter));
                    }
                } else if (x == 4) {
                    int yEye = yCenter + 1;
                    int yTmp = yCenter + 2;
                    if (pieces[3][yEye] == null && checkPlace(pieces[2][yTmp], part) >= 0) {
                        list.add(new Point(2, yTmp));
                    }
                    if (pieces[5][yEye] == null && checkPlace(pieces[6][yTmp], part) >= 0) {
                        list.add(new Point(6, yTmp));
                    }
                    yEye = yCenter - 1;
                    yTmp = yCenter - 2;
                    if (pieces[3][yEye] == null && checkPlace(pieces[2][yTmp], part) >= 0) {
                        list.add(new Point(2, yTmp));
                    }
                    if (pieces[5][yEye] == null && checkPlace(pieces[6][yTmp], part) >= 0) {
                        list.add(new Point(6, yTmp));
                    }
                } else if (x == 6) {
                    int yEye = (y + yCenter) / 2;
                    if (pieces[5][yEye] == null && checkPlace(pieces[4][yCenter], part) >= 0) {
                        list.add(new Point(4, yCenter));
                    }
                    if (pieces[7][yEye] == null && checkPlace(pieces[8][yCenter], part) >= 0) {
                        list.add(new Point(8, yCenter));
                    }
                } else if (x == 8) {
                    if (pieces[7][yCenter - 1] == null && checkPlace(pieces[6][yCenter - 2], part) >= 0) {
                        list.add(new Point(6, yCenter - 2));
                    }
                    if (pieces[7][yCenter + 1] == null && checkPlace(pieces[6][yCenter + 2], part) >= 0) {
                        list.add(new Point(6, yCenter + 2));
                    }
                }
                return list;
            case RED_HORSE:
            case BLACK_HORSE:
                xInit = coordinate.x;
                yInit = coordinate.y;
                if (xInit > 1 && pieces[xInit - 1][yInit] == null) {
                    if (yInit > 0) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit - 2, yInit - 1));
                    }
                    if (yInit < 9) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit - 2, yInit + 1));
                    }
                }
                if (xInit < 7 && pieces[xInit + 1][yInit] == null) {
                    if (yInit > 0) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit + 2, yInit - 1));
                    }
                    if (yInit < 9) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit + 2, yInit + 1));
                    }
                }
                if (yInit > 1 && pieces[xInit][yInit - 1] == null) {
                    if (xInit > 0) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit - 1, yInit - 2));
                    }
                    if (xInit < 8) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit + 1, yInit - 2));
                    }
                }
                if (yInit < 8 && pieces[xInit][yInit + 1] == null) {
                    if (xInit > 0) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit - 1, yInit + 2));
                    }
                    if (xInit < 8) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit + 1, yInit + 2));
                    }
                }
                return list;
            case RED_ROOK:
            case BLACK_ROOK:
                xInit = coordinate.x;
                yInit = coordinate.y;
                for (x = xInit - 1; x >= 0; x--) {
                    CCChessPieces chessPiece = pieces[x][yInit];
                    if (chessPiece == null) {
                        list.add(new Point(x, yInit));
                        continue;
                    }
                    if (chessPiece.getChessRole().getPart() != part) {
                        list.add(new Point(x, yInit));
                    }
                    break;
                }
                for (x = xInit + 1; x < 9; x++) {
                    CCChessPieces chessPiece = pieces[x][yInit];
                    if (chessPiece == null) {
                        list.add(new Point(x, yInit));
                        continue;
                    }
                    if (chessPiece.getChessRole().getPart() != part) {
                        list.add(new Point(x, yInit));
                    }
                    break;
                }
                for (y = yInit - 1; y >= 0; y--) {
                    CCChessPieces chessPiece = pieces[xInit][y];
                    if (chessPiece == null) {
                        list.add(new Point(xInit, y));
                        continue;
                    }
                    if (chessPiece.getChessRole().getPart() != part) {
                        list.add(new Point(xInit, y));
                    }
                    break;
                }
                for (y = yInit + 1; y <= 9; y++) {
                    CCChessPieces chessPiece = pieces[xInit][y];
                    if (chessPiece == null) {
                        list.add(new Point(xInit, y));
                        continue;
                    }
                    if (chessPiece.getChessRole().getPart() != part) {
                        list.add(new Point(xInit, y));
                    }
                    break;
                }
                return list;
            case RED_CANNON:
            case BLACK_CANNON:
                xInit = coordinate.x;
                yInit = coordinate.y;
                boolean kong = false;
                for (x = xInit - 1; x >= 0; x--) {
                    CCChessPieces chessPiece = pieces[x][yInit];
                    if (kong) {
                        if (chessPiece == null) {
                            continue;
                        }
                        if (chessPiece.getChessRole().getPart() != part) {
                            list.add(new Point(x, yInit));
                        }
                        break;
                    } else {
                        if (chessPiece == null) {
                            list.add(new Point(x, yInit));
                            continue;
                        }
                        kong = true;
                    }
                }
                kong = false;
                for (x = xInit + 1; x < 9; x++) {
                    CCChessPieces chessPiece = pieces[x][yInit];
                    if (kong) {
                        if (chessPiece == null) {
                            continue;
                        }
                        if (chessPiece.getChessRole().getPart() != part) {
                            list.add(new Point(x, yInit));
                        }
                        break;
                    } else {
                        if (chessPiece == null) {
                            list.add(new Point(x, yInit));
                            continue;
                        }
                        kong = true;
                    }
                }
                kong = false;
                for (y = yInit - 1; y >= 0; y--) {
                    CCChessPieces chessPiece = pieces[xInit][y];
                    if (kong) {
                        if (chessPiece == null) {
                            continue;
                        }
                        if (chessPiece.getChessRole().getPart() != part) {
                            list.add(new Point(xInit, y));
                        }
                        break;
                    } else {
                        if (chessPiece == null) {
                            list.add(new Point(xInit, y));
                            continue;
                        }
                        kong = true;
                    }
                }
                kong = false;
                for (y = yInit + 1; y < 10; y++) {
                    CCChessPieces chessPiece = pieces[xInit][y];
                    if (kong) {
                        if (chessPiece == null) {
                            continue;
                        }
                        if (chessPiece.getChessRole().getPart() != part) {
                            list.add(new Point(xInit, y));
                        }
                        break;
                    } else {
                        if (chessPiece == null) {
                            list.add(new Point(xInit, y));
                            continue;
                        }
                        kong = true;
                    }
                }
                return list;
            case RED_SOLDIER:
            case BLACK_SOLDIER:
                xInit = coordinate.x;
                yInit = coordinate.y;
                if (part == Part.FIRST) {
                    if (yInit < 5) {
                        // 可以左右移动
                        if (xInit > 0) {
                            addPlaceIntoList(pieces, part, list, new Point(xInit - 1, yInit));
                        }
                        if (xInit < 8) {
                            addPlaceIntoList(pieces, part, list, new Point(xInit + 1, yInit));
                        }
                    }
                    if (yInit > 0) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit, yInit - 1));
                    }
                } else {
                    if (yInit > 4) {
                        // 可以左右移动
                        if (xInit > 0) {
                            addPlaceIntoList(pieces, part, list, new Point(xInit - 1, yInit));
                        }
                        if (xInit < 8) {
                            addPlaceIntoList(pieces, part, list, new Point(xInit + 1, yInit));
                        }
                    }
                    if (yInit < 9) {
                        addPlaceIntoList(pieces, part, list, new Point(xInit, yInit + 1));
                    }
                }
                return list;
        }
       return null;
    }
    public static MyList<Point> find(AnalysisBean analysisBean, Part curPart, Point from) {
        if (analysisBean.pieces[from.x][from.y].getChessRole() ==  ChessRole.BLACK_KING || analysisBean.pieces[from.x][from.y].getChessRole() ==  ChessRole.RED_KING) {
            return CCRules.find(analysisBean.pieces, curPart, from);
        } else {
            MyList<Point> coordinates = CCRules.find(analysisBean.pieces, curPart, from);
            if (analysisBean.isKingF2FAndWithOnlyThePlaceInMiddle(from)) {
                return analysisBean.filterPlace(coordinates);
            }
            return coordinates;
        }
    }
    public static int checkPlace(CCChessPieces chessPiece, Part part) {
        if (chessPiece == null) {
            return 0;
        }
        if (chessPiece.getChessRole().getPart() == part) {
            return -1;
        }
        return 1;
    }
    /**
     * 如果 part 方在 chessPiece 棋盘里面可以一步走到 place的位置, 则将 place 加入到 list 列表中
     *
     * @param chessPieces 棋盘
     * @param part        当前走棋方
     * @param list        列表
     * @param coordinate       位置
     */
    public static void addPlaceIntoList(CCChessPieces[][] chessPieces, Part part, List<Point> list, Point coordinate) {
        CCChessPieces piece = chessPieces[coordinate.x][coordinate.y];
        if (piece == null || piece.getChessRole().getPart() != part) {
            list.add(coordinate);
        }
    }
}
