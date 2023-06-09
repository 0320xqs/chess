package CentralControl.Battle;

import CentralControl.Home;
import ChessGames.GoBang.GoBangController;
import ChessGames.template.Controller;

import javax.swing.*;
import javax.swing.plaf.basic.BasicComboBoxRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;

public class BattleControl {
    JButton ChangeButton = new JButton();
    int SelecetRows = 15, SelectCols = 15;
    MyButtonLister mb = new MyButtonLister();
    JFrame MainFrame = new JFrame(), ChangeFrame = new JFrame();
    BattlePage battlePage = new BattlePage();
    int RowsIndex = 14, ColsIndex = 14;
    String chess = "GoBang";


    private class MyButtonLister implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            Object obj = e.getSource();
            if (obj == ChangeButton) {
                BattleStart(chess);
            } else if (obj == battlePage.ExitButton) {
                ChangeFrame.dispose();
            }
        }
    }

    public void BattleStart(String chess) {

        this.chess = chess;
        MainFrame.dispose();
        ChangeFrame.dispose();
        switch (this.chess) {
            case "GoBang":
                MainFrame = battlePage.GetBattlePage(new GoBangController(SelecetRows, SelectCols));
                break;
        }
        battlePage.ExitButton.addActionListener(mb);
        ChangeFrame = ChangeList();
        ChangeFrame.setLocationRelativeTo(MainFrame);
        ChangeFrame.setLocation(MainFrame.getX() + MainFrame.getWidth(), (int) (MainFrame.getY() * 1.5));
        MainFrame.setVisible(true);
        ChangeFrame.setVisible(true);


    }


    public JFrame ChangeList() {

        JFrame frame = new JFrame("修改列表");
        frame.setLayout(new GridLayout(0, 2));
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        ChangeButton = new JButton("应用");
        String[] list = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "10", "11", "12", "13", "14", "15"};
        JComboBox<String> row = new JComboBox<>(list);
        row.setSelectedIndex(RowsIndex);
        row.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                SelecetRows = Integer.parseInt(evt.getItem().toString());
                RowsIndex = Integer.parseInt(evt.getItem().toString()) - 1;
            }
        });
        JComboBox<String> col = new JComboBox<>(list);
        col.setSelectedIndex(ColsIndex);
        col.addItemListener(evt -> {
            if (evt.getStateChange() == ItemEvent.SELECTED) {
                SelectCols = Integer.parseInt(evt.getItem().toString());
                ColsIndex = Integer.parseInt(evt.getItem().toString()) - 1;
            }
        });
        ChangeButton.addActionListener(mb);
        frame.add(new Label("ROWS:"));
        frame.add(row);
        frame.add(new Label("COLS:"));
        frame.add(col);
        frame.add(ChangeButton);
        frame.pack();
        return frame;


    }
}
