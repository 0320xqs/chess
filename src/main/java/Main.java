import CentralControl.Battle.BattleControl;
import CentralControl.Battle.BattlePage;
import CentralControl.Home;
import CentralControl.Play.PlayPage;
import ChessGames.ChineseChess.CCChessBoard;
import ChessGames.ChineseChess.CCConfig;
import ChessGames.ChineseChess.CCController;
import ChessGames.GoBang.GoBangController;
import Util.GetChess;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class Main {


    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, InstantiationException, IllegalAccessException {
     new Home();


    }


}
