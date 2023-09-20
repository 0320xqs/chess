package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCChessPieces;
import ChessGames.ChineseChess.CCConfig;
import ChessGames.ChineseChess.CCRules;
import ChessGames.ChineseChess.Model.ChessRole;
import ChessGames.template.Model.Part;
import ChessGames.ChineseChess.CCUtil.ListPool;
import com.github.cosycode.common.ext.bean.DoubleBean;

import java.awt.*;
import java.util.HashSet;
import java.util.Set;

/**
 * <b>Description : </b> AI ?????????????
 * <p>
 * <br>???????????????????????????????????????????????????????????????????????????????????????????????????
 * <br>????????????Max??????????????????????????????????????Min??????????????С???????
 * <br>
 * <br>??Negamax???????????AlphaBeta?е??????????????????????????е??
 * <br>??Minimax????AlphaBeta???У????????????????? 100???????????????????100??
 * <br>????Negamax????AlphaBeta???У????????????????? 100??????????????????-100??
 **/
public class AlphaBeta {

    private static final int MAX = 100_000_000;
    /**
     * ???????? Min + Max = 0, ??????????????????????·???????
     */
    private static final int MIN = -MAX;

    /**
     * ????????????, ??????????????
     *
     * @param pieceNum ????????
     * @return ?????????????
     */
    public static int searchDeepSuit(final int pieceNum) {
        // ????????????, ??????????????

        if (pieceNum > 20) {
            return -2;
        } else if (pieceNum <= 4) {
            return 4;
        } else if (pieceNum <= 8) {
            return 2;
        }

        return 0;
    }

    /**
     * ?????????б??????????????λ, ??? deep > 2 ??????????????????.
     *
     * @param analysisBean ???????????
     * @param curPart      ??????巽
     * @param deep         ???????
     * @return ??????????λ????
     */
    private static MyList<StepBean> geneNestStepPlaces(final AnalysisBean analysisBean, final Part curPart, final int deep) {
        final CCChessPieces[][] pieces = analysisBean.pieces;
        // ??????
        MyList<StepBean> stepBeanList = ListPool.localPool().getAStepBeanList();
        for (int x = 0; x < CCConfig.COLS; x++) {
            for (int y = 0; y < CCConfig.ROWS; y++) {
                final CCChessPieces fromPiece = pieces[x][y];
                if (fromPiece != null && fromPiece.chessRole.getPart() == curPart) {
                    final Point from = new Point(x, y);
                    // TO DO 获取每个棋子的候选步
//                    final MyList<Point> list = fromPiece.chessRole.find(analysisBean, curPart, from);
                    final MyList<Point> list = CCRules.find(analysisBean, curPart, from);
//                    System.out.println("find"+fromPiece.getChessRole()+"的候选步："+list);
//                    for (int i = 0; i < list.size(); i++) {
//                        System.out.println(list.get(i).x+" "+list.get(i).y);
//                    }
                    if (list.isEmpty()) {
                        ListPool.localPool().addListToPool(list);
                        continue;
                    }
                    final Object[] elementData = list.eleTemplateDate();
                    for (int i = 0, len = list.size(); i < len; i++) {
                        stepBeanList.add(StepBean.of(from, (Point) elementData[i]));
                    }
                    ListPool.localPool().addListToPool(list);
                }
            }
        }
        // 是否排序, 如果搜索深度大于2, 则对结果进行排序
        // 排序后的结果, 进入极大极小值搜索算法时, 容易被剪枝.
        if (deep > 2) {
            orderStep(analysisBean, stepBeanList, curPart);
        }

        return stepBeanList;
    }

