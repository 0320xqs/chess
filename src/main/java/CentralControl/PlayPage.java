package CentralControl;

import CentralControl.Home;
import ChessGames.GoBang.GoBangController;
import ChessGames.template.Controller;
import Util.GetChess;
import Util.Write;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;


public class PlayPage {

    public Controller chess;
    public JButton StartButton;
    public JButton SaveButton;
    public JButton ExitButton;
    JTextArea textArea;
    JComboBox<String> Chess;//棋类选择按钮
    JComboBox<String> Record;//记录文件选择按钮
    JComboBox<String> OneRecord;//第几局选择按钮
    HashMap<String, Path> RecordMap;
    List<int[]> OneRecordList;//存储文件记录
    MyButtonLister mb;
    int[] Play;//棋子位置
    JFrame frame;
    Thread recordPlay;//回放进程


    public PlayPage() {
        mb = new MyButtonLister();
        chess = GetChess.getChess("GoBang");
        System.out.println(chess);
        chess.chessBoard.setPreferredSize(new Dimension(560, 560));
        Chess = new JComboBox<>(GetChess.ChessList);
        Record = new JComboBox<>();
        OneRecord = new JComboBox<>();
        OneRecordList = new ArrayList<>();

        frame = new JFrame();
        frame.setLayout(new BorderLayout());


        JPanel panel1 = new JPanel();
        panel1.setLayout(new BoxLayout(panel1, BoxLayout.Y_AXIS));
        panel1.setPreferredSize(new Dimension(560, 760));


        JPanel panel2 = new JPanel();
        panel2.setLayout(new BoxLayout(panel2, BoxLayout.Y_AXIS));
        panel2.setPreferredSize(new Dimension(200, 560));


        textArea = new JTextArea("Play:\n", 25, 30);

        textArea.setLineWrap(true);
        JScrollPane scrollPane = new JScrollPane(textArea);


        Chess.setRenderer(new CenteredComboBoxRenderer());
        Chess.addActionListener(mb);

        RecordMap = new HashMap<>();
        RecordMap = FindFile("GoBang");
        for (String a : RecordMap.keySet())
            Record.addItem(a);
        Record.setRenderer(new CenteredComboBoxRenderer());
        Record.addActionListener(mb);

        OneRecord.setRenderer(new CenteredComboBoxRenderer());
        OneRecord.addActionListener(mb);

        panel2.add(Chess);
        panel2.add(Record);
        panel2.add(OneRecord);
        panel2.add(scrollPane);


        JPanel panel3 = new JPanel();
        panel3.setBackground(Color.GRAY);
        panel3.setLayout(new BoxLayout(panel3, BoxLayout.X_AXIS));
        panel3.setPreferredSize(new Dimension(560, 200));


        StartButton = new JButton("Start");
        StartButton.setPreferredSize(new Dimension(100, 200));
        StartButton.setForeground(Color.WHITE);
        StartButton.setBackground(new Color(59, 89, 182));
        StartButton.addActionListener(mb);

        SaveButton = new JButton("Withdraw");
        SaveButton.setPreferredSize(new Dimension(100, 200));
        SaveButton.setForeground(Color.WHITE);
        SaveButton.setBackground(new Color(59, 89, 182));
        SaveButton.addActionListener(mb);

        ExitButton = new JButton("Exit");
        ExitButton.setPreferredSize(new Dimension(100, 200));
        ExitButton.setForeground(Color.WHITE);
        ExitButton.setBackground(new Color(59, 89, 182));
        ExitButton.addActionListener(mb);

        panel3.add(Box.createHorizontalGlue());
        panel3.add(StartButton);
        panel3.add(Box.createHorizontalGlue());
        //panel3.add(SaveButton);
        panel3.add(Box.createHorizontalGlue());
        panel3.add(ExitButton);
        panel3.add(Box.createHorizontalGlue());

        panel1.add(chess.GetBoard());
        panel1.add(panel3);

        frame.getContentPane().add(panel2, BorderLayout.EAST);
        frame.getContentPane().add(panel1, BorderLayout.CENTER);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

    }

