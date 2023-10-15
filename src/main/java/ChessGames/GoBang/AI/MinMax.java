package ChessGames.GoBang.AI;

import ChessGames.GoBang.GoBangChessPieces;
import ChessGames.GoBang.GoBangConfig;
import ChessGames.template.Model.Part;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.awt.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

import static ChessGames.GoBang.GoBangConfig.COLS;
import static ChessGames.GoBang.GoBangConfig.ROWS;

public class MinMax {

    public GoBangConfig Board;
    public int Role;
    public int Depth = 4;

    /**
     * AI分数
     */
    private int[][] computerScore;
    private int[][] computerScore_sort;
    LinkedList<ChessXY> chessXYList = new LinkedList<>();//空格得分排序，便于启发函数
    private static int comx, comy; // 电脑下子坐标
    private final int HUO = 1;
    private final int CHONG = 2;

    /**
     * 记录找到的分数一样的棋子，随机下这些棋子中的一个，以防步法固定
     */
    private ArrayList<ChessXY> chessList = new ArrayList<>();//候选棋子
    Random rand = new Random();
    int BOARD_SIZE;
    private int[][] board = new int[50][50];

    public MinMax(GoBangConfig board, int role) {
        Board = board;
        Role = role;
        Depth = Integer.parseInt(board.minMinDepth);
        this.BOARD_SIZE = board.ROWS;
        this.computerScore = new int[BOARD_SIZE][BOARD_SIZE];
        computerScore_sort = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < 50; i++) {
            for (int j = 0; j < 50; j++) {
                this.board[i][j] = 0;
            }
        }
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (((GoBangChessPieces) board.pieceArray[i][j]) != null){
                    if (((GoBangChessPieces) board.pieceArray[i][j]).getChessRole().getPart() == Part.FIRST)
                        this.board[i][j] = 1;
                    if (((GoBangChessPieces)board.pieceArray[i][j]).getChessRole().getPart() == Part.SECOND)
                        this.board[i][j] = 2;
                }else {
                    this.board[i][j] = 0;
                }
            }
        }
    }

    /**
     * @return
     * @Description 获取下一步棋子位置
     */
    public JSONObject play() {

        if (Board.pieceList.size() == 0) {
            JSONArray jsonElements = new JSONArray();
            jsonElements.add(BOARD_SIZE / 2);
            jsonElements.add(BOARD_SIZE / 2);
            JSONObject next = new JSONObject();
            next.put("next",jsonElements);
            return next;
//            return new Point(BOARD_SIZE / 2, BOARD_SIZE / 2);
        }

        //初始化参数
        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                computerScore[i][j] = -1000000000;
                computerScore_sort[i][j] = -1000000000;
            }
        }
        comx = 0;
        comy = 0;
        chessXYList = arouse(Role);

        int value1 = maxMin(Role, Depth, -1000000000, 1000000000);
        int judgeKill = 0;
        int judgeX = -1;
        int judgeY = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (computerScore_sort[i][j] >= judgeKill) {//判杀（还能再加上随机性）
                    judgeKill = computerScore_sort[i][j];
                    judgeX = i;
                    judgeY = j;
                }
            }
        }
        if (judgeKill == 9 || judgeKill == 12) {//对方有活四以上，优先去堵
            ChessXY chess = new ChessXY(judgeX, judgeY);
            chessList.add(chess);
        } else {
            for (int i = 0; i < BOARD_SIZE; ++i) {
                for (int j = 0; j < BOARD_SIZE; ++j) {
                    if (computerScore[i][j] == value1) {
                        ChessXY chess = new ChessXY(i, j);
                        chessList.add(chess);
                    }
                }
            }
        }
//        System.out.println("可选棋子长度："+chessList.size());
        if (chessList.size() == 0){
            System.out.println("可选为0");
            System.out.println("分数为："+value1);
            for (int i = 0; i < 15; i++) {
                System.out.println("\n");
                for (int j = 0; j < 15; j++) {
                    System.out.println(board[i][j]);
                }
            }
//            chessList.add(new ChessXY(0, 0));
        }
        int n = rand.nextInt(chessList.size()); // 电脑根据分值一样的点随机走，防止每次都走相同的步数
        comx = chessList.get(n).x;
        comy = chessList.get(n).y;
        chessList.clear();
        chessXYList.clear();

        //封装结果
        JSONArray jsonElements = new JSONArray();
        jsonElements.add(comx);
        jsonElements.add(comy);
        JSONObject next = new JSONObject();
        next.put("next",jsonElements);
        return next;
