package Panels;

import ComponentsDescription.*;
import WorkClasses.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Date;

public class ObjectOptionsPanel extends JPanel {

    private ArrayList<Object[]> data;
    private final String[] columnNames = {"Параметр", "Значение"};
    private final JButton buttonColorBackground = new JButton();
    private final JButton buttonColorFont = new JButton();
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
            if (column * 10 + row == 12) {
                buttonConditions.setText(label);
                return buttonConditions;
            }
            if (column * 10 + row == 18) {
                buttonColorBackground.setText(label);
                return buttonColorBackground;
            }
            buttonColorFont.setText(label);
            return buttonColorFont;
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
            if (row == 1) return false;
            return col >= 1;
        }
    }

    public ObjectOptionsPanel(){
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));

        buttonColorBackground.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ComponentData component = RunClass.getObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated());
                Color newColor = JColorChooser.showDialog(RunClass.frame,
                        "Choose background color", new Color(((ButtonData)component).colorBackground[0],
                                ((ButtonData)component).colorBackground[1], ((ButtonData)component).colorBackground[2]));
                if(newColor != null){
                    //  newColor = new Color(20, 100, 100);
                    //   JOptionPane.showMessageDialog(RunClass.frame, newColor.toString());
                    ((ButtonData)component).colorBackground[0] = newColor.getRed();
                    ((ButtonData)component).colorBackground[1] = newColor.getGreen();
                    ((ButtonData)component).colorBackground[2] = newColor.getBlue();
                    RunClass.setObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated(), component);
                    RunClass.resetSave();
                    RunClass.statePanel.updateScreen();
                }
            }
        });

        buttonColorFont.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                ComponentData component = RunClass.getObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated());
                Color newColor = JColorChooser.showDialog(RunClass.frame,
                        "Choose font color", new Color(((ButtonData)component).colorFont[0],
                                ((ButtonData)component).colorFont[1], ((ButtonData)component).colorFont[2]));
                if(newColor != null){
                    //  newColor = new Color(20, 100, 100);
                    //   JOptionPane.showMessageDialog(RunClass.frame, newColor.toString());
                    ((ButtonData)component).colorFont[0] = newColor.getRed();
                    ((ButtonData)component).colorFont[1] = newColor.getGreen();
                    ((ButtonData)component).colorFont[2] = newColor.getBlue();
                    RunClass.setObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated(), component);
                    RunClass.resetSave();
                    RunClass.statePanel.updateScreen();
                }
            }
        });

        buttonConditions.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new TransitionsScreen(RunClass.getTransitionsData(false));
            }
        });
    }

    public void updateScreen(){
        removeAll();
        if (CurrentStatePanel.getDedicated() == -1) {
            this.updateUI();
            return;
        }

        initData(RunClass.getObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated()));
        JTable settings = new JTable(new TableModel()){

            private static final long serialVersionUID = 1L;
            private Class editingClass;

            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                editingClass = null;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1) {
                    if (row == 2) return new ButtonRenderer();
                    if (row == 8) return new ButtonRenderer();
                    if (row == 9) return new ButtonRenderer();
                    Class rowClass = getModel().getValueAt(row, modelColumn).getClass();
                    return getDefaultRenderer(rowClass);
                } else {
                    return super.getCellRenderer(row, column);
                }
            }

            @Override
            public TableCellEditor getCellEditor(int row, int column) {
                editingClass = null;
                int modelColumn = convertColumnIndexToModel(column);
                if (modelColumn == 1) {
                    if (row == 2) return new ButtonEditor(new JCheckBox());
                    if (row == 8) return new ButtonEditor(new JCheckBox());
                    if (row == 9) return new ButtonEditor(new JCheckBox());
                    editingClass = getModel().getValueAt(row, modelColumn).getClass();
                    return getDefaultEditor(editingClass);
                } else {
                    return super.getCellEditor(row, column);
                }
            }

            @Override
            public Class getColumnClass(int column) {
                return editingClass != null ? editingClass : super.getColumnClass(column);
            }

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                int cell = column * 10 + row;
                String s = (String) aValue;
                ScreenData screen = RunClass.getScreen(RunClass.getCurrentScreen());
                ComponentData component = screen.components.get(CurrentStatePanel.getDedicated());;
                switch (cell){
                    case 10:{
                        for (int i = 0; i < screen.components.size(); i++){
                            if (screen.components.get(i).name.compareTo(s) == 0 && CurrentStatePanel.getDedicated() != i){
                                JOptionPane.showMessageDialog(RunClass.frame, "Имя объекта не должно повторяться!");
                                return;
                            }
                        }
                        component.name = s;
                        break;
                    }
                    case 13:{
                        try {
                            component.kx = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(RunClass.frame, "Неправильный формат числа!");
                            return;
                        }
                        break;
                    }
                    case 14:{
                        try {
                            component.ky = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(RunClass.frame, "Неправильный формат числа!");
                            return;
                        }
                        break;
                    }
                    case 15:{
                        try {
                            component.w = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(RunClass.frame, "Неправильный формат числа!");
                            return;
                        }
                        break;
                    }
                    case 16:{
                        try {
                            component.h = Integer.parseInt(s);
                        } catch (NumberFormatException e) {
                            JOptionPane.showMessageDialog(RunClass.frame, "Неправильный формат числа!");
                            return;
                        }
                        break;
                    }
                    case 17:{
                        if (CurrentStatePanel.getDedicatedType().compareTo("TextField") == 0){
                            ((TextFieldData)component).text = s;
                        }
                        else if (CurrentStatePanel.getDedicatedType().compareTo("Button") == 0){
                            ((ButtonData)component).text = s;
                        }
                        break;
                    }
                }
                RunClass.resetSave();
                RunClass.setObject(RunClass.getCurrentScreen(), CurrentStatePanel.getDedicated(), component);
                RunClass.screensPanel.updateScreen();
                RunClass.statePanel.updateScreen();
            }
        };

        settings.setRowSelectionAllowed(false);
        settings.setPreferredSize(new Dimension(this.getWidth()*14/15, this.getHeight()*14/15));
        JScrollPane setPane = new JScrollPane(settings);
        setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        add(setPane);
        this.updateUI();
    }

    public void initData(ComponentData obj){
        data = new ArrayList<>();
        data.add(new Object[]{"Имя", obj.name});
        data.add(new String[]{"Тип", obj.type});
        data.add(new String[]{"Следующий экран", "Изменить..."});
        data.add(new String[]{"Координата X", Integer.toString(obj.kx)});
        data.add(new String[]{"Координата Y", Integer.toString(obj.ky)});
        data.add(new String[]{"Ширина", Integer.toString(obj.w)});
        data.add(new String[]{"Высота", Integer.toString(obj.h)});
        if (obj.type.compareTo("TextField") == 0){
            data.add(new String[]{"Текст", ((TextFieldData)obj).text});
        }
        else if (obj.type.compareTo("Button") == 0){
            data.add(new String[]{"Текст", ((ButtonData)obj).text});
            data.add(new String[]{"Цвет фона", Integer.toString(((ButtonData)obj).colorBackground[0])});
            data.add(new String[]{"Цвет шрифта", "0xffffff"});
        }
        else if (obj.type.compareTo("Image") == 0){
            data.add(new String[]{"Файл", ((ImageData)obj).fileName});
        }
        else if (obj.type.compareTo("Video") == 0){
            data.add(new String[]{"Файл", ((VideoData)obj).fileName});
        }
    }
}
