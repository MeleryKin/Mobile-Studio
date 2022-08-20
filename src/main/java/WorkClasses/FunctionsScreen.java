package WorkClasses;

import ComponentsDescription.ConditionData;
import ComponentsDescription.FunctionData;
import ComponentsDescription.ScreenData;
import Panels.CurrentStatePanel;
import Panels.ScreenOptionsPanel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Date;

public class FunctionsScreen implements ActionListener {

    private static ArrayList<Object[]> data;
    private final String[] columnNames = {"Переменная", "Значение"};
    private static ArrayList<FunctionData> functions;
    private static JPanel panel = new JPanel(new GridBagLayout());
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

    public FunctionsScreen(ArrayList<FunctionData> nf, int index){

        try {

            JDialog frame = new JDialog(RunClass.frame, "Условие", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            frame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    TransitionsScreen.saveFunctions(functions, index);
                }
            });
            //     frame.setResizable(false);
            panel.setBackground(Color.WHITE);

            settings = new JTable(new TableModel()){

                private static final long serialVersionUID = 1L;
                private Class editingClass;

                @Override
                public TableCellRenderer getCellRenderer(int row, int column) {
                    editingClass = null;
                    int modelColumn = convertColumnIndexToModel(column);
                    if (column == 0) {
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
                    if (column == 0) {
                        JComboBox<String> comboBox = new JComboBox<>();
                        for (String x : RunClass.getParametersNameList()){
                            comboBox.addItem(x);
                        }

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
                    if (column == 0){
                        if (!ParsingClass.checkFunctionCorrectness((String) data.get(row)[1], RunClass.getParameter((String)aValue).type)){
                            JOptionPane.showMessageDialog(RunClass.frame, "Выражение задано некорректно.");
                            return;
                        }
                        data.get(row)[column] = aValue;
                        functions.get(row).expression = (String) aValue;
                        RunClass.resetSave();
                    }
                    if (column == 1){
                        if (!ParsingClass.checkFunctionCorrectness((String)aValue, RunClass.getParameter((String) data.get(row)[0]).type)){
                            JOptionPane.showMessageDialog(RunClass.frame, "Выражение задано некорректно.");
                            return;
                        }
                        data.get(row)[column] = aValue;
                        functions.get(row).expression = (String) aValue;
                        RunClass.resetSave();
                    }
                }
            };

            settings.setRowSelectionAllowed(false);
            settings.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));

            functions = nf;
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
        for (FunctionData function : functions) {
            Object[] s = new Object[]{function.varName, function.expression};
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
                new NewFunction();
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
                    JOptionPane.showMessageDialog(RunClass.frame, "Выберите функцию для удаления.");
                    return;
                }
                if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить функцию?", "Удаление", JOptionPane.YES_NO_OPTION) == 0){
                    functions.remove(x);
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

    public static void addFunction(FunctionData function){
        functions.add(function);
        updateScreen();
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

}
