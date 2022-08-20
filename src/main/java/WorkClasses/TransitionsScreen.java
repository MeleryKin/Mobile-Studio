package WorkClasses;

import ComponentsDescription.ConditionData;
import ComponentsDescription.FunctionData;
import ComponentsDescription.ScreenData;
import ComponentsDescription.TransitionData;
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

public class TransitionsScreen implements ActionListener {

    JDialog frame;
    private ArrayList<Object[]> data;
    private final String[] columnNames = {"Условие", "Следующий экран", "Функция"};
    private final ArrayList<JButton> buttonFunctions = new ArrayList<>();
    private final ArrayList<JButton> buttonConditions = new ArrayList<>();
    private static ArrayList<TransitionData> transitions;
    private JTable settings;
    JPanel panel = new JPanel(new GridBagLayout());

    static class ButtonRenderer extends JButton implements TableCellRenderer
    {
        public ButtonRenderer() {
            setOpaque(true);
        }

        public Component getTableCellRendererComponent(JTable table, Object value,
                                                       boolean isSelected, boolean hasFocus, int row, int column) {
            setText("Изменить");
            return this;
        }
    }

    class ButtonEditor extends DefaultCellEditor
    {
        private String label;

        public ButtonEditor(JCheckBox checkBox)
        {
            super(checkBox);
        }

        public Component getTableCellEditorComponent(JTable table, Object value,
                                                     boolean isSelected, int row, int column)
        {
            label = "Изменить";
            if (column == 0) {
                buttonConditions.get(row).setText(label);
                return buttonConditions.get(row);
            }
            buttonFunctions.get(row).setText(label);
            return buttonFunctions.get(row);
        }

        public Object getCellEditorValue()
        {
            return label;
        }
    }

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

    public TransitionsScreen(ArrayList<TransitionData> nt){
        try {
            frame = new JDialog(RunClass.frame, "Следующий экран", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            frame.addWindowListener(new WindowAdapter(){
                public void windowClosing(WindowEvent e){
                    RunClass.saveTransitions(transitions);
                }
            });
            panel.setBackground(Color.WHITE);

            transitions = nt;
            updateScreen();

            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void buildTable(){

        settings = new JTable(new TableModel()){

            private static final long serialVersionUID = 1L;
            private Class editingClass;

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                editingClass = null;
                int modelColumn = convertColumnIndexToModel(column);
                if (column == 0) {
                    return new ButtonRenderer();
                }
                if (column == 1) {
                    Class rowClass = getModel().getValueAt(row, modelColumn).getClass();
                    return getDefaultRenderer(rowClass);
                }
                if (column == 2) {
                    return new ButtonRenderer();
                }
                else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                editingClass = null;
                if (column == 0) {
                    return new ButtonEditor(new JCheckBox());
                }
                if (column == 1) {
                    JComboBox<String> comboBox = new JComboBox<>();
                    for (String x : RunClass.getScreensNameList()){
                        comboBox.addItem(x);
                    }
                    comboBox.addActionListener(new ActionListener() {
                        @Override
                        public void actionPerformed(ActionEvent actionEvent) {
                            String x = (String)comboBox.getSelectedItem();
                            data.get(row)[column] = x;
                            transitions.get(row).screen = x;
                            RunClass.resetSave();
                        }
                    });
                    return new DefaultCellEditor(comboBox);
                }
                if (column == 2) {
                    return new ButtonEditor(new JCheckBox());
                }
                else {
                    return super.getCellEditor(row, column);
                }
            }

            @Override
            public Class getColumnClass(int column) {
                return editingClass != null ? editingClass : super.getColumnClass(column);
            }
        };
        for (int i = 0; i < transitions.size(); i++){
            int finalI = i;
            JButton newButton = new JButton();
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new ConditionsScreen(transitions.get(finalI).conditions, finalI);
                }
            });
            buttonConditions.add(newButton);
            newButton = new JButton();
            newButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new FunctionsScreen(transitions.get(finalI).functions, finalI);
                }
            });
            buttonFunctions.add(newButton);
        }
    }

    public void updateScreen(){
        buildTable();
        data = new ArrayList<>();
        for (TransitionData transition : transitions) {
            Object[] s = new Object[]{"A", transition.screen, "A"};
            data.add(s);
        }

        panel.removeAll();
        settings.setRowSelectionAllowed(false);
        settings.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
        JScrollPane setPane = new JScrollPane(settings);

        panel.add(setPane, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 0, 0, 0), 600, 270));

        JButton addButton = new JButton("Добавить");

        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                transitions.add(new TransitionData());
                RunClass.resetSave();
                updateScreen();
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
                    JOptionPane.showMessageDialog(RunClass.frame, "Выберите переход для удаления.");
                    return;
                }
                if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить переход?", "Удаление", JOptionPane.YES_NO_OPTION) == 0){
                    transitions.remove(x);
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

    public static void saveConditions(ArrayList<ConditionData> conditions, int index){
        transitions.get(index).conditions = conditions;
    }

    public static void saveFunctions(ArrayList<FunctionData> functions, int index){
        transitions.get(index).functions = functions;
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

}
