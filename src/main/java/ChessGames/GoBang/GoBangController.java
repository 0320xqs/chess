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

    GoBangListener listener = new GoBangListener();
    GoBangConfig config = new GoBangConfig();
    String AIMODE = "小白";
    int AIDepth = 4;
    int SelecetRows, SelectCols;
    static int RowsIndex = 14, ColsIndex = 14;


    public GoBangController() {
        System.out.println(this.config.firstAI);

        this.chessRules = new GoBangRules(config);
        chessBoard = new GoBangChessBoard(config);
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        player1 = new GoBangFirstPlayer(config);
        player2 = new GoBangSecondPlayer(config);

    }

    public GoBangController(int rows, int cols) {
        ROWS = rows;
        COLS = cols;

        this.chessBoard = new GoBangChessBoard(config);
        this.chessRules = new GoBangRules(config);
        listener = new GoBangListener();
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        player1 = new GoBangFirstPlayer(config);
        player2 = new GoBangSecondPlayer(config);
    }

    @Override
    public String GetResult() {
        return config.gameResult.getResult();
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
                    chessRules.Process(player1, player2, null, point);
                    chessBoard.repaint();
                }
                break;
            case "人 VS AI":
                while (config.gameResult == GameResult.UNFINISHED) {
                    point = listener.waitForClick();
//                    point = GoBangChessBoard.convertLocationToPlace(point);
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
//                    point = GoBangChessBoard.convertLocationToPlace(point);
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

    @Override
    public void playRecond(int xy, int Role) {
        int x = xy / ROWS;
        int y = xy % ROWS;
//        ChessRole chessRole = config.currentPlayer == Part.SECOND ? ChessRole.BLACKCHESS : ChessRole.WHITECHESS;
        ChessRole chessRole = Role == 1 ? ChessRole.WHITECHESS : ChessRole.BLACKCHESS;
        GoBangChessPieces tempChess = new GoBangChessPieces(x, y, chessRole);
        config.pieceList.add(tempChess);
        config.pieceArray[x][y] = tempChess;
        chessBoard.repaint();
    }

    @Override
    public int[] GameRecord() {
        int[] temp = new int[config.pieceList.size()];
        for (int i = 0; i < config.pieceList.size(); i++) {
            temp[i] = config.pieceList.get(i).getX_coordinate() * ROWS + config.pieceList.get(i).getY_coordinate();
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

    @Override
    public void init() {
        //赋值之前接受的数据
        this.config.firstPlayer = super.config.firstPlayer;
        this.config.secondPlayer = super.config.secondPlayer;
        this.config.firstAI = super.config.firstAI;
        this.config.secondAI = super.config.secondAI;
        this.config.minMinDepth = super.config.minMinDepth;
        System.out.println("此时AI模式为："+config.firstAI);
        System.out.println("此时minMax思考深度为："+config.minMinDepth);
        chessBoard.repaint();
        config.pieceList.clear();
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < ROWS; j++) {
                config.pieceArray[i][j] = null;
            }
        }
        config.currentPlayer = Part.FIRST;
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
                if (chessRules.check(null,GoBangChessBoard.convertLocationToPlace(new Point(clickPoint.x,clickPoint.y)))){
                    lock.notify();
                }
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
            clickPoint = GoBangChessBoard.convertLocationToPlace(clickPoint);
            return clickPoint;
        }
    }


}
