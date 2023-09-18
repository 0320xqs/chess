package ChessGames.GoBang;

import java.awt.*;

import ChessGames.template.*;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangChessBoard extends ChessBoard {

    private final GoBangConfig config;
    public static int DIAMETER = 30;//棋子大小
    public static int MARGIN = 20;//棋盘左上角坐标
    public static int GRID_SPAN = 35;//一格长度

    public GoBangChessBoard(GoBangConfig config) {
        this.config = config;
    }


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        System.out.println("我在画图！");
        getParent().repaint();
        //重绘出棋盘
        g.setColor(Color.black);//线条颜色：黑
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + GRID_SPAN * i, MARGIN + GRID_SPAN * (COLS - 1), MARGIN + GRID_SPAN * i);//横向画线
        }
        for (int j = 0; j < COLS; j++) {
            g.drawLine(MARGIN + GRID_SPAN * j, MARGIN, MARGIN + GRID_SPAN * j, MARGIN + GRID_SPAN * (ROWS - 1));//纵向画线
        }

        for (int i = 0; i < config.chessArray.size(); i++) {
            int countx = config.chessArray.get(i).getX_coordinate() * GRID_SPAN + MARGIN;//得到棋子x坐标
            int county = config.chessArray.get(i).getY_coordinate() * GRID_SPAN + MARGIN;//得到棋子y坐标
            Image img = config.chessArray.get(i).getChessRole().getImage();//得到棋子图片
            g.drawImage(img, countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = COLS * GRID_SPAN;
        int height = ROWS * GRID_SPAN;
        return new Dimension(width, height);
    }

    public static Point convertPlaceToLocation(int x, int y) {
        return new Point(MARGIN + x * GRID_SPAN, MARGIN + y * GRID_SPAN);
    }

    public static Point convertLocationToPlace(Point point) {
        final int x = (point.x - MARGIN+GRID_SPAN/2) / GRID_SPAN;
        final int y = (point.y - MARGIN+GRID_SPAN/2) / GRID_SPAN;
        if (x >= COLS || y >= ROWS) {
            return null;
        }
        return new Point(x, y);
    }


}
