package WorkClasses;
import ComponentsDescription.*;
import Panels.CurrentStatePanel;

import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

    public class ParametersSet extends AbstractTableModel {
        private int columnCount = 2;
        private ArrayList<String []> dataList;
        public ParametersSet() {
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

        public void init(ComponentData obj){
            dataList.clear();
            dataList.add(new String[]{"Имя", obj.name});
            dataList.add(new String[]{"Тип", obj.type});
            dataList.add(new String[]{"Следующий экран", obj.nextScreen});
            dataList.add(new String[]{"Координата X", Integer.toString(obj.kx)});
            dataList.add(new String[]{"Координата Y", Integer.toString(obj.ky)});
            dataList.add(new String[]{"Ширина", Integer.toString(obj.w)});
            dataList.add(new String[]{"Высота", Integer.toString(obj.h)});
            if (obj.type.compareTo("TextField") == 0){
                dataList.add(new String[]{"Текст", ((TextFieldData)obj).text});
            }
            else if (obj.type.compareTo("Button") == 0){
                dataList.add(new String[]{"Текст", ((ButtonData)obj).text});
                dataList.add(new String[]{"Цвет фона", ((ButtonData)obj).color});
                dataList.add(new String[]{"Цвет шрифта", "0xffffff"});
            }
            else if (obj.type.compareTo("Image") == 0){
                dataList.add(new String[]{"Файл", ((ImageData)obj).fileName});
            }
            else if (obj.type.compareTo("Video") == 0){
                dataList.add(new String[]{"Файл", ((VideoData)obj).fileName});
            }
        }

        public void changeRow(int index, String[] row) {
            dataList.set(index, row);
        }

        public void removeRow(int index) {
            dataList.remove(index);
        }
    }
