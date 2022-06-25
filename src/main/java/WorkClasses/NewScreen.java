package WorkClasses;

import ComponentsDescription.ProjectData;
import ComponentsDescription.ScreenData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewScreen implements ActionListener {

    public NewScreen(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Добавление экрана", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(600, 300);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel("Имя экрана:");
            panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            JTextField nameText = new JTextField("");
            panel.add(nameText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            nameText.setPreferredSize(new Dimension(300, 20));
            //start.setPreferredSize(new Dimension(300, 20));

            JButton addButton = new JButton("Добавить");
            addButton.setMinimumSize(new Dimension(150,70));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    String nw = nameText.getText();
                    boolean f = false;
                    for (int i = 0; i < RunClass.getScreensCount(); i++){
                        if (RunClass.getScreen(i).name.compareTo(nw) == 0) {
                            JOptionPane.showMessageDialog(frame, "Экран с таким именем уже существует.");
                            f = true;
                            break;
                        }
                    }
                    if (!f){
                        ScreenData ns = new ScreenData();
                        ns.name = nw;
                        ns.nextScreen = RunClass.getScreen(0).name;
                        //RunClass.projectData.screens.add(ns);
                        RunClass.addNewScreen(ns);
                        frame.dispose();
                    }
                }
            });
            panel.add(addButton, new GridBagConstraints(0, 1, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(25, 20, 20, 20), 30, 30));
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
