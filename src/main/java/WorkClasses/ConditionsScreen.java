package WorkClasses;

import ComponentsDescription.ConditionData;
import ComponentsDescription.ScreenData;
import ComponentsDescription.TransitionData;
import Panels.CurrentStatePanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Date;

public class ConditionsScreen implements ActionListener {

    private static ArrayList<Object[]> data;
    private final String[] columnNames = {"Левая часть", "Знак", "Правая часть"};
    private static ArrayList<ConditionData> conditions;
    public static JPanel panel = new JPanel(new GridBagLayout());
    private static JTable settings;

    class TableModel extends AbstractTableModel {

        public int getColumnCount() {
            return columnNames.length;
        }

        public int getRowCount() {
            return data.size();
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data.get(row)[col];
        }

        public boolean isCellEditable(int row, int col) {
            return true;
        }
    }

    public ConditionsScreen(ArrayList<ConditionData> nc, int index){

        try {
            JDialog frame = new JDialog(RunClass.frame, "Условие", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            frame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    TransitionsScreen.saveConditions(conditions, index);
                }
            });
            panel.setBackground(Color.WHITE);

            settings = new JTable(new TableModel()){

                private static final long serialVersionUID = 1L;
                private Class editingClass;

                @Override
                public TableCellRenderer getCellRenderer(int row, int column) {
                    editingClass = null;
                    int modelColumn = convertColumnIndexToModel(column);
                    if (column == 1) {
                        Class rowClass = getModel().getValueAt(row, modelColumn).getClass();
                        return getDefaultRenderer(rowClass);
                    }
                    else {
                        return super.getCellRenderer(row, column);
                    }
                }

                @Override
                public TableCellEditor getCellEditor(int row, int column) {
                    editingClass = null;
                    if (column == 1) {
                        String[] s = new String[]{"=", ">", "<", ">=", "<="};
                        JComboBox<String> comboBox = new JComboBox<>(s);
                        comboBox.addActionListener(new ActionListener() {
                            @Override
                            public void actionPerformed(ActionEvent actionEvent) {
                                String x = (String)comboBox.getSelectedItem();
                                data.get(row)[column] = x;
                                conditions.get(row).sign = x;
                            }
                        });
                        return new DefaultCellEditor(comboBox);
                    }
                    else {
                        return super.getCellEditor(row, column);
                    }
                }

                @Override
                public Class getColumnClass(int column) {
                    return editingClass != null ? editingClass : super.getColumnClass(column);
                }

                @Override
                public void setValueAt(Object aValue, int row, int column) {
                    String s = (String)aValue;
                    if (column == 0){
                        if (s.compareTo("") == 0){
                            JOptionPane.showMessageDialog(RunClass.frame, "Выражение условия не может быть пустым!");
                            return;
                        }
                        if (!ParsingClass.checkExpressionsCorrectness(s, data.get(row)[2].toString())){
                            JOptionPane.showMessageDialog(RunClass.frame, "Некорректная запись выражения!");
                            return;
                        }
                        data.get(row)[column] = s;
                        conditions.get(row).leftExpression = s;
                        RunClass.resetSave();
                    }
                    if (column == 2){
                        if (s.compareTo("") == 0){
                            JOptionPane.showMessageDialog(RunClass.frame, "Выражение условия не может быть пустым!");
                            return;
                        }
                        if (!ParsingClass.checkExpressionsCorrectness(s, data.get(row)[0].toString())){
                            JOptionPane.showMessageDialog(RunClass.frame, "Некорректная запись выражения!");
                            return;
                        }
                        data.get(row)[column] = s;
                        conditions.get(row).rightExpression = s;
                        RunClass.resetSave();
                    }
                }
            };

            settings.setRowSelectionAllowed(false);
            settings.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

            conditions = nc;
            updateScreen();

            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void updateScreen(){
        data = new ArrayList<>();
        for (ConditionData condition : conditions) {
            Object[] s = new Object[]{condition.leftExpression, condition.sign, condition.rightExpression};
            data.add(s);
        }

        panel.removeAll();
        JScrollPane setPane = new JScrollPane(settings);

        panel.add(setPane, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 0, 0, 0), 600, 270));

        JButton addButton = new JButton("Добавить");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NewCondition();
            }
        });
        panel.add(addButton, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 20, 15));
        JButton delButton = new JButton("Удалить");
        delButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = settings.getSelectedRow();
                if (x < 0){
                    JOptionPane.showMessageDialog(RunClass.frame, "Выберите условие для удаления.");
                    return;
                }
                if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить условие?", "Удаление", JOptionPane.YES_NO_OPTION) == 0){
                    conditions.remove(x);
                    RunClass.resetSave();
                    updateScreen();
                }
            }
        });
        panel.add(delButton, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(1, 1, 1, 1), 20, 15));
        panel.updateUI();
    }


    public static void addCondition(ConditionData condition){
        conditions.add(condition);
        updateScreen();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
