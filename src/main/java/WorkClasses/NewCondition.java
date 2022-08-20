package WorkClasses;

import ComponentsDescription.ConditionData;
import ComponentsDescription.ProjectData;
import ComponentsDescription.ScreenData;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class NewCondition implements ActionListener {

    public NewCondition(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Добавление условия", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(600, 500);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            JLabel leftLabel = new JLabel("Левая часть:");
            panel.add(leftLabel, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            JTextField leftText = new JTextField("");
            panel.add(leftText, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            leftText.setPreferredSize(new Dimension(300, 20));

            JLabel comboLabel = new JLabel("Знак:");
            panel.add(comboLabel, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            String[] s = new String[]{"=", ">", "<", ">=", "<="};
            JComboBox<String> comboBox = new JComboBox<>(s);
            panel.add(comboBox, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            comboBox.setPreferredSize(new Dimension(300, 20));

            JLabel rightLabel = new JLabel("Правая часть:");
            panel.add(rightLabel, new GridBagConstraints(0, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            JTextField rightText = new JTextField("");
            panel.add(rightText, new GridBagConstraints(1, 2, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(50, 10, 10, 10), 0, 0));
            rightText.setPreferredSize(new Dimension(300, 20));
            //start.setPreferredSize(new Dimension(300, 20));

            JButton addButton = new JButton("Добавить");
            addButton.setMinimumSize(new Dimension(150,70));
            addButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    if (leftText.getText().compareTo("") == 0 || rightText.getText().compareTo("") == 0){
                        JOptionPane.showMessageDialog(RunClass.frame, "Выражения условия не могут быть пустыми.");
                        return;
                    }
                    if (!ParsingClass.checkExpressionsCorrectness(leftText.getText(), rightText.getText())){
                        JOptionPane.showMessageDialog(RunClass.frame, "Выражение задано некорректно.");
                        return;
                    }
                    ConditionData condition = new ConditionData();
                    condition.leftExpression = leftText.getText();
                    condition.rightExpression = rightText.getText();
                    condition.sign = (String)comboBox.getSelectedItem();
                    ConditionsScreen.addCondition(condition);
                    RunClass.resetSave();
                    frame.dispose();
                }
            });
            panel.add(addButton, new GridBagConstraints(0, 3, 2, 1, 1, 1,
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
