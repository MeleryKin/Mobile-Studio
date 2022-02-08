package WorkClasses;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class EnterClass implements ActionListener {

    static JFrame frame = new JFrame("Mobile Studio");

    EnterClass(){
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
            frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            frame.setContentPane(panel);
            panel.setBackground(Color.WHITE);

            JButton newProject = new JButton("Создать новый проект");
            JButton oldProject = new JButton("Открыть существующий проект");
            panel.add(newProject, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            panel.add(oldProject, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(10, 10, 10, 10), 0, 0));
            newProject.setPreferredSize(new Dimension(300, 80));
            oldProject.setPreferredSize(new Dimension(300, 80));

            newProject.addActionListener(e -> {
                frame.setVisible(false);
                new RunClass("");
            });
            oldProject.addActionListener(e -> {
                frame.setVisible(false);
                new RunClass("param");
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
