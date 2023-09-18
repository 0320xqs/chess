package ChessGames.ChineseChess;


import ChessGames.ChineseChess.AI.GetAI;
import ChessGames.ChineseChess.AI.StepBean;
import ChessGames.template.FirstPlayer;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class SecondPLayer extends FirstPlayer {
    private CCConfig config;
    private GetAI getAI;

    public SecondPLayer(CCConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        switch (config.secondPlayer) {
            case Man:
                System.out.println("我是黑棋进来了！");
                break;
            case AI:
                getAI = new GetAI(config, 1);
                System.out.println("我获取到了AI");
                StepBean stepBean = getAI.play(config.blackAI);
                from = stepBean.from;
                to = stepBean.to;
                break;
        }
        final CCChessPieces fromPiece = config.pieceArray[from.x][from.y];
        CCRules.eatenPiece = config.pieceArray[to.x][to.y];
        Objects.requireNonNull(fromPiece, "找不到移动的棋子");
        // 判断是否是吃子, 如果棋子被吃掉, 则将棋子移动列表
        if (CCRules.eatenPiece != null) {
            System.out.println("我吃了"+CCRules.eatenPiece.getChessRole());
            config.pieceArray[to.x][to.y] = null;
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
