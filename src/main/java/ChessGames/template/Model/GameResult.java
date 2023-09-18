package ChessGames.template.Model;

public enum GameResult {
    NOTSTARTED("对局尚未开始"),
    UNFINISHED("对局尚未结束"),
    DRAW("平局"),
    SECONDWIN("后手获胜"),
    FIRSTWIN("先手获胜");
    String result;

    GameResult(String result) {
        this.result = result;
    }

    public String getResult() {
        return result;
    }
}
