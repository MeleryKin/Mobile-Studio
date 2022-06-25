package WorkClasses;

import ComponentsDescription.ScreenData;
import Panels.CurrentStatePanel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ConditionsScreen implements ActionListener {

    public ConditionsScreen(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Условие", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            ConditionsSet param = new ConditionsSet();
            param.init();
            JComboBox<String> combo = new JComboBox<>(new String[] { "Screen1", "Screen2"});
            DefaultCellEditor editor = new DefaultCellEditor(combo);
            JTable settings = new JTable(param);
            settings.getColumnModel().getColumn(1).setCellEditor(editor);
            settings.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
            JScrollPane setPane = new JScrollPane(settings);

            panel.add(setPane, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(0, 0, 0, 0), 600, 270));

            JButton addButton = new JButton("Добавить");
            panel.add(addButton, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(1, 1, 1, 1), 20, 15));
            JButton delButton = new JButton("Удалить");
            panel.add(delButton, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(1, 1, 1, 1), 20, 15));
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
