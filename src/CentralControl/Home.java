package CentralControl;

import javax.swing.*;
import java.awt.*;

public class Home {

    public Home() {
        JFrame homepage = new JFrame();
        homepage.setLocation(600, 100);
        homepage.setLayout(new BoxLayout(homepage,BoxLayout.Y_AXIS));
        JPanel title = new JPanel();
        title.setBackground(Color.BLACK);
        JPanel option = new JPanel();
        option.setBackground(Color.BLUE);
        homepage.getContentPane().add(title);
        homepage.getContentPane().add(option);


    }


}
