package ChessGames.GoBang;

import ChessGames.template.*;
import ChessGames.template.Model.GameResult;
import ChessGames.template.Model.Part;
import java.awt.*;
import java.lang.reflect.InvocationTargetException;
import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangRules extends ChessRules {

    private final GoBangConfig config;

    public GoBangRules(GoBangConfig goBangConfig) {
        this.config = goBangConfig;
    }

    @Override
    public void GetBegin() {
        config.pieceArray = new GoBangChessPieces[COLS][ROWS];
    }


    @Override
    public void Process(Player player1, Player player2, Point from, Point to) throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        try {
            if (!check(null, new Point(to.x, to.y)))
                return;
        } catch (NullPointerException ignored) {
        }
        switch (config.currentPlayer) {
            case FIRST:
                player1.play(null, to);
                break;
            case SECOND:
                player2.play(null, to);
                break;
        }
        GoBangChessPieces temp= (GoBangChessPieces) config.pieceList.get(config.pieceList.size()-1);
        if (!End(null, new Point(temp.getX_coordinate(),temp.getY_coordinate())) )
            config.currentPlayer = config.currentPlayer.Exchange(config.currentPlayer);
        }

    @Override
    public Boolean End(Point from, Point to) {
        if (win(null, to)) {
            config.gameResult = config.currentPlayer == Part.SECOND ? GameResult.SECONDWIN : GameResult.FIRSTWIN;
            return true;
        } else if (config.pieceList.size() == COLS * ROWS) {
            config.gameResult = GameResult.DRAW;
            return true;
        }
        return false;
    }

    @Override
    public boolean win(Point from, Point to) {
        int i, count = 1;
        int BOARD_SIZE = ROWS;
        boolean up, down, right, left, rup, lup, rdown, ldown;
        up = down = right = left = rup = lup = rdown = ldown = true;
        /**
         * 上下
         */
        for (i = 1; i < 5; ++i) {
            if ((to.y + i) < BOARD_SIZE) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x][to.y + i]).getChessRole().getPart() == config.currentPlayer && down) {
                        count++;
                    } else {
                        down = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    down = false;
                }
            }
            if ((to.y - i) >= 0) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x][to.y - i]).getChessRole().getPart() == config.currentPlayer && up) {
                        count++;
                    } else {
                        up = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    up = false;
                }
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;

/**
 * 左右
 */
        for (i = 1; i < 5; ++i) {
            if ((to.x + i) < BOARD_SIZE) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x + i][to.y]).getChessRole().getPart() == config.currentPlayer && right) {
                        count++;
                    } else {
                        right = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    right = false;
                }
            }
            if ((to.x - i) >= 0) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x - i][to.y]).getChessRole().getPart() == config.currentPlayer && left) {
                        count++;
                    } else {
                        left = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    left = false;
                }
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;

/**
 * 左上右下
 */
        for (i = 1; i < 5; ++i) {
            if ((to.x + i) < BOARD_SIZE && (to.y + i) < BOARD_SIZE) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x + i][to.y + i]).getChessRole().getPart() == config.currentPlayer && rdown) {
                        count++;
                    } else {
                        rdown = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    rdown = false;
                }
            }
            if ((to.x - i) >= 0 && (to.y - i) >= 0) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x - i][to.y - i]).getChessRole().getPart() == config.currentPlayer && lup) {
                        count++;
                    } else {
                        lup = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    lup = false;
                }
            }
        }
        if (count >= 5) {
            return true;
        }
        count = 1;

/**
 * 右上左下
 */
        for (i = 1; i < 5; ++i) {
            if ((to.x + i) < BOARD_SIZE && (to.y - i) >= 0) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x + i][to.y - i]).getChessRole().getPart() == config.currentPlayer && rup) {
                        count++;
                    } else {
                        rup = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    rup = false;
                }
            }
            if ((to.x - i) >= 0 && (to.y + i) < BOARD_SIZE) {
                try {
                    if (((GoBangChessPieces)config.pieceArray[to.x - i][to.y + i]).getChessRole().getPart() == config.currentPlayer && ldown) {
                        count++;
                    } else {
                        ldown = false;
                    }
                } catch (NullPointerException e) {
                    // 处理空指针异常，可以打印日志或执行其他操作
                    ldown = false;
                }
            }
        }

        if (count >= 5) {
            return true;
        }
        return false;
    }


    @Override
    public boolean check(Point from, Point to) {
        if (config.pieceArray[to.x][to.y] == null)
            return true;
        return false;
    }

    @Override
    public void GoBack() {
        if (config.pieceList.size() == 0) {
            return;
        }
        if (config.pieceList.size() > 0) {
            int x_index = config.pieceList.get(config.pieceList.size() - 1).getX_coordinate();
            int y_index = config.pieceList.get(config.pieceList.size() - 1).getY_coordinate();
            config.pieceArray[x_index][y_index] = null;
        }
        config.pieceList.remove(config.pieceList.size() - 1);
        config.currentPlayer = config.currentPlayer.Exchange(config.currentPlayer);
    }
}
