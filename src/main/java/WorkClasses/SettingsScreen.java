package WorkClasses;

import ComponentsDescription.ScreenData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class SettingsScreen implements ActionListener {

    SettingsScreen(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Настройки проекта", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
        //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
       //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel("Имя проекта:");
            JLabel iconLabel = new JLabel("Иконка проекта:");
            JLabel startLabel = new JLabel("Стартовый экран");
            panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            panel.add(iconLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));
            panel.add(startLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));

            JTextField nameText = new JTextField(RunClass.getProjectName());
            JTextField iconText = new JTextField(RunClass.getIconPath());
            iconText.setEnabled(false);
            JComboBox<String> start = new JComboBox<>();
            ArrayList<ScreenData> data = RunClass.getScreensList();
            start.setModel(new DefaultComboBoxModel<>());
            for (ScreenData i:data){
                start.addItem(i.name);
            }
            start.setSelectedItem(RunClass.getStartScreen());
            panel.add(nameText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            panel.add(iconText, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));
            panel.add(start, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));

            nameText.setPreferredSize(new Dimension(300, 20));
            iconText.setPreferredSize(new Dimension(300, 20));
            start.setPreferredSize(new Dimension(300, 20));

            JButton iconButton = new JButton("Выбрать");
            panel.add(iconButton, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));
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
