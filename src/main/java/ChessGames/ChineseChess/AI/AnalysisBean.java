package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCChessPieces;
import ChessGames.ChineseChess.Model.ChessRole;
import ChessGames.template.Model.Part;
import Util.ArrayUtils;


import java.awt.*;
import java.util.Set;
import java.util.function.Predicate;


/**
 * <b>Description : </b> 用于ai算法的分析运算, 相当于为运算而做的一个副本
 */
public class AnalysisBean {
    public final CCChessPieces[][] pieces;
    /**
     * 红方 King 位置
     */
    private Point redKing;
    /**
     * 黑方 King 位置
     */
    private Point blackKing;
    /**
     * 红方棋子数量
     */
    private int redPieceNum;
    /**
     * 黑方棋子数量
     */
    private int blackPieceNum;
    /**
     * 红方棋子存在总分值(不计算King的分值)
     */
    private int redPieceExistScore;
    /**
     * 黑方棋子存在总分值(不计算King的分值)
     */
    private int blackPieceExistScore;
    /**
     * 双方形势分值总和(红方为正,黑方为负)
     */
    private int pieceScore;

    public AnalysisBean(CCChessPieces[][] rawPieceArrays) {
        this.pieces = rawPieceArrays;
        redPieceExistScore = 0;
        blackPieceExistScore = 0;
        redPieceNum = 0;
        blackPieceNum = 0;
        // 找出king, 和两方棋子数量
        for (int x = 0, xLen = rawPieceArrays.length; x < xLen; x++) {
            CCChessPieces[] pieceArr = rawPieceArrays[x];
            for (int y = 0, yLen = pieceArr.length; y < yLen; y++) {
                CCChessPieces piece = pieceArr[y];
                if (piece != null) {
                    if (Part.FIRST == piece.getChessRole().getPart()) {
                        redPieceNum++;
                        if (piece.getChessRole() == ChessRole.RED_KING) {
                            redKing = new Point(x, y);
                        } else {
                            redPieceExistScore += piece.getChessRole().getPieceScore().existScore;
                        }
                    } else {
                        blackPieceNum++;
                        if (piece.getChessRole() == ChessRole.BLACK_KING) {
                            blackKing = new Point(x, y);
                        } else {
                            blackPieceExistScore += piece.getChessRole().getPieceScore().existScore;
                        }
                    }
                }
            }
        }
        // 计算分值
        this.pieceScore = calcPieceScore(rawPieceArrays);
    }

