package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCChessPieces;
import ChessGames.ChineseChess.CCConfig;

import java.util.ArrayList;
import java.util.Random;

public class Minimax {
    CCConfig ccConfig;
    int role;
    Minimax(CCConfig config, int role){
        this.ccConfig = config;
        this.role = role;
    }
    public StepBean play(){
        System.out.println("我AI教你");
        CCChessPieces[][]  pieceArray = new CCChessPieces[9][10];//避免模拟走棋不断刷新，此处建立副本进行AI走棋思考
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                pieceArray[i][j] = ccConfig.pieceArray[i][j];
             }
        }
        ArrayList<StepBean> list = new ArrayList(AlphaBeta.getEvaluatedPlace(pieceArray,ccConfig.currentPlayer,2,null));
        int randomIndex = new Random().nextInt(list.size());
        StepBean bestPlace = list.get(randomIndex);
        System.out.println("最终选取的最佳："+bestPlace);
        return bestPlace;
    }
}
