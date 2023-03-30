package BoardGames.goBang;


import BoardGames.template.*;

import javax.management.relation.Role;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Random;

public class GoBangAI extends Player implements GoBangConfig {
//    public int chessType;
//    public int x_index;
//    public int y_index;
    /**
     * AI分数
     */
    private int[][] computerScore;
    private int[][] computerScore_sort;
    LinkedList<GoBangChessPieces> chessXYList = new LinkedList<GoBangChessPieces>();//空格得分排序，便于启发函数
    private static int computerscore = 0; // 电脑最大分数
    private static int comx, comy; // 电脑下子坐标
    int Depth;//思考深度

    private final int HUO = 1;
    private final int CHONG = 2;
    private static int chesscou = 0;

    private int Role;
    /**
     * 记录找到的分数一样的棋子，随机下这些棋子中的一个，以防步法固定
     */
    private ArrayList<GoBangChessPieces> chessList = new ArrayList<GoBangChessPieces>();//候选棋子

    Random rand = new Random();

    int BOARD_SIZE;
    private int[][] board = new int[50][50];

    public GoBangAI(GoBangRules gamestatus,int Depth) {
        BOARD_SIZE = gamestatus.ROWS;
        x_index = -1;
        y_index = -1;
        computerScore = new int[BOARD_SIZE][BOARD_SIZE];
        computerScore_sort = new int[BOARD_SIZE][BOARD_SIZE];
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = gamestatus.board[i][j];
            }
        }
        Role = 0;
        this.Depth=Depth;
    }

    public void play(GoBangChessPieces pieces, GoBangRules gameStatus) {

        System.out.println("我思考的深度是:"+Depth);

        GoBangChessPieces bestChess=new GoBangChessPieces();
        Role = gameStatus.currentPlayer ? CHESSTYPE1 : CHESSTYPE2;//判断是什么颜色的棋子

        if (Role == CHESSTYPE1) {
            bestChess.setChessImage(BLACKCHESS);
//            board[x_index][y_index] = chessType;//1黑棋
        } else {
            bestChess.setChessImage(WHITECHESS);
//            board[x_index][y_index] = chessType;//2白棋
        }
        //AI开局
        if (gameStatus.chessCount == 0) {

            gameStatus.board[BOARD_SIZE / 2][BOARD_SIZE / 2] = Role;
//            gameStatus.chessCount++;
            bestChess.setX_coordinate(BOARD_SIZE / 2);
            bestChess.setY_coordinate(BOARD_SIZE / 2);
            chessArray[gameStatus.chessCount++] = bestChess;
//            chessBoard.wait = false;
            gameStatus.currentPlayer=!gameStatus.currentPlayer;
            return;
        }
        for (int i = 0; i < BOARD_SIZE; i++) {//使用自己的board做思考，避免闪烁
            for (int j = 0; j < BOARD_SIZE; j++) {
                this.board[i][j] = gameStatus.board[i][j];
            }
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

        System.out.println("待选长度为："+chessXYList.size());
        int value1 = maxMin(Role,Depth,-100000000, 100000000);
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(String.format("%12d",computerScore[j][i]));//输出跟棋盘同向
            }
            System.out.println("");
        }
        int judgeKill = 0;
        int judgeX = -1;
        int judgeY = -1;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(computerScore_sort[i][j]+" ");
                if(computerScore_sort[i][j] >= judgeKill){//判杀（还能再加上随机性）
                    judgeKill = computerScore_sort[i][j];
                    judgeX = i;
                    judgeY = j;
                }
            }
            System.out.println();
        }
        System.out.println(judgeX+"我们在这里"+judgeY);
        System.out.println(judgeKill);
        if (judgeKill == 9 || judgeKill ==12){//对方有活四以上，优先去堵
            GoBangChessPieces chess = new GoBangChessPieces(judgeX, judgeY);
            chessList.add(chess);
        }else {
            System.out.println("最优分数为："+value1);
            for (int i = 0; i < BOARD_SIZE; ++i) {
                for (int j = 0; j < BOARD_SIZE; ++j) {
                    if (computerScore[i][j] == value1) {
                        System.out.println("最优位置为："+(i+1)+"   "+(j+1));//输出跟棋盘同向
                        GoBangChessPieces chess = new GoBangChessPieces(i, j);
                        chessList.add(chess);
                    }
                }
            }
        }
        int n = rand.nextInt(chessList.size()); // 电脑根据分值一样的点随机走，防止每次都走相同的步数
        comx = chessList.get(n).getX_coordinate();
        comy = chessList.get(n).getY_coordinate();

        bestChess.setX_coordinate(comx);
        bestChess.setY_coordinate(comy);
        chessList.clear();
        chessXYList.clear();
        gameStatus.board[comx][comy] = Role;
        chessArray[gameStatus.chessCount++] = bestChess;

//        System.out.println("AI下棋中----------");
        //判赢
        if (gameStatus.win(comx, comy, gameStatus.currentPlayer)) {//判断是否胜利
            gameStatus.GameOver = true;
            return;
        } else if (gameStatus.chessCount == COLS * ROWS) {//判断是否全部下满
            gameStatus.GameOver = true;
            return;
        }
        gameStatus.currentPlayer = !gameStatus.currentPlayer;//交换下棋顺序
    }

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
    LinkedList<GoBangChessPieces> arouse(int role) {

        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                if (board[i][j] == 0) {
                    getScore(i, j, role);//得到每个空位得分，更新得分数组
                }
            }
        }
        int count = 0;
        for (int i = 0; i < BOARD_SIZE; i++) {
            for (int j = 0; j < BOARD_SIZE; j++) {
                System.out.print(String.format("%12d", computerScore_sort[j][i]));//输出跟棋盘同向
            }
            System.out.println();
        }
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
        System.out.println("可用空间为：" + count);
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
            //computerScore_sort[maxX][maxY] = -1000000000;
            System.out.println("可选坐标为：" + (maxX + 1) + " " + (maxY + 1));//输出跟棋盘同向
            chessXYList.addLast(new GoBangChessPieces(maxX, maxY));
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
        if (count == 0 || depth == 0 || winFlag) {//结束递归
            if (winFlag == true && (role == 3 - Role)) return -evaluate();
            return evaluate();
        }
        for (int i = 0; i < chessXYList.size(); i++) {
            if (board[chessXYList.get(i).getX_coordinate()][chessXYList.get(i).getY_coordinate()] == 0) {

                board[chessXYList.get(i).getX_coordinate()][chessXYList.get(i).getY_coordinate()] = role;//模拟下棋
                GoBangChessPieces chess = new GoBangChessPieces(chessXYList.get(i).getX_coordinate(), chessXYList.get(i).getY_coordinate());
                comx = chess.getX_coordinate();
                comy = chess.getY_coordinate();
                int value = -maxMin((3 - role), depth - 1, -beta, -alpha);

                board[chess.getX_coordinate()][chess.getY_coordinate()] = 0;//撤销下的棋

                if (value > alpha) {//剪枝条会失去随机性，=
                    if (depth == Depth) {
                        computerScore[chessXYList.get(i).getX_coordinate()][chessXYList.get(i).getY_coordinate()] = value;
                    }
                    /**
                     * alpha + beta剪枝点
                     */
                    if (value >= beta) {
                        //System.out.println("剪枝");
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
}