    /**
     * ?? ??λ?б? ????????, ???????λ?б?, ??????С????????, ????????.
     *
     * @param analysisBean ???????????
     * @param stepBeanList ??????????λ?б?
     * @param curPart      ??????巽
     */
    private static void orderStep(final AnalysisBean analysisBean, final MyList<StepBean> stepBeanList, final Part curPart) {
        final CCChessPieces[][] srcPieces = analysisBean.pieces;
        // ?????????????????????ó???
        MyList<DoubleBean<Integer, StepBean>> bestPlace = ListPool.localPool().getADoubleBeanList();
        // ???????
        final Part oppositeCurPart = Part.Exchange(curPart);
        int best = MIN;

        final Object[] objects = stepBeanList.eleTemplateDate();
        for (int i = 0; i < stepBeanList.size(); i++) {
            final StepBean item = (StepBean) objects[i];
            final Point to = item.to;
            // ????
            final CCChessPieces eatenPiece = srcPieces[to.x][to.y];
            int score;
            // ?ж???????
            if (eatenPiece != null && (eatenPiece.chessRole == ChessRole.BLACK_KING || eatenPiece.chessRole == ChessRole.RED_KING )) {
                score = MAX;
            } else {
                // ????
                final int invScr = analysisBean.goForward(item.from, to, eatenPiece);
                DebugInfo.incrementAlphaBetaOrderTime();
                // ????
                score = negativeMaximumWithNoCut(analysisBean, oppositeCurPart, -best);
//                score = negativeMaximum(analysisBean, oppositeCurPart, -best);
                // ????????
                analysisBean.backStep(item.from, to, eatenPiece, invScr);
            }
            // ???????????е????
            bestPlace.add(new DoubleBean<>(score, item));
            if (score > best) { // ??????????????????????λ????????
                best = score;
            }
        }
        /* ?????? */
        // ???????????????, ??????Ч????????
        bestPlace.sort((o1, o2) -> o2.getO1() - o1.getO1());

        stepBeanList.clear();
        bestPlace.forEach(dou -> stepBeanList.add(dou.getO2()));

        ListPool.localPool().addListToDoubleBeanListPool(bestPlace);
    }


    /**
     * ?????????????(?????????)
     *
     * @param analysisBean ???????????
     * @param curPart      ??????巽
     * @return ???????????????????
     */
    private static int negativeMaximumWithNoCut(AnalysisBean analysisBean, Part curPart, int alphaBeta) {
        // 1. ?????????????
        final CCChessPieces[][] pieces = analysisBean.pieces;
        int best = MIN;
        // 2. ?????????б??????????????б?
        MyList<StepBean> stepBeanList = geneNestStepPlaces(analysisBean, curPart, 1);

        final Object[] objects = stepBeanList.eleTemplateDate();
        for (int i = 0, len = stepBeanList.size(); i < len; i++) {
            final StepBean item = (StepBean) objects[i];
            Point from = item.from;
            Point to = item.to;
            // ????
            CCChessPieces eatenPiece = pieces[to.x][to.y];
            int score;
            // ?ж???????
            if (eatenPiece != null && (eatenPiece.chessRole == ChessRole.BLACK_KING || eatenPiece.chessRole == ChessRole.BLACK_KING)) {
                score = MAX;
            } else {
                // ????
                final int invScr = analysisBean.goForward(from, to, eatenPiece);
                DebugInfo.incrementAlphaBetaOrderTime();
                score = analysisBean.getCurPartEvaluateScore(curPart);
                // ????????
                analysisBean.backStep(from, to, eatenPiece, invScr);
            }
            if (score > best) { // ?????????????????·???
                best = score;
            }
            if (score > alphaBeta) { // alpha???
                break;
            }
        }
        ListPool.localPool().addListToStepBeanListPool(stepBeanList);
        return -best;
    }


