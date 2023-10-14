package ChessGames.ChineseChess.AI;

import ChessGames.ChineseChess.CCChessPieces;
import ChessGames.ChineseChess.CCConfig;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.ArrayList;
import java.util.Random;

public class MinMax {
    CCConfig ccConfig;
    int role;
    MinMax(CCConfig config, int role){
        this.ccConfig = config;
        this.role = role;
    }
    public JSONObject play(){
        System.out.println("我AI教你");
        CCChessPieces[][]  pieceArray = new CCChessPieces[9][10];//避免模拟走棋不断刷新，此处建立副本进行AI走棋思考
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                pieceArray[i][j] = (CCChessPieces) ccConfig.pieceArray[i][j];
             }
        }
        System.out.println(Integer.parseInt(ccConfig.minMinDepth));
        AlphaBeta alphaBeta = new AlphaBeta();
        ArrayList<StepBean> list = new ArrayList(alphaBeta.getEvaluatedPlace(pieceArray,ccConfig.currentPlayer,Integer.parseInt(ccConfig.minMinDepth),null));
        int randomIndex = new Random().nextInt(list.size());
        StepBean bestPlace = list.get(randomIndex);
        System.out.println("最终选取的最佳："+bestPlace);
        //封装结果
        JSONArray jsonElements = new JSONArray();
        jsonElements.add(bestPlace.from.x);
        jsonElements.add(bestPlace.from.y);
        jsonElements.add(bestPlace.to.x);
        jsonElements.add(bestPlace.to.y);
        JSONObject next = new JSONObject();
        next.put("next",jsonElements);
        return next;
//        return bestPlace;
    }
}
