package WorkClasses;

import javax.swing.*;
import javax.swing.table.TableCellEditor;
import java.awt.*;

// Редактор даты с использованием прокручивающегося списка JSpinner
public class SettingsEditor extends AbstractCellEditor implements TableCellEditor
{
    // Редактор
    private JSpinner editor;
    // Конструктор
    public SettingsEditor() {
        // Настройка прокручивающегося списка
     //   SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
     //   editor = new JSpinner(model);
    }
    // Метод получения компонента для прорисовки
    public Component getTableCellEditorComponent(JTable table, Object value,
                                                 boolean isSelected, int row, int column) {
        // Изменение значения
        editor.setValue(value);
        return editor;
    }
    // Функция текущего значения в редакторе
    public Object getCellEditorValue() {
        return editor.getValue();
    }
}