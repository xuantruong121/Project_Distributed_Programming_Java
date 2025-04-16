package iuh.fit.qlksfxapp.controller.ItemController;

import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.util.FormatUtil;
import iuh.fit.qlksfxapp.util.StyleUtil;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

import java.net.URL;

public class TypeOfRoomItemController {
    @FXML
    private ImageView imgTypeOfRoom;
    @FXML
    private Label roomType;
    @FXML
    private Label availability;

    @FXML
    private Label bedLabel;

    @FXML
    private Label areaLabel;

    @FXML
    private Label childLabel;
    @FXML
    private Label adultLabel;
    @FXML
    private Label description;
    @FXML
    private Label price;
    private LoaiPhong loaiPhong;

    public void setData(LoaiPhong loaiPhong,String avaiable) {
        this.loaiPhong = loaiPhong;
        roomType.setText(loaiPhong.getTenLoaiPhong());
        availability.setText(avaiable);
        bedLabel.setText(String.valueOf("1 giường đôi"));
        areaLabel.setText(String.valueOf(loaiPhong.getDienTich()));
        childLabel.setText(String.valueOf(loaiPhong.getSoLuongTreEm()));
        adultLabel.setText(String.valueOf(loaiPhong.getSoLuongNguoiLon()));
        description.setText(loaiPhong.getMoTa());
        price.setText(FormatUtil.formatMoney(loaiPhong.getGia())+" VNĐ");
        initImage();
        initStyle();
    }
    private void initImage(){
            try {
                // Construct the image path
                String imagePath = "/images/typeOfRoom/" + loaiPhong.getTenLoaiPhong() + ".jpg";

                // Get the resource URL
                URL imageUrl = getClass().getResource(imagePath);
                System.out.println("Image URL: " + imageUrl);
                if (imageUrl != null) {
                    // Create and set the image
                    Image image = new Image(imageUrl.toExternalForm());
                    imgTypeOfRoom.setImage(image);
                } else {
                    // Handle case when image doesn't exist
                    System.err.println("Image not found: " + imageUrl);
                    // Optionally set a default image
                    URL defaultImageUrl = getClass().getResource("/images/placeholder.png");
                    if (defaultImageUrl != null) {
                        imgTypeOfRoom.setImage(new Image(defaultImageUrl.toExternalForm()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                // Handle any other errors
            }
    }
    private void initStyle(){
        price.setFont(new Font(20));
        if(loaiPhong.getTenLoaiPhong().equals("Standard")){
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.getFirst(),"#fffff",15,1,16);
        } else if (loaiPhong.getTenLoaiPhong().equals("Superior")) {
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.get(1),"#fffff",15,1,16);
        } else if (loaiPhong.getTenLoaiPhong().equals("Deluxe")) {
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.get(2),"#fffff",15,1,16);
        }
    }
}
