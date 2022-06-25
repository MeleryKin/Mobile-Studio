package Panels;

import ComponentsDescription.*;
import WorkClasses.*;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;

public class ExternalDataPanel extends JPanel {

    private ArrayList<Object[]> data;
    private final String[] columnNames = {"Параметр", "Тип", "Значение"};

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
            return col != 1;
        }
    }

    public ExternalDataPanel(){
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public void updateScreen(){
        removeAll();
        initData();
        JTable settings = new JTable(new TableModel()){
            private static final long serialVersionUID = 1L;

            @Override
            public void setValueAt(Object aValue, int row, int column) {
                ParameterData x = RunClass.getParameter(row);
                String s = (String)aValue;
                if (column == 0){
                    for (int i = 0; i < RunClass.getParametersNameList().size(); i++){
                        if (RunClass.getParameter(i).name.compareTo(s) == 0 && i != row){
                            JOptionPane.showMessageDialog(RunClass.frame, "Имя параметра не должно повторяться!");
                            return;
                        }
                    }
                    x.name = s;
                }
                else {
                    if (x.type.compareTo("Число") == 0){
                        try {
                            BigDecimal bd = new BigDecimal(s);
                            x.value = bd.toString();
                        }
                        catch(Exception e){
                            JOptionPane.showMessageDialog(RunClass.frame, "Некорректная запись числа!");
                        }
                    }
                    else {
                        x.value = s;
                    }
                }
                RunClass.setParameter(row, x);
                updateScreen();
            }
        };
        //settings.setPreferredSize(new Dimension(this.getWidth(), 370));
        settings.setPreferredSize(new Dimension(this.getWidth()*81/120, this.getHeight()*81/120));
        JScrollPane setPane = new JScrollPane(settings);
        setPane.setBounds(0, 0, this.getWidth(), 320);
        add(setPane);

        JButton b1 = new JButton("Добавить параметр");
        JButton b2 = new JButton("Удалить параметр");

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NewParameter();
            }
        });
        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                int x = settings.getSelectedRow();
                if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить переменную?", "Удаление", JOptionPane.YES_NO_OPTION) == 0){
                    RunClass.deleteParameter(x);
                    RunClass.dataPanel.updateScreen();
                }
            }
        });
        b1.setBounds(15, 330, 150, 60);
        b2.setBounds(190, 330, 150, 60);

        add(b1, new GridBagConstraints(0, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 0, 0, 0), 20, 20));

        add(b2, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 0, 0, 0), 20, 20));
        this.updateUI();
    }

    public void initData(){
        data = new ArrayList<>();
        for (int i = 0; i < RunClass.getParametersCount(); i++){
            ParameterData x = RunClass.getParameter(i);
            data.add(new Object[]{x.name, x.type, x.value});
        }
    }
}
