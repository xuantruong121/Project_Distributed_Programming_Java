package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.DAO.ChiTietDonDatPhongDAO;
import iuh.fit.qlksfxapp.Entity.ChiTietDonDatPhong;
import iuh.fit.qlksfxapp.Entity.Enum.TrangThaiChiTietDonDatPhong;
import iuh.fit.qlksfxapp.controller.BookingFormController;
import iuh.fit.qlksfxapp.util.FormatUtil;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.function.Consumer;

public class DetailBookingShortController {
    private BookingFormController.MemoTienChiTietDonDatPhong memoTienChiTietDonDatPhong;
    private ChiTietDonDatPhongDAO chiTietDonDatPhongDAO;
    @FXML private Label typeRoomAndId,status,soLuongNguoiLon,tienDichVu,tongTien;
    @FXML private ImageView imageRoom;
    @FXML private AnchorPane detailBookingShort;
    private Consumer<AnchorPane> onItemClickedCallback;

    public void setData(BookingFormController.MemoTienChiTietDonDatPhong memoTienChiTietDonDatPhong, Consumer<AnchorPane> callback){
        this.memoTienChiTietDonDatPhong = memoTienChiTietDonDatPhong;
        this.onItemClickedCallback = callback;
        init();
        setStyle(memoTienChiTietDonDatPhong.getChiTietDonDatPhong().getTrangThaiChiTietDonDatPhong());
    }
    private void init(){
        ChiTietDonDatPhong chiTietDonDatPhong =memoTienChiTietDonDatPhong.getChiTietDonDatPhong();
        try {
           // Construct the image path
            String imagePath = "/images/room/" + chiTietDonDatPhong.getPhong().getHinhAnh();
            // Get the resource URL
            URL imageUrl = getClass().getResource(imagePath);
//            System.out.println(Image URL: " + imageUrl);
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
        typeRoomAndId.setText(chiTietDonDatPhong.getPhong().getLoaiPhong().getTenLoaiPhong() + " / " + chiTietDonDatPhong.getPhong().getMaPhong());
        status.setText(chiTietDonDatPhong.getTrangThaiChiTietDonDatPhong().s);
        soLuongNguoiLon.setText(chiTietDonDatPhong.getKhachHang().size() + "");
        tienDichVu.setText(FormatUtil.formatMoney( memoTienChiTietDonDatPhong.getTienDichVu()));
       tongTien.setText(FormatUtil.formatMoney(memoTienChiTietDonDatPhong.getTongTien()));
    }
    @FXML
    private void handleClickItem(){
        if (onItemClickedCallback != null) {
            onItemClickedCallback.accept(detailBookingShort);
        }
    }
    private void setStyle(TrangThaiChiTietDonDatPhong tt){
        int index = tt.ordinal();
        String[] txfill={
                "txfill-datTruoc",
                "txfill-daNhanPhong",
                "txfill-daTraPhong",
                "txfill-daHuy",
                "txfill-daThanhToan"
        };
        String txfillClass = txfill[index];
        status.getStyleClass().removeIf(s -> s.startsWith("txfill-"));
        status.getStyleClass().add(txfillClass);

    }


}
