package WorkClasses;

import ComponentsDescription.*;
import Panels.*;
import org.json.JSONException;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

public class RunClass implements ActionListener {
    public static JFrame frame = new JFrame("Mobile Studio");
    static Dimension screenSize;
    public static ProjectData projectData;
    public static ArrayList<Object> objects;
    public static ScrollButtonsPanel scrollButtonsPanel = new ScrollButtonsPanel();
    public static CurrentStatePanel statePanel = new CurrentStatePanel();
    public static ComponentsPanel componentsPanel = new ComponentsPanel();
    public static int currentScreen = 0;
    public static ObjectOptionsPanel objectOptionsPanel = new ObjectOptionsPanel();
    public static ScreenOptionsPanel screenOptionsPanel = new ScreenOptionsPanel();

    RunClass(String param){
        try {
            frame.setSize(1200, 900);
            frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
            //frame.setMinimumSize(new Dimension(900, 700));
            frame.setLocationRelativeTo(null);
            frame.setLayout(null);
            frame.setVisible(true);
            frame.setResizable(false);

            JMenuBar menuBar = new JMenuBar();
            JMenu fileMenu = new JMenu("Файл");
            JMenuItem newProject = new JMenuItem("Создать новый проект");
            JMenuItem openProject = new JMenuItem("Открыть существующий проект");
            JMenuItem saveProject = new JMenuItem("Сохранить проект");
            JMenuItem saveAsProject = new JMenuItem("Сохранить проект как...");
            JMenuItem exitProject = new JMenuItem(new AbstractAction() {

                {
                    putValue(NAME, "Выход");
                }

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    System.exit(0);
                }
            });
            fileMenu.add(newProject);
            fileMenu.add(openProject);
            fileMenu.add(saveProject);
            fileMenu.add(saveAsProject);
            fileMenu.add(exitProject);
            menuBar.add(fileMenu);

            JMenu settingsSMenu = new JMenu("Настройки");
            JMenuItem settingsProject = new JMenuItem(new AbstractAction() {

                {
                    putValue(NAME, "Настройки проекта");
                }

                @Override
                public void actionPerformed(ActionEvent actionEvent) {
                    new SettingsScreen();
                }
            });
            settingsSMenu.add(settingsProject);
            menuBar.add(settingsSMenu);
            frame.setJMenuBar(menuBar);

            JPanel mainFramePanel = new JPanel(null);
            screenSize = new Dimension(frame.getBounds().getSize());
            screenSize.height -= frame.getInsets().bottom;
            screenSize.height -= frame.getInsets().top;
            screenSize.width -= frame.getInsets().left;
            screenSize.width -= frame.getInsets().right;

            frame.setVisible(false);
            mainFramePanel.setBounds(LocationClass.getComponentSize(0, 0,1, 1, new Rectangle(0, 0, screenSize.width, screenSize.height)));
            frame.setContentPane(mainFramePanel);

            ScreensListPanel screensPanel = new ScreensListPanel();
            ExternalDataPanel dataPanel = new ExternalDataPanel();

            screensPanel.setBounds(LocationClass.getComponentSize(0, 0,0.3, 0.5, LocationClass.getParentSize(mainFramePanel)));
            dataPanel.setBounds(LocationClass.getComponentSize(0, 0.5,0.3, 0.5, LocationClass.getParentSize(mainFramePanel)));
            componentsPanel.setBounds(LocationClass.getComponentSize(0.3, 0,0.4, 0.15, LocationClass.getParentSize(mainFramePanel)));
            Rectangle r = LocationClass.getComponentSize(0.35, 0.2,0.30, 0.75, LocationClass.getParentSize(mainFramePanel));
            statePanel.setBounds(r);
            statePanel.heightScreen = r.height;
            statePanel.widthScreen = r.width;
            screenOptionsPanel.setBounds(LocationClass.getComponentSize(0.7, 0,0.3, 0.5, LocationClass.getParentSize(mainFramePanel)));
            objectOptionsPanel.setBounds(LocationClass.getComponentSize(0.7, 0.5,0.3, 0.5, LocationClass.getParentSize(mainFramePanel)));
            scrollButtonsPanel.setBounds(LocationClass.getComponentSize(0.65, 0.2,0.05, 0.75, LocationClass.getParentSize(mainFramePanel)));
            mainFramePanel.add(componentsPanel);
            mainFramePanel.add(screensPanel);
            mainFramePanel.add(dataPanel);
            mainFramePanel.add(statePanel);
            mainFramePanel.add(screenOptionsPanel);
            mainFramePanel.add(objectOptionsPanel);
            mainFramePanel.add(scrollButtonsPanel);

            if (param.compareTo("") == 0){
                projectData = new ProjectData();
            }
            else {
                JSONObject tObject = FileWork.readProjectData("test.json");
                projectData = new ProjectData(tObject);
            }
            statePanel.updateScreen(currentScreen);
            screensPanel.updateScreen();
            frame.setVisible(true);
        }
        catch (IOException e){
            JOptionPane.showMessageDialog(frame, "Ошибка при чтении файла!");
            EnterClass.frame.setVisible(true);
        }
        catch (JSONException ex){
            JOptionPane.showMessageDialog(frame, "Ошибка при чтении данных из файла!");
            EnterClass.frame.setVisible(true);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
