package ChessGames.ChineseChess;


import ChessGames.template.Controller;
import ChessGames.template.Model.GameResult;
import ChessGames.template.Model.Part;
import ChessGames.template.Player;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.lang.reflect.InvocationTargetException;


public class CCController extends Controller {
//    private CCRules rules;//弃用
//    private CCChessBoard board;//弃用
    private CCConfig config;
    private Player player1, player2;

    private CCChessPieces curFromPiece;
    listener listener = new listener();


    //改变
    int SelecetRows, SelectCols;
    static int RowsIndex = 14, ColsIndex = 14;

    public CCController() {
        curFromPiece = null;
        config = new CCConfig();
        this.chessBoard = new CCChessBoard(config);
        this.chessRules = new CCRules(config);
        chessBoard.addMouseListener(listener);
        chessBoard.addMouseMotionListener(listener);
        player1 = new SecondPLayer(config);
        player2 = new FirstPLayer(config);

        chessRules.GetBegin();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 9; j++) {
                System.out.println(config.pieceArray[i][j]);
            }

        }
    }


    @Override
    public void StartGame() throws ClassNotFoundException, InvocationTargetException, NoSuchMethodException, InstantiationException, IllegalAccessException {
        init();
        Point point;

        config.gameResult = GameResult.UNFINISHED;
        switch (GAMEMODE) {
            case "人 VS 人":
                while (config.gameResult == GameResult.UNFINISHED) {
                    System.out.println("人-人");
                    point = listener.waitForClick();
                    System.out.println("准备落子！ From:"+curFromPiece.getX_coordinate()+" "+curFromPiece.getY_coordinate()+" to:"+point.getX()+" "+point.getY());
                    chessRules.Process(player1, player2, new Point(curFromPiece.getX_coordinate(), curFromPiece.getY_coordinate()), point);
                    curFromPiece = null;
                    chessBoard.repaint();
                }
                break;
            case "人 VS AI":
                while (config.gameResult == GameResult.UNFINISHED) {
                    config.gameResult = GameResult.UNFINISHED;
                    System.out.println("人-AI");
                    point = listener.waitForClick();
                    chessRules.Process(player1, player2, new Point(curFromPiece.getX_coordinate(), curFromPiece.getY_coordinate()), point); // 人下棋
                    chessBoard.repaint();
                    curFromPiece = null;
                    System.out.println("我进来判断了");
                    if (config.checkFlag){//man选手的操作检测通过才能进行AI下棋
//                        config.checkFlag =false;
                        System.out.println("我判断过了");
                        chessRules.Process(player1, player2, null, null); // AI下棋
                        chessBoard.repaint();
                    }
                }
                break;
            case "AI VS 人":
                while (config.gameResult == GameResult.UNFINISHED) {
                    if (config.checkFlag) {//man选手的操作检测通过才能进行AI下棋
                    System.out.println("我AI先下过了");
//                    config.checkFlag = false;
                    chessRules.Process(player1, player2, null, null);//AI下第一步棋
                    chessBoard.repaint();
                    }
                    point = listener.waitForClick();
                    chessRules.Process(player1, player2, new Point(curFromPiece.getX_coordinate(), curFromPiece.getY_coordinate()), point); // 人下棋
                    chessBoard.repaint();
                    curFromPiece = null;
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
//        switch (GAMEMODE) {
//            case "人 VS 人":
//                config.blackplayer = PlayerType.Man;
//                config.redplayer = PlayerType.Man;
//                break;
//            case "人 VS AI":
//                config.blackplayer = PlayerType.AI;
//                config.redplayer = PlayerType.Man;
//                break;
//            case "AI VS 人":
//                config.blackplayer = PlayerType.Man;
//                config.redplayer = PlayerType.AI;
//                break;
//            case "AI VS AI":
//                config.blackplayer = PlayerType.AI;
//                config.redplayer = PlayerType.AI;
//                break;
//        }
//        player2 = new SecondPLayer(config);
//        player1 = new FirstPLayer(config);
//    }

    public void init() {
        chessBoard.repaint();
        config.pieceList.clear();
        config.checkFlag = true;
        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                config.pieceArray[i][j] = null;
            }
        }
        chessRules.GetBegin();
        config.currentPlayer = Part.FIRST;
        config.gameResult = GameResult.NOTSTARTED;
        System.out.println("初始化完成！");
    }

    @Override
    public int[] GameRecord() {
        return new int[0];
    }

    @Override
    public void play(int xy, int Role) {

    }

    @Override
    public String GetResult() {
        return config.gameResult.getResult();
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
        return null;
    }

    private class BackGround extends JPanel {
//        @Override
//        protected void paintComponent(Graphics g) {
//            //添加背景图片
//            g.drawImage(new ImageIcon("pic\\ChineseChess\\01.jpg").getImage(), 0, 0, this.getWidth(), this.getHeight(), this);
//        }
    }

    @Override
    public Object call() throws Exception {
        return null;
    }


    class listener implements MouseListener, MouseMotionListener {
        private Object lock = new Object();
        private Point clickPoint;

        @Override
        public void mouseClicked(MouseEvent e) {


        }

        @Override
        public void mousePressed(MouseEvent e) {

        }

        @Override
        public void mouseReleased(MouseEvent e) {
            synchronized (lock) {
//                System.out.println("鼠标点击成功");
                clickPoint = e.getPoint();
                clickPoint = CCConfig.convertLocationToPlace(clickPoint);
                // 当前走棋方
//                *@NonNull Part pointerPart = situation.getNextPart();
                // 当前焦点棋子
                CCChessPieces pointerPiece = config.pieceArray[clickPoint.x][clickPoint.y];
                if (pointerPiece != null){
                    System.out.println("当前焦点棋子："+pointerPiece.getChessRole());
                    System.out.println("当前焦点坐标："+pointerPiece.getX_coordinate()+" "+pointerPiece.getY_coordinate());
                }
                // 通过当前方和当前位置判断是否可以走棋
                // step: form
                //之前还未选中棋子
                if (curFromPiece == null) {
                    System.out.println("首次点击！");
                    if (pointerPiece != null && pointerPiece.getChessRole().getPart() == config.currentPlayer){
                        System.out.println("是本方棋子！");
                    }else{
                        System.out.println("不是本方棋子！");
                    }

                    // 当前焦点位置有棋子且是本方棋子
                    if (pointerPiece != null && pointerPiece.getChessRole().getPart() == config.currentPlayer) {
                        // 本方棋子, 同时是from指向
                        curFromPiece = pointerPiece;
                        return;
                    }
                    return;
                }
                //第二次点同样的棋子
                if (clickPoint.x == curFromPiece.getX_coordinate() && clickPoint.y == curFromPiece.getY_coordinate()) {
                    return;
                }
                // 当前焦点位置有棋子且是本方棋子
                if (pointerPiece != null && pointerPiece.getChessRole().getPart() == config.currentPlayer) {
                    // 更新 curFromPiece
                    curFromPiece = pointerPiece;
                    return;
                }
                lock.notify();
            }
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
            p = CCConfig.convertLocationToPlace(p);
//            System.out.println("鼠标移动坐标："+p.getX()+" "+p.getY());
//            if (config.gameResult != GameResult.UNFINISHED && chessRules.check(null, new Point(p.x, p.y))) {
            if (config.gameResult != GameResult.UNFINISHED) {
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

