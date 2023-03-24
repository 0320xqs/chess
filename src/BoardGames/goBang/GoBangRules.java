package BoardGames.goBang;

public class GoBangRules implements GoBangConfig {
    private int[][] Begin;
    public int[][] GetBegin(){
        Begin=new int[ROW][COLUMN];



       return Begin;
    }

    public Boolean Process(GoBangChessPieces pieces){

        return true;
    }

    public Boolean End(){

        return true;
    }
}
