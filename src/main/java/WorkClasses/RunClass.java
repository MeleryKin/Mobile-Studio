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
    private static ProjectData projectData;
    private static ConfigData configData;
    public static ArrayList<Object> objects;
    public static ScrollButtonsPanel scrollButtonsPanel = new ScrollButtonsPanel();
    public static CurrentStatePanel statePanel = new CurrentStatePanel();
    public static ComponentsPanel componentsPanel = new ComponentsPanel();
    private static int currentScreen = 0;
    public static ObjectOptionsPanel objectOptionsPanel = new ObjectOptionsPanel();
    public static ScreenOptionsPanel screenOptionsPanel = new ScreenOptionsPanel();
    public static ScreensListPanel screensPanel = new ScreensListPanel();
    public static ExternalDataPanel dataPanel = new ExternalDataPanel();

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
            JMenuItem generate = new JMenuItem("Создать мобильное приложение");
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
            fileMenu.add(generate);
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
                configData = new ConfigData();
            }
            else {
                JSONObject script = FileWork.readProjectData("pr.json");
                projectData = new ProjectData(script);

                JSONObject config = FileWork.readConfigData("config.json");
                configData = new ConfigData(config);
            }
            statePanel.updateScreen();
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

    public static int getCurrentScreen(){
        return currentScreen;
    }

    public static void setCurrentScreen(int value){
        currentScreen = value;
        CurrentStatePanel.setDedicated(-1);
        CurrentStatePanel.setDedicatedType("");
        scrollButtonsPanel.setEnableUp(false);
        scrollButtonsPanel.upCoord = 0;
        scrollButtonsPanel.downCoord = 320;
        statePanel.updateScreen();
        objectOptionsPanel.updateScreen();
    }

    public static ScreenData getScreen(int number){
        return projectData.screens.get(number);
    }

    public static void setScreen(int number, ScreenData screen){
        projectData.screens.set(number, screen);
        statePanel.updateScreen();
        screenOptionsPanel.updateScreen();
    }

    public static ArrayList<ScreenData> getScreensList(){
        return projectData.screens;
    }

    public static ArrayList<String> getScreensNameList(){
        ArrayList<String> ans = new ArrayList<>();
        for (ScreenData i : projectData.screens){
            ans.add(i.name);
        }
        return ans;
    }

    public static int getScreensCount(){
        return projectData.screens.size();
    }

    public static void addNewScreen(ScreenData newScreen){
        projectData.screens.add(newScreen);
        statePanel.updateScreen();
        objectOptionsPanel.updateScreen();
        screensPanel.updateScreen();
    }

    public static void deleteScreen(int screenIndex){
        currentScreen = 0;
        projectData.screens.remove(screenIndex);
        statePanel.updateScreen();
        objectOptionsPanel.updateScreen();
        screensPanel.updateScreen();
    }

    public static void deleteObject(int screenIndex, int objectIndex){
        projectData.screens.get(screenIndex).components.remove(objectIndex);
        CurrentStatePanel.setDedicated(-1);
        CurrentStatePanel.setDedicatedType("");
        statePanel.updateScreen();
        objectOptionsPanel.updateScreen();
        screensPanel.updateScreen();
    }

    public static int getScreenID(String name){
        for (int i = 0; i < projectData.screens.size(); i++){
            ScreenData data = projectData.screens.get(i);
            if (data.name.compareTo(name) == 0){
                return i;
            }
        }
        return -1;
    }

    public static int getObjectID(int screenIndex, String name){
        ScreenData screen = projectData.screens.get(screenIndex);
        for (int i = 0; i < screen.components.size(); i++){
            ComponentData component = screen.components.get(i);
            if (component.name.compareTo(name) == 0){
                return i;
            }
        }
        return -1;
    }

    public static ComponentData getObject(int screenIndex, int objectIndex){
        return projectData.screens.get(screenIndex).components.get(objectIndex);
    }

    public static void setObject(int screenIndex, int objectIndex, ComponentData component){
        projectData.screens.get(screenIndex).components.set(objectIndex, component);
        statePanel.updateScreen();
        objectOptionsPanel.updateScreen();
    }

    public static String getProjectName(){
        return configData.name;
    }

    public static void setProjectName(String name){
        configData.name = name;
    }

    public static String getIconPath(){
        return configData.projectIcon;
    }

    public static void setIconPath(String path){
        configData.projectIcon = path;
    }

    public static String getStartScreen(){
        return configData.startScreen;
    }

    public static void setStartScreen(String screen){
        configData.startScreen = screen;
    }

    public static ParameterData getParameter(int x){
        return configData.parameters.get(x);
    }

    public static ParameterData getParameter(String name){
        for (int i = 0; i < configData.parameters.size(); i++){
            ParameterData x = configData.parameters.get(i);
            if (x.name.compareTo(name) == 0){
                return x;
            }
        }
        return null;
    }

    public static ArrayList<String> getParametersNameList(){
        ArrayList<String> ans = new ArrayList<>();
        for (ParameterData i : configData.parameters){
            ans.add(i.name);
        }
        return ans;
    }

    public static int getParametersCount(){
        return configData.parameters.size();
    }

    public static void setParameter(int i, ParameterData x){
        configData.parameters.set(i, x);
    }

    public static void addParameter(ParameterData x){
        configData.parameters.add(x);
    }

    public static void deleteParameter(int x){
        configData.parameters.remove(x);
    }

    @Override
    public void actionPerformed(ActionEvent e) {

    }
}