    public HashMap<String, Path> FindFile(String s) {
        String folderPath = "example/" + s;
        HashMap<String, Path> fileMap = new HashMap<>();

        try (Stream<Path> paths = Files.walk(Paths.get(folderPath))) {
            paths
                    .filter(Files::isRegularFile) // 过滤掉目录
                    .forEach(path -> fileMap.put(path.getFileName().toString().substring(0, path.getFileName().toString().lastIndexOf(".")), path));
        } catch (IOException e) {
            e.printStackTrace();
        }

        return fileMap;

    }

//    public List<int[]> FindRecord(Path path) throws IOException {
//        Gson gson = new Gson();
//        BufferedReader reader = new BufferedReader(new FileReader(String.valueOf(path)));
//
//        // 读取整个文件内容
//        StringBuilder sb = new StringBuilder();
//        String line = reader.readLine();
//        while (line != null) {
//            sb.append(line);
//            line = reader.readLine();
//        }
//        reader.close();
//
//        // 解析JSON字符串
//        JsonParser parser = new JsonParser();
//        JsonObject json = parser.parse(sb.toString()).getAsJsonObject();
//        JsonArray array = json.getAsJsonArray("list");
//
//        // 将JSON数组中的整数数组转换为Java的List<int[]>对象
//        List<int[]> result = new ArrayList<>();
//        for (int i = 0; i < array.size(); i++) {
//            JsonObject obj = array.get(i).getAsJsonObject();
//            JsonArray board = obj.getAsJsonArray("board");
//            int[] intArray = new int[board.size()];
//            for (int j = 0; j < board.size(); j++) {
//                intArray[j] = board.get(j).getAsInt();
//            }
//            result.add(intArray);
//        }
//        return result;
//    }


    private class MyButtonLister implements ActionListener {
        //按钮处理事件类
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == StartButton) {
                textArea.replaceRange("", 0, textArea.getDocument().getLength()); // 删除整个文本
                chess.init();
                recordPlay = new Thread(() -> {
                    for (int i = 0; i < Play.length; i++) {
                        try {
                            Thread.sleep(500);
                        } catch (InterruptedException ex) {
                            ex.printStackTrace();
                        }
                        String s = chess.playRecond(Play[i], i);
//                        String s = i % 2 == 0 ? "第" + i + "步，先手落子" : "第" + i + "步，后手落子";
                        if (s != null){
                            textArea.append(s + "\n");
                        }
                    }
                });
                recordPlay.start();

            } else if (obj == ExitButton) {
                if (recordPlay != null){
                    recordPlay.stop();//关闭回放进程
                }
                frame.dispose();
                new Home();
            } else if (obj == Chess) {
                if (recordPlay != null){
                    recordPlay.stop();//关闭回放进程
                }
                Container parent = chess.chessBoard.getParent();
                int index = parent.getComponentZOrder(chess.chessBoard);
                parent.remove(chess.chessBoard);
                chess = GetChess.getChess(Chess.getSelectedItem().toString());
                parent.add(chess.chessBoard, index);
                parent.revalidate();
                parent.repaint();

                Record.removeActionListener(mb);
                OneRecord.removeActionListener(mb);
                RecordMap = FindFile(Chess.getSelectedItem().toString());
                Record.removeAllItems();
                for (String a : RecordMap.keySet())
                    Record.addItem(a);
                Record.repaint();
                OneRecord.removeAllItems();
                OneRecord.addActionListener(mb);
                Record.addActionListener(mb);
            } else if (obj == Record) {//选择记录文件
                if (recordPlay != null){
                    recordPlay.stop();//关闭回放进程
                }
                try {
                    OneRecordList = Write.FindRecord(RecordMap.get(Record.getSelectedItem().toString()));
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                OneRecord.removeActionListener(mb);
                OneRecord.removeAllItems();
                for (int i = 0; i < OneRecordList.size(); i++)
                    OneRecord.addItem("第" + (i + 1) + "局");
                OneRecord.repaint();
                OneRecord.addActionListener(mb);
            } else if (obj == OneRecord) {//选择第几局
                if (recordPlay != null){
                    recordPlay.stop();//关闭回放进程
                }
                if (Record.getSelectedIndex() == -1) {
                    return;
                }
                int b = OneRecord.getSelectedIndex();
                Play = OneRecordList.get(b);
            }

        }
    }


    private class CenteredComboBoxRenderer extends BasicComboBoxRenderer {
        public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(CENTER);
            return this;
        }
    }


}
