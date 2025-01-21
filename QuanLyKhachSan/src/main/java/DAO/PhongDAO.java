package DAO;

import Entity.Phong;
import jakarta.persistence.EntityManager;

import java.util.List;

public class PhongDAO {
    public static List<Phong> getDanhSachPhong(){
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        List<Phong> list = null;
        try {
            list = em.createQuery("SELECT p FROM Phong p", Phong.class).getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return list;
    }

    public static void main(String[] args) {
        List<Phong> list = getDanhSachPhong();
        for (Phong phong : list) {
            System.out.println("MaPhong: " + phong.getMaPhong());
            System.out.println("ViTri: " + phong.getViTri());
            System.out.println("HinhAnh: " + phong.getHinhAnh());
            System.out.println("MoTa: " + phong.getMoTa());
            System.out.println("TrangThaiPhong: " + phong.getTrangThaiPhong());
            System.out.println("LoaiPhong: " + (phong.getLoaiPhong() != null ? phong.getLoaiPhong().getTenLoaiPhong() : "null"));
            System.out.println("------");
        }
    }
}