//        return new Point(comx, comy);
    }

    /**
     * @param @param f 颜色 @param @param x 坐标 @param @param y @param
     * @return boolean
     * @throws
     * @Title: isWin
     * @Description: 判断下该子是否能赢
     */

    public boolean isWin(int f, int x, int y) {
        int i, count = 1;
        boolean up, down, right, left, rup, lup, rdown, ldown;
        up = down = right = left = rup = lup = rdown = ldown = true;
        /**
         *
         * 上下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((y + i) < BOARD_SIZE) {
                if (board[x][y + i] == f && down)
                    count++;
                else
                    down = false;
            }
            if ((y - i) >= 0) {
                if (board[x][y - i] == f && up)
                    count++;
                else
                    up = false;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        /**
         *
         * 左右
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE) {
                if (board[x + i][y] == f && right)
                    count++;
                else
                    right = false;
            }
            if ((x - i) >= 0) {
                if (board[x - i][y] == f && left)
                    count++;
                else
                    left = false;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        /**
         *
         * 左上右下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE && (y + i) < BOARD_SIZE) {
                if (board[x + i][y + i] == f && rdown)
                    count++;
                else
                    rdown = false;
            }
            if ((x - i) >= 0 && (y - i) >= 0) {
                if (board[x - i][y - i] == f && lup)
                    count++;
                else
                    lup = false;
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;
        /**
         *
         * 右上左下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE && (y - i) >= 0) {
                if (board[x + i][y - i] == f && rup)
                    count++;
                else
                    rup = false;
            }
            if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
                if (board[x - i][y + i] == f && ldown)
                    count++;
                else
                    ldown = false;
            }
        }
        if (count >= 5) {
            return true;
        }

        return false;
    }

    /**
     * 启发函数
     *
     * @param role
     * @return LinkedList<ChessXY>
     */
    LinkedList<ChessXY> arouse(int role) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
//                    System.out.println(i+" "+j);
                    getScore(i, j, role);//得到每个空位得分，更新得分数组
                }
            }
        }
        int count = 0;
