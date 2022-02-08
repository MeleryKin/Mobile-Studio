package WorkClasses;

import java.awt.*;

public class LocationClass {

    private static int gridWidth = 240, gridHeight = 320;

    public static Rectangle getComponentSize(double kx, double ky, double dx, double dy, Rectangle r){
        return new Rectangle((int)(r.x+(r.width*kx)), (int)(r.y+(r.height*ky)),
                (int)(r.width*dx), (int)(r.height*dy));
    }

    public static Rectangle getParentSize(Component component){
        return new Rectangle(component.getX(), component.getY(), component.getWidth(), component.getHeight());
    }

    public static Rectangle getGridToScreenSize(int x, int y, int width, int height, int widthScreen, int heightScreen){
        x = x * widthScreen / gridWidth;
        width = width * widthScreen / gridWidth;
        y = y * heightScreen / gridHeight;
        height = height * heightScreen / gridHeight;
        return new Rectangle(x, y, width, height);
    }

    public static Rectangle getScreenToGridSize(int x, int y, int width, int height, int widthScreen, int heightScreen){
        x = x * gridWidth / widthScreen;
        width = width * gridWidth / widthScreen;
        y = y * gridHeight / heightScreen;
        height = height * gridHeight /heightScreen;
        return new Rectangle(x, y, width, height);
    }
}
