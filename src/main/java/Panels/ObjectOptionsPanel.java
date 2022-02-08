package Panels;

import ComponentsDescription.ComponentData;
import WorkClasses.*;

import javax.swing.*;
import java.awt.*;
import java.util.Date;

public class ObjectOptionsPanel extends JPanel {

    public ObjectOptionsPanel(){
        setLayout(null);
        setBorder(BorderFactory.createLineBorder(Color.black, 1));
    }

    public void updateScreen(){
        removeAll();
        if (CurrentStatePanel.dedicated != -1) {
            ParametersSet param = new ParametersSet();
            param.init(RunClass.projectData.screens.get(RunClass.currentScreen).components.get(CurrentStatePanel.dedicated));
            JTable settings = new JTable(param);
          //  settings.setDefaultEditor(new SettingsEditor());
            settings.setDefaultEditor(Date.class, new SettingsEditor());
            settings.setPreferredSize(new Dimension(this.getWidth(), this.getHeight()));
            JScrollPane setPane = new JScrollPane(settings);
            setPane.setBounds(0, 0, this.getWidth(), this.getHeight());
            add(setPane);
        }
        this.updateUI();
    }
}
