package ChessGames.GoBang;

import java.awt.*;

import ChessGames.template.*;

import static ChessGames.GoBang.GoBangConfig.*;

public class GoBangChessBoard extends ChessBoard {
    GoBangConfig config = new GoBangConfig();


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        getParent().repaint();
        //重绘出棋盘
        g.setColor(Color.black);//线条颜色：黑
        for (int i = 0; i < ROWS; i++) {
            g.drawLine(MARGIN, MARGIN + GRID_SPAN * i, MARGIN + GRID_SPAN * (COLS - 1), MARGIN + GRID_SPAN * i);//横向画线
        }
        for (int j = 0; j < COLS; j++) {
            g.drawLine(MARGIN + GRID_SPAN * j, MARGIN, MARGIN + GRID_SPAN * j, MARGIN + GRID_SPAN * (ROWS - 1));//纵向画线
        }

        //重绘出棋子
        for (int i = 0; i < config.chessArray.size(); i++) {
            int countx = config.chessArray.get(i).getX_coordinate() * GRID_SPAN + MARGIN;//得到棋子x坐标
            int county = config.chessArray.get(i).getY_coordinate() * GRID_SPAN + MARGIN;//得到棋子y坐标
            Image img = config.chessArray.get(i).getChessImage();//得到棋子图片
            g.drawImage(img, countx - DIAMETER / 2, county - DIAMETER / 2, DIAMETER, DIAMETER, null);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        int width = (COLS - 1) * GRID_SPAN;
        int height = (ROWS - 1) * GRID_SPAN;
        return new Dimension(width, height);
    }
}
