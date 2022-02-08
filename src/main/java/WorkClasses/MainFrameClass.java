package WorkClasses;

import WorkClasses.EnterClass;

import javax.swing.*;

public class MainFrameClass {

    public static void main(String[] args){
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new EnterClass();
            }
        });
    }
}
