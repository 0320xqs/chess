package BoardGames.goBang;

public class GoBangRules implements GoBangConfig {
    private int[][] Begin;

    public int[][] GetBegin(){
        Begin=new int[ROW][COLUMN];
       return Begin;
    }

    public Boolean Process(GoBangChessPieces pieces){
        if (board[pieces.getX_coordinate()][pieces.getY_coordinate()]==0)
        return true;
        return false;
    }

    public Boolean End(){

        return true;
    }
}
