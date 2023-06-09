package ChessGames.ChineseChess;

import ChessGames.template.ChessBoard;
import ChessGames.template.ChessPieces;

import java.awt.*;

import static ChessGames.ChineseChess.CCConfig.*;


public class CCChessBoard extends ChessBoard {
    private CCConfig ccConfig;

    public CCChessBoard(CCConfig ccConfig) {
        this.ccConfig = ccConfig;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        getParent().repaint();
        Image backgroundImage = BACKGROUND;
        Image chessBoardImage = CHESSBOARD;

        // 创建Graphics2D对象
        Graphics2D g2d = (Graphics2D) g.create();

        // 绘制背景图片
        g2d.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);

        // 设置棋盘图片的透明度
        AlphaComposite alphaComposite = AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.8f);
        g2d.setComposite(alphaComposite);

        // 绘制棋盘图片
        g2d.drawImage(chessBoardImage, 20, -8, null);

        for (int i = 0; i < 9; i++) {
            for (int j = 0; j < 10; j++) {
                if (ccConfig.pieceArray[i][j] == null)
                    continue;
                Point count = convertPlaceToLocation(ccConfig.pieceArray[i][j].getX_coordinate(), ccConfig.pieceArray[i][j].getY_coordinate());
                Image img = ccConfig.pieceArray[i][j].getChessImage();

                g2d.setComposite(AlphaComposite.SrcOver); // 取消透明效果
                g2d.drawImage(img, count.x - X_INIT / 2, count.y - Y_INIT, 56, 56, null);

            }

        }

        // 释放Graphics2D对象
        g2d.dispose();

    }

    @Override
    public Dimension getPreferredSize() {
        int width = 560;
        int height = 560;
        return new Dimension(width, height);
    }
}
