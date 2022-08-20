package Panels;

import ComponentsDescription.ComponentData;
import ComponentsDescription.ScreenData;
import WorkClasses.*;

import javax.swing.*;
import javax.swing.event.TreeSelectionEvent;
import javax.swing.event.TreeSelectionListener;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.RuleBasedCollator;
import java.util.ArrayList;
import java.util.Date;

public class ScreensListPanel extends JPanel {

    JPanel main;
    JTree tree;
    public ScreensListPanel(){
       // setLayout(null);
        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
        main = this;
    }

    public void updateScreen(){
        removeAll();
        ArrayList<ScreenData> data = RunClass.getScreensList();
        DefaultMutableTreeNode root = new DefaultMutableTreeNode("Проект");
        for (ScreenData t : data) {
            DefaultMutableTreeNode s = new DefaultMutableTreeNode(t.name);
            for (int j = 0; j < t.components.size(); j++) {
                DefaultMutableTreeNode o = new DefaultMutableTreeNode(t.components.get(j).name);
                s.add(o);
            }
            root.add(s);
        }
        tree = new JTree(root);
        JScrollPane setPane = new JScrollPane(tree);
     //   setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        setPane.setPreferredSize(new Dimension(this.getWidth(),370));
        setPane.setMinimumSize(new Dimension(this.getWidth(),370));
        //add(setPane);
        tree.addTreeSelectionListener(treeSelectionEvent -> {
            TreePath path = treeSelectionEvent.getPath();
          //  JOptionPane.showMessageDialog(main, path.getPathCount());
            if (path.getPathCount() < 2) return;
            String screen = path.getPathComponent(1).toString(); //имя экрана
            int screenNumber = 0;
            for (int i = 0; i < RunClass.getScreensCount(); i++){
                if (RunClass.getScreen(i).name.compareTo(screen) == 0){
                    screenNumber = i;
                }
            }
            //JOptionPane.showMessageDialog(main, screenNumber);
            if (path.getPathCount() == 3){ //выделить новый объект
                if (screenNumber != RunClass.getCurrentScreen()){
                    RunClass.setCurrentScreen(screenNumber);
                }
                ArrayList<ComponentData> comp = RunClass.getScreen(screenNumber).components;
                int componentNumber = 0;
                for (int i = 0; i < comp.size(); i++){
                    ComponentData temp = comp.get(i);
                    if (temp.name.compareTo(path.getPathComponent(2).toString()) == 0){
                        componentNumber = i;
                    }
                }
                CurrentStatePanel.setDedicated(componentNumber);
                CurrentStatePanel.setDedicatedType(comp.get(componentNumber).type);
                RunClass.statePanel.updateScreen();
                RunClass.objectOptionsPanel.updateScreen();
            }
            else if (path.getPathCount() == 2){ //отобразить новый экран
                RunClass.setCurrentScreen(screenNumber);
            }
        });

        add(setPane, new GridBagConstraints(1, 0, 2, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(0, 10, 0, 10), 0, 0));

        JButton b1 = new JButton("Добавить экран");
        JButton b2 = new JButton("Удалить");

        b1.setMinimumSize(new Dimension(150,70));
        b2.setMinimumSize(new Dimension(150,70));

        b1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                new NewScreen();
            }
        });

        b2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent actionEvent) {
                TreePath path = tree.getSelectionPath();
                if (path == null){
                    JOptionPane.showMessageDialog(RunClass.frame, "Выберите объект дерева для удаления.");
                    return;
                }
                if (path.getPathCount() == 2){
                    String screen = path.getPathComponent(1).toString();
                    if (RunClass.getScreensCount() == 1){
                        JOptionPane.showMessageDialog(RunClass.frame, "Проект должен содержать как минимум один экран!");
                        return;
                    }
                    if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить экран?", "Удаление", JOptionPane.YES_NO_OPTION) == 0){
                        RunClass.deleteScreen(RunClass.getScreenID(screen));
                        RunClass.resetSave();
                    }

                }
                else if (path.getPathCount() == 3){
                    String screen = path.getPathComponent(1).toString();
                    String object = path.getPathComponent(2).toString();
                    if (JOptionPane.showConfirmDialog(RunClass.frame, "Удалить объект?", "Удаление", JOptionPane.YES_NO_OPTION) == 0) {
                        RunClass.deleteObject(RunClass.getScreenID(screen), RunClass.getObjectID(RunClass.getScreenID(screen), object));
                        RunClass.resetSave();
                    }
                }
                else {
                    JOptionPane.showMessageDialog(RunClass.frame, "Выберите объект дерева для удаления.");
                }
            }
        });
        add(b1, new GridBagConstraints(1, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(10, 10, 10, 10), 0, 0));

        add(b2, new GridBagConstraints(2, 1, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.CENTER,
                new Insets(10, 10, 10, 10), 0, 0));

        this.updateUI();
    }
}
