package GUI.Model;

import javax.swing.*;

public class Menu_Model {
    private ImageIcon icon;
    private String name;
    private MenuType type;
    public static  final int sizeOfIcon = 20;
    public enum MenuType {
        TITLE, MENU, EMPTY
    }
    public Menu_Model(ImageIcon icon, String name, MenuType type) {
        this.icon = icon;
        this.name = name;
        this.type = type;
    }
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MenuType getType() {
        return type;
    }

    public void setType(MenuType type) {
        this.type = type;
    }

    public ImageIcon getIcon() {
        return icon;
    }

    public void setIcon(ImageIcon icon) {
        this.icon = icon;
    }
}
