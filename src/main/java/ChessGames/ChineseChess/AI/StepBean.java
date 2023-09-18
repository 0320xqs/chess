package ChessGames.ChineseChess.AI;


import java.awt.*;

/**
 * <b>Description : </b> 记录要走的一步棋
 */
public class StepBean {

    /**
     * 对应棋盘坐标对象池
     */
    private static final StepBean[][][][] stepBeanPool;

    static {
        // 初始化即开始建立棋盘上所有的位置坐标对象
        stepBeanPool = new StepBean[9][10][9][10];
        for (int x = 0; x < 9; x++) {
            for (int y = 0; y < 10; y++) {
                for (int j = 0; j < 9; j++) {
                    for (int k = 0; k < 10; k++) {
                        stepBeanPool[x][y][j][k] = new StepBean(new Point(x, y), new Point(j, k));
                    }
                }
            }
        }
    }

    public final Point from;
    public final Point to;

    public StepBean(Point from, Point to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @param fx 棋盘 from棋子 x坐标(从0-9)
     * @param fy 棋盘 from棋子 y坐标(从0-10)
     * @param tx 棋盘 to棋子 x坐标(从0-9)
     * @param ty 棋盘 to棋子 y坐标(从0-10)
     * @return 对应棋盘坐标对象
     */
    public static StepBean of(int fx, int fy, int tx, int ty) {
        return stepBeanPool[fx][fy][tx][ty];
    }

    /**
     * @param from 棋盘 from棋子 位置
     * @param to   棋盘 from棋子 位置
     * @return 对应棋盘坐标对象
     */
    public static StepBean of(Point from, Point to) {
        return stepBeanPool[from.x][from.y][to.x][to.y];
    }

    @Override
    public String toString() {
        return "StepBean{" + from + " -> " + to + '}';
    }

}
