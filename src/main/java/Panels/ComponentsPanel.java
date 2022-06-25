package Panels;

import ComponentsDescription.*;
import WorkClasses.LocationClass;
import WorkClasses.RunClass;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.text.RuleBasedCollator;

public class ComponentsPanel extends JPanel {

    public ComponentsPanel(){
        JPanel panel = new JPanel(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        System.setProperty("myColor", "#e1b0ff");
        Color color = Color.getColor("myColor");

        JLabel text = new JLabel("Текст");
        text.setMinimumSize(new Dimension(400, 400));
        text.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        text.setOpaque(true);
        text.setBackground(color);
        text.setHorizontalAlignment(JLabel.CENTER);

        JLabel button = new JLabel("Кнопка");
        button.setMinimumSize(new Dimension(400, 400));
        button.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        button.setOpaque(true);
        button.setBackground(color);
        button.setHorizontalAlignment(JLabel.CENTER);

        JLabel image = new JLabel("Изображение");
        image.setMinimumSize(new Dimension(400, 400));
        image.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        image.setOpaque(true);
        image.setBackground(color);
        image.setHorizontalAlignment(JLabel.CENTER);

        JLabel video = new JLabel("Видео");
        video.setMinimumSize(new Dimension(400, 400));
        video.setBorder(BorderFactory.createLineBorder(Color.BLACK, 1));
        video.setOpaque(true);
        video.setBackground(color);
        video.setHorizontalAlignment(JLabel.CENTER);

        text.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getLocationOnScreen().x - RunClass.frame.getX();
                int y = e.getLocationOnScreen().y - RunClass.frame.getY();
                if (x > RunClass.statePanel.getX() && x < RunClass.statePanel.getX() + RunClass.statePanel.widthScreen &&
                        y > RunClass.statePanel.getY() && x < RunClass.statePanel.getY() + RunClass.statePanel.heightScreen) {
                    TextFieldData tx = new TextFieldData();
                    ScreenData tmp = RunClass.getScreen(RunClass.getCurrentScreen());
                    tx.name = "Component" + tmp.indexComp;
                    tmp.indexComp++;
                    tx.text = "Текст";
                    tx.type = "TextField";
                    tx.w = 40;
                    tx.h = 20;
                    Rectangle r = LocationClass.getScreenToGridSize(x - RunClass.statePanel.getX(),
                            y - RunClass.statePanel.getY(), tx.w, tx.h, RunClass.statePanel.widthScreen, RunClass.statePanel.heightScreen);
                    tx.kx = r.x;
                    tx.ky = r.y + RunClass.scrollButtonsPanel.upCoord;
                    tmp.components.add(tx);
                    RunClass.setScreen(RunClass.getCurrentScreen(), tmp);
                    CurrentStatePanel.setDedicated(tmp.components.size() - 1);
                    CurrentStatePanel.setDedicatedType("TextField");
                    RunClass.statePanel.updateScreen();
                    RunClass.objectOptionsPanel.updateScreen();
                    RunClass.screensPanel.updateScreen();
                }
            }
        });
        button.addMouseListener(new MouseAdapter() {

            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getLocationOnScreen().x - RunClass.frame.getX();
                int y = e.getLocationOnScreen().y - RunClass.frame.getY();
                if (x > RunClass.statePanel.getX() && x < RunClass.statePanel.getX() + RunClass.statePanel.widthScreen &&
                        y > RunClass.statePanel.getY() && x < RunClass.statePanel.getY() + RunClass.statePanel.heightScreen){
                    ButtonData bt = new ButtonData();
                    ScreenData tmp = RunClass.getScreen(RunClass.getCurrentScreen());
                    bt.name = "Component" + tmp.indexComp;
                    tmp.indexComp++;
                    bt.text = "Кнопка";
                    bt.colorBackground = new int[]{200, 200, 200};
                    bt.colorFont = new int[]{0, 0, 0};
                    bt.type = "Button";
                    bt.w = 40;
                    bt.h = 30;
                    Rectangle r = LocationClass.getScreenToGridSize(x - RunClass.statePanel.getX(),
                            y - RunClass.statePanel.getY(), bt.w, bt.h, RunClass.statePanel.widthScreen, RunClass.statePanel.heightScreen);
                    bt.kx = r.x;
                    bt.ky = r.y + RunClass.scrollButtonsPanel.upCoord;
                    tmp.components.add(bt);
                    RunClass.setScreen(RunClass.getCurrentScreen(), tmp);
                    CurrentStatePanel.setDedicated(tmp.components.size() - 1);
                    CurrentStatePanel.setDedicatedType("Button");
                    RunClass.statePanel.updateScreen();
                    RunClass.objectOptionsPanel.updateScreen();
                    RunClass.screensPanel.updateScreen();
                }
            }
        });
        image.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                int x = e.getLocationOnScreen().x - RunClass.frame.getX();
                int y = e.getLocationOnScreen().y - RunClass.frame.getY();
                if (x > RunClass.statePanel.getX() && x < RunClass.statePanel.getX() + RunClass.statePanel.widthScreen &&
                        y > RunClass.statePanel.getY() && x < RunClass.statePanel.getY() + RunClass.statePanel.heightScreen) {

                    ImageData tx = new ImageData();
                    ScreenData tmp = RunClass.getScreen(RunClass.getCurrentScreen());
                    tx.name = "Component" + tmp.indexComp;
                    tmp.indexComp++;
                    //tx.text = "Текст";
                    tx.type = "Image";
                    tx.w = 240;
                    tx.h = 320;
                    Rectangle r = LocationClass.getScreenToGridSize(x - RunClass.statePanel.getX(),
                            y - RunClass.statePanel.getY(), tx.w, tx.h, RunClass.statePanel.widthScreen, RunClass.statePanel.heightScreen);
                    tx.kx = r.x;
                    tx.ky = r.y + RunClass.scrollButtonsPanel.upCoord;
                    tmp.components.add(tx);
                    RunClass.setScreen(RunClass.getCurrentScreen(), tmp);
                    CurrentStatePanel.setDedicated(tmp.components.size() - 1);
                    CurrentStatePanel.setDedicatedType("Image");
                    RunClass.statePanel.updateScreen();
                    RunClass.objectOptionsPanel.updateScreen();
                    RunClass.screensPanel.updateScreen();
                }
            }
        });

        panel.add(text, new GridBagConstraints(0, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(25, 20, 20, 20), 40, 50));
        panel.add(button, new GridBagConstraints(1, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(25, 20, 20, 20), 40, 50));
        panel.add(image, new GridBagConstraints(2, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(25, 20, 20, 20), 3, 50));
        panel.add(video, new GridBagConstraints(3, 0, 1, 1, 1, 1,
                GridBagConstraints.NORTH, GridBagConstraints.HORIZONTAL,
                new Insets(25, 20, 20, 20), 40, 50));
        add(panel);
    }
}
