package ChessGames.template.Model;

public enum Part {
    /**
     * 先手
     */
    FIRST,

    /**
     * 后手
     */
    SECOND;

    /**
     * 返回相反的势力
     */
    public static Part Exchange(Part part) {
        if (part == FIRST) {
            return SECOND;
        } else {
            return FIRST;
        }
    }
}