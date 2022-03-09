package WorkClasses;

        import ComponentsDescription.ScreenData;

        import javax.swing.*;
        import java.awt.*;
        import java.awt.event.ActionEvent;
        import java.awt.event.ActionListener;
        import java.util.ArrayList;

public class NewScreen implements ActionListener {

    JComboBox<String>list = new JComboBox<String>();

    public NewScreen(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Настройки проекта", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
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
            /*JComboBox<String> start = new JComboBox<>();
            ArrayList<ScreenData> data = RunClass.projectData.screens;
            start.setModel(new DefaultComboBoxModel<>());
            for (ScreenData i:data){
                start.addItem(i.name);
            }*/
            panel.add(nameText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            nameText.setPreferredSize(new Dimension(300, 20));
            //start.setPreferredSize(new Dimension(300, 20));

            JButton iconButton = new JButton("Добавить");
            iconButton.setMinimumSize(new Dimension(150,70));
            iconButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {

                }
            });
            panel.add(iconButton, new GridBagConstraints(0, 1, 2, 1, 1, 1,
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
