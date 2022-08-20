package WorkClasses;

import ComponentsDescription.ParameterData;
import ComponentsDescription.ProjectData;
import ComponentsDescription.ScreenData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewParameter implements ActionListener {

    public NewParameter(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Добавление параметра", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(600, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            JLabel nameLabel = new JLabel("Имя переменной:");
            JTextField nameText = new JTextField("");
            JLabel typeLabel = new JLabel("Тип переменной:");
            JComboBox<String> typeCombo = new JComboBox<>();
            typeCombo.setModel(new DefaultComboBoxModel<>());
            typeCombo.addItem("Число");
            typeCombo.addItem("Строка");
            JLabel valueLabel = new JLabel("Начальное значение:");
            JTextField valueText = new JTextField("");
            panel.add(nameLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            panel.add(nameText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            panel.add(typeLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            panel.add(typeCombo, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            panel.add(valueLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            panel.add(valueText, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 0, 0));
            nameText.setPreferredSize(new Dimension(200, 20));
            typeCombo.setPreferredSize(new Dimension(200, 20));
            valueText.setPreferredSize(new Dimension(200, 20));
            //start.setPreferredSize(new Dimension(300, 20));

            JButton addButton = new JButton("Добавить");
            addButton.setMinimumSize(new Dimension(70,70));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    ParameterData x = new ParameterData();
                    x.name = nameText.getText();
                    x.type = (String)typeCombo.getSelectedItem();
                    x.value = valueText.getText();
                    if (x.name.compareTo("") == 0){
                        JOptionPane.showMessageDialog(frame, "Некорректное имя переменной.");
                        return;
                    }
                    for (int i = 0; i < RunClass.getParametersCount(); i++){
                        if (RunClass.getParameter(i).name.compareTo(x.name) == 0) {
                            JOptionPane.showMessageDialog(frame, "Переменная с таким именем уже существует.");
                            return;
                        }
                    }
                    if (x.type.compareTo("Число") == 0){
                        try {
                            int s;
                            s = Integer.parseInt(x.value);

                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(frame, "Неправильный формат числа!");
                            return;
                        }
                    }
                    RunClass.addParameter(x);
                    RunClass.resetSave();
                    RunClass.dataPanel.updateScreen();
                    frame.dispose();
                }
            });
            panel.add(addButton, new GridBagConstraints(0, 3, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(30, 10, 10, 10), 20, 20));
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

