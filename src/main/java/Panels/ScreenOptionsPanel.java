package Panels;

import ComponentsDescription.ScreenData;
import WorkClasses.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class ScreenOptionsPanel extends JPanel {

    private Object[][] data;
    private final String[] columnNames = {"Параметр", "Значение"};
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
            return data.length;
        }

        public String getColumnName(int col) {
            return columnNames[col];
        }

        public Object getValueAt(int row, int col) {
            return data[row][col];
        }

        public boolean isCellEditable(int row, int col) {
            return col >= 1;
        }
    }

    public ScreenOptionsPanel(){
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));

        buttonColor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ScreenData screen = RunClass.getScreen(RunClass.getCurrentScreen());
                Color newColor = JColorChooser.showDialog(RunClass.frame,
                        "Choose background color", new Color(screen.color[0], screen.color[1], screen.color[2]));
                if(newColor != null){
                  //  newColor = new Color(20, 100, 100);
                 //   JOptionPane.showMessageDialog(RunClass.frame, newColor.toString());
                    screen.color[0] = newColor.getRed();
                    screen.color[1] = newColor.getGreen();
                    screen.color[2] = newColor.getBlue();
                    RunClass.setScreen(RunClass.getCurrentScreen(), screen);
                    RunClass.resetSave();
                }
            }
        });

        buttonConditions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new TransitionsScreen(RunClass.getTransitionsData(true));
            }
        });
    }

    public void updateScreen(){
        removeAll();
        initData(RunClass.getScreen(RunClass.getCurrentScreen()));

        JTable settings = new JTable(new TableModel()){

            private static final long serialVersionUID = 1L;
            private Class editingClass;
            private int rowMain;

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                editingClass = null;
                rowMain = row;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1) {
                    if (row == 1) return new ButtonRenderer();
                    if (row == 2) return new ButtonRenderer();
                    Class rowClass = getModel().getValueAt(row, modelColumn).getClass();
                    return getDefaultRenderer(rowClass);
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                editingClass = null;
                rowMain = row;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1) {
                    if (row == 1) return new ButtonEditor(new JCheckBox());
                    if (row == 2) return new ButtonEditor(new JCheckBox());
                    editingClass = getModel().getValueAt(row, modelColumn).getClass();
                    return getDefaultEditor(editingClass);
                } else {
                    return super.getCellEditor(row, column);
                }
            }

            @Override
            public Class getColumnClass(int column) {
                if (rowMain >= 0) return editingClass != null ? editingClass : super.getColumnClass(column);
                return null;
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                if (column * 10 + row == 10){
                    String s = (String) aValue;
                    ArrayList<String> list = RunClass.getScreensNameList();
                    for (int i = 0; i < list.size(); i++){
                        if (s.compareTo(list.get(i)) == 0 && RunClass.getCurrentScreen() != i){
                            JOptionPane.showMessageDialog(RunClass.frame, "Имя экрана не должно повторяться!");
                            return;
                        }
                    }
                    ScreenData screen = RunClass.getScreen(RunClass.getCurrentScreen());
                    screen.name = s;
                    RunClass.setScreen(RunClass.getCurrentScreen(), screen);
                    RunClass.resetSave();
                    RunClass.screensPanel.updateScreen();
                }
            }
        };

        settings.setRowSelectionAllowed(false);
        settings.setPreferredSize(new Dimension(this.getWidth()*14/15, this.getHeight()*14/15));
        //settings.setPreferredScrollableViewportSize(settings.getPreferredSize());
        JScrollPane setPane = new JScrollPane(settings);
        setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        add(setPane);
        this.updateUI();
    }

    public void initData(ScreenData screen){
        data = new Object[3][2];
        data[0][0] = "Имя";
        data[1][0] = "Цвет";
        data[2][0] = "Следующий экран";

        data[0][1] = screen.name;
        data[1][1] = screen.color;
        data[2][1] = screen.name;
    }
}
