package com.hotel.hotel;

import com.hotel.hotel.MySqlConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

public class RoomAdd implements Initializable {

    @FXML
    private TextField floor,price;
    @FXML
    private ComboBox<Object> statusCombo;
    @FXML
    private ComboBox<Object> bedTypeCombo;
    @FXML
    private ComboBox<Object> roomFacilityCombo;

    MySqlConnection connection = new MySqlConnection();
    ObservableList<Object> status = FXCollections.observableArrayList( "Clean", "Booked","Not Clean");
    ObservableList<Object> beds = FXCollections.observableArrayList( "Single", "Double");
    ObservableList<Object> facilities = FXCollections.observableArrayList( "AC", "non/Ac");

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comboBoxUpdate();
    }

    public void addnowBtnClicked() throws SQLException {
        try {
            String room_bedtype = (String) bedTypeCombo.getSelectionModel().getSelectedItem();
            String room_floor = floor.getText();
            String room_status = (String) statusCombo.getSelectionModel().getSelectedItem();
            String room_facility = (String) roomFacilityCombo.getSelectionModel().getSelectedItem();
            String room_price = price.getText();

            Statement statement = connection.connect().createStatement();
            String queryData = "('" + room_bedtype + "','" + room_floor + "','" + room_facility + "','" + room_price + "','" + room_status + "',true);";
            String sql = "INSERT INTO RoomData(BedType,RoomFloor,Facility,Price,Status,active) VALUES" + queryData;

            statement.executeUpdate(sql);
            statement.close();

            setEmpty();
        }catch (SQLException e){
            Message.errorMessage(" "+e);
        }

    }

    public void setEmpty(){
        floor.setText("");
        price.setText("");
        comboBoxUpdate();
    }


    private void comboBoxUpdate() {
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
