package ChessGames.template;

public abstract class Player {

    /**
     *  @Date 18:38 2023/4/12
     *  @Param 棋子ChessPieces
     *  @Descrition 对弈一方执行本回合操作
     *  @Return 棋子ChessPieces
     **/
    public abstract ChessPieces play(ChessPieces pieces);
}