    /**
     * 计算棋盘评估分数
     */
    public static int calcPieceScore(final CCChessPieces[][] pieces) {
        int num = 0;
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                CCChessPieces piece = pieces[x][y];
                if (piece == null) {
                    continue;
                }
                if (piece.getChessRole().getPart() == Part.FIRST) {
                    num += piece.getChessRole().getPieceScore().placeScores[x * 10 + 9 - y];
                    num += piece.getChessRole().getPieceScore().existScore;
                } else {
                    num -= piece.getChessRole().getPieceScore().placeScores[x * 10 + y];
                    num -= piece.getChessRole().getPieceScore().existScore;
                }
            }
        }
        return num;
    }

    /**
     * 获取 一步（只进攻, 不防守） 可以相对增多的分值, 红方为正, 黑方为负
     *
     * @param from from 位置
     * @param to   to 位置
     * @return 一步（只进攻, 不防守） 可以相对增多的分值
     */
    public int nextStepOpportunityCost(final Point from, final Point to) {
        DebugInfo.incrementAlphaBetaTime();
        // 临时分值
        int invScr = 0;
        final CCChessPieces eatenPiece = pieces[to.x][to.y];
        if (eatenPiece != null) {
            // 若是将棋, 则更新KING子的位置
            // 若被吃的棋子是红方, 则 - 被吃掉的棋子的存在值, 若是黑方则相反.
            if (eatenPiece.getChessRole().getPart() == Part.FIRST) {
                invScr -= eatenPiece.getChessRole().getPieceScore().existScore;
                invScr -= eatenPiece.getChessRole().getPieceScore().getPlaceScore(Part.FIRST, to.x, to.y);
            } else {
                invScr += eatenPiece.getChessRole().getPieceScore().existScore;
                invScr += eatenPiece.getChessRole().getPieceScore().getPlaceScore(Part.SECOND, to.x, to.y);
            }
            // 更新分数
        }
        final CCChessPieces movePiece = pieces[from.x][from.y];
        // 如果是红方, 则 + 移动之后的棋子存在值, - 移动之前的棋子存在值, 若是黑方则相反.
        if (Part.FIRST == movePiece.getChessRole().getPart()) {
            invScr += movePiece.getChessRole().getPieceScore().getPlaceScore(Part.FIRST, to.x, to.y);
            invScr -= movePiece.getChessRole().getPieceScore().getPlaceScore(Part.FIRST, from.x, from.y);
            return invScr;
        } else {
            invScr -= movePiece.getChessRole().getPieceScore().getPlaceScore(Part.SECOND, to.x, to.y);
            invScr += movePiece.getChessRole().getPieceScore().getPlaceScore(Part.SECOND, from.x, from.y);
            return -invScr;
        }
    }

    /**
     * 模拟走棋
     */
    public int goForward(Point from, Point to, CCChessPieces eatenPiece) {
        final CCChessPieces movePiece = pieces[from.x][from.y];
        pieces[to.x][to.y] = movePiece;
        pieces[from.x][from.y] = null;
        if (movePiece.getChessRole() == ChessRole.BLACK_KING || movePiece.getChessRole() == ChessRole.RED_KING) {
            updateKingPlace(movePiece.getChessRole().getPart(), to);
        }
        // 临时分值
        int invScr = 0;
        // 如果是红方, 则 + 移动之后的棋子存在值, - 移动之前的棋子存在值, 若是黑方则相反.
        if (Part.FIRST == movePiece.getChessRole().getPart()) {
            invScr += movePiece.getChessRole().getPieceScore().getPlaceScore(Part.FIRST, to.x, to.y);
            invScr -= movePiece.getChessRole().getPieceScore().getPlaceScore(Part.FIRST, from.x, from.y);
        } else {
            invScr -= movePiece.getChessRole().getPieceScore().getPlaceScore(Part.SECOND, to.x, to.y);
            invScr += movePiece.getChessRole().getPieceScore().getPlaceScore(Part.SECOND, from.x, from.y);
        }
        if (eatenPiece != null) {
            // 若是将棋, 则更新KING子的位置
            // 若被吃的棋子是红方, 则 - 被吃掉的棋子的存在值, 若是黑方则相反.
            final PieceScore pScore = eatenPiece.getChessRole().getPieceScore();
            final int existScore = pScore.existScore;
            final int pieceCount = redPieceNum + blackPieceNum;
            if (eatenPiece.getChessRole().getPart() == Part.FIRST) {
                redPieceExistScore -= existScore;
                redPieceNum--;
                invScr -= existScore;
                invScr -= pScore.getPlaceScore(Part.FIRST, to.x, to.y);
                /*
                 * 加成分数: 棋子存在值 * (当前方棋子存在总分值 / 双方棋子存在总分值) * 0.5
                 * 1. 与别人换子是不利的, AI 下子尽量保守些.
                 * 2. 相对来讲, 如果自己的棋子子力和比较多, 那么换子是有利的, 如果自己的棋子子力和比较少, 那么换子是不利的.
                 * eg: 假如 red: 2000, black: 3000, 场上12个棋子, 此时 此时红方 kill 黑方 200, 此时可以获得 200 * 0.25 * 2000 / ( 2000 + 3000 ) = 20 分
                 * 之后 red: 2000, black: 2800, 场上11个棋子, 此时黑方再 kill 红方 200, 此时 200 * 0.25 * 2800 / ( 2000 + 2800 ) = 29 分
                 */
                invScr -= (existScore * redPieceExistScore / (redPieceExistScore + blackPieceExistScore)) >> 3;
                // 2. 如果损失的是馬(后期马越来越重要), 前期马有负分数加成, 后期马有正分数加成
                if (eatenPiece.getChessRole() == ChessRole.BLACK_HORSE || eatenPiece.getChessRole() == ChessRole.RED_HORSE) {
                    invScr -= (16 - pieceCount) << 1;
                }
            } else {
                blackPieceExistScore -= existScore;
                blackPieceNum--;
                invScr += existScore;
                invScr += pScore.getPlaceScore(Part.SECOND, to.x, to.y);
                /* 该部分原理同上 */
                invScr += (existScore * redPieceExistScore / (redPieceExistScore + blackPieceExistScore)) >> 3;
                if (eatenPiece.getChessRole() == ChessRole.BLACK_HORSE || eatenPiece.getChessRole() == ChessRole.RED_HORSE) {
                    invScr += (16 - pieceCount) << 1;
                }
            }
            // 更新分数
        }
        pieceScore += invScr;
        // 测试, 去掉上面的加成分数, 则下面的检查成立
//        DebugInfo.checkScoreDynamicCalc(pieces, pieceScore);
        DebugInfo.incrementAlphaBetaTime();
        return invScr;
    }

    /**
     * 模拟后退
     */
    public void backStep(Point from, Point to, CCChessPieces eatenPiece, int tmpScore) {
        System.out.println("模拟后退_from："+from);
        System.out.println("模拟后退_to："+to);
        if (eatenPiece != null){
            System.out.println("模拟后退_eatenPiece："+eatenPiece.getChessRole());
        }
        final CCChessPieces movePiece = pieces[to.x][to.y];
        pieces[from.x][from.y] = movePiece;
        pieces[to.x][to.y] = eatenPiece;
        // 退回上一步
        if (movePiece.getChessRole() == ChessRole.BLACK_KING || movePiece.getChessRole() == ChessRole.RED_KING) {
            updateKingPlace(movePiece.getChessRole().getPart(), from);
        }
        if (eatenPiece != null) {
            if (eatenPiece.getChessRole().getPart() == Part.FIRST) {
                redPieceExistScore += eatenPiece.getChessRole().getPieceScore().existScore;
                redPieceNum++;
            } else {
                blackPieceExistScore += eatenPiece.getChessRole().getPieceScore().existScore;
                blackPieceNum++;
            }
        }
        // 更新分值
        pieceScore -= tmpScore;
    }

    /**
     * 返回对本方的实力评估, 本方为正
     *
     * @param curPart 当前走棋方
     * @return 当前走棋方的实力评估
     */
    public int getCurPartEvaluateScore(Part curPart) {
        if (Part.FIRST == curPart) {
            return pieceScore;
        } else {
            return -pieceScore;
        }
    }

    /**
     * 返回棋盘上某一方的棋子数量
     */
    public int getPieceCount(Part curPart) {
        return curPart == Part.FIRST ? redPieceNum : blackPieceNum;
    }

    /**
     * @return 棋盘还有多少棋子
     */
    public int getPieceNum() {
        return redPieceNum + blackPieceNum;
    }

    /**
     * @param coordinate 棋盘位置
     * @return 对应棋盘位置的棋子对象
     */
    public CCChessPieces getPiece(Point coordinate) {
        return pieces[coordinate.x][coordinate.y];
    }

    /**
     * 获取对方King的位置
     */
    public Point getOppoKingPlace(Part curPart) {
        return curPart == Part.FIRST ? blackKing : redKing;
    }

    /**
     * @param part          更新 KING 棋子的位置
     * @param newCoordinate 新位置
     */
    public void updateKingPlace(Part part, Point newCoordinate) {
        if (part == Part.FIRST) {
            redKing = newCoordinate;
        } else {
            blackKing = newCoordinate;
        }
    }

    /**
     * @return king棋子移动后 是否为面对面
     */
    public boolean kingF2FAfterKingMove(Part curPart, Point curNextCoordinate) {
        Point oppCoordinate = curPart == Part.FIRST ? blackKing : redKing;
        // 若两个 king 棋子坐标不一致, 则直接返回 false
        if (curNextCoordinate.x != oppCoordinate.x) {
            return false;
        }
        return ArrayUtils.nullInMiddle(pieces[curNextCoordinate.x], curNextCoordinate.y, oppCoordinate.y);
    }

    /**
     * @return true : 两个 king 面对面, 且中间只有 place 一个棋子
     */
    public boolean isKingF2FAndWithOnlyThePlaceInMiddle(Point coordinate) {
        // 如果 两个 king 不是面对面 或者 当前位置不在两个king中间, 则直接返回false
        if (redKing.x != blackKing.x || coordinate.x != redKing.x || coordinate.y > redKing.y || coordinate.y < blackKing.y) {
            return false;
        }
        return ArrayUtils.oneInMiddle(pieces[redKing.x], redKing.y, blackKing.y);
    }

    /**
     * @return true : 当前位置在 两个king 中间(包含king的位置)
     */
    public boolean isKingF2FAndWithThePlaceInMiddle(Point coordinate) {
        return redKing.x == blackKing.x && coordinate.x == redKing.x && coordinate.y <= redKing.y && coordinate.y >= blackKing.y;
    }

    /**
     * 检查一步走棋后会不会造成 King 面对面 (当前 part 方, 从 from 走到 to)
     *
     * @param piece 当前走棋棋子
     * @param from  from
     * @param to    to
     * @return true: 符合规则, false: 不符合规则
     */
    public boolean isKingF2FAfterStep(CCChessPieces piece, Point from, Point to) {
        if (piece.getChessRole() == ChessRole.BLACK_KING || piece.getChessRole() == ChessRole.RED_KING) {
            return !kingF2FAfterKingMove(piece.getChessRole().getPart(), to);
        } else {
            return !isKingF2FAndWithOnlyThePlaceInMiddle(from) || isKingF2FAndWithThePlaceInMiddle(to);
        }
    }

    /**
     * AI 计算 curPart 方若是走一步棋是否能够吃掉对方的 KING
     */
   public boolean canEatKingAfterOneAIStep(Part part) {
       final Set<StepBean> nextStepAgainEvalPlace = AlphaBeta.getEvaluatedPlace(pieces, part, 1, null);
        // 计算后的步骤中, 是否存在能吃掉 KING 的一步
        for (StepBean stepBean : nextStepAgainEvalPlace) {
            final CCChessPieces piece = getPiece(stepBean.to);
            if (piece != null && (piece.getChessRole() == ChessRole.BLACK_KING || piece.getChessRole() == ChessRole.RED_KING)) {
                return true;
            }
        }
        return false;
    }


    public boolean simulateOneStep(StepBean stepBean, Predicate<AnalysisBean> predicate) {
        final CCChessPieces eatenPiece = getPiece(stepBean.to);
        if (eatenPiece != null && (eatenPiece.getChessRole() == ChessRole.BLACK_KING || eatenPiece.getChessRole() == ChessRole.RED_KING)) {
            throw new IllegalStateException(eatenPiece.getChessRole() + " 被吃掉了, 无法继续执行下去");

        }
        // 模拟走棋
        // 攻守兼备的走法
        final int invScr = goForward(stepBean.from, stepBean.to, eatenPiece);
        // 评分
        final boolean test = predicate.test(this);
        // 退回上一步
        backStep(stepBean.from, stepBean.to, eatenPiece, invScr);
        return test;
    }

    /**
     * AI计算后, part 走一步棋之后 是否能够避免对方下一步吃掉自己的 KING
     *
     * @param part 当前方
     */
    public boolean canAvoidBeEatKingAfterOneAIStep(Part part) {
        final Set<StepBean> nextStepAgainEvalPlace = AlphaBeta.getEvaluatedPlace(pieces, part, 2, null);
        // 计算后的步骤中, 是否存在能吃掉 KING 的一步
        for (StepBean stepBean : nextStepAgainEvalPlace) {
            final CCChessPieces eatenPiece = getPiece(stepBean.to);
            // 如果是 KING角色被吃掉, 则需要跳出循环或继续下一步循环
            if (eatenPiece != null && (eatenPiece.getChessRole() == ChessRole.BLACK_KING || eatenPiece.getChessRole() == ChessRole.RED_KING)) {
                // 如果吃掉的是对方的 KING, 表示可以避免本方 KING 被吃掉
                if (eatenPiece.getChessRole().getPart() == part) {
                    continue;
                } else {
                    return true;
                }
            }
            // 如果 stepBean 走完之后, 对方无法吃掉自己的 KING, 则返回true
            if (!simulateOneStep(stepBean, bean -> bean.canEatKingAfterOneAIStep(Part.Exchange(part)))) {
                return true;
            }
        }
        return false;
    }
    /**
     * @return king 是否为面对面
     */
    public MyList<Point> filterPlace(MyList<Point> coordinates) {
        return coordinates.filter(item -> {
            Point it = (Point) item;
            return it.x == redKing.x && it.y <= redKing.y && it.y >= blackKing.y;
        });
    }

}
