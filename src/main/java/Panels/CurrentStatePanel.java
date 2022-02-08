package Panels;

import ComponentsDescription.*;
import WorkClasses.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CurrentStatePanel extends JPanel {

    static int dedicated = -1;
    static String dedicatedType = "";
    public int widthScreen, heightScreen;
    private boolean needUpdate = false;

    public CurrentStatePanel(){
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
    }

    public void updateScreen(int x){
        try{
            ScreenData newScreen = RunClass.projectData.screens.get(x);
            RunClass.screenOptionsPanel.updateScreen();
            this.removeAll();
            JPanel tempPanel = this;
            RunClass.objects = new ArrayList<>();
            for (int i = 0; i < newScreen.components.size(); i++){
                ComponentData c = newScreen.components.get(i);
                Object obj = null;
                int finalI = i;

                if (c.type.compareTo("TextField") == 0){
                    obj = new JLabel();
                }
                else if (c.type.compareTo("Button") == 0){
                    obj = new JLabel();
                }
                else if (c.type.compareTo("Image") == 0){
                    obj = new JTextField();
                }
                else if (c.type.compareTo("Video") == 0){
                    obj = new JTextField();
                }

                Object finalObj = obj;
                MouseAdapter clicked = new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        Object[] f = tempPanel.getComponents();
                        for (int i = 0; i < f.length; i++){
                            ComponentData ct = newScreen.components.get(i);
                            if (ct.type.compareTo("TextField") == 0){
                                JLabel tmp = (JLabel) f[i];
                                tmp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                RunClass.objects.set(finalI, tmp);
                            }
                            else if (ct.type.compareTo("Button") == 0){
                                JLabel tmp = (JLabel) f[i];
                                tmp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                RunClass.objects.set(finalI, tmp);
                            }
                            else if (ct.type.compareTo("Image") == 0){
                                JTextField tmp = (JTextField) f[i];
                                tmp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                RunClass.objects.set(finalI, tmp);
                            }
                            else if (ct.type.compareTo("Video") == 0){
                                JTextField tmp = (JTextField) f[i];
                                tmp.setBorder(BorderFactory.createLineBorder(Color.black, 1));
                                RunClass.objects.set(finalI, tmp);
                            }
                        }
                        if (dedicated == finalI){
                            dedicated = -1;
                        }
                        else {
                                if (c.type.compareTo("TextField") == 0){
                                    ((JLabel) finalObj).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                                }
                                else if (c.type.compareTo("Button") == 0){
                                    ((JLabel) finalObj).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                                }
                                else if (c.type.compareTo("Image") == 0){
                                    ((JTextField) finalObj).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                                }
                                else if (c.type.compareTo("Video") == 0){
                                    ((JTextField) finalObj).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
                                }
                            dedicated = finalI;
                            dedicatedType = c.type;
                        }
                        RunClass.objectOptionsPanel.updateScreen();
                    }

                    @Override
                    public void mouseReleased(MouseEvent e) {
                        if (dedicated != finalI && needUpdate) {
                            ((JComponent) finalObj).dispatchEvent(new MouseEvent((JComponent) finalObj, MouseEvent.MOUSE_CLICKED,
                                    System.currentTimeMillis(), 0, 0, 0, 1, false));
                            needUpdate = false;
                        }
                        RunClass.objectOptionsPanel.updateScreen();
                    }
                };
                MouseMotionAdapter moved = new MouseMotionAdapter() {
                    @Override
                    public void mouseDragged(MouseEvent e) {
                        int x = e.getX() + ((JComponent)finalObj).getX();
                        int y = e.getY() + ((JComponent)finalObj).getY();
                        int w =  ((JComponent) finalObj).getWidth();
                        int h = ((JComponent) finalObj).getHeight();
                        if (c.type.compareTo("TextField") == 0){
                            ((JLabel)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Button") == 0){
                            ((JLabel)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Image") == 0){
                            ((JTextField)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Video") == 0){
                            ((JTextField)finalObj).setBounds(x, y, w, h);
                        }
                        ComponentData tmp = newScreen.components.get(finalI);
                        Rectangle r = LocationClass.getScreenToGridSize(x, y, w, h, widthScreen, heightScreen);
                        tmp.kx = r.x;
                        tmp.ky = r.y + RunClass.scrollButtonsPanel.upCoord;
                        newScreen.components.set(finalI, tmp);
                        needUpdate = true;
                    }
                };
                Color g;
                int t;
                if (dedicated == finalI){
                    g = Color.blue;
                    t = 2;
                }
                else {
                    g = Color.black;
                    t = 1;
                }
                if (c.type.compareTo("TextField") == 0){
                    ((JLabel)obj).addMouseListener(clicked);
                    ((JLabel)obj).addMouseMotionListener(moved);
                    ((JLabel)obj).setText(((TextFieldData)c).text);
                    ((JLabel)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                    ((JLabel)obj).setBorder(BorderFactory.createLineBorder(g, t));
                }
                else if (c.type.compareTo("Button") == 0){
                    ((JLabel)obj).addMouseListener(clicked);
                    ((JLabel)obj).addMouseMotionListener(moved);
                    ((JLabel)obj).setOpaque(true);
                    System.setProperty("myColor", ((ButtonData)c).color);
                    Color color = Color.getColor("myColor");
                    ((JLabel)obj).setBackground(color);
                    ((JLabel)obj).setHorizontalAlignment(JLabel.CENTER);
                    ((JLabel)obj).setText(((ButtonData)c).text);
                    ((JLabel)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                    ((JLabel)obj).setBorder(BorderFactory.createLineBorder(g, t));
                }
                else if (c.type.compareTo("Image") == 0){
                    ((JTextField)obj).addMouseListener(clicked);
                    ((JTextField)obj).addMouseMotionListener(moved);
                    ((JTextField)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                }
                else if (c.type.compareTo("Video") == 0){
                    ((JTextField)obj).addMouseListener(clicked);
                    ((JTextField)obj).addMouseMotionListener(moved);
                    ((JTextField)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                }
                RunClass.objects.add(obj);
                add((Component) obj);
                this.updateUI();
            }
        }
        catch (ClassCastException classCastException){
            JOptionPane.showMessageDialog(this, "Ошибка обращения к компоненту. Обратитесь к разработчику.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public boolean isOptimizedDrawingEnabled(){
        return false;
    }

}
