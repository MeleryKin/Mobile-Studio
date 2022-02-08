package WorkClasses;
import ComponentsDescription.*;
import Panels.CurrentStatePanel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class ScreensSet extends AbstractTableModel {
    private int columnCount = 2;
    private ArrayList<String []> dataList;
    public ScreensSet() {
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
            case 0: return "Параметр";
            case 1: return "Значение";
        }
        return null;
    }

    public void init(ScreenData obj){
        dataList.clear();
        dataList.add(new String[]{"Имя", obj.name});
        dataList.add(new String[]{"Цвет", obj.color});
        dataList.add(new String[]{"Следующий экран", obj.nextScreen});
    }

    public void changeRow(int index, String[] row) {
        dataList.set(index, row);
    }

    public void removeRow(int index) {
        dataList.remove(index);
    }
}
