package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import iuh.fit.qlksfxapp.controller.EventBus.NavigationEvent;
import iuh.fit.qlksfxapp.util.FormatUtil;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;

public class InfoDetailBookingRoomController {
    @FXML
    private Label tenKhacHang,
            tenNhanVien,
            treEmVaNguoiLon,
            ngayNhan,
            tenDoan,
            ngayDat,
            ngayTra,trangThaiDon;
    @FXML private VBox contentPane,emptyPane;
    private DonDatPhong donDatPhong;
    public void setData(DonDatPhong donDatPhong) {
        if(donDatPhong == null){
            contentPane.setVisible(false);
            contentPane.setManaged(false);

            emptyPane.setVisible(true);
            emptyPane.setManaged(true);
            return;
        }
        this.donDatPhong = donDatPhong;
        tenKhacHang.setText(donDatPhong.getKhachHang().getTenKhachHang());
        tenNhanVien.setText(donDatPhong.getNhanVien().getTenNhanVien());
        treEmVaNguoiLon.setText(donDatPhong.getSoLuongTreEm() + " trẻ em, " + donDatPhong.getSoLuongNguoiLon() + " người lớn");
        ngayNhan.setText(FormatUtil.formatLocalDateTime(donDatPhong.getNgayNhan()));
        tenDoan.setText(donDatPhong.getTenDoan());
        ngayDat.setText(FormatUtil.formatLocalDateTime(donDatPhong.getNgayDat()));
        ngayTra.setText(FormatUtil.formatLocalDateTime(donDatPhong.getNgayTra()));
        trangThaiDon.setText(donDatPhong.getTrangThai().s);
    }
    @FXML
    private void handleXemThem() throws RemoteException {
        if(donDatPhong==null)
            return;
        Map<Phong,DonDatPhong> map =new HashMap<>();
        GeneralDAO generalDAO= new GeneralDAOImpl();
        map.put(new Phong(),generalDAO.findOb(DonDatPhong.class,donDatPhong.getMaDonDatPhong()));
        EventBusManager.post(new NavigationEvent(
                "/fxml/BookingForm.fxml",
                map
        ));
    }
}
