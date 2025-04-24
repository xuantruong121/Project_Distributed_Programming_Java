package iuh.fit.qlksfxapp.util;

import iuh.fit.qlksfxapp.DAO.*;
import iuh.fit.qlksfxapp.DAO.Impl.ChiTietDonDatPhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.DonDatPhongDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.DAO.Impl.PhongDAOImpl;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.MapOfRoomController;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.rmi.RemoteException;
import java.util.*;
import java.util.stream.Collectors;

public class ManageTrangThaiPhong {

    // Chứa thông tin trạng thái, phòng, và đơn đặt phòng nếu có
    public static Map<TrangThaiPhong, List<PhongDonDatWrapper>> legendPhongTheoTrangThai = new HashMap<>();
    public static List<Phong> listPhongTrong = new ArrayList<>();
    public static List<Phong> listPhong = new ArrayList<>();

    private DonDatPhongDAOImpl donDatPhongDAO;
    private PhongDAOImpl phongDAO;
    private ChiTietDonDatPhongDAOImpl chiTietDonDatPhongDAO;
    private MapOfRoomController mapOfRoomController;
    public ManageTrangThaiPhong(MapOfRoomController mapOfRoomController) {
        setMapOfRoomController(mapOfRoomController);
        if(legendPhongTheoTrangThai.isEmpty()) {
            GeneralDAO generalDAO = new GeneralDAOImpl();
            try {
                listPhong= generalDAO.findAll(Phong.class);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
            loadData();
        }
    }
    public void setMapOfRoomController(MapOfRoomController mapOfRoomController) {
        this.mapOfRoomController = mapOfRoomController;
        donDatPhongDAO = mapOfRoomController.getDonDatPhongDAO();
        phongDAO = mapOfRoomController.getPhongDAO();
        chiTietDonDatPhongDAO = mapOfRoomController.getChiTietDonDatPhongDAO();
    }
    public void loadData() {
        // Load đơn đặt phòng đang sử dụng hoặc đặt trước
        updatePhongDangSuDungVaDatTruoc();

        // Load các phòng theo trạng thái đơn lẻ
        updatePhongConLai();

        // Xác định các phòng "trống"
        updatePhongTrong();
    }
    public void putPhongDonDatPhong(List<DonDatPhong> donDatPhongs, TrangThaiPhong trangThai) {
        EntityManager em = EntityManagerUtil.getEntityManagerFactory().createEntityManager();
        EntityTransaction transaction = null;
        List<PhongDonDatWrapper> wrappers = new ArrayList<>();
        try {
            transaction = em.getTransaction();
            transaction.begin();
            for (DonDatPhong ddp : donDatPhongs) {
                // Sử dụng EntityManager để query thay vì DAO
                List<ChiTietDonDatPhong> chiTietDonDatPhongs = em.createQuery(
                                "SELECT c FROM ChiTietDonDatPhong c " +
                                        "WHERE c.donDatPhong.maDonDatPhong = :maDonDatPhong", ChiTietDonDatPhong.class)
                        .setParameter("maDonDatPhong", ddp.getMaDonDatPhong())
                        .getResultList();
                for (ChiTietDonDatPhong c : chiTietDonDatPhongs) {
                    if (c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG) ||
                            c.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DAT_TRUOC)) {
                        // Sử dụng EntityManager để load phong nếu cần
                        Phong p = c.getPhong();
                        if (p != null) {
                            // Đảm bảo phong là managed entity
                            Phong managedPhong = em.find(Phong.class, p.getMaPhong());
                           wrappers.add(new PhongDonDatWrapper(managedPhong, ddp));
                        }
                    }
                }
            }
            transaction.commit();
            legendPhongTheoTrangThai.put(trangThai, wrappers);

        } catch (Exception e) {
            if (transaction != null && transaction.isActive()) {
                transaction.rollback();
            }
            throw new RuntimeException("Lỗi khi lấy dữ liệu phòng đơn đặt", e);
        } finally {
            if (em != null && em.isOpen()) {
                em.close();
            }
        }
    }
    public List<PhongDonDatWrapper> getPhongsByTrangThai(TrangThaiPhong trangThai) {
        return legendPhongTheoTrangThai.getOrDefault(trangThai, List.of());
    }
    public List<Phong> getPhongByTrangThai(TrangThaiPhong trangThai) {
        return legendPhongTheoTrangThai.getOrDefault(trangThai, List.of()).stream()
                .map(PhongDonDatWrapper::getPhong)
                .collect(Collectors.toList());
    }
    public DonDatPhong getDonDatPhongByMaPhong(String maPhong) {
        for (List<PhongDonDatWrapper> wrappers : legendPhongTheoTrangThai.values()) {
            for (PhongDonDatWrapper wrapper : wrappers) {
                if (wrapper.getPhong().getMaPhong().equals(maPhong)) {
                    return wrapper.getDonDatPhong();
                }
            }
        }
        return null;
    }
    public Integer getNumberOfPhongByTrangThai(TrangThaiPhong trangThai) {
        return legendPhongTheoTrangThai.getOrDefault(trangThai, List.of()).size();
    }
    public List<Phong> getAllPhong() {
        return legendPhongTheoTrangThai.values().stream()
                .flatMap(List::stream)
                .map(PhongDonDatWrapper::getPhong)
                .collect(Collectors.toList());
    }
    public void updatePhongConLai(){
        for (TrangThaiPhong ttp : List.of(
                TrangThaiPhong.DANG_DON_DEP,
                TrangThaiPhong.DANG_SUA_CHUA,
                TrangThaiPhong.KHONG_SU_DUNG
        )) {
            List<Phong> phongs = phongDAO.getListPhongTheoTrangThaiPhongDANG_DON_DEP_DANG_SUA_CHUA_KHONG_SU_DUNG(ttp);
            List<PhongDonDatWrapper> wrappers = phongs.stream()
                    .map(p -> new PhongDonDatWrapper(p, null))
                    .collect(Collectors.toList());
            legendPhongTheoTrangThai.put(ttp, wrappers);
        }
    }
    public void updatePhongDangSuDungVaDatTruoc(){
        donDatPhongDAO.closeEntityManager();
        donDatPhongDAO= new DonDatPhongDAOImpl();
        try {
            List<DonDatPhong> dangSuDung = donDatPhongDAO.getListDonDatPhongTheoTrangThaiPhongDANG_SU_DUNG();
            List<DonDatPhong> datTruoc = donDatPhongDAO.getListDonDatPhongTheoTrangThaiPhongDAT_TRUOC();

            putPhongDonDatPhong(dangSuDung, TrangThaiPhong.DANG_SU_DUNG);
            putPhongDonDatPhong(datTruoc, TrangThaiPhong.DAT_TRUOC);
        } catch (RemoteException e) {
            e.printStackTrace();
            System.err.println("Error updating room status: " + e.getMessage());
        }
    }
    public void updatePhongTrong(){
        // Xác định các phòng "trống"
        Set<Phong> usedPhongs = legendPhongTheoTrangThai.values().stream()
                .flatMap(List::stream)
                .map(PhongDonDatWrapper::getPhong)
                .collect(Collectors.toSet());

        for (Phong p : listPhong) {
            if (!usedPhongs.contains(p)) {
                listPhongTrong.add(p);
            }
        }

        // Đưa vào map
        legendPhongTheoTrangThai.put(
                TrangThaiPhong.TRONG,
                listPhongTrong.stream()
                        .map(p -> new PhongDonDatWrapper(p, null))
                        .collect(Collectors.toList())
        );
    }
    public void callInitLegendAtMapOfRoomController() {
        if (mapOfRoomController != null) {
            mapOfRoomController.initLegend();
        }
    }
    // Lớp wrapper để giữ cả Phong và DonDatPhong
    @Getter
    @Setter
    @AllArgsConstructor
    public static class PhongDonDatWrapper {
        private final Phong phong;
        private final DonDatPhong donDatPhong;
    }

    public static void main(String[] args) {
//        ManageTrangThaiPhong manageTrangThaiPhong = new ManageTrangThaiPhong();
        for (Map.Entry<TrangThaiPhong, List<PhongDonDatWrapper>> entry : legendPhongTheoTrangThai.entrySet()) {
            System.out.println("Trạng thái: " + entry.getKey());
            for (PhongDonDatWrapper wrapper : entry.getValue()) {
                System.out.println("  Phòng: " + wrapper.getPhong().getMaPhong() +
                        ", Đơn đặt phòng: " + (wrapper.getDonDatPhong() != null ? wrapper.getDonDatPhong().getMaDonDatPhong() : "Không có"));
            }
        }
    }
}
