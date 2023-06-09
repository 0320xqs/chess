package ChessGames.GoBang;

import ChessGames.template.*;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangRules extends ChessRules {

    private final GoBangConfig config;

    public GoBangRules(GoBangConfig goBangConfig) {
        this.config = goBangConfig;
    }

    @Override
    public void GetBegin() {
        config.board = new int[COLS][ROWS];
    }


    @Override
    public void Process(Player player1, Player player2, ChessPieces chess) {
        GoBangChessPieces tempChess;
        int x_index, y_index;
        int Role;
        Role = config.currentPlayer ? CHESSTYPE1 : CHESSTYPE2;
        if (config.currentPlayer) {
            tempChess = (GoBangChessPieces) player1.play(chess);
            tempChess.setChessImage(BLACKCHESS);
        } else {
            tempChess = (GoBangChessPieces) player2.play(chess);
            tempChess.setChessImage(WHITECHESS);
        }
        x_index = tempChess.getX_coordinate();
        y_index = tempChess.getY_coordinate();
        config.board[x_index][y_index] = Role;
        config.chessArray.add(tempChess);
        if (!End(tempChess)) {
            config.currentPlayer = !config.currentPlayer;
        } else {
        }
    }

    @Override
    public Boolean End(ChessPieces chessPieces) {
        if (win(chessPieces.getX_coordinate(), chessPieces.getY_coordinate(), config.currentPlayer)) {
            config.GameOver = config.currentPlayer ? 2 : 3;
            return true;
        } else if (config.chessArray.size() == COLS * ROWS) {
            config.GameOver = 1;
            return true;
        }
        return false;
    }


    @Override
    public boolean win(int x, int y, boolean start) {
        int i, count = 1;
        int BOARD_SIZE = ROWS;
        int f = 0;
        if (start) {
            f = 1;
        } else {
            f = 2;
        }
        boolean up, down, right, left, rup, lup, rdown, ldown;
        up = down = right = left = rup = lup = rdown = ldown = true;
        /**
         *
         * 上下
         *
         */
        for (i = 1; i < 5; ++i) {
            if ((y + i) < BOARD_SIZE) {
                if (config.board[x][y + i] == f && down)
                    count++;
                else
                    down = false;
            }
            if ((y - i) >= 0) {
                if (config.board[x][y - i] == f && up)
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
                if (config.board[x + i][y] == f && right)
                    count++;
                else
                    right = false;
            }
            if ((x - i) >= 0) {
                if (config.board[x - i][y] == f && left)
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
                if (config.board[x + i][y + i] == f && rdown)
                    count++;
                else
                    rdown = false;
            }
            if ((x - i) >= 0 && (y - i) >= 0) {
                if (config.board[x - i][y - i] == f && lup)
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
                if (config.board[x + i][y - i] == f && rup)
                    count++;
                else
                    rup = false;
            }
            if ((x - i) >= 0 && (y + i) < BOARD_SIZE) {
                if (config.board[x - i][y + i] == f && ldown)
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


    @Override
    public boolean check(int position_X, int position_Y) {
        for (ChessPieces c : config.chessArray) {
            if (c != null && c.getX_coordinate() == position_X && c.getY_coordinate() == position_Y)
                return true;
        }
        return false;

    }


    @Override
    public void GoBack() {
        if (config.chessArray.size() == 0) {
            return;
        }
        if (config.chessArray.size() > 0) {
            int x_index = config.chessArray.get(config.chessArray.size() - 1).getX_coordinate();
            int y_index = config.chessArray.get(config.chessArray.size() - 1).getY_coordinate();
            config.board[x_index][y_index] = 0;
        }
        config.chessArray.remove(config.chessArray.size() - 1);
        config.currentPlayer = !config.currentPlayer;
    }
}
