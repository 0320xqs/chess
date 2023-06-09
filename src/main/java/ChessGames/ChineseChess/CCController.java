package ChessGames.ChineseChess;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Arrays;

import static ChessGames.ChineseChess.CCConfig.*;

public class CCController {
    private CCRules ccRules;
    private CCChessBoard ccChessBoard;
    private CCConfig ccConfig;

    private CCChessPieces curFromPiece;

    public CCController() {
        ccConfig = new CCConfig();
        ccChessBoard = new CCChessBoard(ccConfig);
        ccRules = new CCRules(ccConfig);
        ccRules.GetBegin();
        ccChessBoard.setPreferredSize(ccChessBoard.getPreferredSize());
    }

    public JPanel GetPanel() {
        return ccChessBoard;
    }


    class CClistener implements MouseListener {
        CCChessPieces fromPiece, toPiece;


        @Override
        public void mouseClicked(MouseEvent e) {

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            // 位置
       //     Point pointerCoordinate = convertLocationToPlace(e.getPoint());
       //     if (pointerCoordinate == null) {
       //         return;
       //     }
       //     if (ccConfig.GameOver != 0) {
       //         return;
       //     }
       //     // 当前走棋方
       //     //*@NonNull Part pointerPart = situation.getNextPart();
       //     // 当前焦点棋子
       //     CCChessPieces pointerPiece = ccConfig.pieceArray[pointerCoordinate.x][pointerCoordinate.y];
       //     // 通过当前方和当前位置判断是否可以走棋
       //     // step: form
       //     if (curFromPiece == null) {
       //         // 当前焦点位置有棋子且是本方棋子
       //         if (pointerPiece != null && pointerPiece.getPart() == ccConfig.currentPlayer) {
       //             // 本方棋子, 同时是from指向
       //             curFromPiece = pointerPiece;
       //             // 获取toList
       //             MyList<Coordinate> list = curFromPiece.piece.chessRole.find(new AnalysisBean(situation.generatePieces()), pointerPart, pointerCoordinate);
       //             final ListPool listPool = ListPool.localPool();
       //             listPool.addListToPool(list);
       //             return;
       //         }
       //         log.warn("warning -> from 焦点指示错误");
       //         return;
       //     }
       //     if (pointerCoordinate.equals(curFromPiece.getCoordinate())) {
       //         log.warn("false -> from == to");
       //         return;
       //     }
       //     // 当前焦点位置有棋子且是本方棋子
       //     if (pointerPiece != null && pointerPiece.piece.part == pointerPart) {
       //         assert curFromPiece.piece.part == pointerPart : "当前焦点位置有棋子且是本方棋子 之前指向了对方棋子";
       //         // 更新 curFromPiece
       //         curFromPiece = pointerPiece;
       //         traceMarker.setMarkFromPlace(pointerCoordinate);
       //         MyList<Coordinate> list = curFromPiece.piece.chessRole.find(new AnalysisBean(situation.generatePieces()), pointerPart, pointerCoordinate);
//     //       traceMarker.showMarkPlace(list);
       //         log.info("true -> 更新 curFromPiece");
       //         ListPool.localPool().addListToPool(list);
       //         return;
       //     }
       //     final StepBean stepBean = StepBean.of(curFromPiece.getCoordinate(), pointerCoordinate);
       //     // 如果不符合规则则直接返回
       //     final Piece[][] pieces = situation.generatePieces();
       //     if (!curFromPiece.piece.chessRole.rule.check(pieces, pointerPart, stepBean.from, stepBean.to)) {
       //         // 如果当前指向棋子是本方棋子
       //         log.warn("不符合走棋规则");
       //         return;
       //     }
       //     // 如果达成长拦或者长捉, 则返回
       //     final StepBean forbidStepBean = situation.getForbidStepBean();
       //     if (forbidStepBean != null && forbidStepBean.from == stepBean.from && forbidStepBean.to == stepBean.to) {
       //         log.warn("长拦或长捉");
       //         return;
       //     }
       //     AnalysisBean analysisBean = new AnalysisBean(pieces);
       //     // 如果走棋后, 导致两个 KING 对面, 则返回
       //     if (!analysisBean.isKingF2FAfterStep(curFromPiece.piece, stepBean.from, stepBean.to)) {
       //         log.warn("KING面对面");
       //         return;
       //     }
//
//
       //     // 当前棋子无棋子或者为对方棋子, 且符合规则, 可以走棋
       //     Object[] objects = new Object[]{stepBean.from, stepBean.to, PlayerType.Man};
       //     final boolean sendSuccess = Main_CC.context().getCommandExecutor().sendCommandWhenNotRun(CommandExecutor.CommandType.LocationPiece, objects);
       //     if (!sendSuccess) {
       //         log.warn("命令未发送成功: {} ==> {}", CommandExecutor.CommandType.LocationPiece, Arrays.toString(objects));
       //     }


        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }
    }

}

