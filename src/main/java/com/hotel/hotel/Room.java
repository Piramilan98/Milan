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
import java.sql.Statement;
import java.util.Objects;
import java.util.ResourceBundle;

public class Room implements Initializable {

    @FXML
    private TableView<RoomData> roomTable;
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
    private TableColumn<RoomData,String> statusCol;
    @FXML
    private Label avaliable,singleRooms,doubleRooms,toCleanRooms;


    ObservableList<RoomData> RoomList = FXCollections.observableArrayList();
    MySqlConnection connection = new MySqlConnection();
    RoomSingleton roomData = RoomSingleton.getRoomData();
    int index = -1;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateTable();
    }

    public void getSelected(){
        index = roomTable.getSelectionModel().getSelectedIndex();
        roomData.setRoom_id(idCol.getCellData(index));
        roomData.setBed_type(bedCol.getCellData(index));
        roomData.setFloor(roomCol.getCellData(index));
        roomData.setFacility(facilityCol.getCellData(index));
        roomData.setPrice(priceCol.getCellData(index));
        roomData.setStatus(statusCol.getCellData(index));

    }

    public void addRoomBtn() throws IOException {
        loadFXML("RoomAdd");
    }

    public void removeRoomBtn() throws SQLException {
        if (index >= 0) {
            String id = roomData.getRoom_id();
            Statement statement = connection.connect().createStatement();
            String sql = "UPDATE RoomData SET active=false WHERE RoomID='"+id+"'";
            statement.executeUpdate(sql);
            statement.close();
        }
    }

    public void editRoomBtn() throws IOException {
        if (index >= 0)
            loadFXML("RoomEdit");
    }

    public void viewRoomBtn() throws IOException {
        if (index >=0)
            loadFXML("RoomView");
    }

    private void loadFXML(String file_name) throws IOException {
        Parent parent = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(file_name + "UI.fxml")));

        Stage stage = new Stage(StageStyle.DECORATED);
        stage.setTitle("Room Menu");

        stage.setScene(new Scene(parent));
        stage.show();
    }

    public void updateTable(){
        ImportData.refreshAllData();
        MySqlConnection connection = new MySqlConnection();
        String SQL = "SELECT * FROM RoomData where active=true";
        try {
            ResultSet resultSet = connection.connect().createStatement().executeQuery(SQL);
            while (resultSet.next()){
                RoomList.add(new RoomData(resultSet.getString("roomid"),
                        resultSet.getString("bedtype"),
                        resultSet.getString("roomfloor"),
                        resultSet.getString("facility"),
                        resultSet.getString("price"),
                        resultSet.getString("status")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        idCol.setCellValueFactory(new PropertyValueFactory<>("room_id"));
        bedCol.setCellValueFactory(new PropertyValueFactory<>("bed_type"));
        roomCol.setCellValueFactory(new PropertyValueFactory<>("room_floor"));
        facilityCol.setCellValueFactory(new PropertyValueFactory<>("facility"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));

        roomTable.setItems(RoomList);

        avaliable.setText(String.valueOf(ImportData.avaliable_rooms));
        singleRooms.setText(String.valueOf(ImportData.single_rooms));
        doubleRooms.setText(String.valueOf(ImportData.double_rooms));
        toCleanRooms.setText(String.valueOf(ImportData.to_clean));

    }

    public void refreshRoomBtn() {
        roomTable.getItems().clear();
        updateTable();
    }
}
