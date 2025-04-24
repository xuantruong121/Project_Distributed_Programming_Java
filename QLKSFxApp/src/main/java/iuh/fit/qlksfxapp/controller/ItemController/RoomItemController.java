package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.DAO.GeneralDAO;
import iuh.fit.qlksfxapp.DAO.PhongDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.controller.EventBus.ConfirmDialogEvent;
import iuh.fit.qlksfxapp.controller.EventBus.EventBusManager;
import iuh.fit.qlksfxapp.controller.EventBus.ToastEvent;
import iuh.fit.qlksfxapp.controller.MapOfRoomController;
import iuh.fit.qlksfxapp.util.ManageTrangThaiPhong;
import iuh.fit.qlksfxapp.controller.EventBus.NavigationEvent;
import iuh.fit.qlksfxapp.controller.MainController;
import iuh.fit.qlksfxapp.util.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public class RoomItemController implements MainController.DataReceivable {
    @FXML private VBox roomItem;
    @FXML
    private Label typeRoomAndId,
            status,
            soLuongTre,
            soLuongNguoiLon,
            tenNguoiDat,
            tenDoan,
            ngayO;
    @FXML
    private ImageView imageRoom;
    @FXML
    private Button button1,button2;
    private Consumer<Node> onItemClickedCallback;
    private Phong phong;
    private DonDatPhong donDatPhong;
    private ManageTrangThaiPhong manageTrangThaiPhong;
    private GeneralDAO generalDAO;
    private MapOfRoomController mapOfRoomController;
    private ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    private  PhongDAO phongDAO;
    public  void setManageTrangThaiPhong(ManageTrangThaiPhong manageTrangThaiPhong) {
        this.manageTrangThaiPhong = manageTrangThaiPhong;
    }
    public  void setData(Phong phong, DonDatPhong donDatPhong, Consumer<Node> callback) {
        this.generalDAO = new GeneralDAO();
        this.onItemClickedCallback = callback;
        this.phong = phong;
        this.donDatPhong = donDatPhong;
        typeRoomAndId.setText(phong.getLoaiPhong().getTenLoaiPhong() + " / " + phong.getMaPhong());
        try {
            // Construct the image path
            String imagePath = "/images/room/" + phong.getHinhAnh();
            // Get the resource URL
            URL imageUrl = getClass().getResource(imagePath);
            System.out.println("Image URL: " + imageUrl);
            if (imageUrl != null) {
                // Create and set the image
                Image image = new Image(imageUrl.toExternalForm());
                imageRoom.setImage(image);
            } else {
                // Handle case when image doesn't exist
                System.err.println("Image not found: " + imageUrl);
                // Optionally set a default image
                URL defaultImageUrl = getClass().getResource("/images/placeholder.png");
                if (defaultImageUrl != null) {
                    imageRoom.setImage(new Image(defaultImageUrl.toExternalForm()));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            // Handle any other errors
        }

        roomItem.getStyleClass().add("room-item");
        status.getStyleClass().add("lable-status");
        button1.getStyleClass().add("button-item");
        button2.getStyleClass().add("button-item");
        if(donDatPhong==null || donDatPhong.getMaDonDatPhong()==null){
            status.setText(phong.getTrangThaiPhong().s);
            tenNguoiDat.setText("Chưa có khách");
            tenDoan.setText("");
            ngayO.setText("");
            setStyle(phong.getTrangThaiPhong());
            return;
        }
        soLuongTre.setText(donDatPhong.getSoLuongTreEm()+" Trẻ");
        soLuongNguoiLon.setText(donDatPhong.getSoLuongNguoiLon()+" Người lớn");
        tenNguoiDat.setText(donDatPhong.getKhachHang().getTenKhachHang());
        tenDoan.setText(donDatPhong.getTenDoan());
        getNgayO(donDatPhong.getNgayNhan(), donDatPhong.getNgayTra());
        // Chưa có ảnh
        ChiTietDonDatPhong chiTietDonDatPhong = chiTietDonDatPhongDAO.findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(donDatPhong.getMaDonDatPhong(), phong.getMaPhong());
        if(chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DAT_TRUOC)){
            setStyle(TrangThaiPhong.DAT_TRUOC);
        } else if (chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().equals(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG)){
            setStyle(TrangThaiPhong.DANG_SU_DUNG);
        }else{
            status.setText(phong.getTrangThaiPhong().s);
            tenNguoiDat.setText("Chưa có khách");
            tenDoan.setText("");
            ngayO.setText("");
            setStyle(phong.getTrangThaiPhong());
        }
    }
    private void getNgayO(LocalDateTime ngayNhan, LocalDateTime ngayDi){
        // 10/12 - 11/12 (1)
        String ngayO = ngayNhan.getDayOfMonth() + "/" + ngayNhan.getMonthValue() + " - " + ngayDi.getDayOfMonth() + "/" + ngayDi.getMonthValue();
       String soLuongNgay = String.valueOf(ngayDi.getDayOfMonth() - ngayNhan.getDayOfMonth());
        if (soLuongNgay.equals("0")){
            soLuongNgay = "1";
        }
        this.ngayO.setText(ngayO + " (" + soLuongNgay + ")");
    }
    private void setStyle(TrangThaiPhong trangThaiPhong) {
        int indexStatus = trangThaiPhong.ordinal();
        String[] txfillStatus = {
                "txfill-trong",
                "txfill-dangsudung",
                "txfill-dangdondep",
                "txfill-dattruoc",
                "txfill-dangsuachua",
                "txfill-khongsudung"
        };
        String[] bgStatus = {
                "bg-trong",
                "bg-dangsudung",
                "bg-dangdondep",
                "bg-dattruoc",
                "bg-dangsuachua",
                "bg-khongsudung"
        };
        String[] brStatus = {
                "br-trong",
                "br-dangsudung",
                "br-dangdondep",
                "br-dattruoc",
                "br-dangsuachua",
                "br-khongsudung"
        };

        // Lấy class ứng với trạng thái
        String bgClassName = bgStatus[indexStatus];
        String txfillClassName = txfillStatus[indexStatus];
        String brClassName = brStatus[indexStatus];

        // Danh sách các node cần áp dụng style
        List<Label> nodes = Arrays.asList(
                typeRoomAndId, tenNguoiDat, tenDoan, ngayO
        );
        for (Label label : nodes) {
            label.getStyleClass().removeIf(style -> style.startsWith("txfill-")); // Xóa tất cả các class bắt đầu bằng "txfill-"
            label.getStyleClass().add(txfillClassName);           // Thêm class mới
        }
        roomItem.getStyleClass().removeIf(style -> style.startsWith("br-")); // Xóa tất cả các class bắt đầu bằng "br-"
        roomItem.getStyleClass().add(brClassName);           // Thêm class mới

        status.setText(trangThaiPhong.s); // Đặt lại text cho label status
        status.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
        status.getStyleClass().add(bgClassName);
        // button
        if(trangThaiPhong.equals(TrangThaiPhong.TRONG)) {
            button1.setDisable(false);
            button2.setDisable(true);
            button1.setText("Đặt phòng");
            button2.setText("Xem đơn đặt");
            button1.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button1.getStyleClass().add("bg-green-button");
            button2.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button2.getStyleClass().add("bg-disable-button");
        } else if ( trangThaiPhong.equals(TrangThaiPhong.DANG_SU_DUNG)) {
            button1.setDisable(false);
            button2.setDisable(false);
            button1.setText("Trả phòng");
            button2.setText("Xem đơn đặt");
            button1.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button1.getStyleClass().add("bg-green-button");
            button2.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button2.getStyleClass().add("bg-blue-button");
        } else if (trangThaiPhong.equals(TrangThaiPhong.DANG_DON_DEP)) {
            button1.setDisable(false);
            button2.setDisable(false);
            button1.setText("Hoàn thành");
            button2.setText("Hủy");
            button1.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button1.getStyleClass().add("bg-green-button");
            button2.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button2.getStyleClass().add("bg-blue-button");
        } else if (trangThaiPhong.equals(TrangThaiPhong.DAT_TRUOC)) {
            button1.setDisable(false);
            button2.setDisable(false);
            button1.setText("Nhận phòng");
            button2.setText("Hủy đặt");
            button1.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button1.getStyleClass().add("bg-green-button");
            button2.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button2.getStyleClass().add("bg-blue-button");
        } else {
            button1.setDisable(true);
            button2.setDisable(true);
            button1.setText("Đặt phòng");
            button2.setText("Xem đơn đặt");
            button1.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button1.getStyleClass().add("bg-disable-button");
            button2.getStyleClass().removeIf(style -> style.startsWith("bg-")); // Xóa tất cả các class bắt đầu bằng "bg-"
            button2.getStyleClass().add("bg-disable-button");
        }

    }
    @FXML
    private void handleClickInfo(){
        if (onItemClickedCallback != null) {
            onItemClickedCallback.accept(roomItem);
        }
        // chuyển sang dơn dat phong
    }
    public void setMapOfRoomController(MapOfRoomController mapOfRoomController) {
        this.mapOfRoomController = mapOfRoomController;
        chiTietDonDatPhongDAO = mapOfRoomController.getChiTietDonDatPhongDAO();
        phongDAO= mapOfRoomController.getPhongDAO();
    }
    @FXML
    private void handleClickButton(ActionEvent event) throws IOException {
        Button clickedButton = (Button) event.getSource();
        switch (clickedButton.getText()) {
            // trong button
            case "Đặt phòng": {
                // don dat phong la null
                Map<Phong, DonDatPhong> map = Map.of(phong, new DonDatPhong());
                EventBusManager.post(new NavigationEvent(
                        "/fxml/BookingForm.fxml",
                        map
                ));
                break;
            }
            case "Xem đơn đặt": {
                // don dat phong la null
                Map<Phong, DonDatPhong> map = Map.of(phong, donDatPhong==null? new DonDatPhong(): donDatPhong);
                EventBusManager.post(new NavigationEvent(
                        "/fxml/BookingForm.fxml",
                        map
                ));
                break;
            }
            // DAT_TRUOC
            case "Nhận phòng": {
                if (chiTietDonDatPhongDAO == null)
                    chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
                ChiTietDonDatPhong chiTietDonDatPhong = chiTietDonDatPhongDAO.findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(donDatPhong.getMaDonDatPhong(), phong.getMaPhong());
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn nhận phòng?",
                        () -> {
                            // cập nhật trạng thái phòng
                            chiTietDonDatPhong.setNgayNhan(LocalDateTime.now());
                            chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_NHAN_PHONG);

                            boolean r= generalDAO.updateOb(chiTietDonDatPhong);
                            if(r){
                                setData(phong, donDatPhong, onItemClickedCallback);
                                manageTrangThaiPhong.updatePhongDangSuDungVaDatTruoc();
                                manageTrangThaiPhong.updatePhongTrong();
                                manageTrangThaiPhong.callInitLegendAtMapOfRoomController();
                            }
                            return r;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Nhận phòng thành công", "Nhận phòng thất bại")
                ));
                break;
            }
            case "Hủy đặt": {
                if (chiTietDonDatPhongDAO == null)
                    chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
                ChiTietDonDatPhong chiTietDonDatPhong = chiTietDonDatPhongDAO.findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(donDatPhong.getMaDonDatPhong(), phong.getMaPhong());
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn hủy đặt phòng?",
                        () -> {
                            chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_HUY);
                            boolean r= generalDAO.updateOb(chiTietDonDatPhong);
                            if(r){
                                setData(phong, null, onItemClickedCallback);
                                manageTrangThaiPhong.updatePhongDangSuDungVaDatTruoc();
                                manageTrangThaiPhong.updatePhongTrong();
                                manageTrangThaiPhong.callInitLegendAtMapOfRoomController();
                            }

                            return r;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Hủy đặt phòng thành công", "Hủy đặt phòng thất bại")
                ));
                break;
            }
                //DANG_SU_DUNG
            case "Trả phòng":{
                if (chiTietDonDatPhongDAO == null)
                    chiTietDonDatPhongDAO = new ChiTietDonDatPhongDAO();
                ChiTietDonDatPhong chiTietDonDatPhong = chiTietDonDatPhongDAO.findChiTietDonDatPhongByMaDonDatPhongAndMaPhong(donDatPhong.getMaDonDatPhong(), phong.getMaPhong());
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn trả phòng?",
                        () -> {
                            chiTietDonDatPhong.setNgayTra(LocalDateTime.now());
                            // cập nhật trạng thái phòng
                            chiTietDonDatPhong.setTrangThaiChiTietDonDatPhong(TrangThaiChiTietDonDatPhong.DA_TRA_PHONG);
                            boolean r= generalDAO.updateOb(chiTietDonDatPhong);
                            if(r) {
                                setData(phong, null, onItemClickedCallback);
                                manageTrangThaiPhong.updatePhongDangSuDungVaDatTruoc();
                                manageTrangThaiPhong.updatePhongTrong();
                                manageTrangThaiPhong.callInitLegendAtMapOfRoomController();
                            }
                            return r;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Trả phòng thành công", "Trả phòng thất bại")
                ));
                break;
            }
            // dang don dep
            case "Hoàn thành":{
              EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn hoàn thành dọn dẹp?",
                        () -> {
                            // cập nhật trạng thái phòng
                            phong.setTrangThaiPhong(TrangThaiPhong.TRONG);
                            boolean r= generalDAO.updateOb(phong);
                            if(r){
                                setData(phong, donDatPhong, onItemClickedCallback);
                                manageTrangThaiPhong.updatePhongConLai();
                                manageTrangThaiPhong.updatePhongTrong();
                                manageTrangThaiPhong.callInitLegendAtMapOfRoomController();
                            }
                            return r;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Hoàn thành dọn dẹp thành công", "Hoàn thành dọn dẹp thất bại")
                ));
                break;
            }
            case "Hủy":{
                EventBusManager.post(new ConfirmDialogEvent(
                        "Bạn có chắc muốn hủy dọn dẹp?",
                        () -> {
                            // cập nhật trạng thái phòng
                            phong.setTrangThaiPhong(TrangThaiPhong.TRONG);
                            boolean r= generalDAO.updateOb(phong);
                            if(r){
                                setData(phong, donDatPhong, onItemClickedCallback);
                                manageTrangThaiPhong.updatePhongConLai();
                                manageTrangThaiPhong.updatePhongTrong();
                                manageTrangThaiPhong.callInitLegendAtMapOfRoomController();
                            }
                            return r;
                        },
                        () -> System.out.println("Đã huỷ"),
                        List.of("Hủy dọn dẹp thành công", "Hủy dọn dẹp thất bại")
                ));
                break;
            }
        }
    }
    @Override
    public void setData(Object data) {

    }


}
