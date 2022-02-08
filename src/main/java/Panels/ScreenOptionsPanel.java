package Panels;

import WorkClasses.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ScreenOptionsPanel extends JPanel {

    public ScreenOptionsPanel(){
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public void updateScreen(){
        removeAll();
        ScreensSet param = new ScreensSet();
        param.init(RunClass.projectData.screens.get(RunClass.currentScreen));
        JTable settings = new JTable(param);
        settings.setDefaultEditor(Date.class, new SettingsEditor());
        settings.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
        JScrollPane setPane = new JScrollPane(settings);
        setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
        add(setPane);
        this.updateUI();
    }
}
