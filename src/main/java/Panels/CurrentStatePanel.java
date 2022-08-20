package Panels;

import ComponentsDescription.*;
import WorkClasses.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class CurrentStatePanel extends JPanel {

    private static int dedicated = -1;
    private static String dedicatedType = "";
    public int widthScreen, heightScreen;
    private boolean needUpdate = false;
    private Point coord = new Point();
    private int moveMode = 0;
    private JPanel panel;

    public CurrentStatePanel(){
        setLayout(null);
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createLineBorder(Color.CYAN, 1));
        panel = this;
    }

    public void updateScreen(){
        try{
            RunClass.screenOptionsPanel.updateScreen();
            RunClass.dataPanel.updateScreen();
            this.removeAll();
            ScreenData newScreen = RunClass.getScreen(RunClass.getCurrentScreen());

            Color backgroundColor = new Color(newScreen.color[0], newScreen.color[1], newScreen.color[2]);
            this.setBackground(backgroundColor);
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
                    obj = new JLabel();
                }
                else if (c.type.compareTo("Video") == 0){
                    obj = new JTextField();
                }

                Object finalObj = obj;
                MouseAdapter clicked = new MouseAdapter() {
                    @Override
                    public void mousePressed(MouseEvent e) {
                        coord.x = e.getPoint().x;
                        coord.y = e.getPoint().y;
                        if (coord.x <= 10 && coord.x >= 0 && coord.y <= 10 && coord.y >= 0) moveMode = 1;
                        else if (coord.x <= ((JComponent)finalObj).getWidth() &&
                                coord.x >= ((JComponent)finalObj).getWidth() - 10 &&coord.y <= 10 && coord.y >= 0) moveMode = 2;
                        else if (coord.x <= 10 && coord.x >= 0 && coord.y <= ((JComponent)finalObj).getHeight() &&
                                coord.y >= ((JComponent)finalObj).getHeight() - 10)moveMode = 3;
                        else if (coord.x <= ((JComponent)finalObj).getWidth() &&
                                coord.x >= ((JComponent)finalObj).getWidth() - 10 && coord.y <= ((JComponent)finalObj).getHeight() &&
                                coord.y >= ((JComponent)finalObj).getHeight() - 10) moveMode = 4;
                        else moveMode = 0;
                    }

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
                                JLabel tmp = (JLabel) f[i];
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
                                    ((JLabel) finalObj).setBorder(BorderFactory.createLineBorder(Color.blue, 2));
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
                        ComponentData tmp = newScreen.components.get(finalI);
                        int x = 0, y = 0, w = 0, h = 0;
                        if (moveMode == 0){
                            x = e.getX() + ((JComponent)finalObj).getX() - coord.x;
                            y = e.getY() + ((JComponent)finalObj).getY() - coord.y;
                            w =  ((JComponent) finalObj).getWidth();
                            h = ((JComponent) finalObj).getHeight();
                        }
                        else if (moveMode == 1){
                            x = e.getX() + ((JComponent)finalObj).getX() - coord.x;
                            y = e.getY() + ((JComponent)finalObj).getY() - coord.y;
                            w =  ((JComponent) finalObj).getWidth() - e.getX() + coord.x;
                            h = ((JComponent) finalObj).getHeight() - e.getY() + coord.y;
                        }
                        else if (moveMode == 2){
                            x = ((JComponent)finalObj).getX();
                            y = ((JComponent)finalObj).getY();
                            w =  ((JComponent) finalObj).getWidth() - (((JComponent) finalObj).getWidth() - e.getX() + coord.x);
                            h = ((JComponent) finalObj).getHeight() - (((JComponent) finalObj).getHeight() - e.getY() + coord.y);
                        }
                        else if (moveMode == 3){
                            x = ((JComponent)finalObj).getX();
                            y = ((JComponent)finalObj).getY();
                            w =  ((JComponent) finalObj).getWidth() - e.getX() + coord.x;
                            h = ((JComponent) finalObj).getHeight() - e.getY() + coord.y;
                        }
                        else if (moveMode == 4){
                            x = ((JComponent)finalObj).getX();
                            y = ((JComponent)finalObj).getY();
                            w =  ((JComponent) finalObj).getWidth() - (((JComponent) finalObj).getWidth() - e.getX() + coord.x);
                            h = ((JComponent) finalObj).getHeight() - (((JComponent) finalObj).getHeight() - e.getY() + coord.y);
                        }
                        if (c.type.compareTo("TextField") == 0){
                            ((JLabel)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Button") == 0){
                            ((JLabel)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Image") == 0){
                            ((JLabel)finalObj).setBounds(x, y, w, h);
                        }
                        else if (c.type.compareTo("Video") == 0){
                            ((JTextField)finalObj).setBounds(x, y, w, h);
                        }
                        Rectangle r = LocationClass.getScreenToGridSize(x, y, w, h, widthScreen, heightScreen);
                        tmp.kx = r.x;
                        tmp.ky = r.y + RunClass.scrollButtonsPanel.upCoord;
                        tmp.w = r.width;
                        tmp.h = r.height;
                        newScreen.components.set(finalI, tmp);
                        RunClass.resetSave();
                        needUpdate = true;
                        RunClass.objectOptionsPanel.updateScreen();
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
                    //System.setProperty("myColor", );
                    Color color = new Color(((ButtonData)c).colorBackground[0], ((ButtonData)c).colorBackground[1], ((ButtonData)c).colorBackground[2]);
                    ((JLabel)obj).setBackground(color);
                    ((JLabel)obj).setHorizontalAlignment(JLabel.CENTER);
                    ((JLabel)obj).setText(((ButtonData)c).text);
                    ((JLabel)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                    ((JLabel)obj).setBorder(BorderFactory.createLineBorder(g, t));
                }
                else if (c.type.compareTo("Image") == 0){
                    File f;
                    f = new File("xiao.jpg");
                    try {
                        BufferedImage img = ImageIO.read(f);
                        obj = new JLabel(new ImageIcon(img));
                    }
                    catch (Exception jex){
                        jex.printStackTrace();
                    }
                    ((JLabel)obj).addMouseListener(clicked);
                    ((JLabel)obj).addMouseMotionListener(moved);
                    ((JLabel)obj).setBounds(LocationClass.getGridToScreenSize(c.kx, c.ky - RunClass.scrollButtonsPanel.upCoord, c.w, c.h, widthScreen, heightScreen));
                    ((JLabel)obj).setBorder(BorderFactory.createLineBorder(g, t));
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
            this.updateUI();
        }
        catch (ClassCastException classCastException){
            JOptionPane.showMessageDialog(this, "Ошибка обращения к компоненту. Обратитесь к разработчику.");
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static int getDedicated(){
        return dedicated;
    }

    public static void setDedicated(int value){
        dedicated = value;
    }

    public static String getDedicatedType(){
        return dedicatedType;
    }

    public static void setDedicatedType(String type){
        dedicatedType = type;
    }

    @Override
    public boolean isOptimizedDrawingEnabled(){
        return false;
    }

}
