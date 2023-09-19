package ChessGames.GoBang;

import ChessGames.template.Config;
import lombok.Data;

@Data
public class GoBangConfig extends Config {

    public static int ROWS = 15;//棋盘行数
    public static int COLS = 15;//棋盘列数

//    public int minMaxDepth = 4;

    public GoBangConfig() {

    }

    public GoBangConfig(int ROWS, int COLS) {
        super(ROWS, COLS);
    }
}
