package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.Entity.DonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiPhong;
import iuh.fit.qlksfxapp.Entity.Phong;
import iuh.fit.qlksfxapp.util.StyleUtil;
import javafx.fxml.FXML;
import javafx.scene.chart.BubbleChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.net.URL;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

public class RoomItemController {
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
    public  void setData(Phong phong, DonDatPhong donDatPhong) {
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
        if(donDatPhong==null){
            status.setText("Trống");
            tenNguoiDat.setText("Chưa có khách");
            tenDoan.setText("");
            ngayO.setText("");
            return;
        }
        StyleUtil.applyStyleToNode(status, StyleUtil.STATUS_COLOR_OF_ROOM.get(phong.getTrangThaiPhong().ordinal()), "#ffffff", 10, 1, 20);
        soLuongTre.setText(donDatPhong.getSoLuongTreEm()+" Trẻ");
        soLuongNguoiLon.setText(donDatPhong.getSoLuongNguoiLon()+" Người lớn");
        tenNguoiDat.setText(donDatPhong.getKhachHang().getTenKhachHang());
        tenDoan.setText(donDatPhong.getTenDoan());
        getNgayO(donDatPhong.getNgayNhan(), donDatPhong.getNgayTra());
        // Chưa có ảnh
        setStyle(phong.getTrangThaiPhong().ordinal());
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
    private void setStyle(int indexStatus) {
        String[] statusClasses = {
                "room-status-trong",
                "room-status-dang-su-dung",
                "room-status-dang-don-dep",
                "room-status-dat-truoc",
                "room-status-dang-sua-chua",
                "room-status-khong-su-dung"
        };

        String[] borderColors = {
                "#269EB6", // Trống
                "#DC3545", // Đang sử dụng
                "#6C757D", // Đang dọn dẹp
                "#FFC107", // Đặt trước
                "#FD7E14", // Đang sửa chữa
                "#343A40"  // Không sử dụng
        };

        // Lấy class ứng với trạng thái
        String className = statusClasses[indexStatus];
        String borderColor = borderColors[indexStatus];

        // Danh sách các node cần áp dụng style
        List<Label> nodes = Arrays.asList(
                typeRoomAndId, soLuongTre, soLuongNguoiLon, tenNguoiDat, tenDoan, ngayO
        );

        for (Label label : nodes) {
            label.getStyleClass().removeAll(statusClasses); // Xóa hết class cũ
            label.getStyleClass().add(className);           // Thêm class mới
        }

        // Đổi màu viền cho roomItem
        roomItem.setStyle(
                "-fx-border-color: " + borderColor + ";" +
                        "-fx-border-width: 2px;" +
                        "-fx-border-radius: 10px;"
        );

    }
}
