package ChessGames.ChineseChess;


import ChessGames.ChineseChess.AI.CCGetAI;
import ChessGames.ChineseChess.AI.StepBean;
import ChessGames.template.ChessPieces;
import ChessGames.template.SecondPlayer;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class CCSecondPlayer extends SecondPlayer {
    private CCConfig config;
    private CCGetAI getAI;

    public CCSecondPlayer(CCConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        switch (config.secondPlayer) {
            case Man:
                System.out.println("我是后手进来了！");
                break;
            case AI:
                getAI = new CCGetAI(config, 1);
                System.out.println("我获取到了AI");
//                StepBean stepBean = getAI.play(config.secondAI);
                JSONObject jsonObject = getAI.play(config.secondAI);
                JSONArray jsonArray = (JSONArray) jsonObject.get("next");
                from = new Point((int)jsonArray.get(0),(int)jsonArray.get(1));
                to = new Point((int)jsonArray.get(2),(int)jsonArray.get(3));
                System.out.println("cccc"+from.x+" "+from.y);
                System.out.println("cccc"+to.x+" "+to.y);
                break;
        }
        final CCChessPieces fromPiece = (CCChessPieces) config.pieceArray[from.x][from.y];
        CCChessPieces eatenPiece = (CCChessPieces) config.pieceArray[to.x][to.y];
        Objects.requireNonNull(fromPiece, "找不到移动的棋子");
        //存入走棋记录list
        config.pieceList.add(new ChessPieces(from.x,from.y));
        config.pieceList.add(new ChessPieces(to.x,to.y));
        System.out.println(config.pieceList);
        // 判断是否是吃子, 如果棋子被吃掉, 则将棋子移动列表
        if (eatenPiece != null) {
            config.eatenList.add(eatenPiece);
            System.out.println("我吃了"+eatenPiece.getChessRole());
            config.pieceArray[to.x][to.y] = null;
        }else {
            config.eatenList.add(null);
        }
        // 更改棋盘数组
        config.pieceArray[from.x][from.y] = null;
        config.pieceArray[to.x][to.y] = fromPiece;
        //更改棋子内部坐标
        config.pieceArray[to.x][to.y].setX_coordinate(to.x);
        config.pieceArray[to.x][to.y].setY_coordinate(to.y);
        System.out.println("改变完数组了！From:"+from.x+" "+from.y+" to:"+to.getX()+" "+to.getY());
    }
}
