package ChessGames.GoBang;

import ChessGames.GoBang.AI.Minimax;
import ChessGames.template.ChessPieces;
import ChessGames.template.Player;

public class GoBangAI extends Player {
    Minimax AI;



    public GoBangAI(GoBangConfig board, int role, int depth) {
      AI=new Minimax(board,role,depth);

    }

    @Override
    public ChessPieces play(ChessPieces pieces){

        return AI.play(pieces);
    }





}

