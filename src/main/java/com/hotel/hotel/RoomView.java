package com.hotel.hotel;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomView implements Initializable {

    @FXML
    Label roomid,bedtype,floor,price,status,facility;

    RoomSingleton room = RoomSingleton.getRoomData();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomid.setText(room.getRoom_id());
        bedtype.setText(room.getBed_type());
        floor.setText(room.getFloor());
        price.setText(room.getPrice());
        status.setText(room.getStatus());
        facility.setText(room.getFacility());
    }

}
