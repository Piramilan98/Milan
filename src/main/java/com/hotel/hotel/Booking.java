package com.hotel.hotel;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;
import java.util.ResourceBundle;

public class Booking implements Initializable {

    @FXML
    private TableView<RoomData> bookingTable;
    @FXML
    private TableColumn<RoomData,String> idCol;
    @FXML
    private TableColumn<RoomData,String> bedCol;
    @FXML
    private TableColumn<RoomData,String> roomCol;
    @FXML
    private TableColumn<RoomData,String> facilityCol;
    @FXML
    private TableColumn<RoomData,String> priceCol;

    @FXML
    private Label avaliable,singleRooms,doubleRooms,bookedRooms;

    ObservableList<RoomData> BookingList = FXCollections.observableArrayList();
    int index = -1;
    BookingSingleton booking = BookingSingleton.getBookingData();
    RoomSingleton roomData = RoomSingleton.getRoomData();


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public void bookingBtnClicked() throws IOException {
        if (index >= 0)
            loadFXML("BookingMenu");
    }

    public void getSelected() {
        index = bookingTable.getSelectionModel().getSelectedIndex();

        if (index >= 0) {
            booking.setRood_id(idCol.getCellData(index));
            booking.setTotal(Integer.parseInt(priceCol.getCellData(index)));

            roomData.setRoom_id(idCol.getCellData(index));
            roomData.setBed_type(bedCol.getCellData(index));
            roomData.setFloor(roomCol.getCellData(index));
            roomData.setFacility(facilityCol.getCellData(index));
            roomData.setPrice(priceCol.getCellData(index));
            roomData.setStatus("Clean");
        }

    }

    public void viewRoomBtnClicked() throws IOException {
        if (index >= 0)
            loadFXML("RoomView");
    }

    private void loadFXML(String file_name) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file_name + "UI.fxml")));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Setting Menu");

        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void updateTable(){
        ImportData.refreshAllData();
        MySqlConnection connection = new MySqlConnection();
        String SQL = "SELECT * FROM RoomData WHERE Status = 'Clean' and active=true";
        try {
            ResultSet resultSet = connection.connect().createStatement().executeQuery(SQL);
            while (resultSet.next()){
                BookingList.add(new RoomData(resultSet.getString("roomid"),
                        resultSet.getString("bedtype"),
                        resultSet.getString("roomfloor"),
                        resultSet.getString("facility"),
                        resultSet.getString("price")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        bedCol.setCellValueFactory(new PropertyValueFactory<>("bed_type"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room_floor"));
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facility"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));

        bookingTable.setItems(BookingList);

        avaliable.setText(String.valueOf(ImportData.avaliable_rooms));
        singleRooms.setText(String.valueOf(ImportData.single_rooms));
        doubleRooms.setText(String.valueOf(ImportData.double_rooms));
        bookedRooms.setText(String.valueOf(ImportData.booked_rooms));
    }

    public void refreshBookingBtn() {
        bookingTable.getItems().clear();
        updateTable();
    }
}