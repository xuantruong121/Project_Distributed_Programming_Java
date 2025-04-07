package util;

import DAO.GeneralDAO;
import Entity.NhanVien;
import lombok.Getter;

@Getter
public class UserSession {
    private static UserSession instance;
    private NhanVien nhanVien;
    private UserSession(NhanVien nhanVien){
        this.nhanVien = nhanVien;
    }
    public static UserSession getInstance(NhanVien nhanVien){
        if(instance == null){
            instance = new UserSession(nhanVien);
        }
        return instance;
    }
    public void cleanUserSession(){
        nhanVien = null;
    }

    public static void main(String[] args) {
        NhanVien nhanvien = new NhanVien();
        nhanvien.setMaNhanVien("RAT-250008");
        GeneralDAO generalDAO= new GeneralDAO();
        NhanVien nhanVien1 = generalDAO.findOb(NhanVien.class, nhanvien.getMaNhanVien());
        if(nhanVien1!=null){
            UserSession userSession = UserSession.getInstance(nhanVien1);
            System.out.println(userSession.getNhanVien().getMaNhanVien());
        }
    }
}
