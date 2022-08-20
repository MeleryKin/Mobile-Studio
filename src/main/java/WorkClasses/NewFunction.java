package WorkClasses;

import ComponentsDescription.ConditionData;
import ComponentsDescription.FunctionData;
import ComponentsDescription.ProjectData;
import ComponentsDescription.ScreenData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewFunction implements ActionListener {

    public NewFunction(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Добавление функции", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(600, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            JLabel comboLabel = new JLabel("Переменная:");
            panel.add(comboLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            String[] s = RunClass.getParametersNameList().toArray(new String[0]);
            JComboBox<String> comboBox = new JComboBox<>(s);
            panel.add(comboBox, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            comboBox.setPreferredSize(new Dimension(300, 20));

            JLabel nameLabel = new JLabel("Значение:");
            panel.add(nameLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            JTextField nameText = new JTextField("");
            panel.add(nameText, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            nameText.setPreferredSize(new Dimension(300, 20));
            //start.setPreferredSize(new Dimension(300, 20));

            JButton addButton = new JButton("Добавить");
            addButton.setMinimumSize(new Dimension(150,70));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (nameText.getText().compareTo("") == 0){
                        JOptionPane.showMessageDialog(RunClass.frame, "Выражение значения не может быть пустым.");
                        return;
                    }
                    if (!ParsingClass.checkFunctionCorrectness(nameText.getText(), RunClass.getParameter((String) comboBox.getSelectedItem()).type)){
                        JOptionPane.showMessageDialog(RunClass.frame, "Выражение задано некорректно.");
                        return;
                    }
                    FunctionData function = new FunctionData();
                    function.varName = (String) comboBox.getSelectedItem();
                    function.expression = nameText.getText();
                    FunctionsScreen.addFunction(function);
                    RunClass.resetSave();
                    frame.dispose();
                }
            });
            panel.add(addButton, new GridBagConstraints(0, 2, 2, 1, 1, 1,
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
