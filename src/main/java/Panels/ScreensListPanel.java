package Panels;

import ComponentsDescription.ScreenData;
import WorkClasses.RunClass;
import WorkClasses.ScreensSet;
import WorkClasses.SettingsEditor;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import java.awt.*;
import java.util.ArrayList;
import java.util.Date;

public class ScreensListPanel extends JPanel {

    public ScreensListPanel(){
       // setLayout(null);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public void updateScreen(){
        removeAll();
        ArrayList<ScreenData> data = RunClass.projectData.screens;
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Проект");
        for (ScreenData t : data) {
            DefaultMutableTreeNode s = new DefaultMutableTreeNode(t.name);
            for (int j = 0; j < t.components.size(); j++) {
                DefaultMutableTreeNode o = new DefaultMutableTreeNode(t.components.get(j).name);
                s.add(o);
            }
            root.add(s);
        }
        JTree tree = new JTree(root);
        JScrollPane setPane = new JScrollPane(tree);
     //   setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        setPane.setPreferredSize(new Dimension(this.getWidth(),370));
        setPane.setMinimumSize(new Dimension(this.getWidth(),370));
        //add(setPane);
        add(setPane, new GridBagConstraints(1, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 10, 0, 10), 0, 0));

        JButton b1 = new JButton("Добавить автору мозг");
        JButton b2 = new JButton("Удалить экран");
        b1.setMinimumSize(new Dimension(150,70));
        b2.setMinimumSize(new Dimension(150,70));

        add(b1, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(10, 10, 10, 10), 0, 0));

        add(b2, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(10, 10, 10, 10), 0, 0));

        this.updateUI();
    }
}
