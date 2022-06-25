package WorkClasses;

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
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Date;

public class TransitionsScreen implements ActionListener {

    private ArrayList<Object[]> data;
    private final String[] columnNames = {"Условие", "Следующий экран", "Функция"};
    private final JButton buttonColor = new JButton();
    private final JButton buttonConditions = new JButton();

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
            if (column * 10 + row == 11) {
                buttonColor.setText(label);
                return buttonColor;
            }
            buttonConditions.setText(label);
            return buttonConditions;
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
            return col >= 0;
        }
    }

    public TransitionsScreen(){
        try {
            JDialog frame = new JDialog(RunClass.frame, "Следующий экран", true);
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            frame.setSize(700, 400);
            //    frame.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
            frame.setLocationRelativeTo(RunClass.frame);
            //     frame.setResizable(false);
            JPanel panel = new JPanel(new GridBagLayout());
            panel.setBackground(Color.WHITE);

            initData(RunClass.getScreen(RunClass.getCurrentScreen()));

            JTable settings = new JTable(new TableModel()){

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

            settings.setPreferredSize(new Dimension(frame.getWidth(), frame.getHeight()));
            JScrollPane setPane = new JScrollPane(settings);

            panel.add(setPane, new GridBagConstraints(0, 0, 2, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(0, 0, 0, 0), 600, 270));

            JButton addButton = new JButton("Добавить");
            panel.add(addButton, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(1, 1, 1, 1), 20, 15));
            JButton delButton = new JButton("Удалить");
            panel.add(delButton, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                    GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                    new Insets(1, 1, 1, 1), 20, 15));
            frame.setContentPane(panel);
            frame.setVisible(true);
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    public void initData(ScreenData screen){
        data = new ArrayList<>();
        Object[] s = new Object[]{"A", RunClass.getScreen(0).name, "A"};
        data.add(s);
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {

    }

}
