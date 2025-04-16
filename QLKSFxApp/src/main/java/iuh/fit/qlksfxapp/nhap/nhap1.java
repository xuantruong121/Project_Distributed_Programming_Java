package iuh.fit.qlksfxapp.nhap;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;

import java.util.List;

public class nhap1 {
    public static void main(String[] args) {
        GeneralDAO generalDAO =new GeneralDAO();
        List<LoaiPhong> loaiPhongList = generalDAO.findAll(LoaiPhong.class);
        System.out.println("so luong loai phong: " + loaiPhongList.size());
    }
}
