package ChessGames.GoBang;

import ChessGames.template.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

import static ChessGames.GoBang.GoBangConfig.*;

import ChessGames.GoBang.Model.*;
import ChessGames.template.Model.GameResult;
import ChessGames.template.Model.Part;

public class GoBangController extends Controller {

    @Override
    public int[] call() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        StartGame();
        return GameRecord();
    }
    GoBangChessBoard chessBoard;
    GoBangListener listener = new GoBangListener();
    GoBangConfig config = new GoBangConfig();
//    String GAMEMODE = "人 VS 人";
    String AIMODE = "小白";
    int AIDepth = 4;
    int SelecetRows, SelectCols;
    static int RowsIndex = 14, ColsIndex = 14;


    public GoBangController() {
//        this.config.
        this.chessRules = new GoBangRules(config);
        chessBoard = new GoBangChessBoard(config);
        chessBoard.addMouseListener(listener);
        System.out.println("我得到了");
        chessBoard.addMouseMotionListener(listener);
        player1 = new FirstPLayer(config);
        player2 = new SecondPlayer(config);

    }

    public GoBangController(int rows, int cols) {
        ROWS = rows;
        COLS = cols;
        this.config = new GoBangConfig();
        this.chessBoard = new GoBangChessBoard(config);
        this.chessRules = new GoBangRules(config);
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        player1 = new FirstPLayer(config);
        player2 = new SecondPlayer(config);
    }

    public String GetResult() {
        return config.gameResult.getResult();
    }

    public void init() {
        chessBoard.repaint();
        config.chessArray.clear();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                config.board[i][j] = null;
            }
        }
        config.currentPlayer = Part.SECOND;
        config.gameResult = GameResult.NOTSTARTED;
    }

    @Override
    public void StartGame() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        Point point;
        init();
        config.gameResult = GameResult.UNFINISHED;
        switch (GAMEMODE) {
            case "人 VS 人":
                while (config.gameResult == GameResult.UNFINISHED) {
                    point = listener.waitForClick();
                    point = GoBangChessBoard.convertLocationToPlace(point);
                    chessRules.Process(player1, player2, null, point);
                    chessBoard.repaint();
                }
                break;
            case "人 VS AI":
                while (config.gameResult == GameResult.UNFINISHED) {
                    point = listener.waitForClick();
                    point = GoBangChessBoard.convertLocationToPlace(point);
                    chessRules.Process(player1, player2, null, point); // 人下棋
                    chessBoard.repaint();
                    chessRules.Process(player1, player2, null, null); // AI下棋
                    chessBoard.repaint();
                }
                break;
            case "AI VS 人":
                chessRules.Process(player1, player2, null, null);//AI下第一步棋
                chessBoard.repaint();
                while (config.gameResult == GameResult.UNFINISHED) {
                    point = listener.waitForClick();
                    point = GoBangChessBoard.convertLocationToPlace(point);
                    chessRules.Process(player1, player2, null, point); // 人下棋
                    chessBoard.repaint();
                    chessRules.Process(player1, player2, null, null); // AI下棋
                    chessBoard.repaint();

                }
                break;
            case "AI VS AI":
                while (config.gameResult == GameResult.UNFINISHED) {
                    chessRules.Process(player1, player2, null, null);
                    chessBoard.repaint();
                }

                break;
        }


    }

//    @Override
//    public void GameModeSelect(String GameMode) {
//        this.GAMEMODE=GameMode;
//        switch (GameMode) {
//            case "人 VS 人":
//                config.blackplayer = PlayerType.Man;
//                config.whiteplayer = PlayerType.Man;
//                break;
//            case "人 VS AI":
//                config.blackplayer = PlayerType.Man;
//                config.whiteplayer = PlayerType.AI;
//                break;
//            case "AI VS 人":
//                config.blackplayer = PlayerType.AI;
//                config.whiteplayer = PlayerType.Man;
//                break;
//            case "AI VS AI":
//                config.blackplayer = PlayerType.AI;
//                config.whiteplayer = PlayerType.AI;
//                break;
//        }
//        player1 = new BlackPLayer(config);
//        player2 = new WhitePlayer(config);
//    }

    public void play(int xy, int Role) {
        int x = xy / ROWS;
        int y = xy % ROWS;
        ChessRole chessRole = config.currentPlayer == Part.SECOND ? ChessRole.BLACKCHESS : ChessRole.WHITECHESS;
        GoBangChessPieces tempChess = new GoBangChessPieces(x, y, chessRole);
        config.chessArray.add(tempChess);
        config.board[x][y] = tempChess;
        chessBoard.repaint();
    }

    @Override
    public int[] GameRecord() {
        int[] temp = new int[config.chessArray.size()];
        for (int i = 0; i < config.chessArray.size(); i++) {
            temp[i] = config.chessArray.get(i).getX_coordinate() * ROWS + config.chessArray.get(i).getY_coordinate();
        }
        return temp;
    }

    @Override
    public JPanel GetBoard() {
        JPanel panel = new BackGround();
        panel.setLayout(new FlowLayout());
        chessBoard.setPreferredSize(chessBoard.getPreferredSize());
        panel.add(chessBoard);
        return panel;
    }

    @Override
    public JPanel ChangeList() {
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(0,2));
        String[] list = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        JComboBox<String> row = new JComboBox<>(list);
        row.setSelectedIndex(14);
        row.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                SelecetRows = Integer.parseInt(evt.getItem().toString());
                RowsIndex = Integer.parseInt(evt.getItem().toString()) - 1;
            }
        });
        JComboBox<String> col = new JComboBox<>(list);
        col.setSelectedIndex(14);
        col.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                SelectCols = Integer.parseInt(evt.getItem().toString());
                ColsIndex = Integer.parseInt(evt.getItem().toString()) - 1;
            }
        });
        panel.add(new Label("ROWS:"));
        panel.add(row);
        panel.add(new Label("COLS:"));
        panel.add(col);
        return panel;
    }

    @Override
    public Controller changeGame() {
        return new GoBangController(SelecetRows, SelectCols);
    }

    private class BackGround extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            //添加背景图片
            g.drawImage(new ImageIcon("pic\\ChessBoard.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
        }
    }

    private class GoBangListener extends Component implements MouseListener, MouseMotionListener {

        private Object lock = new Object();
        private Point clickPoint;

        @Override
        public void mouseClicked(MouseEvent e) {
            synchronized (lock) {
                clickPoint = e.getPoint();

                lock.notify();
            }

        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {

        }

        @Override
        public void mouseEntered(MouseEvent e) {

        }

        @Override
        public void mouseExited(MouseEvent e) {

        }

        @Override
        public void mouseDragged(MouseEvent e) {

        }

        @Override
        public void mouseMoved(MouseEvent e) {
            Point p = new Point(e.getX(), e.getY());
            p = GoBangChessBoard.convertLocationToPlace(p);
            if (config.gameResult != GameResult.UNFINISHED || chessRules.check(null, new Point(p.x, p.y))) {
                chessBoard.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));//设置鼠标光标为默认形状
            } else {
                chessBoard.setCursor(new Cursor(Cursor.HAND_CURSOR));//设置鼠标光标为手型
            }

        }

        public Point waitForClick() {
            // 等待事件发生
            synchronized (lock) {
                try {
                    lock.wait();
                } catch (InterruptedException c) {
                    c.printStackTrace();
                }
            }
            return clickPoint;
        }
    }


}
