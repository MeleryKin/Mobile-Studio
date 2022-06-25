package Panels;

import WorkClasses.RunClass;

import javax.swing.*;

public class ScrollButtonsPanel extends JPanel {
    public int upCoord = 0;
    public int downCoord = 320;
    private final int maxConst = 1000;
    private final int step = 50;
    private static JButton up, down;

    public static void setEnableUp(boolean enable){
        up.setEnabled(enable);
    }

    public ScrollButtonsPanel(){
        setLayout(null);
        up = new JButton("˄");
        down = new JButton("˅");
        up.setBounds(5,250,50,50);
        down.setBounds(5,310,50,50);
        add(up);
        up.setEnabled(false);
        add(down);
        down.addActionListener(actionEvent -> {
            if (downCoord + step > maxConst){
                upCoord += maxConst - downCoord;
                downCoord = maxConst;
            }
            else {
                upCoord += step;
                downCoord += step;
            }
            if (downCoord == maxConst){
                down.setEnabled(false);
            }
            up.setEnabled(true);
            RunClass.statePanel.updateScreen();
        });
        up.addActionListener(actionEvent -> {
            if (upCoord - step < 0){
                downCoord += (-upCoord);
                upCoord = 0;
            }
            else {
                upCoord -= step;
                downCoord -= step;
            }
            if (upCoord == 0){
                up.setEnabled(false);
            }
            down.setEnabled(true);
            RunClass.statePanel.updateScreen();
        });
    }
}
