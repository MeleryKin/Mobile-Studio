package WorkClasses;

import ComponentsDescription.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;

public class ConditionsSet extends AbstractTableModel{
    private int columnCount = 3;
    private ArrayList<String []> dataList;
    public ConditionsSet() {
        dataList = new ArrayList<>();
        //  dataList.add(new String []{"Имя", });
    }

    @Override
    public int getRowCount() {
        return dataList.size();
    }

    @Override
    public int getColumnCount() {
        return columnCount;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        String[] rows = dataList.get(rowIndex);
        return rows[columnIndex];
    }

    @Override
    public String getColumnName(int columnIndex) {
        switch(columnIndex) {
            case 0: return "Левая часть";
            case 1: return "Знак";
            case 2: return "Правая часть";
        }
        return null;
    }

    public Class<?> getColumnClass(int column) {
        JComboBox<String> t = new JComboBox<>();
        String s = "";
        if (column == 1) return t.getClass();
        else return s.getClass();
    }

    public void init(){
        dataList.clear();
        dataList.add(new String[]{"[x]", "=", "1"});
      //  dataList.add(new String[]{"Изменить...", "Screen2", "Изменить..."});
      //  dataList.add(new String[]{"Изменить...", "Screen1", "Изменить..."});
    }

    public void changeRow(int index, String[] row) {
        dataList.set(index, row);
    }

    public void removeRow(int index) {
        dataList.remove(index);
    }
}
