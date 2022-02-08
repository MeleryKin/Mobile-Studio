package Panels;

import javax.swing.*;
import java.awt.*;

public class ExternalDataPanel extends JPanel {

    public ExternalDataPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
       // JButton button = new JButton("options");
     //   panel.add(button);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        add(panel);
    }
}
