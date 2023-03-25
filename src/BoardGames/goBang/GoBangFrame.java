package BoardGames.goBang;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GoBangFrame {

    private class GoBangListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            // 获取事件源和事件命令
            Object source = e.getSource();
            String command = e.getActionCommand();


        }
    }

}
