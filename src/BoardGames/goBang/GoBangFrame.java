package BoardGames.goBang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBangFrame {


    public void GameModeSelect() {
        if (GAMEMODE.equals("人 VS 人")) {
            player1 = new GoBangMan();
            player2 = new GoBangMan();
        } else if (GAMEMODE.equals("人 VS AI")) {
            player1 = new GoBangMan();
        } else if (GAMEMODE.equals("AI VS 人")) {
            player2 = new GoBangMan();
        } else if (GAMEMODE.equals("AI VS AI")) {

        }
    }
    //AI模式选择
    public void AIModeSelect() {
        if (GAMEMODE.equals("人 VS 人")) {
            return;
        }
        if (AIMODE.equals("小白")) {

            AIDepth = 4;

        } else if (AIMODE.equals("新手")) {

            AIDepth = 6;

        } else if (AIMODE.equals("普通")) {

            AIDepth = 8;
        }
        if (GAMEMODE.equals("人 VS AI")) {
            player2 = new GoBangAI(gameStatus, AIDepth);
        } else if (GAMEMODE.equals("AI VS 人")) {
            player1 = new GoBangAI(gameStatus, AIDepth);
            player1.play(null, gameStatus);
            repaint();
        } else if (GAMEMODE.equals("AI VS AI")) {
            player1 = new GoBangAI(gameStatus, AIDepth);
            player2 = new GoBangAI(gameStatus, AIDepth);
            player1.play(null, gameStatus);
            repaint();
        }
    }
    public void RestartGame() {//重新开始函数

        gameStatus.RestartGame();
        if(GAMEMODE.equals("AI VS 人")||GAMEMODE.equals("AI VS AI"))
        {
            player1.play(null, gameStatus);
        }

        repaint();
    }

    public void GoBack() {//悔棋函数

        gameStatus.GoBack();
        repaint();
    }

}
