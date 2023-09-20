package ChessGames.ChineseChess;

import ChessGames.ChineseChess.AI.CCGetAI;
import ChessGames.ChineseChess.AI.StepBean;
import ChessGames.template.ChessPieces;
import ChessGames.template.FirstPlayer;

import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

public class CCFirstPlayer extends FirstPlayer {
    private CCConfig config;
    private CCGetAI getAI;

    public CCFirstPlayer(CCConfig config) {
        super(config);
        this.config = config;
    }

    @Override
    public void play(Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        switch (config.firstPlayer) {
            case Man:
                System.out.println("我是先手进来了！");
                break;
            case AI:
                getAI = new CCGetAI(config, 1);
                System.out.println("我获取到了AI");
                StepBean stepBean = getAI.play(config.secondAI);
                from = stepBean.from;
                to = stepBean.to;
                break;
        }
        System.out.println(from.x);
        System.out.println(from.y);
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
