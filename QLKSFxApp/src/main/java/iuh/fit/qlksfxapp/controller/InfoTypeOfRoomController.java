package iuh.fit.qlksfxapp.controller;

import iuh.fit.qlksfxapp.DAO.Impl.GeneralDAOImpl;
import iuh.fit.qlksfxapp.Entity.LoaiPhong;
import iuh.fit.qlksfxapp.controller.ItemController.TypeOfRoomItemController;
import iuh.fit.qlksfxapp.util.StyleUtil;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;

public class InfoTypeOfRoomController {
    @FXML
    private ListView<LoaiPhong> listView;
    @FXML
    private ImageView mainImage;
    @FXML
    private ImageView thumb1,thumb2,thumb3;
    @FXML
    private Label bedLabel,
            areaLabel,
            childLabel,
            adultLabel,
            description,
            roomType;

    @FXML
    public void initialize() {
        GeneralDAOImpl generalDAOImpl = new GeneralDAOImpl();
        List<LoaiPhong> loaiPhongList = generalDAOImpl.findAll(LoaiPhong.class);

        // Đưa dữ liệu gốc vào ListView (không phải HBox)
        listView.getItems().addAll(loaiPhongList);

        // Render từng item bằng FXML qua ListCell
        listView.setCellFactory(param -> new ListCell<LoaiPhong>() {
            @Override
            protected void updateItem(LoaiPhong item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setGraphic(null);
                } else {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/item/TypeOfRoomItem.fxml"));
                        HBox itemUI = loader.load();
                        TypeOfRoomItemController controller = loader.getController();
                        controller.setData(item, "Trống 10/12");
                        setGraphic(itemUI);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        // Xử lý khi click chọn item
        listView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue != null) {
                handleClickItem(newValue); // ✅ newValue là LoaiPhong
            }
        });
        listView.getSelectionModel().selectFirst();
    }
    private void handleClickItem(LoaiPhong loaiPhong){
        // Set the image for the item
        initImage(loaiPhong.getTenLoaiPhong(), mainImage);
        initImage(loaiPhong.getTenLoaiPhong()+"1", thumb1);
        initImage(loaiPhong.getTenLoaiPhong()+"2", thumb2);
        initImage(loaiPhong.getTenLoaiPhong()+"3", thumb3);

        // Set the text for the labels
        bedLabel.setText(String.valueOf(loaiPhong.getSoLuongNguoiLon()) + " giường");
        areaLabel.setText(String.valueOf(loaiPhong.getDienTich())+" m2");
        childLabel.setText(String.valueOf(loaiPhong.getSoLuongTreEm())+" trẻ");
        adultLabel.setText(String.valueOf(loaiPhong.getSoLuongNguoiLon())+" người");
        description.setText(loaiPhong.getMoTa());
        initStyle(loaiPhong.getTenLoaiPhong());
    }
    private void initImage(String tenLoaiPhong, ImageView imgTypeOfRoom) {
        try {
            // Construct the image path
            String imagePath = "/images/typeOfRoom/" + tenLoaiPhong + ".jpg";
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
    private void initStyle(String tenLoaiPhong){
        roomType.setText(tenLoaiPhong);
        if(tenLoaiPhong.equals("Standard")){
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.getFirst(),"#ffffff",15,1,23);
        } else if (tenLoaiPhong.equals("Superior")) {
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.get(1),"#ffffff",15,1,23);
        } else if (tenLoaiPhong.equals("Deluxe")) {
            StyleUtil.applyStyleToNode(roomType,StyleUtil.TYPE_OF_ROOM_COLOR.get(2),"#ffffff",15,1,23);
        }
    }
}
