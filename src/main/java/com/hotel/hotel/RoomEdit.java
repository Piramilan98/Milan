package com.hotel.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RoomEdit implements Initializable {

    @FXML
    private TextField floor,price;
    @FXML
    private Label roomid;
    @FXML
    private ComboBox<Object> statusCombo;
    @FXML
    private ComboBox<Object> bedTypeCombo;
    @FXML
    private ComboBox<Object> roomFacilityCombo;

    MySqlConnection connection = new MySqlConnection();
    RoomSingleton roomData = RoomSingleton.getRoomData();
    ObservableList<Object> status = FXCollections.observableArrayList( "Clean", "Booked","Not Clean");
    ObservableList<Object> beds = FXCollections.observableArrayList( "Single", "Double");
    ObservableList<Object> facilities = FXCollections.observableArrayList( "AC", "non/Ac");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        roomid.setText(roomData.getRoom_id());
        floor.setText(roomData.getFloor());
        price.setText(roomData.getPrice());
        comboBoxUpdate();
    }

    public void addnowBtnClicked() throws SQLException {
        String room_id = roomid.getText();
        String room_bedtype = (String) bedTypeCombo.getSelectionModel().getSelectedItem();
        String room_floor = floor.getText();
        String room_status = (String) statusCombo.getSelectionModel().getSelectedItem();
        String room_facility = (String) roomFacilityCombo.getSelectionModel().getSelectedItem();
        String room_price = price.getText();

        Statement statement = connection.connect().createStatement();
        String sql = "UPDATE RoomData SET BedType='"+room_bedtype+"',RoomFloor='"+room_floor+"',Facility='"+room_facility+"',Price="+room_price+",Status='"+room_status+"' WHERE RoomID='"+room_id+"';";

        statement.executeUpdate(sql);
        statement.close();

        setEmpty();
    }

    public void setEmpty(){
        roomid.setText("");
        floor.setText("");
        price.setText("");
        comboBoxUpdate();
    }

    private void comboBoxUpdate() {
        statusCombo.getSelectionModel().select(roomData.getStatus());
        bedTypeCombo.getSelectionModel().select(roomData.getBed_type());
        roomFacilityCombo.getSelectionModel().select(roomData.getFacility());
        statusCombo.setItems(status);
        bedTypeCombo.setItems(beds);
        roomFacilityCombo.setItems(facilities);
        statusCombo.setStyle("-fx-font-size:20px");
        statusCombo.setStyle("-fx-font-weight: bold");
        bedTypeCombo.setStyle("-fx-font-size:20px");
        bedTypeCombo.setStyle("-fx-font-weight: bold");
        roomFacilityCombo.setStyle("-fx-font-size:20px");
        roomFacilityCombo.setStyle("-fx-font-weight: bold");
    }

}