//        for (int i = 0; i < BOARD_SIZE; i++) {
//            for (int j = 0; j < BOARD_SIZE; j++) {
//            }
//        }
        int[][] maxList = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (computerScore_sort[i][j] != -1000000000) {
                    count++;
                }
            }

        }
        int maxValue = -1000000000;
        int maxX = -1;
        int maxY = -1;
        for (int h = 0; h < count; h++) {
            maxValue = -1000000000;
            maxX = -1;
            maxY = -1;
            for (int i = 0; i < BOARD_SIZE; i++) {
                for (int j = 0; j < BOARD_SIZE; j++) {
                    //  if(computerScore_sort[i][j] != -1000000000){
                    if (maxList[i][j] == 0) {
                        if (computerScore_sort[i][j] >= maxValue) {
                            maxValue = computerScore_sort[i][j];
                            maxX = i;
                            maxY = j;
                        }
                    }
                }
            }
            if (maxValue < 1) continue;
            chessXYList.addLast(new ChessXY(maxX, maxY));
            maxList[maxX][maxY] = 1;
        }
        return chessXYList;

    }

    /**
     * @param
     * @return void
     * @Title: getScore
     * @Description: 一个位置评分（按照启发规则哪个空位最有利）
     */
    public void getScore(int i, int j, int role) {
        if (board[i][j] == 0) {//空位置可以下棋
            if (isWin(role, i, j)) // 电脑能赢，故给分最高，因为可以结束，所以不再检测
            {
                computerScore_sort[i][j] = 13;

                return;
            } else if (isWin(3 - role, i, j)) // 电脑不能赢，玩家能赢，要阻止，所以给12分
            {
                computerScore_sort[i][j] = 12;
            } else if (isHuoOrChong(role, i, j, 4, HUO)) // 电脑玩家都不能赢，电脑能形成活四，给11分
            {
                computerScore_sort[i][j] = 11;
            } else if (isHuoOrChong(role, i, j, 4, CHONG)) // 电脑玩家都不能赢，电脑能形成冲四，给10分
            {
                computerScore_sort[i][j] = 10;
            } else if (isHuoOrChong(3 - role, i, j, 4, HUO)) // 电脑玩家都不能赢，玩家能形成活四，给9分
            {
                computerScore_sort[i][j] = 9;
            } else if (isHuoOrChong(role, i, j, 3, HUO)) // 电脑玩家都不能赢，电脑能形成活三，给8分
            {
                computerScore_sort[i][j] = 8;
            } else if (isHuoOrChong(3 - role, i, j, 4, CHONG)) // 电脑玩家都不能赢，玩家能形成冲四，给7分
            {
                computerScore_sort[i][j] = 7;
            } else if (isHuoOrChong(role, i, j, 3, CHONG)) // 电脑玩家都不能赢，电脑能形成冲三，给6分
            {
                computerScore_sort[i][j] = 6;
            } else if (isHuoOrChong(role, i, j, 2, HUO)) // 电脑玩家都不能赢，电脑能形成活二，给5分
            {
                computerScore_sort[i][j] = 5;
            } else if (isHuoOrChong(3 - role, i, j, 3, CHONG)) // 电脑玩家都不能赢，玩家能形成冲三，给4分
            {
                computerScore_sort[i][j] = 4;
            } else if (isHuoOrChong(3 - role, i, j, 2, HUO)) // 电脑玩家都不能赢，玩家能形成活二，给3分
            {
                computerScore_sort[i][j] = 3;
            } else if (isHuoOrChong(role, i, j, 2, CHONG)) // 电脑玩家都不能赢，电脑能形成冲二，给2分
            {
                computerScore_sort[i][j] = 2;
            } else if (isHuoOrChong(3 - role, i, j, 2, CHONG)) // 电脑玩家都不能赢，玩家能形成冲二，给1分
            {
                computerScore_sort[i][j] = 1;
            } else {
                computerScore_sort[i][j] = 0;
            }
        }
    }

    /**
     * @param f,x,y,num,hORc
     * @return boolean
     * @Title: isHuoOrChong
     * @Description: 判断是否为活
     */
    private boolean isHuoOrChong(int f, int x, int y, int num, int hORc) // 活
    {

        int i, count = 1;
        boolean terminal1 = false;
        boolean terminal2 = false;
        boolean up, down, right, left, rup, lup, rdown, ldown;
        up = down = right = left = rup = lup = rdown = ldown = true;
        /**
         *
         * 上下
         *
         */
        for (i = 1; i <= num; ++i) {
            if ((y + i) < BOARD_SIZE) {
                if (board[x][y + i] == f && down)
                    count++;
                else {
                    if (board[x][y + i] == 0 && down) {
                        terminal1 = true;
                    }
                    down = false;
                }
            }
            if ((y - i) >= 0) {
                if (board[x][y - i] == f && up)
                    count++;
                else {
                    if (board[x][y - i] == 0 && up) {
                        terminal2 = true;
                    }
                    up = false;
                }
            }
        }
        if (count == num && hORc == HUO && terminal1 && terminal2) {
            return true;
        }
        if (count == num && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
            return true;
        }
        count = 1;
        terminal1 = false;
        terminal2 = false;
        /* 左右 */
        for (i = 1; i <= num; ++i) {
            if ((x + i) < BOARD_SIZE) {
                if (board[x + i][y] == f && right)
                    count++;
                else {
                    if (board[x + i][y] == 0 && right) {
                        terminal1 = true;
                    }
                    right = false;
                }
            }
            if ((x - i) >= 0) {
                if (board[x - i][y] == f && left)
                    count++;
                else {
                    if (board[x - i][y] == 0 && left) {
                        terminal2 = true;
                    }
                    left = false;
                }
            }
        }
        if (count == num && hORc == HUO && terminal1 && terminal2) {
            return true;
        }
        if (count == num && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
            return true;
        }
        count = 1;
        terminal1 = false;
        terminal2 = false;
        /**
         *
         * 左上右下
         *
         */
        for (i = 1; i <= num; ++i) {
            if ((x + i) < BOARD_SIZE && (y + i) < BOARD_SIZE) {
                if (board[x + i][y + i] == f && rdown)
                    count++;
                else {
                    if (board[x + i][y + i] == 0 && rdown) {
                        terminal1 = true;
                    }
                    rdown = false;
                }
            }
            if ((x - i) >= 0 && (y - i) >= 0) {
                if (board[x - i][y - i] == f && lup)
                    count++;
                else {
                    if (board[x - i][y - i] == 0 && lup) {
                        terminal2 = true;
                    }
                    lup = false;
                }
            }
        }
        if (count == num && hORc == HUO && terminal1 && terminal2) {
            return true;
        }
        if (count == num && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
            return true;
        }
        count = 1;
        terminal1 = false;
        terminal2 = false;
        /**
         *
         * 右上左下
         *
         */
        for (i = 1; i <= num; ++i) {
            if ((x + i) < BOARD_SIZE && (y - i) >= 0) {
                if (board[x + i][y - i] == f && rup)
                    count++;
                else {
                    if (board[x + i][y - i] == 0 && rup) {
                        terminal1 = true;
                    }
                    rup = false;
                }
            }
            if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
                if (board[x - i][y + i] == f && ldown)
                    count++;
                else {
                    if (board[x - i][y + i] == 0 && ldown) {
                        terminal2 = true;
                    }
                    ldown = false;
                }
            }
        }
        if (count == num && hORc == HUO && terminal1 && terminal2) {
            return true;
        }
        if (count == num && hORc == CHONG && ((terminal1 && !terminal2) || (!terminal1 && terminal2))) {
            return true;
        }

        return false;
    }

    /**
     * @param role
     * @param depth
     * @param alpha
     * @param beta
     * @return int
     * @Description 极大极小值搜索
     */
    public int maxMin(int role, int depth, int alpha, int beta) {

        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    count++;
                }
            }
        }
        boolean winFlag = isWin(3 - Role, comx, comy);
        if (count == 0 || depth == 0) {//结束递归
            if (winFlag == true && (role == 3 - Role)) return -evaluate();
            return evaluate();
        }
        for (int i = 0; i < chessXYList.size(); i++) {
            if (board[chessXYList.get(i).x][chessXYList.get(i).y] == 0) {

                board[chessXYList.get(i).x][chessXYList.get(i).y] = role;//模拟下棋
                ChessXY chess = new ChessXY(chessXYList.get(i).x, chessXYList.get(i).y);
                comx = chess.x;
                comy = chess.y;
                int value = -maxMin((3 - role), depth - 1, -beta, -alpha);

                board[chess.x][chess.y] = 0;//撤销下的棋

                if (value > alpha) {//剪枝条会失去随机性，=
                    if (depth == Depth) {
                        computerScore[chessXYList.get(i).x][chessXYList.get(i).y] = value;
                    }
                    /**
                     * alpha + beta剪枝点
                     */
                    if (value >= beta) {
                        return beta;
                    }
                    alpha = value;
                }
            }

        }
        return alpha;
    }

    /**
     * 评估棋盘状态整体函数
     *
     * @return int
     */
    public int evaluate() {
        int computer = 0;
        int player = 0;

        for (int i = 0; i < BOARD_SIZE; ++i) {
            for (int j = 0; j < BOARD_SIZE; ++j) {
                if (board[i][j] == Role) {
                    computer += getScore_point(i, j, Role);
                } else if (board[i][j] == 3 - Role) {
                    player += getScore_point(i, j, 3 - Role);
                }
            }
        }
        return computer - player;
    }

    /**
     * @param x
     * @param y
     * @param role
     * @return int
     * @Decription 求棋盘已有的某个棋子的最大分数
     */
    public int getScore_point(int x, int y, int role) {
        int i, count = 1;
        int value = 0, bestValue = 0;
        int XX = -1, YY = -1;//连棋边界坐标
        int boundary = 0;
        boolean up, down, right, left, rup, lup, rdown, ldown;
        up = down = right = left = rup = lup = rdown = ldown = true;

        /**
         *
         * 上下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((y + i) < BOARD_SIZE) {
                if (board[x][y + i] == role && down)
                    count++;
                else {
                    if (down == true && board[x][y + i] != 0) {
                        boundary++;
                    }
                    down = false;
                }

            } else if (y + i == BOARD_SIZE) {
                boundary++;
            }
            if ((y - i) >= 0) {
                if (board[x][y - i] == role && up)
                    count++;
                else {
                    if (up == true && board[x][y - i] != 0) {
                        boundary++;
                    }
                    up = false;
                }
            } else if (y - i < 0) {
                boundary++;
            }
            if (down == false && up == false) break;//找到了两端
        }
        value = boundary(count, boundary);
        if (bestValue < value) {
            bestValue = value;
        }
        count = 1;
        boundary = 0;
        /**
         *
         * 左右
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE) {
                if (board[x + i][y] == role && right)
                    count++;
                else {
                    if (right == true && board[x + i][y] != 0) {
                        boundary++;
                    }
                    right = false;
                }

            } else if (x + i == BOARD_SIZE) {
                boundary++;
            }
            if ((x - i) >= 0) {
                if (board[x - i][y] == role && left)
                    count++;
                else {
                    if (left == true && board[x - i][y] != 0) {
                        boundary++;
                    }
                    left = false;
                }
            } else if (x - i < 0) {
                boundary++;
            }
            if (right == false && left == false) break;//找到了两端
        }
        value = boundary(count, boundary);
        if (bestValue < value) {
            bestValue = value;
        }
        count = 1;
        boundary = 0;
        /**
         *
         * 左上右下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE && (y + i) < BOARD_SIZE) {
                if (board[x + i][y + i] == role && rdown)
                    count++;
                else {
                    if (rdown == true && board[x + i][y + i] != 0) {
                        boundary++;
                    }
                    rdown = false;
                }

            } else if (x + i == BOARD_SIZE || y + i == BOARD_SIZE) {
                boundary++;
            }
            if ((x - i) >= 0 && (y - i) >= 0) {
                if (board[x - i][y - i] == role && lup)
                    count++;
                else {
                    if (lup == true && board[x - i][y - i] != 0) {
                        boundary++;
                    }
                    lup = false;
                }
            } else if (x - i < 0 || y - i < 0) {
                boundary++;
            }
            if (rdown == false && lup == false) break;//找到了两端
        }
        value = boundary(count, boundary);
        if (bestValue < value) {
            bestValue = value;
        }
        count = 1;
        boundary = 0;
        /**
         *
         * 右上左下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((x + i) < BOARD_SIZE && (y - i) >= 0) {
                if (board[x + i][y - i] == role && rup)
                    count++;
                else {
                    if (rup == true && board[x + i][y - i] != 0) {
                        boundary++;
                    }
                    rup = false;
                }
            } else if (x + i == BOARD_SIZE || y - i < 0) {
                boundary++;
            }
            if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
                if (board[x - i][y + i] == role && ldown)
                    count++;
                else {
                    if (ldown == true && board[x - i][y + i] != 0) {
                        boundary++;
                    }
                    ldown = false;
                }
            } else if (x - i < 0 || y + i == BOARD_SIZE) {
                boundary++;
            }
            if (rup == false && ldown == false) break;//找到了两端
        }
        value = boundary(count, boundary);
        if (bestValue < value) {
            bestValue = value;
        }
        //computerScore[x][y] = bestValue;
        return bestValue;
    }

    /**
     * @param count
     * @param boundary
     * @return
     * @Decription 计算棋子边界个数
     */
    public int boundary(int count, int boundary) {
        int value = 0;
        if (count >= 5) {
            if (boundary == 0) {
                value = 10000000;
            } else if (boundary == 1) {
                value = 100000;
            }
            //value = 10000000;

        } else if (count == 4) {
            if (boundary == 0) {
                value = 100000;
            } else if (boundary == 1) {
                value = 10000;
            }
        } else if (count == 3) {
            if (boundary == 0) {
                value = 1000;
            } else if (boundary == 1) {
                value = 100;
            }
        } else if (count == 2) {
            if (boundary == 0) {
                value = 100;
            } else if (boundary == 1) {
                value = 10;
            }
        } else if (count == 1) {
            if (boundary == 0) {
                value = 10;
            } else if (boundary == 1) {
                value = 1;
            }
        }
        return value;
    }

    private class ChessXY {
        int x;
        int y;

        public ChessXY(int x, int y) {
            this.x = x;
            this.y = y;
        }
    }


}
