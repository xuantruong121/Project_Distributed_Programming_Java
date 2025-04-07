package nhap;

import util.raven.drawer.MyDrawerBuilder;

import java.net.URL;

public class TestloadImg {
    public static void main(String[] args) {
        String iconPath ="/pic/icon/dashboard.svg";
        URL iconURL = MyDrawerBuilder.class.getResource(iconPath);
        if (iconURL == null) {
            System.err.println("Không tìm thấy icon: " + iconPath);
        } else {
            System.out.println("Icon loaded từ: " + iconURL);
        }

    }
}