    /**
     * 奇数层是电脑(max层)thisSide, 偶数层是human(min层)otherSide
     *
     * @param srcPieces  棋盘
     * @param curPart    当前走棋方
     * @param deep       搜索深度
     * @param forbidStep 禁止的步骤(长捉或长拦)
     * @return 下一步的位置
     */
    public static Set<StepBean> getEvaluatedPlace(final CCChessPieces[][] srcPieces, final Part curPart, final int deep, final StepBean forbidStep) {
        // 1. 初始化各个变量
        final AnalysisBean analysisBean = new AnalysisBean(srcPieces);
        // 2. 下一步list
        MyList<StepBean> stepBeanList = geneNestStepPlaces(analysisBean, curPart, deep);
        // 3. 去除不该下的步
        stepBeanList.remove(forbidStep);
        for (int i = 0; i < stepBeanList.size(); i++) {
            System.out.println("下一步列表_from："+stepBeanList.get(i).from.x+" "+stepBeanList.get(i).from.y);
            System.out.println("下一步列表_to："+stepBeanList.get(i).to.x+" "+stepBeanList.get(i).to.y);
        }
        // 进入循环之前计算好循环内使用常量
        Set<StepBean> bestPlace = new HashSet<>();
        int best = MIN;
        // 对方棋手
        final Part oppositeCurPart = Part.Exchange(curPart);
        // 下一深度
        final int nextDeep = deep - 1;
        System.out.println("size : {"+stepBeanList.size()+"}, content: {"+stepBeanList+"}");
//        log.debug("size : {}, content: {}", stepBeanList.size(), stepBeanList);
        final Object[] objects = stepBeanList.eleTemplateDate();
        for (int i = 0, len = stepBeanList.size(); i < len; i++) {
            StepBean item = (StepBean) objects[i];
            final Point to = item.to;
            // 备份
            final CCChessPieces eatenPiece = srcPieces[to.x][to.y];
            int score;
            // 判断是否胜利
            if (eatenPiece != null && (eatenPiece.chessRole == ChessRole.RED_KING || eatenPiece.chessRole == ChessRole.BLACK_KING)) {
                // 步数越少, 分值越大
                score = MAX + deep;
            } else {
                // ????
                final int invScr = analysisBean.goForward(item.from, to, eatenPiece);
                // ????
                if (deep <= 1) {
                    score = analysisBean.getCurPartEvaluateScore(curPart);
                } else {
                    System.out.println(analysisBean.pieces);
                    System.out.println(oppositeCurPart);
                    System.out.println(nextDeep);
                    score = negativeMaximum(analysisBean, oppositeCurPart, nextDeep, -best);
                }
                // ????????
                analysisBean.backStep(item.from, to, eatenPiece, invScr);
            }
            if (score == best) { // 找到相同的分数, 就添加这一步
                bestPlace.add(item);
            }
            if (score > best) { // 找到一个更好的分，就把以前存的位子全部清除
                best = score;
                bestPlace.clear();
                bestPlace.add(item);
            }
        }
        ListPool.end();
        ListPool.localPool().addListToStepBeanListPool(stepBeanList);
        System.out.println("最佳有："+bestPlace.size()+"个");
//        Iterator<StepBean> bestItertor = bestPlace.iterator();
//        while (bestItertor.hasNext()){
//            System.out.println("最佳："+bestItertor.next().from.getX()+" "+bestItertor.next().from.getY()+","+bestItertor.next().to.getX()+" "+bestItertor.next().to.getY());
//        }
        for (StepBean place : bestPlace) {
            System.out.println("最佳："+place);
        }
        return bestPlace;
    }

    /**
     * ?????????????
     *
     * @param analysisBean ???????????
     * @param curPart      ??????巽
     * @param deep         ???????
     * @param alphaBeta    alphaBeta ??????
     * @return ???????????????????
     */
    private static int negativeMaximum(AnalysisBean analysisBean, Part curPart, int deep, int alphaBeta) {
        // 1. ?????????????
        final CCChessPieces[][] pieces = analysisBean.pieces;
        int best = MIN;
        // ???????
        final Part oppositeCurPart = Part.Exchange(curPart);
        // ??????
        final int nextDeep = deep - 1;
        // 2. ?????????б??????????????б?
        System.out.println(nextDeep);
        final MyList<StepBean> stepBeanList = geneNestStepPlaces(analysisBean, curPart, deep);

        final Object[] objects = stepBeanList.eleTemplateDate();
        for (int i = 0, len = stepBeanList.size(); i < len; i++) {
            final StepBean item = (StepBean) objects[i];
            Point from = item.from;
            Point to = item.to;
            // ????
            CCChessPieces eatenPiece = pieces[to.x][to.y];
            int score;
            // ?ж???????
            if (eatenPiece != null && (eatenPiece.chessRole == ChessRole.BLACK_KING || eatenPiece.chessRole == ChessRole.RED_KING)) {
                // ???????, ??????
                score = MAX + deep;
            } else {
                // ????
                final int invScr = analysisBean.goForward(from, to, eatenPiece);
                // ????
                if (deep <= 1) {
                    score = analysisBean.getCurPartEvaluateScore(curPart);
                } else {
                    score = negativeMaximum(analysisBean, oppositeCurPart, nextDeep, -best);
                }
                // ????????
                analysisBean.backStep(from, to, eatenPiece, invScr);
            }
            if (score > best) { // ?????????????????·???
                best = score;
            }
            if (score > alphaBeta) { // alpha???
                break;
            }
        }
        ListPool.localPool().addListToStepBeanListPool(stepBeanList);
        return -best;
    }

}
