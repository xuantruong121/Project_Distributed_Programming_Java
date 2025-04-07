package util.raven.drawer;

import raven.drawer.component.SimpleDrawerBuilder;
import raven.drawer.component.footer.SimpleFooterData;
import raven.drawer.component.header.SimpleHeaderData;
import raven.drawer.component.menu.MenuAction;
import raven.drawer.component.menu.MenuEvent;
import raven.drawer.component.menu.MenuValidation;
import raven.drawer.component.menu.SimpleMenuOption;
import util.raven.form.TestForm;
import main.Main;
import raven.swing.AvatarIcon;
import util.raven.tabbed.WindowsTabbed;

/**
 *
 * @author RAVEN
 */
public class MyDrawerBuilder extends SimpleDrawerBuilder {

    @Override
    public SimpleHeaderData getSimpleHeaderData() {
            return new SimpleHeaderData()
                    .setIcon(new AvatarIcon("D:/logo.png",275,150,0));
    }
    @Override
    public SimpleMenuOption getSimpleMenuOption() {
        String menus[][] = {
            {"~MAIN~"},
            {"Trang chủ"},
            {"~Chức năng~"},
            {"Quản lý đặt phòng", "Đơn đặt phòng", "Bản đồ phòng", "Thông tin loại phòng"},
            {"Thống kê","Doanh thu","Kho","Khác"},
            {"Quản lý kho"},
                {"Quản lý khuyến mãi"},
                {"Quản lý đơn báo cáo"},
            };

        String icons[] = {
            "dashboard.svg",
            "email.svg",
            "chat.svg",
            "calendar.svg",
            "ui.svg",
            "forms.svg",
            "chart.svg",
            "icon.svg",
            "page.svg",
            "logout.svg"};

        return new SimpleMenuOption()
                .setMenus(menus)
                .setIcons(icons)
                .setBaseIconPath("pic/icon")
                .setIconScale(0.45f)
                .addMenuEvent(new MenuEvent() {
                    @Override
                    public void selected(MenuAction action, int index, int subIndex) {
                        if (index == 0) {
                            WindowsTabbed.getInstance().addTab("Test Form", new TestForm());
                        } else if (index == 9) {
                            Main.main.login();
                        }
                        System.out.println("Menu selected " + index + " " + subIndex);
                    }
                })
                .setMenuValidation(new MenuValidation() {
                    @Override
                    public boolean menuValidation(int index, int subIndex) {
//                        if(index==0){
//                            return false;
//                        }else if(index==3){
//                            return false;
//                        }
                        return true;
                    }

                });
    }

    @Override
    public SimpleFooterData getSimpleFooterData() {
        return new SimpleFooterData()
                .setTitle("Java Swing Drawer")
                .setDescription("Version 1.1.0");
    }

    @Override
    public int getDrawerWidth() {
        return 310;
    }
}
