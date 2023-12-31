package ChessGames.ChineseChess.AI;


import ChessGames.template.Model.Part;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.ToString;

import java.util.Arrays;

/**
 * <b>Description : </b>
 * <p>
 *    <br/> 1. 每个棋子本身的价值
 *    <br/> 2. 棋子的地理评分
 *    <br/> 3. 写了一方的，另一方的直接转换一下
 * </p>
 **/
@ToString()
public enum PieceScore {

//原
//评估函数1
//KING(10000, new int[]{
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        1, -8, -9, 0, 0, 0, 0, 0, 0, 0,
//        5, -8, -9, 0, 0, 0, 0, 0, 0, 0,
//        1, -8, -9, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//        0, 0, 0, 0, 0, 0, 0, 0, 0, 0
//}),
//    MINISTER(200, new int[]{
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 3, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0
//    }),
//    ELEPHANT(200, new int[]{
//            0, 0, -2, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 3, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
//            0, 0, -2, 0, 0, 0, 0, 0, 0, 0
//    }),
//    ROOK(950, new int[]{
//            -6, 5, -2, 4, 8, 8, 6, 6, 6, 6,
//            6, 8, 8, 9, 12, 11, 13, 8, 12, 8,
//            4, 6, 4, 4, 12, 11, 13, 7, 9, 7,
//            12, 12, 12, 12, 14, 14, 16, 14, 16, 13,
//            0, 0, 12, 14, 15, 15, 16, 16, 33, 14,
//            12, 12, 12, 12, 14, 14, 16, 14, 16, 13,
//            4, 6, 4, 4, 12, 11, 13, 7, 9, 7,
//            6, 8, 8, 9, 12, 11, 13, 8, 12, 8,
//            -6, 5, -2, 4, 8, 8, 6, 6, 6, 6
//    }),
//    CANNON(450, new int[]{
//            0, 0, 1, 0, -1, 0, 0, 1, 2, 4,
//            0, 1, 0, 0, 0, 0, 3, 1, 2, 4,
//            1, 2, 4, 0, 3, 0, 3, 0, 0, 0,
//            3, 2, 3, 0, 0, 0, 2, -5, -4, -5,
//            3, 2, 5, 0, 4, 4, 4, -4, -7, -6,
//            3, 2, 3, 0, 0, 0, 2, -5, -4, -5,
//            1, 2, 4, 0, 3, 0, 3, 0, 0, 0,
//            0, 1, 0, 0, 0, 0, 3, 1, 2, 4,
//            0, 0, 1, 0, -1, 0, 0, 1, 2, 4
//    }),
//    HORSE(450, new int[]{
//            0, -3, 5, 4, 2, 2, 5, 4, 2, 2,
//            -3, 2, 4, 6, 10, 12, 20, 10, 8, 2,
//            2, 4, 6, 10, 13, 11, 12, 11, 15, 2,
//            0, 5, 7, 7, 14, 15, 19, 15, 9, 8,
//            2, -10, 4, 10, 15, 16, 12, 11, 6, 2,
//            0, 5, 7, 7, 14, 15, 19, 15, 9, 8,
//            2, 4, 6, 10, 13, 11, 12, 11, 15, 2,
//            -3, 2, 4, 6, 10, 12, 20, 10, 8, 2,
//            0, -3, 5, 4, 2, 2, 5, 4, 2, 2
//    }),
//    SOLDIER(130, new int[]{
//            0, 0, 0, -1, 3, 8, 13, 15, 18, 1,
//            0, 0, 0, 0, 0, 17, 22, 28, 36, 3,
//            0, 0, 0, -3, 7, 18, 90, 42, 56, 9,
//            0, 0, 0, 0, 0, 21, 42, 73, 95, 10,
//            0, 0, 0, 3, 8, 26, 52, 80, 118, 12,
//            0, 0, 0, 0, 0, 21, 42, 73, 95, 10,
//            0, 0, 0, -3, 7, 18, 90, 42, 56, 9,
//            0, 0, 0, 0, 0, 17, 22, 28, 36, 3,
//            0, 0, 0, -1, 3, 8, 13, 15, 18, 1
//    });
//
//评估函数2
    KING(10000, new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            11, 2, 1, 0, 0, 0, 0, 0, 0, 0,
            15, 2, 1, 0, 0, 0, 0, 0, 0, 0,
            11, 2, 1, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    }),
    MINISTER(200, new int[]{
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            20, 0, 20, 0, 0, 0, 0, 0, 0, 0,
            0, 23, 0, 0, 0, 0, 0, 0, 0, 0,
            20, 0, 20, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0
    }),
    ELEPHANT(200, new int[]{
            0, 0, 18, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            20, 0, 0, 0,20, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 23, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            20, 0, 0, 0,20, 0, 0, 0, 0, 0,
            0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
            0, 0, 18, 0, 0, 0, 0, 0, 0, 0
    }),
    HORSE(450, new int[]{
            88, 85, 93, 92, 90, 90, 93, 92, 90, 90,
            85, 90, 92, 94, 98, 100, 108, 98, 96, 90,
            90, 92, 94, 98, 101, 99, 100, 99, 103, 90,
            88, 93, 95, 95, 102, 103, 107, 103, 97, 96,
            90, 78, 92, 98, 103, 104, 100, 99, 94, 90,
            88, 93, 95, 95, 102, 103, 107, 103, 97, 96,
            90, 92, 94, 98, 101, 99, 100, 99, 103, 90,
            85, 90, 92, 94, 98, 100, 108, 98, 96, 90,
            88, 85, 93, 92, 90, 90, 93, 92, 90, 90,
    }),
    ROOK(900, new int[]{
            194, 200, 198, 204, 208, 208, 206, 206, 206, 206,
            206, 208, 208, 209, 212, 211, 213, 208, 212, 208,
            204, 206, 204, 204, 212, 211, 213, 207, 209, 207,
            212, 212, 212, 212, 214, 214, 216, 214, 216, 213,
            200, 200, 212, 214, 215, 215, 216, 216, 233, 214,
            212, 212, 212, 212, 214, 214, 216, 214, 216, 213,
            204, 206, 204, 204, 212, 211, 213, 207, 209, 207,
            209, 208, 208, 209, 212, 211, 213, 208, 212, 208,
            194, 200, 198, 204, 208, 208, 206, 206, 206, 206
    }),
    CANNON(400, new int[]{
            96, 96, 97, 96, 95, 96, 96, 97, 98, 100,
            96, 97, 96, 96, 96, 96, 99, 97, 98, 100,
            97, 98, 100, 96, 99, 96, 99, 96, 96, 96,
            99, 98, 100, 96, 96, 96, 98, 91, 92, 91,
            99, 98, 101, 96, 100, 100, 100, 92, 89, 90,
            99, 98, 100, 96, 96, 96, 98, 91, 92, 91,
            97, 98, 100, 96, 99, 96, 99, 96, 96, 96,
            96, 97, 96, 96, 96, 96, 99, 97, 98, 100,
            96, 96, 97, 96, 95, 96, 96, 97, 98, 100,
    }),
    SOLDIER(100, new int[]{
            0, 0, 0, 7, 7, 14, 19, 19, 19, 9,
            0, 0, 0, 0, 0, 18, 23, 24, 24, 9,
            0, 0, 0, 7, 13, 20, 27, 32, 34, 9,
            0, 0, 0, 0, 0, 27, 29, 37, 42, 11,
            0, 0, 0, 15, 16, 29, 30, 37, 44, 13,
            0, 0, 0, 0, 0, 27, 29, 37, 42, 11,
            0, 0, 0, 7, 13, 20, 27, 32, 34, 9,
            0, 0, 0, 0, 0, 18, 23, 24, 24, 9,
            0, 0, 0, 7, 7, 14, 19, 19, 19, 9,
    });
    /**
     * 存在分
     */
    public final int existScore;

    /**
     * 占位分数组
     */
    final int[] placeScores;

    PieceScore(int existScore, int[] placeScores) {
        this.existScore = existScore;
        this.placeScores = placeScores;
    }

    /**
     * @param part 棋盘方
     * @param x    坐标x
     * @param y    坐标y
     * @return 占位分数
     */
    public int getPlaceScore(Part part, int x, int y) {
        if (Part.FIRST == part) {
            return placeScores[x * 10 + 9 - y];
        } else {
            return placeScores[x * 10 + y];
        }
    }

}
